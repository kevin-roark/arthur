package arthur.backend.builtins.java;

import arthur.backend.media.*;
import arthur.backend.whisperer.*;

public class JavaBuiltins {

  public static ArthurMedia get(String filename) {
    return null;
  }

  public static ArthurMedia get(ArthurString filename) {
    return null;
  }

  public static ArthurNumber ms() {
    return null;
  }

  public static void add(ArthurMedia media) {
    JsWhisperer.addMedia(media);
  }

  public static final ArthurColor RED = new ArthurColor(255, 0, 0, 255);
  public static final ArthurColor WHITE = new ArthurColor(255, 255, 255, 255);

}
