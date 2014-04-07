package arthur.backend.media;

/**
 * Java implementation of arthur image!
 */
import java.io.*;
import java.awt.image.*;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.imageio.*;
import java.lang.*;

public class ArthurImage extends ArthurMedia implements java.io.Serializable {

  public static final String IMAGE = "image";
  public String filename;
  public transient BufferedImage bf;
  public ArthurNumber height;
  public ArthurNumber width;
  public ArthurNumber murk;

  /*
  public ArthurColor pixel(ArthurNumber i, ArthurNumber j) {
    return null;
  }

  public void pixel(ArthurNumber i, ArthurNumber j, ArthurColor c) {

  }
  */

  public ArthurImage(BufferedImage buff, ArthurString fn) {
    this(buff, fn.str);
  }

  public ArthurImage(BufferedImage buff, String fn) {
    filename = fn;
    this.type = IMAGE;
    bf = buff;
    WritableRaster raster = bf.getRaster();
    height = new ArthurNumber(raster.getHeight());
    width = new ArthurNumber(raster.getWidth());
  }

  public ArthurImage(ArthurString fn) {
    this(fn.str);
  }

  public ArthurImage(String fn) {
    this.type = IMAGE;
    bf = null;
    filename = fn;
    try {
      bf = ImageIO.read(new File(fn));
    } catch (IOException e) {

    }
    if (bf == null) {
      System.out.println("Error - couldn't get that image.");
    }
    WritableRaster raster = bf.getRaster();
    height = new ArthurNumber(raster.getHeight());
    width = new ArthurNumber(raster.getWidth());
  }

  public ArthurImage add(ArthurMedia two) {
    ArthurImage res;
    if (two.type.equals(IMAGE)) {
      res = JavaImageMath.add(this, (ArthurImage) two);
    } else {
      res = this;
    }
    res.murk = this.murk;
    return res;
  }

  public ArthurImage minus(ArthurMedia two) {
    ArthurImage res;
    if (two.type.equals(IMAGE)) {
      res = JavaImageMath.minus(this, (ArthurImage) two);
    } else {
      // coerce to Image?
      res = this;
    }
    res.murk = this.murk;
    return res;
  }

  public ArthurImage multiply(ArthurMedia two) {
    ArthurImage res;
    if (two.type.equals(IMAGE)) {
      res = JavaImageMath.multiply(this, (ArthurImage) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      res = JavaImageMath.multiply(this, (ArthurNumber) two);
    } else {
      res = this;
    }
    res.murk = this.murk;
    return res;
  }

  public ArthurImage divide(ArthurMedia two) {
    ArthurImage res;
    if (two.type.equals(IMAGE)) {
      res = JavaImageMath.divide(this, (ArthurImage) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      res = JavaImageMath.divide(this, (ArthurNumber) two);
    } else {
      res = this;
    }
    res.murk = this.murk;
    return res;
  }

  public void writeToFile(String fname) {
    try {
      File outputFile = new File(fname);
      ImageIO.write(this.bf, "jpg", outputFile);
      this.filename = fname.substring(fname.indexOf('/') + 1); // remove 'buster'
    } catch (IOException e) {
      //error message
      e.printStackTrace();
    }
  }

  public String json() {
    String js = "{";
    js += "'filename': '" + this.filename + "'";
    if (this.frame != null) {
      js += ", 'frame': " + this.frame.json() + "";
    }
    if (this.murk != null) {
      js += ", 'murk': " + this.murk.val + "";
    }
    js += "}";
    js = js.replace("'", "\"");
    return js;
  }

  public String toString() {
    return "image width " + width.val + "px, height " + height.val + "px";
  }

  public String jsLiteral() {
    return "new ArthurImage(" + json() + ")";
  }

}
