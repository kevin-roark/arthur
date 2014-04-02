package arthur.backend.translator;

import java.util.ArrayList;

public abstract class ArthurFun extends ArthurType {

  public String returnType;
  public ArrayList<ArthurVar> parameters;

  public abstract String getFunDec();

  public String toString() {
    return "Function named \"" + name + "\" returning \"" + returnType  + "\"";
  }

  public void addParameter(ArthurVar var) {
    this.parameters.add(var);
  }

}
