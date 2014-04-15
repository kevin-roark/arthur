import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class ScaleCopyDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
        /*
        * workaround for flat initial value 
        * (so you dont have to click twice for something to happen)
        */
        value += 0.1;
        /* scale image */
        value += 0.5;
        ScaleCopy filter = new ScaleCopy(source, value);
        return filter;
    }
}
