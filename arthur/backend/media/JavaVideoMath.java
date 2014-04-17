package arthur.backend.media;

import arthur.backend.IoUtils;

/**
 * Contains a suite of static methods to perform math operations involving
 * colors.
 */
public class JavaVideoMath {

  public static ArthurVideo add(ArthurVideo one, ArthurVideo two, String outname) {
    String f1 = one.filename.replace(".mp4", "");
    String f2 = two.filename.replace(".mp4", "");
    //convert both mp4's to mpeg transport streams
    String rawConvertCommand = "ffmpeg -i %s -c copy -bsf:v h264_mp4toannexb -f mpegts %s.ts";
    String convertCommand = String.format(rawConvertCommand, one.filename, f1);
    IoUtils.execute(convertCommand);
    convertCommand = String.format(rawConvertCommand, two.filename, f2);
    IoUtils.execute(convertCommand);
    //concatenate and write back to mp4
    String rawConcatCommand = "ffmpeg -i \"concat:%s.ts|%s.ts\" -c copy -bsf:a aac_adtstoasc %s";
    String concatCommand = String.format(rawConcatCommand, f1, f2, outname);

    System.out.println("writing added video to " + outname);
    return new ArthurVideo(outname);
  }

  public static ArthurVideo add(ArthurVideo one, ArthurSound two, String outname) {

  }

  public static ArthurVideo add(ArthurVideo one, ArthurColor two, String outname) {

  }

  public static ArthurVideo add(ArthurVideo one, ArthurNumber two, String outname) {

  }

  public static ArthurVideo add(ArthurVideo one, ArthurString two, String outname) {

  }

  public static ArthurVideo add(ArthurVideo one, ArthurImage two, String outname) {
    
  }

  public static ArthurVideo minus(ArthurVideo one, ArthurVideo two, String outname) {
    return JavaVideoMath.add(two, one, outname);
  }

}
