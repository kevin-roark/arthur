package arthur.backend.translator;

import arthur.frontend.ParseNode;

public abstract class ArthurVar extends ArthurType {

  public String type;

  public abstract String getVarDec();
  public static String numLiteral(String val) {
    return "";
  }
  public static String colorLiteral(ParseNode color) {
    return "";
  }
  public static String stringLiteral(String val) {
    return "";
  }

  public String toString() {
    return "Variable named \"" + name + "\" of type \"" + type  + "\"";
  }

}
