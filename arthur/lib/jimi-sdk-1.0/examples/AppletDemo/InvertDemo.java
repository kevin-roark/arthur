import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class InvertDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
        Invert filter = new Invert(source);
        return filter;
    }
}
   
