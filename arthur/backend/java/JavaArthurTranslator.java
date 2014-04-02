package arthur.backend.java;

import java.util.ArrayList;

import arthur.frontend.ParseNode;

public class JavaArthurTranslator {

  ParseNode source;
  ArrayList<JavaArthurType> globals;
  int blockDepth;
  JavaArthurFun activeFunction;
  boolean ignoreChildren;
  boolean isStatement;

  public JavaArthurTranslator(ParseNode source) {
    this(source, true);
  }

  public JavaArthurTranslator(ParseNode source, boolean isStatement) {
    this.source = source;
    this.globals = new ArrayList<JavaArthurType>();
    this.blockDepth = 0;
    this.ignoreChildren = false;
    this.isStatement = isStatement;
  }

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
        ParseNode type = n.children.get(0);
        ParseNode name = n.children.get(1);
        JavaArthurVar var = new JavaArthurVar(name.val, type.val);
        if (blockDepth == 0)
          this.globals.add(var);
        return var.getVarDec();
      case "Function":
        ParseNode returnType = n.children.get(0);
        ParseNode fname = n.children.get(1);
        ParseNode parameters = n.children.get(2);
        JavaArthurFun fun = new JavaArthurFun(fname.val, returnType.val);
        if (blockDepth == 0)
          this.globals.add(fun);
        blockDepth++;
        activeFunction = fun;
        return "\n" + fun.getFunDec();
      case "parameters":
        this.ignoreChildren = true;
        for (ParseNode param : n.children) {
          ParseNode ptype = param.children.get(0);
          ParseNode pname = param.children.get(1);
          JavaArthurVar p = new JavaArthurVar(pname.val, ptype.val);
          activeFunction.addParameter(p);
          s += p.getVarDec();
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
      case "Identifier":
        return " " + n.children.get(0).val;
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
      case "Property access":
        return twoSideOp(n, ".");
      case "Property":
        return n.children.get(0).val;
      case "Color":
        return JavaArthurVar.colorLiteral(n);
      case "number":
        ParseNode num = n.children.get(0);
        return JavaArthurVar.numLiteral(num.val);
      case "String":
        ParseNode str = n.children.get(0);
        return JavaArthurVar.stringLiteral(str.val);
      case "=":
        return twoSideOp(n, " = ");
      case "is equal to":
        return twoSideOp(n, ".arthurEquals(");
      case "less than":
        return twoSideOp(n, ".lessThan(");
      case "greater than":
        return twoSideOp(n, ".greaterThan(");
      case "and":
        return twoSideOp(n, " && ");
      case "-":
        return twoSideOp(n, ".minus(");
      case "+":
        return twoSideOp(n, ".plus(");
      case "*":
        return twoSideOp(n, ".multiply(");
      case "/":
        return twoSideOp(n, ".divide(");
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
    s += createAndTranslate(body, true);
    return s;
  }

  private String getIntro() {
    String s = "/**\n";
    s += " * An automatically generated translation from arthur to java.\n";
    s += " */\n\n";
    s += "public class ArthurTranslation {\n";
    return s;
  }

  private String endNode(ParseNode n) {
    switch (n.val) {
      case "globals":
        return "\n}\n";
      case "arthur":
        return "\n}\n";
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
      case "Identifier":
        return "";
      case "Fun call":
        return ender(false);
      case "Property access":
        return ender(false);
      case "=":
        return ender(false);
      case "is equal to":
        return ")" + ender(false);
      case "less than":
        return ")" + ender(false);
      case "greater than":
        return ")" + ender(false);
      case "and":
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

  public static String createAndTranslate(ParseNode source, boolean statement) {
    JavaArthurTranslator t = new JavaArthurTranslator(source, statement);
    return t.translateTree();
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
