import java.util.ArrayList;
public class nodetest {
	public static void main(String[] args) {
		ArrayList<ParseNode> kids0 = new ArrayList<ParseNode>();
		kids0.add(new ParseNode(new Token(1)));
		ArrayList<ParseNode> kids1 = new ArrayList<ParseNode>();
		ParseNode node1 = new ParseNode(new Number(30.0), kids0);
		ParseNode node2 = new ParseNode(new StringLit ("hi"));
		ParseNode node3 = new ParseNode(new Value("yo"));
		kids1.add(node1);
		kids1.add(node2);
		kids1.add(node3);
		ParseNode parent = new ParseNode(new Token(40), kids1);
		parent.toString(0);
	}
}