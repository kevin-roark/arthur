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

/**
 * Contains a suite of static methods to perform math operations involving
 * colors.
 */
public class JavaSoundMath {

  public static ArthurSound add(ArthurSound one, ArthurSound two, String outname) {
  	//ConcatenateAudioAndVideo c = new ConcatenateAudioAndVideo();
    ConcatenateAudio c = new ConcatenateAudio();
  	c.concatenate(one.filename, two.filename, outname);
    ArthurSound.intermediateFiles.add(outname);
    System.out.println("writing added audio to " + outname);
    return new ArthurSound(outname);
  }

  public static ArthurSound minus(ArthurSound one, ArthurSound two, String outname) {
    return add(two, one, outname);
  }

  // merges the two audios
  public static ArthurSound multiply(ArthurSound one, ArthurSound two, String outname) {
    IMediaWriter writer = ToolFactory.makeWriter(outname);

    // create audio containers
    IContainer audioContainer1 = IContainer.make();
    IContainer audioContainer2 = IContainer.make();
    audioContainer1.open(one.filename, IContainer.Type.READ, null);
    audioContainer2.open(two.filename, IContainer.Type.READ, null);

    // read audio files and create streams
    IStreamCoder audioCoder1 = audioContainer1.getStream(0).getStreamCoder();
    IStreamCoder audioCoder2 = audioContainer2.getStream(0).getStreamCoder();
    audioCoder1.open(null, null);
    audioCoder2.open(null, null);
    IPacket audioPacket1 = IPacket.make();
    IPacket audioPacket2 = IPacket.make();

    // add stream to writer
    int idx1 = writer.addAudioStream(0, 0, audioCoder1.getChannels(), audioCoder1.getSampleRate());

    boolean read1 = true;
    boolean read2 = true;
    IAudioSamples samp1 = null;
    IAudioSamples samp2 = null;

    short[] mix = null;

    while(read1 || read2) {
      // read packet from audio 1
      if (read1) {
        if (audioContainer1.readNextPacket(audioPacket1) < 0) {
          read1 = false;
          samp1 = null;
        } else {
          // decode it
          samp1 = IAudioSamples.make(512, audioCoder1.getChannels(), IAudioSamples.Format.FMT_S16);
          audioCoder1.decodeAudio(samp1, audioPacket1, 0);

          mix = new short[(int) samp1.getNumSamples()];
        }
      }

      // read packet from audio 2
      if (read2) {
        if (audioContainer2.readNextPacket(audioPacket2) < 0) {
          read2 = false;
          samp2 = null;
        } else {
          // decode it
          samp2  = IAudioSamples.make(512, audioCoder2.getChannels(), IAudioSamples.Format.FMT_S16);
          audioCoder2.decodeAudio(samp2, audioPacket2, 0);

          if (mix == null) {
            mix = new short[(int) samp2.getNumSamples()];
          }
        }
      }

      if (samp1 != null && samp2 != null) {
        for (int i = 0; i < mix.length; i++) {
          int s1 = samp1.getSample(i, 0, IAudioSamples.Format.FMT_S16);
          int s2 = samp2.getSample(i, 0, IAudioSamples.Format.FMT_S16);
          mix[i] = mixSamples(1, s1, 1, s2);
        }
        writer.encodeAudio(0, mix);
      }

      mix = null;
    }

    audioCoder1.close();
    audioCoder2.close();
    audioContainer1.close();
    audioContainer2.close();
    writer.close();

    System.out.println("writing multiplied audio to " + outname);
    return new ArthurSound(outname);
  }

  public static ArthurSound divide(ArthurSound one, ArthurSound two) {
    return null;
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
