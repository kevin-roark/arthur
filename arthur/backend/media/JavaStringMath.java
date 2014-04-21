package arthur.backend.media;

import arthur.backend.builtins.java.JavaBuiltins;

/**
 * Contains a suite of static methods to perform math operations involving
 * colors.
 */
public class JavaStringMath {

  /* standard string concatenation */
  public static ArthurString add(ArthurString one, ArthurString two) {
    return new ArthurString(one.str + two.str);
  }

  /* treat number like string and concat them .. boring */
  public static ArthurString add(ArthurString one, ArthurNumber two) {
    String t = "" + two.val;
    return add(one, new ArthurString(t));
  }

  public static ArthurString add(ArthurString one, ArthurImage two) {
    String t = two.toAscii();
    String result = one.str + "\n" + t;
    return new ArthurString(result);
  }

  public static ArthurString add(ArthurString one, ArthurColor two) {
    ArthurString closest = two.closestString();
    return add(one, closest);
  }

  public static ArthurString add(ArthurString one, ArthurVideo two) {
    return null;
  }

  public static ArthurString add(ArthurString one, ArthurSound two) {
    return add(one, two.toArtString());
  }

  /* remove all instances of two from one */
  public static ArthurString minus(ArthurString one, ArthurString two) {
    return new ArthurString(one.str.replace(two.str, ""));
  }

  /* treats number as string and performs minus */
  public static ArthurString minus(ArthurString one, ArthurNumber two) {
    String t = "" + two.val;
    return minus(one, new ArthurString(t));
  }

  public static ArthurString minus(ArthurString one, ArthurImage two) {
    String t = two.toAscii();
    String result = t + "\n" + one.str;
    return new ArthurString(result);
  }

  public static ArthurString minus(ArthurString one, ArthurColor two) {
    ArthurString furthest = two.furthestString();
    return add(one, furthest);
  }

  public static ArthurString minus(ArthurString one, ArthurVideo two) {
    return null;
  }

  public static ArthurString minus(ArthurString one, ArthurSound two) {
    return minus(one, two.toArtString());
  }

  /* makes the strings the same length, then averages them character by
   * character */
  public static ArthurString multiply(ArthurString one, ArthurString two) {
    // find longer string
    String longer, shorter;
    if (one.str.length() > two.str.length()) {
      longer = one.str;
      shorter = two.str;
    } else {
      longer = two.str;
      shorter = one.str;
    }

    // make shorter same length as longer
    int diff = longer.length() - shorter.length();
    String orig = shorter;
    while(diff >= orig.length()) {
      shorter += orig;
      diff = longer.length() - shorter.length();
    }
    if (diff != 0) {
      shorter += shorter.substring(diff);
    }

    // average the strings
    StringBuffer product = new StringBuffer(shorter.length());
    for (int i = 0; i < shorter.length(); i++) {
      int avg = (longer.codePointAt(i) + shorter.codePointAt(i)) / 2;
      product.appendCodePoint(avg);
    }

    return new ArthurString(product.toString());
  }

  /* concatenates the first string with itself x times */
  public static ArthurString multiply(ArthurString one, ArthurNumber two) {
    int times = (int) Math.round(two.val);
    String product = "";
    for (int i = 0; i < times; i++) {
      product += one.str;
    }
    return new ArthurString(product);
  }

  /* reverses the second string and then calls multiply */
  public static ArthurString divide(ArthurString one, ArthurString two) {
    String twoReverse = new StringBuilder(two.str).reverse().toString();
    return multiply(one, new ArthurString(twoReverse));
  }

  /* removes the last x chars from one */
  public static ArthurString divide(ArthurString one, ArthurNumber two) {
    int x = (int) Math.round(two.val);
    int lastPos = Math.max(0, one.str.length() - x);
    String cut = one.str.substring(lastPos);
    return new ArthurString(cut);
  }

}
