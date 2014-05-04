
/* imports */
 %{
    package arthur.frontend;

    import java.io.Reader;
    import java.io.IOException;
    import java.lang.Math;
    import java.util.HashSet;
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
%token TYPE ARG
%token EOF
%token UNKNOWN

%%

/* grammar rules */

program
    : EOF                                           { ParseNode p = new ParseNode("arthur"); $$ = new ParserVal(p); AST = p; }
    | stuff EOF                                     { $$ = $1; AST = (ParseNode) $1.obj; }
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
    | variable_decs                                 {
                                                        $$ = $1;
                                                    }
    ;

variable_decs
    : var_dec_stmt                                  {
                                                      ParseNode p = new ParseNode("globals");
                                                      p.addChild((ParseNode) $1.obj);
                                                      $$ = new ParserVal(p);
                                                    }
    | variable_decs var_dec_stmt                    { ParseNode g = (ParseNode)$1.obj; g.addChild((ParseNode) $2.obj); $$ = $1; }
    ;

param_list
    :                                               { $$ = new ParserVal(new ParseNode("parameters")); }
    | hard_param_list                               { $$ = $1; }
    ;

hard_param_list
    : param                                           {
                                                        ParseNode params = new ParseNode("parameters");
                                                        ParseNode var = (ParseNode) $1.obj;
                                                        var.setParent(params);
                                                        params.addChild(var);
                                                        $$ = new ParserVal(params);
                                                    }
    | hard_param_list COMMA param                     {
                                                        ParseNode params = (ParseNode) $1.obj;
                                                        ParseNode var = (ParseNode) $3.obj;
                                                        var.setParent(params);
                                                        params.addChild(var);
                                                        $$ = $1;
                                                    }
    ;

dw_stmt
    : DW LPAREN cond RPAREN stmt                    {
                                                      ParseNode dw = new ParseNode("dw");
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) $3.obj);
                                                      body.addChild((ParseNode) $5.obj);
                                                      dw.addChild(condition);
                                                      dw.addChild(body);
                                                      $$ = new ParserVal(dw);
                                                    }
    ;

sugarloop_stmt
    : NUMBER TIMES LCURLY stmt RCURLY               {
                                                      ParseNode sl = new ParseNode("sugarloop");
                                                      ParseNode times = new ParseNode("times");
                                                      Num n = (Num) $1.obj;
                                                      ParseNode val = new ParseNode(n.val.toString());
                                                      times.addChild(val);
                                                      ParseNode body = new ParseNode("body");
                                                      body.addChild((ParseNode) $4.obj);
                                                      sl.addChild(times);
                                                      sl.addChild(body);
                                                      $$ = new ParserVal(sl);
                                                    }
    ;

