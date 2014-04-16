// package arthur.backend.media;
import arthur.backend.media.*;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.ToolFactory;


/* you better hear 3 glass crashes */

public class lol {

	public static void main(String[] args) {

		/* Test constructor: ArthurSound(IMediaReader clop) */
		ArthurSound s1 = new ArthurSound(ToolFactory.makeReader("glass.mp3"));
		System.out.println("Playing: " + s1.toString());
		s1.play();

		/* Test constructor: ArthurSound(AthurString fn) */

		ArthurSound s2 = new ArthurSound(new ArthurString("glass.mp3"));
		System.out.println("Playing: " + s2.toString());
		s2.play();

		/* Test constructor: ArthurSound(String fn) */

		ArthurSound s3 = new ArthurSound("glass.mp3");
		System.out.println("Playing: " + s3.toString());	
		s3.play();
	}
}