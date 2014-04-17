package arthur.backend.media;

import java.io.File;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IAudioSamples;

import arthur.backend.IoUtils;

/**
 * Contains a suite of static methods to perform math operations involving
 * colors.
 */
public class JavaSoundMath {

  public static final int LOW_HZ = 250; // bass center
  public static final int WIDTH_LOW_HZ = 460; // bass is 20 -> 480
  public static final int MID_HZ = 1400; // mid center
  public static final int WIDTH_MID_HZ = 2000; // mid is 400 -> 2400
  public static final int HIGH_HZ = 5000; // high center
  public static final int WIDTH_HIGH_HZ = 5000; // high is 2500 -> 7500
  public static final double MAX_EQ_GAIN = 20.0;

  public static String ffmpegStarter(String filename1, String filename2) {
    return "ffmpeg -i " + filename1 + " -i " + filename2 + " ";
  }

  public static String ffmpegEnder(String outname) {
    return " -c:a libmp3lame -q:a 4 " + outname;
  }

  public static String soxStarter(String filename1) {
    return soxStarter(filename1, "");
  }

  public static String soxStarter(String filename1, String filename2) {
    return "sox " + filename1 + " " + filename2  + " ";
  }

  public static ArthurSound add(ArthurSound one, ArthurSound two, String outname) {
    String command = soxStarter(one.filename, two.filename) + outname;
    IoUtils.execute(command);
    System.out.println("writing added audio to " + outname);
    return new ArthurSound(outname);
  }

  public static ArthurSound add(ArthurSound one, ArthurNumber two, String outname) {
    double semitoneCents = two.val * 10;
    String command = soxStarter(one.filename) + outname  +
        " pitch " + semitoneCents;
    IoUtils.execute(command);
    System.out.println("writing pitch-shifted sound to " + outname);
    return new ArthurSound(outname);
  }

  public static ArthurSound add(ArthurSound one, ArthurColor two, String outname) {
    double r = two.r.val;
    double g = two.g.val;
    double b = two.b.val;
    double mid = 127.5;

    double lowGain, midGain, highGain;
    if (r < mid) {
      lowGain = -1.0 * MAX_EQ_GAIN * (r / mid);
    } else {
      lowGain = MAX_EQ_GAIN * ((r - mid) / mid);
    }

    if (g < mid) {
      midGain = -1.0 * MAX_EQ_GAIN * (g / mid);
    } else {
      midGain = MAX_EQ_GAIN * ((g - mid) / mid);
    }

    if (b < mid) {
      highGain = -1.0 * MAX_EQ_GAIN * (b / mid);
    } else {
      highGain = MAX_EQ_GAIN * ((b - mid) / mid);
    }

    String command = soxStarter(one.filename) + outname +
      " bass " + lowGain +
      " equalizer " + MID_HZ + " " + WIDTH_MID_HZ + " " + midGain +
      " treble " + highGain;
    IoUtils.execute(command);
    System.out.println("writing equalized sound to " + outname);
    return new ArthurSound(outname);
  }

  public static ArthurSound add(ArthurSound one, ArthurImage two, String outname) {
    ArthurColor twoColor = two.getAverageColor();
    return add(one, twoColor, outname);
  }

  public static ArthurSound add(ArthurSound one, ArthurString two, String outname) {
    ArthurSound speech = two.toSound();
    return add(one, speech, outname);
  }

  public static ArthurSound minus(ArthurSound one, ArthurSound two, String outname) {
    return add(two, one, outname);
  }

  public static ArthurSound minus(ArthurSound one, ArthurNumber two, String outname) {
    ArthurNumber neg = new ArthurNumber(-1 * two.val);
    return add(one, neg, outname);
  }

  public static ArthurSound minus(ArthurSound one, ArthurColor two, String outname) {
    ArthurColor flipped = two.flip();
    return add(one, flipped, outname);
  }

  public static ArthurSound minus(ArthurSound one, ArthurImage two, String outname) {
    ArthurColor twoColor = two.getAverageColor();
    ArthurColor twoOpposite = twoColor.opposite();
    return add(one, twoOpposite, outname);
  }

