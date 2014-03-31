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
    package arthur.frontend;

    import java.io.Reader;
    import java.io.IOException;
    import java.lang.Math;
 
//#line 24 "Parser.java"




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
    0,    0,    1,    1,    1,    3,    3,    5,    5,    6,
    6,    8,   11,   13,   13,   13,   13,   13,   13,   14,
   14,   16,   15,   17,   17,    2,   18,   21,   22,   23,
   24,   25,   26,   20,   20,   27,   27,   10,   10,   10,
   10,   10,   10,   10,   10,   10,   10,   10,   32,   32,
   32,    4,   30,   30,   30,   29,   29,   29,   33,   33,
   28,   28,   28,   28,    9,    9,    9,   34,   34,   34,
   34,   34,   34,   12,   12,   12,   12,   12,   12,   12,
   31,   31,   35,   35,   35,   36,   36,   36,   36,   37,
   37,   38,   38,   38,   38,   38,   38,   38,    7,   19,
   19,   19,   19,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    1,    1,    2,    0,    1,    1,
    3,    5,    5,    7,    6,    5,    6,    2,    5,    2,
    1,    5,    2,    3,    2,    4,    4,    3,    3,    1,
    2,    2,    2,    0,    1,    1,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    3,    2,
    3,    2,    1,    2,    2,    3,    2,    1,    2,    1,
    1,    1,    1,    0,    1,    1,    1,    1,    1,    1,
    3,    3,    2,    1,    1,    3,    3,    3,    3,    3,
    4,    4,    3,    3,    1,    3,    3,    3,    1,    3,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   99,    1,    0,    0,    4,    0,    6,    0,    0,
    0,   10,    2,    3,    7,   52,    0,    0,    0,   26,
   11,    0,    0,    0,    0,    0,   53,    0,    0,   25,
  103,   92,    0,   94,    0,    0,   46,    0,   40,    0,
   41,    0,   39,    0,    0,    0,    0,   47,   45,   44,
    0,   38,   42,   43,   48,    0,    0,    0,    0,    0,
   91,   55,    0,   18,    0,    0,  100,    0,  101,  102,
    0,    0,    0,   50,    0,    0,   93,   95,   96,    0,
   57,   60,    0,    0,   24,    0,    0,    0,    0,    0,
    0,   33,    0,    0,    0,   32,   31,   54,   59,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   51,   49,   98,   56,    0,    0,    0,    0,
    0,    0,    0,   30,   28,    0,   29,    0,    0,   36,
    0,    0,    0,    0,    0,    0,    0,    0,   90,    0,
    0,   82,    0,   27,    0,   81,   12,    0,    0,   13,
   37,    0,    0,    0,   17,   21,    0,   23,   14,   20,
    0,    0,   22,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,   37,   10,   11,   38,   39,  108,   99,
   41,   42,   43,  154,  155,  156,   20,   67,   68,  128,
   69,   70,  127,   48,   49,   50,  129,   51,   52,   53,
   54,   55,   56,   57,   58,   59,   60,   61,
};
final static short yysindex[] = {                      -187,
 -283,    0,    0,    0, -251,    0, -283,    0, -256, -232,
 -239,    0,    0,    0,    0,    0, -227, -283,   37,    0,
    0, -221, -214, -241, -260, -243,    0,  -91,   77,    0,
    0,    0,    0,    0,    0,    0,    0, -250,    0, -207,
    0,  195,    0, -200, -246, -178, -176,    0,    0,    0,
 -162,    0,    0,    0,    0,  135, -144, -150, -101, -179,
    0,    0, -260,    0, -260,  -84,    0, -121,    0,    0,
 -144, -150, -130,    0,  -84, -128,    0,    0,    0, -189,
    0,    0,  117,  -91,    0,  -53,  -53,  -53,  -53,  -53,
 -118,    0, -138, -260,  -91,    0,    0,    0,    0, -260,
 -260,  -91,  -91,  -91,  -91,  -91,  -91, -109,  -84, -144,
 -150, -103,    0,    0,    0,    0, -225,  -84,  -84,  -84,
  -84,  -84,  135,    0,    0, -121,    0,  -85,  -97,    0,
 -209, -144, -144, -101, -101, -179, -179, -179,    0,  135,
  175,    0,  -95,    0, -260,    0,    0, -221,  -62,    0,
    0,  -82,  135,  -62,    0,    0, -260,    0,    0,    0,
  -80,  135,    0,
};
final static short yyrindex[] = {                         0,
  -64,    0,    0,    0,    0,    0, -208,    0,    0,    0,
  -47,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  226,    0,  252,  278,    0,    0,    0,  191,
    0, -148,    0,  304,   35,  330,  356,    0,    0,    0,
    0,    0,    0,    0,    0, -197, -104,  508,  548,  408,
    0,    0,    0,    0,    0,   14,    0,  382,    0,    0,
  -48,  528,    0,    0,   91,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -120,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -159,  -45,
  605,    0,    0,    0,    0,    0,    0,   16,   93,  152,
  154,  510,    0,    0,    0,    0,    0,    0,  -40,    0,
    0,  -34,   -8,  570,  592,  433,  458,  483,    0,    0,
    0,    0,  191,    0, -102,    0,    0,  -83,  -43,    0,
    0,    0,    0,   -3,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  212,    0,   87,    0,    0,   53,    0,  -58,  -11,
    0,   -4,    0,    0,   89,   90,    0,  -19,  -17,    0,
  -15,  -13,    0,    0,    0,    0,    0,  -25,    0,    0,
    0,    0,  217,   15,   70,  132,  168,  149,
};
final static int YYTABLESIZE=885;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         44,
   76,   45,   25,   46,    2,   47,  112,   40,   28,   44,
   16,   45,   73,   46,   64,   47,   16,   82,   93,   25,
   66,   75,   94,   74,   84,   28,   18,   65,   95,   31,
   32,   33,   34,   35,   36,    1,   44,   17,   45,   71,
   46,  142,   47,   19,   13,   62,   31,   32,   33,   34,
   35,   36,    9,   12,   63,  102,  103,  146,  109,    9,
  109,   58,   58,   44,   85,   45,   92,   46,  130,   47,
   21,  102,  103,  125,   58,  126,   58,  110,    5,  110,
  115,  118,  119,  120,  121,  122,    8,    5,   96,   75,
   97,  102,  103,   15,   72,   66,   66,   80,  161,    1,
    2,   70,   70,   44,   98,   45,  107,   46,    3,   47,
   66,  143,   70,   70,  132,  133,  100,  101,   62,  151,
   44,   44,   45,   45,   46,   46,   47,   47,  147,  149,
  102,  103,  111,   44,  111,   45,  113,   46,  114,   47,
   75,  158,   44,   93,   45,   64,   46,   94,   47,   34,
  163,  124,  109,  117,  123,   72,   72,   72,   72,   72,
  140,   61,   61,   64,  131,   61,  141,   64,  145,   72,
   72,  110,   19,   19,   19,   19,   19,   28,  150,   19,
   19,  104,  105,  106,  144,   19,  157,   19,   19,  162,
   19,   86,   87,   88,   89,   90,  152,  153,   31,   32,
   77,   34,   78,   79,   19,    8,   19,   19,   19,   19,
   19,   19,   16,   16,   16,   28,   14,   73,   73,   16,
   16,   73,    9,   16,   65,   16,  111,   16,   16,   35,
   16,   71,   71,  134,  135,   71,   31,   32,   33,   34,
   78,   79,  159,  160,   16,   83,   16,   16,   16,   16,
   16,   16,   15,   15,   15,  139,    0,   72,   72,   15,
   15,   72,    0,   15,    0,   15,    0,   15,   15,    0,
   15,  136,  137,  138,   70,   70,   80,   80,    0,   70,
   70,   80,   80,   70,   15,   80,   15,   15,   15,   15,
   15,   15,   22,   23,   24,   97,   97,    0,   80,   25,
   26,   97,    0,   27,    0,   28,    0,   29,   30,    0,
   97,   97,   97,   97,   97,   97,   97,   97,   97,   97,
   97,    0,    0,    0,    2,    0,   31,   32,   33,   34,
   35,   36,   22,   23,   24,    0,    0,    0,    0,   25,
   26,    0,    0,   27,    0,   28,    0,   29,   81,    0,
    0,   70,   70,   76,   76,    0,   62,   62,   76,   76,
   62,    0,   76,    0,    2,    0,   31,   32,   33,   34,
   35,   36,   22,   23,   24,   76,    0,    0,    0,   25,
   26,    0,    0,   27,    0,   28,    0,   29,  116,    0,
   22,   23,   24,    0,    0,    0,    0,   25,   26,    0,
    0,   27,    0,   28,    2,   29,   31,   32,   33,   34,
   35,   36,   77,   77,   78,   78,    0,   77,   77,   78,
   78,   77,    2,   78,   31,   32,   33,   34,   35,   36,
  148,   23,   24,    0,   77,    0,   78,   25,   26,    0,
    0,   27,    0,   28,    0,   29,   60,   60,   60,    0,
    0,    0,    0,   60,   60,    0,    0,   60,    0,   60,
    0,   60,    2,    0,   31,   32,   33,   34,   35,   36,
   86,   87,   88,   89,   90,    0,    0,   91,   60,    0,
   60,   60,   60,   60,   60,   60,   74,   74,    0,    0,
    0,   74,   74,    0,    0,   74,    0,    0,    0,    0,
    0,   74,   74,   74,   74,   74,   93,   93,   74,   93,
   93,   93,   68,   68,    0,    0,    0,   68,   68,    0,
    0,   68,    0,    0,    0,    0,    0,   95,   95,   95,
   95,   95,   95,   95,   95,   95,   95,   95,   69,   69,
    0,    0,    0,   69,   69,    0,    0,   69,    0,    0,
    0,    0,    0,   96,   96,   96,   96,   96,   96,   96,
   96,   96,   96,   96,  100,  100,    0,    0,  100,    0,
    0,    0,  100,    0,    0,    0,    0,    0,  100,  100,
  100,  100,  100,  100,  100,  100,  100,  100,  100,  100,
  101,  101,    0,    0,  101,    0,    0,    0,  101,    0,
    0,    0,    0,    0,  101,  101,  101,  101,  101,  101,
  101,  101,  101,  101,  101,  101,  102,  102,    0,    0,
  102,    0,    0,    0,  102,    0,    0,    0,    0,    0,
  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,
  102,  102,   97,   97,    0,    0,    0,   97,   97,    0,
    0,   97,    0,    0,    0,    0,    0,   97,   97,   97,
   97,   97,   97,   97,   97,   97,   97,   97,   89,   89,
    0,    0,    0,   89,   89,    0,    0,   89,    0,    0,
    0,    0,    0,   89,   89,   89,   89,   89,   89,   89,
   89,   89,   89,   86,   86,    0,    0,    0,   86,   86,
    0,    0,   86,    0,    0,    0,    0,    0,   86,   86,
   86,   86,   86,   86,   86,   86,   86,   86,   87,   87,
    0,    0,    0,   87,   87,    0,    0,   87,    0,    0,
    0,    0,    0,   87,   87,   87,   87,   87,   87,   87,
   87,   87,   87,   88,   88,    0,    0,    0,   88,   88,
    0,    0,   88,    0,    0,    0,    0,    0,   88,   88,
   88,   88,   88,   88,   88,   88,   88,   88,   75,   75,
   79,   79,    0,   63,   63,   79,   79,   63,    0,   79,
    0,    0,    0,   75,   75,   75,   75,   75,   75,   75,
   75,    0,   79,   75,   75,    0,    0,   75,    0,    0,
    0,    0,    0,   75,   75,   75,   75,   75,   85,   85,
   75,    0,    0,   85,   85,    0,    0,   85,    0,    0,
    0,    0,    0,   85,   85,   85,   85,   85,   85,   85,
   83,   83,    0,    0,    0,   83,   83,    0,    0,   83,
    0,    0,    0,    0,    0,   83,   83,   83,   83,   83,
   83,   83,   84,   84,    0,    0,    0,   84,   84,    0,
    0,   84,    0,    0,    0,   75,   75,   84,   84,   84,
   84,   84,   84,   84,   67,    0,    0,    0,    0,    0,
   75,   75,   75,   75,   75,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         19,
   26,   19,  263,   19,  288,   19,   65,   19,  269,   29,
  267,   29,  256,   29,  256,   29,  267,   29,  265,  263,
   25,   26,  269,  267,  275,  269,  266,  269,  275,  290,
  291,  292,  293,  294,  295,  287,   56,  270,   56,   25,
   56,  267,   56,  271,  296,  267,  290,  291,  292,  293,
  294,  295,    0,    1,  269,  281,  282,  267,   63,    7,
   65,  259,  260,   83,  272,   83,  267,   83,   94,   83,
   18,  281,  282,   93,  272,   93,  274,   63,  287,   65,
  270,   86,   87,   88,   89,   90,    0,  296,  267,   94,
  267,  281,  282,    7,   25,  100,  101,   28,  157,  287,
  288,  261,  262,  123,  267,  123,  286,  123,  296,  123,
  270,  123,  261,  262,  100,  101,  261,  262,  267,  145,
  140,  141,  140,  141,  140,  141,  140,  141,  140,  141,
  281,  282,   63,  153,   65,  153,  267,  153,  267,  153,
  145,  153,  162,  265,  162,  266,  162,  269,  162,  270,
  162,  290,  157,   84,  273,   86,   87,   88,   89,   90,
  270,  266,  267,  266,   95,  270,  270,  270,  266,  100,
  101,  157,  256,  257,  258,  259,  260,  269,  274,  263,
  264,  283,  284,  285,  270,  269,  269,  271,  272,  270,
  274,  276,  277,  278,  279,  280,  259,  260,  290,  291,
  292,  293,  294,  295,  288,  270,  290,  291,  292,  293,
  294,  295,  256,  257,  258,  269,    5,  266,  267,  263,
  264,  270,  270,  267,  270,  269,  157,  271,  272,  270,
  274,  266,  267,  102,  103,  270,  290,  291,  292,  293,
  294,  295,  154,  154,  288,   29,  290,  291,  292,  293,
  294,  295,  256,  257,  258,  107,   -1,  266,  267,  263,
  264,  270,   -1,  267,   -1,  269,   -1,  271,  272,   -1,
  274,  104,  105,  106,  261,  262,  261,  262,   -1,  266,
  267,  266,  267,  270,  288,  270,  290,  291,  292,  293,
  294,  295,  256,  257,  258,  261,  262,   -1,  283,  263,
  264,  267,   -1,  267,   -1,  269,   -1,  271,  272,   -1,
  276,  277,  278,  279,  280,  281,  282,  283,  284,  285,
  286,   -1,   -1,   -1,  288,   -1,  290,  291,  292,  293,
  294,  295,  256,  257,  258,   -1,   -1,   -1,   -1,  263,
  264,   -1,   -1,  267,   -1,  269,   -1,  271,  272,   -1,
   -1,  261,  262,  261,  262,   -1,  266,  267,  266,  267,
  270,   -1,  270,   -1,  288,   -1,  290,  291,  292,  293,
  294,  295,  256,  257,  258,  283,   -1,   -1,   -1,  263,
  264,   -1,   -1,  267,   -1,  269,   -1,  271,  272,   -1,
  256,  257,  258,   -1,   -1,   -1,   -1,  263,  264,   -1,
   -1,  267,   -1,  269,  288,  271,  290,  291,  292,  293,
  294,  295,  261,  262,  261,  262,   -1,  266,  267,  266,
  267,  270,  288,  270,  290,  291,  292,  293,  294,  295,
  256,  257,  258,   -1,  283,   -1,  283,  263,  264,   -1,
   -1,  267,   -1,  269,   -1,  271,  256,  257,  258,   -1,
   -1,   -1,   -1,  263,  264,   -1,   -1,  267,   -1,  269,
   -1,  271,  288,   -1,  290,  291,  292,  293,  294,  295,
  276,  277,  278,  279,  280,   -1,   -1,  283,  288,   -1,
  290,  291,  292,  293,  294,  295,  261,  262,   -1,   -1,
   -1,  266,  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  279,  280,  281,  282,  283,  284,
  285,  286,  261,  262,   -1,   -1,   -1,  266,  267,   -1,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  286,  261,  262,
   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  286,  261,  262,   -1,   -1,  265,   -1,
   -1,   -1,  269,   -1,   -1,   -1,   -1,   -1,  275,  276,
  277,  278,  279,  280,  281,  282,  283,  284,  285,  286,
  261,  262,   -1,   -1,  265,   -1,   -1,   -1,  269,   -1,
   -1,   -1,   -1,   -1,  275,  276,  277,  278,  279,  280,
  281,  282,  283,  284,  285,  286,  261,  262,   -1,   -1,
  265,   -1,   -1,   -1,  269,   -1,   -1,   -1,   -1,   -1,
  275,  276,  277,  278,  279,  280,  281,  282,  283,  284,
  285,  286,  261,  262,   -1,   -1,   -1,  266,  267,   -1,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  286,  261,  262,
   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  261,  262,   -1,   -1,   -1,  266,  267,
   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,  279,  280,  281,  282,  283,  284,  285,  261,  262,
   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  261,  262,   -1,   -1,   -1,  266,  267,
   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,  279,  280,  281,  282,  283,  284,  285,  261,  262,
  261,  262,   -1,  266,  267,  266,  267,  270,   -1,  270,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  261,  262,
  283,   -1,  283,  266,  267,   -1,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  261,  262,
  283,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  261,  262,   -1,   -1,   -1,  266,  267,   -1,   -1,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,  280,
  281,  282,  261,  262,   -1,   -1,   -1,  266,  267,   -1,
   -1,  270,   -1,   -1,   -1,  261,  262,  276,  277,  278,
  279,  280,  281,  282,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,  280,
};
}
final static short YYFINAL=4;
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
"stuff : variable_decs",
"variable_decs : var_dec_stmt",
"variable_decs : variable_decs var_dec_stmt",
"param_list :",
"param_list : hard_param_list",
"hard_param_list : var",
"hard_param_list : hard_param_list COMMA var",
"dw_stmt : DW LPAREN cond RPAREN stmt",
"sugarloop_stmt : num_expr TIMES LBRACK stmt RBRACK",
"if_stmt : IF LPAREN cond RPAREN stmt elfs else",
"if_stmt : IF LPAREN cond RPAREN stmt elfs",
"if_stmt : IF LPAREN cond RPAREN stmt",
"if_stmt : IF LPAREN cond RPAREN stmt else",
"if_stmt : IF error",
"if_stmt : IF LPAREN cond RPAREN error",
"elfs : elfs elf",
"elfs : elf",
"elf : ELF LPAREN cond RPAREN stmt",
"else : ELSE stmt",
"func_body : LCURLY stmt RCURLY",
"func_body : LCURLY RCURLY",
"func_def : FUNCTION param_list RPAREN func_body",
"fun_call : id LPAREN arg_list RPAREN",
"meth_call : id DOT fun_call",
"prop_access : id DOT prop",
"prop : ID",
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
"stmt : var_dec_stmt",
"stmt : prop_access_stmt",
"stmt : return_stmt",
"return_stmt : RETURN expr SEMI",
"return_stmt : RETURN SEMI",
"return_stmt : RETURN error SEMI",
"var_dec_stmt : var SEMI",
"expr_stmt : SEMI",
"expr_stmt : expr SEMI",
"expr_stmt : error SEMI",
"stmt_block : LCURLY stmt_list RCURLY",
"stmt_block : LCURLY RCURLY",
"stmt_block : stmt_list",
"stmt_list : stmt_list stmt",
"stmt_list : stmt",
"expr : bool_expr",
"expr : num_expr",
"expr : val",
"expr :",
"cond : bool_expr",
"cond : num_expr",
"cond : val",
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

