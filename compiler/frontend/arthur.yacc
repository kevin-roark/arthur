
/* imports */
 %{ 
    import java.io.Reader;
    import java.io.IOException;
    import java.lang.Math;
 %}

/* YACC declarations */

%token DW IF ELF ELSE
%token AND OR NOT RETURN
%token DOT COMMA SEMI ARROW
%token LPAREN RPAREN LCURLY RCURLY LBRACK RBRACK
%token EQ EQ2X LT LTE RT RTE
%token PLUS MINUS TIMES DIV MOD EXP
%token FUNCTION VAR VALUE ID
%token COLOR NUMBER STRINGLIT TRUE FALSE
%token EOF
%token UNKNOWN

%%

/* grammar rules */

param_list
    :
    | hard_param_list;              {

                                    }
    ;

hard_param_list
    : VAR                           {
                                        ParseNode params = new ParseNode("parameters");
                                        Var v = (Var) $1;
                                        ParseNode var = new ParseNode("variable", params);
                                        var.addChild(new ParseNode(v.type, var));
                                        var.addChild(new ParseNode(v.id, var));
                                        params.addChild(var);
                                        $$ = params;
                                    }
    | hard_param_list COMMA VAR     {

                                    }
    ;

dw_stmt
    : DW LPAREN expr RPAREN stmt                    {   

                                                    }
    ;

if_stmt
    : IF LPAREN expr RPAREN stmt elf else           /* {   ParseNode if_stmt = new ParseNode("if statement", current);
                                                        current.addChild(if_stmt);
                                                        current = if_stmt;
                                                        ParseNode iffer = new ParseNode("if", current);
                                                        current.addChild(iffer);
                                                        current = iffer;
                                                        ParseNode lparen = new ParseNode("(", current);
                                                        current.addChild(lparen);
                                                        ParseNode expr = new ParseNode("expr", current);
                                                        current.addChild(expr);
                                                        ParseNode rparen = new ParseNode("RPAREN", current);
                                                        current.addChild(iffer);
                                                        ParseNode stmt = new ParseNode("stmt", current);
                                                        current.addChild(iffer);
                                                        ParseNode elf = new ParseNode("elf", current);
                                                        current.addChild(iffer);
                                                        ParseNode elser = new ParseNode("else", current);
                                                        current.addChild(iffer);
                                                        $$ = if_stmt;
                                                    } */





    ;

elf
    : elf ELF LPAREN expr RPAREN stmt
    |
    ;

else
    : ELSE stmt
    |
    ;

func_def
    : FUNCTION param_list RPAREN    {
                                        ParseNode fundef = new ParseNode("Function definition", current);
                                        current.addChild(fundef);
                                        current = fundef;
                                        Function f = (Function) $1.obj;
                                        ParseNode params = (ParseNode) $2.obj;

                                        current.addChild(new ParseNode(f.returnType));
                                                
                                    }
    ;

stmt
    : SEMI
    | expr stmt
    | stmt stmt
    | if_stmt stmt
    | dw_stmt stmt
    |
    ;

expr
    : bool_expr
    | num_expr
    | val
    ;

bool_expr
    : TRUE
    | FALSE
    | bool_expr AND bool_expr
    | bool_expr OR bool_expr
    | NOT bool_expr
    ;

num_expr
    : NUMBER                                        { $$ = $1; }
    | num_expr LT num_expr                          { $$ = $1 < $3; }
    | num_expr LTE num_expr                         { $$ = $1 <= $3; }
    | num_expr GT num_expr                          { $$ = $1 > $3; }
    | num_expr GTE num_expr                         { $$ = $1 >= $3; }
    | num_expr EQ2X num_expr                        { $$ = $1 == $3; }
    ;

val
    : VAR
    | COLOR
    | NUMBER
    | STRINGLIT
    | TRUE
    | FALSE
    | LPAREN val RPAREN
    | val PLUS val                                  { $$ = $1 + $3; }
    | val MINUS val                                 { $$ = $1 - $3; }
    | val TIMES val                                 { $$ = $1 * $3; }
    | val DIV val                                   { $$ = $1 / $3; }
    | val MOD val                                   { $$ = $1 % $3; }
    | val EXP val                                   { $$ = pow($1, $3); }
    ;

input: /* empty string */
    | input line
    ;

line:   '\n'
|       expression '\n'       {
                                Boolean result = (Boolean) $1.obj;
                                expNo++;
                                System.out.println("Expression " + expNo + " result: "  + result);
                              }
    ;

expression:     expression 'N' expression       { boolean one = (Boolean) $1.obj;
                                                  boolean two = (Boolean) $3.obj;
                                                  $$ = new ParserVal(new Boolean(!(one && two))); 
                                                }
        |       '(' expression ')'              { $$ = $2; }
        |       BOOL                            { $$ = $1; }
        ;

%%
ParseNode root = new ParseNode("Program");
ParseNode current = root;

Lexer lexer;

void yyerror(String s) {
    System.out.println("Error: " + s);
}

int yylex() {
    Token tok;

    try {
        tok = lexer.yylex();
    } catch(IOException e) {
        System.out.println("got this exception getting lex: " + e);
        return '\n';
    }

    /* might need to do stuff with token and symbol table, etc */
    yylval = new ParserVal(tok);

    /* need to do this at end */
    return tokenMap(tok.tokenType);
}

int tokenMap(int tokenType) {
    switch(tokenType) {
        case Tokens.DW: return DW;
        case Tokens.IF: return IF;
        case Tokens.ELF: return ELF;
        case Tokens.ELSE: return ELSE;
        case Tokens.AND: return AND;
        case Tokens.OR: return OR;
        case Tokens.RETURN: return RETURN;
        case Tokens.TRUE: return TRUE;
        case Tokens.FALSE: return FALSE;
        case Tokens.SEMI: return SEMI;
        case Tokens.LPAREN: return LPAREN;
        case Tokens.RPAREN: return RPAREN;
        case Tokens.LCURLY: return LCURLY;
        case Tokens.RCURLY: return RCURLY;
        case Tokens.LBRACK: return LBRACK;
        case Tokens.RBRACK: return RBRACK;
        case Tokens.DOT: return DOT;
        case Tokens.EQ: return EQ;
        case Tokens.EQX2: return EQ2X;
        case Tokens.NOT: return NOT;
        case Tokens.LT: return LT;
        case Tokens.LTE: return LTE;
        case Tokens.GT: return GT;
        case Tokens.GTE: return GTE;
        case Tokens.COMMA: return COMMA;
        case Tokens.PLUS: return PLUS;
        case Tokens.MINUS: return MINUS;
        case Tokens.TIMES: return TIMES;
        case Tokens.DIV: return DIV;
        case Tokens.MOD: return MOD;
        case Tokens.EXP: return EXP;
        case Tokens.ARROW: return ARROW;
        case Tokens.FUNCTION: return FUNCTION;
        case Tokens.VAR: return VAR;
        case Tokens.ID: return ID;
        case Tokens.COLOR: return COLOR;
        case Tokens.NUMBER: return NUMBER;
        case Tokens.STRINGLIT: return STRINGLIT;
        case Tokens.EOF: return EOF;
        case Tokens.VALUE: return VALUE;
        default: return UNKNOWN;
    }
}

void doParsing(Reader in) {
    lexer = new Lexer(in);
    yyparse();
}
