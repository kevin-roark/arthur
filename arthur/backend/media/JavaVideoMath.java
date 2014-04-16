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
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import java.nio.ShortBuffer;

/**
 * Contains a suite of static methods to perform math operations involving
 * colors.
 */
public class JavaVideoMath {

  static int counter = 0;

  public static ArthurVideo add(ArthurVideo one, ArthurVideo two) {
    /*
    ArthurVideo result = new ArthurVideo();
    IMediaReader reader1 = one.reader;
    IMediaReader reader2 = two.reader;
    //final int videoStreamIndex = 0;
    //final int audioStreamIndex = 1;
    
    concatenatorTool concatenator = new concatenatorTool(0, 1);
    reader1.addListener(concatenator);
    reader2.addListener(concatenator);
    IMediaWriter writer = ToolFactory.makeWriter("concatenated.mp4");
    concatenator.addListener(writer);

    //add video stream
    int width1 = one.width.val.intValue();
    int height1 = one.height.val.intValue();
    int width2 = two.width.val.intValue();
    int height2 = two.height.val.intValue();
    writer.addVideoStream(0, 0, width1, height1);
    writer.addVideoStream(1, 0, width2, height2); 
    while (reader1.readPacket() == null);
    while (reader2.readPacket() == null);
    writer.close();

    return result;
    */
    ArthurVideo result = new ArthurVideo();

    IMediaReader reader1 = one.reader;
    //IMediaReader reader2 = two.reader;
    IMediaReader reader2 = null;

    int width1 = one.width.val.intValue();
    int height1 = one.height.val.intValue();

    int width2 = two.width.val.intValue();
    int height2 = two.height.val.intValue();

    int channelCount1 = one.channelCount.val.intValue();
    int sampleRate1 = one.sampleRate.val.intValue();

    int channelCount2 = two.channelCount.val.intValue();
    int sampleRate2 = two.sampleRate.val.intValue();

    if (width1 != width2 || height1 != height2 || 
        channelCount1 != channelCount2 || sampleRate1 != sampleRate2) {
      IContainer container = IContainer.make();
      if (container.open(two.filename, IContainer.Type.READ, null) < 0) {
        System.out.println("Error! Couldn't open this vid: " + two.filename);
      }
      int numStreams = container.getNumStreams();
      for (int i = 0; i < numStreams; i++) {
        IStream stream = container.getStream(i);
        IStreamCoder coder = stream.getStreamCoder();
        if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
          coder.setWidth(width1);
          coder.setHeight(height1);
          System.out.println("New width: " + coder.getWidth() + ", New height: " + coder.getHeight());
        }
        else if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
          coder.setChannels(channelCount1);
          coder.setSampleRate(sampleRate1);
          System.out.println("New channel count: " + coder.getChannels() + ", New sample rate: " + coder.getSampleRate());
        }
      }
      //set reader2 to this new thing
      reader2 = ToolFactory.makeReader(container);
    }

    if (reader2 == null) {
      System.out.println("Reader 2 fucked up");
    }

    concatenatorTool concatenator = new concatenatorTool(0, 1);
    reader1.addListener(concatenator);
    reader2.addListener(concatenator);
    IMediaWriter writer = ToolFactory.makeWriter("concatenated" + counter + ".mp4");
    counter++;
    result.writer = writer;
    concatenator.addListener(writer);

    writer.addVideoStream(0, 0, width1, height1);
    writer.addAudioStream(1, 0, channelCount1, sampleRate1);
    while (reader1.readPacket() == null);
    while (reader2.readPacket() == null);
    writer.close();

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
    private long mOffset = 0;
    private long mNextVideo = 0;
    private long mNextAudio = 0;

    private final int mVideoStreamIndex;
    private final int mAudioStreamIndex;

    //public concatenatorTool(index_of_audio_stream, index_of_video_stream)
    public concatenatorTool(int videoStreamIndex, int audioStreamIndex) {
      mVideoStreamIndex = videoStreamIndex;
      mAudioStreamIndex = audioStreamIndex;
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
      super.onVideoPicture(new VideoPictureEvent(this, picture, mVideoStreamIndex)); //?
    }

    public void onClose(ICloseEvent event) {
      mOffset = Math.max(mNextVideo, mNextAudio);
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
