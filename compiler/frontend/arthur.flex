
/** USER CODE SECTION */

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
  SymbolTable table = new SymbolTable();
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
StringLiteral = \".*\"

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
<YYINITIAL> "->"            { return new Token(Tokens.ARROW); }
<YYINITIAL> ","             { return new Token(Tokens.COMMA); }
<YYINITIAL> ";"             { return new Token(Tokens.SEMI); }
<YYINITIAL> "("             { return new Token(Tokens.LPAREN); }
<YYINITIAL> ")"             { return new Token(Tokens.RPAREN); }
<YYINITIAL> "{"             { table = new SymbolTable(table); return new Token(Tokens.LCURLY); }
<YYINITIAL> "}"             { table = table.getPrev(); return new Token(Tokens.RCURLY); }
<YYINITIAL> "."             { return new Token(Tokens.DOT); }

/* control flow */
<YYINITIAL> "if"            { return new Token(Tokens.IF); }
<YYINITIAL> "elf"           { return new Token(Tokens.ELF); }
<YYINITIAL> "else"          { return new Token(Tokens.ELSE); }
<YYINITIAL> "dw"            { return new Token(Tokens.DW); }
<YYINITIAL> "return"        { return new Token(Tokens.RETURN); }

/* logics */
<YYINITIAL> "and"           { return new Token(Tokens.AND); }
<YYINITIAL> "or"            { return new Token(Tokens.OR); }
<YYINITIAL> "not"           { return new Token(Tokens.NOT); }
<YYINITIAL> "=="            { return new Token(Tokens.EQX2); }
<YYINITIAL> "<"             { return new Token(Tokens.LT); }
<YYINITIAL> "<="            { return new Token(Tokens.LTE); }
<YYINITIAL> ">"             { return new Token(Tokens.GT); }
<YYINITIAL> ">="            { return new Token(Tokens.GTE); }

/* maths */
<YYINITIAL> "+"             { return new Token(Tokens.PLUS); }
<YYINITIAL> "-"             { return new Token(Tokens.MINUS); }
<YYINITIAL> "*"             { return new Token(Tokens.TIMES); }
<YYINITIAL> "/"             { return new Token(Tokens.DIV); }
<YYINITIAL> "%"             { return new Token(Tokens.MOD); }
<YYINITIAL> "^"             { return new Token(Tokens.EXP); }
<YYINITIAL> "="             { return new Token(Tokens.EQ); }

<YYINITIAL> {
    {VarDec}                { String[] arr = yytext().split("\\s+");
                              String type = arr[0];
                              String name = arr[1];
                              Var var = new Var(name, type);
                              table.getMap().put(name, var);
                              return var;
                            }

    {FunDec}                { String text = yytext().replace("(", "");
                              String[] arr = text.split("\\s+");
                              String returnType = arr[0];
                              String name = arr[1];
                              Function fun = new Function(name, returnType);
                              table.getMap().put(name, fun);
                              return fun;
                            }

    {Number}                { Double val = Double.parseDouble(yytext()); return new Number(val); }

    {StringLiteral}         { return new StringLit(yytext()); }

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

    {Identifier}            { return new Identifier(yytext()); }

    {Value}                 { return new Value(yytext()); }

    {WS}                    { /* do nothing */ }

    {Comment}               { /* do nothing */ }

  /*{FunDec}                { String[] arr = yytext().split("\\(");
                              String params = arr[1].replace(")", "").trim();
                              String[] arr2 = arr[0].split("\\s+");
                              String returnType = arr2[0];
                              String name = arr2[1];
                              Function fun = new Function(name, params, returnType);
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
                              Var var = new Var(name, type, value);
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
