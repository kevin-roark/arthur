package arthur.backend.java;

import java.util.ArrayList;

import arthur.frontend.ParseNode;

public class JavaArthurTranslator {

  ParseNode source;
  ArrayList<JavaArthurVar> globals;
  int blockDepth;

  public JavaArthurTranslator(ParseNode source) {
    this.source = source;
    this.globals = new ArrayList<JavaArthurVar>();
    this.blockDepth = 0;
  }

  public String translateTree() {
    String translation = translate(this.source);
    return translation;
  }

  private String translate(ParseNode node) {
    String s = "";

    s += startNode(node);

    for (ParseNode child : node.children) {
      s += translate(child);
    }

    s += endNode(node);

    return s;
  }

  private String startNode(ParseNode n) {
    String t = "";
    switch (n.val) {
      case "globals":
        t += "public class ArthurTranslation {\n";
        return t;
      case "variable":
        ParseNode type = n.children.get(0);
        ParseNode name = n.children.get(1);
        JavaArthurVar var = new JavaArthurVar(name.val, type.val);
        if (blockDepth == 0)
          this.globals.add(var);
        t += var.getVarDec();
        return t;
      default:
        return t;
    }
  }

  private String endNode(ParseNode n) {
    String t = "";
    switch (n.val) {
      case "globals":
        t += "}";
        return t;
      default:
        return ";";
    }
  }

}
