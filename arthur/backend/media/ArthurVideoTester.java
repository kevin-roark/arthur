package arthur.backend.media;

public class ArthurVideoTester {

  public static void main(String[] args) {
    String f1 = "ball.mp4";
    String f2 = "ZERO.mp4";

    ArthurVideo v1 = new ArthurVideo(f1);
    ArthurVideo v2 = new ArthurVideo(f2);

    //ArthurVideo v3 = v1.add(v2);

    //ArthurVideo v4 = v2.divide(new ArthurNumber(2));

    ArthurSound s = new ArthurSound("ZERO.mp3");
    //ArthurVideo v5 = v2.minus(s);
    //ArthurVideo v6 = v2.add(s);

    ArthurString str = new ArthurString("The future is now - Arthur");
    //ArthurVideo v7 = str.toVideo();

    ArthurVideo v8 = v1.divide(new ArthurNumber(4));
  }
}
