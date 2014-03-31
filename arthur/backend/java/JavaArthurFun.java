package arthur.backend.java;

import java.util.ArrayList;

public class JavaArthurFun extends JavaArthurType {

  String returnType;
  ArrayList<JavaArthurVar> parameters;

  public JavaArthurFun(String name, String type) {
    this.name = name;
    this.returnType = type;
    this.parameters = new ArrayList<JavaArthurVar>();
  }

  public String toString() {
    return "Function named \"" + name + "\" returning \"" + returnType  + "\"";
  }

  public void addParameter(JavaArthurVar var) {
    this.parameters.add(var);
  }

  public String getFunDec() {
    switch (this.returnType) {
      case "num":
        return "JavaArthurNum " + this.name + "(";
      case "string":
        return "JavaArthurString " + this.name + "(";
      case "color":
        return "JavaArthurColor " + this.name + "(";
      case "Image":
        return "JavaArthurImage " + this.name + "(";
      case "Video":
        return "JavaArthurVideo " + this.name + "(";
      case "Sound":
        return "JavaArthurSound " + this.name + "(";
      case "void":
        return "void " + this.name + "(";
      default:
        return "unknown funciton!!";
    }
  }

}
