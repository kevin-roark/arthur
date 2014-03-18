
public class Identifier extends Token {
	
	public String name;

	public Identifier(String n) {
		super(Tokens.ID);
		this.name = n;
	}

	public String toString() {
		return "Identifier with name: " + this.name;
	}
}