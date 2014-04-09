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






//#line 4 "arthur.yacc"
    import java.io.Reader;
    import java.io.IOException;
    import java.lang.Math;
 
//#line 22 "Parser.java"




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
   10,   10,   10,   10,   11,   11,   13,   12,   14,   14,
    2,   15,   18,   19,   20,   21,   22,   17,   17,   23,
   23,    8,    8,    8,    8,    8,    8,    8,    8,    8,
    8,   27,   27,   25,   25,   24,   24,   24,   28,   28,
    7,    7,    7,    7,   29,   29,   29,   29,   29,   29,
   30,   30,   30,   30,   30,   30,   30,   26,   26,   31,
   31,   31,   31,   32,   32,   32,   32,   33,   33,   34,
   34,   34,   34,   34,   34,   34,    5,   16,   16,   16,
   16,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    0,    1,    1,    3,    5,    5,
    7,    6,    5,    6,    2,    1,    5,    2,    3,    2,
    4,    4,    3,    3,    2,    2,    2,    0,    1,    1,
    3,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    2,    1,    2,    3,    2,    1,    2,    1,
    1,    1,    1,    0,    1,    1,    1,    3,    3,    2,
    1,    1,    3,    3,    3,    3,    3,    4,    4,    0,
    3,    3,    1,    3,    3,    3,    1,    3,    1,    1,
    1,    1,    1,    1,    1,    3,    1,    1,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
    0,    1,    0,    0,    4,   87,    0,    0,    7,    2,
    3,    0,    0,    0,   21,    8,    0,    0,    0,    0,
   44,    0,    0,   20,   91,   80,    0,   82,    0,    0,
    0,   34,    0,    0,   35,   33,    0,    0,    0,    0,
   40,   39,   38,   32,   36,   37,   41,    0,    0,    0,
    0,    0,    0,   79,    0,    0,    0,   88,    0,   89,
   90,    0,    0,    0,   43,    0,   81,   83,   84,    0,
   47,   50,    0,    0,    0,   45,   19,   27,    0,    0,
    0,   26,   25,   49,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   42,
   86,   46,    0,    0,   23,    0,   30,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   78,    0,    0,    0,   69,   22,    0,   68,
    9,    0,   10,   31,    0,    0,    0,   14,   16,    0,
   18,   11,   15,    0,    0,   17,
};
final static short yydgoto[] = {                          3,
    4,    5,    7,    8,   31,   32,   33,   84,   35,   36,
  137,  138,  139,   15,   58,   59,  108,   60,   61,   41,
   42,   43,  109,   44,   45,   46,   47,   48,   49,   50,
   51,   52,   53,   54,
};
final static short yysindex[] = {                      -219,
 -268,    0,    0, -208,    0,    0, -245, -229,    0,    0,
    0, -231, -268,   13,    0,    0, -221, -207, -248, -239,
    0,    3,   52,    0,    0,    0, -205,    0,    0,    0,
 -202,    0, -175, -156,    0,    0, -170, -236, -167, -148,
    0,    0,    0,    0,    0,    0,    0,  107, -204,  -97,
 -197,  -37, -161,    0, -248, -248,    0,    0, -264,    0,
    0, -204,  -97, -197,    0, -137,    0,    0,    0, -210,
    0,    0,   91, -136,    3,    0,    0,    0, -146, -248,
    3,    0,    0,    0, -248, -248,   42,   42,   42,   42,
   42,    3,    3,    3,    3,    3,    3, -125, -115,    0,
    0,    0,  107, -259,    0, -264,    0, -114, -105, -255,
 -204, -204,  -97,  -97,  -97,  -97,  -97,  -37,  -37, -161,
 -161, -161,    0,  107,  107, -112,    0,    0, -248,    0,
    0, -151,    0,    0, -103,  107, -151,    0,    0, -248,
    0,    0,    0, -106,  107,    0,
};
final static short yyrindex[] = {                         0,
 -101,    0,    0,    0,    0,    0,    0,  -99,    0,    0,
    0,    0,    0,  111,    0,    0,    0,    0,  526,  111,
    0, -206,  111,    0,    0,    0,  376,    0,  168,  194,
    0,    0,    0,  -26,    0,    0,  220,  350,  246,  272,
    0,    0,    0,    0,    0,    0,    0,  504, -172, -180,
  681,  548,  402,    0,  649,  649,  298,    0,  324,    0,
    0, -127,  144,  701,    0,    0,    0,    0,    0,    0,
    0,    0,  659,    0, -250,    0,    0,    0,    0,  614,
 -250,    0,    0,    0,  526,  526,  526,  526,  526,  526,
  526,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  111,    0,    0,  142,    0,    0,  -85,    0,
  -48,  -46,  170,  196,  300,  326,  352,  570,  592,  427,
  452,  477,    0,  111,  111,  -26,    0,    0,  636,    0,
    0, -104,    0,    0,    0,  111,  -65,    0,    0,  649,
    0,    0,    0,    0,  111,    0,
};
final static short yygindex[] = {                         0,
    0,  190,    0,    0,    5,    0,   -6,   -4,    0,    0,
    0,   58,   63,    0,  -14,  -12,    0,  -10,   -7,    0,
    0,    0,    0,    0,    0,    0,    0,  178,  -16,   16,
   61,   35,  163,  106,
};
final static int YYTABLESIZE=981;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         37,
   79,   38,   62,   39,   80,    9,   40,  127,   37,   34,
   38,  130,   39,   66,   19,   40,   70,   16,   72,    6,
   22,   92,   93,   19,   12,   92,   93,   65,   79,   22,
   70,   70,   80,   37,   63,   38,   13,   39,   81,   14,
   40,   25,   26,   57,   28,   29,   30,   55,   98,   99,
   25,   26,   57,   28,   29,   30,   85,   86,   37,  101,
   38,   56,   39,   70,  105,   40,  106,    1,  111,  112,
   92,   93,   75,  107,   70,   70,    2,   74,    1,   64,
   57,   57,   70,   92,   93,   52,   52,   10,   37,   52,
   38,   76,   39,   51,   51,   40,   78,   51,  126,   82,
   63,   63,  113,  114,  115,  116,  117,  135,  136,   37,
   37,   38,   38,   39,   39,   77,   40,   40,   83,  131,
  132,   37,  134,   38,   97,   39,  118,  119,   40,  100,
   37,  141,   38,  144,   39,  104,  103,   40,   60,   60,
  146,  110,   60,   25,  124,   64,   64,   64,   64,   64,
   64,   64,   13,   13,  125,  128,   13,   13,   13,   13,
  129,  133,   13,  145,   13,  140,   13,   13,    5,   13,
    6,   13,   13,   13,   13,   13,   13,   13,   87,   88,
   89,   90,   91,   13,   29,   13,   13,   13,   13,   13,
   13,   12,   12,   11,  142,   12,   12,   12,   12,  143,
   73,   12,  123,   12,    0,   12,   12,    0,   12,    0,
   12,   12,   12,   12,   12,   12,   12,   58,   58,   59,
   59,   58,   12,   59,   12,   12,   12,   12,   12,   12,
   50,   50,    0,    0,   50,   50,   50,   50,    0,    0,
   50,    0,   50,    0,   50,   94,   95,   96,    0,   50,
   50,   50,   50,   50,   50,   50,  120,  121,  122,    0,
    0,   50,    0,   50,   50,   50,   50,   50,   50,   17,
   18,   22,    0,    0,    0,   19,   20,    0,    0,   21,
    0,   22,    0,   23,   24,    0,    0,    0,    0,    0,
    0,    0,   25,   26,   67,   28,   68,   69,    0,    0,
    6,    0,   25,   26,   27,   28,   29,   30,   17,   18,
   22,    0,    0,    0,   19,   20,    0,    0,   21,    0,
   22,    0,   23,   71,    0,    0,    0,    0,    0,    0,
    0,   25,   26,   57,   28,   68,   69,    0,    0,    6,
    0,   25,   26,   27,   28,   29,   30,   17,   18,    0,
    0,    0,    0,   19,   20,    0,    0,   21,    0,   22,
    0,   23,  102,   17,   18,    0,    0,    0,    0,   19,
   20,   70,   70,   21,    0,   22,    0,   23,    6,    0,
   25,   26,   27,   28,   29,   30,   70,   70,   70,   70,
   70,   70,   70,    0,    6,    0,   25,   26,   27,   28,
   29,   30,   24,   24,   57,   57,    0,   24,   24,   57,
   57,   24,    0,   57,    0,    0,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   55,   55,
   67,   67,    0,   55,   55,   67,   67,   55,    0,   67,
    0,    0,    0,   83,   83,   83,   83,   83,   83,   83,
   83,   83,   83,   83,   56,   56,   63,   63,    0,   56,
   56,   63,   63,   56,    0,   63,    0,    0,    0,   84,
   84,   84,   84,   84,   84,   84,   84,   84,   84,   84,
   88,   88,    0,    0,   88,    0,    0,    0,   88,    0,
    0,    0,    0,    0,   88,   88,   88,   88,   88,   88,
   88,   88,   88,   88,   88,   88,   89,   89,    0,    0,
   89,    0,    0,    0,   89,    0,    0,    0,    0,    0,
   89,   89,   89,   89,   89,   89,   89,   89,   89,   89,
   89,   89,   90,   90,    0,    0,   90,    0,    0,    0,
   90,    0,    0,    0,    0,    0,   90,   90,   90,   90,
   90,   90,   90,   90,   90,   90,   90,   90,   61,   61,
   64,   64,    0,   61,   61,   64,   64,   61,    0,   64,
    0,    0,    0,   61,   61,   61,   61,   61,   81,   81,
   81,   81,   81,   81,   85,   85,   65,   65,    0,   85,
   85,   65,   65,   85,    0,   65,    0,    0,    0,   85,
   85,   85,   85,   85,   85,   85,   85,   85,   85,   85,
   85,   85,   66,   66,    0,    0,   85,   66,   66,    0,
    0,   66,    0,    0,    0,   85,   85,   85,   85,   85,
   85,   85,   85,   85,   85,   85,   61,   61,    0,    0,
    0,    0,   61,    0,    0,    0,    0,    0,    0,    0,
    0,   61,   61,   61,   61,   61,   81,   81,    0,   81,
   81,   81,   77,   77,    0,    0,    0,   77,   77,    0,
    0,   77,    0,    0,    0,    0,    0,   77,   77,   77,
   77,   77,   77,   77,   77,   77,   77,   74,   74,    0,
    0,    0,   74,   74,    0,    0,   74,    0,    0,    0,
    0,    0,   74,   74,   74,   74,   74,   74,   74,   74,
   74,   74,   75,   75,    0,    0,    0,   75,   75,    0,
    0,   75,    0,    0,    0,    0,    0,   75,   75,   75,
   75,   75,   75,   75,   75,   75,   75,   76,   76,    0,
    0,    0,   76,   76,    0,    0,   76,    0,    0,    0,
    0,    0,   76,   76,   76,   76,   76,   76,   76,   76,
   76,   76,   48,   48,   48,   48,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   48,    0,   48,    0,   48,
   48,   48,   48,   48,   48,   48,   70,   70,    0,    0,
    0,   70,   70,    0,    0,   70,    0,    0,    0,    0,
    0,   70,   70,   70,   70,   70,   70,   70,   73,   73,
    0,    0,    0,   73,   73,    0,    0,   73,    0,    0,
    0,    0,    0,   73,   73,   73,   73,   73,   73,   73,
   71,   71,    0,    0,    0,   71,   71,    0,    0,   71,
    0,    0,    0,    0,    0,   71,   71,   71,   71,   71,
   71,   71,   72,   72,    0,    0,    0,   72,   72,    0,
    0,   72,    0,    0,    0,    0,    0,   72,   72,   72,
   72,   72,   72,   72,   70,   70,    0,    0,    0,   54,
    0,    0,    0,   28,    0,    0,    0,    0,    0,   70,
   70,   70,   70,   70,   70,   70,   70,   70,    0,    0,
    0,   54,    0,    0,    0,   54,    0,    0,    0,   70,
   70,   70,   70,   70,   70,   70,   70,   70,   54,   48,
   48,    0,    0,    0,   70,   70,   70,   70,   70,   70,
   70,    0,    0,    0,   48,   48,   48,   48,   48,   48,
   48,   62,   62,    0,    0,    0,   53,   53,    0,    0,
   53,    0,    0,    0,    0,    0,   62,   62,   62,   62,
   62,   62,   62,    0,    0,    0,   62,   62,    0,    0,
   62,    0,    0,    0,    0,    0,   62,   62,   62,   62,
   62,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         14,
  265,   14,   19,   14,  269,    1,   14,  267,   23,   14,
   23,  267,   23,   20,  263,   23,  267,   13,   23,  288,
  269,  281,  282,  263,  270,  281,  282,  267,  265,  269,
  281,  282,  269,   48,   19,   48,  266,   48,  275,  271,
   48,  290,  291,  292,  293,  294,  295,  269,   55,   56,
  290,  291,  292,  293,  294,  295,  261,  262,   73,  270,
   73,  269,   73,  270,   79,   73,   79,  287,   85,   86,
  281,  282,  275,   80,  281,  282,  296,  283,  287,   19,
  261,  262,   22,  281,  282,  266,  267,  296,  103,  270,
  103,  267,  103,  266,  267,  103,  267,  270,  103,  267,
   85,   86,   87,   88,   89,   90,   91,  259,  260,  124,
  125,  124,  125,  124,  125,  272,  124,  125,  267,  124,
  125,  136,  129,  136,  286,  136,   92,   93,  136,  267,
  145,  136,  145,  140,  145,   75,  273,  145,  266,  267,
  145,   81,  270,  290,  270,   85,   86,   87,   88,   89,
   90,   91,  257,  258,  270,  270,  261,  262,  263,  264,
  266,  274,  267,  270,  269,  269,  271,  272,  270,  274,
  270,  276,  277,  278,  279,  280,  281,  282,  276,  277,
  278,  279,  280,  288,  270,  290,  291,  292,  293,  294,
  295,  257,  258,    4,  137,  261,  262,  263,  264,  137,
   23,  267,   97,  269,   -1,  271,  272,   -1,  274,   -1,
  276,  277,  278,  279,  280,  281,  282,  266,  267,  266,
  267,  270,  288,  270,  290,  291,  292,  293,  294,  295,
  257,  258,   -1,   -1,  261,  262,  263,  264,   -1,   -1,
  267,   -1,  269,   -1,  271,  283,  284,  285,   -1,  276,
  277,  278,  279,  280,  281,  282,   94,   95,   96,   -1,
   -1,  288,   -1,  290,  291,  292,  293,  294,  295,  257,
  258,  269,   -1,   -1,   -1,  263,  264,   -1,   -1,  267,
   -1,  269,   -1,  271,  272,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  290,  291,  292,  293,  294,  295,   -1,   -1,
  288,   -1,  290,  291,  292,  293,  294,  295,  257,  258,
  269,   -1,   -1,   -1,  263,  264,   -1,   -1,  267,   -1,
  269,   -1,  271,  272,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  290,  291,  292,  293,  294,  295,   -1,   -1,  288,
   -1,  290,  291,  292,  293,  294,  295,  257,  258,   -1,
   -1,   -1,   -1,  263,  264,   -1,   -1,  267,   -1,  269,
   -1,  271,  272,  257,  258,   -1,   -1,   -1,   -1,  263,
  264,  261,  262,  267,   -1,  269,   -1,  271,  288,   -1,
  290,  291,  292,  293,  294,  295,  276,  277,  278,  279,
  280,  281,  282,   -1,  288,   -1,  290,  291,  292,  293,
  294,  295,  261,  262,  261,  262,   -1,  266,  267,  266,
  267,  270,   -1,  270,   -1,   -1,  275,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  286,  261,  262,
  261,  262,   -1,  266,  267,  266,  267,  270,   -1,  270,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  286,  261,  262,  261,  262,   -1,  266,
  267,  266,  267,  270,   -1,  270,   -1,   -1,   -1,  276,
  277,  278,  279,  280,  281,  282,  283,  284,  285,  286,
  261,  262,   -1,   -1,  265,   -1,   -1,   -1,  269,   -1,
   -1,   -1,   -1,   -1,  275,  276,  277,  278,  279,  280,
  281,  282,  283,  284,  285,  286,  261,  262,   -1,   -1,
  265,   -1,   -1,   -1,  269,   -1,   -1,   -1,   -1,   -1,
  275,  276,  277,  278,  279,  280,  281,  282,  283,  284,
  285,  286,  261,  262,   -1,   -1,  265,   -1,   -1,   -1,
  269,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  286,  261,  262,
  261,  262,   -1,  266,  267,  266,  267,  270,   -1,  270,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  286,  261,  262,  261,  262,   -1,  266,
  267,  266,  267,  270,   -1,  270,   -1,   -1,   -1,  276,
  277,  278,  279,  280,  281,  282,  283,  284,  285,  286,
  261,  262,  261,  262,   -1,   -1,  267,  266,  267,   -1,
   -1,  270,   -1,   -1,   -1,  276,  277,  278,  279,  280,
  281,  282,  283,  284,  285,  286,  261,  262,   -1,   -1,
   -1,   -1,  267,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  279,  280,  281,  282,   -1,  284,
  285,  286,  261,  262,   -1,   -1,   -1,  266,  267,   -1,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  261,  262,   -1,
   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,   -1,
   -1,   -1,  276,  277,  278,  279,  280,  281,  282,  283,
  284,  285,  261,  262,   -1,   -1,   -1,  266,  267,   -1,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  261,  262,   -1,
   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,   -1,
   -1,   -1,  276,  277,  278,  279,  280,  281,  282,  283,
  284,  285,  259,  260,  261,  262,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  272,   -1,  274,   -1,  276,
  277,  278,  279,  280,  281,  282,  261,  262,   -1,   -1,
   -1,  266,  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  279,  280,  281,  282,  261,  262,
   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  261,  262,   -1,   -1,   -1,  266,  267,   -1,   -1,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,  280,
  281,  282,  261,  262,   -1,   -1,   -1,  266,  267,   -1,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,  281,  282,  261,  262,   -1,   -1,   -1,  266,
   -1,   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  279,  280,  281,  282,  261,  262,   -1,   -1,
   -1,  266,   -1,   -1,   -1,  270,   -1,   -1,   -1,  261,
  262,  276,  277,  278,  279,  280,  281,  282,  270,  261,
  262,   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,
  282,   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,
  282,  261,  262,   -1,   -1,   -1,  266,  267,   -1,   -1,
  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,
  280,  261,  262,   -1,   -1,   -1,  266,  267,   -1,   -1,
  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,
  280,
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
"sugarloop_stmt : NUMBER TIMES LBRACK stmt RBRACK",
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
"prop_access : id DOT id",
"prop_access_stmt : prop_access SEMI",
"meth_call_stmt : meth_call SEMI",
"fun_call_stmt : fun_call SEMI",
"arg_list :",
"arg_list : hard_arg_list",
"hard_arg_list : expr",
"hard_arg_list : hard_arg_list COMMA expr",
"stmt : stmt_block",
"stmt : if_stmt",
"stmt : dw_stmt",
"stmt : sugarloop_stmt",
"stmt : expr_stmt",
"stmt : eq_stmt",
"stmt : fun_call_stmt",
"stmt : meth_call_stmt",
"stmt : prop_access_stmt",
"stmt : return_stmt",
"return_stmt : RETURN expr SEMI",
"return_stmt : RETURN SEMI",
"expr_stmt : SEMI",
"expr_stmt : expr SEMI",
"stmt_block : LCURLY stmt_list RCURLY",
"stmt_block : LCURLY RCURLY",
"stmt_block : stmt_list",
"stmt_list : stmt_list stmt",
"stmt_list : stmt",
"expr : bool_expr",
"expr : num_expr",
"expr : val",
"expr :",
"bool_expr : TRUE",
"bool_expr : FALSE",
"bool_expr : num_expr",
"bool_expr : bool_expr AND bool_expr",
"bool_expr : bool_expr OR bool_expr",
"bool_expr : NOT bool_expr",
"num_expr : NUMBER",
"num_expr : val",
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
"id : prop_access",
"id : ID",
};

