package arthur.backend.media;

import java.io.File;
// import com.xuggle.mediatools.demos.ConcatenateAudioAndVideo;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;

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
    System.out.println("writing audio to " + outname);
    return new ArthurSound(outname);
  }

  public static ArthurSound minus(ArthurSound one, ArthurSound two, String outname) {
    return add(two, one, outname);
  }

  public static ArthurSound multiply(ArthurSound one, ArthurSound two) {
    return null;
  }

  public static ArthurSound divide(ArthurSound one, ArthurSound two) {
    return null;
  }

}
