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
    } else {
      ArthurNumber num = (ArthurNumber) two.castTo("num");
      return JavaNumberMath.divide(this, num);
    }
  }

  public ArthurNumber minus(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.minus(this, (ArthurNumber) two);
    } else {
      ArthurNumber num = (ArthurNumber) two.castTo("num");
      return JavaNumberMath.divide(this, num);
    }
  }

  public ArthurNumber multiply(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.multiply(this, (ArthurNumber) two);
    } else {
      ArthurNumber num = (ArthurNumber) two.castTo("num");
      return JavaNumberMath.divide(this, num);
    }
  }

  public ArthurNumber divide(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      return JavaNumberMath.divide(this, (ArthurNumber) two);
    } else {
      ArthurNumber num = (ArthurNumber) two.castTo("num");
      return JavaNumberMath.divide(this, num);
    }
  }

  public boolean lessThan(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      ArthurNumber t = (ArthurNumber) two;
      return (this.val < t.val);
    } else {
      ArthurNumber num = (ArthurNumber) two.castTo("num");
      return lessThan(num);
    }
  }

  public boolean greaterThan(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      ArthurNumber t = (ArthurNumber) two;
      return (this.val > t.val);
    } else {
      ArthurNumber num = (ArthurNumber) two.castTo("num");
      return greaterThan(num);
    }
  }

  public boolean lessThanEquals(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      ArthurNumber t = (ArthurNumber) two;
      if (this.val < t.val || this.arthurEquals(two))
        return true;
      else
        return false;
    } else {
      ArthurNumber num = (ArthurNumber) two.castTo("num");
      return lessThanEquals(num);
    }
  }

  public boolean greaterThanEquals(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      ArthurNumber t = (ArthurNumber) two;
      if (this.val > t.val || this.arthurEquals(two))
        return true;
      else
        return false;
    } else {
      ArthurNumber num = (ArthurNumber) two.castTo("num");
      return greaterThanEquals(num);
    }
  }

  public boolean arthurEquals(ArthurMedia two) {
    if (two.type.equals(NUMBER)) {
      ArthurNumber t = (ArthurNumber) two;
      double diff = Math.abs(this.val - t.val);
      return (diff <= 0.0001);
    } else {
      ArthurNumber num = (ArthurNumber) two.castTo("num");
      return arthurEquals(num);
    }
  }

  public ArthurMedia castTo(ArthurString mediaType) {
    return castTo(mediaType.str);
  }

  public ArthurMedia castTo(String mediaType) {
    if (mediaType.equals("Image")) {
      return this.toImage();
    } else if (mediaType.equals("Sound")) {
      return this.toSound();
    } else if (mediaType.equals("string")) {
      return this.toArtString();
    } else if (mediaType.equals("Video")) {
      return this.toVideo();
    } else if (mediaType.equals("color")) {
      return this.toColor();
    }

    return this;
  }

  public ArthurColor toColor() {
    double r = Math.max(this.val, 255);
    double g = Math.max(this.val, 255);
    double b = Math.max(this.val, 255);
    double a = Math.max(this.val / 255.0, 1.0);
    return new ArthurColor(r, g, b, a);
  }

  public ArthurString toArtString() {
    String str = "" + this.val;
    return new ArthurString(str);
  }

  public ArthurVideo toVideo() {
    ArthurVideo zero = new ArthurVideo(ArthurVideo.ZERO);
    return zero.add(this);
  }

  public ArthurImage toImage() {
    ArthurImage zero = new ArthurImage(ArthurImage.ZERO);
    return zero.add(this);
  }

  public ArthurSound toSound() {
    ArthurSound zero = new ArthurSound(ArthurSound.ZERO);
    return zero.add(this);
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
