/** This is a basic Jimi Demo demonstrating how to color reduce/dither a file **/

import com.sun.jimi.core.core.*;
import com.sun.jimi.core.core.util.ColorReducer;
import java.awt.*;

public class ColorReduceWithJimi
{
	static Image image = null;

	public static void main(String[] args) throws JimiException
	{
		if (args.length < 3) {
			System.err.println("Usage: ColorReduceWithJimi <source> <dest> <# colors>");
			System.exit(1);
		}
		image = Jimi.getImage(args[0]);
		image = reduceColors(image, Integer.parseInt(args[2]), true);
		Jimi.putImage(image, args[1]);
		System.exit(0);
	}
	
	public static Image reduceColors(Image image, int colors, boolean dither)
	{
		ColorReducer reducer = new ColorReducer(colors, dither);
		Image img = null;
		try {
				img = reducer.getColorReducedImage(image);
		}
		catch (JimiException e) {
			System.out.println("Error color reducing/dithering");
		}
		return img;
	}
}
