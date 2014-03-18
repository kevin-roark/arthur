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
    9,    9,    9,   10,   10,   12,   11,   13,   13,    2,
   14,   17,   18,   19,   16,   16,   20,   20,    8,    8,
    8,    8,    8,    8,    8,    8,   24,   22,   22,   21,
   21,   25,   25,    7,    7,    7,    7,   26,   26,   26,
   26,   26,   27,   27,   27,   27,   27,   27,   23,   23,
   28,   28,   28,   28,   29,   29,   29,   29,   30,   30,
   31,   31,   31,   31,   31,   31,   31,    5,   15,   15,
   15,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    0,    1,    1,    3,    5,    7,
    6,    5,    6,    2,    1,    5,    2,    3,    2,    4,
    4,    3,    2,    2,    0,    1,    1,    3,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    1,    2,    3,
    1,    2,    1,    1,    1,    1,    0,    1,    1,    3,
    3,    2,    1,    3,    3,    3,    3,    3,    4,    4,
    0,    3,    3,    1,    3,    3,    3,    1,    3,    1,
    1,    1,    1,    1,    1,    1,    3,    1,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
    0,    1,    0,    0,    4,   78,    0,    0,    7,    2,
    3,    0,    0,    0,   20,    8,    0,    0,    0,    0,
   38,    0,    0,   19,   81,   71,    0,   73,    0,    0,
    0,   31,    0,    0,   30,    0,    0,    0,   35,   34,
   29,   32,   33,   36,    0,    0,    0,    0,    0,    0,
   70,    0,    0,   48,   49,    0,    0,   79,    0,   80,
   72,   74,   75,    0,   43,    0,    0,   39,   18,   24,
    0,    0,    0,   23,   42,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   37,   77,   40,    0,   22,    0,   27,    0,    0,    0,
    0,    0,   53,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   69,    0,    0,   60,   21,    0,   59,
    9,    0,   28,    0,    0,    0,   13,   15,    0,   17,
   10,   14,    0,    0,   16,
};
final static short yydgoto[] = {                          3,
    4,    5,    7,    8,   31,   32,   33,   75,   35,  126,
  127,  128,   15,   58,   59,   98,   60,   39,   40,   99,
   41,   42,   43,   44,   45,   46,   47,   48,   49,   50,
   51,
};
final static short yysindex[] = {                      -284,
 -278,    0,    0, -280,    0,    0, -249, -232,    0,    0,
    0, -216, -278,  -51,    0,    0, -221, -211, -258, -144,
    0, -268,    7,    0,    0,    0,    0,    0,    0,    0,
 -208,    0, -180, -183,    0, -167,  -19, -158,    0,    0,
    0,    0,    0,    0,    7,    5,   47,   22, -100, -174,
    0, -144, -144,    0,    0,    5, -146,    0, -155,    0,
    0,    0,    0, -191,    0,   -9, -268,    0,    0,    0,
 -154, -144, -268,    0,    0, -258, -258, -169, -169, -169,
 -169, -169, -268, -268, -268, -268, -268, -268, -131, -126,
    0,    0,    0, -198,    0, -155,    0, -110,  -88, -196,
    5,    5,    0,   47,   47,   47,   47,   47, -100, -100,
 -174, -174, -174,    0,    7,    7,    0,    0, -144,    0,
    0,   46,    0,  -93,    7,   46,    0,    0, -144,    0,
    0,    0,  -56,    7,    0,
};
final static short yyrindex[] = {                         0,
  -46,    0,    0,    0,    0,    0,    0,  -43,    0,    0,
    0,    0,    0,   65,    0,    0,    0,    0,    0, -151,
    0, -189,   65,    0,    0,    0,   73,    0,   26,   52,
    0,    0,    0, -218,    0, -187,  130,  -50,    0,    0,
    0,    0,    0,    0, -240, -223,  -44,    2, -112,  -95,
    0,  -53,  -53,    0,    0,   24,    0,    0,   94,    0,
    0,    0,    0,    0,    0,   81, -149,    0,    0,    0,
    0, -252, -149,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -40,    0,
   50,   75,    0,  102,  151,  153,  158,  160,  -73,  -71,
  100,  120,  125,    0,   65,   65,    0,    0, -125,    0,
    0, -129,    0,    0,   65,  -90,    0,    0,  -53,    0,
    0,    0,    0,   65,    0,
};
final static short yygindex[] = {                         0,
    0,  176,    0,    0,   37,    0,    8,   -8,    0,    0,
  112,  119,    0,  -14,  -12,    0,  -10,    0,    0,    0,
    0,    0,    0,    0,  224,  -11,  250,   -5,  290,  166,
  169,
};
final static int YYTABLESIZE=430;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         36,
   22,   37,    1,   38,   19,   34,    1,   56,   36,    6,
   37,    2,   38,   47,   65,   10,   64,   25,   41,   41,
   12,   25,   26,   61,   28,   62,   63,   57,   61,   61,
   36,   41,   37,   13,   38,   54,   55,    9,   43,   43,
   41,   41,   44,   44,   43,   43,   44,   52,   43,   16,
   43,   36,   43,   37,   14,   38,   95,   53,   96,   89,
   90,   94,   43,   43,  101,  102,   67,  100,  117,   43,
  120,   43,   43,   43,   43,   43,   43,   79,   92,   97,
   61,   79,   83,   84,   83,   84,   68,   79,   69,   83,
   84,   61,   61,   79,   79,   79,   79,   79,   79,   70,
   36,   36,   37,   37,   38,   38,  121,  122,   74,   71,
   36,   88,   37,   72,   38,   47,  130,   61,   19,   36,
   91,   37,  103,   38,   22,  135,  123,   12,   12,   61,
   61,   61,   61,   12,   12,   25,  133,   12,  115,   12,
   47,   12,   12,  116,   47,   25,   26,   27,   28,   29,
   30,   12,   12,   64,   64,   61,   61,   64,   12,  118,
   12,   12,   12,   12,   12,   12,   11,   11,   64,   64,
   68,   68,   11,   11,   68,  129,   11,  119,   11,   11,
   11,   11,   85,   86,   87,   68,   68,   68,   68,   68,
   11,   11,   62,   62,   63,   63,   62,   11,   63,   11,
   11,   11,   11,   11,   11,   17,   18,   62,   62,   63,
   63,   19,   20,  134,   80,   21,   47,   22,   80,   23,
   24,   45,   45,    5,   80,   45,    6,   61,   61,   26,
   80,   80,   80,   80,   80,   80,    6,  131,   25,   26,
   27,   28,   29,   30,  132,   71,   66,   17,   18,   72,
  111,  112,  113,   19,   20,   73,  114,   21,    0,   22,
    0,   23,   93,   17,   18,   76,   77,   46,   46,   19,
   20,   46,    0,   21,    0,   22,    0,   23,    6,    0,
   25,   26,   27,   28,   29,   30,   48,   48,    0,   52,
   52,   48,   48,   52,    6,   48,   25,   26,   27,   28,
   29,   30,   83,   84,  124,  125,   74,   74,   74,   74,
   74,   74,   49,   49,    0,   50,   50,   49,   49,   50,
    0,   49,   78,   79,   80,   81,   82,  104,  105,  106,
  107,  108,   75,   75,   75,   75,   75,   75,   53,   53,
   51,   51,   53,    0,   51,   61,   61,    0,   53,   53,
   53,   53,   53,   72,   72,   72,   72,   72,   72,   76,
   76,   41,   41,   76,    0,   65,   65,   58,   58,   65,
    0,   58,  109,  110,   76,   76,   76,   76,   76,   76,
   65,   65,   65,   65,   65,   66,   66,    0,    0,   66,
   67,   67,    0,    0,   67,    0,   76,    0,    0,    0,
   66,   66,   66,   66,   66,   67,   67,   67,   67,   67,
   76,   76,   76,   76,   76,   76,   54,   54,   55,   55,
   54,    0,   55,   56,   56,   57,   57,   56,    0,   57,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         14,
  269,   14,  287,   14,  263,   14,  287,   19,   23,  288,
   23,  296,   23,  266,   23,  296,   22,  270,  259,  260,
  270,  290,  291,  292,  293,  294,  295,   20,  281,  282,
   45,  272,   45,  266,   45,  294,  295,    1,  257,  258,
  281,  282,  266,  267,  263,  264,  270,  269,  267,   13,
  269,   66,  271,   66,  271,   66,   71,  269,   71,   52,
   53,   67,  281,  282,   76,   77,  275,   73,  267,  288,
  267,  290,  291,  292,  293,  294,  295,  265,  270,   72,
  270,  269,  281,  282,  281,  282,  267,  275,  272,  281,
  282,  281,  282,  281,  282,  283,  284,  285,  286,  267,
  115,  116,  115,  116,  115,  116,  115,  116,  267,  265,
  125,  286,  125,  269,  125,  267,  125,  267,  263,  134,
  267,  134,  292,  134,  269,  134,  119,  257,  258,  281,
  282,  281,  282,  263,  264,  290,  129,  267,  270,  269,
  266,  271,  272,  270,  270,  290,  291,  292,  293,  294,
  295,  281,  282,  266,  267,  281,  282,  270,  288,  270,
  290,  291,  292,  293,  294,  295,  257,  258,  281,  282,
  266,  267,  263,  264,  270,  269,  267,  266,  269,    4,
  271,  272,  283,  284,  285,  281,  282,  283,  284,  285,
  281,  282,  266,  267,  266,  267,  270,  288,  270,  290,
  291,  292,  293,  294,  295,  257,  258,  281,  282,  281,
  282,  263,  264,  270,  265,  267,  270,  269,  269,  271,
  272,  266,  267,  270,  275,  270,  270,  281,  282,  270,
  281,  282,  283,  284,  285,  286,  288,  126,  290,  291,
  292,  293,  294,  295,  126,  265,   23,  257,  258,  269,
   85,   86,   87,  263,  264,  275,   88,  267,   -1,  269,
   -1,  271,  272,  257,  258,  261,  262,  266,  267,  263,
  264,  270,   -1,  267,   -1,  269,   -1,  271,  288,   -1,
  290,  291,  292,  293,  294,  295,  261,  262,   -1,  266,
  267,  266,  267,  270,  288,  270,  290,  291,  292,  293,
  294,  295,  281,  282,  259,  260,  281,  282,  283,  284,
  285,  286,  261,  262,   -1,  266,  267,  266,  267,  270,
   -1,  270,  276,  277,  278,  279,  280,   78,   79,   80,
   81,   82,  281,  282,  283,  284,  285,  286,  266,  267,
  266,  267,  270,   -1,  270,  281,  282,   -1,  276,  277,
  278,  279,  280,  281,  282,  283,  284,  285,  286,  266,
  267,  281,  282,  270,   -1,  266,  267,  266,  267,  270,
   -1,  270,   83,   84,  281,  282,  283,  284,  285,  286,
  281,  282,  283,  284,  285,  266,  267,   -1,   -1,  270,
  266,  267,   -1,   -1,  270,   -1,  267,   -1,   -1,   -1,
  281,  282,  283,  284,  285,  281,  282,  283,  284,  285,
  281,  282,  283,  284,  285,  286,  266,  267,  266,  267,
  270,   -1,  270,  266,  267,  266,  267,  270,   -1,  270,
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
"if_stmt : IF LPAREN expr RPAREN stmt elfs else",
"if_stmt : IF LPAREN expr RPAREN stmt elfs",
"if_stmt : IF LPAREN expr RPAREN stmt",
"if_stmt : IF LPAREN expr RPAREN stmt else",
"elfs : elfs elf",
"elfs : elf",
"elf : ELF LPAREN expr RPAREN stmt",
"else : ELSE stmt",
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
"stmt : stmt_block",
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
"stmt_block : LCURLY stmt_list RCURLY",
"stmt_block : stmt_list",
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

