package arthur.backend.media;

public class mothertester {
	public static void main(String[] args) {
		if (args.length < 6){
			System.out.println("ERROR! Usage example: java imagetester img1.jpg img2.jpg vid1.mp4 vid2.mp4 sound1.mp3 sound2.mp3");
			return;
		}
		ArthurImage image1 = new ArthurImage(args[0]);
		ArthurImage image2 = new ArthurImage(args[1]);

		ArthurVideo video1 = new ArthurVideo(args[2]);
		ArthurVideo video2 = new ArthurVideo(args[3]);

		ArthurSound sound1 = new ArthurSound(args[4]);
		ArthurSound sound2 = new ArthurSound(args[5]);


		//IMAGE OPERATIONS:
		//Image+Image
		try{
			System.out.print("Image+Image......");
			ArthurImage addedImages=image1.add(image2);
			addedImages.writeToFile("im+im.jpg");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}


		//Image-Image
		try{
			System.out.print("Image-Image......");
			ArthurImage subtractedImages=image1.minus(image2);
			subtractedImages.writeToFile("im-im.jpg");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}


		//Image*Image
		try{
			System.out.print("Image*Image......");
			ArthurImage multipliedImages=image1.multiply(image2);
			multipliedImages.writeToFile("imxim.jpg");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Image/Image
		try{
			System.out.print("Image/Image......");
			ArthurImage dividedImages=image1.divide(image2);
			dividedImages.writeToFile("imDIVim.jpg");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Image+Video
		try{
			System.out.print("Image+Video......");
			ArthurImage implusvid=image1.add(video1);
			implusvid.writeToFile("im+vid.jpg");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Image-Video
		try{
			System.out.print("Image-Video......");
			ArthurImage imminusvid=image1.minus(video1);
			imminusvid.writeToFile("im-vid.jpg");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Image+Sound
		try{
			System.out.print("Image+Sound......");
			ArthurImage implussound=image1.add(sound1);
			implussound.writeToFile("im+sound.jpg");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Image-Sound
		try{
			System.out.print("Image-Sound......");
			ArthurImage imminussound=image1.minus(sound1);
			imminussound.writeToFile("im-sound.jpg");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}





		//VIDEO OPERATIONS:
		//Video+Video
		try{
			System.out.print("Video+Video......");
			ArthurVideo addedVideos=video1.add(video2);
			addedVideos.writeToFile("vid+vid.mp4");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}


		//Video-Video
		try{
			System.out.print("Video-Video......");
			ArthurVideo subtractedVideos=video1.minus(video2);
			subtractedVideos.writeToFile("vid-vid.mp4");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}


		//Video*Video
		try{
			System.out.print("Video*Video......");
			ArthurVideo multipliedVideos=video1.multiply(video2);
			multipliedVideos.writeToFile("vidxvid.mp4");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Video/Video
		try{
			System.out.print("Video/Video......");
			ArthurVideo dividedVideos=video1.divide(video2);
			dividedVideos.writeToFile("vidDIVvid.mp4");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Video+Image
		try{
			System.out.print("Video+Image......");
			ArthurVideo vidplusim=video1.add(image2);
			vidplusim.writeToFile("vid+im.mp4");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Video-Image
		try{
			System.out.print("Video-Image......");
			ArthurVideo vidminusim=video1.minus(image1);
			vidminusim.writeToFile("vid-im.mp4");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Video+Sound
		try{
			System.out.print("Video+Sound......");
			ArthurVideo vidplussound=video1.add(sound1);
			vidplussound.writeToFile("vid+sound.mp4");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Video-Sound
		try{
			System.out.print("Video-Sound......");
			ArthurVideo vidminussound=video1.minus(sound1);
			vidminussound.writeToFile("vid-sound.mp4");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}






		//SOUND OPERATIONS:
		//Sound+Sound
		try{
			System.out.print("Sound+Sound......");
			ArthurSound addedSounds=sound1.add(sound2);
			addedSounds.writeToFile("sound+sound.mp3");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}


		//Sound-Sound
		try{
			System.out.print("Sound-Sound......");
			ArthurSound subtractedSounds=sound1.minus(sound2);
			subtractedSounds.writeToFile("sound-sound.mp3");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}


		//Sound*Sound
		try{
			System.out.print("Sound*Sound......");
			ArthurSound multipliedSounds=sound1.multiply(sound2);
			multipliedSounds.writeToFile("soundxsound.mp3");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Sound/Sound
		try{
			System.out.print("Sound/Sound......");
			ArthurSound dividedSounds=sound1.divide(sound2);
			dividedSounds.writeToFile("soundDIVsound.mp3");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Sound+Image
		try{
			System.out.print("Sound+Image......");
			ArthurSound soundplusim=sound1.add(image2);
			soundplusim.writeToFile("sound+im.mp3");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Sound-Image
		try{
			System.out.print("Sound-Image......");
			ArthurSound soundminusim=sound1.minus(image1);
			soundminusim.writeToFile("sound-im.mp3");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Sound+Video
		try{
			System.out.print("Sound+Video......");
			ArthurSound soundplusvideo=sound1.add(video1);
			soundplusvideo.writeToFile("sound+vid.mp3");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}

		//Sound-Video
		try{
			System.out.print("Sound-Video......");
			ArthurSound soundminusvid=sound1.minus(video1);
			soundminusvid.writeToFile("sound-vid.mp3");
			System.out.print("			OK\n");
		}

		catch(Exception e){
			System.out.print("			FAILURE\n");
		}




	
	}
}