package arthur.backend.media;

import arthur.backend.builtins.java.JavaBuiltins;

/**
* Contains a suite of static methods to perform math operations involving
* colors.
*/
public class JavaColorMath {

public static ArthurColor add(ArthurColor one, ArthurColor two) {
	ArthurNumber r = new ArthurNumber(Math.min(one.r.val + two.r.val, 255));
	ArthurNumber g = new ArthurNumber(Math.min(one.g.val + two.g.val, 255));
	ArthurNumber b = new ArthurNumber(Math.min(one.b.val + two.b.val, 255));
	ArthurNumber a = new ArthurNumber(Math.min(one.a.val + two.a.val, 1.0));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor add(ArthurColor one, ArthurImage two) {
	ArthurColor avg=two.getAverageColor();

	return multiply(one, avg);

}

public static ArthurColor add(ArthurColor one, ArthurVideo two) {
	return null;
}

public static ArthurColor add(ArthurColor one, ArthurSound two) {
	return null;
}

public static ArthurColor add(ArthurColor one, ArthurNumber two) {
	ArthurNumber r = new ArthurNumber(Math.min(one.r.val + two.val, 255));
	ArthurNumber g = new ArthurNumber(Math.min(one.g.val + two.val, 255));
	ArthurNumber b = new ArthurNumber(Math.min(one.b.val + two.val, 255));
	ArthurNumber a = one.a;
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor add(ArthurColor one, ArthurString two) {
	String name = two.toString();
	for (int i = 0; i < JavaBuiltins.colors().size(); i++){
		String c = JavaBuiltins.colors().get(i);
		if(name.toUpperCase().contains(c)) {
			ArthurColor color = JavaBuiltins.colorMap().get(c);
			return add(one, color);
		}
	}
	return one;
}

public static ArthurColor minus(ArthurColor one, ArthurColor two) {
	ArthurNumber r = new ArthurNumber(Math.max(one.r.val - two.r.val, 0));
	ArthurNumber g = new ArthurNumber(Math.max(one.g.val - two.g.val, 0));
	ArthurNumber b = new ArthurNumber(Math.max(one.b.val - two.b.val, 0));
	ArthurNumber a = new ArthurNumber(Math.max(one.a.val - two.a.val, 0));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor minus(ArthurColor one, ArthurImage two) {
	ArthurColor avg=two.getAverageColor();

	return divide(one, avg);
}

public static ArthurColor minus(ArthurColor one, ArthurVideo two) {
	return null;
}

public static ArthurColor minus(ArthurColor one, ArthurSound two) {
	return null;
}

public static ArthurColor minus(ArthurColor one, ArthurNumber two) {
	ArthurNumber r = new ArthurNumber(Math.max(one.r.val - two.val, 0));
	ArthurNumber g = new ArthurNumber(Math.max(one.g.val - two.val, 0));
	ArthurNumber b = new ArthurNumber(Math.max(one.b.val - two.val, 0));
	ArthurNumber a = one.a;
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor minus(ArthurColor one, ArthurString two) {
	String name = two.toString();
	for (int i = 0; i < JavaBuiltins.colors().size(); i++){
		String c = JavaBuiltins.colors().get(i);
		if(name.toUpperCase().contains(c)) {
			ArthurColor color = JavaBuiltins.colorMap().get(c);
			return minus(one, color);
		}
	}
	return one;
}

public static ArthurColor multiply(ArthurColor one, ArthurColor two) {
	ArthurNumber r = new ArthurNumber((one.r.val + two.r.val) / 2);
	ArthurNumber g = new ArthurNumber((one.g.val + two.g.val) / 2);
	ArthurNumber b = new ArthurNumber((one.b.val + two.b.val) / 2);
	ArthurNumber a = new ArthurNumber(Math.max(one.a.val, two.a.val));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor multiply(ArthurColor one, ArthurImage two) {
	return null;
}

public static ArthurColor multiply(ArthurColor one, ArthurVideo two) {
	return null;
}

public static ArthurColor multiply(ArthurColor one, ArthurSound two) {
	return null;
}

public static ArthurColor multiply(ArthurColor one, ArthurNumber two) {
	ArthurNumber r = new ArthurNumber(Math.max(one.r.val * two.val, 255));
	ArthurNumber g = new ArthurNumber(Math.max(one.g.val * two.val, 255));
	ArthurNumber b = new ArthurNumber(Math.max(one.b.val * two.val, 255));
	ArthurNumber a = new ArthurNumber(Math.max(one.a.val * two.val, 1.0));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor multiply(ArthurColor one, ArthurString two) {
	return null;
}

public static ArthurColor divide(ArthurColor one, ArthurColor two) {
	ArthurNumber r = new ArthurNumber(one.r.val / Math.max(two.r.val, 1));
	ArthurNumber g = new ArthurNumber(one.g.val / Math.max(two.g.val, 1));
	ArthurNumber b = new ArthurNumber(one.b.val / Math.max(two.b.val, 1));
	ArthurNumber a = new ArthurNumber(Math.max(one.a.val, two.a.val));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor divide(ArthurColor one, ArthurImage two) {
	return null;
}

public static ArthurColor divide(ArthurColor one, ArthurVideo two) {
	return null;
}

public static ArthurColor divide(ArthurColor one, ArthurSound two) {
	return null;
}

public static ArthurColor divide(ArthurColor one, ArthurNumber two) {
	ArthurNumber r = new ArthurNumber(one.r.val / Math.max(two.val, 1));
	ArthurNumber g = new ArthurNumber(one.g.val / Math.max(two.val, 1));
	ArthurNumber b = new ArthurNumber(one.b.val / Math.max(two.val, 1));
	ArthurNumber a = new ArthurNumber(Math.min(one.a.val * two.val, 1.0));
	return new ArthurColor(r, g, b, a);
}

public static ArthurColor divide(ArthurColor one, ArthurString two) {
	return null;
}

}
