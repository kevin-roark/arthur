package arthur.backend.media;

/**
 * Contains a suite of static methods to perform math operations involving
 * numbers.
 */
public class JavaNumberMath {

  public static ArthurNumber add(ArthurNumber one, ArthurNumber two) {
    return new ArthurNumber(one.val + two.val);
  }

  public static ArthurNumber minus(ArthurNumber one, ArthurNumber two) {
    return new ArthurNumber(one.val - two.val);
  }

  public static ArthurNumber multiply(ArthurNumber one, ArthurNumber two) {
    return new ArthurNumber(one.val * two.val);
  }

  public static ArthurNumber divide(ArthurNumber one, ArthurNumber two) {
    return new ArthurNumber(one.val / two.val);
  }

}
