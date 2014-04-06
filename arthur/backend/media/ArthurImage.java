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

public class ArthurImage extends ArthurMedia {

  public static String IMAGE = "image";
  public String filename;
  public BufferedImage bf;
  public ArthurNumber height;
  public ArthurNumber width;

  /*
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
  */

  public void writeToFile(String filename) {
    try {
      File outputFile = new File(filename);
      ImageIO.write(this.bf, "jpg", outputFile);
    } catch (IOException e) {
      //error message
    }
  }

  public ArthurImage(BufferedImage buff, String fn) {
    filename = fn + ".jpg";
    this.type = IMAGE;
    bf = buff;
    WritableRaster raster = bf.getRaster();
    height = new ArthurNumber(raster.getHeight());
    width = new ArthurNumber(raster.getWidth());
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

  /*
  public String toString() {
    return "image width " + width + "px, height " + height + "px";
  }

  public String jsLiteral() {
    return "new ArthurImage()";
  }
  */

}