//#line 610 "arthur.yacc"
Lexer lexer;
Token prevTok;
ParseNode AST;

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
        default: return UNKNOWN;
    }
}

public ParseNode doParsing(Reader in) {
    lexer = new Lexer(in);
    errorCount = 0;

    int result = yyparse();

    return AST;
}

public void doParsingAndPrint(Reader in) {
    lexer = new Lexer(in);
    errorCount = 0;

    int result = yyparse();
    System.out.println(AST);
    System.out.println("Number of errors: " + errorCount);
    System.out.println("Return value: " + result);
}
//#line 651 "Parser.java"
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
//#line 29 "arthur.yacc"
{ ParseNode p = new ParseNode("arthur"); yyval = new ParserVal(p); AST = p; }
break;
case 2:
//#line 30 "arthur.yacc"
{ yyval = val_peek(1); AST = (ParseNode) val_peek(1).obj; }
break;
case 3:
//#line 34 "arthur.yacc"
{
                                                        ParseNode s = (ParseNode) val_peek(1).obj;
                                                        ParseNode f = (ParseNode) val_peek(0).obj;
                                                        s.addChild(f);
                                                        yyval = val_peek(1);
                                                    }
break;
case 4:
//#line 40 "arthur.yacc"
{
                                                        ParseNode p = new ParseNode("arthur");
                                                        p.addChild((ParseNode) val_peek(0).obj);
                                                        yyval = new ParserVal(p);
                                                    }
