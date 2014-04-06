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

  public ArthurNumber add(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.add(this, (ArthurNumber) two);
    } else {
      // coerce to Number?
      return this;
    }
  }

  public ArthurNumber minus(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.minus(this, (ArthurNumber) two);
    } else {
      // coerce to Number?
      return this;
    }
  }

  public ArthurNumber multiply(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.multiply(this, (ArthurNumber) two);
    } else {
      // coerce to Number?
      return this;
    }
  }

  public ArthurNumber divide(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.divide(this, (ArthurNumber) two);
    } else {
      // coerce to Number?
      return this;
    }
  }

  public int intval() {
    return (int) Math.round(this.val);
  }

  public String toString() {
    return "" + this.val;
  }

  public String jsLiteral() {
    return "new ArthurNumber(" + this.val + ")";
  }

}
