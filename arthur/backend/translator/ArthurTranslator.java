package arthur.backend.translator;

import java.util.ArrayList;

import arthur.frontend.ParseNode;

public abstract class ArthurTranslator {

  public ParseNode source;
  public int blockDepth;
  public ArthurFun activeFunction;
  public boolean ignoreChildren;
  public boolean isStatement;

  public abstract String getIntro();
  public abstract String getOutro();
  public abstract String createAndTranslate(ParseNode source, boolean statement);
  public abstract String functionCode(ParseNode n);
  public abstract String numDec();
  public abstract String castCode(ParseNode n);
  public abstract String varCode(ParseNode n);
  public abstract String setToSymbol();
  public abstract String setToEndSymbol();
  public abstract String parameterCode(ParseNode param);
  public abstract String colorLiteral(ParseNode n);
  public abstract String numberLiteral(ParseNode n);
  public abstract String stringLiteral(ParseNode n);

  public String translateTree() {
    String translation = translate(this.source);
    return translation;
  }

  private String translate(ParseNode node) {
    String s = "";

    s += startNode(node);

    if (!this.ignoreChildren) {
      for (ParseNode child : node.children) {
        s += translate(child);
      }
    }

    s += endNode(node);

    return s;
  }

  private String startNode(ParseNode n) {
    String s = "";
    switch (n.val) {
      case "globals":
        return getIntro();
      case "arthur":
        return getIntro();
      case "variable":
        return varCode(n);
      case "Function":
        blockDepth++;
        return functionCode(n);
      case "parameters":
        this.ignoreChildren = true;
        for (ParseNode param : n.children) {
          s += parameterCode(param);
          if (param != n.children.get(n.children.size() - 1))
            s += ", ";
        }
        return s;
      case "if":
        return ifStyle(n, "if");
      case "elf":
        return ifStyle(n, "else if");
      case "else":
        return ifStyle(n, "else");
      case "dw":
        return ifStyle(n, "while");
      case "sugarloop":
        this.ignoreChildren = true;
        blockDepth++;

        String precursor = "";
        for (int i = 0; i <= blockDepth; i++)
          precursor += "_";
        String varname = precursor + "idx";

        ParseNode times = n.children.get(0);
        String val = times.children.get(0).val;

        String forloop = "for(";
        forloop += numDec();
        forloop += varname + " = 0; ";
        forloop += varname + " < " + val + "; ";
        forloop += varname + "++";
        forloop += ") {\n";

        ParseNode body = n.children.get(1);
        forloop += createAndTranslate(body, true);
        forloop += "\n}\n";
        return forloop;
      case "Identifier":
        return identifierVal(n.children.get(0).val);
      case "Fun call":
        this.ignoreChildren = true;
        ParseNode fcname = n.children.get(0).children.get(0);
        ParseNode arguments = n.children.get(1);
        s += fcname.val + "(";
        for (ParseNode arg : arguments.children) {
          s += createAndTranslate(arg, false);
          if (arg != arguments.children.get(arguments.children.size() - 1))
            s += ", ";
        }
        s += ")";
        return s;
      case "cast":
        this.ignoreChildren = true;
        return castCode(n);
      case "sysarg":
        ParseNode num = n.children.get(0);
        int aNum = Integer.parseInt(num.val) - 1;
        return "new ArthurString(sysargs[" + aNum + "])";
      case "Method call":
        return twoSideOp(n, ".");
      case "Property access":
        return twoSideOp(n, ".");
      case "Property":
        return n.children.get(0).val;
      case "Color":
        return colorLiteral(n);
      case "number":
        return numberLiteral(n);
      case "String":
        return stringLiteral(n);
      case "=":
        return twoSideOp(n, setToSymbol());
      case "is equal to":
        return twoSideOp(n, ".arthurEquals(");
      case "less than":
        return twoSideOp(n, ".lessThan(");
      case "less than or equal to":
        return twoSideOp(n, ".lessThanEquals(");
      case "greater than":
        return twoSideOp(n, ".greaterThan(");
      case "greater than or equal to":
        return twoSideOp(n, ".greaterThanEquals(");
      case "and":
        return twoSideOp(n, " && ");
      case "or":
        return twoSideOp(n, " || ");
      case "not":
        return "!";
      case "-":
        return twoSideOp(n, ".minus(");
      case "+":
        return twoSideOp(n, ".add(");
      case "*":
        return twoSideOp(n, ".multiply(");
      case "/":
        return twoSideOp(n, ".divide(");
      case "true":
        return "true";
      case "false":
        return "false";
      case "return":
        this.ignoreChildren = true;
        if (n.children.size() > 0) {
          s += createAndTranslate(n.children.get(0), false);
        }
        return "return " + s + ";\n";
      default:
        return "";
    }
  }

  public String identifierVal(String val) {
    return " " + val;
  }

  /* like '=' or '<' */
  private String twoSideOp(ParseNode n, String op) {
    this.ignoreChildren = true;
    String one = createAndTranslate(n.children.get(0), false);
    String two = createAndTranslate(n.children.get(1), false);
    String s = one + op + two;
    return s;
  }

  /* like 'if' or 'elf' or 'while' */
  private String ifStyle(ParseNode n, String stmt) {
    this.ignoreChildren = true;
    ParseNode cond = n.children.get(0);
    ParseNode body = n.children.get(1);

    String s = stmt + " (";
    s += createAndTranslate(cond, false);
    s += ") {\n";
    blockDepth++;
    s += createAndTranslate(body, true);
    blockDepth--;
    return s;
  }

  private String endNode(ParseNode n) {
    switch (n.val) {
      case "globals":
        return getOutro();
      case "arthur":
        return getOutro();
      case "Function":
        blockDepth--;
        return "\n}\n";
      case "parameters":
        this.ignoreChildren = false;
        return ") {\n";
      case "if":
        this.ignoreChildren = false;
        return "}\n";
      case "elf":
        this.ignoreChildren = false;
        return "}\n";
      case "else":
        this.ignoreChildren = false;
        return "}\n";
      case "dw":
        this.ignoreChildren = false;
        return "}\n";
      case "sugarloop":
        blockDepth--;
        this.ignoreChildren = false;
        return "";
      case "Identifier":
        return "";
      case "Fun call":
        return ender(false);
      case "cast":
        return ender(false);
      case "arg":
        return ender(false);
      case "Method call":
        return ender(false);
      case "Property access":
        return ender(false);
      case "=":
        return setToEndSymbol() + ender(false);
      case "is equal to":
        return ")" + ender(false);
      case "less than":
        return ")" + ender(false);
      case "less than or equal to":
        return ")" + ender(false);
      case "greater than":
        return ")" + ender(false);
      case "greater than or equal to":
        return ")" + ender(false);
      case "and":
        return ender(false);
      case "or":
        return ender(false);
      case "not":
        return ender(false);
      case "-":
        return ")" + ender(false);
      case "+":
        return ")" + ender(false);
      case "*":
        return ")" + ender(false);
      case "/":
        return ")" + ender(false);
      case "variable":
        return ender(null);
      case "return":
        this.ignoreChildren = false;
        return "";
      default:
        return "";
    }
  }

  private String ender(Boolean ignoreVal) {
    if (ignoreVal != null) {
      this.ignoreChildren = ignoreVal;
    }

    if (this.isStatement) {
      return ";\n";
    } else {
      return "";
    }
  }

}