if_stmt
    : IF LPAREN cond RPAREN stmt elfs else           {
                                                      ParseNode ifmaster = new ParseNode("if-switch");
                                                      ParseNode iffer = new ParseNode("if", ifmaster);
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) $3.obj);
                                                      body.addChild((ParseNode) $5.obj);
                                                      iffer.addChild(condition);
                                                      iffer.addChild(body);
                                                      ifmaster.addChild(iffer);
                                                      ParseNode elfer = (ParseNode) $6.obj;
                                                      ifmaster.addChild(elfer);
                                                      ParseNode elser = (ParseNode) $7.obj;
                                                      ifmaster.addChild(elser);
                                                      $$ = new ParserVal(ifmaster);
                                                    }
    | IF LPAREN cond RPAREN stmt elfs               {
                                                      ParseNode ifmaster = new ParseNode("if-switch");
                                                      ParseNode iffer = new ParseNode("if", ifmaster);
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) $3.obj);
                                                      body.addChild((ParseNode) $5.obj);
                                                      iffer.addChild(condition);
                                                      iffer.addChild(body);
                                                      ifmaster.addChild(iffer);
                                                      ParseNode elfer = (ParseNode) $6.obj;
                                                      ifmaster.addChild(elfer);
                                                      $$ = new ParserVal(ifmaster);
                                                    }
    | IF LPAREN cond RPAREN stmt                    {
                                                      ParseNode ifmaster = new ParseNode("if-switch");
                                                      ParseNode iffer = new ParseNode("if", ifmaster);
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) $3.obj);
                                                      body.addChild((ParseNode) $5.obj);
                                                      iffer.addChild(condition);
                                                      iffer.addChild(body);
                                                      ifmaster.addChild(iffer);
                                                      $$ = new ParserVal(ifmaster);
                                                    }
    | IF LPAREN cond RPAREN stmt else               {
                                                      ParseNode ifmaster = new ParseNode("if-switch");
                                                      ParseNode iffer = new ParseNode("if", ifmaster);
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) $3.obj);
                                                      body.addChild((ParseNode) $5.obj);
                                                      iffer.addChild(condition);
                                                      iffer.addChild(body);
                                                      ifmaster.addChild(iffer);;
                                                      ParseNode elser = (ParseNode) $6.obj;
                                                      if (elser != null)
                                                          ifmaster.addChild(elser);
                                                      $$ = new ParserVal(ifmaster);
                                                    }
    | IF error                                      { $$ = new ParserVal(new ParseNode("wrong if statement??")); }

    | IF LPAREN cond RPAREN error                   { ParseNode ifmaster = new ParseNode("if-switch");
                                                      ParseNode iffer = new ParseNode("if", ifmaster);
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) $3.obj);
                                                      body.addChild(new ParseNode("bad body!"));
                                                      iffer.addChild(condition);
                                                      iffer.addChild(body);
                                                      ifmaster.addChild(iffer);
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
    : ELF LPAREN cond RPAREN stmt                   {
                                                        ParseNode elf = new ParseNode("elf");
                                                        ParseNode condition = new ParseNode("condition");
                                                        ParseNode body = new ParseNode("body");
                                                        condition.addChild((ParseNode) $3.obj);
                                                        body.addChild((ParseNode) $5.obj);
                                                        elf.addChild(condition);
                                                        elf.addChild(body);
                                                        $$ = new ParserVal(elf);
                                                    }
    ;

else
    : ELSE stmt                                     {
                                                        ParseNode elser = new ParseNode("else");
                                                        ParseNode body = new ParseNode("body");
                                                        body.addChild((ParseNode) $2.obj);
                                                        elser.addChild(body);
                                                        $$ = new ParserVal(elser);
                                                    }
    ;

func_body
    : LCURLY stmt RCURLY    { $$ = $2; }
    | LCURLY RCURLY         { $$ = new ParserVal(new ParseNode("")); }
    ;

func_def
    : FUNCTION param_list RPAREN func_body          {
                                                        ParseNode funDef = new ParseNode("Function");
                                                        Function f = (Function) $1.obj;
                                                        if (lexer.table.get(f.name) != null) {
                                                          System.out.println("function named " + f.name + " already exists!!");
                                                          errorCount++;
                                                        }

                                                        if (javaWords.contains(f.name)) {
                                                          System.out.println(f.name + " is not a viable function name!");
                                                          errorCount++;
                                                        }

                                                        lexer.table.put(f.name, f);

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
                                                      ParseNode funCall = new ParseNode("Fun call");
                                                      ParseNode name = (ParseNode) $1.obj;
                                                      funCall.addChild(name);
                                                      ParseNode args = (ParseNode) $3.obj;
                                                      funCall.addChild(args);
                                                      $$ = new ParserVal(funCall);
                                                    }
    ;

caster
    : id ARROW TYPE                                 {
                                                      ParseNode caster = new ParseNode("cast");
                                                      ParseNode par = (ParseNode) $1.obj;
                                                      caster.addChild(par);

                                                      Type t = (Type) $3.obj;
                                                      ParseNode type = new ParseNode(t.name);
                                                      caster.addChild(type);
                                                      $$ = new ParserVal(caster);
                                                    }
    | literal ARROW TYPE                            {
                                                      ParseNode caster = new ParseNode("cast");
                                                      ParseNode par = (ParseNode) $1.obj;
                                                      caster.addChild(par);

                                                      Type t = (Type) $3.obj;
                                                      ParseNode type = new ParseNode(t.name);
                                                      caster.addChild(type);
                                                      $$ = new ParserVal(caster);
                                                    }
    ;

sysarg
    : ARG                                           {
                                                      ParseNode arg = new ParseNode("sysarg");
                                                      Arg a = (Arg) $1.obj;
                                                      arg.addChild(new ParseNode(a.num));
                                                      $$ = new ParserVal(arg);
                                                    }
    ;