break;
case 5:
//#line 45 "arthur.yacc"
{
                                                        yyval = val_peek(0);
                                                    }
break;
case 6:
//#line 51 "arthur.yacc"
{
                                                      ParseNode p = new ParseNode("globals");
                                                      p.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(p);
                                                    }
break;
case 7:
//#line 56 "arthur.yacc"
{ ParseNode g = (ParseNode)val_peek(1).obj; g.addChild((ParseNode) val_peek(0).obj); yyval = val_peek(1); }
break;
case 8:
//#line 60 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("parameters")); }
break;
case 9:
//#line 61 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 10:
//#line 65 "arthur.yacc"
{
                                                        ParseNode params = new ParseNode("parameters");
                                                        lexer.table = new SymbolTable(lexer.table, "function");
                                                        lexer.startingFunction = true;
                                                        ParseNode var = (ParseNode) val_peek(0).obj;
                                                        var.setParent(params);
                                                        params.addChild(var);
                                                        yyval = new ParserVal(params);
                                                    }
break;
case 11:
//#line 74 "arthur.yacc"
{
                                                        ParseNode params = (ParseNode) val_peek(2).obj;
                                                        ParseNode var = (ParseNode) val_peek(0).obj;
                                                        var.setParent(params);
                                                        params.addChild(var);
                                                        yyval = val_peek(2);
                                                    }
