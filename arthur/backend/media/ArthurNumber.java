package arthur.backend.media;

/**
 * Java implementation of arthur number!
 */
public class ArthurNumber extends ArthurMedia {

  public static final String NUMBER = "number";

  public Double val;

  public ArthurNumber() {
    this(0.0);
  }

  public ArthurNumber(double val) {
    this.type = NUMBER;
    this.val = val;
  }

  public ArthurMedia add(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.add(this, (ArthurNumber) two);
    } else {
      // coerce to Number?
      return null;
    }
  }

  public ArthurMedia minus(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.minus(this, (ArthurNumber) two);
    } else {
      // coerce to Number?
      return null;
    }
  }

  public ArthurMedia multiply(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.multiply(this, (ArthurNumber) two);
    } else {
      // coerce to Number?
      return null;
    }
  }

  public ArthurMedia divide(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.divide(this, (ArthurNumber) two);
    } else {
      // coerce to Number?
      return null;
    }
  }

}
