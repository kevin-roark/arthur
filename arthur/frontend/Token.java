package arthur.frontend;

public class Token {

    public int tokenType;
    public int line;

    public Token(int tt) {
        this.tokenType = tt;
        this.line = -1;
    }

    public Token(int tt, int line) {
        this.tokenType = tt;
        this.line = line + 1;
    }

    public String toString() {
        return "Token | " + Tokens.getName(this.tokenType);
    }
}
