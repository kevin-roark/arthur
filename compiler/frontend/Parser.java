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
    9,    9,   10,   10,   11,   11,   12,    2,   13,   16,
   15,   15,   17,   17,    8,    8,    8,    8,    8,    8,
   18,   18,   20,   20,    7,    7,    7,    7,   21,   21,
   21,   21,   21,   22,   22,   22,   22,   22,   22,   19,
   19,   23,   23,   23,   23,   24,   24,   24,   24,   25,
   25,   26,   26,   26,   26,   26,   26,   26,    5,   14,
   14,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    0,    1,    1,    3,    5,    7,
    5,    6,    1,    0,    2,    0,    3,    4,    4,    2,
    0,    1,    1,    3,    1,    1,    1,    1,    1,    1,
    1,    2,    0,    2,    1,    1,    1,    0,    1,    1,
    3,    3,    2,    1,    3,    3,    3,    3,    3,    3,
    3,    0,    3,    3,    1,    3,    3,    3,    1,    3,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
    0,    1,    0,    0,    4,   69,    0,    0,    7,    2,
    3,    0,    0,    0,   18,    8,    0,    0,    0,   31,
    0,   71,   62,    0,   64,    0,    0,    0,   26,    0,
    0,   25,    0,    0,   29,   27,   28,    0,    0,    0,
    0,    0,    0,   61,    0,    0,   39,   40,    0,   63,
   65,   66,   70,    0,    0,    0,   32,   17,   20,    0,
    0,   34,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   68,    0,   23,
    0,    0,    0,    0,    0,   44,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   60,    0,    0,   19,
    0,    9,    0,   24,    0,   13,    0,   12,   15,   10,
};
final static short yydgoto[] = {                          3,
    4,    5,    7,    8,   28,   29,   30,   31,   32,  107,
  108,   15,   53,   54,   81,   35,   82,   36,   37,   38,
   39,   40,   41,   42,   43,   44,
};
final static short yysindex[] = {                      -273,
 -269,    0,    0, -213,    0,    0, -245, -233,    0,    0,
    0, -230, -269,  196,    0,    0, -206, -181, -248,    0,
   91,    0,    0,    0,    0,    0,    0, -163,    0, -146,
 -133,    0, -132, -258,    0,    0,    0,  196, -180,  -91,
 -185,  138, -144,    0, -256, -256,    0,    0, -180,    0,
    0,    0,    0, -119, -226,   91,    0,    0,    0, -256,
   91,    0, -248, -248, -152, -152, -152, -152, -152,   91,
   91,   91,   91,   91,   91, -112, -110,    0, -185,    0,
 -101, -115, -185, -180, -180,    0,  -91,  -91,  -91,  -91,
  -91,  138,  138, -144, -144, -144,    0,  196,  196,    0,
 -256,    0, -155,    0,  196,    0,  -82,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
  -89,    0,    0,    0,    0,    0,    0,  -80,    0,    0,
    0,    0,    0,  -98,    0,    0,    0,    0,    0,    0,
 -212,    0,    0,  262,    0,  234,  241,    0,    0,    0,
    0,    0, -175, -137,    0,    0,    0, -252, -134,  -95,
  -56,   28, -168,    0, -210, -210,    0,    0,   -4,    0,
    0,    0,    0, -129,    0,  211,    0,    0,    0, -254,
  211,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  101,    0,
    0,  -74,  140,   -2,   23,    0,   58,   64,   79,  135,
  149,   45,   84,  -90,  -51,  -11,    0, -250, -250,    0,
 -217,    0,  157,    0, -250,    0, -215,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  193,    0,    0,    8,    0,  -42,  -37,   96,    0,
  106,    0,  -14,  -12,    0,    0,    0,    0,    0,    0,
  -13,  158,  -16,   48,  368,  133,
};
final static int YYTABLESIZE=548;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         33,
   62,   34,   76,   77,   55,   49,   19,   30,    9,   33,
   60,   38,   21,    1,   19,   21,   61,   80,    6,   30,
   16,   33,    2,   33,   12,   34,   52,   52,   30,   30,
   33,   33,   13,   22,   23,   24,   25,   26,   27,   79,
   14,   16,   16,   78,   83,   47,   48,   16,   38,   84,
   85,   16,   38,   16,   70,   71,   16,   52,  104,   38,
  102,  103,   45,   52,   52,   16,   16,  109,   52,   52,
   52,   52,   16,    1,   16,   16,   16,   16,   16,   16,
   63,   64,   10,   33,   33,   34,   34,   46,   59,   59,
   33,   59,   34,   70,   59,   70,   71,   59,   59,   70,
   59,   59,   18,   59,  105,   70,   70,   70,   70,   70,
   70,   56,   59,   59,   59,   59,   59,   92,   93,   59,
   57,   59,   59,   59,   59,   59,   59,   67,   67,   67,
   67,   35,   35,   67,   59,   35,   67,   67,   58,   86,
   67,   75,   67,   67,   67,   67,   67,   67,   67,   60,
  101,   67,   67,   67,   67,   67,   67,   98,   67,   99,
   67,   67,   67,   67,   67,   67,   56,   56,  100,   56,
   36,   36,   56,   33,   36,   56,   56,  105,   56,   56,
    5,   56,   33,   33,   65,   66,   67,   68,   69,    6,
   56,   56,   56,   56,   56,   22,   11,   56,  106,   56,
   56,   56,   56,   56,   56,   57,   57,   97,   57,   37,
   37,   57,  110,   37,   57,   57,    0,   57,   57,    0,
   57,    0,   87,   88,   89,   90,   91,    0,    0,   57,
   57,   57,   57,   57,    0,    0,   57,    0,   57,   57,
   57,   57,   57,   57,    0,   58,   58,    0,   58,    0,
    0,   58,    0,    0,   58,   58,    0,   58,   58,    0,
   58,   43,   43,   41,   41,   43,    0,   41,    0,   58,
   58,   58,   58,   58,    0,    0,   58,    0,   58,   58,
   58,   58,   58,   58,   55,   55,    0,   55,   42,   42,
   55,    0,   42,   55,   55,    0,   55,   55,    0,   55,
    0,   53,   53,    0,   53,    0,    0,   53,   55,   55,
   53,   53,    0,   53,   53,   55,   53,   55,   55,   55,
   55,   55,   55,   49,   49,   53,   53,   49,    0,   45,
   45,    0,   53,   45,   53,   53,   53,   53,   53,   53,
   54,   54,    0,   54,   46,   46,   54,    0,   46,   54,
   54,    0,   54,   54,    0,   54,    0,   51,   51,   21,
   51,    0,    0,   51,   54,   54,    0,   51,    0,   51,
    0,   54,   51,   54,   54,   54,   54,   54,   54,    0,
   22,   23,   50,   25,   51,   52,    0,    0,   51,    0,
   51,   51,   51,   51,   51,   51,   50,   50,    0,   50,
   47,   47,   50,    0,   47,    0,   50,    0,   50,    0,
    0,   50,    0,   11,   48,   48,    0,    0,   48,   11,
   72,   73,   74,   11,    0,   11,    0,   50,   11,   50,
   50,   50,   50,   50,   50,    0,    0,   11,   11,   94,
   95,   96,    0,    0,   11,    0,   11,   11,   11,   11,
   11,   11,   17,   18,    0,    0,    0,    0,   19,    0,
    0,    0,   20,    0,   21,    0,    0,   52,   52,    0,
   52,    0,    0,   52,    0,    0,    0,   52,    0,    0,
    0,    0,   52,    6,    0,   22,   23,   24,   25,   26,
   27,   52,   52,    0,   39,   39,    0,    0,   52,   39,
   39,   40,   40,   39,    0,    0,   40,   40,    0,    0,
   40,    0,    0,    0,   65,   65,   65,   65,   65,   65,
    0,   66,   66,   66,   66,   66,   66,   44,   44,    0,
    0,   44,    0,    0,    0,    0,    0,   44,   44,   44,
   44,   44,   63,   63,   63,   63,   63,   63,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         14,
   38,   14,   45,   46,   21,   19,  263,  260,    1,  260,
  269,  266,  269,  287,  263,  270,  275,   60,  288,  272,
   13,  272,  296,   38,  270,   38,  281,  282,  281,  282,
  281,  282,  266,  290,  291,  292,  293,  294,  295,   56,
  271,  257,  258,  270,   61,  294,  295,  263,  266,   63,
   64,  267,  270,  269,  281,  282,  272,  270,  101,  270,
   98,   99,  269,  281,  282,  281,  282,  105,  281,  282,
  281,  282,  288,  287,  290,  291,  292,  293,  294,  295,
  261,  262,  296,   98,   99,   98,   99,  269,  257,  258,
  105,  260,  105,  269,  263,  281,  282,  266,  267,  275,
  269,  270,  258,  272,  260,  281,  282,  283,  284,  285,
  286,  275,  281,  282,  283,  284,  285,   70,   71,  288,
  267,  290,  291,  292,  293,  294,  295,  257,  258,  267,
  260,  266,  267,  263,  267,  270,  266,  267,  272,  292,
  270,  286,  272,  281,  282,  283,  284,  285,  286,  269,
  266,  281,  282,  283,  284,  285,  286,  270,  288,  270,
  290,  291,  292,  293,  294,  295,  257,  258,  270,  260,
  266,  267,  263,  272,  270,  266,  267,  260,  269,  270,
  270,  272,  281,  282,  276,  277,  278,  279,  280,  270,
  281,  282,  283,  284,  285,  270,    4,  288,  103,  290,
  291,  292,  293,  294,  295,  257,  258,   75,  260,  266,
  267,  263,  107,  270,  266,  267,   -1,  269,  270,   -1,
  272,   -1,   65,   66,   67,   68,   69,   -1,   -1,  281,
  282,  283,  284,  285,   -1,   -1,  288,   -1,  290,  291,
  292,  293,  294,  295,   -1,  257,  258,   -1,  260,   -1,
   -1,  263,   -1,   -1,  266,  267,   -1,  269,  270,   -1,
  272,  266,  267,  266,  267,  270,   -1,  270,   -1,  281,
  282,  283,  284,  285,   -1,   -1,  288,   -1,  290,  291,
  292,  293,  294,  295,  257,  258,   -1,  260,  266,  267,
  263,   -1,  270,  266,  267,   -1,  269,  270,   -1,  272,
   -1,  257,  258,   -1,  260,   -1,   -1,  263,  281,  282,
  266,  267,   -1,  269,  270,  288,  272,  290,  291,  292,
  293,  294,  295,  266,  267,  281,  282,  270,   -1,  266,
  267,   -1,  288,  270,  290,  291,  292,  293,  294,  295,
  257,  258,   -1,  260,  266,  267,  263,   -1,  270,  266,
  267,   -1,  269,  270,   -1,  272,   -1,  257,  258,  269,
  260,   -1,   -1,  263,  281,  282,   -1,  267,   -1,  269,
   -1,  288,  272,  290,  291,  292,  293,  294,  295,   -1,
  290,  291,  292,  293,  294,  295,   -1,   -1,  288,   -1,
  290,  291,  292,  293,  294,  295,  257,  258,   -1,  260,
  266,  267,  263,   -1,  270,   -1,  267,   -1,  269,   -1,
   -1,  272,   -1,  257,  266,  267,   -1,   -1,  270,  263,
  283,  284,  285,  267,   -1,  269,   -1,  288,  272,  290,
  291,  292,  293,  294,  295,   -1,   -1,  281,  282,   72,
   73,   74,   -1,   -1,  288,   -1,  290,  291,  292,  293,
  294,  295,  257,  258,   -1,   -1,   -1,   -1,  263,   -1,
   -1,   -1,  267,   -1,  269,   -1,   -1,  257,  258,   -1,
  260,   -1,   -1,  263,   -1,   -1,   -1,  267,   -1,   -1,
   -1,   -1,  272,  288,   -1,  290,  291,  292,  293,  294,
  295,  281,  282,   -1,  261,  262,   -1,   -1,  288,  266,
  267,  261,  262,  270,   -1,   -1,  266,  267,   -1,   -1,
  270,   -1,   -1,   -1,  281,  282,  283,  284,  285,  286,
   -1,  281,  282,  283,  284,  285,  286,  266,  267,   -1,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  286,
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
"func_def : FUNCTION param_list RPAREN func_body",
"fun_call : id LPAREN arg_list RPAREN",
"fun_call_stmt : fun_call SEMI",
"arg_list :",
"arg_list : hard_arg_list",
"hard_arg_list : expr",
"hard_arg_list : hard_arg_list COMMA expr",
"stmt : if_stmt",
"stmt : dw_stmt",
"stmt : expr_stmt",
"stmt : eq_stmt",
"stmt : fun_call_stmt",
"stmt : stmt_list",
"expr_stmt : SEMI",
"expr_stmt : expr SEMI",
"stmt_list :",
"stmt_list : stmt_list stmt",
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
"eq_stmt : id EQ val",
"eq_stmt : var EQ val",
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
"id : ID",
};

