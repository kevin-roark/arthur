import java.util.ArrayList;

public class ParseNode {
    
    public Token val;
    public ArrayList<ParseNode> children;

    public ParseNode(Token tok) {
        val = tok;
        children = null;
    }

    public ParseNode(Token tok, ParseNode left, ParseNode right) {
        val = tok;
        children = new ArrayList<ParseNode>();
        children.add(left);
        children.add(right);
    }

    public ParseNode(Token tok, ArrayList<ParseNode> chills) {
        val = tok;
        children = chills;
    }

    public void toString(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("   ");
        }
        System.out.println("\\_ParseNode with token value: " + this.val);
        if (children != null) {
            for (ParseNode child : children) {
                child.toString(depth + 1);
            }
        }
    }

}