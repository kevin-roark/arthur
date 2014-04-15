package arthur.backend.builtins.java;

import arthur.backend.media.*;
import arthur.backend.whisperer.*;

import java.util.HashMap;
import java.util.ArrayList;
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
  public static final ArthurColor BLACK = new ArthurColor(0, 0, 0, 1.0);
  public static final ArthurColor BLUE = new ArthurColor(0, 0, 255, 1.0);
  public static final ArthurColor GREEN = new ArthurColor(0, 255, 0, 1.0);
  public static final ArthurColor ORANGE = new ArthurColor(255, 128, 0, 1.0);
  public static final ArthurColor YELLOW = new ArthurColor(255, 255, 0, 1.0);
  public static final ArthurColor PERRYWINKLE = new ArthurColor(204, 204, 255, 1.0);
  public static final ArthurColor ARTHURS_SKIN = new ArthurColor(255, 195, 34, 1.0);

  private static HashMap<String, ArthurColor> colorMap;

  public static HashMap<String, ArthurColor> colorMap() {
    if (colorMap == null) {
      colorMap = new HashMap<String, ArthurColor>();
      colorMap.put("RED", RED);
      colorMap.put("WHITE", WHITE);
      colorMap.put("BLACK", BLACK);
      colorMap.put("BLUE", BLUE);
      colorMap.put("GREEN", GREEN);
      colorMap.put("ORANGE", ORANGE);
      colorMap.put("YELLOW", YELLOW);
      colorMap.put("PERRYWINKLE", PERRYWINKLE);
      colorMap.put("ARTHURS_SKIN", ARTHURS_SKIN);
    }

    return colorMap;
  }

  private static ArrayList<String> colors;

  public static ArrayList<String> colors() {
    if (colors == null) {
      colors = new ArrayList<String>();
      colors.add("RED");
      colors.add("WHITE");
      colors.add("BLACK");
      colors.add("BLUE");
      colors.add("GREEN");
      colors.add("ORANGE");
      colors.add("YELLOW");
      colors.add("PERRYWINKLE");
      colors.add("ARTHURS_SKIN");
    }

    return colors;
  }

}
