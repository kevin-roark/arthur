package arthur.backend.media;

/**
 * Java implementation of arthur color!
 */
public class ArthurColor extends ArthurMedia {

  public static String COLOR = "color";

  public ArthurColor() {
    this.type = COLOR;
  }

  public ArthurMedia add(ArthurMedia two) {
    if (two.type.equals(COLOR)) {
      return JavaColorMath.add(this, two);
    } else {
      // coerce to Color?
      return null;
    }
  }

  public ArthurMedia minus(ArthurMedia two) {
    if (two.type.equals(COLOR)) {
      return JavaColorMath.minus(this, two);
    } else {
      // coerce to Color?
      return null;
    }
  }

  public ArthurMedia multiply(ArthurMedia two) {
    if (two.type.equals(COLOR)) {
      return JavaColorMath.multiply(this, two);
    } else {
      // coerce to Color?
      return null;
    }
  }

  public ArthurMedia divide(ArthurMedia two) {
    if (two.type.equals(COLOR)) {
      return JavaColorMath.divide(this, two);
    } else {
      // coerce to Color?
      return null;
    }
  }

}
