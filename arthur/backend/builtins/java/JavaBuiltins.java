package arthur.backend.builtins.java;

import arthur.backend.media.*;
import arthur.backend.whisperer.*;

public class JavaBuiltins {

  public static ArthurMedia get(ArthurString filename) {
    return new ArthurMedia();
  }

  public static ArthurImage image(ArthurString filename) {
    return new ArthurImage();
  }

  public static ArthurVideo video(ArthurString filename) {
    return new ArthurVideo();
  }

  public static ArthurSound sound(ArthurString filename) {
    return new ArthurSound();
  }

  public static ArthurNumber ms() {
    return new ArthurNumber();
  }

  public static void add(ArthurMedia media) {
    JsWhisperer.addMedia(media);
  }

  public static final ArthurColor RED = new ArthurColor(255, 0, 0, 255);
  public static final ArthurColor WHITE = new ArthurColor(255, 255, 255, 255);
  public static final ArthurColor BLUE = new ArthurColor(0, 0, 255, 255);

}
