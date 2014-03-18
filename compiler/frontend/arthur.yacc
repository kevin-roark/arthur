
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
    : IF LPAREN expr RPAREN stmt elf else           {   ParseNode ifmaster = new ParseNode("if");
                                                        ParseNode iffer = new ParseNode("iffer", ifmaster);
                                                        ParseNode expr = (ParseNode) $3.obj;
                                                        ParseNode stmt = (ParseNode) $5.obj;
                                                        iffer.addChild(expr);
                                                        iffer.addChild(stmt);
                                                        ifmaster.addChild(iffer);
                                                        ParseNode elfer = (ParseNode) $6.obj;
                                                        if (elfer != null)
                                                            ifmaster.addChild(elfer);
                                                        ParseNode elser = (ParseNode) $7.obj;
                                                        if (elser != null)
                                                            ifmaster.addChild(elser);
                                                        $$ = new ParserVal(ifmaster);
                                                    }
    | IF LPAREN expr RPAREN stmt                    {   ParseNode ifmaster = new ParseNode("if");
                                                        ParseNode expr = (ParseNode) $3.obj;
                                                        ParseNode stmt = (ParseNode) $5.obj;
                                                        ifmaster.addChild(expr);
                                                        ifmaster.addChild(stmt);
                                                        $$ = new ParserVal(ifmaster);
                                                    }
    | IF LPAREN expr RPAREN stmt else               {   ParseNode ifmaster = new ParseNode("if");
                                                        ParseNode iffer = new ParseNode("iffer", ifmaster);
                                                        ParseNode expr = (ParseNode) $3.obj;
                                                        ParseNode stmt = (ParseNode) $5.obj;
                                                        iffer.addChild(expr);
                                                        iffer.addChild(stmt);
                                                        ifmaster.addChild(iffer);
                                                        ParseNode elser = (ParseNode) $6.obj;
                                                        if (elser != null)
                                                            ifmaster.addChild(elser);
                                                        $$ = new ParserVal(ifmaster);
                                                    }
    ;

elf
    : if_stmt                                       { $$ = (ParseNode) $1.obj; }
    |                                               { $$ = new ParserVal(null); }
    ;


// elfzam
//     : elf ELF LPAREN expr RPAREN stmt               {   ParseNode elfmaster = new ParseNode("elf", current);
//                                                         current.addChild(elfmaster); 
//                                                         ParseNode recelf = (ParseNode) $1.obj; 


//                                                         }
//     |                                               { $$ = new ParserVal(null); }
//     ;

else
    : ELSE stmt                                     { $$ = $2; }
    |                                               { $$ = new ParserVal(null); }
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
    : bool_expr                                     { $$ = (ParseNode) $1.obj; }
    | num_expr                                      { $$ = (ParseNode) $1.obj; }
    | val                                           { $$ = (ParseNode) $1.obj; }
    ;

bool_expr
    : TRUE                                          { $$ = $1; }
    | FALSE                                         { $$ = $1; }
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
