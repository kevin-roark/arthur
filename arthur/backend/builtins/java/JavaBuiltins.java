package arthur.backend.builtins.java;

import arthur.backend.media.*;
import arthur.backend.whisperer.*;

import java.lang.reflect.Field;

public class JavaBuiltins {

  public static ArthurMedia get(ArthurString filename) {
    return new ArthurMedia();
  }

  public static ArthurImage image(ArthurString filename) {
    return new ArthurImage(filename);
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

  public static void _addMedia(ArthurMedia media, String name) {
    JsWhisperer.addMedia(media, name);
  }

  public static void _addMedia(ArthurMedia media, String name, ArthurFrame frame) {
    media.frame = frame;
    JsWhisperer.addMedia(media, name);
  }

  public static ArthurFrame frame(ArthurNumber x, ArthurNumber y, ArthurNumber w, ArthurNumber h) {
    return new ArthurFrame(x, y, w, h);
  }

  public static ArthurFrame frame(ArthurNumber x, ArthurNumber y) {
    return frame(x, y, new ArthurNumber(-1), new ArthurNumber(-1));
  }

  public static ArthurFrame frame(ArthurString style) {
    if (style.equals("fill")) {
      return frame(new ArthurNumber(0), new ArthurNumber(0), new ArthurNumber(-1), new ArthurNumber(-1));
    } else {
      return frame(new ArthurNumber(0), new ArthurNumber(0));
    }
  }

  public static final ArthurColor RED = new ArthurColor(255, 0, 0, 1.0);
  public static final ArthurColor WHITE = new ArthurColor(255, 255, 255, 1.0);
  public static final ArthurColor BLUE = new ArthurColor(0, 0, 255, 1.0);

}
