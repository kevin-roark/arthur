package arthur.backend.translator.js;

import java.util.ArrayList;

import arthur.backend.translator.ArthurFun;
import arthur.backend.translator.ArthurVar;

public class JsArthurFun extends ArthurFun {

  public JsArthurFun(String name, String type) {
    this.name = name;
    this.returnType = type;
    this.parameters = new ArrayList<ArthurVar>();
  }

  public String getFunDec() {
    String s = "function ";
    s += this.name;
    s += "(";
    return s;
  }

}
