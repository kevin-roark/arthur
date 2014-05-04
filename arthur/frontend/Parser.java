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
    import java.util.HashSet;
 
//#line 25 "Parser.java"




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
public final static short TYPE=296;
public final static short ARG=297;
public final static short EOF=298;
public final static short UNKNOWN=299;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    3,    3,    5,    5,    6,
    6,    8,   11,   12,   12,   12,   12,   12,   12,   13,
   13,   15,   14,   16,   16,    2,   17,   20,   20,   22,
   23,   24,   25,   26,   27,   28,   19,   19,   29,   29,
   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,   34,   34,   34,    4,   32,   32,   32,   31,   31,
   31,   36,   36,   30,   30,   30,   30,   30,    9,    9,
    9,   37,   37,   37,   37,   37,   37,   38,   38,   38,
   38,   38,   38,   38,   39,   39,   39,   39,   39,   39,
   33,   40,   40,   40,   41,   41,   41,   41,   42,   42,
   43,   43,   43,   43,   43,   21,   21,   21,   35,    7,
   18,   18,   18,   18,   18,   18,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    1,    1,    2,    0,    1,    1,
    3,    5,    5,    7,    6,    5,    6,    2,    5,    2,
    1,    5,    2,    3,    2,    4,    4,    3,    3,    1,
    3,    3,    1,    2,    2,    2,    0,    1,    1,    3,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    2,    3,    2,    1,    2,    2,    3,    2,
    1,    2,    1,    1,    1,    1,    1,    0,    1,    1,
    1,    1,    1,    1,    3,    3,    2,    1,    1,    3,
    3,    3,    3,    3,    3,    4,    4,    4,    4,    3,
    2,    3,    3,    1,    3,    3,    3,    1,    3,    1,
    1,    1,    1,    1,    3,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,  109,    1,    0,    0,    4,    0,    6,    0,  110,
    0,    0,   10,    2,    3,    7,   55,    0,    0,    0,
   26,   11,    0,    0,    0,    0,    0,   56,    0,    0,
   25,  116,  106,    0,  108,    0,    0,   30,   49,   43,
    0,   44,   42,    0,    0,  114,    0,  115,    0,    0,
   50,   48,   47,    0,   41,   45,   46,   51,    0,    0,
    0,    0,    0,    0,    0,    0,  100,   58,    0,   18,
    0,    0,  111,    0,  112,  113,    0,    0,    0,    0,
   53,    0,    0,   66,  107,  102,  103,    0,   60,   63,
    0,    0,   24,   36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   35,   34,   57,    0,   62,    0,    0,
    0,    0,    0,    0,    0,   91,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   54,   52,  105,
   59,    0,   33,   32,   28,   31,    0,    0,    0,    0,
   39,    0,    0,    0,    0,    0,   29,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   99,    0,    0,    0,   27,    0,    0,    0,    0,    0,
   12,    0,    0,   13,   40,    0,    0,    0,   17,   21,
    0,   23,   14,   20,    0,    0,   22,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,   39,   11,   12,   13,   40,  123,  108,
   42,   43,  178,  179,  180,   21,   73,   74,  139,   46,
   47,   48,   75,   76,  134,   51,   52,   53,  140,   54,
   55,   56,   57,   58,   59,   60,   61,   62,   63,   64,
   65,   66,   67,
};
final static short yysindex[] = {                      -213,
 -284,    0,    0,    0, -244,    0, -267,    0, -237,    0,
 -233, -217,    0,    0,    0,    0,    0, -207, -284,  -50,
    0,    0, -176, -181, -250,  227,  128,    0, -102,   -8,
    0,    0,    0, -186,    0,    0,    0,    0,    0,    0,
 -164,    0,    0, -151, -179,    0, -149,    0, -136, -118,
    0,    0,    0, -103,    0,    0,    0,    0, -264,  136,
 -121,  233,  -98, -230, -203, -113,    0,    0,  227,    0,
  227,    0,    0, -210,    0,    0, -121,  233, -230,  -89,
    0,  -87,  -81,    0,    0,    0,    0, -256,    0,    0,
   34,  -74,    0,    0, -104,  -25,  211, -102,  -59,  -44,
  -39,  -38,  -73,    0,    0,    0, -102,    0,  227,  227,
  -16,  -16,  -16,  -16,  -16,    0, -102, -102, -102, -102,
 -102, -102,  -31, -121,  233, -230,  -24,    0,    0,    0,
    0,  136,    0,    0,    0,    0, -210, -149,  -19,  -46,
    0, -230, -102, -102, -102, -102,    0, -230, -121, -121,
  233,  233,  233,  233,  233, -203, -203, -113, -113, -113,
    0,  136,  178,  -14,    0,  211, -230, -230, -230, -230,
    0, -176,  -76,    0,    0,  -17,  136,  -76,    0,    0,
  227,    0,    0,    0,  -13,  136,    0,
};
final static short yyrindex[] = {                         0,
  -10,    0,    0,    0,    0,    0, -242,    0,    0,    0,
    0,   -1,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   32,    0,  368,  394,    0,    0,    0,
  194,    0,    0,  264,  472,    0,  420,    0,  290,  316,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -231,
 -157, -140,    0,  659,  593,  493,    0,    0,    0,    0,
    0,  342,    0,  446,    0,    0,  -55, -138,  679,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -209,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    3, -163,  -52,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   18,
    0,   53,    0,    0,    0,    0,    0,   71,   95,  113,
  -85,   88,   90,  370,  396,  615,  637,  518,  543,  568,
    0,    0,    0,  194,    0, -183,  173,  189,  228,  261,
    0, -225,   76,    0,    0,    0,    0,  118,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  249,    0,   20,    0,    0,  243,    0,  -70,  -15,
    0,    0,    0,  117,  124,    0,  -18,  -20,    0,    0,
  200,    0,  -12,   -7,    0,    0,    0,    0,    0,   -5,
    0,    0,    0,    0,    9,  274,   -9,   24,   -3,   89,
  112,   33,  185,
};
final static int YYTABLESIZE=959;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         45,
  127,   44,   17,   10,   41,   70,   45,   49,    9,   45,
  107,   44,   50,  130,   90,    9,   77,   49,   71,    8,
    2,   82,   50,   84,  117,  118,   16,   61,   61,   17,
   19,   19,   19,   19,   19,   83,   18,   19,   19,   45,
   61,   44,    1,   19,    5,   19,   19,   49,   19,   78,
  117,  118,   50,   14,   95,    5,   68,   96,   97,  124,
   37,  124,   19,   20,   19,   19,   19,   19,   19,   19,
   45,   19,   44,    1,    2,  137,   45,  136,   49,  119,
  120,  121,   68,   50,    3,   95,   68,   69,   96,   97,
   68,  141,  125,   84,  125,   98,   92,   74,   74,  149,
  150,   99,  100,  101,  102,   83,   70,   93,   64,   64,
  185,   45,   64,   44,   79,   94,  164,   88,  103,   49,
   74,   74,   74,   74,   50,   65,   65,   74,   74,   65,
  104,   74,   78,   78,  151,  152,  153,  154,  155,  109,
  110,   45,   45,   44,   44,   45,  171,  173,  105,   49,
   49,  158,  159,  160,   50,   50,   45,  126,   44,  126,
  175,  182,   84,  106,   49,   45,   29,   44,  116,   50,
  187,  124,  122,   49,   83,   84,   84,  128,   50,  129,
   84,   84,  176,  177,   84,  133,  142,   32,   33,   85,
   35,   86,   87,  107,   38,  148,  132,   79,   79,   79,
   79,   79,   79,   79,  125,   23,   24,   25,   79,   79,
   77,   77,   26,   27,   77,  143,   28,   71,   29,  166,
   30,   31,  147,   79,   79,   79,   79,   79,  156,  157,
  144,  167,  168,  169,  170,  145,  146,    2,  162,   32,
   33,   34,   35,   36,   37,  163,   38,   23,   24,   25,
  165,  181,   29,   15,   26,   27,  186,  174,   28,    8,
   29,   22,   30,   89,   32,   33,   85,   35,    9,  126,
  135,   38,   69,   32,   33,   72,   35,   86,   87,    2,
   38,   32,   33,   34,   35,   36,   37,   38,   38,   23,
   24,   25,   78,   78,  183,  138,   26,   27,   78,  107,
   28,  184,   29,   91,   30,  131,  161,   78,   78,   78,
   78,   78,  107,  107,    0,  107,  107,  107,   85,   85,
    0,    2,   85,   32,   33,   34,   35,   36,   37,    0,
   38,   16,   16,   16,    0,    0,   90,   90,   16,   16,
   90,    0,   16,    0,   16,    0,   16,   16,   80,   80,
   81,   81,    0,   80,   80,   81,   81,   80,    0,   81,
   75,   75,    0,   16,   75,   16,   16,   16,   16,   16,
   16,    0,   16,   15,   15,   15,    0,    0,   76,   76,
   15,   15,   76,   80,   15,    0,   15,    0,   15,   15,
   26,   23,   24,   25,   81,    0,   29,    0,   26,   27,
    0,    0,   28,    0,   29,   15,   30,   15,   15,   15,
   15,   15,   15,    0,   15,    2,    0,   32,   33,   72,
   35,   36,   37,    2,   38,   32,   33,   34,   35,   36,
   37,    0,   38,  172,   24,   25,    0,    0,   86,   86,
   26,   27,   86,    0,   28,    0,   29,    0,   30,   63,
   63,   63,    0,    0,   87,   87,   63,   63,   87,    0,
   63,    0,   63,    0,   63,    2,    0,   32,   33,   34,
   35,   36,   37,   26,   38,    0,    0,    0,    0,   29,
    0,   63,    0,   63,   63,   63,   63,   63,   63,   26,
   63,    0,    0,   88,   88,   29,    0,   88,    2,    0,
   32,   33,   72,   35,   36,   37,    0,   38,  111,  112,
  113,  114,  115,    0,    0,    0,   32,   33,   72,   35,
   36,   37,    0,   38,  111,  111,   89,   89,  111,    0,
   89,  111,  111,    0,    0,    0,    0,    0,  111,  111,
  111,  111,  111,  111,  111,  111,  111,  111,  111,  111,
  112,  112,    0,    0,  112,    0,    0,  112,  112,    0,
    0,    0,    0,    0,  112,  112,  112,  112,  112,  112,
  112,  112,  112,  112,  112,  112,  113,  113,    0,    0,
  113,    0,    0,  113,  113,    0,    0,    0,    0,    0,
  113,  113,  113,  113,  113,  113,  113,  113,  113,  113,
  113,  113,   78,   78,    0,    0,    0,   78,   78,  107,
    0,   78,    0,    0,    0,    0,    0,   78,   78,   78,
   78,   78,  107,  107,  107,  107,  107,  107,   72,   72,
   82,   82,    0,   72,   72,   82,   82,   72,    0,   82,
    0,    0,    0,  102,  102,  102,  102,  102,  102,  102,
  102,  102,  102,  102,   73,   73,   83,   83,    0,   73,
   73,   83,   83,   73,    0,   83,    0,    0,    0,  103,
  103,  103,  103,  103,  103,  103,  103,  103,  103,  103,
  101,  101,    0,    0,    0,  101,  101,    0,    0,  101,
    0,    0,    0,    0,    0,  101,  101,  101,  101,  101,
  101,  101,  101,  101,  101,  101,  104,  104,    0,    0,
    0,  104,  104,    0,    0,  104,    0,    0,    0,    0,
    0,  104,  104,  104,  104,  104,  104,  104,  104,  104,
  104,  104,  104,  104,    0,    0,    0,  104,  104,    0,
    0,  104,    0,    0,    0,    0,    0,  104,  104,  104,
  104,  104,    0,   98,   98,    0,  104,  104,   98,   98,
    0,    0,   98,    0,    0,    0,    0,    0,   98,   98,
   98,   98,   98,   98,   98,   98,   98,   98,   95,   95,
    0,    0,    0,   95,   95,    0,    0,   95,    0,    0,
    0,    0,    0,   95,   95,   95,   95,   95,   95,   95,
   95,   95,   95,   96,   96,    0,    0,    0,   96,   96,
    0,    0,   96,    0,    0,    0,    0,    0,   96,   96,
   96,   96,   96,   96,   96,   96,   96,   96,   97,   97,
    0,    0,    0,   97,   97,    0,    0,   97,    0,    0,
    0,    0,    0,   97,   97,   97,   97,   97,   97,   97,
   97,   97,   97,   94,   94,    0,    0,    0,   94,   94,
    0,    0,   94,    0,    0,    0,    0,    0,   94,   94,
   94,   94,   94,   94,   94,   92,   92,    0,    0,    0,
   92,   92,    0,    0,   92,    0,    0,    0,    0,    0,
   92,   92,   92,   92,   92,   92,   92,   93,   93,    0,
    0,    0,   93,   93,    0,    0,   93,    0,    0,    0,
    0,    0,   93,   93,   93,   93,   93,   93,   93,   79,
   79,    0,    0,    0,   67,   67,    0,    0,   67,    0,
    0,    0,    0,    0,   79,   79,   79,   79,   79,   79,
   79,    0,    0,    0,   79,   79,    0,    0,   79,    0,
    0,    0,    0,    0,   79,   79,   79,   79,   79,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         20,
   71,   20,  267,  288,   20,  256,   27,   20,    0,   30,
  275,   30,   20,  270,   30,    7,   26,   30,  269,    0,
  288,   27,   30,   27,  281,  282,    7,  259,  260,  267,
  256,  257,  258,  259,  260,   27,  270,  263,  264,   60,
  272,   60,  287,  269,  287,  271,  272,   60,  266,   26,
  281,  282,   60,  298,  265,  298,  266,  268,  269,   69,
  270,   71,  288,  271,  290,  291,  292,  293,  294,  295,
   91,  297,   91,  287,  288,   96,   97,   96,   91,  283,
  284,  285,  266,   91,  298,  265,  270,  269,  268,  269,
  267,   97,   69,   97,   71,  275,  283,  261,  262,  109,
  110,  281,  282,  283,  284,   97,  270,  272,  266,  267,
  181,  132,  270,  132,   26,  267,  132,   29,  268,  132,
  261,  262,  261,  262,  132,  266,  267,  266,  267,  270,
  267,  270,  109,  110,  111,  112,  113,  114,  115,  261,
  262,  162,  163,  162,  163,  166,  162,  163,  267,  162,
  163,  119,  120,  121,  162,  163,  177,   69,  177,   71,
  166,  177,  166,  267,  177,  186,  269,  186,  267,  177,
  186,  181,  286,  186,  166,  261,  262,  267,  186,  267,
  266,  267,  259,  260,  270,  290,   98,  290,  291,  292,
  293,  294,  295,  275,  297,  107,  271,  109,  110,  111,
  112,  113,  114,  115,  181,  256,  257,  258,  261,  262,
  266,  267,  263,  264,  270,  275,  267,  270,  269,  266,
  271,  272,  296,  276,  277,  278,  279,  280,  117,  118,
  275,  143,  144,  145,  146,  275,  275,  288,  270,  290,
  291,  292,  293,  294,  295,  270,  297,  256,  257,  258,
  270,  269,  269,    5,  263,  264,  270,  272,  267,  270,
  269,   19,  271,  272,  290,  291,  292,  293,  270,  181,
  296,  297,  270,  290,  291,  292,  293,  294,  295,  288,
  297,  290,  291,  292,  293,  294,  295,  270,  297,  256,
  257,  258,  261,  262,  178,   96,  263,  264,  267,  268,
  267,  178,  269,   30,  271,  272,  122,  276,  277,  278,
  279,  280,  281,  282,   -1,  284,  285,  286,  266,  267,
   -1,  288,  270,  290,  291,  292,  293,  294,  295,   -1,
  297,  256,  257,  258,   -1,   -1,  266,  267,  263,  264,
  270,   -1,  267,   -1,  269,   -1,  271,  272,  261,  262,
  261,  262,   -1,  266,  267,  266,  267,  270,   -1,  270,
  266,  267,   -1,  288,  270,  290,  291,  292,  293,  294,
  295,   -1,  297,  256,  257,  258,   -1,   -1,  266,  267,
  263,  264,  270,  256,  267,   -1,  269,   -1,  271,  272,
  263,  256,  257,  258,  267,   -1,  269,   -1,  263,  264,
   -1,   -1,  267,   -1,  269,  288,  271,  290,  291,  292,
  293,  294,  295,   -1,  297,  288,   -1,  290,  291,  292,
  293,  294,  295,  288,  297,  290,  291,  292,  293,  294,
  295,   -1,  297,  256,  257,  258,   -1,   -1,  266,  267,
  263,  264,  270,   -1,  267,   -1,  269,   -1,  271,  256,
  257,  258,   -1,   -1,  266,  267,  263,  264,  270,   -1,
  267,   -1,  269,   -1,  271,  288,   -1,  290,  291,  292,
  293,  294,  295,  263,  297,   -1,   -1,   -1,   -1,  269,
   -1,  288,   -1,  290,  291,  292,  293,  294,  295,  263,
  297,   -1,   -1,  266,  267,  269,   -1,  270,  288,   -1,
  290,  291,  292,  293,  294,  295,   -1,  297,  276,  277,
  278,  279,  280,   -1,   -1,   -1,  290,  291,  292,  293,
  294,  295,   -1,  297,  261,  262,  266,  267,  265,   -1,
  270,  268,  269,   -1,   -1,   -1,   -1,   -1,  275,  276,
  277,  278,  279,  280,  281,  282,  283,  284,  285,  286,
  261,  262,   -1,   -1,  265,   -1,   -1,  268,  269,   -1,
   -1,   -1,   -1,   -1,  275,  276,  277,  278,  279,  280,
  281,  282,  283,  284,  285,  286,  261,  262,   -1,   -1,
  265,   -1,   -1,  268,  269,   -1,   -1,   -1,   -1,   -1,
  275,  276,  277,  278,  279,  280,  281,  282,  283,  284,
  285,  286,  261,  262,   -1,   -1,   -1,  266,  267,  268,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  286,  261,  262,
  261,  262,   -1,  266,  267,  266,  267,  270,   -1,  270,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  286,  261,  262,  261,  262,   -1,  266,
  267,  266,  267,  270,   -1,  270,   -1,   -1,   -1,  276,
  277,  278,  279,  280,  281,  282,  283,  284,  285,  286,
  261,  262,   -1,   -1,   -1,  266,  267,   -1,   -1,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,  280,
  281,  282,  283,  284,  285,  286,  261,  262,   -1,   -1,
   -1,  266,  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  279,  280,  281,  282,  283,  284,
  285,  286,  261,  262,   -1,   -1,   -1,  266,  267,   -1,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,   -1,  261,  262,   -1,  285,  286,  266,  267,
   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,  279,  280,  281,  282,  283,  284,  285,  261,  262,
   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  261,  262,   -1,   -1,   -1,  266,  267,
   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,  279,  280,  281,  282,  283,  284,  285,  261,  262,
   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  261,  262,   -1,   -1,   -1,  266,  267,
   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,  279,  280,  281,  282,  261,  262,   -1,   -1,   -1,
  266,  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,  280,  281,  282,  261,  262,   -1,
   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,   -1,
   -1,   -1,  276,  277,  278,  279,  280,  281,  282,  261,
  262,   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,  279,  280,  261,
  262,   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,  279,  280,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=299;
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
"FUNCTION","VAR","VALUE","ID","COLOR","NUMBER","STRINGLIT","TRUE","FALSE",
"TYPE","ARG","EOF","UNKNOWN",
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
"hard_param_list : param",
"hard_param_list : hard_param_list COMMA param",
"dw_stmt : DW LPAREN cond RPAREN stmt",
"sugarloop_stmt : NUMBER TIMES LCURLY stmt RCURLY",
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
"caster : id ARROW TYPE",
"caster : literal ARROW TYPE",
"sysarg : ARG",
"meth_call : id ARROW fun_call",
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
"expr : eq_expr",
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
"eq_expr : id EQ val",
"eq_expr : id PLUS EQ val",
"eq_expr : id MINUS EQ val",
"eq_expr : id TIMES EQ val",
"eq_expr : id DIV EQ val",
"eq_expr : var EQ val",
"eq_stmt : eq_expr SEMI",
"val : val PLUS term",
"val : val MINUS term",
"val : term",
"term : term TIMES exfactor",
"term : term DIV exfactor",
"term : term MOD exfactor",
"term : exfactor",
"exfactor : exfactor EXP factor",
"exfactor : factor",
"factor : literal",
"factor : TRUE",
"factor : FALSE",
"factor : id",
"factor : LPAREN val RPAREN",
"literal : COLOR",
"literal : NUMBER",
"literal : STRINGLIT",
"var : VAR",
"param : VAR",
"id : fun_call",
"id : meth_call",
"id : prop_access",
"id : caster",
"id : sysarg",
"id : ID",
};

