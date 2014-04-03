package arthur.backend.media;

/**
 * Java implementation of arthur sound!
 */
public class ArthurSound extends ArthurMedia {

  public static final String SOUND = "sound";

  public ArthurSound() {
    this.type = SOUND;
  }

  public ArthurSound add(ArthurMedia two) {
    return this;
  }

  public ArthurSound minus(ArthurMedia two) {
    return this;
  }

  public ArthurSound multiply(ArthurMedia two) {
    return this;
  }

  public ArthurSound divide(ArthurMedia two) {
    return this;
  }

}