//#line 423 "arthur.yacc"
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
//#line 510 "Parser.java"
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
{ ParseNode p = new ParseNode("arthur"); yyval = new ParserVal(p); }
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
//#line 133 "arthur.yacc"
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
case 19:
//#line 149 "arthur.yacc"
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
//#line 164 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("arguments")); }
break;
case 22:
//#line 165 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 23:
//#line 169 "arthur.yacc"
{
                                                        ParseNode params = new ParseNode("arguments");
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = new ParserVal(params);
                                                    }
break;
case 24:
//#line 176 "arthur.yacc"
{
                                                        ParseNode params = (ParseNode) val_peek(2).obj;
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = val_peek(2);
                                                    }
break;
case 25:
//#line 186 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: if");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 26:
//#line 191 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: dw");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 27:
//#line 196 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: expr");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 28:
//#line 201 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: eq");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 29:
//#line 206 "arthur.yacc"
{ 
                                                      ParseNode s = new ParseNode("stmt: fun_call");
                                                      s.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(s);
                                                    }
break;
case 30:
//#line 211 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 31:
//#line 215 "arthur.yacc"
{ yyval = new ParserVal(null); }
break;
case 32:
//#line 216 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 33:
//#line 220 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("stmt_list"));}
break;
case 34:
//#line 221 "arthur.yacc"
{
                                                      ParseNode list = (ParseNode) val_peek(1).obj;
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      list.addChild(s);
                                                      yyval = val_peek(1);
                                                    }
