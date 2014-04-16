package arthur.backend.media;

public class vid {
	public static void main(String[] args) {
		ArthurVideo vid = new ArthurVideo("sample1.mp4");
		ArthurVideo vid2 = vid.add(new ArthurNumber(2.0));
	}
}