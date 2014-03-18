public class Token {

    public int tokenType;

    public Token(int tt) {
        this.tokenType = tt;
    }

    public String toString() {
    	return "Token type: " + this.tokenType;
    }
}
