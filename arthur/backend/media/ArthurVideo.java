package arthur.backend.media;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaTool;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import java.nio.ShortBuffer;

/**
 * Java implementation of arthur video!
 */
public class ArthurVideo extends ArthurMedia {

  public static final String VIDEO = "video";
  public String filename;
  public IMediaReader reader;
  public IMediaWriter writer;
  //vid stream properties
  public ArthurNumber width;
  public ArthurNumber height;
  //audio stream properties
  public ArthurNumber channelCount;
  public ArthurNumber sampleRate;

  public ArthurVideo() {
    this.type = VIDEO;
    filename = null;
    reader = null;
    writer = null;
    width = null;
    height = null;
    channelCount = null;
    sampleRate = null;
  }

  public ArthurVideo(String fn) {
    this.type = VIDEO;
    filename = fn;
    reader = ToolFactory.makeReader(filename);
    writer = null;
    
    IContainer container = IContainer.make();
    if (container.open(filename, IContainer.Type.READ, null) < 0) {
      System.out.println("Error! Couldn't open this video file: " + filename);
    }
    int numStreams = container.getNumStreams();
    for (int i = 0; i < numStreams; i++) {
      IStream stream = container.getStream(i);
      IStreamCoder coder = stream.getStreamCoder();
      if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
        width = new ArthurNumber(coder.getWidth());
        height = new ArthurNumber(coder.getHeight());
        System.out.println("Width: " + width + ", Height: " + height);
      }
      else if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
        channelCount = new ArthurNumber(coder.getChannels());
        sampleRate = new ArthurNumber(coder.getSampleRate());
        System.out.println("Channel count: " + channelCount + ", Sample rate: " + sampleRate);
      }
    }
  }

  public ArthurVideo add(ArthurMedia two) {
    ArthurVideo res = this;
    if (two.type.equals(VIDEO)) {
      res = JavaVideoMath.add(this, (ArthurVideo) two);
    }
    else if (two.type.equals(ArthurNumber.NUMBER)) {
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
