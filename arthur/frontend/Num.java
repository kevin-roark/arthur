package arthur.frontend;

public class Num extends Token {

    public Double val;

    public Num(Double val) {
        super(Tokens.NUMBER);
        this.val = val;
    }

    public Num(Double val, int line) {
        super(Tokens.NUMBER, line);
        this.val = val;
    }

    public String toString() {
    	return "Number token with value: " + this.val;
    }
}