//#line 494 "arthur.yacc"
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
//#line 509 "Parser.java"
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
                                                      ifmaster.addChild(elfer);
                                                      ParseNode elser = (ParseNode) val_peek(0).obj;
                                                      ifmaster.addChild(elser);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 11:
//#line 93 "arthur.yacc"
{
                                                      ParseNode ifmaster = new ParseNode("if");
                                                      ParseNode iffer = new ParseNode("iffer", ifmaster);
                                                      ParseNode expr = (ParseNode) val_peek(3).obj;
                                                      ParseNode stmt = (ParseNode) val_peek(1).obj;
                                                      iffer.addChild(expr);
                                                      iffer.addChild(stmt);
                                                      ifmaster.addChild(iffer);
                                                      ParseNode elfer = (ParseNode) val_peek(0).obj;
                                                      ifmaster.addChild(elfer);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 12:
//#line 105 "arthur.yacc"
{
                                                      ParseNode ifmaster = new ParseNode("if");
                                                      ParseNode expr = (ParseNode) val_peek(2).obj;
                                                      ParseNode stmt = (ParseNode) val_peek(0).obj;
                                                      ifmaster.addChild(expr);
                                                      ifmaster.addChild(stmt);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 13:
//#line 113 "arthur.yacc"
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
case 14:
//#line 129 "arthur.yacc"
{
                                                      ParseNode elfs = (ParseNode) val_peek(1).obj;
                                                      ParseNode elf = (ParseNode) val_peek(0).obj;
                                                      elfs.addChild(elf);
                                                      yyval = val_peek(1);
                                                    }
break;
case 15:
//#line 135 "arthur.yacc"
{
                                                      ParseNode elfs = new ParseNode("elves");
                                                      elfs.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(elfs);
                                                    }
break;
case 16:
//#line 143 "arthur.yacc"
{
                                                        ParseNode elf = new ParseNode("elf");
                                                        elf.addChild((ParseNode) val_peek(2).obj);
                                                        elf.addChild((ParseNode) val_peek(0).obj);
                                                        yyval = new ParserVal(elf);
                                                    }
break;
case 17:
//#line 152 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 18:
//#line 156 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 19:
//#line 157 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 20:
//#line 161 "arthur.yacc"
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
case 21:
//#line 177 "arthur.yacc"
{
                                                      ParseNode funCall = new ParseNode("Function call");
                                                      ParseNode name = (ParseNode) val_peek(3).obj;
                                                      funCall.addChild(name);
                                                      ParseNode args = (ParseNode) val_peek(1).obj;
                                                      funCall.addChild(args);
                                                      yyval = new ParserVal(funCall);
                                                    }
break;
case 22:
//#line 188 "arthur.yacc"
{
                                                      ParseNode methCall = new ParseNode("Method call");
                                                      ParseNode ob = (ParseNode) val_peek(2).obj;
                                                      methCall.addChild(ob);
                                                      ParseNode fun = (ParseNode) val_peek(0).obj;
                                                      methCall.addChild(fun);
                                                      yyval = new ParserVal(methCall);
                                                    }
break;
case 23:
//#line 199 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 24:
//#line 203 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 25:
//#line 207 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("arguments")); }
break;
case 26:
//#line 208 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 27:
//#line 212 "arthur.yacc"
{
                                                        ParseNode params = new ParseNode("arguments");
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = new ParserVal(params);
                                                    }
break;
case 28:
//#line 219 "arthur.yacc"
{
                                                        ParseNode params = (ParseNode) val_peek(2).obj;
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = val_peek(2);
                                                    }
break;
case 29:
//#line 229 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 30:
//#line 230 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: if");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 31:
//#line 235 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: dw");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 32:
//#line 240 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: expr");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 33:
//#line 245 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: eq");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 34:
//#line 250 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: fun_call");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 35:
//#line 255 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: meth_call");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 36:
//#line 260 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: return");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 37:
//#line 268 "arthur.yacc"
{
                                                      ParseNode e = (ParseNode) val_peek(1).obj;
                                                      ParseNode r = new ParseNode("return");
                                                      r.addChild(e);
                                                      yyval = new ParserVal(r);
                                                    }
break;
case 38:
//#line 277 "arthur.yacc"
{ yyval = new ParserVal(null); }
break;
case 39:
//#line 278 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 40:
//#line 282 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 41:
//#line 283 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 42:
//#line 287 "arthur.yacc"
{ 
                                                      ParseNode list = (ParseNode) val_peek(1).obj;
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      list.addChild(s);
                                                      yyval = val_peek(1);
                                                    }
break;
case 43:
//#line 293 "arthur.yacc"
{
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      ParseNode list = new ParseNode("stmt_list");
                                                      list.addChild(s);
                                                      yyval = new ParserVal(list);
                                                    }
break;
case 44:
//#line 302 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 45:
//#line 303 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 46:
//#line 304 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 47:
//#line 305 "arthur.yacc"
{ yyval = new ParserVal(null); }
break;
case 48:
//#line 309 "arthur.yacc"
{ ParseNode bool = new ParseNode("true"); yyval = new ParserVal(bool); }
break;
case 49:
//#line 310 "arthur.yacc"
{ ParseNode bool = new ParseNode("false"); yyval = new ParserVal(bool); }
break;
case 50:
//#line 311 "arthur.yacc"
{ 
                                                      ParseNode and = new ParseNode("and");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      and.addChild(b1); b1.setParent(and);
                                                      and.addChild(b2); b2.setParent(and);
                                                      yyval = new ParserVal(and);
                                                    }
break;
case 51:
//#line 318 "arthur.yacc"
{ 
                                                      ParseNode or = new ParseNode("or");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      or.addChild(b1); b1.setParent(or);
                                                      or.addChild(b2); b2.setParent(or);
                                                      yyval = new ParserVal(or);
                                                    }
break;
case 52:
//#line 325 "arthur.yacc"
{ 
                                                      ParseNode b = (ParseNode) val_peek(0).obj;
                                                      ParseNode not = new ParseNode("not");
                                                      not.addChild(b); not.setParent(b.parent); b.setParent(not);
                                                      yyval = new ParserVal(not);
                                                    }
break;
case 53:
//#line 334 "arthur.yacc"
{ ParseNode number = new ParseNode("number"); yyval = new ParserVal(number); }
break;
case 54:
//#line 335 "arthur.yacc"
{
                                                      ParseNode lt = new ParseNode("less than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lt.addChild(ne1); ne1.setParent(lt);
                                                      lt.addChild(ne2); ne2.setParent(lt);
                                                      yyval = new ParserVal(lt);
                                                    }
break;
case 55:
//#line 342 "arthur.yacc"
{
                                                      ParseNode lte = new ParseNode("less than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lte.addChild(ne1); ne1.setParent(lte);
                                                      lte.addChild(ne2); ne2.setParent(lte);
                                                      yyval = new ParserVal(lte);
                                                    }
break;
case 56:
//#line 349 "arthur.yacc"
{
                                                      ParseNode gt = new ParseNode("greater than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gt.addChild(ne1); ne1.setParent(gt);
                                                      gt.addChild(ne2); ne2.setParent(gt);
                                                      yyval = new ParserVal(gt);
                                                    }
break;
case 57:
//#line 356 "arthur.yacc"
{
                                                      ParseNode gte = new ParseNode("greater than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gte.addChild(ne1); ne1.setParent(gte);
                                                      gte.addChild(ne2); ne2.setParent(gte);
                                                      yyval = new ParserVal(gte);
                                                    }
break;
case 58:
//#line 363 "arthur.yacc"
{
                                                      ParseNode eq2x = new ParseNode("is equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      eq2x.addChild(ne1); ne1.setParent(eq2x);
                                                      eq2x.addChild(ne2); ne2.setParent(eq2x);
                                                      yyval = new ParserVal(eq2x);
                                                    }
break;
case 59:
//#line 373 "arthur.yacc"
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
case 60:
//#line 383 "arthur.yacc"
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
case 62:
//#line 397 "arthur.yacc"
{ 
                                                        ParseNode plus = new ParseNode("+");
                                                        plus.addChild((ParseNode)val_peek(2).obj);
                                                        plus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(plus);
                                                    }
break;
case 63:
//#line 403 "arthur.yacc"
{ 
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.addChild((ParseNode)val_peek(2).obj);
                                                        minus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(minus);
                                                    }
break;
case 64:
//#line 409 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 65:
//#line 413 "arthur.yacc"
{ 
                                                        ParseNode times = new ParseNode("*");
                                                        times.addChild((ParseNode)val_peek(2).obj);
                                                        times.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(times);
                                                    }
break;
case 66:
//#line 419 "arthur.yacc"
{ 
                                                        ParseNode div = new ParseNode("/");
                                                        div.addChild((ParseNode)val_peek(2).obj);
                                                        div.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(div);
                                                    }
break;
case 67:
//#line 425 "arthur.yacc"
{ 
                                                        ParseNode mod = new ParseNode("%");
                                                        mod.addChild((ParseNode)val_peek(2).obj);
                                                        mod.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(mod);
                                                    }
break;
case 68:
//#line 431 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 69:
//#line 435 "arthur.yacc"
{ 
                                                        ParseNode exp = new ParseNode("^");
                                                        exp.addChild((ParseNode)val_peek(2).obj);
                                                        exp.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(exp);
                                                    }
break;
case 70:
//#line 441 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 71:
//#line 445 "arthur.yacc"
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
case 72:
//#line 454 "arthur.yacc"
{
                                                        Number n = (Number) val_peek(0).obj;
                                                        ParseNode number = new ParseNode("Number literal");
                                                        number.addChild(new ParseNode(n.val.toString(), number)); 
                                                        yyval = new ParserVal(number);    
                                                    }
break;
case 73:
//#line 460 "arthur.yacc"
{
                                                        StringLit s = (StringLit) val_peek(0).obj;
                                                        ParseNode string = new ParseNode("String literal");
                                                        string.addChild(new ParseNode(s.val, string)); 
                                                        yyval = new ParserVal(string); 
                                                    }
break;
case 74:
//#line 466 "arthur.yacc"
{ ParseNode t = new ParseNode("true"); yyval = new ParserVal(t); }
break;
case 75:
//#line 467 "arthur.yacc"
{ ParseNode f = new ParseNode("false"); yyval = new ParserVal(f); }
break;
case 76:
//#line 468 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 77:
//#line 469 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 78:
//#line 473 "arthur.yacc"
{
                                                        Var v = (Var) val_peek(0).obj;
                                                        ParseNode var = new ParseNode("variable");
                                                        var.addChild(new ParseNode(v.typeName(), var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        yyval = new ParserVal(var);
                                                    }
break;
case 79:
//#line 483 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 80:
//#line 484 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 81:
//#line 485 "arthur.yacc"
{
                                                        Identifier i = (Identifier) val_peek(0).obj;
                                                        ParseNode id = new ParseNode("Identifier");
                                                        id.addChild(new ParseNode(i.name, id));
                                                        yyval = new ParserVal(id); 
                                                    }
break;
//#line 1268 "Parser.java"
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
