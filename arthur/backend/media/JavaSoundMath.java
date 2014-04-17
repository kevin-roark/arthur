package arthur.backend.media;

import java.io.File;
// import com.xuggle.mediatools.demos.ConcatenateAudioAndVideo;

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

import arthur.backend.media.lib.ConcatenateAudio;
import arthur.backend.IoUtils;

/**
 * Contains a suite of static methods to perform math operations involving
 * colors.
 */
public class JavaSoundMath {

  public static String ffmpegStarter(String filename1, String filename2) {
    return "ffmpeg -i " + filename1 + " -i " + filename2 + " ";
  }

  public static ArthurSound add(ArthurSound one, ArthurSound two, String outname) {
  	//ConcatenateAudioAndVideo c = new ConcatenateAudioAndVideo();
    ConcatenateAudio c = new ConcatenateAudio();
  	c.concatenate(one.filename, two.filename, outname);
    System.out.println("writing added audio to " + outname);
    return new ArthurSound(outname);
  }

  public static ArthurSound minus(ArthurSound one, ArthurSound two, String outname) {
    return add(two, one, outname);
  }

  // merges the two audios
  // here is a 1 LINE ffmpeg command to do the same thing
  // ffmpeg -i one.filename -i two.filename -filter_complex amerge -c:a libmp3lame -q:a 4 outname
  public static ArthurSound multiply(ArthurSound one, ArthurSound two, String outname) {
    String exec = ffmpegStarter(one.filename, two.filename);
    exec += " -filter_complex amerge -c:a libmp3lame -q:a 4 " + outname;

    IoUtils.execute(exec);
    System.out.println("writing multiplied audio to " + outname);
    return new ArthurSound(outname);
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

    ArthurSound speedyTwo = speedChange(two, diff);
    return multiply(one, two, ArthurSound.nameGen());
  }

  // changes sound by a given rate.
  // ffmpeg limited to between 0.5 -> 2.0, so have to combine filters
  public static ArthurSound speedChange(ArthurSound sound, double rate) {
    String outname = ArthurSound.nameGen();
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
        " -vn -c:a libmp3lame -q:a 4 " + outname;
    IoUtils.execute(command);
    System.out.println("writing sped audio to " + outname);
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
