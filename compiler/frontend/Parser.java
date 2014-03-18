//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 3 "arthur.yacc"
 
    import java.io.Reader;
    import java.io.IOException;
    import java.lang.Math;
 
//#line 23 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short DW=257;
public final static short IF=258;
public final static short ELF=259;
public final static short ELSE=260;
public final static short AND=261;
public final static short OR=262;
public final static short NOT=263;
public final static short RETURN=264;
public final static short DOT=265;
public final static short COMMA=266;
public final static short SEMI=267;
public final static short ARROW=268;
public final static short LPAREN=269;
public final static short RPAREN=270;
public final static short LCURLY=271;
public final static short RCURLY=272;
public final static short LBRACK=273;
public final static short RBRACK=274;
public final static short EQ=275;
public final static short EQ2X=276;
public final static short LT=277;
public final static short LTE=278;
public final static short GT=279;
public final static short GTE=280;
public final static short PLUS=281;
public final static short MINUS=282;
public final static short TIMES=283;
public final static short DIV=284;
public final static short MOD=285;
public final static short EXP=286;
public final static short FUNCTION=287;
public final static short VAR=288;
public final static short VALUE=289;
public final static short ID=290;
public final static short COLOR=291;
public final static short NUMBER=292;
public final static short STRINGLIT=293;
public final static short TRUE=294;
public final static short FALSE=295;
public final static short EOF=296;
public final static short UNKNOWN=297;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    3,    3,    4,    4,    6,    9,
    9,    9,   10,   10,   11,   11,   12,   12,    2,   13,
   16,   17,   18,   15,   15,   19,   19,    8,    8,    8,
    8,    8,    8,    8,    8,   23,   21,   21,   20,   20,
    7,    7,    7,    7,   24,   24,   24,   24,   24,   25,
   25,   25,   25,   25,   25,   22,   22,   26,   26,   26,
   26,   27,   27,   27,   27,   28,   28,   29,   29,   29,
   29,   29,   29,   29,    5,   14,   14,   14,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    0,    1,    1,    3,    5,    7,
    5,    6,    1,    0,    2,    0,    3,    2,    4,    4,
    3,    2,    2,    0,    1,    1,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    3,    1,    2,    2,    1,
    1,    1,    1,    0,    1,    1,    3,    3,    2,    1,
    3,    3,    3,    3,    3,    4,    4,    0,    3,    3,
    1,    3,    3,    3,    1,    3,    1,    1,    1,    1,
    1,    1,    1,    3,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    1,    0,    0,    4,   75,    0,    0,    7,    2,
    3,    0,    0,    0,   19,    8,    0,    0,    0,    0,
   37,    0,   18,   78,   68,    0,   70,    0,    0,    0,
   30,    0,    0,   29,    0,    0,    0,   34,   33,    0,
   31,   32,   35,    0,    0,    0,    0,    0,   67,    0,
    0,   45,   46,    0,    0,   76,    0,   77,   69,   71,
   72,    0,    0,   38,   17,   23,    0,    0,    0,   22,
   39,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   36,   74,    0,   21,
    0,   26,    0,    0,    0,    0,    0,   50,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   66,    0,
    0,   57,   20,    0,   56,    9,    0,   27,    0,   13,
    0,   12,   15,   10,
};
final static short yydgoto[] = {                          3,
    4,    5,    7,    8,   30,   31,   32,   33,   34,  121,
  122,   15,   56,   57,   93,   58,   38,   39,   94,   40,
   41,   42,   43,   44,   45,   46,   47,   48,   49,
};
final static short yysindex[] = {                      -275,
 -272,    0,    0, -252,    0,    0, -232, -219,    0,    0,
    0, -215, -272, -100,    0,    0, -206, -194, -253,  -80,
    0,  -73,    0,    0,    0,    0,    0,    0,    0, -188,
    0, -186, -164,    0, -173, -143, -165,    0,    0,  -87,
    0,    0,    0, -242,  -15, -116,  -16, -175,    0,  -80,
  -80,    0,    0, -242, -140,    0, -251,    0,    0,    0,
    0, -198,  -73,    0,    0,    0, -159,  -80,  -73,    0,
    0, -253, -253, -158, -158, -158, -158, -158,  -73,  -73,
  -73,  -73,  -73,  -73, -129, -123,    0,    0, -189,    0,
 -251,    0, -102,  -93, -161, -242, -242,    0,  -15,  -15,
  -15,  -15,  -15,  -16,  -16, -175, -175, -175,    0,  -87,
  -87,    0,    0,  -80,    0,    0, -170,    0,  -87,    0,
  -85,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
  -89,    0,    0,    0,    0,    0,    0,  -70,    0,    0,
    0,    0,    0, -103,    0,    0,    0,    0,    0,  -83,
    0,  -96,    0,    0,    0,   12,    0,  -38,  -31,    0,
    0,    0, -178,    0, -146,   88,   -9,    0,    0, -245,
    0,    0,    0, -216,  -33,   41, -259,   39,    0,  -11,
  -11,    0,    0,   90,    0,    0,   33,    0,    0,    0,
    0,    0,  -41,    0,    0,    0,    0, -257,  -41,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -68,    0,   92,   97,    0,  109,  111,
  116,  118,  123, -205, -122,   59,   64,   69,    0, -103,
 -103,    0,    0, -120,    0,    0, -139,    0, -103,    0,
 -224,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  183,    0,    0,   16,    0,  -19,  -37,   80,    0,
   95,    0,  -14,  -12,    0,  -10,    0,    0,    0,    0,
    0,    0,    0,  -13,  209,  -17,  178,  313,  125,
};
final static int YYTABLESIZE=396;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         35,
   55,   36,   71,   37,   62,   54,   61,   61,   44,   19,
   61,    1,   24,   67,   28,    6,    9,   68,   72,   73,
    2,   61,   61,   58,   58,   35,   28,   36,   16,   37,
   85,   86,   16,   16,    1,   28,   28,   12,   16,   16,
   52,   53,   16,   10,   16,   89,   13,   16,   92,   41,
   41,   95,   90,   41,   91,   14,   16,   16,   96,   97,
   59,   59,   50,   16,   59,   16,   16,   16,   16,   16,
   16,   88,  116,  117,   51,   59,   59,  112,   40,   40,
   64,  123,   79,   80,   40,   40,   63,   18,   40,  119,
   40,   79,   80,   66,  118,   35,   35,   36,   36,   37,
   37,   70,   40,   40,   35,  115,   36,   65,   37,   40,
   84,   40,   40,   40,   40,   40,   40,   11,   76,   79,
   80,   67,   76,   11,   11,   68,   87,   11,   76,   11,
   24,   69,   11,   98,   76,   76,   76,   76,   76,   76,
  110,   11,   11,   60,   60,   44,  111,   60,   11,   44,
   11,   11,   11,   11,   11,   11,   17,   18,   60,   60,
   58,   58,   19,   20,   79,   80,   21,  113,   22,   17,
   18,   23,  114,   58,  119,   19,   20,   58,   58,   21,
    5,   22,   19,   44,   58,   58,   11,    6,   22,   24,
   25,   26,   27,   28,   29,   22,  120,   58,   58,    6,
    6,   25,   24,   25,   26,   27,   28,   29,  109,   24,
   25,   26,   27,   28,   29,  124,   24,   25,   59,   27,
   60,   61,   45,   45,    0,   58,    0,   45,   45,   46,
   46,   45,   42,   42,   46,   46,   42,    0,   46,   58,
   58,    0,   71,   71,   71,   71,   71,   71,    0,   72,
   72,   72,   72,   72,   72,   77,  104,  105,   44,   77,
   74,   75,   76,   77,   78,   77,   81,   82,   83,   58,
   58,   77,   77,   77,   77,   77,   77,   50,   50,    0,
    0,   50,   99,  100,  101,  102,  103,   50,   50,   50,
   50,   50,   69,   69,   69,   69,   69,   69,   73,   73,
    0,    0,   73,    0,   65,   65,   43,   43,   65,    0,
   43,    0,    0,   73,   73,   73,   73,   73,   73,   65,
   65,   65,   65,   65,   62,   62,    0,    0,   62,   63,
   63,    0,    0,   63,   64,   64,    0,    0,   64,   62,
   62,   62,   62,   62,   63,   63,   63,   63,   63,   64,
   64,   64,   64,   64,   73,   49,   49,   47,   47,   49,
    0,   47,   48,   48,    0,    0,   48,    0,   73,   73,
   73,   73,   73,   73,   55,   55,   51,   51,   55,    0,
   51,   52,   52,   53,   53,   52,    0,   53,   54,   54,
    0,    0,   54,  106,  107,  108,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         14,
   20,   14,   40,   14,   22,   19,  266,  267,  266,  263,
  270,  287,  270,  265,  260,  288,    1,  269,  261,  262,
  296,  281,  282,  281,  282,   40,  272,   40,   13,   40,
   50,   51,  257,  258,  287,  281,  282,  270,  263,  264,
  294,  295,  267,  296,  269,   63,  266,  272,   68,  266,
  267,   69,   67,  270,   67,  271,  281,  282,   72,   73,
  266,  267,  269,  288,  270,  290,  291,  292,  293,  294,
  295,  270,  110,  111,  269,  281,  282,  267,  257,  258,
  267,  119,  281,  282,  263,  264,  275,  258,  267,  260,
  269,  281,  282,  267,  114,  110,  111,  110,  111,  110,
  111,  267,  281,  282,  119,  267,  119,  272,  119,  288,
  286,  290,  291,  292,  293,  294,  295,  257,  265,  281,
  282,  265,  269,  263,  264,  269,  267,  267,  275,  269,
  290,  275,  272,  292,  281,  282,  283,  284,  285,  286,
  270,  281,  282,  266,  267,  266,  270,  270,  288,  270,
  290,  291,  292,  293,  294,  295,  257,  258,  281,  282,
  281,  282,  263,  264,  281,  282,  267,  270,  269,  257,
  258,  272,  266,  270,  260,  263,  264,  281,  282,  267,
  270,  269,  263,  267,  281,  282,    4,  288,  269,  290,
  291,  292,  293,  294,  295,  269,  117,  281,  282,  270,
  288,  270,  290,  291,  292,  293,  294,  295,   84,  290,
  291,  292,  293,  294,  295,  121,  290,  291,  292,  293,
  294,  295,  261,  262,   -1,  267,   -1,  266,  267,  261,
  262,  270,  266,  267,  266,  267,  270,   -1,  270,  281,
  282,   -1,  281,  282,  283,  284,  285,  286,   -1,  281,
  282,  283,  284,  285,  286,  265,   79,   80,  270,  269,
  276,  277,  278,  279,  280,  275,  283,  284,  285,  281,
  282,  281,  282,  283,  284,  285,  286,  266,  267,   -1,
   -1,  270,   74,   75,   76,   77,   78,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  286,  266,  267,
   -1,   -1,  270,   -1,  266,  267,  266,  267,  270,   -1,
  270,   -1,   -1,  281,  282,  283,  284,  285,  286,  281,
  282,  283,  284,  285,  266,  267,   -1,   -1,  270,  266,
  267,   -1,   -1,  270,  266,  267,   -1,   -1,  270,  281,
  282,  283,  284,  285,  281,  282,  283,  284,  285,  281,
  282,  283,  284,  285,  267,  266,  267,  266,  267,  270,
   -1,  270,  266,  267,   -1,   -1,  270,   -1,  281,  282,
  283,  284,  285,  286,  266,  267,  266,  267,  270,   -1,
  270,  266,  267,  266,  267,  270,   -1,  270,  266,  267,
   -1,   -1,  270,   81,   82,   83,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=297;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"DW","IF","ELF","ELSE","AND","OR","NOT","RETURN","DOT","COMMA",
