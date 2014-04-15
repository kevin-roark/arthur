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
import arthur.backend.*;
import java.util.Random;

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

  public ArthurMedia castTo(ArthurString mediaType) {
    return castTo(mediaType.str);
  }

  public ArthurMedia castTo(String mediaType) {
    if (mediaType.equals("string")) {
      return this.toArtString();
    } else if (mediaType.equals("number")) {
      return this.toNumber();
    } else if (mediaType.equals("color")) {
      return this.toColor();
    }

    return this;
  }

  public ArthurString toArtString() {
      String s = toAscii();
      return new ArthurString(s);
  }

  public ArthurNumber toNumber() {
    return this.width.add(this.height);
  }

  public ArthurColor toColor() {
    /*
    double r = 0;
    double g = 0;
    double b = 0;
    double a = 0;

    int w = this.bf.getRaster().getWidth();
    int h = this.bf.getRaster().getHeight();
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; i++) {
        int rgb = this.bf.getRGB(i, j);
        Color c = new Color(rgb);
        r += c.getRed();
        g += c.getGreen();
        b += c.getBlue();
        a += c.getAlpha();
      }
    }

    r = r / (i * j);
    g = g / (i * j);
    b = b / (i * j);
    a = a / (i * j) / 255;
    return new ArthurColor(r, g, b, a);*/

    return getAverageColor();
  }

  public String toAscii() {
    String filearg = "temp-" + (new Random()).nextInt() + ".jpg";
    this.writeToFile(filearg);

    try {
      String r = "java -cp $CLASSPATH:arthur/lib/jitac.jar:arthur/lib/jimi-sdk-1.0/JimiProClasses.zip:. org.roqe.jitac.Jitac";
      Process proc = Runtime.getRuntime().exec(r + " " + filearg);
      InputStream in = proc.getInputStream();
      InputStream err = proc.getErrorStream();
      proc.waitFor();
      String result = IoUtils.string(in);

      Runtime.getRuntime().exec("rm -rf " + filearg);
      return result;
    } catch(Exception e) {
      System.out.println("error with ascci conversion");
      e.printStackTrace();
      return "xxx";
    }
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
