package arthur.backend.builtins;

import arthur.backend.builtins.java.*;
import arthur.backend.media.*;

public class BuiltinsTester {

  public static void main(String[] args) {
      System.out.println("MS: " + JavaBuiltins.ms());

      System.out.println("RAND: " + JavaBuiltins.rand());

      System.out.println("COOLER: " + JavaBuiltins.cooler());

      System.out.println("IMAGE: " + JavaBuiltins.image(new ArthurString("ZERO.jpg")));

      System.out.println("SOUND: " + JavaBuiltins.sound(new ArthurString("ZERO.mp3")));

      System.out.println("VIDEO: " + JavaBuiltins.video(new ArthurString("ZERO.mp4")));

      System.out.println("FRAMEXY: " + JavaBuiltins.frame(new ArthurNumber(0), new ArthurNumber(0)));

      System.out.println("FRAMEXYWH: " + JavaBuiltins.frame(new ArthurNumber(0), new ArthurNumber(0), new ArthurNumber(200), new ArthurNumber(300)));
  }

}