"SEMI","ARROW","LPAREN","RPAREN","LCURLY","RCURLY","LBRACK","RBRACK","EQ",
"EQ2X","LT","LTE","GT","GTE","PLUS","MINUS","TIMES","DIV","MOD","EXP",
"FUNCTION","VAR","VALUE","ID","COLOR","NUMBER","STRINGLIT","TRUE","FALSE","EOF",
"UNKNOWN",
};
final static String yyrule[] = {
"$accept : program",
"program : EOF",
"program : stuff EOF",
"stuff : stuff func_def",
"stuff : func_def",
"param_list :",
"param_list : hard_param_list",
"hard_param_list : var",
"hard_param_list : hard_param_list COMMA var",
"dw_stmt : DW LPAREN expr RPAREN stmt",
"if_stmt : IF LPAREN expr RPAREN stmt elf else",
"if_stmt : IF LPAREN expr RPAREN stmt",
"if_stmt : IF LPAREN expr RPAREN stmt else",
"elf : if_stmt",
"elf :",
"else : ELSE stmt",
"else :",
"func_body : LCURLY stmt RCURLY",
"func_body : LCURLY RCURLY",
"func_def : FUNCTION param_list RPAREN func_body",
"fun_call : id LPAREN arg_list RPAREN",
"meth_call : id DOT fun_call",
"meth_call_stmt : meth_call SEMI",
"fun_call_stmt : fun_call SEMI",
"arg_list :",
"arg_list : hard_arg_list",
"hard_arg_list : expr",
"hard_arg_list : hard_arg_list COMMA expr",
"stmt : stmt_list",
"stmt : if_stmt",
"stmt : dw_stmt",
"stmt : expr_stmt",
"stmt : eq_stmt",
"stmt : fun_call_stmt",
"stmt : meth_call_stmt",
"stmt : return_stmt",
"return_stmt : RETURN expr SEMI",
"expr_stmt : SEMI",
"expr_stmt : expr SEMI",
"stmt_list : stmt_list stmt",
"stmt_list : stmt",
"expr : bool_expr",
"expr : num_expr",
"expr : val",
"expr :",
"bool_expr : TRUE",
"bool_expr : FALSE",
"bool_expr : bool_expr AND bool_expr",
"bool_expr : bool_expr OR bool_expr",
"bool_expr : NOT bool_expr",
"num_expr : NUMBER",
"num_expr : num_expr LT num_expr",
"num_expr : num_expr LTE num_expr",
"num_expr : num_expr GT num_expr",
"num_expr : num_expr GTE num_expr",
"num_expr : num_expr EQ2X num_expr",
"eq_stmt : id EQ val SEMI",
"eq_stmt : var EQ val SEMI",
"val :",
"val : val PLUS term",
"val : val MINUS term",
"val : term",
"term : term TIMES exfactor",
"term : term DIV exfactor",
"term : term MOD exfactor",
"term : exfactor",
"exfactor : exfactor EXP factor",
"exfactor : factor",
"factor : COLOR",
"factor : NUMBER",
"factor : STRINGLIT",
"factor : TRUE",
"factor : FALSE",
"factor : id",
"factor : LPAREN val RPAREN",
"var : VAR",
"id : fun_call",
"id : meth_call",
"id : ID",
};

