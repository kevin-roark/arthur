import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class SmoothDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
       filter = new Smooth(source, (int)value);   
       return filter;
    }
}
