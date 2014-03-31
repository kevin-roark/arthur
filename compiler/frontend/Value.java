
public class Value extends Token {

    public String val;

    public Value(String v) {
    	super(Tokens.VALUE);
        this.val = v;
    }

    public Value(String v, int line) {
      super(Tokens.VALUE, line);
        this.val = v;
    }

    public String toString() {
    	return "Value with val: " + this.val;
    }
}