//#line 551 "arthur.yacc"
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
//#line 635 "Parser.java"
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
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) val_peek(2).obj);
                                                      body.addChild((ParseNode) val_peek(0).obj);
                                                      dw.addChild(condition);
                                                      dw.addChild(body);
                                                      yyval = new ParserVal(dw);
                                                    }
break;
case 10:
//#line 81 "arthur.yacc"
{
                                                      ParseNode sl = new ParseNode("sugarloop");
                                                      ParseNode times = new ParseNode("times");
                                                      ParseNode number = new ParseNode("number");
                                                      Number n = (Number) val_peek(4).obj;
                                                      ParseNode val = new ParseNode(n.val.toString());
                                                      number.addChild(val);
                                                      times.addChild(number);
                                                      ParseNode body = new ParseNode("body");
                                                      body.addChild((ParseNode) val_peek(1).obj);
                                                      sl.addChild(times);
                                                      sl.addChild(body);
                                                      yyval = new ParserVal(sl);
                                                    }
break;
case 11:
//#line 98 "arthur.yacc"
{
                                                      ParseNode ifmaster = new ParseNode("if-switch");
                                                      ParseNode iffer = new ParseNode("if", ifmaster);
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) val_peek(4).obj);
                                                      body.addChild((ParseNode) val_peek(2).obj);
                                                      iffer.addChild(condition);
                                                      iffer.addChild(body);
                                                      ifmaster.addChild(iffer);
                                                      ParseNode elfer = (ParseNode) val_peek(1).obj;
                                                      ifmaster.addChild(elfer);
                                                      ParseNode elser = (ParseNode) val_peek(0).obj;
                                                      ifmaster.addChild(elser);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 12:
//#line 114 "arthur.yacc"
{
                                                      ParseNode ifmaster = new ParseNode("if-switch");
                                                      ParseNode iffer = new ParseNode("if", ifmaster);
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) val_peek(3).obj);
                                                      body.addChild((ParseNode) val_peek(1).obj);
                                                      iffer.addChild(condition);
                                                      iffer.addChild(body);
                                                      ifmaster.addChild(iffer);
                                                      ParseNode elfer = (ParseNode) val_peek(0).obj;
                                                      ifmaster.addChild(elfer);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 13:
//#line 128 "arthur.yacc"
{
                                                      ParseNode ifmaster = new ParseNode("if-switch");
                                                      ParseNode iffer = new ParseNode("if", ifmaster);
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) val_peek(2).obj);
                                                      body.addChild((ParseNode) val_peek(0).obj);
                                                      iffer.addChild(condition);
                                                      iffer.addChild(body);
                                                      ifmaster.addChild(iffer);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 14:
//#line 140 "arthur.yacc"
{
                                                     ParseNode ifmaster = new ParseNode("if-switch");
                                                      ParseNode iffer = new ParseNode("if", ifmaster);
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) val_peek(3).obj);
                                                      body.addChild((ParseNode) val_peek(1).obj);
                                                      iffer.addChild(condition);
                                                      iffer.addChild(body);
                                                      ifmaster.addChild(iffer);;
                                                      ParseNode elser = (ParseNode) val_peek(0).obj;
                                                      if (elser != null)
                                                          ifmaster.addChild(elser);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 15:
//#line 158 "arthur.yacc"
{
                                                      ParseNode elfs = (ParseNode) val_peek(1).obj;
                                                      ParseNode elf = (ParseNode) val_peek(0).obj;
                                                      elfs.addChild(elf);
                                                      yyval = val_peek(1);
                                                    }
break;
case 16:
//#line 164 "arthur.yacc"
{
                                                      ParseNode elfs = new ParseNode("elves");
                                                      elfs.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(elfs);
                                                    }
break;
case 17:
//#line 172 "arthur.yacc"
{
                                                        ParseNode elf = new ParseNode("elf");
                                                        ParseNode condition = new ParseNode("condition");
                                                        ParseNode body = new ParseNode("body");
                                                        condition.addChild((ParseNode) val_peek(2).obj);
                                                        body.addChild((ParseNode) val_peek(0).obj);
                                                        elf.addChild(condition);
                                                        elf.addChild(body);
                                                        yyval = new ParserVal(elf);
                                                    }
break;
case 18:
//#line 185 "arthur.yacc"
{
                                                        ParseNode elser = new ParseNode("else");
                                                        ParseNode body = new ParseNode("body");
                                                        body.addChild((ParseNode) val_peek(0).obj);
                                                        elser.addChild(body);
                                                        yyval = new ParserVal(elser);
                                                    }
