package arthur.backend.media;

import arthur.backend.IoUtils;

import java.io.File;

/**
 * Contains a suite of static methods to perform math operations involving
 * colors.
 */
public class JavaVideoMath {

  public static ArthurVideo add(ArthurVideo one, ArthurVideo two, String outname) {
    System.out.println(outname);

    String f1 = "mpg1-" + System.currentTimeMillis();
    String f2 = "mpg2-" + System.currentTimeMillis();
    String o = "mpg3-" + System.currentTimeMillis();

    String mp4tompg = "ffmpeg -i %s -qscale:v 1 %s.mpg";
    String command1 = String.format(mp4tompg, one.filename, f1);
    String command2 = String.format(mp4tompg, two.filename, f2);

    //String commandtemp = "echo hello";

    String concat = "cat %s.mpg %s.mpg > %s.mpg";
    String command3 = String.format(concat, f1, f2, o);

    String mpgtomp4 = "ffmpeg -i %s.mpg -qscale:v 2 %s";
    String command4 = String.format(mpgtomp4, o, outname);

    String command5 = "rm *.mpg";

    IoUtils.execute(command1);
    IoUtils.execute(command2);
    //IoUtils.execute(commandtemp);
    IoUtils.execute(command3);
    IoUtils.execute(command4);
    IoUtils.execute(command5);
    /*
    ffmpeg -i input1.avi -qscale:v 1 intermediate1.mpg
    ffmpeg -i input2.avi -qscale:v 1 intermediate2.mpg
    cat intermediate1.mpg intermediate2.mpg > intermediate_all.mpg
    ffmpeg -i intermediate_all.mpg -qscale:v 2 output.avi
    */

    return new ArthurVideo(outname);
  }

  public static ArthurVideo add(ArthurVideo one, ArthurColor two, String outname) {
    String command1 = "ffmpeg -i " + one.filename + " %d.jpg";
    
    long counter = 1;
    while (true) {
      String filename = counter + ".jpg";
      if (new File(filename).isFile() == false) {
        //System.out.println(filename + "? That's not a file");
        break;
      }
      ArthurImage image = new ArthurImage(filename);
      ArthurImage result = image.add(two);
      result.writeToFile("tinted-" + filename);
      counter++;
    }

    String tempVid = "Vid-temp-" + System.currentTimeMillis() + ".mp4";
    String tempSound = "Sound-temp-" + System.currentTimeMillis() + ".mp3";

    String command2 = "ffmpeg -r 30 -i tinted-%d.jpg " + tempVid;
    String command3 = "ffmpeg -i " + one.filename + " " + tempSound;
    String command4 = "ffmpeg -i " + tempVid + " -i " + tempSound + " -ab 192k " + outname;
    /*
    ffmpeg -r 30 -i mixedFrame%d.jpg outputMovie.mp4
    ffmpeg -i sample1.mp4 sample1sound.mp3
    ffmpeg -i outputMovie.mp4 -i sample1sound.mp3 -ab 192k movSound.mp4
    */

    IoUtils.execute(command1);
    IoUtils.execute(command2);
    IoUtils.execute(command3);
    IoUtils.execute(command4);

    for (int i = 1; i < 10; i++) {
      IoUtils.execute("rm " + i + "*.jpg");
      IoUtils.execute("rm tinted-" + i + "*.jpg");
    }

    //TODO: remove other temp files
    
    return new ArthurVideo(outname);
  }

  public static ArthurVideo add(ArthurVideo one, ArthurNumber two, String outname) {
    String command1 = "ffmpeg -i " + one.filename + " %d.jpg";
    
    long counter = 1;
    while (true) {
      String filename = counter + ".jpg";
      if (new File(filename).isFile() == false) {
        //System.out.println(filename + "? That's not a file");
        break;
      }
      ArthurImage image = new ArthurImage(filename);
      ArthurImage result = image.add(two);
      result.writeToFile("adjusted-" + filename);
      counter++;
    }

    String tempVid = "Vid-temp-" + System.currentTimeMillis() + ".mp4";
    String tempSound = "Sound-temp-" + System.currentTimeMillis() + ".mp3";

    String command2 = "ffmpeg -r 30 -i adjusted-%d.jpg " + tempVid;
    String command3 = "ffmpeg -i " + one.filename + " " + tempSound;
    String command4 = "ffmpeg -i " + tempVid + " -i " + tempSound + " -ab 192k " + outname;
    /*
    ffmpeg -r 30 -i mixedFrame%d.jpg outputMovie.mp4
    ffmpeg -i sample1.mp4 sample1sound.mp3
    ffmpeg -i outputMovie.mp4 -i sample1sound.mp3 -ab 192k movSound.mp4
    */

    IoUtils.execute(command1);
    IoUtils.execute(command2);
    IoUtils.execute(command3);
    IoUtils.execute(command4);

    for (int i = 1; i < 10; i++) {
      IoUtils.execute("rm " + i + "*.jpg");
      IoUtils.execute("rm adjusted-" + i + "*.jpg");
    }

    //TODO: remove other temp files
    
    return new ArthurVideo(outname);
  }

