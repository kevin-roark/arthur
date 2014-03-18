
public class Number extends Token {

    public Double val;

    public Number(Double val) {
        super(Tokens.NUMBER);
        this.val = val;
    }

    public String toString() {
    	return "Number token with value: " + this.val;
    }
}