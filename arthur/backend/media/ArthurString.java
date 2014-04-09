package arthur.backend.media;

import java.io.*;

/**
 * Java implementation of arthur string!!
 */
public class ArthurString extends ArthurMedia {

  public static final String STRING = "string";

  public String str;
  public ArthurColor color;
  public ArthurNumber size;

  public ArthurString() {
    this("");
  }

  public ArthurString(String str) {
    this.type = STRING;
    this.str = str;
  }

  public ArthurString add(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.add(this, (ArthurString) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaStringMath.add(this, (ArthurNumber) two);
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
    } else {
      // coerce later
      return this;
    }
  }

  public ArthurString divide(ArthurMedia two) {
    if (two.type.equals(STRING)) {
      return JavaStringMath.divide(this, (ArthurString) two);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaStringMath.divide(this, (ArthurNumber) two);
    } else {
      // coerce later
      return this;
    }
  }

  public boolean arthurEquals(ArthurMedia two) {
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
    String json = "{'str': '" + this.str + "'";
    if (this.color != null) {
      json += ", 'color': " + this.color.json();
    }
    if (this.size != null) {
      json += ", 'size': '" + this.size.val + "'";
    }
    if (this.frame != null) {
      json += ", 'frame': " + this.frame.json();
    }
    json += "}";
    json = json.replace("'", "\"");
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
