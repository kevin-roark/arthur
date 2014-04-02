package arthur.backend.media;

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
    return null;
  }

  public static ArthurString add(ArthurString one, ArthurColor two) {
    return null;
  }

  public static ArthurString add(ArthurString one, ArthurVideo two) {
    return null;
  }

  public static ArthurString add(ArthurString one, ArthurSound two) {
    return null;
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
    return null;
  }

  public static ArthurString minus(ArthurString one, ArthurColor two) {
    return null;
  }

  public static ArthurString minus(ArthurString one, ArthurVideo two) {
    return null;
  }

  public static ArthurString minus(ArthurString one, ArthurSound two) {
    return null;
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
    while(diff >= shorter.length()) {
      shorter += shorter;
      diff = longer.length() - shorter.length();
    }
    if (diff != 0) {
      shorter += shorter.substring(diff);
    }

    // average the strings
    String product = new StringBuffer(shorter.length());
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

  public static ArthurString multiply(ArthurString one, ArthurImage two) {
    return null;
  }

  public static ArthurString multiply(ArthurString one, ArthurColor two) {
    return null;
  }

  public static ArthurString multiply(ArthurString one, ArthurVideo two) {
    return null;
  }

  public static ArthurString multiply(ArthurString one, ArthurSound two) {
    return null;
  }

  /* reverses the second string and then calls multiply */
  public static ArthurString divide(ArthurString one, ArthurString two) {
    String twoReverse = new StringBuilder(two).reverse().toString();
    return multiply(one, new ArthurString(twoReverse));
  }

  /* removes the last x chars from one */
  public static ArthurString divide(ArthurString one, ArthurNumber two) {
    int x = (int) Math.round(two.val);
    int lastPos = Math.max(0, one.str.length() - x);
    String cut = one.str.substring(lastPos);
    return new ArthurString(cut);
  }

  public static ArthurString divide(ArthurString one, ArthurImage two) {
    return null;
  }

  public static ArthurString divide(ArthurString one, ArthurColor two) {
    return null;
  }

  public static ArthurString divide(ArthurString one, ArthurVideo two) {
    return null;
  }

  public static ArthurString divide(ArthurString one, ArthurSound two) {
    return null;
  }

}
