package arthur.backend.media;

import arthur.backend.IoUtils;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;
import java.io.*;
import java.awt.image.*;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.imageio.*;
import java.lang.*;
import com.xuggle.xuggler.IContainer;
import java.awt.AlphaComposite;

/**
 * Java implementation of arthur Video!
 */
public class ArthurVideo extends ArthurMedia {

  public static final String VIDEO = "Video";
  public static final String ZERO = "ZERO.mp4";

  public String filename;
  public static ArrayList<String> intermediateFiles;


    public static final double SECONDS_BETWEEN_FRAMES = 1;

    private static ArthurImage frames;
    public static long duration;

    // The video stream index, used to ensure we display frames from one and
    // only one video stream from the media container.
    private static int mVideoStreamIndex = -1;

    // Time of last frame write
    private static long mLastPtsWrite = Global.NO_PTS;

    public static final long MICRO_SECONDS_BETWEEN_FRAMES =
    (long)(Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);


  public ArthurVideo(String fn) {
    this.type = VIDEO;
    filename = fn;
    if (intermediateFiles == null) {
      intermediateFiles = new ArrayList<String>();
    }
  }

  public ArthurVideo add(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(VIDEO)) {
      return JavaVideoMath.add(this, (ArthurVideo) two, outname);
    }
    else if (two.type.equals(ArthurColor.COLOR)) {
      return JavaVideoMath.add(this, (ArthurColor) two, outname);
    }
    else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaVideoMath.add(this, (ArthurNumber) two, outname);
    }
    else if (two.type.equals(ArthurString.STRING)) {
      return JavaVideoMath.add(this, (ArthurString) two, outname);
    }
    else if (two.type.equals(ArthurImage.IMAGE)) {
      return JavaVideoMath.add(this, (ArthurImage) two, outname);
    }
    else if (two.type.equals(ArthurSound.SOUND)) {
      return JavaVideoMath.add(this, (ArthurSound) two, outname);
    }
    return this;
  }

  public ArthurVideo minus(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(VIDEO)) {
      return JavaVideoMath.minus(this, (ArthurVideo) two, outname);
    }
    else if (two.type.equals(ArthurColor.COLOR)) {
      return JavaVideoMath.minus(this, (ArthurColor) two, outname);
    }
    else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaVideoMath.minus(this, (ArthurNumber) two, outname);
    }
    else if (two.type.equals(ArthurString.STRING)) {
      return JavaVideoMath.minus(this, (ArthurString) two, outname);
    }
    else if (two.type.equals(ArthurImage.IMAGE)) {
      return JavaVideoMath.minus(this, (ArthurImage) two, outname);
    }
    //TODO: minus sound
    return this;
  }

  public ArthurVideo multiply(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(VIDEO)) {
      return JavaVideoMath.multiply(this, (ArthurVideo) two, outname);
    }
    else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaVideoMath.multiply(this, (ArthurNumber) two, outname);
    }
    return this;
  }

  public ArthurVideo divide(ArthurMedia two) {
    String outname = nameGen();
    if (two.type.equals(VIDEO)) {
      return JavaVideoMath.divide(this, (ArthurVideo) two, outname);
    }
    else if (two.type.equals(ArthurNumber.NUMBER)) {
      return JavaVideoMath.divide(this, (ArthurNumber) two, outname);
    }
    return this;
  }

  public ArthurMedia castTo(String mediaType) {
    if (mediaType.equals("Image")) {
      return this.toImage();
    } else if (mediaType.equals("Sound")) {
      return this.toSound();
    } else if (mediaType.equals("string")) {
      return this.toArtString();
    } else if (mediaType.equals("num")) {
      return this.toNumber();
    } else if (mediaType.equals("color")) {
      return this.toImage().toColor();
    }

    return this;
  }

  public ArthurSound toSound() {
    String name = ArthurSound.nameGen();
    System.out.println(name);
    String rawExtractCommand = "ffmpeg -i %s -vn -ar 44100 -ac 2 -ab 192 -f mp3 %s";
    String extractCommand = String.format(rawExtractCommand, this.filename, name);
    IoUtils.execute(extractCommand);
    return new ArthurSound(name);
  }

  public ArthurNumber toNumber() {
    IContainer container = IContainer.make();
    int result = container.open(this.filename, IContainer.Type.READ, null);
    double dur = (double) container.getDuration() / 1000000;
    return new ArthurNumber(dur);
  }

  public ArthurString toArtString() {
    ArthurImage im = toImage();
    return im.toArtString();
  }

  public ArthurImage toImage() {
    IContainer container = IContainer.make();
    int result = container.open(filename, IContainer.Type.READ, null);
    duration = container.getDuration()/1000000;
    duration =duration-duration%1;

    IMediaReader mediaReader = ToolFactory.makeReader(filename);
        // frames=new ArthurImage("OUTPUT.jpg");
        // stipulate that we want BufferedImages created in BGR 24bit color space
    mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

    mediaReader.addListener(new ImageSnapListener());

    // read out the contents of the media file and dispatch events to the attached listener
    while (mediaReader.readPacket() == null) ;

    return frames;
  }

  private static class ImageSnapListener extends MediaListenerAdapter {

    public void onVideoPicture(IVideoPictureEvent event) {

      if (event.getStreamIndex() != mVideoStreamIndex) {
                // if the selected video stream id is not yet set, go ahead an
                // select this lucky video stream
        if (mVideoStreamIndex == -1)
          mVideoStreamIndex = event.getStreamIndex();
                // no need to show frames from this video stream
        else
          return;
      }

            // if uninitialized, back date mLastPtsWrite to get the very first frame
      if (mLastPtsWrite == Global.NO_PTS)
        mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;

            // if it's time to write the next frame
      if (event.getTimeStamp() - mLastPtsWrite >=
        MICRO_SECONDS_BETWEEN_FRAMES) {

                // String outputFilename = dumpImageToFile(event.getImage(), "sampledvid.jpg");
        ArthurImage temp=new ArthurImage(event.getImage(), "sampledvid.jpg");

      if(frames==null)
        frames=temp;

      else{

        BufferedImage image = JavaImageMath.clone(frames.bf);
        BufferedImage image2 = JavaImageMath.clone(temp.bf);

        WritableRaster r1 = image.getRaster();
        int width = r1.getWidth();
        int height = r1.getHeight();

        BufferedImage r2resize = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=image2.createGraphics();
        float opacity = 1/(duration+1);
        // float opacity=0.9f;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2.drawImage(image2, 0, 0, width, height, null);
        g2.dispose();

        temp.bf=image2;
        frames=frames.divide(temp);

      }

                // indicate file written
      double seconds = ((double) event.getTimeStamp()) /
      Global.DEFAULT_PTS_PER_SECOND;


                // update last write time
      mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
    }
  }
}



  public String jsLiteral() {
    return "new ArthurVideo(" + json() + ")";
  }

  public String json() {
    String js = "{";
    js += "\"filename\": \"" + this.filename + "\"";
    if (this.frame != null) {
      js += ", \"frame\": " + this.frame.json();
    }
    if (this.delay != null) {
      js += ", \"delay\": " + this.delay.val;
    }
    js += "}";
    return js;
  }

  public static String nameGen() {
    String name = "Video-" + System.currentTimeMillis() + ".mp4";
    if (ArthurVideo.intermediateFiles == null) {
      ArthurVideo.intermediateFiles = new ArrayList<String>();
    }
    ArthurVideo.intermediateFiles.add(name);
    return name;
  }

  public void writeToFile(String fname) {
    IoUtils.move(this.filename, fname); // move file to correct name
    this.filename = fname.substring(fname.indexOf('/') + 1); // remove 'buster'
  }

}
