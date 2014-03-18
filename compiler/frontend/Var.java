
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

    public Var(String id, String t) {
        super(Tokens.VAR);
        this.id = id;
        this.type = Var.getType(t);
        this.val = null;
    }

    public static int getType(String t) {
        if (t.equals("num")) {
            return NUM;
        } else if (t.equals("string")) {
            return STRING;
        } else if (t.equals("color")) {
            return COLOR;
        } else if (t.equals("Sound")) {
            return SOUND;
        } else if (t.equals("Video")) {
            return VIDEO;
        } else if (t.equals("Image")) {
            return IMAGE;
        } else {
            return -1;
        }
    }

    public String toString() {
        String s = "Var token | type: " + this.type;
        s += " | id: " + this.id;
        if (this.val != null) {
            s += " | val: " + this.val;
        }
        return s;
    }
}