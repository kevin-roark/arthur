package arthur.backend.media;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaTool;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.AudioSamplesEvent;
import com.xuggle.mediatool.event.VideoPictureEvent;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.mediatool.event.ICloseEvent;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.mediatool.event.IOpenEvent;
import com.xuggle.mediatool.event.IOpenCoderEvent;
import com.xuggle.mediatool.event.ICloseCoderEvent;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IVideoPicture;
import java.nio.ShortBuffer;

/**
 * Contains a suite of static methods to perform math operations involving
 * colors.
 */
public class JavaVideoMath {

  public static ArthurVideo add(ArthurVideo one, ArthurVideo two) {
    ArthurVideo result = new ArthurVideo();
    IMediaReader r1 = one.reader;
    IMediaReader r2 = two.reader;

    return result;
  }

  public static ArthurVideo add(ArthurVideo one, ArthurNumber two) {
    ArthurVideo result = new ArthurVideo();
    result.filename = one.filename;
    result.writer = ToolFactory.makeWriter("intermediate.mp4", one.reader);
  	IMediaReader reader = one.reader; //clone later
    IMediaTool volumeMediaTool = new volumeTool(two.val);
  	reader.addListener(volumeMediaTool);
  	volumeMediaTool.addListener(result.writer);
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
        System.out.print(buffer.get(i));
  			buffer.put(i, (short) (buffer.get(i) * volume));
  		}
  		super.onAudioSamples(event);
  	}
  }

  private static class concatenatorTool extends MediaToolAdapter {
    //current offset
    private long mOffset = 0;
    //next video timestamp
    private long mNextVideo = 0;
    //index of audio stream 
    private long mNextAudio = 0;
    //index of audio stream
    private final int mAudioStreamIndex;
    //index of video stream
    private final int mVideoStreamIndex;

    //public concatenatorTool(index_of_audio_stream, index_of_video_stream)
    public concatenatorTool(int audioStreamIndex, int videoStreamIndex) {
      mAudioStreamIndex = audioStreamIndex;
      mVideoStreamIndex = videoStreamIndex;
    }
    public void onAudioSamples(IAudioSamplesEvent event) {
      IAudioSamples samples = event.getAudioSamples();
      //new time stamp = original + offset for this media file
      long newTimeStamp = samples.getTimeStamp() + mOffset;
      //keep track of predicted time of the next audio samples
      //if the end of the media file is encountered, the offset will be adjusted to this time
      mNextAudio = samples.getNextPts();
      //set new timestamp on audio samples
      samples.setTimeStamp(newTimeStamp);
      //create a new audio samples event w/ the true audio stream index
      super.onAudioSamples(new AudioSamplesEvent(this, samples, mAudioStreamIndex));
    }
    public void onVideoPicture(IVideoPictureEvent event) {
      IVideoPicture picture = event.getMediaData();
      long originalTimeStamp = picture.getTimeStamp();
      //new time stamp = original + offset for this media file
      long newTimeStamp = originalTimeStamp + mOffset;
      //keep track of predicted time of the next video picture
      //if the end of the file is encountered, the offset will be adjusted to this time
      //no method called getNextPts() for video pictures
      //but we know the next video timestamp should be >= 1 tick ahead 
      mNextVideo = originalTimeStamp + 1;
      //set the new timestamp on video samples
      picture.setTimeStamp(newTimeStamp);
      //create a new video picture event w/ the true video stream index
      super.onVideoPicture(new VideoPictureEvent(this, picture, mVideoStreamIndex));
    }
    public void onClose(ICloseEvent event) {
      //update the offset by the larger of the next expected audio or video frame time
      mOffset = Math.max(mNextVideo, mNextAudio);
      if (mNextAudio < mNextVideo) {
        //do we want to deal with this
      }
    }
    @Override
    public void onAddStream(IAddStreamEvent event) {

    }
    @Override
    public void onOpen(IOpenEvent event) {

    }
    @Override 
    public void onOpenCoder(IOpenCoderEvent event) {

    }
    @Override
    public void onCloseCoder(ICloseCoderEvent event) {

    }
  }

}