break;
case 12:
//#line 84 "arthur.yacc"
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
case 13:
//#line 97 "arthur.yacc"
{
                                                      ParseNode sl = new ParseNode("sugarloop");
                                                      ParseNode times = new ParseNode("times");
                                                      times.addChild((ParseNode) val_peek(4).obj);
                                                      ParseNode body = new ParseNode("body");
                                                      body.addChild((ParseNode) val_peek(1).obj);
                                                      sl.addChild(times);
                                                      sl.addChild(body);
                                                      yyval = new ParserVal(sl);
                                                    }
break;
case 14:
//#line 110 "arthur.yacc"
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
case 15:
//#line 126 "arthur.yacc"
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
case 16:
//#line 140 "arthur.yacc"
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
case 17:
//#line 152 "arthur.yacc"
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
case 18:
//#line 167 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("wrong if statement??")); }
break;
case 19:
//#line 169 "arthur.yacc"
{ ParseNode ifmaster = new ParseNode("if-switch");
                                                      ParseNode iffer = new ParseNode("if", ifmaster);
                                                      ParseNode condition = new ParseNode("condition");
                                                      ParseNode body = new ParseNode("body");
                                                      condition.addChild((ParseNode) val_peek(2).obj);
                                                      body.addChild(new ParseNode("bad body!"));
                                                      iffer.addChild(condition);
                                                      iffer.addChild(body);
                                                      ifmaster.addChild(iffer);
                                                      yyval = new ParserVal(ifmaster);
                                                    }
