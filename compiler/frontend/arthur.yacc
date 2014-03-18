
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
%token EQ EQ2X LT LTE GT GTE
%token PLUS MINUS TIMES DIV MOD EXP
%token FUNCTION VAR VALUE ID
%token COLOR NUMBER STRINGLIT TRUE FALSE
%token EOF
%token UNKNOWN

%%

/* grammar rules */

program
    : EOF                                           { ParseNode p = new ParseNode("arthur"); $$ = new ParserVal(p); System.out.println($$.obj);}
    | stuff EOF                                     { $$ = $1; System.out.println($1.obj); }
    ;

stuff
    : stuff func_def                                {
                                                        ParseNode s = (ParseNode) $1.obj;
                                                        ParseNode f = (ParseNode) $2.obj;
                                                        s.addChild(f);
                                                        $$ = $1;
                                                    }
    | func_def                                      { 
                                                        ParseNode p = new ParseNode("arthur"); 
                                                        p.addChild((ParseNode) $1.obj);
                                                        $$ = new ParserVal(p);
                                                    }   
    ;

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
    : IF LPAREN expr RPAREN stmt elfs else           {
                                                      ParseNode ifmaster = new ParseNode("if");
                                                      ParseNode iffer = new ParseNode("iffer", ifmaster);
                                                      ParseNode expr = (ParseNode) $3.obj;
                                                      ParseNode stmt = (ParseNode) $5.obj;
                                                      iffer.addChild(expr);
                                                      iffer.addChild(stmt);
                                                      ifmaster.addChild(iffer);
                                                      ParseNode elfer = (ParseNode) $6.obj;
                                                      ifmaster.addChild(elfer);
                                                      ParseNode elser = (ParseNode) $7.obj;
                                                      ifmaster.addChild(elser);
                                                      $$ = new ParserVal(ifmaster);
                                                    }
    | IF LPAREN expr RPAREN stmt elfs               {
                                                      ParseNode ifmaster = new ParseNode("if");
                                                      ParseNode iffer = new ParseNode("iffer", ifmaster);
                                                      ParseNode expr = (ParseNode) $3.obj;
                                                      ParseNode stmt = (ParseNode) $5.obj;
                                                      iffer.addChild(expr);
                                                      iffer.addChild(stmt);
                                                      ifmaster.addChild(iffer);
                                                      ParseNode elfer = (ParseNode) $6.obj;
                                                      ifmaster.addChild(elfer);
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

elfs
    : elfs elf                                      {
                                                      ParseNode elfs = (ParseNode) $1.obj;
                                                      ParseNode elf = (ParseNode) $2.obj;
                                                      elfs.addChild(elf);
                                                      $$ = $1;
                                                    }                                      
    | elf                                           {
                                                      ParseNode elfs = new ParseNode("elves");
                                                      elfs.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(elfs);
                                                    }
    ;

elf
    : ELF LPAREN expr RPAREN stmt                   {
                                                        ParseNode elf = new ParseNode("elf");
                                                        elf.addChild((ParseNode) $3.obj);
                                                        elf.addChild((ParseNode) $5.obj);
                                                        $$ = new ParserVal(elf);
                                                    }
    ;

else
    : ELSE stmt                                     { $$ = $2; }
    ;

func_body
    : LCURLY stmt RCURLY    { $$ = $2; }
    | LCURLY RCURLY         { $$ = new ParserVal(new ParseNode("")); }
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

fun_call
    : id LPAREN arg_list RPAREN                     {
                                                      ParseNode funCall = new ParseNode("Function call");
                                                      ParseNode name = (ParseNode) $1.obj;
                                                      funCall.addChild(name);
                                                      ParseNode args = (ParseNode) $3.obj;
                                                      funCall.addChild(args);
                                                      $$ = new ParserVal(funCall);
                                                    }
    ;

meth_call
    : id DOT fun_call                               {
                                                      ParseNode methCall = new ParseNode("Method call");
                                                      ParseNode ob = (ParseNode) $1.obj;
                                                      methCall.addChild(ob);
                                                      ParseNode fun = (ParseNode) $3.obj;
                                                      methCall.addChild(fun);
                                                      $$ = new ParserVal(methCall);
                                                    }
    ;

meth_call_stmt
    : meth_call SEMI                                { $$ = $1; }
    ;

fun_call_stmt
    : fun_call SEMI                                 { $$ = $1; }
    ;

arg_list
    :                                               { $$ = new ParserVal(new ParseNode("arguments")); }
    | hard_arg_list                                 { $$ = $1; }
    ;

hard_arg_list
    : expr                                          {
                                                        ParseNode params = new ParseNode("arguments");
                                                        ParseNode ex = (ParseNode) $1.obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        $$ = new ParserVal(params);
                                                    }
    | hard_arg_list COMMA expr                      {
                                                        ParseNode params = (ParseNode) $1.obj;
                                                        ParseNode ex = (ParseNode) $3.obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        $$ = $1;
                                                    }
    ;

stmt
    : stmt_list                                     { $$ = $1; }
    | if_stmt                                       { 
                                                      ParseNode s = new ParseNode("stmt: if");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    | dw_stmt                                       { 
                                                      ParseNode s = new ParseNode("stmt: dw");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    | expr_stmt                                     { 
                                                      ParseNode s = new ParseNode("stmt: expr");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    | eq_stmt                                       { 
                                                      ParseNode s = new ParseNode("stmt: eq");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    | fun_call_stmt                                 { 
                                                      ParseNode s = new ParseNode("stmt: fun_call");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    | meth_call_stmt                                { 
                                                      ParseNode s = new ParseNode("stmt: meth_call");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    | return_stmt                                   { 
                                                      ParseNode s = new ParseNode("stmt: return");
                                                      s.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(s);
                                                    }
    ;

return_stmt
    : RETURN expr SEMI                              {
                                                      ParseNode e = (ParseNode) $2.obj;
                                                      ParseNode r = new ParseNode("return");
                                                      r.addChild(e);
                                                      $$ = new ParserVal(r);
                                                    }
    ;

expr_stmt
    : SEMI                                          { $$ = new ParserVal(null); }
    | expr SEMI                                     { $$ = $1; }
    ;

stmt_list
    : stmt_list stmt                                { 
                                                      ParseNode list = (ParseNode) $1.obj;
                                                      ParseNode s = (ParseNode) $2.obj;
                                                      list.addChild(s);
                                                      $$ = $1;
                                                    }
    | stmt                                          {
                                                      ParseNode s = (ParseNode) $1.obj;
                                                      ParseNode list = new ParseNode("stmt_list");
                                                      list.addChild(s);
                                                      $$ = new ParserVal(list);
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
    : id EQ val SEMI                                {
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) $1.obj;
                                                        ParseNode val = (ParseNode) $3.obj;
                                                        id.setParent(eq); //again, can take these out later if need be
                                                        val.setParent(eq);
                                                        eq.addChild(id);
                                                        eq.addChild(val);    
                                                        $$ = new ParserVal(eq);
                                                    }
    | var EQ val SEMI                               {
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
                                                        plus.addChild((ParseNode)$1.obj);
                                                        plus.addChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(plus);
                                                    }
    | val MINUS term                                { 
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.addChild((ParseNode)$1.obj);
                                                        minus.addChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(minus);
                                                    }
    | term                                          { $$ = $1; }
    ;

term
    : term TIMES exfactor                           { 
                                                        ParseNode times = new ParseNode("*");
                                                        times.addChild((ParseNode)$1.obj);
                                                        times.addChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(times);
                                                    }
    | term DIV exfactor                             { 
                                                        ParseNode div = new ParseNode("/");
                                                        div.addChild((ParseNode)$1.obj);
                                                        div.addChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(div);
                                                    }
    | term MOD exfactor                             { 
                                                        ParseNode mod = new ParseNode("%");
                                                        mod.addChild((ParseNode)$1.obj);
                                                        mod.addChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(mod);
                                                    }
    | exfactor                                      { $$ = $1; }
    ;

exfactor
    : exfactor EXP factor                           { 
                                                        ParseNode exp = new ParseNode("^");
                                                        exp.addChild((ParseNode)$1.obj);
                                                        exp.addChild((ParseNode)$3.obj);
                                                        $$ = new ParserVal(exp);
                                                    }
    | factor                                        { $$ = $1; }
    ;

factor
    : COLOR                                         {
                                                        Color c = (Color) $1.obj;
                                                        ParseNode color = new ParseNode("Color literal");
                                                        color.addChild(new ParseNode(c.r.toString(), color));
                                                        color.addChild(new ParseNode(c.g.toString(), color));
                                                        color.addChild(new ParseNode(c.b.toString(), color));
                                                        color.addChild(new ParseNode(c.a.toString(), color));
                                                        $$ = new ParserVal(color);
                                                    }
    | NUMBER                                        {
                                                        Number n = (Number) $1.obj;
                                                        ParseNode number = new ParseNode("Number literal");
                                                        number.addChild(new ParseNode(n.val.toString(), number)); 
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
                                                        var.addChild(new ParseNode(v.typeName(), var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        $$ = new ParserVal(var);
                                                    }
    ;

id
    : fun_call                                      { $$ = $1; }
    | meth_call                                     { $$ = $1; }
    | ID                                            {
                                                        Identifier i = (Identifier) $1.obj;
                                                        ParseNode id = new ParseNode("Identifier");
                                                        id.addChild(new ParseNode(i.name, id));
                                                        $$ = new ParserVal(id); 
                                                    }
    ;

%%
Lexer lexer;
Token prevTok;

void yyerror(String s) {
    System.out.println("Error: " + s);
}

int yylex() {
    Token tok;

    if (prevTok != null && prevTok.tokenType == Tokens.EOF) {
        return -1;
    }

    try {
        tok = lexer.yylex();
    } catch(IOException e) {
        System.out.println("got this exception getting lex: " + e);
        return '\n';
    }

    /* might need to do stuff with token and symbol table, etc */
    yylval = new ParserVal(tok);

    prevTok = tok;

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
    //ParserVal program = yyparse();
    //ParseNode p = (ParseNode) program.obj;
    int result = yyparse();
    System.out.println("Return value: " + result);
}
