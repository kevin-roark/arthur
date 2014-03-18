import java.util.ArrayList;

public class ParseNode {
    
    public String val;
    public ParseNode parent;
    public ArrayList<ParseNode> children;

    public ParseNode() {
        val = null;
        parent = null;
        children = null;
    }

    public ParseNode(String v, ParseNode p) {
        val = v;
        parent = p;
        children = null;
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
    }

    public void addChild(ParseNode child) {
        children.add(child);
    }

    public void setParent(ParseNode p) {
        parent = p;
    }

    public void toString(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("   ");
        }
        System.out.println("\\_" + this.val);
        if (children != null) {
            for (ParseNode child : children) {
                child.toString(depth + 1);
            }
        }
    }

}