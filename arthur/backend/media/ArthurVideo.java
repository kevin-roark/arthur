package arthur.backend.media;

/**
 * Java implementation of arthur video!
 */
public class ArthurVideo extends ArthurMedia {

  public static final String VIDEO = "video";

  public ArthurVideo() {
    this.type = VIDEO;
  }

  public ArthurVideo add(ArthurMedia two) {
    return this;
  }

  public ArthurVideo minus(ArthurMedia two) {
    return this;
  }

  public ArthurVideo multiply(ArthurMedia two) {
    return this;
  }

  public ArthurVideo divide(ArthurMedia two) {
    return this;
  }

}
