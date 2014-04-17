package arthur.backend.media;

import arthur.backend.IoUtils;
import java.util.ArrayList;

/**
 * Java implementation of arthur Video!
 */
public class ArthurVideo extends ArthurMedia {

  public static final String VIDEO = "Video";
  public String filename;
  public static ArrayList<String> intermediateFiles;

  public ArthurVideo() {
    this.type = VIDEO;
    filename = null;
    intermediateFiles = null;
  }

  public ArthurVideo(String fn) {
    this.type = VIDEO;
    filename = fn;
    if (intermediateFiles == null)
      intermediateFiles = new ArrayList<String>();
  }

  public ArthurVideo add(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(VIDEO)) {
      return JavaVideoMath.add(this, (ArthurVideo) two, outname);
    } 
    return this;
  }

  public ArthurVideo minus(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(VIDEO)) {
      return JavaVideoMath.minus(this, (ArthurVideo) two, outname);
    } 
    return this;
  }

  public ArthurVideo multiply(ArthurMedia two) {
    String outname = nameGen();
    /*
    if (two.type.equals(VIDEO)) {
      return JavaVideoMath.multiply(this, (ArthurVideo) two, outname);
    } 
    */
    return this;
  }

  public ArthurVideo divide(ArthurMedia two) {
    String outname = nameGen();
    /*
    if (two.type.equals(VIDEO)) {
      return JavaVideoMath.divide(this, (ArthurVideo) two, outname);
    } 
    */
    return this;
  }

  public ArthurSound toSound() {
    String name = ArthurSound.nameGen();
    String rawExtractCommand = "ffmpeg -i %s -vn -ar 44100 -ac 2 -ab 192 -f mp3 %s";
    String extractCommand = String.format(rawExtractCommand, this.filename, name);
    IoUtils.execute();
    return new ArthurSound(name);
  }

  public String jsLiteral() {
    return "new ArthurVideo()";
  }

  public static String nameGen() {
    String name = "Video-" + System.currentTimeMillis() + ".mp4";
    ArthurVideo.intermediateFiles.add(name);
    return name;
  }

  public void writeToFile(String fname) {
    IoUtils.move(this.filename, fname); // move file to correct name
    this.filename = fname.substring(fname.indexOf('/') + 1); // remove 'buster'
  }

  /*
  public void writeToFile(String fname) {
    writer = ToolFactory.makeWriter(fname, reader);
  }
  */

}