meth_call
    : id ARROW fun_call                             {
                                                      ParseNode methCall = new ParseNode("Method call");
                                                      ParseNode ob = (ParseNode) $1.obj;
                                                      methCall.addChild(ob);
                                                      ParseNode fun = (ParseNode) $3.obj;
                                                      methCall.addChild(fun);
                                                      $$ = new ParserVal(methCall);
                                                    }
    ;

prop_access
    : id DOT prop                                   {
                                                      ParseNode main = (ParseNode) $1.obj;
                                                      ParseNode sub = (ParseNode) $3.obj;
                                                      String pi = main.children.get(0).val;
                                                      String ci = sub.children.get(0).val;
                                                      Var object = (Var) lexer.table.get(pi);
                                                      if (object != null) {
                                                        if (! object.getVarProperties().contains(ci)) {
                                                          System.out.println("'" + pi + "' (of type " + object.typeName() + ") does not contain property '" + ci + "'.");
                                                          errorCount++;
                                                        }
                                                      }

                                                      ParseNode prop = new ParseNode("Property access");
                                                      prop.addChild(main);
                                                      prop.addChild(sub);
                                                      $$ = new ParserVal(prop);
                                                    }
    ;

prop
    : ID                                            {
                                                      Identifier i = (Identifier) $1.obj;
                                                      ParseNode id = new ParseNode("Property");
                                                      id.addChild(new ParseNode(i.name, id));
                                                      $$ = new ParserVal(id);
                                                    }
    ;

prop_access_stmt
    : prop_access SEMI                              { $$ = $1; }
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
    : stmt_block                                    { $$ = $1; }
    | if_stmt                                       { $$ = $1; }
    | dw_stmt                                       { $$ = $1; }
    | sugarloop_stmt                                { $$ = $1; }
    | expr_stmt                                     { $$ = $1; }
    | eq_stmt                                       { $$ = $1; }
    | fun_call_stmt                                 { $$ = $1; }
    | meth_call_stmt                                { $$ = $1; }
    | var_dec_stmt                                  { $$ = $1; }
    | prop_access_stmt                              { $$ = $1; }
    | return_stmt                                   { $$ = $1; }
    ;

return_stmt
    : RETURN expr SEMI                              {
                                                      ParseNode e = (ParseNode) $2.obj;
                                                      ParseNode r = new ParseNode("return");
                                                      r.addChild(e);
                                                      $$ = new ParserVal(r);
                                                    }
    | RETURN SEMI                                   {
                                                      ParseNode r = new ParseNode("return");
                                                      $$ = new ParserVal(r);
                                                    }
    | RETURN error SEMI                             {
                                                        System.out.println("ERROR: return statement must be in the form: " +
                                                        "LOL");
                                                        errorCount++;
                                                        $$ = new ParserVal(new ParseNode("fucked return stmt"));
                                                    }
    ;

var_dec_stmt
    : var SEMI                                      { $$ = $1; }
    ;

expr_stmt
    : SEMI                                          { $$ = new ParserVal(new ParseNode("")); }
    | expr SEMI                                     { $$ = $1; }
    | error SEMI                                    { System.out.println($1.obj); System.out.println("Not an expression !!!! jesus");
                                                      errorCount++;
                                                      $$ = new ParserVal(new ParseNode("fucked expression stmt"));
                                                    }
    ;

stmt_block
    : LCURLY stmt_list RCURLY                       { $$ = $2; }
    | LCURLY RCURLY                                 { $$ = new ParserVal(new ParseNode("")); }
    | stmt_list                                     { $$ = $1; }
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
    | eq_expr                                       { $$ = $1; }
    | val                                           { $$ = $1; }
    |                                               { $$ = new ParserVal(new ParseNode("")); }
    ;

cond
    : bool_expr                                     { $$ = $1; }
    | num_expr                                      { $$ = $1; }
    | val                                           { $$ = $1; }
    ;

bool_expr
    : TRUE                                          { ParseNode bool = new ParseNode("true"); $$ = new ParserVal(bool); }
    | FALSE                                         { ParseNode bool = new ParseNode("false"); $$ = new ParserVal(bool); }
    | num_expr                                      { $$ = $1; }
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
    : NUMBER                                        {
                                                      ParseNode number = new ParseNode("number");
                                                      Num n = (Num) $1.obj;
                                                      ParseNode val = new ParseNode(n.val.toString());
                                                      number.addChild(val);
                                                      $$ = new ParserVal(number); }
    | val                                           { $$ = $1; }
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

