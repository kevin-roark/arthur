binky


When you declare a variable in the init block, you can call it in the loop block by typing:
	varName
	[USED TO BE: arthur.varName  ??????]

This happens by default. If you don't want a variable to be accessible within the loop (e.g. variables only used once for setup, etc.), use the reserved word 'binky' to ghost it out of arthur's domain. 



init {
	image importantDog = "terrier_float.jpg";
	binky num X_OFFSET = 100;
	binky num Y_OFFSET = 150;

	frame dogframe = [300, 400, X_OFFSET, Y_OFFSET]; 		// idk: [width, height, x_pos, y_pos]

	arthur.add(importantDog);
}


loop {
	
	/* yes */
	dw(importantDog.size_x > 50) {
		shrink(importantDog, 5, 500); 		// shrink the pic 5% every 500ms
	}

	/* NO!!!! */
	importantDog.fling(X_OFFSET);  			// it's binky'd
}
