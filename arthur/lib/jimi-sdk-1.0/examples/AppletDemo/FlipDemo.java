import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class FlipDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
        /*
        * Several Flip 'modes' can be set:
        * FLIP_CCW,  FLIP_CW,
        * FLIP_LR,   FLIP_NULL,
        * FLIP_R180, FLIP_TB,
        * FLIP_XY,
        */
        // flip image 180 degrees.
        Flip filter = new Flip(source, Flip.FLIP_R180);
        return filter;
    }
}
