public class imagetester {
	public static void main(String[] args) {
		if (args.length < 2){
            System.out.println("ERROR! Usage example: java imagetester img1.jpg img2.jpg");
        }
		ArthurImage image1 = new ArthurImage(args[0]);
		ArthurImage image2 = new ArthurImage(args[1]);

		System.out.println("Output files:");

		ArthurImage image3 = image1.add(image2);
		System.out.println(image3.filename);

		ArthurImage image4 = image1.minus(image2);
		System.out.println(image4.filename);

		ArthurImage image5 = image1.multiply(image2);
		System.out.println(image5.filename);

		ArthurImage image6 = image1.divide(image2);
		System.out.println(image6.filename);

		ArthurImage image7 = JavaImageMath.divide(image1, 2);
		System.out.println(image7.filename);

		ArthurImage image8 = JavaImageMath.multiply(image1, 3);
		System.out.println(image8.filename);

		ArthurImage image9 = JavaImageMath.multiply(image1, 0.25);
		System.out.println(image9.filename);
	}
}