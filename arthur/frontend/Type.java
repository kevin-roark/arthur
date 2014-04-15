package arthur.frontend;

public class Type extends Token {

  public String name;

  public Type(String n) {
    super(Tokens.TYPE);
    this.name = n;
  }

  public Type(String n, int line) {
    super(Tokens.TYPE, line);
    this.name = n;
  }

  public String toString() {
    return "Type with name: " + this.name;
  }
}