//#line 462 "arthur.yacc"
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
//#line 493 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 27 "arthur.yacc"
{ ParseNode p = new ParseNode("arthur"); yyval = new ParserVal(p); System.out.println(yyval.obj);}
break;
case 2:
//#line 28 "arthur.yacc"
{ yyval = val_peek(1); System.out.println(val_peek(1).obj); }
break;
case 3:
//#line 32 "arthur.yacc"
{
                                                        ParseNode s = (ParseNode) val_peek(1).obj;
                                                        ParseNode f = (ParseNode) val_peek(0).obj;
                                                        s.addChild(f);
                                                        yyval = val_peek(1);
                                                    }
break;
case 4:
//#line 38 "arthur.yacc"
{ 
                                                        ParseNode p = new ParseNode("arthur"); 
                                                        p.addChild((ParseNode) val_peek(0).obj);
                                                        yyval = new ParserVal(p);
                                                    }
break;
case 5:
//#line 46 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("parameters")); }
break;
case 6:
//#line 47 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 7:
//#line 51 "arthur.yacc"
{
                                                        ParseNode params = new ParseNode("parameters");
                                                        ParseNode var = (ParseNode) val_peek(0).obj;
                                                        var.setParent(params);
                                                        params.addChild(var);
                                                        yyval = new ParserVal(params);
                                                    }
