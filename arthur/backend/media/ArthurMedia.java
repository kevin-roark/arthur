package arthur.backend.media;

/**
 * The base class for all of the media types like JavaArthurImage.
 *
 * They will extend this class and overwrite the methods, along with adding
 * their own when appropriate.
 */
public class ArthurMedia implements java.io.Serializable {

  public String type;

  public ArthurMedia() {
    this.type = "untyped";
  }

  public ArthurMedia add(ArthurMedia two) {
    return this;
  }

  public ArthurMedia minus(ArthurMedia two) {
    return this;
  }

  public ArthurMedia multiply(ArthurMedia two) {
    return this;
  }

  public ArthurMedia divide(ArthurMedia two) {
    return this;
  }

  public boolean arthurEquals(ArthurMedia two) {
    return false;
  }

  public boolean lessThan(ArthurMedia two) {
    return false;
  }

  public boolean greaterThan(ArthurMedia two) {
    return false;
  }

  public ArthurMedia castTo(String mediaType) {
    return this;
  }

  public void writeToFile(String filename) {

  }

}