  public static ArthurSound minus(ArthurSound one, ArthurString two, String outname) {
    ArthurSound speech = two.toSound();
    return minus(one, speech, outname);
  }

  // merges the two audios
  // here is a 1 LINE ffmpeg command to do the same thing
  // ffmpeg -i one.filename -i two.filename -filter_complex amerge -c:a libmp3lame -q:a 4 outname
  public static ArthurSound multiply(ArthurSound one, ArthurSound two, String outname) {
    String exec = ffmpegStarter(one.filename, two.filename);
    exec += " -filter_complex amerge " + ffmpegEnder(outname);

    IoUtils.execute(exec);
    System.out.println("writing multiplied audio to " + outname);
    return new ArthurSound(outname);
  }

  // speeds up audio by factor of num
  public static ArthurSound multiply(ArthurSound one, ArthurNumber two, String outname) {
    double rate = (double) two.val;
    return speedChange(one, rate, outname);
  }

  // does multiplication, but changes speed of two so that it equals
  // length of one
  public static ArthurSound divide(ArthurSound one, ArthurSound two, String outname) {
    IContainer audioContainer1 = IContainer.make();
    IContainer audioContainer2 = IContainer.make();
    audioContainer1.open(one.filename, IContainer.Type.READ, null);
    audioContainer2.open(two.filename, IContainer.Type.READ, null);

    double dur1 = (double) audioContainer1.getDuration();
    double dur2 = (double) audioContainer2.getDuration();

    double diff = 0;
    if (dur1 < dur2) {
      diff = dur2 / dur1;
    } else if (dur1 > dur2) {
      diff = dur1 / dur2;
    }

    ArthurSound speedyTwo = speedChange(two, diff, ArthurSound.nameGen());
    return multiply(one, two, ArthurSound.nameGen());
  }

  // changes volume by factor of num
  public static ArthurSound divide(ArthurSound one, ArthurNumber two, String outname) {
    double rate = (double) two.val;
    return volumeChange(one, rate, outname);
  }

  // changes sound by a given rate.
  // ffmpeg limited to between 0.5 -> 2.0, so have to combine filters
  // https://trac.ffmpeg.org/wiki/How%20to%20speed%20up%20/%20slow%20down%20a%20video
  public static ArthurSound speedChange(ArthurSound sound, double rate, String outname) {
    double r = rate;
    String filter = "";

    if (r < 1.0) {
      while (r < 0.5) {
        filter += "atempo=0.5,";
        r = r * 2.0;
      }
      filter += "atempo=" + r;
    } else {
      while (r > 2.0) {
        filter += "atempo=2.0,";
        r = r / 2.0;
      }
      filter += "atempo=" + r;
    }

    String command = "ffmpeg -i " + sound.filename + " -filter:a " + filter +
        ffmpegEnder(outname);
    IoUtils.execute(command);
    System.out.println("writing sped audio to " + outname);
    return new ArthurSound(outname);
  }

  public static ArthurSound volumeChange(ArthurSound sound, double rate, String outname) {
    String command = "ffmpeg -i " + sound.filename + " -af volume=" + rate +
        ffmpegEnder(outname);
    IoUtils.execute(command);
    System.out.println("writing volume changed audio to " + outname);
    return new ArthurSound(outname);
  }

  /**
   * Mixes together two audio samples to produce a new value.  Allows you to adjust relative volume of each sample in the
   * final mix.  Clips audio output to max available in a short.
   * @param V1 volume (1 is 100% and should be default) you want to mix first audio stream with.  Should default to 1
   * @param S1 the sample from the 1st stream of audio
   * @param V2 volume (1 is 100% and should be default) you want to mix second audio stream with.  Should default to 1
   * @param S2 the sample from the 2nd stream of audio
   * @return the mixed sample
   */
  private static short mixSamples(float V1, int S1, float V2, int S2) {
    final int n = (int) ((V1*S1) + (V2*S2));
    return (short) (n > Short.MAX_VALUE ? Short.MAX_VALUE : (n < Short.MIN_VALUE ? Short.MIN_VALUE : n));
  }

}
