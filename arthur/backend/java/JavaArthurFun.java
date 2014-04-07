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
    String s = "public static ";
    switch (this.returnType) {
      case "num":
        return s + "JavaArthurNum " + this.name + "(";
      case "string":
        return s + "JavaArthurString " + this.name + "(";
      case "color":
        return s + "JavaArthurColor " + this.name + "(";
      case "Image":
        return s + "JavaArthurImage " + this.name + "(";
      case "Video":
        return s + "JavaArthurVideo " + this.name + "(";
      case "Sound":
        return s + "JavaArthurSound " + this.name + "(";
      case "void":
        return s + "void " + this.name + "(";
      default:
        return "unknown funciton!!";
    }
  }

}