break;
case 19:
//#line 195 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 20:
//#line 196 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 21:
//#line 200 "arthur.yacc"
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
case 22:
//#line 216 "arthur.yacc"
{
                                                      ParseNode funCall = new ParseNode("Function call");
                                                      ParseNode name = (ParseNode) val_peek(3).obj;
                                                      funCall.addChild(name);
                                                      ParseNode args = (ParseNode) val_peek(1).obj;
                                                      funCall.addChild(args);
                                                      yyval = new ParserVal(funCall);
                                                    }
break;
case 23:
//#line 227 "arthur.yacc"
{
                                                      ParseNode methCall = new ParseNode("Method call");
                                                      ParseNode ob = (ParseNode) val_peek(2).obj;
                                                      methCall.addChild(ob);
                                                      ParseNode fun = (ParseNode) val_peek(0).obj;
                                                      methCall.addChild(fun);
                                                      yyval = new ParserVal(methCall);
                                                    }
break;
case 24:
//#line 238 "arthur.yacc"
{
                                                      ParseNode prop = new ParseNode("Property access");
                                                      prop.addChild((ParseNode) val_peek(2).obj);
                                                      prop.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(prop);
                                                    }
break;
case 25:
//#line 247 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 26:
//#line 251 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 27:
//#line 255 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 28:
//#line 259 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("arguments")); }
break;
case 29:
//#line 260 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 30:
//#line 264 "arthur.yacc"
{
                                                        ParseNode params = new ParseNode("arguments");
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = new ParserVal(params);
                                                    }
