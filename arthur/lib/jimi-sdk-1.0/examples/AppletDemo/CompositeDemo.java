import java.awt.Image;
import java.awt.image.*;
import com.sun.jimi.core.filters.*;

public class CompositeDemo extends AbstractDemo
{
   /* current mode */
   int index = 0;
   /* all available Flip modes */
   final private static int mode[] =
   {
      Flip.FLIP_CCW,
      Flip.FLIP_CW,
      Flip.FLIP_LR,
      Flip.FLIP_NULL,
      Flip.FLIP_R180,
      Flip.FLIP_TB,
      Flip.FLIP_XY
   };

   public ImageFilter getFilter()
   {
      Flip flip = new Flip(source, mode[index++]);
      if(index == mode.length) {
         index = 0;
      }
      return flip;
   }

   /* using multiple filters */
   protected void applyFilter(ImageFilter filter)
   {
      setBusy("Applying Filter, please wait...", canvas.getGraphics());

      /* create the second filter */
      EdgeDetect edge = new EdgeDetect(source);

      source = image.getSource();
      FilteredImageSource fis = new FilteredImageSource(source, edge);
      Image new_image = createImage(fis);
      /*
      * ok now we have the first filtered image,
      * apply the second filter.
      */
      source = new_image.getSource();
      fis = new FilteredImageSource(source, filter);
      new_image = createImage(fis);

      if(new_image != null) {
         canvas.setImage(new_image);
      }
      new_image.flush();
      new_image = null;
   }

   public void clear()
   {
//      super.clear();
	   reset();
      index = 0;
   }
}
