package arthur.backend.media;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaTool;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import java.nio.ShortBuffer;

/**
 * Java implementation of arthur video!
 */
public class ArthurVideo extends ArthurMedia {

  public static final String VIDEO = "video";
  public String filename;
  public IMediaReader reader;
  public IMediaWriter writer;

  public ArthurVideo() {
    this.type = VIDEO;
    filename = null;
    reader = null;
    writer = null;
  }

  public ArthurVideo(String fn) {
    this.type = VIDEO;
    filename = fn;
    reader = ToolFactory.makeReader(filename);
    writer = null;
  }

  public ArthurVideo add(ArthurMedia two) {
    ArthurVideo res = this;
    if (two.type.equals(ArthurNumber.NUMBER)) {
      res = JavaVideoMath.add(this, (ArthurNumber) two);
    }
    return res;
  }

  public ArthurVideo minus(ArthurMedia two) {
    return this;
  }

  public ArthurVideo multiply(ArthurMedia two) {
    return this;
  }

  public ArthurVideo divide(ArthurMedia two) {
    return this;
  }

  public String jsLiteral() {
    return "new ArthurVideo()";
  }

  /*
  public void writeToFile(String fname) {
    writer = ToolFactory.makeWriter(fname, reader);
  }
  */

}