break;
case 31:
//#line 271 "arthur.yacc"
{
                                                        ParseNode params = (ParseNode) val_peek(2).obj;
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = val_peek(2);
                                                    }
break;
case 32:
//#line 281 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 33:
//#line 282 "arthur.yacc"
{
                                                      yyval = val_peek(0);
                                                    }
break;
case 34:
//#line 285 "arthur.yacc"
{
                                                      yyval = val_peek(0);
                                                    }
break;
case 35:
//#line 288 "arthur.yacc"
{
                                                      yyval = val_peek(0);
                                                    }
break;
case 36:
//#line 291 "arthur.yacc"
{
                                                      yyval = val_peek(0);
                                                    }
break;
case 37:
//#line 294 "arthur.yacc"
{
                                                      yyval = val_peek(0);
                                                    }
break;
case 38:
//#line 297 "arthur.yacc"
{
                                                      yyval = val_peek(0);
                                                    }
break;
case 39:
//#line 300 "arthur.yacc"
{
                                                      yyval = val_peek(0);
                                                    }
break;
case 40:
//#line 303 "arthur.yacc"
{
                                                      yyval = val_peek(0);
                                                    }
break;
case 41:
//#line 306 "arthur.yacc"
{
                                                      yyval = val_peek(0);
                                                    }
