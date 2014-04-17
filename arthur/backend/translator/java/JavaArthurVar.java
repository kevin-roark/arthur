package arthur.backend.translator.java;

import arthur.frontend.ParseNode;
import arthur.backend.translator.ArthurVar;
import arthur.backend.media.*;

public class JavaArthurVar extends ArthurVar {

  public JavaArthurVar(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getVarDec() {
    switch (this.type) {
      case "num":
        return "ArthurNumber " + this.name;
      case "string":
        return "ArthurString " + this.name;
      case "color":
        return "ArthurColor " + this.name;
      case "bool":
        return "Boolean " + this.name;
      case "image":
        return "ArthurImage " + this.name;
      case "video":
        return "ArthurVideo " + this.name;
      case "sound":
        return "ArthurSound " + this.name;
      default:
        return "UNKNOWN " + " " + this.name;
    }
  }

  public static String javaName(String arthurName) {
    switch(arthurName) {
      case "num": return "ArthurNumber";
      case "string": return "ArthurString";
      case "color": return "ArthurColor";
      case "bool": return  "Boolean";
      case "Image": return "ArthurImage";
      case "Video": return "ArthurVideo";
      case "Sound": return "ArthurSound";
      default: return "UNKNOWN";
    }
  }

  public static String numLiteral(String val) {
    return "new ArthurNumber(" + val + ")";
  }

  public static String colorLiteral(ParseNode color) {
    String c = "new ArthurColor(";
    c += color.children.get(0).val + ", ";
    c += color.children.get(1).val + ", ";
    c += color.children.get(2).val + ", ";
    c += color.children.get(3).val;
    return c + ")";
  }

  public static String stringLiteral(String val) {
    return "new ArthurString(" + val + ")";
  }

}
