package arthur.backend.builtins.java;

import arthur.backend.media.*;
import arthur.backend.whisperer.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.lang.reflect.Field;

import java.util.Random;

public class JavaBuiltins {

  public static final Random gen = new Random();

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
    double ctms = (double) System.currentTimeMillis();
    return new ArthurNumber(ctms);
  }

  public static ArthurNumber rand() {
    double r = Math.random();
    return new ArthurNumber(r);
  }

  // random color from the good stuff
  public static ArthurColor cooler() {
    int index = gen.nextInt(colors().size());
    String name = colors().get(index);
    ArthurColor color = colorMap().get(name);
    return color;
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
  public static final ArthurColor INDIAN_RED = new ArthurColor(176, 23, 31, 1.0);
  public static final ArthurColor CRIMSON = new ArthurColor(220, 20, 60, 1.0);
  public static final ArthurColor PINK = new ArthurColor(255, 192, 203, 1.0);
  public static final ArthurColor PALEVIOLETRED = new ArthurColor(219, 112, 147, 1.0);
  public static final ArthurColor LAVENDERBLUSH = new ArthurColor(255, 240, 245, 1.0);
  public static final ArthurColor HOTPINK = new ArthurColor(255, 105, 180, 1.0);
  public static final ArthurColor RASPBERRY = new ArthurColor(135, 38, 87, 1.0);
  public static final ArthurColor DEEPPINK = new ArthurColor(255, 20, 147, 1.0);
  public static final ArthurColor MAROON = new ArthurColor(255, 52, 179, 1.0);
  public static final ArthurColor ORCHID = new ArthurColor(218, 112, 214, 1.0);
  public static final ArthurColor THISTLE = new ArthurColor(216, 191, 216, 1.0);
  public static final ArthurColor PLUM = new ArthurColor(255, 187, 255, 1.0);
  public static final ArthurColor VIOLET = new ArthurColor(238, 130, 238, 1.0);
  public static final ArthurColor MAGENTA = new ArthurColor(255, 0, 255, 1.0);
  public static final ArthurColor PURPLE = new ArthurColor(128, 0, 128, 1.0);
  public static final ArthurColor DARKVIOLET = new ArthurColor(148, 0, 211, 1.0);
  public static final ArthurColor HENRY = new ArthurColor(147, 112, 219, 1.0);
  public static final ArthurColor SLATEBLUE = new ArthurColor(72, 61, 139, 1.0);
  public static final ArthurColor GHOSTWHITE = new ArthurColor(248, 248, 255, 1.0);
  public static final ArthurColor LAVENDER = new ArthurColor(230, 230, 250, 1.0);
  public static final ArthurColor NAVY = new ArthurColor(0, 0, 128, 1.0);
  public static final ArthurColor MIDNIGHTBLUE = new ArthurColor(25, 25, 112, 1.0);
  public static final ArthurColor COBALT = new ArthurColor(61, 89, 171, 1.0);
  public static final ArthurColor ROYALBLUE = new ArthurColor(64, 105, 225, 1.0);
  public static final ArthurColor CORNFLOWERBLUE = new ArthurColor(100, 149, 237, 1.0);
  public static final ArthurColor LIGHTSTEELBLUE = new ArthurColor(176, 196, 222, 1.0);
  public static final ArthurColor SLATEGRAY = new ArthurColor(112, 128, 144, 1.0);
  public static final ArthurColor SKYBLUE = new ArthurColor(135, 206, 235, 1.0);
  public static final ArthurColor PEACOCK = new ArthurColor(51, 161, 201, 1.0);
  public static final ArthurColor TURQUIOSE = new ArthurColor(0, 245, 255, 1.0);
  public static final ArthurColor AZURE = new ArthurColor(240, 255, 255, 1.0);
  public static final ArthurColor AQUAMARINE = new ArthurColor(69, 139, 116, 1.0);
  public static final ArthurColor MINT = new ArthurColor(189, 252, 201, 1.0);
  public static final ArthurColor HONEYDEW = new ArthurColor(240, 255, 240, 1.0);
  public static final ArthurColor LIMEGREEN = new ArthurColor(50, 205, 50, 1.0);
  public static final ArthurColor DARKGREEN = new ArthurColor(0, 100, 0, 1.0);
  public static final ArthurColor GREENYELLOW = new ArthurColor(173, 255, 47, 1.0);
  public static final ArthurColor IVORY = new ArthurColor(255, 255, 240, 1.0);
  public static final ArthurColor BEIGE = new ArthurColor(245, 245, 220, 1.0);
  public static final ArthurColor OLIVE = new ArthurColor(128, 128, 0, 1.0);
  public static final ArthurColor KHAKI = new ArthurColor(255, 246, 143, 1.0);
  public static final ArthurColor LEMONCHIFFON = new ArthurColor(255, 250, 205, 1.0);
  public static final ArthurColor BANANA = new ArthurColor(227, 207, 87, 1.0);
  public static final ArthurColor GOLD = new ArthurColor(255, 215, 0, 1.0);
  public static final ArthurColor DARKGOLDENROD = new ArthurColor(184, 134, 11, 1.0);
  public static final ArthurColor WHEAT = new ArthurColor(245, 222, 179, 1.0);
  public static final ArthurColor CARROT = new ArthurColor(237, 145, 33, 1.0);
  public static final ArthurColor CORAL = new ArthurColor(255, 127, 80, 1.0);
  public static final ArthurColor BRICK = new ArthurColor(156, 102, 31, 1.0);
  public static final ArthurColor BROWN = new ArthurColor(165, 42, 42, 1.0);
  public static final ArthurColor BEET = new ArthurColor(142, 56, 142, 1.0);
  public static final ArthurColor TOMATO = new ArthurColor(255, 99, 71, 1.0);
  public static final ArthurColor INDIGO = new ArthurColor(75, 0, 130, 1.0);

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
      colorMap.put("INDIAN_RED", INDIAN_RED);
      colorMap.put("CRIMSON",CRIMSON);
      colorMap.put("PINK", PINK);
      colorMap.put("PALEVIOLETRED",PALEVIOLETRED);
      colorMap.put("LAVENDERBLUSH", LAVENDERBLUSH);
      colorMap.put("HOTPINK", HOTPINK);
      colorMap.put("RASPBERRY", RASPBERRY);
      colorMap.put("DEEPPINK", DEEPPINK);
      colorMap.put("MAROON", MAROON);
      colorMap.put("ORCHID",ORCHID);
      colorMap.put("THISTLE",THISTLE);
      colorMap.put("PLUM", PLUM);
      colorMap.put("VIOLET",VIOLET);
      colorMap.put("MAGENTA",MAGENTA);
      colorMap.put("PURPLE",PURPLE);
      colorMap.put("DARKVIOLET",DARKVIOLET);
      colorMap.put("HENRY", HENRY);
      colorMap.put("SLATEBLUE", SLATEBLUE);
      colorMap.put("GHOSTWHITE", GHOSTWHITE);
      colorMap.put("LAVENDER", LAVENDER);
      colorMap.put("NAVY", NAVY);
      colorMap.put("MIDNIGHTBLUE", MIDNIGHTBLUE);
      colorMap.put("COBALT", COBALT);
      colorMap.put("ROYALBLUE", ROYALBLUE);
      colorMap.put("CORNFLOWERBLUE", CORNFLOWERBLUE);
      colorMap.put("LIGHTSTEELBLUE", LIGHTSTEELBLUE);
      colorMap.put("SLATEGRAY", SLATEGRAY);
      colorMap.put("SKYBLUE", SKYBLUE);
      colorMap.put("PEACOCK", PEACOCK);
      colorMap.put("TURQUIOSE", TURQUIOSE);
      colorMap.put("AZURE", AZURE);
      colorMap.put("AQUAMARINE", AQUAMARINE);
      colorMap.put("MINT", MINT);
      colorMap.put("HONEYDEW", HONEYDEW);
      colorMap.put("LIMEGREEN", LIMEGREEN);
      colorMap.put("DARKGREEN", DARKGREEN);
      colorMap.put("GREENYELLOW", GREENYELLOW);
      colorMap.put("IVORY", IVORY);
      colorMap.put("BEIGE", BEIGE);
      colorMap.put("OLIVE", OLIVE);
      colorMap.put("KHAKI", KHAKI);
      colorMap.put("LEMONCHIFFON", LEMONCHIFFON);
      colorMap.put("BANANA", BANANA);
      colorMap.put("GOLD", GOLD);
      colorMap.put("DARKGOLDENROD", DARKGOLDENROD);
      colorMap.put("WHEAT", WHEAT);
      colorMap.put("BRICK", BRICK);
      colorMap.put("CARROT", CARROT);
      colorMap.put("BROWN", BROWN);
      colorMap.put("BEET", BEET);
      colorMap.put("TOMATO", TOMATO);
      colorMap.put("INDIGO", INDIGO);
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
      colors.add("INDIAN_RED");
      colors.add("CRIMSON");
      colors.add("PINK");
      colors.add("PALEVIOLETRED");
      colors.add("LAVENDERBLUSH");
      colors.add("HOTPINK");
      colors.add("RASPBERRY");
      colors.add("DEEPPINK");
      colors.add("MAROON");
      colors.add("ORCHID");
      colors.add("THISTLE");
      colors.add("PLUM");
      colors.add("VIOLET");
      colors.add("MAGENTA");
      colors.add("PURPLE");
      colors.add("DARKVIOLET");
      colors.add("HENRY");
      colors.add("SLATEBLUE");
      colors.add("GHOSTWHITE");
      colors.add("LAVENDER");
      colors.add("NAVY");
      colors.add("MIDNIGHTBLUE");
      colors.add("COBALT");
      colors.add("ROYALBLUE");
      colors.add("CORNFLOWERBLUE");
      colors.add("LIGHTSTEELBLUE");
      colors.add("SLATEGRAY");
      colors.add("SKYBLUE");
      colors.add("PEACOCK");
      colors.add("TURQUIOSE");
      colors.add("AZURE");
      colors.add("AQUAMARINE");
      colors.add("MINT");
      colors.add("HONEYDEW");
      colors.add("LIMEGREEN");
      colors.add("DARKGREEN");
      colors.add("GREENYELLOW");
      colors.add("IVORY");
      colors.add("BEIGE");
      colors.add("OLIVE");
      colors.add("KHAKI");
      colors.add("LEMONCHIFFON");
      colors.add("BANANA");
      colors.add("GOLD");
      colors.add("DARKGOLDENROD");
      colors.add("WHEAT");
      colors.add("CARROT");
      colors.add("BRICK");
      colors.add("BROWN");
      colors.add("BEET");
      colors.add("TOMATO");
      colors.add("INDIGO");
    }

    return colors;
  }

}
