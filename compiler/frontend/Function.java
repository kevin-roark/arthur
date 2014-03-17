
public class Function extends Token {

    public String name;

    public Function(String name) {
        super(Tokens.FUNCTION);
        this.name = name;
    }
}
