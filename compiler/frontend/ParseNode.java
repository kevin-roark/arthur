import java.util.ArrayList;

public class ParseNode {
    
    public String val;
    public ParseNode parent;
    public ArrayList<ParseNode> children;

    public ParseNode(String v) {
        val = v;
        parent = null;
        children = new ArrayList<ParseNode>();
    }

    public ParseNode(String v, ParseNode p) {
        val = v;
        parent = p;
        children = new ArrayList<ParseNode>();
    }

    /*
    public ParseNode(Token tok, ParseNode left, ParseNode right) {
        val = tok;
        children = new ArrayList<ParseNode>();
        children.add(left);
        children.add(right);
    }
    */

    public ParseNode(String v, ParseNode p, ArrayList<ParseNode> chills) {
        val = v;
        parent = p;
        children = chills;
        System.out.println(this);
    }

    public void addChild(ParseNode child) {
        children.add(child);
        System.out.println(this);
    }

    public void setParent(ParseNode p) {
        parent = p;
    }

    public String depthString(int depth) {
        String s = "";
        for (int i = 0; i < depth; i++) {
            s += "   ";
        }
        s += "\\_" + this.val;
        if (children != null) {
            for (ParseNode child : children) {
                s += "\n" + child.depthString(depth + 1);
            }
        }
        return s;
    }

    public String toString() {
        return "Parse node:\n" + depthString(0);
    }

}