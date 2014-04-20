package arthur.backend.media;

import com.google.gson.*;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;

import arthur.backend.builtins.java.*;
import arthur.backend.IoUtils;

/**
 * Java implementation of arthur string!!
 */
public class ArthurString extends ArthurMedia {

  public static final String STRING = "string";

  public String str;
  public ArthurColor tint;
  public ArthurNumber size;
  public Boolean wrap;

  public ArthurString() {
    this("");
  }

  public ArthurString(String str) {
    this.type = STRING;
    this.str = str;
  }

  public ArthurNumber len() {
    return new ArthurNumber(this.str.length());
  }

  public ArthurString add(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.add(this, (ArthurString) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaStringMath.add(this, (ArthurNumber) two);
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      return JavaStringMath.add(this, (ArthurImage) two);
    } else if (two.type.equals(ArthurColor.COLOR)) {
      return JavaStringMath.add(this, (ArthurColor) two);
    } else {
      // coerce later
      return this;
    }
  }

  public ArthurString minus(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.minus(this, (ArthurString) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaStringMath.minus(this, (ArthurNumber) two);
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      return JavaStringMath.minus(this, (ArthurImage) two);
    } else if (two.type.equals(ArthurColor.COLOR)) {
      return JavaStringMath.minus(this, (ArthurColor) two);
    } else {
      // coerce later
      return this;
    }
  }

  public ArthurString multiply(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.multiply(this, (ArthurString) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaStringMath.multiply(this, (ArthurNumber) two);
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      ArthurImage img = (ArthurImage) two;
      return JavaStringMath.multiply(this, img.toArtString());
    } else if (two.type.equals(ArthurColor.COLOR)) {
      ArthurColor color = (ArthurColor) two;
      return JavaStringMath.multiply(this, color.toArtString());
    }

    return this;
  }

  public ArthurString divide(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.divide(this, (ArthurString) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaStringMath.divide(this, (ArthurNumber) two);
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      ArthurImage img = (ArthurImage) two;
      return JavaStringMath.divide(this, img.toArtString());
    } else if (two.type.equals(ArthurColor.COLOR)) {
      ArthurColor color = (ArthurColor) two;
      return JavaStringMath.divide(this, color.toArtString());
    }

    return this;
  }

  public ArthurMedia castTo(ArthurString mediaType) {
    return castTo(mediaType.str);
  }

  public ArthurMedia castTo(String mediaType) {
    if (mediaType.equals("color")) {
      return this.toColor();
    } else if (mediaType.equals("number")) {
      return this.toNumber();
    } else if (mediaType.equals("Image")) {
      return this.toImage();
    } else if (mediaType.equals("Sound")) {
      return this.toSound();
    }

    return this;
  }

  public ArthurColor toColor() {
    String name = this.str;
    for (int i = 0; i < JavaBuiltins.colors().size(); i++){
      String c = JavaBuiltins.colors().get(i);
      if(name.toUpperCase().contains(c)) {
        ArthurColor color = JavaBuiltins.colorMap().get(c);
        return color;
      }
    }
    return JavaBuiltins.BLACK;
  }

  public ArthurNumber toNumber() {
    double val = 0;
    for (int i = 0; i < this.str.length(); i++) {
      val += this.str.codePointAt(i);
    }
    return new ArthurNumber(val);
  }

  public ArthurImage toImage() {
    String filename = "string-" + (new Random()).nextInt() + ".jpg";

    Font font = new Font("Times New Roman", Font.PLAIN, 11);
    FontRenderContext frc = new FontRenderContext(null, true, true);

    Rectangle2D bounds = font.getStringBounds(this.str, frc);
    int w = (int) bounds.getWidth();
    int h = (int) bounds.getHeight();

    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

    Graphics2D g = image.createGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, w, h);
    g.setColor(Color.BLACK);
    g.setFont(font);

    g.drawString(this.str, (float) bounds.getX(), (float) -bounds.getY());
    g.dispose();

    return new ArthurImage(image, filename);
  }

  public ArthurSound toSound() {
    String tempWav = "text-" + System.currentTimeMillis() + ".wav";
    String command = "java -jar lib/freetts/lib/freetts.jar " +
        " -dumpAudio " + tempWav +
        " -text '" + this.str + "'";
    IoUtils.execute(command);
    ArthurSound s = ArthurSound.fromWav(tempWav);
    IoUtils.execute("rm -f " + tempWav);
    return s;
  }

  public boolean arthurEquals(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      ArthurString t = (ArthurString) two;
      return this.str.equals(t.str);
    }

    return false;
  }

  public String toString() {
    return this.str;
  }

  public String jsLiteral() {
    String js = "new ArthurString(";
    js += "'" + json() + "'";
    js += ")";
    return js;
  }

  public String json() {
    Gson gson = new Gson();
    String jsonStr = gson.toJson(this.str);

    String json = "{\"str\": " + jsonStr.replace("\\", "\\\\");

    if (this.tint != null) {
      json += ", \"color\": " + this.tint.json();
    }
    if (this.size != null) {
      json += ", \"size\": \"" + this.size.val + "\"";
    }
    if (this.wrap != null) {
      json +=", \"wrap\": \"" + this.wrap.toString() + "\"";
    }
    if (this.frame != null) {
      json += ", \"frame\": " + this.frame.json();
    }
    json += "}";
    return json;
  }

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