break;
case 42:
//#line 312 "arthur.yacc"
{
                                                      ParseNode e = (ParseNode) val_peek(1).obj;
                                                      ParseNode r = new ParseNode("return");
                                                      r.addChild(e);
                                                      yyval = new ParserVal(r);
                                                    }
break;
case 43:
//#line 318 "arthur.yacc"
{
                                                      ParseNode r = new ParseNode("return");
                                                      yyval = new ParserVal(r);
                                                    }
break;
case 44:
//#line 325 "arthur.yacc"
{ yyval = new ParserVal(null); }
break;
case 45:
//#line 326 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 46:
//#line 330 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 47:
//#line 331 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 48:
//#line 332 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 49:
//#line 336 "arthur.yacc"
{
                                                      ParseNode list = (ParseNode) val_peek(1).obj;
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      list.addChild(s);
                                                      yyval = val_peek(1);
                                                    }
break;
case 50:
//#line 342 "arthur.yacc"
{
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      ParseNode list = new ParseNode("stmt_list");
                                                      list.addChild(s);
                                                      yyval = new ParserVal(list);
                                                    }
break;
case 51:
//#line 351 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 52:
//#line 352 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 53:
//#line 353 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 54:
//#line 354 "arthur.yacc"
{ yyval = new ParserVal(null); }
break;
case 55:
//#line 358 "arthur.yacc"
{ ParseNode bool = new ParseNode("true"); yyval = new ParserVal(bool); }
break;
case 56:
//#line 359 "arthur.yacc"
{ ParseNode bool = new ParseNode("false"); yyval = new ParserVal(bool); }
break;
case 57:
//#line 360 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 58:
//#line 361 "arthur.yacc"
{
                                                      ParseNode and = new ParseNode("and");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      and.addChild(b1); b1.setParent(and);
                                                      and.addChild(b2); b2.setParent(and);
                                                      yyval = new ParserVal(and);
                                                    }
break;
case 59:
//#line 368 "arthur.yacc"
{
                                                      ParseNode or = new ParseNode("or");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      or.addChild(b1); b1.setParent(or);
                                                      or.addChild(b2); b2.setParent(or);
                                                      yyval = new ParserVal(or);
                                                    }
break;
case 60:
//#line 375 "arthur.yacc"
{
                                                      ParseNode b = (ParseNode) val_peek(0).obj;
                                                      ParseNode not = new ParseNode("not");
                                                      not.addChild(b); not.setParent(b.parent); b.setParent(not);
                                                      yyval = new ParserVal(not);
                                                    }
break;
case 61:
//#line 384 "arthur.yacc"
{
                                                      ParseNode number = new ParseNode("number");
                                                      Number n = (Number) val_peek(0).obj;
                                                      ParseNode val = new ParseNode(n.val.toString());
                                                      number.addChild(val);
                                                      yyval = new ParserVal(number); }
