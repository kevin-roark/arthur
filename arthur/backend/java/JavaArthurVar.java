package arthur.backend.java;

import arthur.frontend.ParseNode;

public class JavaArthurVar extends JavaArthurType {

  String type;

  public JavaArthurVar(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String toString() {
    return "Variable named \"" + name + "\" of type \"" + type  + "\"";
  }

  public String getVarDec() {
    switch (this.type) {
      case "num":
        return "JavaArthurNum " + this.name;
      case "string":
        return "JavaArthurString " + this.name;
      case "color":
        return "JavaArthurColor " + this.name;
      case "image":
        return "JavaArthurImage " + this.name;
      case "video":
        return "JavaArthurVideo " + this.name;
      case "Sound":
        return "JavaArthurSound " + this.name;
      default:
        return "unknown variable!!";
    }
  }

  public static String numLiteral(String val) {
    return "new JavaArthurNum(" + val + ")";
  }

  public static String colorLiteral(ParseNode color) {
    String c = "new JavaArthurColor(";
    c += color.children.get(0).val + ", ";
    c += color.children.get(1).val + ", ";
    c += color.children.get(2).val + ", ";
    c += color.children.get(3).val;
    return c + ")";
  }

  public static String stringLiteral(String val) {
    return "new JavaArthurString(" + val + ")";
  }

}
