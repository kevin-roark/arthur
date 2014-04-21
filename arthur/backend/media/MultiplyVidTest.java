package arthur.backend.media;


class MultiplyVidTest {

public static void main (String[] args){

ArthurVideo video = new ArthurVideo("footlocker.mp4");
ArthurVideo video2 = new ArthurVideo("newthing.mp4");
ArthurVideo video3 = video.multiply(video2);
video3.writeToFile("multiply.mp4");

}

}