break;
case 20:
//#line 183 "arthur.yacc"
{
                                                      ParseNode elfs = (ParseNode) val_peek(1).obj;
                                                      ParseNode elf = (ParseNode) val_peek(0).obj;
                                                      elfs.addChild(elf);
                                                      yyval = val_peek(1);
                                                    }
break;
case 21:
//#line 189 "arthur.yacc"
{
                                                      ParseNode elfs = new ParseNode("elves");
                                                      elfs.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(elfs);
                                                    }
break;
case 22:
//#line 197 "arthur.yacc"
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
case 23:
//#line 210 "arthur.yacc"
{
                                                        ParseNode elser = new ParseNode("else");
                                                        ParseNode body = new ParseNode("body");
                                                        body.addChild((ParseNode) val_peek(0).obj);
                                                        elser.addChild(body);
                                                        yyval = new ParserVal(elser);
                                                    }
break;
case 24:
//#line 220 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 25:
//#line 221 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 26:
//#line 225 "arthur.yacc"
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
case 27:
//#line 241 "arthur.yacc"
{
                                                      ParseNode funCall = new ParseNode("Function call");
                                                      ParseNode name = (ParseNode) val_peek(3).obj;
                                                      funCall.addChild(name);
                                                      ParseNode args = (ParseNode) val_peek(1).obj;
                                                      funCall.addChild(args);
                                                      yyval = new ParserVal(funCall);
                                                    }
