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
 * Contains a suite of static methods to perform math operations involving
 * colors.
 */
public class JavaVideoMath {

  public static ArthurVideo add(ArthurVideo one, ArthurVideo two) {
    return one;
  }

  public static ArthurVideo add(ArthurVideo one, ArthurNumber two) {
    ArthurVideo result = new ArthurVideo();
    result.filename = one.filename;
    result.writer = ToolFactory.makeWriter("newthing.mp4", one.reader);
  	IMediaReader reader = one.reader; //clone later
    IMediaTool volumeMediaTool = new volumeTool(two.val);
  	reader.addListener(volumeMediaTool);
  	volumeMediaTool.addListener(result.writer);
    reader.addListener(result.writer);
  	while (reader.readPacket() == null);
    return result;
  }

  public static ArthurVideo minus(ArthurVideo one, ArthurVideo two) {
    return one;
  }

  public static ArthurVideo multiply(ArthurVideo one, ArthurVideo two) {
    return one;
  }

  public static ArthurVideo divide(ArthurVideo one, ArthurVideo two) {
    return one;
  }

  //tools

  private static class volumeTool extends MediaToolAdapter {
  	private double volume;
  	public volumeTool(double vol) {
  		volume = vol;
  	}
  	@Override
  	public void onAudioSamples(IAudioSamplesEvent event) {
  		ShortBuffer buffer = event.getAudioSamples().getByteBuffer().asShortBuffer();
  		for (int i = 0; i < buffer.limit(); i++) {
  			buffer.put(i, (short) (buffer.get(i) * volume));
  		}
  		super.onAudioSamples(event);
  	}
  }

}
