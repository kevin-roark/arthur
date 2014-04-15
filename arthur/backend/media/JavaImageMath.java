package arthur.backend.media;

import java.io.*;
import java.awt.image.*;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.imageio.*;
import java.lang.*;
import java.util.Random;

/**
 * Contains a suite of static methods to perform math operations involving
 * images.
 */
public class JavaImageMath {

	static int counter = 0;

	public static ArthurImage add(ArthurImage one, ArthurColor two) {
		BufferedImage image = one.bf;

		double r = two.r.val;
		double g = two.g.val;
		double b = two.g.val;

		WritableRaster raster = image.getRaster();
		int[] pixelArray = new int[3];
		for (int y = 0; y < raster.getHeight(); y++) {
			for (int x = 0; x < raster.getWidth(); x++) {
				pixelArray = raster.getPixel(x, y, pixelArray);
				pixelArray[0] = (int) (pixelArray[0] + r) / 2;
				pixelArray[1] = (int) (pixelArray[1] + g) / 2;
				pixelArray[2] = (int) (pixelArray[2] + b) / 2;
				raster.setPixel(x, y, pixelArray);
			}
		}

		//save image
		String outputFn = one.filename.substring(0, one.filename.indexOf(".jpg")) +
								"+" + //filename can't contain the / or *characters; decide later
								r + g + b +
								counter +
								".jpg";
		counter++;

		return new ArthurImage(image, outputFn);
	}

	public static ArthurImage add(ArthurImage one, ArthurImage two) {
		return JavaImageMath.addition(one, two, 0);
	}

	public static ArthurImage minus(ArthurImage one, ArthurColor two) {
		//calculate complementary color and add it
		ArthurColor complement = new ArthurColor(255.0 - two.r.val, 255.0 - two.g.val, 255.0 - two.b.val, two.a.val);
		return JavaImageMath.add(one, complement);
	}

	public static ArthurImage minus(ArthurImage one, ArthurImage two) {
		return JavaImageMath.addition(two, one, 1);
	}

	public static ArthurImage addition(ArthurImage one, ArthurImage two, int op) {
		BufferedImage image = one.bf;
		BufferedImage image2 = two.bf;

		WritableRaster r1 = image.getRaster();
		WritableRaster r2 = image2.getRaster();
		int newWidth = r1.getWidth() + r2.getWidth();
		int newHeight = Math.max(r1.getHeight(), r2.getHeight());

		BufferedImage collage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = collage.createGraphics();
		//g2d.setBackground(Color.BLACK);
		g2d.drawImage(image, 0, 0, null);
		g2d.drawImage(image2, r1.getWidth(), 0, null);
		g2d.dispose();

		//save image
		String outputFn;
		if (op == 0) {
			outputFn = one.filename.substring(0, one.filename.indexOf(".jpg")) +
						"+" +
						two.filename.substring(0, two.filename.indexOf(".jpg")) +
						counter +
						".jpg";
		}
		else {
			outputFn = two.filename.substring(0, two.filename.indexOf(".jpg")) +
						"-" +
						one.filename.substring(0, one.filename.indexOf(".jpg")) +
						counter +
						".jpg";
		}

		counter++;

		return new ArthurImage(collage, outputFn);
	}

	public static ArthurImage multiply(ArthurImage one, ArthurImage two) {
		BufferedImage image = one.bf;
		BufferedImage image2 = two.bf;

		WritableRaster r1 = image.getRaster();
		WritableRaster r2 = image2.getRaster();
		int newWidth = Math.max(r1.getWidth(), r2.getWidth());
		int newHeight = Math.max(r1.getHeight(), r2.getHeight());

		BufferedImage collage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = collage.getRaster();

		int[] p1 = new int[3];
		int[] p2 = new int[3];
		int[] pixelArray = new int[3];
		for (int y = 0; y < newHeight; y++) {
			for (int x = 0; x < newWidth; x++) {
				p1 = null;
				p2 = null;

				if (x < r1.getWidth() && y < r1.getHeight()) {
					p1 = r1.getPixel(x, y, p1);
				}

				if (x < r2.getWidth() && y < r2.getHeight()) {
					p2 = r2.getPixel(x, y, p2);
				}

				for (int i = 0; i < 3; i++) {
					if (p1 == null && p2 == null) {
						pixelArray[i] = 0;
					}
					else if (p1 == null && p2 != null) {
						pixelArray[i] = p2[i];
					}
					else if (p1 != null && p2 == null) {
						pixelArray[i] = p1[i];
					}
					else {
						pixelArray[i] = (int) ((p1[i] + p2[i]) / 2);
					}
				}
				raster.setPixel(x, y, pixelArray);
			}
		}

		//save image
		String outputFn = one.filename.substring(0, one.filename.indexOf(".jpg")) +
								"X" + //filename can't contain the / or *characters; decide later
								two.filename.substring(0, two.filename.indexOf(".jpg")) +
								counter +
								".jpg";
		counter++;

		return new ArthurImage(collage, outputFn);
	}