break;
case 28:
//#line 252 "arthur.yacc"
{
                                                      ParseNode methCall = new ParseNode("Method call");
                                                      ParseNode ob = (ParseNode) val_peek(2).obj;
                                                      methCall.addChild(ob);
                                                      ParseNode fun = (ParseNode) val_peek(0).obj;
                                                      methCall.addChild(fun);
                                                      yyval = new ParserVal(methCall);
                                                    }
break;
case 29:
//#line 263 "arthur.yacc"
{
                                                      ParseNode main = (ParseNode) val_peek(2).obj;
                                                      ParseNode sub = (ParseNode) val_peek(0).obj;
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
                                                      yyval = new ParserVal(prop);
                                                    }
break;
case 30:
//#line 284 "arthur.yacc"
{
                                                      Identifier i = (Identifier) val_peek(0).obj;
                                                      ParseNode id = new ParseNode("Property");
                                                      id.addChild(new ParseNode(i.name, id));
                                                      yyval = new ParserVal(id);
                                                    }
break;
case 31:
//#line 293 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 32:
//#line 297 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 33:
//#line 301 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 34:
//#line 305 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("arguments")); }
break;
case 35:
//#line 306 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 36:
//#line 310 "arthur.yacc"
{
                                                        ParseNode params = new ParseNode("arguments");
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = new ParserVal(params);
                                                    }