//#line 750 "arthur.yacc"
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
//#line 761 "Parser.java"
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
//#line 31 "arthur.yacc"
{ ParseNode p = new ParseNode("arthur"); yyval = new ParserVal(p); AST = p; }
break;
case 2:
//#line 32 "arthur.yacc"
{ yyval = val_peek(1); AST = (ParseNode) val_peek(1).obj; }
break;
case 3:
//#line 36 "arthur.yacc"
{
                                                        ParseNode s = (ParseNode) val_peek(1).obj;
                                                        ParseNode f = (ParseNode) val_peek(0).obj;
                                                        s.addChild(f);
                                                        yyval = val_peek(1);
                                                    }
break;
case 4:
//#line 42 "arthur.yacc"
{
                                                        ParseNode p = new ParseNode("arthur");
                                                        p.addChild((ParseNode) val_peek(0).obj);
                                                        yyval = new ParserVal(p);
                                                    }
break;
case 5:
//#line 47 "arthur.yacc"
{
                                                        yyval = val_peek(0);
                                                    }
break;
case 6:
//#line 53 "arthur.yacc"
{
                                                      ParseNode p = new ParseNode("globals");
                                                      p.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(p);
                                                    }
break;
case 7:
//#line 58 "arthur.yacc"
{ ParseNode g = (ParseNode)val_peek(1).obj; g.addChild((ParseNode) val_peek(0).obj); yyval = val_peek(1); }
break;
case 8:
//#line 62 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("parameters")); }
break;
case 9:
//#line 63 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 10:
//#line 67 "arthur.yacc"
{
                                                        ParseNode params = new ParseNode("parameters");
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
                                                      Num n = (Num) val_peek(4).obj;
                                                      ParseNode val = new ParseNode(n.val.toString());
                                                      times.addChild(val);
                                                      ParseNode body = new ParseNode("body");
                                                      body.addChild((ParseNode) val_peek(1).obj);
                                                      sl.addChild(times);
                                                      sl.addChild(body);
                                                      yyval = new ParserVal(sl);
                                                    }
