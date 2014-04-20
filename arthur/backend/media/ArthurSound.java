package arthur.backend.media;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IContainer;

/**
 * Java implementation of arthur sound!
 */
public class ArthurSound extends ArthurMedia implements java.io.Serializable {

    public static final String SOUND = "sound";
    public String filename;
    public IMediaReader clip;
    public ArthurNumber duration;

    public ArthurSound() {
        this.type = SOUND;
        filename = null;
        clip = null;
        duration = new ArthurNumber(0);
    }

    public ArthurSound(IMediaReader clop) {
        this.type = SOUND;
        filename = clop.getUrl();
        clip = clop;
        // get duration
        clip.open();
        final IContainer c = clip.getContainer();
        duration = new ArthurNumber(new Double(c.getDuration()));     // this fucked up
        c.close();
    }

    public ArthurSound(ArthurString fn) {
        this.type = SOUND;
        filename = fn.toString();
        clip = ToolFactory.makeReader(fn.str);
        // get duration
        clip.open();
        final IContainer c = clip.getContainer();
        duration = new ArthurNumber(new Double(c.getDuration()));
        c.close();
    }

    public ArthurSound(String fn) {
        this.type = SOUND;
        filename = fn;
        clip = ToolFactory.makeReader(fn);
        // get duration
        clip.open();
        final IContainer c = clip.getContainer();
        duration = new ArthurNumber(new Double(c.getDuration()));
        c.close();
    }


    public ArthurSound add(ArthurMedia two) {
        return JavaSoundMath.add(this, (ArthurSound) two);
    }

    public ArthurSound minus(ArthurMedia two) {
        return JavaSoundMath.minus(this, (ArthurSound) two);
    }

    public ArthurSound multiply(ArthurMedia two) {
        return JavaSoundMath.multiply(this, (ArthurSound) two);
    }

    public ArthurSound divide(ArthurMedia two) {
        return JavaSoundMath.divide(this, (ArthurSound) two);
    }



    public String toString() {
        return "sound file " + filename + ", duration " + duration + " ms";
    }

    public String jsLiteral() {
        return "new ArthurSound()";
    }



    // TEMP: REMOVE AND DO IN JS LATER
    public void play() {
        clip.addListener(ToolFactory.makeViewer(IMediaViewer.Mode.AUDIO_ONLY));
        while (clip.readPacket() == null)
          ;
    }

}