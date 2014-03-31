
public class Function extends Token {

    public String name;
    public String params;
    public String returnType;

    public Function(String name, String returnType) {
        super(Tokens.FUNCTION);
        this.name = name;
        this.params = "";
        this.returnType = returnType;
    }

    public Function(String name, String returnType, int line) {
        super(Tokens.FUNCTION, line);
        this.name = name;
        this.params = "";
        this.returnType = returnType;
    }

    public Function(String name, String params, String returnType) {
        super(Tokens.FUNCTION);
        this.name = name;
        this.params = params;
        this.returnType = returnType;
    }

    public Function(String name, String params, String returnType, int line) {
        super(Tokens.FUNCTION, line);
        this.name = name;
        this.params = params;
        this.returnType = returnType;
    }

    public String toString() {
    	String s = "Function | name: " + this.name;
        if (this.params.length() > 0) {
            s += " | params: " + this.params;
        }
    	s += " | return type: " + this.returnType;
    	return s;
    }
}
