package arthur.backend.media;

/**
 * Java implementation of arthur string!!
 */
public class ArthurString extends ArthurMedia {

  public static String STRING = "string";

  public ArthurString() {
    this("");
  }

  public ArthurString(String str) {
    this.type = STRING;
    this.str = str;
  }

  public ArthurMedia add(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.add(one, (ArthurString) two);
    } else if (two.type.equals(NUMBER)) {
      return JavaStringMath.add(one, (ArthurNumber) two);
    } else {
      // coerce later
      return null;
    }
  }

}
