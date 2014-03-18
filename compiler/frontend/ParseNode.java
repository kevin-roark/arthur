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

    public String toString() {
        String s = "ParseNode with token value: " + this.val;
        if (children.size() > 0) {
            s += "Children: \n";
            for (ParseNode node : children) {
                s += node + "\n";
            }
        }
        return s;
    }

}