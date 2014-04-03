package arthur.backend.media;

/**
 * Java implementation of arthur image!
 */
public class ArthurImage extends ArthurMedia {

  public static final String IMAGE = "image";

  public ArthurNumber height;
  public ArthurNumber width;

  public ArthurImage() {
    this(new ArthurNumber(0), new ArthurNumber(0));
  }

  public ArthurImage(ArthurNumber w, ArthurNumber h) {
    this.type = IMAGE;
    this.width = w;
    this.height = h;
  }

  public ArthurNumber pixel(ArthurNumber i, ArthurNumber j) {
    return new ArthurNumber(0);
  }

  public void pixel(ArthurNumber i, ArthurNumber j, ArthurColor c) {

  }

  public ArthurImage add(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.add(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return this;
    }
  }

  public ArthurImage minus(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.minus(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return this;
    }
  }

  public ArthurImage multiply(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.multiply(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return this;
    }
  }

  public ArthurImage divide(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.divide(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      return this;
    }
  }

  public String toString() {
    return "image width " + width + "px, height " + height + "px";
  }

  public String jsLiteral() {
    return "new ArthurImage()";
  }

}
