package arthur.backend.media;

public class ArthurVideoTester {

  public static void main(String[] args) {
    String f1 = "sample1.mp4";
    String f2 = "sample1.mp4";

    ArthurVideo v1 = new ArthurVideo(f1);
    ArthurVideo v2 = new ArthurVideo(f2);

    ArthurVideo v3 = v1.add(v2);

    //ArthurVideo v4 = v2.divide(new ArthurNumber(2));
  }
}
