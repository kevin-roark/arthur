
package arthur.backend.media;

/**
 * Java implementation of arthur number!
 */
public class ArthurBoolean extends ArthurMedia implements java.io.Serializable {

  public static final String BOOLEAN = "bool";

  public Boolean val;

  public ArthurBoolean() {
    this(false);
  }

  public ArthurBoolean(Boolean val) {
    this.type = BOOLEAN;
    this.val = val;
  }

  public boolean arthurEquals(ArthurMedia two) {
    if (two.type.equals(BOOLEAN)) {
      ArthurBoolean b = (ArthurBoolean) two;
      return this.val == b.val;
    }

    return false;
  }

  public String toString() {
    return "" + this.val.toString();
  }

  public String jsLiteral() {
    return this.val.toString();
  }

}