break;
case 14:
//#line 112 "arthur.yacc"
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
//#line 128 "arthur.yacc"
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
//#line 142 "arthur.yacc"
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
//#line 154 "arthur.yacc"
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
//#line 169 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("wrong if statement??")); }
break;
case 19:
//#line 171 "arthur.yacc"
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
//#line 185 "arthur.yacc"
{
                                                      ParseNode elfs = (ParseNode) val_peek(1).obj;
                                                      ParseNode elf = (ParseNode) val_peek(0).obj;
                                                      elfs.addChild(elf);
                                                      yyval = val_peek(1);
                                                    }
break;
case 21:
//#line 191 "arthur.yacc"
{
                                                      ParseNode elfs = new ParseNode("elves");
                                                      elfs.addChild((ParseNode) val_peek(0).obj);
                                                      yyval = new ParserVal(elfs);
                                                    }
break;
case 22:
//#line 199 "arthur.yacc"
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
//#line 212 "arthur.yacc"
{
                                                        ParseNode elser = new ParseNode("else");
                                                        ParseNode body = new ParseNode("body");
                                                        body.addChild((ParseNode) val_peek(0).obj);
                                                        elser.addChild(body);
                                                        yyval = new ParserVal(elser);
                                                    }
break;
case 24:
//#line 222 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 25:
//#line 223 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 26:
//#line 227 "arthur.yacc"
{
                                                        ParseNode funDef = new ParseNode("Function");
                                                        Function f = (Function) val_peek(3).obj;
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
//#line 255 "arthur.yacc"
{
                                                      ParseNode funCall = new ParseNode("Fun call");
                                                      ParseNode name = (ParseNode) val_peek(3).obj;
                                                      funCall.addChild(name);
                                                      ParseNode args = (ParseNode) val_peek(1).obj;
                                                      funCall.addChild(args);
                                                      yyval = new ParserVal(funCall);
                                                    }
break;
case 28:
//#line 266 "arthur.yacc"
{
                                                      ParseNode caster = new ParseNode("cast");
                                                      ParseNode par = (ParseNode) val_peek(2).obj;
                                                      caster.addChild(par);

                                                      Type t = (Type) val_peek(0).obj;
                                                      ParseNode type = new ParseNode(t.name);
                                                      caster.addChild(type);
                                                      yyval = new ParserVal(caster);
                                                    }
break;
case 29:
//#line 276 "arthur.yacc"
{
                                                      ParseNode caster = new ParseNode("cast");
                                                      ParseNode par = (ParseNode) val_peek(2).obj;
                                                      caster.addChild(par);

                                                      Type t = (Type) val_peek(0).obj;
                                                      ParseNode type = new ParseNode(t.name);
                                                      caster.addChild(type);
                                                      yyval = new ParserVal(caster);
                                                    }
break;
case 30:
//#line 289 "arthur.yacc"
{
                                                      ParseNode arg = new ParseNode("sysarg");
                                                      Arg a = (Arg) val_peek(0).obj;
                                                      arg.addChild(new ParseNode(a.num));
                                                      yyval = new ParserVal(arg);
                                                    }
break;
case 31:
//#line 298 "arthur.yacc"
{
                                                      ParseNode methCall = new ParseNode("Method call");
                                                      ParseNode ob = (ParseNode) val_peek(2).obj;
                                                      methCall.addChild(ob);
                                                      ParseNode fun = (ParseNode) val_peek(0).obj;
                                                      methCall.addChild(fun);
                                                      yyval = new ParserVal(methCall);
                                                    }
break;
case 32:
//#line 309 "arthur.yacc"
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
case 33:
//#line 330 "arthur.yacc"
{
                                                      Identifier i = (Identifier) val_peek(0).obj;
                                                      ParseNode id = new ParseNode("Property");
                                                      id.addChild(new ParseNode(i.name, id));
                                                      yyval = new ParserVal(id);
                                                    }
break;
case 34:
//#line 339 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 35:
//#line 343 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 36:
//#line 347 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 37:
//#line 351 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("arguments")); }
break;
case 38:
//#line 352 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 39:
//#line 356 "arthur.yacc"
{
                                                        ParseNode params = new ParseNode("arguments");
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = new ParserVal(params);
                                                    }
break;
case 40:
//#line 363 "arthur.yacc"
{
                                                        ParseNode params = (ParseNode) val_peek(2).obj;
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = val_peek(2);
                                                    }
break;
case 41:
//#line 373 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 42:
//#line 374 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 43:
//#line 375 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 44:
//#line 376 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 45:
//#line 377 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 46:
//#line 378 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 47:
//#line 379 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 48:
//#line 380 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 49:
//#line 381 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 50:
//#line 382 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 51:
//#line 383 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 52:
//#line 387 "arthur.yacc"
{
                                                      ParseNode e = (ParseNode) val_peek(1).obj;
                                                      ParseNode r = new ParseNode("return");
                                                      r.addChild(e);
                                                      yyval = new ParserVal(r);
                                                    }
break;
case 53:
//#line 393 "arthur.yacc"
{
                                                      ParseNode r = new ParseNode("return");
                                                      yyval = new ParserVal(r);
                                                    }
break;
case 54:
//#line 397 "arthur.yacc"
{
                                                        System.out.println("ERROR: return statement must be in the form: " +
                                                        "LOL");
                                                        errorCount++;
                                                        yyval = new ParserVal(new ParseNode("fucked return stmt"));
                                                    }
break;
case 55:
//#line 406 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 56:
//#line 410 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 57:
//#line 411 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 58:
//#line 412 "arthur.yacc"
{ System.out.println(val_peek(1).obj); System.out.println("Not an expression !!!! jesus");
                                                      errorCount++;
                                                      yyval = new ParserVal(new ParseNode("fucked expression stmt"));
                                                    }
break;
case 59:
//#line 419 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 60:
//#line 420 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 61:
//#line 421 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 62:
//#line 425 "arthur.yacc"
{
                                                      ParseNode list = (ParseNode) val_peek(1).obj;
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      list.addChild(s);
                                                      yyval = val_peek(1);
                                                    }
break;
case 63:
//#line 431 "arthur.yacc"
{
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      ParseNode list = new ParseNode("stmt_list");
                                                      list.addChild(s);
                                                      yyval = new ParserVal(list);
                                                    }
break;
case 64:
//#line 440 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 65:
//#line 441 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 66:
//#line 442 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 67:
//#line 443 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 68:
//#line 444 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 69:
//#line 448 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 70:
//#line 449 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 71:
//#line 450 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 72:
//#line 454 "arthur.yacc"
{ ParseNode bool = new ParseNode("true"); yyval = new ParserVal(bool); }
break;
case 73:
//#line 455 "arthur.yacc"
{ ParseNode bool = new ParseNode("false"); yyval = new ParserVal(bool); }
break;
case 74:
//#line 456 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 75:
//#line 457 "arthur.yacc"
{
                                                      ParseNode and = new ParseNode("and");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      and.addChild(b1); b1.setParent(and);
                                                      and.addChild(b2); b2.setParent(and);
                                                      yyval = new ParserVal(and);
                                                    }
break;
case 76:
//#line 464 "arthur.yacc"
{
                                                      ParseNode or = new ParseNode("or");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      or.addChild(b1); b1.setParent(or);
                                                      or.addChild(b2); b2.setParent(or);
                                                      yyval = new ParserVal(or);
                                                    }
break;
case 77:
//#line 471 "arthur.yacc"
{
                                                      ParseNode b = (ParseNode) val_peek(0).obj;
                                                      ParseNode not = new ParseNode("not");
                                                      not.addChild(b); not.setParent(b.parent); b.setParent(not);
                                                      yyval = new ParserVal(not);
                                                    }
break;
case 78:
//#line 480 "arthur.yacc"
{
                                                      ParseNode number = new ParseNode("number");
                                                      Num n = (Num) val_peek(0).obj;
                                                      ParseNode val = new ParseNode(n.val.toString());
                                                      number.addChild(val);
                                                      yyval = new ParserVal(number); }
break;
case 79:
//#line 486 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 80:
//#line 487 "arthur.yacc"
{
                                                      ParseNode lt = new ParseNode("less than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lt.addChild(ne1); ne1.setParent(lt);
                                                      lt.addChild(ne2); ne2.setParent(lt);
                                                      yyval = new ParserVal(lt);
                                                    }
break;
case 81:
//#line 494 "arthur.yacc"
{
                                                      ParseNode lte = new ParseNode("less than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lte.addChild(ne1); ne1.setParent(lte);
                                                      lte.addChild(ne2); ne2.setParent(lte);
                                                      yyval = new ParserVal(lte);
                                                    }
break;
case 82:
//#line 501 "arthur.yacc"
{
                                                      ParseNode gt = new ParseNode("greater than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gt.addChild(ne1); ne1.setParent(gt);
                                                      gt.addChild(ne2); ne2.setParent(gt);
                                                      yyval = new ParserVal(gt);
                                                    }
break;
case 83:
//#line 508 "arthur.yacc"
{
                                                      ParseNode gte = new ParseNode("greater than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gte.addChild(ne1); ne1.setParent(gte);
                                                      gte.addChild(ne2); ne2.setParent(gte);
                                                      yyval = new ParserVal(gte);
                                                    }
break;
case 84:
//#line 515 "arthur.yacc"
{
                                                      ParseNode eq2x = new ParseNode("is equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      eq2x.addChild(ne1); ne1.setParent(eq2x);
                                                      eq2x.addChild(ne2); ne2.setParent(eq2x);
                                                      yyval = new ParserVal(eq2x);
                                                    }
break;
case 85:
//#line 525 "arthur.yacc"
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
case 86:
//#line 535 "arthur.yacc"
{
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) val_peek(3).obj;
                                                        ParseNode val = (ParseNode) val_peek(0).obj;
                                                        ParseNode plus = new ParseNode("+");
                                                        plus.addChild(id);
                                                        plus.addChild(val);
                                                        eq.addChild(id);
                                                        eq.addChild(plus);
                                                        yyval = new ParserVal(eq);
                                                    }
break;
case 87:
//#line 546 "arthur.yacc"
{
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) val_peek(3).obj;
                                                        ParseNode val = (ParseNode) val_peek(0).obj;
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.addChild(id);
                                                        minus.addChild(val);
                                                        eq.addChild(id);
                                                        eq.addChild(minus);
                                                        yyval = new ParserVal(eq);
                                                    }
break;
case 88:
//#line 557 "arthur.yacc"
{
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) val_peek(3).obj;
                                                        ParseNode val = (ParseNode) val_peek(0).obj;
                                                        ParseNode mult = new ParseNode("*");
                                                        mult.addChild(id);
                                                        mult.addChild(val);
                                                        eq.addChild(id);
                                                        eq.addChild(mult);
                                                        yyval = new ParserVal(eq);
                                                    }
break;
case 89:
//#line 568 "arthur.yacc"
{
                                                        ParseNode eq = new ParseNode("=");
                                                        ParseNode id = (ParseNode) val_peek(3).obj;
                                                        ParseNode val = (ParseNode) val_peek(0).obj;
                                                        ParseNode div = new ParseNode("/");
                                                        div.addChild(id);
                                                        div.addChild(val);
                                                        eq.addChild(id);
                                                        eq.addChild(div);
                                                        yyval = new ParserVal(eq);
                                                    }
break;
case 90:
//#line 579 "arthur.yacc"
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
case 91:
//#line 597 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 92:
//#line 601 "arthur.yacc"
{
                                                        ParseNode plus = new ParseNode("+");
                                                        plus.addChild((ParseNode)val_peek(2).obj);
                                                        plus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(plus);
                                                    }
break;
case 93:
//#line 607 "arthur.yacc"
{
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.addChild((ParseNode)val_peek(2).obj);
                                                        minus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(minus);
                                                    }
break;
case 94:
//#line 613 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 95:
//#line 617 "arthur.yacc"
{
                                                        ParseNode times = new ParseNode("*");
                                                        times.addChild((ParseNode)val_peek(2).obj);
                                                        times.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(times);
                                                    }
break;
case 96:
//#line 623 "arthur.yacc"
{
                                                        ParseNode div = new ParseNode("/");
                                                        div.addChild((ParseNode)val_peek(2).obj);
                                                        div.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(div);
                                                    }
break;
case 97:
//#line 629 "arthur.yacc"
{
                                                        ParseNode mod = new ParseNode("%");
                                                        mod.addChild((ParseNode)val_peek(2).obj);
                                                        mod.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(mod);
                                                    }
break;
case 98:
//#line 635 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 99:
//#line 639 "arthur.yacc"
{
                                                        ParseNode exp = new ParseNode("^");
                                                        exp.addChild((ParseNode)val_peek(2).obj);
                                                        exp.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(exp);
                                                    }
break;
case 100:
//#line 645 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 101:
//#line 648 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 102:
//#line 649 "arthur.yacc"
{ ParseNode t = new ParseNode("true"); yyval = new ParserVal(t); }
break;
case 103:
//#line 650 "arthur.yacc"
{ ParseNode f = new ParseNode("false"); yyval = new ParserVal(f); }
break;
case 104:
//#line 651 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 105:
//#line 652 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 106:
//#line 656 "arthur.yacc"
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
case 107:
//#line 665 "arthur.yacc"
{
                                                        Num n = (Num) val_peek(0).obj;
                                                        ParseNode number = new ParseNode("number");
                                                        number.addChild(new ParseNode(n.val.toString(), number));
                                                        yyval = new ParserVal(number);
                                                    }
break;
case 108:
//#line 671 "arthur.yacc"
{
                                                        StringLit s = (StringLit) val_peek(0).obj;
                                                        ParseNode string = new ParseNode("String");
                                                        string.addChild(new ParseNode(s.val, string));
                                                        yyval = new ParserVal(string);
                                                    }
break;
case 109:
//#line 680 "arthur.yacc"
{
                                                        Var v = (Var) val_peek(0).obj;
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
                                                        yyval = new ParserVal(var);
                                                    }
break;
case 110:
//#line 702 "arthur.yacc"
{
                                                        Var v = (Var) val_peek(0).obj;
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
                                                        yyval = new ParserVal(var);
                                                    }
break;
case 111:
//#line 724 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 112:
//#line 725 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 113:
//#line 726 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 114:
//#line 727 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 115:
//#line 728 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 116:
//#line 729 "arthur.yacc"
{
                                                        Identifier i = (Identifier) val_peek(0).obj;

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
                                                        yyval = new ParserVal(id);
                                                    }
break;
//#line 1840 "Parser.java"
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
