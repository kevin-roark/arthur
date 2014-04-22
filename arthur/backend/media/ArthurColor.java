package arthur.backend.media;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;

import arthur.backend.builtins.java.*;

/**
 * Java implementation of arthur color!
 */
public class ArthurColor extends ArthurMedia {

  public static final String COLOR = "color";
  public ArthurNumber r, g, b, a;

  public ArthurColor() {
    this(new ArthurNumber(0), new ArthurNumber(0), new ArthurNumber(0), new ArthurNumber(0));
  }

  public ArthurColor(double r, double g, double b, double a) {
    this(new ArthurNumber(r), new ArthurNumber(g), new ArthurNumber(b), new ArthurNumber(a));
  }

  public ArthurColor(ArthurNumber r, ArthurNumber g, ArthurNumber b, ArthurNumber a) {
    this.type = COLOR;
    this.r=r;
    this.g=g;
    this.b=b;
    this.a=a;
  }

  public ArthurColor flip() {
    return new ArthurColor(this.b, this.g, this.r, this.a);
  }

  public ArthurColor opposite() {
    return new ArthurColor(255 - this.r.val, 255 - this.g.val, 255 - this.b.val, this.a.val);
  }

  public ArthurNumber valDiff(ArthurColor other) {
    double rd = Math.abs(this.r.val - other.r.val);
    double gd = Math.abs(this.g.val - other.g.val);
    double bd = Math.abs(this.b.val - other.b.val);
    double ad = Math.abs(this.a.val - other.a.val);
    return new ArthurNumber(rd + gd + bd + ad);
  }

