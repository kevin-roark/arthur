package arthur.frontend;

public class Arg extends Token {

  public String num;

  public Arg(String n) {
    super(Tokens.ARG);
    this.num = n.substring(1); // remove the '@'
  }

  public Arg(String n, int line) {
    super(Tokens.ARG, line);
    this.num = n.substring(1);
  }

  public String toString() {
    return "Arg numbered " + this.num;
  }
}
