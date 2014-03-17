
public class Function extends Token {

    public String name;
    public String params;
    public String returnType;

    public Function(String name, String params, String returnType) {
        super(Tokens.FUNCTION);
        this.name = name;
        this.params = params;
        this.returnType = returnType;
    }
}