  /*
  public static ArthurVideo add(ArthurVideo one, ArthurColor two, String outname) {

  }

  public static ArthurVideo add(ArthurVideo one, ArthurNumber two, String outname) {

  }

  public static ArthurVideo add(ArthurVideo one, ArthurString two, String outname) {

  }

  public static ArthurVideo add(ArthurVideo one, ArthurImage two, String outname) {
    
  }
  */

  public static ArthurVideo minus(ArthurVideo one, ArthurVideo two, String outname) {
    return JavaVideoMath.add(two, one, outname);
  }

  public static ArthurVideo minus(ArthurVideo one, ArthurColor two, String outname) {
    ArthurColor complement = new ArthurColor(255.0 - two.r.val, 255.0 - two.g.val, 255.0 - two.b.val, two.a.val);
    return JavaVideoMath.add(one, complement, outname);
  }

  public static ArthurVideo minus(ArthurVideo one, ArthurNumber two, String outname) {
    String command1 = "ffmpeg -i " + one.filename + " %d.jpg";
    
    long counter = 1;
    while (true) {
      String filename = counter + ".jpg";
      if (new File(filename).isFile() == false) {
        //System.out.println(filename + "? That's not a file");
        break;
      }
      ArthurImage image = new ArthurImage(filename);
      ArthurImage result = image.minus(two);
      result.writeToFile("adjusted-" + filename);
      counter++;
    }

    String tempVid = "Vid-temp-" + System.currentTimeMillis() + ".mp4";
    String tempSound = "Sound-temp-" + System.currentTimeMillis() + ".mp3";

    String command2 = "ffmpeg -r 30 -i adjusted-%d.jpg " + tempVid;
    String command3 = "ffmpeg -i " + one.filename + " " + tempSound;
    String command4 = "ffmpeg -i " + tempVid + " -i " + tempSound + " -ab 192k " + outname;
    /*
    ffmpeg -r 30 -i mixedFrame%d.jpg outputMovie.mp4
    ffmpeg -i sample1.mp4 sample1sound.mp3
    ffmpeg -i outputMovie.mp4 -i sample1sound.mp3 -ab 192k movSound.mp4
    */

    IoUtils.execute(command1);
    IoUtils.execute(command2);
    IoUtils.execute(command3);
    IoUtils.execute(command4);

    for (int i = 1; i < 10; i++) {
      IoUtils.execute("rm " + i + "*.jpg");
      IoUtils.execute("rm adjusted-" + i + "*.jpg");
    }

    //TODO: remove other temp files
    
    return new ArthurVideo(outname);
  }

  public static ArthurVideo multiply(ArthurVideo one, ArthurVideo two, String outname) {
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

    //TODO: clean up temp files

    return new ArthurVideo(outname);
  }

  public static ArthurVideo divide(ArthurVideo one, ArthurNumber two, String outname) {
    String command1 = "ffmpeg -i " + one.filename + " %d.jpg";
    
    long counter = 1;
    while (true) {
      String filename = counter + ".jpg";
      if (new File(filename).isFile() == false) {
        //System.out.println(filename + "? That's not a file");
        break;
      }
      ArthurImage image = new ArthurImage(filename);
      ArthurImage result = image.divide(two);
      result.writeToFile("tiled-" + filename);
      counter++;
    }

    String tempVid = "Vid-temp-" + System.currentTimeMillis() + ".mp4";
    String tempSound = "Sound-temp-" + System.currentTimeMillis() + ".mp3";

    String command2 = "ffmpeg -r 30 -i tiled-%d.jpg " + tempVid;
    String command3 = "ffmpeg -i " + one.filename + " " + tempSound;
    String command4 = "ffmpeg -i " + tempVid + " -i " + tempSound + " -ab 192k " + outname;
    /*
    ffmpeg -r 30 -i mixedFrame%d.jpg outputMovie.mp4
    ffmpeg -i sample1.mp4 sample1sound.mp3
    ffmpeg -i outputMovie.mp4 -i sample1sound.mp3 -ab 192k movSound.mp4
    */

    IoUtils.execute(command1);
    IoUtils.execute(command2);
    IoUtils.execute(command3);
    IoUtils.execute(command4);

    for (int i = 1; i < 10; i++) {
      IoUtils.execute("rm " + i + "*.jpg");
      IoUtils.execute("rm tiled-" + i + "*.jpg");
    }

    //TODO: remove other temp files
    
    return new ArthurVideo(outname);
  }

}
