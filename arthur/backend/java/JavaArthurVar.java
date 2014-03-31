package arthur.backend.java;

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
      case "Image":
        return "JavaArthurImage " + this.name;
      case "Video":
        return "JavaArthurVideo " + this.name;
      case "Sound":
        return "JavaArthurSound " + this.name;
      default:
        return "unknown variable!!";
    }
  }

}