eq_expr
    : id EQ val                                    {
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) $1.obj;
                                                        ParseNode val = (ParseNode) $3.obj;
                                                        id.setParent(eq); //again, can take these out later if need be
                                                        val.setParent(eq);
                                                        eq.addChild(id);
                                                        eq.addChild(val);
                                                        $$ = new ParserVal(eq);
                                                    }
    | id PLUS EQ val                                {
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) $1.obj;
                                                        ParseNode val = (ParseNode) $4.obj;
                                                        ParseNode plus = new ParseNode("+");
                                                        plus.addChild(id);
                                                        plus.addChild(val);
                                                        eq.addChild(id);
                                                        eq.addChild(plus);
                                                        $$ = new ParserVal(eq);
                                                    }
    | id MINUS EQ val                               {
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) $1.obj;
                                                        ParseNode val = (ParseNode) $4.obj;
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.addChild(id);
                                                        minus.addChild(val);
                                                        eq.addChild(id);
                                                        eq.addChild(minus);
                                                        $$ = new ParserVal(eq);
                                                    }
    | id TIMES EQ val                               {
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) $1.obj;
                                                        ParseNode val = (ParseNode) $4.obj;
                                                        ParseNode mult = new ParseNode("*");
                                                        mult.addChild(id);
                                                        mult.addChild(val);
                                                        eq.addChild(id);
                                                        eq.addChild(mult);
                                                        $$ = new ParserVal(eq);
                                                    }
    | id DIV EQ val                                 {
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) $1.obj;
                                                        ParseNode val = (ParseNode) $4.obj;
                                                        ParseNode div = new ParseNode("/");
                                                        div.addChild(id);
                                                        div.addChild(val);
                                                        eq.addChild(id);
                                                        eq.addChild(div);
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
    // Errors
    // | id EQ error SEMI                              { System.out.println("id = NO"); errorCount++; }
    // | var EQ error SEMI                             { System.out.println("var = NO"); errorCount++;  }
    // | error EQ val SEMI                             { System.out.println("NO = val"); errorCount++;  }
    // | error EQ error SEMI                           { System.out.println("NO = NO"); errorCount++;  }
    ;

eq_stmt
    : eq_expr SEMI                                  { $$ = $1; }
    ;

