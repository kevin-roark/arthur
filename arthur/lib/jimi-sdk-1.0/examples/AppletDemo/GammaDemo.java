import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class GammaDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
        /* 
        * NOTE!!
        * A simpler constructor is also available:
        * Gamma(ImageProducer source, double value);
        */
        Gamma filter = new Gamma(source, 3.3, 5.7, 2.0);
        return filter;
    }
}
