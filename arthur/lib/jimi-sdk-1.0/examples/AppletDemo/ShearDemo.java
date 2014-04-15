import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class ShearDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
       /* value is the angel to use */
       Shear filter = new Shear(source, 40);
       return filter;
    }
}