break;
case 37:
//#line 317 "arthur.yacc"
{
                                                        ParseNode params = (ParseNode) val_peek(2).obj;
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = val_peek(2);
                                                    }
break;
case 38:
//#line 327 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 39:
//#line 328 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 40:
//#line 329 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 41:
//#line 330 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 42:
//#line 331 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 43:
//#line 332 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 44:
//#line 333 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 45:
//#line 334 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 46:
//#line 335 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 47:
//#line 336 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 48:
//#line 337 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 49:
//#line 341 "arthur.yacc"
{
                                                      ParseNode e = (ParseNode) val_peek(1).obj;
                                                      ParseNode r = new ParseNode("return");
                                                      r.addChild(e);
                                                      yyval = new ParserVal(r);
                                                    }
break;
case 50:
//#line 347 "arthur.yacc"
{
                                                      ParseNode r = new ParseNode("return");
                                                      yyval = new ParserVal(r);
                                                    }
break;
case 51:
//#line 351 "arthur.yacc"
{
                                                        System.out.println("ERROR: return statement must be in the form: " +
                                                        "LOL");
                                                        errorCount++;
                                                        yyval = new ParserVal(new ParseNode("fucked return stmt"));
                                                    }
break;
case 52:
//#line 360 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 53:
//#line 364 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 54:
//#line 365 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 55:
//#line 366 "arthur.yacc"
{ System.out.println("Not an expression !!!! jesus");
                                                      errorCount++;
                                                      yyval = new ParserVal(new ParseNode("fucked expression stmt"));
                                                    }
break;
case 56:
//#line 373 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 57:
//#line 374 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 58:
//#line 375 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 59:
//#line 379 "arthur.yacc"
{
                                                      ParseNode list = (ParseNode) val_peek(1).obj;
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      list.addChild(s);
                                                      yyval = val_peek(1);
                                                    }
break;
case 60:
//#line 385 "arthur.yacc"
{
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      ParseNode list = new ParseNode("stmt_list");
                                                      list.addChild(s);
                                                      yyval = new ParserVal(list);
                                                    }
break;
case 61:
//#line 394 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 62:
//#line 395 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 63:
//#line 396 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 64:
//#line 397 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 65:
//#line 401 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 66:
//#line 402 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 67:
//#line 403 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 68:
//#line 407 "arthur.yacc"
{ ParseNode bool = new ParseNode("true"); yyval = new ParserVal(bool); }
break;
case 69:
//#line 408 "arthur.yacc"
{ ParseNode bool = new ParseNode("false"); yyval = new ParserVal(bool); }
break;
case 70:
//#line 409 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 71:
//#line 410 "arthur.yacc"
{
                                                      ParseNode and = new ParseNode("and");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      and.addChild(b1); b1.setParent(and);
                                                      and.addChild(b2); b2.setParent(and);
                                                      yyval = new ParserVal(and);
                                                    }
break;
case 72:
//#line 417 "arthur.yacc"
{
                                                      ParseNode or = new ParseNode("or");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      or.addChild(b1); b1.setParent(or);
                                                      or.addChild(b2); b2.setParent(or);
                                                      yyval = new ParserVal(or);
                                                    }
break;
case 73:
//#line 424 "arthur.yacc"
{
                                                      ParseNode b = (ParseNode) val_peek(0).obj;
                                                      ParseNode not = new ParseNode("not");
                                                      not.addChild(b); not.setParent(b.parent); b.setParent(not);
                                                      yyval = new ParserVal(not);
                                                    }
break;
case 74:
//#line 433 "arthur.yacc"
{
                                                      ParseNode number = new ParseNode("number");
                                                      Num n = (Num) val_peek(0).obj;
                                                      ParseNode val = new ParseNode(n.val.toString());
                                                      number.addChild(val);
                                                      yyval = new ParserVal(number); }
