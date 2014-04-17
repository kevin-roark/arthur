package arthur.backend.media;

public class ArthurSoundTester {

	public static void main(String[] args) {
		String f1 = "output.mp3";
		String f2 = "glass.mp3";

		ArthurSound s1 = new ArthurSound(f1);
		ArthurSound s2 = new ArthurSound(f2);

		ArthurSound s3 = s1.add(s2);

		ArthurSound s4 = s1.minus(s2);

		ArthurSound s5 = s1.multiply(s3);

		ArthurSound s6 = s1.divide(s3);

		ArthurSound s7 = s1.multiply(new ArthurNumber(3.0));

		ArthurSound s8 = s1.divide(new ArthurNumber(3.0));
	}
}
