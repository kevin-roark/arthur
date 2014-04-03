package arthur.backend.media;

/**
 * Java implementation of arthur string!!
 */
public class ArthurString extends ArthurMedia {

  public static final String STRING = "string";

  public String str;

  public ArthurString() {
    this("");
  }

  public ArthurString(String str) {
    this.type = STRING;
    this.str = str;
  }

  public ArthurMedia add(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.add(this, (ArthurString) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaStringMath.add(this, (ArthurNumber) two);
    } else {
      // coerce later
      return null;
    }
  }

  public ArthurMedia minus(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.minus(this, (ArthurString) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaStringMath.minus(this, (ArthurNumber) two);
    } else {
      // coerce later
      return null;
    }
  }

  public ArthurMedia multiply(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.multiply(this, (ArthurString) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaStringMath.multiply(this, (ArthurNumber) two);
    } else {
      // coerce later
      return null;
    }
  }

  public ArthurMedia divide(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.divide(this, (ArthurString) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaStringMath.divide(this, (ArthurNumber) two);
    } else {
      // coerce later
      return null;
    }
  }

}
