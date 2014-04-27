package arthur.backend.media;

public class ArthurVideoTester {

  public static void main(String[] args) {
    String f1 = "ball.mp4";
    String f2 = "ZERO.mp4";
    String f3 = "russian.mp4";

    ArthurVideo v1 = new ArthurVideo(f1);
    ArthurVideo v2 = new ArthurVideo(f2);
    ArthurVideo v03 = new ArthurVideo(f3);

    //ArthurVideo v3 = v1.add(v2);

    //ArthurVideo v4 = v2.divide(new ArthurNumber(2));

    ArthurSound s = new ArthurSound("ZERO.mp3");
    //ArthurVideo v5 = v2.minus(s);
    //ArthurVideo v6 = v2.add(s);

    ArthurString str = new ArthurString("The future is now - Arthur");
    //ArthurVideo v7 = str.toVideo();

    //ArthurVideo v8 = v1.divide(new ArthurNumber(4));

    //ArthurVideo v9 = v2.multiply(new ArthurNumber(5));
    //ArthurVideo v10 = v2.multiply(new ArthurNumber(0.2));

    /*ArthurVideo v11 = v2.multiply(new ArthurNumber(2))
                        .divide(new ArthurNumber(2))
                        .add(new ArthurColor(255.0, 0.0, 0.0, 1.0))
                        .add(new ArthurSound("future.mp3"))
                        ;*/

    //ArthurVideo v12 = v1.multiply(v2);
    //ArthurVideo v13 = v1.divide(v2);

    //System.out.println("v12: " + v12.filename);
    //System.out.println("v13: " + v13.filename);

    ArthurVideo v14 = v2.multiply(v03);
    ArthurVideo v15 = v03.multiply(v14);
    ArthurVideo v16 = v2.divide(v03);

    System.out.println("v14: " + v14.filename);
    System.out.println("v15: " + v15.filename);
    System.out.println("v16: " + v16.filename);
  }
}
