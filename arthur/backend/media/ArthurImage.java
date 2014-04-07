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

  public static final String IMAGE = "image";
  public String filename;
  public BufferedImage bf;
  public ArthurNumber height;
  public ArthurNumber width;

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
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.add(this, (ArthurImage) two);
    } else {
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
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaImageMath.multiply(this, (ArthurNumber) two);
    } else {
      return this;
    }
  }

  public ArthurImage divide(ArthurMedia two) {
    if (two.type.equals(IMAGE)) {
      return JavaImageMath.divide(this, (ArthurImage) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaImageMath.divide(this, (ArthurNumber) two);
    } else {
      return this;
    }
  }

  public void writeToFile(String filename) {
    try {
      File outputFile = new File(filename);
      ImageIO.write(this.bf, "jpg", outputFile);
    } catch (IOException e) {
      //error message
      e.printStackTrace();
    }
  }

  public ArthurColor getAverageColor(){
    int[] rgbArray=bf.getRGB(0, 0, bf.getWidth(), bf.getHeight(), null, 0, bf.getWidth());

    Color c = new Color(rgbArray[(int)((Math.random()*(bf.getHeight()*bf.getWidth()))+1)]);
    double red = c.getRed();
    double green = c.getGreen();
    double blue = c.getBlue();

    //Random sampling to get average color of an image
    for(int i=0;i<199;i++){
      c = new Color(rgbArray[(int)((Math.random()*(bf.getHeight()*bf.getWidth()))+1)]);
    red = red+ c.getRed();
    green = green+ c.getGreen();
    blue = blue+ c.getBlue();
    }
    double redAvg=red/200;
    double greenAvg=green/200;
    double blueAvg=blue/200;

    return new ArthurColor(redAvg,greenAvg,blueAvg, 1.0);

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
