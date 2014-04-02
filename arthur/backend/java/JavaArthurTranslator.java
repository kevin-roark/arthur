package arthur.backend.java;

import java.util.ArrayList;

import arthur.frontend.ParseNode;

public class JavaArthurTranslator {

  ParseNode source;
  ArrayList<JavaArthurType> globals;
  int blockDepth;
  JavaArthurFun activeFunction;
  boolean ignoreChildren;

  public JavaArthurTranslator(ParseNode source) {
    this.source = source;
    this.globals = new ArrayList<JavaArthurType>();
    this.blockDepth = 0;
    this.ignoreChildren = false;
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
    JavaArthurTranslator subTranslator;
    String s = "";
    switch (n.val) {
      case "globals":
        return "public class ArthurTranslation {\n";
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
        return fun.getFunDec();
      case "parameter":
        ParseNode ptype = n.children.get(0);
        ParseNode pname = n.children.get(1);
        JavaArthurVar p = new JavaArthurVar(pname.val, ptype.val);
        activeFunction.addParameter(p);
        return p.getVarDec();
      case "Identifier":
        return " " + n.children.get(0).val;
      case "Fun call":
        this.ignoreChildren = true;
        ParseNode fcname = n.children.get(0).children.get(0);
        ParseNode arguments = n.children.get(1);
        s += fcname.val + "(";
        for (ParseNode arg : arguments.children) {
          subTranslator = new JavaArthurTranslator(arg);
          s += subTranslator.translateTree();
          if (arg != arguments.children.get(arguments.children.size() - 1))
            s += ", ";
        }
        s += ")";
        return s;
      case "=":
        this.ignoreChildren = true;
        subTranslator = new JavaArthurTranslator(n.children.get(0));
        s += subTranslator.translateTree();
        subTranslator = new JavaArthurTranslator(n.children.get(1));
        return s + " = " + subTranslator.translateTree();
      default:
        return "";
    }
  }

  private String endNode(ParseNode n) {
    switch (n.val) {
      case "globals":
        return "\n}\n";
      case "Function":
        blockDepth--;
        return "\n}\n";
      case "parameters":
        return ") {\n";
      case "parameter":
        return ", ";
      case "Identifier":
        return " ";
      case "Fun call":
        this.ignoreChildren = false;
        return ";";
      case "=":
        this.ignoreChildren = false;
        return ";\n";
      case "variable":
        return ";\n";
      default:
        return "";
    }
  }

}
