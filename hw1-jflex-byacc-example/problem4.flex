/* Kevin Roark - ker2143 */

/* PLT Homework 1, Problem 4 - A Flex program to make nand
 * expression tokens.
 */

/** USER CODE SECTION */

%%

/** OPTIONS AND DECLARATIONS SECTION */

%public
%class Lexer /* name the class 'Lexer' */
%type char /* it will return char */

%unicode     /* work with unicode characters */
%line        /* access current line via yyline */
%column      /* access current column via yycolumn */

%{
  StringBuffer string = new StringBuffer();

  public static final char FALSE = 'F';
  public static final char TRUE = 'T';
  public static final char NAND = 'N';
  public static final char NEWLINE = '\n';
  public static final char LEFTP = '(';
  public static final char RIGHTP = ')';
%}

%eofval{
 return '0'; 
%eofval}

/* white space definitions */
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

/* state definitions */
%state VOIDNESS

%%

/** LEXICAL RULES SECTION */

/* keywords */
<YYINITIAL> "true"          { return TRUE; }
<YYINITIAL> "false"         { return FALSE; }
<YYINITIAL> "nand"          { return NAND; }

<YYINITIAL> {
    {LineTerminator}        { return NEWLINE; }

    {WhiteSpace}            { /* do nothing */ }

    [(]                     { return LEFTP; }

    [)]                     { return RIGHTP; }

    /* anything else */
    [^]                     { throw new Error("Illegal character: " + yytext()); }
}

<VOIDNESS> {
    [^]                     { throw new Error("Shouldn't get anything post newline"); }
}