val
    : val PLUS term                                 {
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

factor
    : literal                                       { $$ = $1; }
    | TRUE                                          { ParseNode t = new ParseNode("true"); $$ = new ParserVal(t); }
    | FALSE                                         { ParseNode f = new ParseNode("false"); $$ = new ParserVal(f); }
    | id                                            { $$ = $1; }
    | LPAREN val RPAREN                             { $$ = $2; }
    ;

literal
    : COLOR                                         {
                                                        Color c = (Color) $1.obj;
                                                        ParseNode color = new ParseNode("Color");
                                                        color.addChild(new ParseNode(c.r.toString(), color));
                                                        color.addChild(new ParseNode(c.g.toString(), color));
                                                        color.addChild(new ParseNode(c.b.toString(), color));
                                                        color.addChild(new ParseNode(c.a.toString(), color));
                                                        $$ = new ParserVal(color);
                                                    }
    | NUMBER                                        {
                                                        Num n = (Num) $1.obj;
                                                        ParseNode number = new ParseNode("number");
                                                        number.addChild(new ParseNode(n.val.toString(), number));
                                                        $$ = new ParserVal(number);
                                                    }
    | STRINGLIT                                     {
                                                        StringLit s = (StringLit) $1.obj;
                                                        ParseNode string = new ParseNode("String");
                                                        string.addChild(new ParseNode(s.val, string));
                                                        $$ = new ParserVal(string);
                                                    }
    ;

var
    : VAR                                           {
                                                        Var v = (Var) $1.obj;
                                                        if (lexer.table.getMap().get(v.id) != null) {
                                                          System.out.println("variable " + v.id + " already exists!!");
                                                          errorCount++;
                                                        }

                                                        if (javaWords.contains(v.id)) {
                                                          System.out.println(v.id + " is not a viable variable name!");
                                                          errorCount++;
                                                        }

                                                        lexer.table.put(v.id, v);

                                                        ParseNode var = new ParseNode("variable");
                                                        var.addChild(new ParseNode(v.typeName(), var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        $$ = new ParserVal(var);
                                                    }
    ;

param
    : VAR                                           {
                                                        Var v = (Var) $1.obj;
                                                        if (lexer.table.getMap().get(v.id) != null) {
                                                          System.out.println("parameter " + v.id + " already exists!!");
                                                          errorCount++;
                                                        }

                                                        if (javaWords.contains(v.id)) {
                                                          System.out.println(v.id + " is not a viable variable name!");
                                                          errorCount++;
                                                        }

                                                        lexer.table.put(v.id, v);

                                                        ParseNode var = new ParseNode("parameter");
                                                        var.addChild(new ParseNode(v.typeName(), var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        $$ = new ParserVal(var);
                                                    }
    ;

id
    : fun_call                                      { $$ = $1; }
    | meth_call                                     { $$ = $1; }
    | prop_access                                   { $$ = $1; }
    | caster                                        { $$ = $1; }
    | sysarg                                        { $$ = $1; }
    | ID                                            {
                                                        Identifier i = (Identifier) $1.obj;

                                                        Token check = (Token) lexer.table.get(i.name);
                                                        if (check == null) {
                                                          System.out.println("Identifier not declared on line " + i.line + ": " + i.name);
                                                          errorCount++;
                                                        }

                                                        if (javaWords.contains(i.name)) {
                                                          System.out.println(i.name + " is not a viable identifier!");
                                                          errorCount++;
                                                        }

                                                        ParseNode id = new ParseNode("Identifier");
                                                        id.addChild(new ParseNode(i.name, id));
                                                        $$ = new ParserVal(id);
                                                    }
    ;

%%
Lexer lexer;
Token prevTok;
ParseNode AST;

HashSet<String> javaWords;

int errorCount;

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
        case Tokens.TYPE: return TYPE;
        case Tokens.ARG: return ARG;
        default: return UNKNOWN;
    }
}

public void createJava() {
  javaWords = new HashSet<String>();
  javaWords.add("abstract");
  javaWords.add("assert");
  javaWords.add("boolean");
  javaWords.add("break");
  javaWords.add("byte");
  javaWords.add("case");
  javaWords.add("catch");
  javaWords.add("char");
  javaWords.add("class");
  javaWords.add("const");
  javaWords.add("continue");
  javaWords.add("default");
  javaWords.add("do");
  javaWords.add("double");
  javaWords.add("enum");
  javaWords.add("extends");
  javaWords.add("final");
  javaWords.add("finally");
  javaWords.add("float");
  javaWords.add("for");
  javaWords.add("goto");
  javaWords.add("implements");
  javaWords.add("import");
  javaWords.add("instanceof");
  javaWords.add("int");
  javaWords.add("interface");
  javaWords.add("long");
  javaWords.add("native");
  javaWords.add("new");
  javaWords.add("package");
  javaWords.add("private");
  javaWords.add("protected");
  javaWords.add("public");
  javaWords.add("short");
  javaWords.add("static");
  javaWords.add("strictfp");
  javaWords.add("super");
  javaWords.add("switch");
  javaWords.add("synchronized");
  javaWords.add("this");
  javaWords.add("throw");
  javaWords.add("throws");
  javaWords.add("transient");
  javaWords.add("try");
  javaWords.add("volatile");
  javaWords.add("while");
  javaWords.add("null");

  // js words, technically
  javaWords.add("var");
  javaWords.add("delete");
  javaWords.add("function");
  javaWords.add("in");
  javaWords.add("typeof");
  javaWords.add("with");
}

public ParseNode doParsing(Reader in) {
    lexer = new Lexer(in);
    errorCount = 0;

    createJava();

    int result = yyparse();

    if (errorCount == 0)
      return AST;
    else
      return null;
}

public void doParsingAndPrint(Reader in) {
    lexer = new Lexer(in);
    errorCount = 0;

    createJava();

    int result = yyparse();
    System.out.println(AST);
    System.out.println("Number of errors: " + errorCount);
    System.out.println("Return value: " + result);
}
