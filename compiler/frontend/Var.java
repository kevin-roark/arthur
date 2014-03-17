
public class Var extends Token {

    public static final int NUM = 0;
    public static final int STRING = 1;
    public static final int COLOR = 2;
    public static final int SOUND = 3;
    public static final int VIDEO = 4;
    public static final int IMAGE = 5;

    public String id;
    public int type;
    public Object val;

    public Var(String id, int type) {
        super(Tokens.VAR);
        this.id = id;
        this.type = type;
    }

    public Var(String id, int type, Object val) {
        super(Tokens.VAR);
        this.id = id;
        this.type = type;
        this.val = val;
    }
}
