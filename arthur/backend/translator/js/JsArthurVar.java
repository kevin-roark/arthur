package arthur.backend.translator.js;

import arthur.frontend.ParseNode;
import arthur.backend.translator.ArthurVar;

public class JsArthurVar extends ArthurVar {

  public JsArthurVar(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getVarDec() {
    return "var " + this.name;
  }

  public String getParamDec() {
    return this.name;
  }

  public static String numLiteral(String val) {
    return "new JsArthurNum(" + val + ")";
  }

  public static String colorLiteral(ParseNode color) {
    String c = "new JsArthurColor(";
    c += color.children.get(0).val + ", ";
    c += color.children.get(1).val + ", ";
    c += color.children.get(2).val + ", ";
    c += color.children.get(3).val;
    return c + ")";
  }

  public static String stringLiteral(String val) {
    return "new JsArthurString(" + val + ")";
  }

}
