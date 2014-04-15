import java.awt.Image;
import java.awt.image.*;
import com.sun.jimi.core.filters.Tile;
import com.sun.jimi.core.component.JimiCanvas;

public class TileDemo extends AbstractDemo
{
    int h_tiles = 2;
    int v_tiles = 2;

    public ImageFilter getFilter()
    {
       Image tile_image = canvas.getImage();
       int tile_w = tile_image.getWidth(null);
       int tile_h = tile_image.getHeight(null);
       /* create a h_tiles x v_tile tile */
       Tile filter = new Tile(source, tile_w * h_tiles, tile_h * v_tiles);
       /* more tiles next time */
       h_tiles++;
       v_tiles++;
       return filter;
    }

    public void clear()
    {
      super.clear();
      h_tiles = 2;
      v_tiles = 2;
    }
}
