
public class Value extends Token {
    
    public String val;

    public Value(String v) {
    	super(Tokens.VALUE);
        this.val = v;
    }

    public String toString() {
    	return "Value with val: " + this.val;
    }
}
