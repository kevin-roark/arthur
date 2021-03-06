package arthur.frontend;

public class Color extends Token {

    public Integer r;
    public Integer g;
    public Integer b;
    public Double a;

    public Color(int r, int g, int b) {
        super(Tokens.COLOR);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0;
    }

    public Color(int r, int g, int b, double a) {
        super(Tokens.COLOR);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public String toString() {
        String s = "Color | r: " + this.r;
        s += " | g: " + this.g;
        s += " | b: " + this.b;
        s += " | a: " + this.a;
        return s;
    }
}
