/* Kevin Roark - ker2143 */

/* PLT Homework 1, Problem 4 - A Yacc program to make nand
 * expression tokens.
 */

/* imports */
 %{ 
    import java.io.Reader;
    import java.io.IOException;
 %}

 /* YACC declarations */

%token BOOL 
%left 'N' /* nand */

%%

/* grammar rules */

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

Lexer calcLexer;
int expNo;

void yyerror(String s) {
    System.out.println("Error: " + s);
}

int yylex() {
    char lexVal;

    try {
        lexVal = calcLexer.yylex();
    } catch(IOException e) {
        System.out.println("got this exception getting lex: " + e);
        return '\n';
    }

    if (lexVal == '0') {
        return -1;
    }

    if (lexVal == Lexer.FALSE) {
        yylval = new ParserVal(Boolean.FALSE);
        return BOOL;
    } else if (lexVal == Lexer.TRUE) {
        yylval = new ParserVal(Boolean.TRUE);
        return BOOL;
    } else if (lexVal == Lexer.NAND) {
        return 'N';
    } else if (lexVal == Lexer.LEFTP) {
        return '(';
    } else if (lexVal == Lexer.RIGHTP) {
        return ')';
    } else if (lexVal == Lexer.NEWLINE) {
        return '\n';
    }
    /* got nothing good .. */
    return -1;
}

void doParsing(Reader in) {
    calcLexer = new Lexer(in);
    expNo = 0;
    yyparse();
}