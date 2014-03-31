
public class Boolean extends Token {

    public boolean bool;

    public Boolean(boolean bool) {
        super(Tokens.BOOL);
        this.bool = bool;
    }

    public Boolean(boolean bool, int line) {
        super(Tokens.BOOL, line);
        this.bool = bool;
    }
}
