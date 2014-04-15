import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class GrayDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
        Gray filter = new Gray();
        return filter;
    }
}