break;
case 35:
//#line 230 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 36:
//#line 231 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 37:
//#line 232 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 38:
//#line 233 "arthur.yacc"
{ yyval = new ParserVal(null); }
break;
case 39:
//#line 237 "arthur.yacc"
{ ParseNode bool = new ParseNode("true"); yyval = new ParserVal(bool); }
break;
case 40:
//#line 238 "arthur.yacc"
{ ParseNode bool = new ParseNode("false"); yyval = new ParserVal(bool); }
break;
case 41:
//#line 239 "arthur.yacc"
{ 
                                                      ParseNode and = new ParseNode("and");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      and.addChild(b1); b1.setParent(and);
                                                      and.addChild(b2); b2.setParent(and);
                                                      yyval = new ParserVal(and);
                                                    }
break;
case 42:
//#line 246 "arthur.yacc"
{ 
                                                      ParseNode or = new ParseNode("or");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      or.addChild(b1); b1.setParent(or);
                                                      or.addChild(b2); b2.setParent(or);
                                                      yyval = new ParserVal(or);
                                                    }
break;
case 43:
//#line 253 "arthur.yacc"
{ 
                                                      ParseNode b = (ParseNode) val_peek(0).obj;
                                                      ParseNode not = new ParseNode("not");
                                                      not.addChild(b); not.setParent(b.parent); b.setParent(not);
                                                      yyval = new ParserVal(not);
                                                    }
