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
    return new ArthurVideo(filename.str);
  }

  public static ArthurSound sound(ArthurString filename) {
    return new ArthurSound(filename.str);
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

  public static void _addMedia(ArthurMedia media, String name, ArthurNumber delay) {
    media.delay = delay;
    JsWhisperer.addMedia(media, name);
  }

  public static void _addMedia(ArthurMedia media, String name, ArthurFrame frame, ArthurNumber delay) {
    media.delay = delay;
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
  public static final ArthurColor SARCOLINE = new ArthurColor(150, 223, 167, 1.0);
  public static final ArthurColor COQUELICOT = new ArthurColor(236, 75, 0, 1.0);
  public static final ArthurColor SMARAGDINE = new ArthurColor(8, 152, 113, 1.0);
  public static final ArthurColor ALMOND = new ArthurColor(239, 222, 205, 1.0);
  public static final ArthurColor ASPARAGUS = new ArthurColor(135, 169, 107, 1.0);
  public static final ArthurColor BURNT_SIENNA = new ArthurColor(234, 126, 93, 1.0);
  public static final ArthurColor CERULEAN = new ArthurColor(29, 172, 214, 1.0);
  public static final ArthurColor DANDELION = new ArthurColor(253, 219, 109, 1.0);
  public static final ArthurColor DENIM = new ArthurColor(43, 108, 196, 1.0);
  public static final ArthurColor ELECTRIC_LIME = new ArthurColor(206, 255, 29, 1.0);
  public static final ArthurColor FUZZY_WUZZY = new ArthurColor(204, 102, 102, 1.0);
  public static final ArthurColor GOLDENROD = new ArthurColor(252, 217, 117, 1.0);
  public static final ArthurColor JAZZBERRY_JAM = new ArthurColor(202, 55, 103, 1.0);
  public static final ArthurColor MAC_AND_CHEESE = new ArthurColor(255, 189, 136, 1.0);
  public static final ArthurColor MAHOGANY = new ArthurColor(205, 74, 76, 1.0);
  public static final ArthurColor MANGO_TANGO = new ArthurColor(255, 130, 67, 1.0);
  public static final ArthurColor MAUVELOUS = new ArthurColor(239, 152, 170, 1.0);
  public static final ArthurColor PURPLE_PIZZAZZ = new ArthurColor(254, 78, 218, 1.0);
  public static final ArthurColor RAZZMATAZZ = new ArthurColor(227, 37, 107, 1.0);
  public static final ArthurColor SALMON = new ArthurColor(255, 155, 170, 1.0);
  public static final ArthurColor SILVER = new ArthurColor(205, 197, 194, 1.0);
  public static final ArthurColor TICKLE_ME_PINK = new ArthurColor(252, 137, 172, 1.0);
  public static final ArthurColor WILD_BLUE_YONDER = new ArthurColor(162, 173, 208, 1.0);
  public static final ArthurColor WISTERIA = new ArthurColor(205, 164, 222, 1.0);
  public static final ArthurColor LASER_LEMON = new ArthurColor(254, 254, 34, 1.0);
  public static final ArthurColor EGGPLANT = new ArthurColor(110, 81, 96, 1.0);
  public static final ArthurColor CHARTREUSE = new ArthurColor(127, 255, 0, 1.0);

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
      colorMap.put("SARCOLINE", SARCOLINE);
      colorMap.put("COQUELICOT", COQUELICOT);
      colorMap.put("SMARAGDINE", SMARAGDINE);
      colorMap.put("ALMOND", ALMOND);
      colorMap.put("ASPARAGUS", ASPARAGUS);
      colorMap.put("BURNT_SIENNA", BURNT_SIENNA);
      colorMap.put("CERULEAN", CERULEAN);
      colorMap.put("DANDELION", DANDELION);
      colorMap.put("DENIM", DENIM);
      colorMap.put("ELECTRIC_LIME", ELECTRIC_LIME);
      colorMap.put("FUZZY_WUZZY", FUZZY_WUZZY);
      colorMap.put("GOLDENROD", GOLDENROD);
      colorMap.put("JAZZBERRY_JAM", JAZZBERRY_JAM);
      colorMap.put("MAC_AND_CHEESE", MAC_AND_CHEESE);
      colorMap.put("MAHOGANY", MAHOGANY);
      colorMap.put("MANGO_TANGO", MANGO_TANGO);
      colorMap.put("MAUVELOUS", MAUVELOUS);
      colorMap.put("PURPLE_PIZZAZZ", PURPLE_PIZZAZZ);
      colorMap.put("RAZZMATAZZ", RAZZMATAZZ);
      colorMap.put("SALMON", SALMON);
      colorMap.put("SILVER", SILVER);
      colorMap.put("TICKLE_ME_PINK", TICKLE_ME_PINK);
      colorMap.put("WILD_BLUE_YONDER", WILD_BLUE_YONDER);
      colorMap.put("WISTERIA", WISTERIA);
      colorMap.put("LASER_LEMON", LASER_LEMON);
      colorMap.put("EGGPLANT", EGGPLANT);
      colorMap.put("CHARTREUSE", CHARTREUSE);

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
      colors.add("SARCOLINE");
      colors.add("COQUELICOT");
      colors.add("SMARAGDINE");
      colors.add("ALMOND");
      colors.add("ASPARAGUS");
      colors.add("BURNT_SIENNA");
      colors.add("CERULEAN");
      colors.add("DANDELION");
      colors.add("DENIM");
      colors.add("ELECTRIC_LIME");
      colors.add("FUZZY_WUZZY");
      colors.add("GOLDENROD");
      colors.add("JAZZBERRY_JAM");
      colors.add("MAC_AND_CHEESE");
      colors.add("MAHOGANY");
      colors.add("MANGO_TANGO");
      colors.add("MAUVELOUS");
      colors.add("PURPLE_PIZZAZZ");
      colors.add("RAZZMATAZZ");
      colors.add("SALMON");
      colors.add("SILVER");
      colors.add("TICKLE_ME_PINK");
      colors.add("WILD_BLUE_YONDER");
      colors.add("WISTERIA");
      colors.add("LASER_LEMON");
      colors.add("EGGPLANT");
      colors.add("CHARTREUSE");


    }

    return colors;
  }

}