break;
case 75:
//#line 439 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 76:
//#line 440 "arthur.yacc"
{
                                                      ParseNode lt = new ParseNode("less than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lt.addChild(ne1); ne1.setParent(lt);
                                                      lt.addChild(ne2); ne2.setParent(lt);
                                                      yyval = new ParserVal(lt);
                                                    }
break;
case 77:
//#line 447 "arthur.yacc"
{
                                                      ParseNode lte = new ParseNode("less than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lte.addChild(ne1); ne1.setParent(lte);
                                                      lte.addChild(ne2); ne2.setParent(lte);
                                                      yyval = new ParserVal(lte);
                                                    }
break;
case 78:
//#line 454 "arthur.yacc"
{
                                                      ParseNode gt = new ParseNode("greater than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gt.addChild(ne1); ne1.setParent(gt);
                                                      gt.addChild(ne2); ne2.setParent(gt);
                                                      yyval = new ParserVal(gt);
                                                    }
break;
case 79:
//#line 461 "arthur.yacc"
{
                                                      ParseNode gte = new ParseNode("greater than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gte.addChild(ne1); ne1.setParent(gte);
                                                      gte.addChild(ne2); ne2.setParent(gte);
                                                      yyval = new ParserVal(gte);
                                                    }
break;
case 80:
//#line 468 "arthur.yacc"
{
                                                      ParseNode eq2x = new ParseNode("is equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      eq2x.addChild(ne1); ne1.setParent(eq2x);
                                                      eq2x.addChild(ne2); ne2.setParent(eq2x);
                                                      yyval = new ParserVal(eq2x);
                                                    }
break;
case 81:
//#line 478 "arthur.yacc"
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
case 82:
//#line 488 "arthur.yacc"
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
case 83:
//#line 506 "arthur.yacc"
{
                                                        ParseNode plus = new ParseNode("+");
                                                        plus.addChild((ParseNode)val_peek(2).obj);
                                                        plus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(plus);
                                                    }
break;
case 84:
//#line 512 "arthur.yacc"
{
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.addChild((ParseNode)val_peek(2).obj);
                                                        minus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(minus);
                                                    }
break;
case 85:
//#line 518 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 86:
//#line 522 "arthur.yacc"
{
                                                        ParseNode times = new ParseNode("*");
                                                        times.addChild((ParseNode)val_peek(2).obj);
                                                        times.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(times);
                                                    }
break;
case 87:
//#line 528 "arthur.yacc"
{
                                                        ParseNode div = new ParseNode("/");
                                                        div.addChild((ParseNode)val_peek(2).obj);
                                                        div.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(div);
                                                    }
break;
case 88:
//#line 534 "arthur.yacc"
{
                                                        ParseNode mod = new ParseNode("%");
                                                        mod.addChild((ParseNode)val_peek(2).obj);
                                                        mod.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(mod);
                                                    }
break;
case 89:
//#line 540 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 90:
//#line 544 "arthur.yacc"
{
                                                        ParseNode exp = new ParseNode("^");
                                                        exp.addChild((ParseNode)val_peek(2).obj);
                                                        exp.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(exp);
                                                    }
break;
case 91:
//#line 550 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 92:
//#line 553 "arthur.yacc"
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
case 93:
//#line 562 "arthur.yacc"
{
                                                        Num n = (Num) val_peek(0).obj;
                                                        ParseNode number = new ParseNode("Number");
                                                        number.addChild(new ParseNode(n.val.toString(), number));
                                                        yyval = new ParserVal(number);
                                                    }
break;
case 94:
//#line 568 "arthur.yacc"
{
                                                        StringLit s = (StringLit) val_peek(0).obj;
                                                        ParseNode string = new ParseNode("String");
                                                        string.addChild(new ParseNode(s.val, string));
                                                        yyval = new ParserVal(string);
                                                    }
break;
case 95:
//#line 574 "arthur.yacc"
{ ParseNode t = new ParseNode("true"); yyval = new ParserVal(t); }
break;
case 96:
//#line 575 "arthur.yacc"
{ ParseNode f = new ParseNode("false"); yyval = new ParserVal(f); }
break;
case 97:
//#line 576 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 98:
//#line 577 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 99:
//#line 581 "arthur.yacc"
{
                                                        Var v = (Var) val_peek(0).obj;
                                                        ParseNode var = new ParseNode("variable");
                                                        var.addChild(new ParseNode(v.typeName(), var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        yyval = new ParserVal(var);
                                                    }
break;
case 100:
//#line 591 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 101:
//#line 592 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 102:
//#line 593 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 103:
//#line 594 "arthur.yacc"
{
                                                        Identifier i = (Identifier) val_peek(0).obj;

                                                        Token check = (Token) lexer.table.get(i.name);
                                                        if (check == null) {
                                                          System.out.println("Identifier not declared on line " + i.line + ": " + i.name);
                                                          errorCount++;
                                                        }

                                                        ParseNode id = new ParseNode("Identifier");
                                                        id.addChild(new ParseNode(i.name, id));
                                                        yyval = new ParserVal(id);
                                                    }
break;
//#line 1568 "Parser.java"
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
