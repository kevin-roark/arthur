package arthur.backend.media;

/**
 * Java implementation of arthur number!
 */
public class ArthurNumber extends ArthurMedia implements java.io.Serializable {

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
    } else if (two.type.equals(ArthurColor.COLOR)) {
      ArthurColor color = (ArthurColor) two;
      return JavaNumberMath.add(this, color.toNumber());
    } else if (two.type.equals(ArthurString.STRING)) {
      ArthurString str = (ArthurString) two;
      return JavaNumberMath.add(this, str.toNumber());
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      ArthurImage image = (ArthurImage) two;
      return JavaNumberMath.add(this, image.toNumber());
    }

    return this;
  }

  public ArthurNumber minus(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.minus(this, (ArthurNumber) two);
    } else if (two.type.equals(ArthurColor.COLOR)) {
      ArthurColor color = (ArthurColor) two;
      return JavaNumberMath.minus(this, color.toNumber());
    } else if (two.type.equals(ArthurString.STRING)) {
      ArthurString str = (ArthurString) two;
      return JavaNumberMath.minus(this, str.toNumber());
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      ArthurImage image = (ArthurImage) two;
      return JavaNumberMath.minus(this, image.toNumber());
    }

    return this;
  }

  public ArthurNumber multiply(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.multiply(this, (ArthurNumber) two);
    } else if (two.type.equals(ArthurColor.COLOR)) {
      ArthurColor color = (ArthurColor) two;
      return JavaNumberMath.multiply(this, color.toNumber());
    } else if (two.type.equals(ArthurString.STRING)) {
      ArthurString str = (ArthurString) two;
      return JavaNumberMath.multiply(this, str.toNumber());
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      ArthurImage image = (ArthurImage) two;
      return JavaNumberMath.multiply(this, image.toNumber());
    }

    return this;
  }

  public ArthurNumber divide(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.divide(this, (ArthurNumber) two);
    } else if (two.type.equals(ArthurColor.COLOR)) {
      ArthurColor color = (ArthurColor) two;
      return JavaNumberMath.divide(this, color.toNumber());
    } else if (two.type.equals(ArthurString.STRING)) {
      ArthurString str = (ArthurString) two;
      return JavaNumberMath.divide(this, str.toNumber());
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      ArthurImage image = (ArthurImage) two;
      return JavaNumberMath.divide(this, image.toNumber());
    }

    return this;
  }

  public boolean lessThan(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      ArthurNumber t = (ArthurNumber) two;
      return (this.val < t.val);
    } else {
      return false;
    }
  }

  public boolean greaterThan(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      ArthurNumber t = (ArthurNumber) two;
      return (this.val > t.val);
    } else {
      return false;
    }
  }

  public boolean lessThanEquals(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      ArthurNumber t = (ArthurNumber) two;
      if (this.val < t.val || this.arthurEquals(two))
        return true;
      else
        return false;
    }

    return false;
  }

  public boolean greaterThanEquals(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      ArthurNumber t = (ArthurNumber) two;
      if (this.val > t.val || this.arthurEquals(two))
        return true;
      else
        return false;
    }

    return false;
  }

  public boolean arthurEquals(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      ArthurNumber t = (ArthurNumber) two;
      double diff = Math.abs(this.val - t.val);
      return (diff <= 0.0001);
    }

    return false;
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
