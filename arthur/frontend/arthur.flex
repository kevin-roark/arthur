
/** USER CODE SECTION */

package arthur.frontend;

%%

/** OPTIONS AND DECLARATIONS SECTION */

%public
%class Lexer /* name the class 'Lexer' */
%type Token  /* it will return Token */

%unicode     /* work with unicode characters */
%line        /* access current line via yyline */
%column      /* access current column via yycolumn */

%{
  StringBuffer string = new StringBuffer();
  SymbolTable table = SymbolTable.getGlobalTable();

  boolean startingFunction = false;
%}

%eofval{
 return new Token(Tokens.EOF);
%eofval}

/**
 * TIME TO DEFINE REGULAR EXPRESSION DEFINITIONS
 */

/* white space/character definitions */
NewLine = \r|\n|\r\n
WS = {NewLine}|[ \t\f]
All = (.|{NewLine})

/* basics */
Letter = [a-zA-Z_]
Digit = [0-9]

/* comments */
Comment = \/\/.*{NewLine}|\/\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*\*+\/

/* string literal */
StringLiteral = \".*\"|\'.*\'

/* numbers */
Number = [-+]?[0-9]*\.?[0-9]+
Byte = {Digit}|{Digit}{2}|{Digit}{3}

/* color literals */
RGB = <<{WS}*{Byte}{WS}*,{WS}*{Byte}{WS}*,{WS}*{Byte}{WS}*>>
RGBA = <<{WS}*{Byte}{WS}*,{WS}*{Byte}{WS}*,{WS}*{Byte}{WS}*,{WS}*{Number}{WS}*>>

/* identifiers */
Identifier = {Letter}({Letter}|{Digit})*
Value = {Identifier}|{Number}|{StringLiteral}|{RGB}|{RGBA}

