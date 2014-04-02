package arthur.backend.media;

/**
 * Java implementation of arthur image!
 */
public class ArthurImage extends ArthurMedia {

  public static String IMAGE = "image";

  public ArthurImage() {
    this.type = IMAGE;
  }

  public ArthurMedia add(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.add(this, two);
    } else {
      // coerce to Image?
      return null;
    }
  }

  public ArthurMedia minus(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.minus(this, two);
    } else {
      // coerce to Image?
      return null;
    }
  }

  public ArthurMedia multiply(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.multiply(this, two);
    } else {
      // coerce to Image?
      return null;
    }
  }

  public ArthurMedia divide(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.divide(this, two);
    } else {
      // coerce to Image?
      return null;
    }
  }

}
