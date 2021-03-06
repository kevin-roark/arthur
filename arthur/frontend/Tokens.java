package arthur.frontend;

public class Tokens {

    public static final int DW = 0;
    public static final int IF = 1;
    public static final int ELF = 2;
    public static final int ELSE = 3;
    public static final int AND = 4;
    public static final int OR = 5;
    public static final int RETURN = 6;
    public static final int TRUE = 7;
    public static final int FALSE = 8;
    public static final int SEMI = 9;
    public static final int LPAREN = 10;
    public static final int RPAREN = 11;
    public static final int LCURLY = 12;
    public static final int RCURLY = 13;
    public static final int LBRACK = 14;
    public static final int RBRACK = 15;
    // public static final int LVEC = 16;
    // public static final int RVEC = 17;
    public static final int DOT = 18;
    public static final int EQ = 19;
    public static final int EQX2 = 190;
    public static final int NOT = 20;
    public static final int LT = 21;
    public static final int LTE = 210;
    public static final int GT = 22;
    public static final int GTE = 23;
    public static final int COMMA = 24;
    public static final int PLUS = 25;
    public static final int MINUS = 26;
    public static final int TIMES = 27;
    public static final int DIV = 28;
    public static final int MOD = 29;
    public static final int EXP = 30;
    public static final int ARROW = 31;
    public static final int FUNCTION = 32;
    public static final int VAR = 33;
    public static final int ID = 330;
    public static final int COLOR = 34;
    public static final int NUMBER = 35;
    public static final int STRINGLIT = 350;
    //public static final int NEWLINE = 37;
    //public static final int WHITESPACE = 38;
    public static final int EOF = 39;
    public static final int VALUE = 40;
    public static final int TYPE = 41;
    public static final int ARG = 42;

    public static String getName(int token) {
        switch (token) {
            case DW: return "dw";
            case IF: return "if";
            case ELF: return "elf";
            case ELSE: return "else";
            case AND: return "and";
            case OR: return "or";
            case RETURN: return "return";
            case TRUE: return "true";
            case FALSE: return "false";
            case SEMI: return "semi-colon";
            case LPAREN: return "left parentheses";
            case RPAREN: return "right parentheses";
            case LCURLY: return "left curly";
            case RCURLY: return "right curly";
            case LBRACK: return "left bracket";
            case RBRACK: return "right bracket";
            case DOT: return "dot";
            case EQ: return "single equals";
            case EQX2: return "double equals";
            case NOT: return "not";
            case LT: return "less than";
            case LTE: return "less than / equal to";
            case GT: return "greater than";
            case GTE: return "greater than / equal to";
            case COMMA: return "comma";
            case PLUS: return "plus";
            case MINUS: return "minus";
            case TIMES: return "times";
            case DIV: return "divide";
            case MOD: return "mod";
            case EXP: return "exponent";
            case ARROW: return "arrow";
            case FUNCTION: return "function";
            case VAR: return "variable";
            case ID: return "identifier";
            case COLOR: return "color literal";
            case NUMBER: return "number literal";
            case STRINGLIT: return "string literal";
            case EOF: return "EOF";
            case VALUE: return "value fallback";
            case TYPE: return "type";
            case ARG: return "arg";
            default: return "unknown token";
        }
    }
}
