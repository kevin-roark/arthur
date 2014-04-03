//package arthur.backend.media;

/**
 * Java implementation of arthur image!
 */
public class ArthurImage extends ArthurMedia {

  public static String IMAGE = "image";
  public String filename;

  public ArthurImage() {
    this.type = IMAGE;
  }

  public ArthurImage(String fn) {
    this.type = IMAGE;
    this.filename = fn;
  }
  
  public ArthurImage add(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.add(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return null;
    }
  }

  public ArthurImage minus(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.minus(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return null;
    }
  }

  public ArthurImage multiply(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.multiply(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return null;
    }
  }

  public ArthurImage divide(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.divide(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return null;
    }
  }

}
