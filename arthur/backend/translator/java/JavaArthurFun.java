package arthur.backend.translator.java;

import java.util.ArrayList;

import arthur.backend.translator.ArthurFun;
import arthur.backend.translator.ArthurVar;
import arthur.backend.media.*;

public class JavaArthurFun extends ArthurFun {

  public JavaArthurFun(String name, String type) {
    this.name = name;
    this.returnType = type;
    this.parameters = new ArrayList<ArthurVar>();
  }

  public String getFunDec() {
    String s = "public ";
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
