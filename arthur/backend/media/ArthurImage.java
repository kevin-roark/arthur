package arthur.backend.media;

/**
 * Java implementation of arthur image!
 */
public class ArthurImage extends ArthurMedia {

  public static final String IMAGE = "image";

  public ArthurImage() {
    this.type = IMAGE;
  }

  public ArthurMedia add(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.add(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return null;
    }
  }

  public ArthurMedia minus(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.minus(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return null;
    }
  }

  public ArthurMedia multiply(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.multiply(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return null;
    }
  }

  public ArthurMedia divide(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.divide(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return null;
    }
  }

}
