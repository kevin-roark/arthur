package arthur.backend.media;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IContainer;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;

import arthur.backend.IoUtils;

/**
 * Java implementation of arthur sound!
 */
public class ArthurSound extends ArthurMedia implements java.io.Serializable {


  public static final String SOUND = "sound";
  public static final String ZERO = "ZERO.mp3";

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
    duration = new ArthurNumber(new Double(c.getDuration()));
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

  public static ArthurSound fromWav(String wavefile) {
    String outname = nameGen();
    String command = "ffmpeg -i " + wavefile + " -ar 44100 -ac 2 -f mp3 " + outname;
    IoUtils.execute(command);
    return new ArthurSound(outname);
  }

  public ArthurSound add(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(SOUND)) {
      return JavaSoundMath.add(this, (ArthurSound) two, outname);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaSoundMath.add(this, (ArthurNumber) two, outname);
    } else if (two.type.equals(ArthurColor.COLOR)) {
      return JavaSoundMath.add(this, (ArthurColor) two, outname);
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      return JavaSoundMath.add(this, (ArthurImage) two, outname);
    } else if (two.type.equals(ArthurString.STRING)) {
      return JavaSoundMath.add(this, (ArthurString) two, outname);
    } else if (two.type.equals(ArthurVideo.VIDEO)) {
      return JavaSoundMath.add(this, (ArthurVideo) two, outname);
    }

    return this;
  }

  public ArthurSound minus(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(SOUND)) {
      return JavaSoundMath.minus(this, (ArthurSound) two, outname);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaSoundMath.minus(this, (ArthurNumber) two, outname);
    } else if (two.type.equals(ArthurColor.COLOR)) {
      return JavaSoundMath.minus(this, (ArthurColor) two, outname);
    } else if (two.type.equals(ArthurImage.IMAGE)) {
      return JavaSoundMath.minus(this, (ArthurImage) two, outname);
    } else if (two.type.equals(ArthurString.STRING)) {
      return JavaSoundMath.minus(this, (ArthurString) two, outname);
    } else if (two.type.equals(ArthurVideo.VIDEO)) {
      return JavaSoundMath.minus(this, (ArthurVideo) two, outname);
    }

    return this;
  }

  public ArthurSound multiply(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(SOUND)) {
      return JavaSoundMath.multiply(this, (ArthurSound) two, outname);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaSoundMath.multiply(this, (ArthurNumber) two, outname);
    } else {
      ArthurSound s = (ArthurSound) two.castTo("Sound");
      return JavaSoundMath.multiply(this, s, outname);
    }
  }

  public ArthurSound divide(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(SOUND)) {
      return JavaSoundMath.divide(this, (ArthurSound) two, outname);
    } else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaSoundMath.divide(this, (ArthurNumber) two, outname);
    } else {
      ArthurSound s = (ArthurSound) two.castTo("Sound");
      return JavaSoundMath.divide(this, s, outname);
    }
  }

  public static String nameGen() {
    String name = "sound-" + System.currentTimeMillis() + ".mp3";
    if (ArthurSound.intermediateFiles == null) {
      ArthurSound.intermediateFiles = new ArrayList<String>();
    }
    ArthurSound.intermediateFiles.add(name);
    return name;
  }

  public ArthurMedia castTo(ArthurString mediaType) {
    return castTo(mediaType.str);
  }

  public ArthurMedia castTo(String mediaType) {
    if (mediaType.equals("string")) {
      return this.toArtString();
    } else if (mediaType.equals("num")) {
      return this.toNumber();
    } else if (mediaType.equals("color")) {
      return this.toColor();
    } else if (mediaType.equals("Image")) {
      return this.toImage();
    } else if (mediaType.equals("Video")) {
      return this.toVideo();
    }

    return this;
  }

  // right now its just a gray based on average frequency
  public ArthurColor toColor() {
    double freq = JavaSoundMath.getFrequency(this);
    double ratio = 255 * freq / (JavaColorMath.maxFreq - JavaColorMath.minFreq);
    return new ArthurColor(ratio, ratio, ratio, 1.0);
  }

  public ArthurString toArtString() {
    char curChar = 'x';
    int count = 0;
    StringBuilder builder = new StringBuilder();

    try {
      FileReader inputStream = new FileReader(this.filename);

      while (curChar != (char) -1 && count++ <= 100) {
        curChar = (char) inputStream.read();
        builder.append(curChar);
      }
    } catch(IOException e) {
      e.printStackTrace();
    }

    return new ArthurString(builder.toString());
  }

  public ArthurNumber toNumber() {
    IContainer container = IContainer.make();
    int result = container.open(this.filename, IContainer.Type.READ, null);
    double dur = (double) container.getDuration() / 1000000;
    return new ArthurNumber(dur);
  }

  public ArthurImage toImage() {
    ArthurImage zero = new ArthurImage(ArthurImage.ZERO);
    return zero.add(this);
  }

  public ArthurVideo toVideo() {
    ArthurVideo zero = new ArthurVideo(ArthurVideo.ZERO);
    return zero.add(this);
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
    if (this.delay != null) {
      js += ", \"delay\": " + this.delay.val;
    }
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
