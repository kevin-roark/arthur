import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class EnlargeDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
       //double the size for each time this method is called.
       value += 1;
       // filter
       Enlarge filter = new Enlarge(source, (int)value);
       return filter;
    }
}
