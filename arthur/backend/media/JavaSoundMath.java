package arthur.backend.media;

import java.io.File;
// import com.xuggle.mediatools.demos.ConcatenateAudioAndVideo;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;

/**
 * Contains a suite of static methods to perform math operations involving
 * colors.
 */
public class JavaSoundMath {

  public static ArthurSound add(ArthurSound one, ArthurSound two) {
  	ConcatenateAudioAndVideo c = new ConcatenateAudioAndVideo();
  	c.concatenate(one.filename, two.filename, "sound_sum.mp3");
  	return new File("sound_sum.mp3");
  }

  public static ArthurSound minus(ArthurSound one, ArthurSound two) {

  }

  public static ArthurSound multiply(ArthurSound one, ArthurSound two) {

  }

  public static ArthurSound divide(ArthurSound one, ArthurSound two) {

  }

}
