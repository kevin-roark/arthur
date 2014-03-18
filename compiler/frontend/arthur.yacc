
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
%token FUNCTION VAR
%token COLOR NUMBER STRINGLIT TRUE FALSE
%token NEWLINE WHITESPACE EOF

%%

/* grammar rules */

param_list
    :
    | hard_param_list;
    ;

hard_param_list
    : VAR                                           { $1 }
    | hard_param_list COMMA VAR
    ;

dw_stmt
    : DW LPAREN expr RPAREN stmt                    {   ArrayList<ParseNode> = new ArrayList<ParseNode>();

                                                        ParseNode p = new ParseNode() }
    ;

if_stmt
    : IF LPAREN expr RPAREN stmt elf else
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
    : FUNCTION LBRACK stmt RBRACK
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
    return tok.tokenType;
}

void doParsing(Reader in) {
    lexer = new Lexer(in);
    yyparse();
}