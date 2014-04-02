package arthur.backend.media;

/**
* Contains a suite of static methods to perform math operations involving
* colors.
*/
public class JavaColorMath {

public static ArthurColor add(ArthurColor one, ArthurColor two) {
	return new ArthurColor(Math.min(one.r+two.r, 255), Math.min(one.g+two.g, 255), Math.min(one.b+two.b, 255), Math.min(one.a+two.a, 255);

}

public static ArthurColor add(ArthurColor one, ArthurImage two) {

}

public static ArthurColor add(ArthurColor one, ArthurVideo two) {

}

public static ArthurColor add(ArthurColor one, ArthurSound two) {

}

public static ArthurColor add(ArthurColor one, ArthurNumber two) {
	return new ArthurColor(Math.min(one.r+two.val, 255), Math.min(one.g+two.val, 255), Math.min(one.b+two.val, 255), one.a;
}

public static ArthurColor add(ArthurColor one, ArthurString two) {

}

public static ArthurColor minus(ArthurColor one, ArthurColor two) {
	return new ArthurColor(Math.max(one.r-two.r, 0), Math.max(one.g-two.g, 0), Math.max(one.b-two.b, 0), Math.max(one.a-two.a, 0);

}

public static ArthurColor minus(ArthurColor one, ArthurImage two) {
	
}

public static ArthurColor minus(ArthurColor one, ArthurVideo two) {

}

public static ArthurColor minus(ArthurColor one, ArthurSound two) {

}

public static ArthurColor minus(ArthurColor one, ArthurNumber two) {
	return new ArthurColor(Math.max(one.r-two.val, 0), Math.max(one.g-two.val, 0), Math.max(one.b-two.val, 0), Math.max(one.a-two.val, 0);

}

public static ArthurColor minus(ArthurColor one, ArthurString two) {

}

public static ArthurColor multiply(ArthurColor one, ArthurColor two) {
	int newR=(one.r+two.r)/2;
	int newG=(one.g+two.g)/2;
	int newB=(one.b+two.b)/2;
	int newA=Math.max(one.a, two.a);
	return new ArthurColor(newR, newG, newB, newA);

}

public static ArthurColor multiply(ArthurColor one, ArthurImage two) {

}

public static ArthurColor multiply(ArthurColor one, ArthurVideo two) {

}

public static ArthurColor multiply(ArthurColor one, ArthurSound two) {

}

public static ArthurColor multiply(ArthurColor one, ArthurNumber two) {
	int newR=Math.max(one.r*two.val, 255);
	int newG=Math.max(one.g*two.val, 255);
	int newB=Math.max(one.b*two.val, 255);
	int newA=Math.max(one.a*two.val, 255);
	return new ArthurColor(newR, newG, newB, newA);

}

public static ArthurColor multiply(ArthurColor one, ArthurString two) {

}

public static ArthurColor divide(ArthurColor one, ArthurColor two) {
	int newR=one.r/Math.max(two.r,1);
	int newG=one.g/Math.max(two.g,1);
	int newB=one.b/Math.max(two.b,1);
	int newA=Math.max(one.a, two.a);
	return new ArthurColor(newR, newG, newB, newA);
}

public static ArthurColor divide(ArthurColor one, ArthurImage two) {

}

public static ArthurColor divide(ArthurColor one, ArthurVideo two) {

}

public static ArthurColor divide(ArthurColor one, ArthurSound two) {

}

public static ArthurColor divide(ArthurColor one, ArthurNumber two) {
	int newR=one.r/Math.max(two.val,1);
	int newG=one.g/Math.max(two.val,1);
	int newB=one.b/Math.max(two.val,1);
	int newA=Math.min(one.a*two.val, 255);
	return new ArthurColor(newR, newG, newB, newA);

}

public static ArthurColor divide(ArthurColor one, ArthurString two) {

}

}
