package arthur.backend.media;

import arthur.backend.IoUtils;

import java.io.File;

/**
 * Contains a suite of static methods to perform math operations involving
 * videos.
 */
public class JavaVideoMath {

  public static ArthurVideo add(ArthurVideo one, ArthurVideo two, String outname) {
    System.out.println(outname);

    String f1 = "ts1-" + System.currentTimeMillis();
    String f2 = "ts2-" + System.currentTimeMillis();

    String mp4tompeg = "ffmpeg -i %s -c copy -bsf:v h264_mp4toannexb -f mpegts %s.ts";
    String command1 = String.format(mp4tompeg, one.filename, f1);
    String command2 = String.format(mp4tompeg, two.filename, f2);
    IoUtils.execute(command1);
    IoUtils.execute(command2);

    String concat = "ffmpeg -i \"concat:%s.ts|%s.ts\" -c copy -bsf:a aac_adtstoasc %s";
    String command3 = String.format(concat, f1, f2, outname);
    IoUtils.execute(command3);
    
    IoUtils.execute("rm ts*");
    
    return new ArthurVideo(outname);
  }

  public static ArthurVideo add(ArthurVideo one, ArthurColor two, String outname) {
    return JavaVideoMath.editFrames(one, two, outname, "+ArthurColor");
  }

  public static ArthurVideo add(ArthurVideo one, ArthurNumber two, String outname) {
    return JavaVideoMath.editFrames(one, two, outname, "+ArthurNumber");
  }

  public static ArthurVideo add(ArthurVideo one, ArthurString two, String outname) {
    return JavaVideoMath.editFrames(one, two, outname, "+ArthurString");
  }

  public static ArthurVideo add(ArthurVideo one, ArthurImage two, String outname) {
    return JavaVideoMath.editFrames(one, two, outname, "+ArthurImage");
  }

  public static ArthurVideo minus(ArthurVideo one, ArthurVideo two, String outname) {
    return JavaVideoMath.add(two, one, outname);
  }

  public static ArthurVideo minus(ArthurVideo one, ArthurColor two, String outname) {
    return JavaVideoMath.editFrames(one, two, outname, "-ArthurColor");
  }

  public static ArthurVideo minus(ArthurVideo one, ArthurNumber two, String outname) {
    return JavaVideoMath.editFrames(one, two, outname, "-ArthurNumber");
  }

  public static ArthurVideo minus(ArthurVideo one, ArthurString two, String outname) {
    return JavaVideoMath.editFrames(one, two, outname, "-ArthurString");
  }

  public static ArthurVideo minus(ArthurVideo one, ArthurImage two, String outname) {
    return JavaVideoMath.editFrames(one, two, outname, "-ArthurImage");
  }

  public static ArthurVideo multiply(ArthurVideo one, ArthurVideo two, String outname) {
    //TODO
    return one;
  }

  public static ArthurVideo multiply(ArthurVideo one, ArthurNumber two, String outname) {
    double factor = 1 / two.val;

    String speedUpVideo = "ffmpeg -i %s -filter:v \"setpts=%f*PTS\" %s";
    String tempVid = "Vid-temp-" + System.currentTimeMillis() + ".mp4";
    String command1 = String.format(speedUpVideo, one.filename, factor, tempVid);

    String extractAudio = "ffmpeg -i %s -vn -ar 44100 -ac 2 -ab 192 -f mp3 %s";
    String tempSound = "Sound-temp-" + System.currentTimeMillis() + ".mp3";
    String command2 = String.format(extractAudio, tempVid, tempSound);

    //atempo can only take between 0.5 and 2.0; fix this later by doing it multiple times
    String speedUpAudio = "ffmpeg -i %s -filter:a \"atempo=%f\" %s";
    String tempSoundSpeedy = "Sound-temp-speedy-" + System.currentTimeMillis() + ".mp3";
    String command3 = String.format(speedUpAudio, tempSound, two.val, tempSoundSpeedy);

    String addbackAudio = "ffmpeg -i %s -i %s %s";
    String command4 = String.format(addbackAudio, tempSoundSpeedy, tempVid, outname);

    IoUtils.execute(command1);
    IoUtils.execute(command2);
    IoUtils.execute(command3);
    IoUtils.execute(command4);

    IoUtils.execute("rm Vid-temp-*");
    IoUtils.execute("rm Sound-temp-*");

    return new ArthurVideo(outname);
  }

  public static ArthurVideo divide(ArthurVideo one, ArthurVideo two, String outname) {
    //TODO
    return one;
  }

  public static ArthurVideo divide(ArthurVideo one, ArthurNumber two, String outname) {
    return JavaVideoMath.editFrames(one, two, outname, "/ArthurNumber");
  }

  public static ArthurVideo editFrames(ArthurVideo one, ArthurMedia two, String outname, String function) {
    String command1 = "ffmpeg -i " + one.filename + " %d.jpg";

    IoUtils.execute(command1);
    
    long counter = 1;
    while (true) {
      String filename = counter + ".jpg";
      if (new File(filename).isFile() == false) {
        break;
      }
      ArthurImage image = new ArthurImage(filename);
      ArthurImage result;
      if (function.equals("+ArthurNumber")) { 
        result = image.add((ArthurNumber) two); //brighten each frame
      }
      else if (function.equals("-ArthurNumber")) { 
        result = image.minus((ArthurNumber) two); //darken each frame
      }
      else if (function.equals("/ArthurNumber")) {
        result = image.divide((ArthurNumber) two); //tile each frame
      }
      else if (function.equals("+ArthurColor")) {
        result = image.add((ArthurColor) two); //tint each frame
      }
      else if (function.equals("-ArthurColor")) {
        result = image.minus((ArthurColor) two); //tint each frame with complement
      }
      else if (function.equals("+ArthurImage")) {
        result = image.add((ArthurImage) two); //put image on right of video
      }
      else if (function.equals("-ArthurImage")) {
        result = image.minus((ArthurImage) two); //put image on left of video
      }
      else if (function.equals("+ArthurString")) {
        result = image.add((ArthurString) two); //pin text on image
      }
      else if (function.equals("-ArthurString")) {
        result = image.minus((ArthurString) two); //tbd
      }
      else {
        System.out.println("Error - that's not an acceptable function");
        result = image;
      }

      result.writeToFile("adjusted-" + filename);
      counter++;
    }

    String tempVid = "Vid-temp-" + System.currentTimeMillis() + ".mp4";
    String tempSound = "Sound-temp-" + System.currentTimeMillis() + ".mp3";

    String command2 = "ffmpeg -r 30 -i adjusted-%d.jpg " + tempVid;
    String command3 = "ffmpeg -i " + one.filename + " " + tempSound;
    String command4 = "ffmpeg -i " + tempVid + " -i " + tempSound + " -ab 192k " + outname;

    IoUtils.execute(command2);
    IoUtils.execute(command3);
    IoUtils.execute(command4);

    for (int i = 1; i < 10; i++) {
      IoUtils.execute("rm " + i + "*.jpg");
      IoUtils.execute("rm adjusted-" + i + "*.jpg");
    }

    IoUtils.execute("rm Vid-temp-*");
    IoUtils.execute("rm Sound-temp-*");
    
    return new ArthurVideo(outname);
  }

}
