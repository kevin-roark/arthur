/* Kevin Roark - ker2143 */

/* PLT Homework 1, Problem 2 - A Flex program to turn my phone number into words 
 * My phone number ends with 8538.
 */

/** USER CODE SECTION */

%%

/** OPTIONS AND DECLARATIONS SECTION */

%public
%class Lexer /* name the class 'Lexer' */
%type String

%unicode     /* work with unicode characters */
%line        /* access current line via yyline */
%column      /* access current column via yycolumn */

%{
  StringBuffer string = new StringBuffer();
%}

/* white space definitions */
LineTerminator = \r|\n|\r\n

/* interesting definitions */
eight_letters = [t|u|v]
five_letters = [j|k|l]
three_letters = [d|e|f]
valid_letters = {eight_letters} | {five_letters} | {three_letters}

/* state definitions */
%state WORD
%state VOIDNESS

%%

/** LEXICAL RULES SECTION */

<YYINITIAL> {
    {valid_letters}         { string.setLength(0); string.append(yytext()); yybegin(WORD); }

    /* anything else */
    [^]                     { yybegin(VOIDNESS); }
}

<WORD> {
    {valid_letters}         { string.append(yytext()); }

    {LineTerminator}        { yybegin(YYINITIAL); return string.toString(); }

    /* anything else means not valid word*/
    [^]                     { yybegin(VOIDNESS); }
}

<VOIDNESS> {
    {LineTerminator}        { yybegin(YYINITIAL); }

    /* anything else do nothing */
    [^]                     { /* do nothing */ }
}