break;
case 62:
//#line 390 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 63:
//#line 391 "arthur.yacc"
{
                                                      ParseNode lt = new ParseNode("less than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lt.addChild(ne1); ne1.setParent(lt);
                                                      lt.addChild(ne2); ne2.setParent(lt);
                                                      yyval = new ParserVal(lt);
                                                    }
break;
case 64:
//#line 398 "arthur.yacc"
{
                                                      ParseNode lte = new ParseNode("less than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lte.addChild(ne1); ne1.setParent(lte);
                                                      lte.addChild(ne2); ne2.setParent(lte);
                                                      yyval = new ParserVal(lte);
                                                    }
break;
case 65:
//#line 405 "arthur.yacc"
{
                                                      ParseNode gt = new ParseNode("greater than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gt.addChild(ne1); ne1.setParent(gt);
                                                      gt.addChild(ne2); ne2.setParent(gt);
                                                      yyval = new ParserVal(gt);
                                                    }
break;
case 66:
//#line 412 "arthur.yacc"
{
                                                      ParseNode gte = new ParseNode("greater than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gte.addChild(ne1); ne1.setParent(gte);
                                                      gte.addChild(ne2); ne2.setParent(gte);
                                                      yyval = new ParserVal(gte);
                                                    }
break;
case 67:
//#line 419 "arthur.yacc"
{
                                                      ParseNode eq2x = new ParseNode("is equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      eq2x.addChild(ne1); ne1.setParent(eq2x);
                                                      eq2x.addChild(ne2); ne2.setParent(eq2x);
                                                      yyval = new ParserVal(eq2x);
                                                    }
break;
case 68:
//#line 429 "arthur.yacc"
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
case 69:
//#line 439 "arthur.yacc"
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
case 71:
//#line 453 "arthur.yacc"
{
                                                        ParseNode plus = new ParseNode("+");
                                                        plus.addChild((ParseNode)val_peek(2).obj);
                                                        plus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(plus);
                                                    }
break;
case 72:
//#line 459 "arthur.yacc"
{
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.addChild((ParseNode)val_peek(2).obj);
                                                        minus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(minus);
                                                    }
break;
case 73:
//#line 465 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 74:
//#line 469 "arthur.yacc"
{
                                                        ParseNode times = new ParseNode("*");
                                                        times.addChild((ParseNode)val_peek(2).obj);
                                                        times.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(times);
                                                    }
break;
case 75:
//#line 475 "arthur.yacc"
{
                                                        ParseNode div = new ParseNode("/");
                                                        div.addChild((ParseNode)val_peek(2).obj);
                                                        div.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(div);
                                                    }
break;
case 76:
//#line 481 "arthur.yacc"
{
                                                        ParseNode mod = new ParseNode("%");
                                                        mod.addChild((ParseNode)val_peek(2).obj);
                                                        mod.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(mod);
                                                    }
break;
case 77:
//#line 487 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 78:
//#line 491 "arthur.yacc"
{
                                                        ParseNode exp = new ParseNode("^");
                                                        exp.addChild((ParseNode)val_peek(2).obj);
                                                        exp.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(exp);
                                                    }
break;
case 79:
//#line 497 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 80:
//#line 501 "arthur.yacc"
{
                                                        Color c = (Color) val_peek(0).obj;
                                                        ParseNode color = new ParseNode("Color");
                                                        color.addChild(new ParseNode(c.r.toString(), color));
                                                        color.addChild(new ParseNode(c.g.toString(), color));
                                                        color.addChild(new ParseNode(c.b.toString(), color));
                                                        color.addChild(new ParseNode(c.a.toString(), color));
                                                        yyval = new ParserVal(color);
                                                    }
break;
case 81:
//#line 510 "arthur.yacc"
{
                                                        Number n = (Number) val_peek(0).obj;
                                                        ParseNode number = new ParseNode("Number");
                                                        number.addChild(new ParseNode(n.val.toString(), number));
                                                        yyval = new ParserVal(number);
                                                    }
break;
case 82:
//#line 516 "arthur.yacc"
{
                                                        StringLit s = (StringLit) val_peek(0).obj;
                                                        ParseNode string = new ParseNode("String");
                                                        string.addChild(new ParseNode(s.val, string));
                                                        yyval = new ParserVal(string);
                                                    }
break;
case 83:
//#line 522 "arthur.yacc"
{ ParseNode t = new ParseNode("true"); yyval = new ParserVal(t); }
break;
case 84:
//#line 523 "arthur.yacc"
{ ParseNode f = new ParseNode("false"); yyval = new ParserVal(f); }
break;
case 85:
//#line 524 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 86:
//#line 525 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 87:
//#line 529 "arthur.yacc"
{
                                                        Var v = (Var) val_peek(0).obj;
                                                        ParseNode var = new ParseNode("variable");
                                                        var.addChild(new ParseNode(v.typeName(), var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        yyval = new ParserVal(var);
                                                    }
break;
case 88:
//#line 539 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 89:
//#line 540 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 90:
//#line 541 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 91:
//#line 542 "arthur.yacc"
{
                                                        Identifier i = (Identifier) val_peek(0).obj;
                                                        ParseNode id = new ParseNode("Identifier");
                                                        id.addChild(new ParseNode(i.name, id));
                                                        yyval = new ParserVal(id);
                                                    }
break;
//#line 1472 "Parser.java"
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
