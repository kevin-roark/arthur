package arthur.backend.media;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IContainer;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import arthur.backend.IoUtils;

/**
 * Java implementation of arthur sound!
 */
public class ArthurSound extends ArthurMedia implements java.io.Serializable {

  public static final String SOUND = "sound";
  public String filename;
  public transient IMediaReader clip;
  public ArthurNumber duration;
  public static ArrayList<String> intermediateFiles;

  public ArthurSound(IMediaReader clop) {
    this.type = SOUND;
    filename = clop.getUrl();
    if (intermediateFiles == null)
      intermediateFiles = new ArrayList<String>();
    clip = clop;
    // get duration
    clip.open();
    final IContainer c = clip.getContainer();
    duration = new ArthurNumber(new Double(c.getDuration()));     // this fucked up
    c.close();
  }

  public ArthurSound(ArthurString fn) {
    this(fn.str);
  }

  public ArthurSound(String fn) {
    this.type = SOUND;
    filename = fn;
    if (intermediateFiles == null)
      intermediateFiles = new ArrayList<String>();
    clip = ToolFactory.makeReader(fn);
    // get duration
    clip.open();
    final IContainer c = clip.getContainer();
    duration = new ArthurNumber(new Double(c.getDuration()));
    c.close();
  }


  public ArthurSound add(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(SOUND)) {
      return JavaSoundMath.add(this, (ArthurSound) two, outname);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaSoundMath.add(this, (ArthurNumber) two, outname);
    }

    return this;
  }

  public ArthurSound minus(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(SOUND)) {
      return JavaSoundMath.minus(this, (ArthurSound) two, outname);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaSoundMath.minus(this, (ArthurNumber) two, outname);
    }
    return this;
  }

  public ArthurSound multiply(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(SOUND)) {
      return JavaSoundMath.multiply(this, (ArthurSound) two, outname);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaSoundMath.multiply(this, (ArthurNumber) two, outname);
    }
    return this;
  }

  public ArthurSound divide(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(SOUND)) {
      return JavaSoundMath.divide(this, (ArthurSound) two, outname);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaSoundMath.divide(this, (ArthurNumber) two, outname);
    }
    return this;
  }

  public static String nameGen() {
    String name = "sound-" + System.currentTimeMillis() + ".mp3";
    ArthurSound.intermediateFiles.add(name);
    return name;
  }

  public String toString() {
    return "sound file " + filename + ", duration " + duration + " ms";
  }

  public String jsLiteral() {
    return "new ArthurSound(" + json() + ")";
  }

  public String json() {
    String js = "{";
    js += "\"filename\": \"" + this.filename + "\"";
    js += "}";
    return js;
  }

  public void writeToFile(String fname) {
    IoUtils.move(this.filename, fname); // move file to correct name
    this.filename = fname.substring(fname.indexOf('/') + 1); // remove 'buster'
  }

  // TEMP: REMOVE AND DO IN JS LATER
  public void play() {
    clip.addListener(ToolFactory.makeViewer(IMediaViewer.Mode.AUDIO_ONLY));
    while (clip.readPacket() == null)
      ;
  }

}
