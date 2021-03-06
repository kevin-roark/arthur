package arthur.backend.media;

import arthur.backend.builtins.java.JavaBuiltins;

/**
* Contains a suite of static methods to perform math operations involving
* colors.
*/
public class JavaColorMath {

public static final double maxFreq = 10000.0;
public static final double minFreq = 25.0;

public static ArthurColor add(ArthurColor one, ArthurColor two) {
	ArthurNumber r = new ArthurNumber(Math.min(one.r.val + two.r.val, 255));
	ArthurNumber g = new ArthurNumber(Math.min(one.g.val + two.g.val, 255));
	ArthurNumber b = new ArthurNumber(Math.min(one.b.val + two.b.val, 255));
	ArthurNumber a = new ArthurNumber(Math.min(one.a.val + two.a.val, 1.0));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor add(ArthurColor one, ArthurImage two) {
	ArthurColor avg = two.getAverageColor();
	return add(one, avg);
}

public static ArthurColor add(ArthurColor one, ArthurVideo two) {
	ArthurColor vidColor = two.toColor();
	return add(one, vidColor);
}

// brightens color if tinny sound, darkens if bassy
public static ArthurColor add(ArthurColor one, ArthurSound two) {
	double freq = JavaSoundMath.getFrequency(two);
	double ratio = 255 * freq / (maxFreq - minFreq);
	if (ratio < 127.5)
		return minus(one, new ArthurNumber(ratio));
	else
		return add(one, new ArthurNumber(ratio));
}

public static ArthurColor add(ArthurColor one, ArthurNumber two) {
	ArthurNumber r = new ArthurNumber(Math.min(one.r.val + two.val, 255));
	ArthurNumber g = new ArthurNumber(Math.min(one.g.val + two.val, 255));
	ArthurNumber b = new ArthurNumber(Math.min(one.b.val + two.val, 255));
	ArthurNumber a = one.a;
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor add(ArthurColor one, ArthurString two) {
	ArthurColor color = two.toColor();
	return add(one, color);
}

public static ArthurColor minus(ArthurColor one, ArthurColor two) {
	ArthurNumber r = new ArthurNumber(Math.max(one.r.val - two.r.val, 0));
	ArthurNumber g = new ArthurNumber(Math.max(one.g.val - two.g.val, 0));
	ArthurNumber b = new ArthurNumber(Math.max(one.b.val - two.b.val, 0));
	ArthurNumber a = new ArthurNumber(Math.max(one.a.val - two.a.val, 0));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor minus(ArthurColor one, ArthurImage two) {
	ArthurColor avg = two.getAverageColor();
	return minus(one, avg);
}

public static ArthurColor minus(ArthurColor one, ArthurVideo two) {
	ArthurColor vidColor = two.toColor();
	return add(one, vidColor.opposite());
}

// darkens color if tinny sound, brightens if bassy
public static ArthurColor minus(ArthurColor one, ArthurSound two) {
	double freq = JavaSoundMath.getFrequency(two);
	double ratio = 255 * freq / (maxFreq - minFreq);
	if (ratio < 127.5)
		return add(one, new ArthurNumber(ratio));
	else
		return minus(one, new ArthurNumber(ratio));
}

public static ArthurColor minus(ArthurColor one, ArthurNumber two) {
	ArthurNumber r = new ArthurNumber(Math.max(one.r.val - two.val, 0));
	ArthurNumber g = new ArthurNumber(Math.max(one.g.val - two.val, 0));
	ArthurNumber b = new ArthurNumber(Math.max(one.b.val - two.val, 0));
	ArthurNumber a = one.a;
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor minus(ArthurColor one, ArthurString two) {
	ArthurColor color = two.toColor();
	return minus(one, color);
}

public static ArthurColor multiply(ArthurColor one, ArthurColor two) {
	ArthurNumber r = new ArthurNumber((one.r.val + two.r.val) / 2);
	ArthurNumber g = new ArthurNumber((one.g.val + two.g.val) / 2);
	ArthurNumber b = new ArthurNumber((one.b.val + two.b.val) / 2);
	ArthurNumber a = new ArthurNumber(Math.max(one.a.val, two.a.val));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor multiply(ArthurColor one, ArthurNumber two) {
	double abs = Math.abs(two.val);
	ArthurNumber r = new ArthurNumber(Math.max(one.r.val * abs, 255));
	ArthurNumber g = new ArthurNumber(Math.max(one.g.val * abs, 255));
	ArthurNumber b = new ArthurNumber(Math.max(one.b.val * abs, 255));
	ArthurNumber a = new ArthurNumber(Math.max(one.a.val * abs, 1.0));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor divide(ArthurColor one, ArthurColor two) {
	ArthurNumber r = new ArthurNumber(one.r.val / Math.max(two.r.val, 1));
	ArthurNumber g = new ArthurNumber(one.g.val / Math.max(two.g.val, 1));
	ArthurNumber b = new ArthurNumber(one.b.val / Math.max(two.b.val, 1));
	ArthurNumber a = new ArthurNumber(Math.max(one.a.val, two.a.val));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor divide(ArthurColor one, ArthurNumber two) {
	double abs = Math.abs(two.val);
	ArthurNumber r = new ArthurNumber(one.r.val / Math.max(abs, 1));
	ArthurNumber g = new ArthurNumber(one.g.val / Math.max(abs, 1));
	ArthurNumber b = new ArthurNumber(one.b.val / Math.max(abs, 1));
	ArthurNumber a = new ArthurNumber(Math.min(one.a.val * abs, 1.0));
	return new ArthurColor(r, g, b, a);
}

}