  public ArthurColor add(ArthurMedia two) {
    if (two.type.equals(COLOR)) {
      return JavaColorMath.add(this, (ArthurColor)two);
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      return JavaColorMath.add(this, (ArthurImage)two);
    }
    else if (two.type.equals(ArthurSound.SOUND)) {
      return JavaColorMath.add(this, (ArthurSound)two);
    }
    else if (two.type.equals(ArthurVideo.VIDEO)) {
      return JavaColorMath.add(this, (ArthurVideo)two);
    }
    else if (two.type.equals(ArthurString.STRING)) {
      return JavaColorMath.add(this, (ArthurString)two);
    }
    else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaColorMath.add(this, (ArthurNumber)two);
    }
    else {
      return this;
    }
  }

  public ArthurColor minus(ArthurMedia two) {
    if (two.type.equals(COLOR)) {
      return JavaColorMath.minus(this, (ArthurColor)two);
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      return JavaColorMath.minus(this, (ArthurImage)two);
    }
    else if (two.type.equals(ArthurSound.SOUND)) {
      return JavaColorMath.minus(this, (ArthurSound)two);
    }
    else if (two.type.equals(ArthurVideo.VIDEO)) {
      return JavaColorMath.minus(this, (ArthurVideo)two);
    }
    else if (two.type.equals(ArthurString.STRING)) {
      return JavaColorMath.minus(this, (ArthurString)two);
    }
    else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaColorMath.minus(this, (ArthurNumber)two);
    }
    return this;
  }

  public ArthurColor multiply(ArthurMedia two) {
    if (two.type.equals(COLOR)) {
      return JavaColorMath.multiply(this, (ArthurColor) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaColorMath.multiply(this, (ArthurNumber) two);
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      ArthurImage img = (ArthurImage) two;
      return JavaColorMath.multiply(this, img.toColor());
    } else if (two.type.equals(ArthurString.STRING)) {
      ArthurString str = (ArthurString) two;
      return JavaColorMath.multiply(this, str.toColor());
    }
    return this;
  }

  public ArthurColor divide(ArthurMedia two) {
    if (two.type.equals(COLOR)) {
      return JavaColorMath.divide(this, (ArthurColor) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaColorMath.divide(this, (ArthurNumber) two);
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      ArthurImage img = (ArthurImage) two;
      return JavaColorMath.divide(this, img.toColor());
    } else if (two.type.equals(ArthurString.STRING)) {
      ArthurString str = (ArthurString) two;
      return JavaColorMath.divide(this, str.toColor());
    }
    return this;
  }

  public ArthurMedia castTo(ArthurString mediaType) {
    return castTo(mediaType.str);
  }

  public ArthurMedia castTo(String mediaType) {
    if (mediaType.equals("string")) {
      return this.toArtString();
    } else if (mediaType.equals("number")) {
      return this.toNumber();
    } else if (mediaType.equals("Image")) {
      return this.toImage();
    }

    return this;
  }

  public ArthurNumber toNumber() {
    double val = 0;
    val += this.g.val;
    val += 1000 * this.b.val;
    val += 1000000 * this.r.val;
    return new ArthurNumber(val);
  }

  public ArthurImage toImage() {
    String filename = "color-" + (new Random()).nextInt() + ".jpg";

    int w = 300;
    int h = 300;

    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

    Graphics2D g = image.createGraphics();
    Color c = new Color(this.r.val.floatValue(), this.g.val.floatValue(), this.b.val.floatValue(), this.a.val.floatValue());
    g.setColor(c);
    g.fillRect(0, 0, w, h);
    g.dispose();

    return new ArthurImage(image, filename);
  }

  public ArthurVideo toVideo() {
    return JavaVideoMath.add(new ArthurVideo("ZERO.mp4"), this, "color-" + System.currentTimeMillis() + ".mp4");
  }

  public ArthurString toArtString() {
    return closestString();
  }

  public ArthurString closestString() {
    ArthurNumber bestDiff = new ArthurNumber(Double.POSITIVE_INFINITY);
    ArthurNumber diff;
    ArthurColor current;
    ArthurString best = new ArthurString("color");

    for (String c : JavaBuiltins.colors()) {
      current = JavaBuiltins.colorMap().get(c);
      diff = this.valDiff(current);

      if (diff.val < bestDiff.val) {
        bestDiff = diff;
        best = new ArthurString(c);
      }
    }

    return best;
  }

  public ArthurString furthestString() {
    ArthurNumber bestDiff = new ArthurNumber(Double.NEGATIVE_INFINITY);
    ArthurNumber diff;
    ArthurColor current;
    ArthurString best = new ArthurString("color");

    for (String c : JavaBuiltins.colors()) {
      current = JavaBuiltins.colorMap().get(c);
      diff = this.valDiff(current);

      if (diff.val > bestDiff.val) {
        bestDiff = diff;
        best = new ArthurString(c);
      }
    }

    return best;
  }

  public boolean arthurEquals(ArthurMedia two) {
    if (two.type.equals(COLOR)) {
      ArthurColor t = (ArthurColor) two;
      if (this.r.arthurEquals(t.r) &&
          this.g.arthurEquals(t.g) &&
          this.b.arthurEquals(t.b) &&
          this.a.arthurEquals(t.a))
          return true;

      return false;
    }

    return false;
  }

  public String toString() {
    return "<<" + this.r + ", " + this.g + ", " + this.b + ">>";
  }

  public String jsLiteral() {
    String js = "new ArthurColor(";
    js += "'" + json() + "'";
    js += ")";
    return js;
  }

  public String json() {
    String json = "{'r': " + r.val + ", ";
    json += "'g': " + g.val + ", ";
    json += "'b': " + b.val + ", ";
    if (this.frame != null) {
      json += "'frame': " + this.frame.json() + ", ";
    }
    if (this.delay != null) {
      json += "'delay': " + this.delay.val + ", ";
    }
    json += "'a': " + a.val + "}";
    json = json.replace("'", "\"");
    return json;
  }

  /* saves state of current media to filename */
  public void writeToFile(String filename) {
    String json = json();
    try {
      PrintWriter out = new PrintWriter(filename);
      out.println(json);
      out.close();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}
