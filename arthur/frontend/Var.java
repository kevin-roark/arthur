package arthur.frontend;

import java.util.HashSet;
import java.util.Set;

public class Var extends Token {

    public static final int NUM = 0;
    public static final int STRING = 1;
    public static final int COLOR = 2;
    public static final int SOUND = 3;
    public static final int VIDEO = 4;
    public static final int IMAGE = 5;
    public static final int MEDIA = 6;
    public static final int BOOL = 7;

    public String id;
    public int type;
    public Object val;

    public Var(String id, String t) {
        super(Tokens.VAR);
        this.id = id;
        this.type = Var.getType(t);
        this.val = null;
    }

    public Var(String id, String t, int line) {
        super(Tokens.VAR, line);
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
        } else if (t.equals("bool")) {
            return BOOL;
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

    public static String getName(int type) {
        switch (type) {
            case NUM: return "num";
            case STRING: return "string";
            case COLOR: return "color";
            case BOOL: return "bool";
            case SOUND: return "sound";
            case VIDEO: return "video";
            case IMAGE: return "image";
            default: return "unknown type";
        }
    }

    public String typeName() {
        String type = getName(this.type);
        return type;
    }

    public String toString() {
        String s = "Variable | type: " + getName(this.type);
        s += " | id: " + this.id;
        if (this.val != null) {
            s += " | val: " + this.val;
        }
        return s;
    }

    public Set getVarProperties() {
      return getProperties(this.type);
    }

    public static Set getProperties(int t) {
      HashSet<String> set = new HashSet<String>();
      switch (t) {
        case NUM:
          return set;
        case STRING:
          set.add("color"); set.add("size"); set.add("wrap");
          return set;
        case COLOR:
          set.add("r"); set.add("g"); set.add("b"); set.add("a");
          return set;
        case BOOL:
          return set;
        case SOUND:
          return set;
        case VIDEO:
          return set;
        case IMAGE:
          set.add("width"); set.add("height"); set.add("pixel");
          set.add("murk"); set.add("frame");
          return set;
        default:
          return set;
      }
    }
}
