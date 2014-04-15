import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class EdgeDetectDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
        EdgeDetect filter = new EdgeDetect(source);
        return filter;
    }
}
   
