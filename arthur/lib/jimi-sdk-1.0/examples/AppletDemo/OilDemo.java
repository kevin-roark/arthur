import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class OilDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
        Oil filter = new Oil(source, (int)value);
        return filter;
    }
}
