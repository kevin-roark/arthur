
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
    :                                               { $$ = new ParserVal(new ParseNode("parameters")); }
    | hard_param_list                               { $$ = $1; }  
    ;

hard_param_list
    : var                                           {
                                                        ParseNode params = new ParseNode("parameters");
                                                        ParseNode var = (ParseNode) $1.obj;
                                                        var.setParent(params);
                                                        params.addChild(var);
                                                        $$ = new ParserVal(params);
                                                    }
    | hard_param_list COMMA var                     {
                                                        ParseNode params = (ParseNode) $1.obj;
                                                        ParseNode var = (ParseNode) $3.obj;
                                                        var.setParent(params);
                                                        params.addChild(var);
                                                        $$ = $1;
                                                    }
    ;

dw_stmt
    : DW LPAREN expr RPAREN stmt                    {
                                                      ParseNode dw = new ParseNode("dw");
                                                      ParseNode expr = (ParseNode) $3.obj;
                                                      ParseNode stmt = (ParseNode) $5.obj;
                                                      dw.addChild(expr);
                                                      dw.addChild(stmt);
                                                      $$ = new ParserVal(dw);
                                                    }
    ;

if_stmt
    : IF LPAREN expr RPAREN stmt elf else           {
                                                      ParseNode ifmaster = new ParseNode("if");
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
    | IF LPAREN expr RPAREN stmt                    {
                                                      ParseNode ifmaster = new ParseNode("if");
                                                      ParseNode expr = (ParseNode) $3.obj;
                                                      ParseNode stmt = (ParseNode) $5.obj;
                                                      ifmaster.addChild(expr);
                                                      ifmaster.addChild(stmt);
                                                      $$ = new ParserVal(ifmaster);
                                                    }
    | IF LPAREN expr RPAREN stmt else               {
                                                      ParseNode ifmaster = new ParseNode("if");
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

else
    : ELSE stmt                                     { $$ = $2; }
    |                                               { $$ = new ParserVal(null); }
    ;

func_body
    : LCURLY stmt RCURLY    { $$ = $2; }
    ;

func_def
    : FUNCTION param_list RPAREN func_body          {
                                                        ParseNode funDef = new ParseNode("Function definition");
                                                        Function f = (Function) $1.obj;
                                                        funDef.addChild(new ParseNode(f.returnType, funDef));
                                                        funDef.addChild(new ParseNode(f.name, funDef));
                                                        ParseNode params = (ParseNode) $2.obj;
                                                        params.setParent(funDef); //can take statements of this nature out later if parent becomes obsolete
                                                        funDef.addChild(params);
                                                        ParseNode body = (ParseNode) $4.obj;
                                                        body.setParent(funDef);
                                                        funDef.addChild(body);
                                                        $$ = new ParserVal(funDef);
                                                    }
    ;

stmt
    : if_stmt                                       { 
                                                      ParseNode s = new ParseNode("stmt");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    | dw_stmt                                       { 
                                                      ParseNode s = new ParseNode("stmt");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    | expr_stmt                                     { 
                                                      ParseNode s = new ParseNode("stmt");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    | eq_stmt                                       { 
                                                      ParseNode s = new ParseNode("stmt");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    | stmt_list                                     { $$ = $1; }
    ;

expr_stmt
    : SEMI                                          { $$ = new ParserVal(null); }
    | expr SEMI                                     { $$ = $1; }
    ;

stmt_list
    :                                               { $$ = new ParserVal(new ParseNode("stmt_list"));}
    | stmt_list stmt                                {
                                                      ParseNode list = (ParseNode) $1.obj;
                                                      ParseNode s = (ParseNode) $2.obj;
                                                      list.addChild(s);
                                                      $$ = $1;
                                                    }
    ;

expr
    : bool_expr                                     { $$ = $1; }
    | num_expr                                      { $$ = $1; }
    | val                                           { $$ = $1; }
    |                                               { $$ = new ParserVal(null); }
    ;

bool_expr
    : TRUE                                          { ParseNode bool = new ParseNode("true"); $$ = new ParserVal(bool); }
    | FALSE                                         { ParseNode bool = new ParseNode("false"); $$ = new ParserVal(bool); }
    | bool_expr AND bool_expr                       { 
                                                      ParseNode and = new ParseNode("and");
                                                      ParseNode b1 = (ParseNode) $1.obj; ParseNode b2 = (ParseNode) $3.obj;
                                                      and.addChild(b1); b1.setParent(and);
                                                      and.addChild(b2); b2.setParent(and);
                                                      $$ = new ParserVal(and);
                                                    }
    | bool_expr OR bool_expr                        { 
                                                      ParseNode or = new ParseNode("or");
                                                      ParseNode b1 = (ParseNode) $1.obj; ParseNode b2 = (ParseNode) $3.obj;
                                                      or.addChild(b1); b1.setParent(or);
                                                      or.addChild(b2); b2.setParent(or);
                                                      $$ = new ParserVal(or);
                                                    }                                                      
    | NOT bool_expr                                 { 
                                                      ParseNode b = (ParseNode) $2.obj;
                                                      ParseNode not = new ParseNode("not");
                                                      not.addChild(b); not.setParent(b.parent); b.setParent(not);
                                                      $$ = new ParserVal(not);
                                                    }
    ;

num_expr
    : NUMBER                                        { ParseNode number = new ParseNode("number"); $$ = new ParserVal(number); }
    | num_expr LT num_expr                          {
                                                      ParseNode lt = new ParseNode("less than");
                                                      ParseNode ne1 = (ParseNode) $1.obj; ParseNode ne2 = (ParseNode) $3.obj;
                                                      lt.addChild(ne1); ne1.setParent(lt);
                                                      lt.addChild(ne2); ne2.setParent(lt);
                                                      $$ = new ParserVal(lt);
                                                    }
    | num_expr LTE num_expr                         {
                                                      ParseNode lte = new ParseNode("less than or equal to");
                                                      ParseNode ne1 = (ParseNode) $1.obj; ParseNode ne2 = (ParseNode) $3.obj;
                                                      lte.addChild(ne1); ne1.setParent(lte);
                                                      lte.addChild(ne2); ne2.setParent(lte);
                                                      $$ = new ParserVal(lte);
                                                    }
    | num_expr GT num_expr                          {
                                                      ParseNode gt = new ParseNode("greater than");
                                                      ParseNode ne1 = (ParseNode) $1.obj; ParseNode ne2 = (ParseNode) $3.obj;
                                                      gt.addChild(ne1); ne1.setParent(gt);
                                                      gt.addChild(ne2); ne2.setParent(gt);
                                                      $$ = new ParserVal(gt);
                                                    }
    | num_expr GTE num_expr                         {
                                                      ParseNode gte = new ParseNode("greater than or equal to");
                                                      ParseNode ne1 = (ParseNode) $1.obj; ParseNode ne2 = (ParseNode) $3.obj;
                                                      gte.addChild(ne1); ne1.setParent(gte);
                                                      gte.addChild(ne2); ne2.setParent(gte);
                                                      $$ = new ParserVal(gte);
                                                    }
    | num_expr EQ2X num_expr                        {
                                                      ParseNode eq2x = new ParseNode("is equal to");
                                                      ParseNode ne1 = (ParseNode) $1.obj; ParseNode ne2 = (ParseNode) $3.obj;
                                                      eq2x.addChild(ne1); ne1.setParent(eq2x);
                                                      eq2x.addChild(ne2); ne2.setParent(eq2x);
                                                      $$ = new ParserVal(eq2x);
                                                    }
    ;

eq_stmt
    : id EQ val                                     {
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) $1.obj;
                                                        ParseNode val = (ParseNode) $3.obj;
                                                        id.setParent(eq); //again, can take these out later if need be
                                                        val.setParent(eq);
                                                        eq.addChild(id);
                                                        eq.addChild(val);    
                                                        $$ = new ParserVal(eq);
                                                    }
    | var EQ val                                    {
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode var = (ParseNode) $1.obj;
                                                        ParseNode val = (ParseNode) $3.obj;
                                                        var.setParent(eq);
                                                        val.setParent(eq);
                                                        eq.addChild(var);
                                                        eq.addChild(val);
                                                        $$ = new ParserVal(eq);
                                                    }
    ;

val
    :                             
    | val PLUS term                                 { 
                                                        ParseNode plus = new ParseNode("+");
                                                        plus.AddChild((ParseNode)$1.obj);
                                                        plus.AddChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(plus);
                                                    }
    | val MINUS term                                { 
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.AddChild((ParseNode)$1.obj);
                                                        minus.AddChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(minus);
                                                    }
    | term                                          { $$ = $1; }
    ;

term
    : term TIMES exfactor                           { 
                                                        ParseNode times = new ParseNode("*");
                                                        times.AddChild((ParseNode)$1.obj);
                                                        times.AddChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(times);
                                                    }
    | term DIV exfactor                             { 
                                                        ParseNode div = new ParseNode("/");
                                                        div.AddChild((ParseNode)$1.obj);
                                                        div.AddChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(div);
                                                    }
    | term MOD exfactor                             { 
                                                        ParseNode mod = new ParseNode("%");
                                                        mod.AddChild((ParseNode)$1.obj);
                                                        mod.AddChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(mod);
                                                    }
    | exfactor                                      { $$ = $1; }
    ;

exfactor
    : exfactor EXP factor                           { 
                                                        ParseNode exp = new ParseNode("^");
                                                        exp.AddChild((ParseNode)$1.obj);
                                                        exp.AddChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(exp);
                                                    }
    | factor                                        { $$ = $1; }
    ;

factor
    : COLOR                                         {
                                                        Color c = (Color) $1.obj;
                                                        ParseNode color = new ParseNode("Color literal");
                                                        color.addChild(new ParseNode(c.r, color));
                                                        color.addChild(new ParseNode(c.g, color));
                                                        color.addChild(new ParseNode(c.b, color));
                                                        color.addChild(new ParseNode(c.a, color));
                                                        $$ = new ParserVal(color);
                                                    }
    | NUMBER                                        {
                                                        Number n = (Number) $1.obj;
                                                        ParseNode number = new ParseNode("Number literal");
                                                        number.addChild(new ParseNode(n.val, number)); 
                                                        $$ = new ParserVal(number);    
                                                    }
    | STRINGLIT                                     {
                                                        StringLit s = (StringLit) $1.obj;
                                                        ParseNode string = new ParseNode("String literal");
                                                        string.addChild(new ParseNode(s.val, string)); 
                                                        $$ = new ParserVal(string); 
                                                    }
    | TRUE                                          { ParseNode t = new ParseNode("true"); $$ = new ParserVal(t); }
    | FALSE                                         { ParseNode f = new ParseNode("false"); $$ = new ParserVal(f); }
    | id                                            { $$ = $1; }
    | LPAREN val RPAREN                             { $$ = $2; }
    ;

var
    : VAR                                           {
                                                        Var v = (Var) $1.obj;
                                                        ParseNode var = new ParseNode("variable");
                                                        var.addChild(new ParseNode(v.type, var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        $$ = new ParserVal(var);
                                                    }
    ;

id
    : ID                                            {
                                                        Identifier i = (Identifier) $1.obj;
                                                        ParseNode id = new ParseNode("Identifier");
                                                        id.addChild(new ParseNode(i.name), id);
                                                        $$ = new ParserVal(id); 
                                                    }
    ;

%%
ParseNode root = new ParseNode("Program");

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