break;
case 8:
//#line 58 "arthur.yacc"
{
                                                        ParseNode params = (ParseNode) val_peek(2).obj;
                                                        ParseNode var = (ParseNode) val_peek(0).obj;
                                                        var.setParent(params);
                                                        params.addChild(var);
                                                        yyval = val_peek(2);
                                                    }
break;
case 9:
//#line 68 "arthur.yacc"
{
                                                      ParseNode dw = new ParseNode("dw");
                                                      ParseNode expr = (ParseNode) val_peek(2).obj;
                                                      ParseNode stmt = (ParseNode) val_peek(0).obj;
                                                      dw.addChild(expr);
                                                      dw.addChild(stmt);
                                                      yyval = new ParserVal(dw);
                                                    }
break;
case 10:
//#line 79 "arthur.yacc"
{
                                                      ParseNode ifmaster = new ParseNode("if");
                                                      ParseNode iffer = new ParseNode("iffer", ifmaster);
                                                      ParseNode expr = (ParseNode) val_peek(4).obj;
                                                      ParseNode stmt = (ParseNode) val_peek(2).obj;
                                                      iffer.addChild(expr);
                                                      iffer.addChild(stmt);
                                                      ifmaster.addChild(iffer);
                                                      ParseNode elfer = (ParseNode) val_peek(1).obj;
                                                      if (elfer != null)
                                                          ifmaster.addChild(elfer);
                                                      ParseNode elser = (ParseNode) val_peek(0).obj;
                                                      if (elser != null)
                                                          ifmaster.addChild(elser);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 11:
//#line 95 "arthur.yacc"
{
                                                      ParseNode ifmaster = new ParseNode("if");
                                                      ParseNode expr = (ParseNode) val_peek(2).obj;
                                                      ParseNode stmt = (ParseNode) val_peek(0).obj;
                                                      ifmaster.addChild(expr);
                                                      ifmaster.addChild(stmt);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 12:
//#line 103 "arthur.yacc"
{
                                                      ParseNode ifmaster = new ParseNode("if");
                                                      ParseNode iffer = new ParseNode("iffer", ifmaster);
                                                      ParseNode expr = (ParseNode) val_peek(3).obj;
                                                      ParseNode stmt = (ParseNode) val_peek(1).obj;
                                                      iffer.addChild(expr);
                                                      iffer.addChild(stmt);
                                                      ifmaster.addChild(iffer);
                                                      ParseNode elser = (ParseNode) val_peek(0).obj;
                                                      if (elser != null)
                                                          ifmaster.addChild(elser);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 13:
//#line 119 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 14:
//#line 120 "arthur.yacc"
{ yyval = new ParserVal(null); }
break;
case 15:
//#line 124 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 16:
//#line 125 "arthur.yacc"
{ yyval = new ParserVal(null); }
break;
case 17:
//#line 129 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 18:
//#line 130 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 19:
//#line 134 "arthur.yacc"
{   
                                                        ParseNode funDef = new ParseNode("Function definition");
                                                        Function f = (Function) val_peek(3).obj;
                                                        funDef.addChild(new ParseNode(f.returnType, funDef));
                                                        funDef.addChild(new ParseNode(f.name, funDef));
                                                        ParseNode params = (ParseNode) val_peek(2).obj;
                                                        params.setParent(funDef); /*can take statements of this nature out later if parent becomes obsolete*/
                                                        funDef.addChild(params);
                                                        ParseNode body = (ParseNode) val_peek(0).obj;
                                                        body.setParent(funDef);
                                                        funDef.addChild(body);
                                                        yyval = new ParserVal(funDef);
                                                    }
break;
case 20:
//#line 150 "arthur.yacc"
{
                                                      ParseNode funCall = new ParseNode("Function call");
                                                      ParseNode name = (ParseNode) val_peek(3).obj;
                                                      funCall.addChild(name);
                                                      ParseNode args = (ParseNode) val_peek(1).obj;
                                                      funCall.addChild(args);
                                                      yyval = new ParserVal(funCall);
                                                    }
break;
case 21:
//#line 161 "arthur.yacc"
{
                                                      ParseNode methCall = new ParseNode("Method call");
                                                      ParseNode ob = (ParseNode) val_peek(2).obj;
                                                      methCall.addChild(ob);
                                                      ParseNode fun = (ParseNode) val_peek(0).obj;
                                                      methCall.addChild(fun);
                                                      yyval = new ParserVal(methCall);
                                                    }
break;
case 22:
//#line 172 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 23:
//#line 176 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 24:
//#line 180 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("arguments")); }
break;
case 25:
//#line 181 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 26:
//#line 185 "arthur.yacc"
{
                                                        ParseNode params = new ParseNode("arguments");
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = new ParserVal(params);
                                                    }
break;
case 27:
//#line 192 "arthur.yacc"
{
                                                        ParseNode params = (ParseNode) val_peek(2).obj;
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = val_peek(2);
                                                    }
break;
case 28:
//#line 202 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 29:
//#line 203 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: if");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 30:
//#line 208 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: dw");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 31:
//#line 213 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: expr");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 32:
//#line 218 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: eq");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 33:
//#line 223 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: fun_call");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 34:
//#line 228 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: meth_call");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 35:
//#line 233 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: return");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 36:
//#line 241 "arthur.yacc"
{
                                                      ParseNode e = (ParseNode) val_peek(1).obj;
                                                      ParseNode r = new ParseNode("return");
                                                      r.addChild(e);
                                                      yyval = new ParserVal(r);
                                                    }
break;
case 37:
//#line 250 "arthur.yacc"
{ yyval = new ParserVal(null); }
break;
case 38:
//#line 251 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 39:
//#line 255 "arthur.yacc"
{ 
                                                      ParseNode list = (ParseNode) val_peek(1).obj;
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      list.addChild(s);
                                                      yyval = val_peek(1);
                                                    }
break;
case 40:
//#line 261 "arthur.yacc"
{
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      ParseNode list = new ParseNode("stmt_list");
                                                      list.addChild(s);
                                                      yyval = new ParserVal(list);
                                                    }
break;
case 41:
//#line 270 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 42:
//#line 271 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 43:
//#line 272 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 44:
//#line 273 "arthur.yacc"
{ yyval = new ParserVal(null); }
break;
case 45:
//#line 277 "arthur.yacc"
{ ParseNode bool = new ParseNode("true"); yyval = new ParserVal(bool); }
break;
case 46:
//#line 278 "arthur.yacc"
{ ParseNode bool = new ParseNode("false"); yyval = new ParserVal(bool); }
break;
case 47:
//#line 279 "arthur.yacc"
{ 
                                                      ParseNode and = new ParseNode("and");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      and.addChild(b1); b1.setParent(and);
                                                      and.addChild(b2); b2.setParent(and);
                                                      yyval = new ParserVal(and);
                                                    }
break;
case 48:
//#line 286 "arthur.yacc"
{ 
                                                      ParseNode or = new ParseNode("or");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      or.addChild(b1); b1.setParent(or);
                                                      or.addChild(b2); b2.setParent(or);
                                                      yyval = new ParserVal(or);
                                                    }
break;
case 49:
//#line 293 "arthur.yacc"
{ 
                                                      ParseNode b = (ParseNode) val_peek(0).obj;
                                                      ParseNode not = new ParseNode("not");
                                                      not.addChild(b); not.setParent(b.parent); b.setParent(not);
                                                      yyval = new ParserVal(not);
                                                    }
break;
case 50:
//#line 302 "arthur.yacc"
{ ParseNode number = new ParseNode("number"); yyval = new ParserVal(number); }
break;
case 51:
//#line 303 "arthur.yacc"
{
                                                      ParseNode lt = new ParseNode("less than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lt.addChild(ne1); ne1.setParent(lt);
                                                      lt.addChild(ne2); ne2.setParent(lt);
                                                      yyval = new ParserVal(lt);
                                                    }
break;
case 52:
//#line 310 "arthur.yacc"
{
                                                      ParseNode lte = new ParseNode("less than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lte.addChild(ne1); ne1.setParent(lte);
                                                      lte.addChild(ne2); ne2.setParent(lte);
                                                      yyval = new ParserVal(lte);
                                                    }
break;
case 53:
//#line 317 "arthur.yacc"
{
                                                      ParseNode gt = new ParseNode("greater than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gt.addChild(ne1); ne1.setParent(gt);
                                                      gt.addChild(ne2); ne2.setParent(gt);
                                                      yyval = new ParserVal(gt);
                                                    }
break;
case 54:
//#line 324 "arthur.yacc"
{
                                                      ParseNode gte = new ParseNode("greater than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gte.addChild(ne1); ne1.setParent(gte);
                                                      gte.addChild(ne2); ne2.setParent(gte);
                                                      yyval = new ParserVal(gte);
                                                    }
break;
case 55:
//#line 331 "arthur.yacc"
{
                                                      ParseNode eq2x = new ParseNode("is equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      eq2x.addChild(ne1); ne1.setParent(eq2x);
                                                      eq2x.addChild(ne2); ne2.setParent(eq2x);
                                                      yyval = new ParserVal(eq2x);
                                                    }
break;
case 56:
//#line 341 "arthur.yacc"
{
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) val_peek(3).obj;
                                                        ParseNode val = (ParseNode) val_peek(1).obj;
                                                        id.setParent(eq); /*again, can take these out later if need be*/
                                                        val.setParent(eq);
                                                        eq.addChild(id);
                                                        eq.addChild(val);    
                                                        yyval = new ParserVal(eq);
                                                    }
break;
case 57:
//#line 351 "arthur.yacc"
{
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode var = (ParseNode) val_peek(3).obj;
                                                        ParseNode val = (ParseNode) val_peek(1).obj;
                                                        var.setParent(eq);
                                                        val.setParent(eq);
                                                        eq.addChild(var);
                                                        eq.addChild(val);
                                                        yyval = new ParserVal(eq);
                                                    }
break;
case 59:
//#line 365 "arthur.yacc"
{ 
                                                        ParseNode plus = new ParseNode("+");
                                                        plus.addChild((ParseNode)val_peek(2).obj);
                                                        plus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(plus);
                                                    }
break;
case 60:
//#line 371 "arthur.yacc"
{ 
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.addChild((ParseNode)val_peek(2).obj);
                                                        minus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(minus);
                                                    }
break;
case 61:
//#line 377 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 62:
//#line 381 "arthur.yacc"
{ 
                                                        ParseNode times = new ParseNode("*");
                                                        times.addChild((ParseNode)val_peek(2).obj);
                                                        times.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(times);
                                                    }
break;
case 63:
//#line 387 "arthur.yacc"
{ 
                                                        ParseNode div = new ParseNode("/");
                                                        div.addChild((ParseNode)val_peek(2).obj);
                                                        div.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(div);
                                                    }
break;
case 64:
//#line 393 "arthur.yacc"
{ 
                                                        ParseNode mod = new ParseNode("%");
                                                        mod.addChild((ParseNode)val_peek(2).obj);
                                                        mod.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(mod);
                                                    }
break;
case 65:
//#line 399 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 66:
//#line 403 "arthur.yacc"
{ 
                                                        ParseNode exp = new ParseNode("^");
                                                        exp.addChild((ParseNode)val_peek(2).obj);
                                                        exp.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(exp);
                                                    }
break;
case 67:
//#line 409 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 68:
//#line 413 "arthur.yacc"
{
                                                        Color c = (Color) val_peek(0).obj;
                                                        ParseNode color = new ParseNode("Color literal");
                                                        color.addChild(new ParseNode(c.r.toString(), color));
                                                        color.addChild(new ParseNode(c.g.toString(), color));
                                                        color.addChild(new ParseNode(c.b.toString(), color));
                                                        color.addChild(new ParseNode(c.a.toString(), color));
                                                        yyval = new ParserVal(color);
                                                    }
break;
case 69:
//#line 422 "arthur.yacc"
{
                                                        Number n = (Number) val_peek(0).obj;
                                                        ParseNode number = new ParseNode("Number literal");
                                                        number.addChild(new ParseNode(n.val.toString(), number)); 
                                                        yyval = new ParserVal(number);    
                                                    }
break;
case 70:
//#line 428 "arthur.yacc"
{
                                                        StringLit s = (StringLit) val_peek(0).obj;
                                                        ParseNode string = new ParseNode("String literal");
                                                        string.addChild(new ParseNode(s.val, string)); 
                                                        yyval = new ParserVal(string); 
                                                    }
break;
case 71:
//#line 434 "arthur.yacc"
{ ParseNode t = new ParseNode("true"); yyval = new ParserVal(t); }
break;
case 72:
//#line 435 "arthur.yacc"
{ ParseNode f = new ParseNode("false"); yyval = new ParserVal(f); }
break;
case 73:
//#line 436 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 74:
//#line 437 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 75:
//#line 441 "arthur.yacc"
{
                                                        Var v = (Var) val_peek(0).obj;
                                                        ParseNode var = new ParseNode("variable");
                                                        var.addChild(new ParseNode(v.typeName(), var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        yyval = new ParserVal(var);
                                                    }
break;
case 76:
//#line 451 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 77:
//#line 452 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 78:
//#line 453 "arthur.yacc"
{
                                                        Identifier i = (Identifier) val_peek(0).obj;
                                                        ParseNode id = new ParseNode("Identifier");
                                                        id.addChild(new ParseNode(i.name, id));
                                                        yyval = new ParserVal(id); 
                                                    }
break;
//#line 1217 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
