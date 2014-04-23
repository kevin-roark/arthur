package arthur.backend.media;

import arthur.backend.IoUtils;

import java.io.File;
import java.lang.Runtime;

/**
 * Contains a suite of static methods to perform math operations involving
 * videos.
 */
public class JavaVideoMath {

  public static String ffmpegStarter(String filename1, String filename2) {
    return "ffmpeg -i " + filename1 + " -i " + filename2 + " ";
  }

  public static String ffmpegEnder(String outname) {
    return outname;
  }

  public static String scriptPath() {
    return "./arthur/backend/media/";
  }

  public static ArthurVideo add(ArthurVideo one, ArthurVideo two, String outname) {
    IoUtils.execute(scriptPath() + "vidcat.sh " + one.filename + " " + two.filename + " " + outname);
    return new ArthurVideo(outname);
  }

  public static ArthurVideo add(ArthurVideo one, ArthurSound two, String outname) {
    ArthurSound mixedSounds = JavaSoundMath.divide(one.toSound(), two, ArthurSound.nameGen());
    String soundFile = mixedSounds.filename;
    String soundFileWav = mixedSounds.filename.replace(".mp3", ".wav");
    String mp3towav = "ffmpeg -i %s %s";
    String command1 = String.format(mp3towav, soundFile, soundFileWav);
    String addAudio = "ffmpeg -i %s -i %s -c:v copy -c:a aac -strict experimental -map 0:v:0 -map 1:a:0 %s";
    String command2 = String.format(addAudio, one.filename, soundFileWav, outname);
    IoUtils.execute(command1);
    IoUtils.execute(command2);
    IoUtils.execute("rm " + soundFileWav);
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

  // replaces sound in video with TWO
  public static ArthurVideo minus(ArthurVideo one, ArthurSound two, String outname) {
    String soundFile = two.filename;
    String soundFileWav = two.filename.replace(".mp3", ".wav");
    String mp3towav = "ffmpeg -i %s %s";
    String command1 = String.format(mp3towav, soundFile, soundFileWav);
    String addAudio = "ffmpeg -i %s -i %s -c:v copy -c:a aac -strict experimental -map 0:v:0 -map 1:a:0 %s";
    String command2 = String.format(addAudio, one.filename, soundFileWav, outname);
    IoUtils.execute(command1);
    IoUtils.execute(command2);
    IoUtils.execute("rm " + soundFileWav);
    return new ArthurVideo(outname);
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
    //we need to conserve the audio from the second vid, i haven't been able to do that!

    /*
    String extractAudio = "ffmpeg -i %s -vn -ar 44100 -ac 2 -ab 192 -f mp3 %s";
    String tempSound = "Sound-temp-" + System.currentTimeMillis() + ".mp3";
    String command1 = String.format(extractAudio, two.filename, tempSound);
*/
    //this actually merges the vids though
    IoUtils.execute(scriptPath() + "vidoverlay.sh " + one.filename + " " + two.filename + " " + outname);
    /*String addbackAudio = "ffmpeg -i %s -i %s %s";
    String command3 = String.format(addbackAudio, tempSound, outname, outname);*/

    return new ArthurVideo(outname);
  }

  public static ArthurVideo multiply(ArthurVideo one, ArthurNumber two, String outname) {
    double factor = 1.0 / Math.abs(two.val);

    String speedUpVideo = "ffmpeg -i %s -filter:v setpts=%f*PTS -strict -2 %s";
    String tempVid = "Vid-temp-" + System.currentTimeMillis() + ".mp4";
    String command1 = String.format(speedUpVideo, one.filename, factor, tempVid);

    String extractAudio = "ffmpeg -i %s -vn -ar 44100 -ab 192 -f mp3 %s";
    String tempSound = "Sound-temp-" + System.currentTimeMillis() + ".mp3";
    String command2 = String.format(extractAudio, tempVid, tempSound);

    IoUtils.execute(command1);
    IoUtils.execute(command2);

    String speedyName = "Sound-temp-speedy-" + System.currentTimeMillis() + ".mp3";
    ArthurSound spedUpAudio = JavaSoundMath.speedChange(new ArthurSound(tempSound), Math.abs(two.val), speedyName);

    String addbackAudio = "ffmpeg -i %s -i %s -strict -2 %s";
    String command3 = String.format(addbackAudio, spedUpAudio.filename, tempVid, outname);

    IoUtils.execute(command3);

    IoUtils.execute("rm " + tempSound);
    IoUtils.execute("rm " + tempVid);
    IoUtils.execute("rm " + speedyName);

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
        //System.out.println("heeey");
        result = image.divide((ArthurImage) two); //overlay image on video
      }
      else if (function.equals("-ArthurImage")) {
        result = image.multiply((ArthurImage) two); //overlay image on video without resizing
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
    String command4 = "ffmpeg -i " + tempVid + " -i " + tempSound + " -strict -2 -ab 192k " + outname;

    IoUtils.execute(command2);
    IoUtils.execute(command3);
    IoUtils.execute(command4);

    counter = 1;
    while (true) {
      String filename = counter + ".jpg";
      if (new File(filename).isFile() == false) {
        break;
      }
      IoUtils.execute("rm " + filename);
      IoUtils.execute("rm adjusted-" + filename);
      counter++;
    }

    /*

    for (int i = 1; i < 10; i++) {
      IoUtils.execute("/bin/bash -c 'rm " + i + "*.jpg'");
      IoUtils.execute("/bin/bash -c 'rm adjusted-" + i + "*.jpg'");
    }

    IoUtils.execute("/bin/bash -c 'rm Vid-temp-*'");
    IoUtils.execute("/bin/bash -c 'rm Sound-temp-*'");

    */
    IoUtils.execute("rm " + tempVid);
    IoUtils.execute("rm " + tempSound);

    return new ArthurVideo(outname);
  }

}
