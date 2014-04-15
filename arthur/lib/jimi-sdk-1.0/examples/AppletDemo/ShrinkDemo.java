import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class ShrinkDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
       /*
       * workaround for flat initial value 
       * (so you dont have to click twice for something to happen)
       */
       value += 1.0;  
       /* and action */
       Shrink filter = new Shrink(source, (int)value);
       return filter;
    }
}