	public static ArthurImage multiply (ArthurImage one, ArthurNumber two) { //change to ArthurNumber later
		double f = two.val;
		//get image
		BufferedImage image = one.bf;

		//manipulate image
		WritableRaster raster = image.getRaster();
		int width = (int) (raster.getWidth() * f);
		int height = (int) (raster.getHeight() * f);

		BufferedImage collage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = collage.createGraphics();
		g2d.drawImage(image, 0, 0, width, height, null);
		g2d.dispose();

		//save image
		String outputFn = one.filename.substring(0, one.filename.indexOf(".jpg")) +
								"X" + //filename can't contain the / or *characters; decide later
								f +
								counter +
								".jpg";
		counter++;

		return new ArthurImage(collage, outputFn);
	}

	public static ArthurImage divide(ArthurImage one, ArthurImage two) {
		BufferedImage image = one.bf;
		BufferedImage image2 = two.bf;

		WritableRaster r1 = image.getRaster();
		int width = r1.getWidth();
		int height = r1.getHeight();

		BufferedImage r2resize = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = r2resize.createGraphics();
		g2d.drawImage(image2, 0, 0, width, height, null);
		g2d.dispose();
		WritableRaster r2 = r2resize.getRaster();

		BufferedImage collage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = collage.getRaster();

		int[] p1 = new int[3];
		int[] p2 = new int[3];
		int[] pixelArray = new int[3];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				p1 = r1.getPixel(x, y, p1);
				p2 = r2.getPixel(x, y, p2);

				for (int i = 0; i < 3; i++) {
					pixelArray[i] = (int) ((p1[i] + p2[i]) / 2);
				}
				raster.setPixel(x, y, pixelArray);
			}
		}

		//save image
		String outputFn = one.filename.substring(0, one.filename.indexOf(".jpg")) +
								"D" + //filename can't contain the / or *characters; decide later
								two.filename.substring(0, two.filename.indexOf(".jpg")) +
								counter +
								".jpg";
		counter++;

		return new ArthurImage(collage, outputFn);
	}

	public static ArthurImage divide (ArthurImage one, ArthurNumber two) { //change to ArthurNumber later
		int f = two.intval();
		//get image
		BufferedImage image = one.bf;

		//manipulate image
		WritableRaster raster = image.getRaster();
		int width0 = raster.getWidth();
		int height0 = raster.getHeight();
		int width = width0 / f;
		int height = height0 / f;

		BufferedImage temp = new BufferedImage(width * f, height * f, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = temp.createGraphics();
		for (int i = 0; i < f; i++) {
			for (int j = 0; j < f; j++) {
				g2d.drawImage(image, j * width, i * height, width, height, null);
			}
		}
		g2d.dispose();
		BufferedImage collage = new BufferedImage(width0, height0, BufferedImage.TYPE_INT_RGB);
		g2d = collage.createGraphics();
		g2d.drawImage(temp, 0, 0, width0, height0, null);
		g2d.dispose();

		//save image
		String outputFn = one.filename.substring(0, one.filename.indexOf(".jpg")) +
								"D" + //filename can't contain the / or *characters; decide later
								f +
								counter +
								".jpg";
		counter++;

		return new ArthurImage(collage, outputFn);
	}
	/*

	public static BufferedImage art2bf(ArthurImage artImage) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(artImage.filename));
		} catch (IOException e) {

		}
		if (image == null) {
			System.out.println("Error - couldn't retrieve image.");
		}

		return image;
	}

	public static ArthurImage bf2art(BufferedImage buffImage, String outputFn) {
		ArthurImage result = null;

		try {
			File outputFile = new File(outputFn);
			ImageIO.write(buffImage, "jpg", outputFile);
			result = new ArthurImage(outputFn);
		} catch (IOException e) {

		}

		return result;
	}
	*/
}