/* declarations */
Type = num|color|bool|string|Sound|Video|Image
VarDec = {Type}{WS}+{Identifier}
//VarInit = {VarDec}{WS}+\={WS}+{Value}
//FunDec = [{Type}|void]{WS}+{Identifier}{WS}+\({WS}*(({VarDec}{WS}*,{WS}*)*{VarDec})?{WS}*\)
ParamStuff = {Identifier}|{WS}|,|{Type}
//FunDec = ({Type}|void){WS}+{Identifier}{WS}*\({ParamStuff}*\)
FunDec = ({Type}|void){WS}+{Identifier}{WS}*\(

/* calls */
//FunCall = {Identifier}{WS}+\({All}*\)
//MethCall = {Identifier}\.{FunCall}
//VarAcc = {Identifier}\.{Identifier}
//Call = {FunCall}|{MethCall}|{VarAcc}

/* state definitions */
%state VOIDNESS

%%

/** LEXICAL RULES SECTION */

/* weird keywords */
<YYINITIAL> "->"            { return new Token(Tokens.ARROW, yyline); }
<YYINITIAL> ","             { return new Token(Tokens.COMMA, yyline); }
<YYINITIAL> ";"             { return new Token(Tokens.SEMI, yyline); }
<YYINITIAL> "("             { return new Token(Tokens.LPAREN, yyline); }
<YYINITIAL> ")"             { return new Token(Tokens.RPAREN, yyline); }
<YYINITIAL> "{"             {
                              if(!startingFunction) {
                                table = new SymbolTable(table, "block");
                              } else {
                                startingFunction = false;
                              }
                              return new Token(Tokens.LCURLY, yyline);
                            }
<YYINITIAL> "}"             { table = table.getPrev(); return new Token(Tokens.RCURLY, yyline); }
<YYINITIAL> "."             { return new Token(Tokens.DOT, yyline); }

/* control flow */
<YYINITIAL> "if"            { return new Token(Tokens.IF, yyline); }
<YYINITIAL> "elf"           { return new Token(Tokens.ELF, yyline); }
<YYINITIAL> "else"          { return new Token(Tokens.ELSE, yyline); }
<YYINITIAL> "dw"            { return new Token(Tokens.DW, yyline); }
<YYINITIAL> "return"        { return new Token(Tokens.RETURN, yyline); }

/* logics */
<YYINITIAL> "and"           { return new Token(Tokens.AND, yyline); }
<YYINITIAL> "or"            { return new Token(Tokens.OR, yyline); }
<YYINITIAL> "not"           { return new Token(Tokens.NOT, yyline); }
<YYINITIAL> "=="            { return new Token(Tokens.EQX2, yyline); }
<YYINITIAL> "<"             { return new Token(Tokens.LT, yyline); }
<YYINITIAL> "<="            { return new Token(Tokens.LTE, yyline); }
<YYINITIAL> ">"             { return new Token(Tokens.GT, yyline); }
<YYINITIAL> ">="            { return new Token(Tokens.GTE, yyline); }

/* maths */
<YYINITIAL> "+"             { return new Token(Tokens.PLUS, yyline); }
<YYINITIAL> "-"             { return new Token(Tokens.MINUS, yyline); }
<YYINITIAL> "*"             { return new Token(Tokens.TIMES, yyline); }
<YYINITIAL> "/"             { return new Token(Tokens.DIV, yyline); }
<YYINITIAL> "%"             { return new Token(Tokens.MOD, yyline); }
<YYINITIAL> "^"             { return new Token(Tokens.EXP, yyline); }
<YYINITIAL> "="             { return new Token(Tokens.EQ, yyline); }

<YYINITIAL> {
    {VarDec}                { String[] arr = yytext().split("\\s+");
                              String type = arr[0];
                              String name = arr[1];
                              Var var = new Var(name, type, yyline);
                              table.getMap().put(name, var);
                              return var;
                            }

    {FunDec}                { String text = yytext().replace("(", "");
                              String[] arr = text.split("\\s+");
                              String returnType = arr[0];
                              String name = arr[1];
                              Function fun = new Function(name, returnType, yyline);
                              table.getMap().put(name, fun);
                              return fun;
                            }

    {Number}                { Double val = Double.parseDouble(yytext()); return new Num(val, yyline); }

    {StringLiteral}         { return new StringLit(yytext(), yyline); }

    {RGB}                   { String rgb = yytext().replace("<<", "").replace(">>", "");
                              String[] arr = rgb.split(",");
                              int r = Integer.parseInt(arr[0].trim());
                              int g = Integer.parseInt(arr[1].trim());
                              int b = Integer.parseInt(arr[2].trim());
                              return new Color(r, g, b);
                            }

    {RGBA}                  { String rgb = yytext().replace("<<", "").replace(">>", "");
                              String[] arr = rgb.split(",");
                              int r = Integer.parseInt(arr[0].trim());
                              int g = Integer.parseInt(arr[1].trim());
                              int b = Integer.parseInt(arr[2].trim());
                              double a = Double.parseDouble(arr[3].trim());
                              return new Color(r, g, b, a);
                            }

    {Identifier}            { return new Identifier(yytext(), yyline); }

    /* {Value}                 { return new Value(yytext(), yyline); } */

    {WS}                    { /* do nothing */ }

    {Comment}               { /* do nothing */ }

  /*{FunDec}                { String[] arr = yytext().split("\\(");
                              String params = arr[1].replace(")", "").trim();
                              String[] arr2 = arr[0].split("\\s+");
                              String returnType = arr2[0];
                              String name = arr2[1];
                              Function fun = new Function(name, params, returnType, yyline);
                              table.getMap().put(name, fun);
                              return fun;
                            }*/

    /* anything else */
    [^]                     { throw new Error("Illegal character: " + yytext()); }


    /*
    {VarInit}                { String[] arr = yytext().split("=");
                              String varDec = arr[0];
                              String[] arr2 = varDec.split("\\s+");
                              String type = arr2[0];
                              String name = arr2[1];
                              String value = arr[1].trim();
                              Var var = new Var(name, type, value, yyline);
                              symbolTable.put(name, var);
                              return var;
                            }*/

/*
    {FunCall}               { String[] arr = yytext().split("(");
                              String name = arr[0].trim();
                              Function f = (Function) symbolTable.get(name);
                              String params = arr[1].split(")")[0].trim();
                              return new FunCall(f, params);
                            }

*/

}
