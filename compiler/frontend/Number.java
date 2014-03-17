
public class Number extends Token {

    public Double val;

    public Number(Double val) {
        super(Tokens.NUMBER);
        this.val = val;
    }
}