break;
case 44:
//#line 262 "arthur.yacc"
{ ParseNode number = new ParseNode("number"); yyval = new ParserVal(number); }
break;
case 45:
//#line 263 "arthur.yacc"
{
                                                      ParseNode lt = new ParseNode("less than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lt.addChild(ne1); ne1.setParent(lt);
                                                      lt.addChild(ne2); ne2.setParent(lt);
                                                      yyval = new ParserVal(lt);
                                                    }
break;
case 46:
//#line 270 "arthur.yacc"
{
                                                      ParseNode lte = new ParseNode("less than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lte.addChild(ne1); ne1.setParent(lte);
                                                      lte.addChild(ne2); ne2.setParent(lte);
                                                      yyval = new ParserVal(lte);
                                                    }
break;
case 47:
//#line 277 "arthur.yacc"
{
                                                      ParseNode gt = new ParseNode("greater than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gt.addChild(ne1); ne1.setParent(gt);
                                                      gt.addChild(ne2); ne2.setParent(gt);
                                                      yyval = new ParserVal(gt);
                                                    }
break;
case 48:
//#line 284 "arthur.yacc"
{
                                                      ParseNode gte = new ParseNode("greater than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gte.addChild(ne1); ne1.setParent(gte);
                                                      gte.addChild(ne2); ne2.setParent(gte);
                                                      yyval = new ParserVal(gte);
                                                    }
break;
case 49:
//#line 291 "arthur.yacc"
{
                                                      ParseNode eq2x = new ParseNode("is equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      eq2x.addChild(ne1); ne1.setParent(eq2x);
                                                      eq2x.addChild(ne2); ne2.setParent(eq2x);
                                                      yyval = new ParserVal(eq2x);
                                                    }
break;
case 50:
//#line 301 "arthur.yacc"
{
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) val_peek(2).obj;
                                                        ParseNode val = (ParseNode) val_peek(0).obj;
                                                        id.setParent(eq); /*again, can take these out later if need be*/
                                                        val.setParent(eq);
                                                        eq.addChild(id);
                                                        eq.addChild(val);    
                                                        yyval = new ParserVal(eq);
                                                    }
break;
case 51:
//#line 311 "arthur.yacc"
{
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode var = (ParseNode) val_peek(2).obj;
                                                        ParseNode val = (ParseNode) val_peek(0).obj;
                                                        var.setParent(eq);
                                                        val.setParent(eq);
                                                        eq.addChild(var);
                                                        eq.addChild(val);
                                                        yyval = new ParserVal(eq);
                                                    }
break;
case 53:
//#line 325 "arthur.yacc"
{ 
                                                        ParseNode plus = new ParseNode("+");
                                                        plus.addChild((ParseNode)val_peek(2).obj);
                                                        plus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(plus);
                                                    }
break;
case 54:
//#line 331 "arthur.yacc"
{ 
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.addChild((ParseNode)val_peek(2).obj);
                                                        minus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(minus);
                                                    }
break;
case 55:
//#line 337 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 56:
//#line 341 "arthur.yacc"
{ 
                                                        ParseNode times = new ParseNode("*");
                                                        times.addChild((ParseNode)val_peek(2).obj);
                                                        times.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(times);
                                                    }
break;
case 57:
//#line 347 "arthur.yacc"
{ 
                                                        ParseNode div = new ParseNode("/");
                                                        div.addChild((ParseNode)val_peek(2).obj);
                                                        div.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(div);
                                                    }
break;
case 58:
//#line 353 "arthur.yacc"
{ 
                                                        ParseNode mod = new ParseNode("%");
                                                        mod.addChild((ParseNode)val_peek(2).obj);
                                                        mod.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(mod);
                                                    }
break;
case 59:
//#line 359 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 60:
//#line 363 "arthur.yacc"
{ 
                                                        ParseNode exp = new ParseNode("^");
                                                        exp.addChild((ParseNode)val_peek(2).obj);
                                                        exp.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(exp);
                                                    }
break;
case 61:
//#line 369 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 62:
//#line 373 "arthur.yacc"
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
case 63:
//#line 382 "arthur.yacc"
{
                                                        Number n = (Number) val_peek(0).obj;
                                                        ParseNode number = new ParseNode("Number literal");
                                                        number.addChild(new ParseNode(n.val.toString(), number)); 
                                                        yyval = new ParserVal(number);    
                                                    }
break;
case 64:
//#line 388 "arthur.yacc"
{
                                                        StringLit s = (StringLit) val_peek(0).obj;
                                                        ParseNode string = new ParseNode("String literal");
                                                        string.addChild(new ParseNode(s.val, string)); 
                                                        yyval = new ParserVal(string); 
                                                    }
break;
case 65:
//#line 394 "arthur.yacc"
{ ParseNode t = new ParseNode("true"); yyval = new ParserVal(t); }
break;
case 66:
//#line 395 "arthur.yacc"
{ ParseNode f = new ParseNode("false"); yyval = new ParserVal(f); }
break;
case 67:
//#line 396 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 68:
//#line 397 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 69:
//#line 401 "arthur.yacc"
{
                                                        Var v = (Var) val_peek(0).obj;
                                                        ParseNode var = new ParseNode("variable");
                                                        var.addChild(new ParseNode(v.typeName(), var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        yyval = new ParserVal(var);
                                                    }
break;
case 70:
//#line 411 "arthur.yacc"
{
                                                        yyval = val_peek(0);
                                                    }
break;
case 71:
//#line 414 "arthur.yacc"
{
                                                        Identifier i = (Identifier) val_peek(0).obj;
                                                        ParseNode id = new ParseNode("Identifier");
                                                        id.addChild(new ParseNode(i.name, id));
                                                        yyval = new ParserVal(id); 
                                                    }
break;
//#line 1179 "Parser.java"
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
