import java.awt.image.*;
import java.awt.Color;
import com.sun.jimi.core.filters.*;

public class MarginDemo extends AbstractDemo
{
    final Color color[] = { Color.red, Color.blue, Color.white, Color.yellow, Color.green, Color.pink };
    int index = 0;
    
    public ImageFilter getFilter()
    {
        /*
        * Border color and width.
        * Let's we add a little fancy color changing 
        * and border size changing too :)
        */
        Margin filter = new Margin(source, color[index++], (int)value);
        if(index == color.length)
            index = 0;
        return filter;
    }

    public void clear()
    {
        super.clear();
//        index = 0;
    }
}
