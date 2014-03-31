package arthur.frontend;

public class StringLit extends Token {

    public String val;

    public StringLit(String val) {
        super(Tokens.STRINGLIT);
        this.val = val;
    }

    public StringLit(String val, int line) {
        super(Tokens.STRINGLIT, line);
        this.val = val;
    }

    public String toString() {
    	return "String literal with val: " + val;
    }
}
