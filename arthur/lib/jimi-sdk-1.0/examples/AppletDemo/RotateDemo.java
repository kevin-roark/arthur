import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class RotateDemo extends AbstractDemo
{
    public ImageFilter getFilter()
    {
       Rotate filter = new Rotate(28);
       return filter;
    }
}
