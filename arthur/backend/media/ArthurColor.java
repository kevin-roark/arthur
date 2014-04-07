//package arthur.backend.media;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
      JavaColorMath.multiply(this, (ArthurNumber) two);
    }
    return this;
  }

  public ArthurColor divide(ArthurMedia two) {
    if (two.type.equals(COLOR)) {
      return JavaColorMath.divide(this, (ArthurColor) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      JavaColorMath.divide(this, (ArthurNumber) two);
    }
    return this;
  }

  public String toString() {
    return "<<" + this.r + ", " + this.g + ", " + this.b + ">>";
  }

  public String jsLiteral() {
    String js = "new ArthurColor(";
    js += "new ArthurNumber(" + this.r.val + "),";
    js += "new ArthurNumber(" + this.g.val + "),";
    js += "new ArthurNumber(" + this.b.val + "),";
    js += "new ArthurNumber(" + this.a.val + "))";
    return js;
  }

  /* saves state of current media to filename */
  public void writeToFile(String filename) {
    String json = "{\n'r': " + r.val + ",\n";
    json += "'g': " + g.val + ",\n";
    json += "'b': " + b.val + ",\n";
    json += "'a': " + a.val + "\n}";
    json = json.replace("'", "\"");

    try {
      PrintWriter out = new PrintWriter(filename);
      out.println(json);
      out.close();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}
