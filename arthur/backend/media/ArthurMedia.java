//package arthur.backend.media;

/**
 * The base class for all of the media types like JavaArthurImage.
 *
 * They will extend this class and overwrite the methods, along with adding
 * their own when appropriate.
 */
public class ArthurMedia {

  public String type;

  public ArthurMedia() {
    this.type = "untyped";
  }

  public ArthurMedia add(ArthurMedia two) {
    return null;
  }

  public ArthurMedia minus(ArthurMedia two) {
    return null;
  }
  
  public ArthurMedia multiply(ArthurMedia two) {
    return null;
  }

  public ArthurMedia divide(ArthurMedia two) {
    return null;
  }

  /*

  public boolean arthurEquals(ArthurMedia two) {
    return false;
  }

  public ArthurMedia castTo(String mediaType) {
    return null;
  }

  */
}
