
public class FunCall extends Token {
    
    public Function function;
    public String params;

    public FunCall(Function f, String p) {
        this.function = f;
        this.params = p;
    }
}
