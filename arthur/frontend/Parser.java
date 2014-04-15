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
public final static short TYPE=296;
public final static short EOF=297;
public final static short UNKNOWN=298;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    1,    1,    3,    3,    5,    5,    6,
    6,    8,   11,   13,   13,   13,   13,   13,   13,   14,
   14,   16,   15,   17,   17,    2,   18,   21,   22,   23,
   24,   25,   26,   27,   20,   20,   28,   28,   10,   10,
   10,   10,   10,   10,   10,   10,   10,   10,   10,   33,
   33,   33,    4,   31,   31,   31,   30,   30,   30,   35,
   35,   29,   29,   29,   29,   29,    9,    9,    9,   36,
   36,   36,   36,   36,   36,   12,   12,   12,   12,   12,
   12,   12,   37,   37,   37,   37,   37,   37,   32,   38,
   38,   38,   39,   39,   39,   39,   40,   40,   41,   41,
   41,   41,   41,   41,   41,   34,    7,   19,   19,   19,
   19,   19,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    1,    1,    2,    0,    1,    1,
    3,    5,    5,    7,    6,    5,    6,    2,    5,    2,
    1,    5,    2,    3,    2,    4,    4,    3,    3,    3,
    1,    2,    2,    2,    0,    1,    1,    3,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    3,
    2,    3,    2,    1,    2,    2,    3,    2,    1,    2,
    1,    1,    1,    1,    1,    0,    1,    1,    1,    1,
    1,    1,    3,    3,    2,    1,    1,    3,    3,    3,
    3,    3,    3,    4,    4,    4,    4,    3,    2,    3,
    3,    1,    3,    3,    3,    1,    3,    1,    1,    1,
    1,    1,    1,    1,    3,    1,    1,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,  106,    1,    0,    0,    4,    0,    6,    0,  107,
    0,    0,   10,    2,    3,    7,   53,    0,    0,    0,
   26,   11,    0,    0,    0,    0,    0,   54,    0,    0,
   25,  112,   99,    0,  101,    0,    0,   47,   41,    0,
   42,    0,   40,    0,    0,  111,    0,    0,   48,   46,
   45,    0,   39,   43,   44,   49,    0,    0,    0,    0,
    0,    0,    0,   98,   56,    0,   18,    0,    0,  108,
    0,  109,  110,    0,    0,    0,   51,    0,    0,    0,
   64,  100,  102,  103,    0,   58,   61,    0,   24,    0,
    0,    0,    0,    0,    0,   34,    0,    0,    0,    0,
    0,    0,    0,    0,   33,   32,   55,    0,   60,    0,
    0,   89,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   52,   50,  105,   57,    0,    0,    0,
    0,    0,    0,   31,   30,   28,   29,    0,    0,    0,
   37,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   97,    0,    0,    0,   27,    0,
    0,    0,    0,    0,   12,    0,    0,   13,   38,    0,
    0,    0,   17,   21,    0,   23,   14,   20,    0,    0,
   22,
};
final static short yydgoto[] = {                          4,
    5,    6,    7,   38,   11,   12,   13,   39,  119,  109,
   41,   42,   43,  172,  173,  174,   21,   70,   71,  139,
   46,   72,   73,  135,   49,   50,   51,  140,   52,   53,
   54,   55,   56,   57,   58,   59,   60,   61,   62,   63,
   64,
};
final static short yysindex[] = {                      -267,
 -284,    0,    0,    0, -286,    0, -263,    0, -236,    0,
 -228, -200,    0,    0,    0,    0,    0, -189, -284,   83,
    0,    0, -190, -184, -250,  -52, -230,    0, -240,  123,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -175,
    0,  183,    0, -150, -194,    0, -143, -138,    0,    0,
    0, -135,    0,    0,    0,    0, -219,  181, -127, -124,
  -36,  -98, -140,    0,    0,  -52,    0,  -52,  241,    0,
 -224,    0,    0, -127,  -36, -119,    0,  241, -104,  -76,
    0,    0,    0,    0, -198,    0,    0,  163,    0, -102,
 -102, -102, -102, -102,  -66,    0,  -80, -171, -111, -240,
  -62,  -57,  -39,  -31,    0,    0,    0, -240,    0,  -52,
  -52,    0, -240, -240, -240, -240, -240, -240,  -99,  241,
 -127,  -36,  -54,    0,    0,    0,    0,  241,  241,  241,
  241,  241,  181,    0,    0,    0,    0, -224,   -1,  -33,
    0,  -36, -240, -240, -240, -240,  -36, -127, -127,  -98,
  -98, -140, -140, -140,    0,  181,  221,  -22,    0, -111,
  -36,  -36,  -36,  -36,    0, -190, -105,    0,    0,    2,
  181, -105,    0,    0,  -52,    0,    0,    0,    6,  181,
    0,
};
final static short yyrindex[] = {                         0,
   20,    0,    0,    0,    0,    0, -265,    0,    0,    0,
    0,   33,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  350,    0,  376,  402,    0,    0,  237,
    0,  -59,    0,  272,  454,    0,  298,  324,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -225,  -92,    0,
  575,  615,  475,    0,    0,    0,    0,    0, -139,    0,
  428,    0,    0,  -42,  595,    0,    0,  -61,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   12,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -156,
   39,  672,    0,    0,    0,    0,    0, -163,   19,   59,
   99,  139,    0,    0,    0,    0,    0,    0,    0,   41,
    0,   -2,    0,    0,    0,    0,   17,   38,   52,  637,
  659,  500,  525,  550,    0,    0,    0,  237,    0,   22,
   57,   78,   92,   97,    0,  -37,    3,    0,    0,    0,
    0,   43,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,  268,    0,  109,    0,    0,  294,    0,  -65,   -7,
    0,    1,    0,    0,  144,  156,    0,  -18,  -20,    0,
    0,  -15,  -12,    0,    0,    0,    0,    0,  -13,    0,
    0,    0,    0,    9,  300,   -9,   -3,  104,  149,  114,
  214,
};
final static int YYTABLESIZE=952;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         45,
    1,   44,  123,   10,   47,   67,   45,   48,    9,   45,
   14,   44,   40,   79,   47,    9,   74,   48,   68,    1,
    2,    5,   87,   81,    2,   76,   69,   78,   29,    3,
   17,    5,   26,   59,   59,   80,   77,   45,   29,   44,
   97,   18,   47,   98,   99,   48,   59,   17,   59,   32,
   33,   82,   35,   83,   84,  108,  121,    2,  121,   32,
   33,   34,   35,   36,   37,   19,  120,   45,  120,   44,
   97,  126,   47,   98,   99,   48,   65,  138,   45,  137,
  100,   20,  113,  114,   66,  141,  101,  102,  103,  104,
  128,  129,  130,  131,  132,   81,   89,   82,   82,   78,
  148,  149,   82,   82,   72,   72,   82,   80,    8,  179,
   69,   69,   45,   68,   44,   16,   96,   47,   32,   82,
   48,   72,   72,  105,  136,  158,   72,   72,  106,   75,
   72,  107,   85,  110,  111,   45,   45,   44,   44,   45,
   47,   47,  112,   48,   48,  118,  169,  124,  165,  167,
   45,   26,   44,  170,  171,   47,   81,   29,   48,   45,
   78,   44,  125,  176,   47,  121,   29,   48,   80,  122,
  156,  122,  181,   62,   62,  120,    2,   62,   32,   33,
   34,   35,   36,   37,  115,  116,  117,   32,   33,   34,
   35,   83,   84,   75,   75,   75,   75,   75,  108,   72,
   72,   72,   72,  142,   63,   63,  133,   63,   63,  134,
   26,  147,  143,   75,   75,  157,   29,  144,   19,   19,
   19,   19,   19,   75,   75,   19,   19,   75,  152,  153,
  154,   19,  160,   19,   19,  145,   19,   32,   33,   34,
   35,   36,   37,  146,  113,  114,  161,  162,  163,  164,
   19,  168,   19,   19,   19,   19,   19,   19,   16,   16,
   16,  150,  151,   83,   83,   16,   16,   83,  159,   16,
  175,   16,   15,   16,   16,  180,   16,   66,  122,   78,
   78,   35,   88,   88,   78,   78,   88,   66,   78,    8,
   16,   66,   16,   16,   16,   16,   16,   16,   15,   15,
   15,   78,    9,   73,   73,   15,   15,   73,   67,   15,
   36,   15,   22,   15,   15,  177,   15,   74,   74,   79,
   79,   74,   84,   84,   79,   79,   84,  178,   79,   88,
   15,  155,   15,   15,   15,   15,   15,   15,   23,   24,
   25,   79,    0,   85,   85,   26,   27,   85,    0,   28,
    0,   29,    0,   30,   31,    0,    0,   86,   86,   80,
   80,   86,   87,   87,   80,   80,   87,    0,   80,    0,
    2,    0,   32,   33,   34,   35,   36,   37,   23,   24,
   25,   80,    0,    0,    0,   26,   27,    0,    0,   28,
    0,   29,    0,   30,   86,    0,    0,    0,    0,   81,
   81,    0,    0,    0,   81,   81,    0,    0,   81,    0,
    2,    0,   32,   33,   34,   35,   36,   37,   23,   24,
   25,   81,    0,    0,    0,   26,   27,    0,    0,   28,
    0,   29,    0,   30,  127,    0,   23,   24,   25,    0,
    0,    0,    0,   26,   27,    0,    0,   28,    0,   29,
    2,   30,   32,   33,   34,   35,   36,   37,   90,   91,
   92,   93,   94,    0,    0,   95,    0,    0,    2,    0,
   32,   33,   34,   35,   36,   37,  166,   24,   25,    0,
    0,    0,    0,   26,   27,    0,    0,   28,    0,   29,
    0,   30,   61,   61,   61,    0,    0,    0,    0,   61,
   61,    0,    0,   61,    0,   61,    0,   61,    2,    0,
   32,   33,   34,   35,   36,   37,   90,   91,   92,   93,
   94,    0,    0,    0,   61,    0,   61,   61,   61,   61,
   61,   61,  108,  108,    0,    0,  108,    0,    0,  108,
  108,    0,    0,    0,    0,    0,  108,  108,  108,  108,
  108,  108,  108,  108,  108,  108,  108,  108,  109,  109,
    0,    0,  109,    0,    0,  109,  109,    0,    0,    0,
    0,    0,  109,  109,  109,  109,  109,  109,  109,  109,
  109,  109,  109,  109,  110,  110,    0,    0,  110,    0,
    0,  110,  110,    0,    0,    0,    0,    0,  110,  110,
  110,  110,  110,  110,  110,  110,  110,  110,  110,  110,
   76,   76,    0,    0,    0,   76,   76,    0,    0,   76,
    0,    0,    0,    0,    0,   76,   76,   76,   76,   76,
  100,  100,   76,  100,  100,  100,   70,   70,    0,    0,
    0,   70,   70,    0,    0,   70,    0,    0,    0,    0,
    0,  102,  102,  102,  102,  102,  102,  102,  102,  102,
  102,  102,   71,   71,    0,    0,    0,   71,   71,    0,
    0,   71,    0,    0,    0,    0,    0,  103,  103,  103,
  103,  103,  103,  103,  103,  103,  103,  103,  104,  104,
    0,    0,    0,  104,  104,    0,    0,  104,    0,    0,
    0,    0,    0,  104,  104,  104,  104,  104,  104,  104,
  104,  104,  104,  104,  104,  104,    0,    0,    0,  104,
  104,    0,    0,  104,    0,    0,    0,    0,    0,  104,
  104,  104,  104,  104,    0,   96,   96,    0,  104,  104,
   96,   96,    0,    0,   96,    0,    0,    0,    0,    0,
   96,   96,   96,   96,   96,   96,   96,   96,   96,   96,
   93,   93,    0,    0,    0,   93,   93,    0,    0,   93,
    0,    0,    0,    0,    0,   93,   93,   93,   93,   93,
   93,   93,   93,   93,   93,   94,   94,    0,    0,    0,
   94,   94,    0,    0,   94,    0,    0,    0,    0,    0,
   94,   94,   94,   94,   94,   94,   94,   94,   94,   94,
   95,   95,    0,    0,    0,   95,   95,    0,    0,   95,
    0,    0,    0,    0,    0,   95,   95,   95,   95,   95,
   95,   95,   95,   95,   95,   77,   77,    0,    0,    0,
   65,   65,    0,    0,   65,    0,    0,    0,    0,    0,
   77,   77,   77,   77,   77,   77,   77,   77,    0,    0,
   77,   77,    0,    0,   77,    0,    0,    0,    0,    0,
   77,   77,   77,   77,   77,   92,   92,   77,    0,    0,
   92,   92,    0,    0,   92,    0,    0,    0,    0,    0,
   92,   92,   92,   92,   92,   92,   92,   90,   90,    0,
    0,    0,   90,   90,    0,    0,   90,    0,    0,    0,
    0,    0,   90,   90,   90,   90,   90,   90,   90,   91,
   91,    0,    0,    0,   91,   91,    0,    0,   91,    0,
    0,    0,   77,   77,   91,   91,   91,   91,   91,   91,
   91,   69,    0,    0,    0,    0,    0,   77,   77,   77,
   77,   77,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         20,
  287,   20,   68,  288,   20,  256,   27,   20,    0,   30,
  297,   30,   20,   27,   30,    7,   26,   30,  269,  287,
  288,  287,   30,   27,  288,  256,   26,   27,  269,  297,
  267,  297,  263,  259,  260,   27,  267,   58,  269,   58,
  265,  270,   58,  268,  269,   58,  272,  267,  274,  290,
  291,  292,  293,  294,  295,  275,   66,  288,   68,  290,
  291,  292,  293,  294,  295,  266,   66,   88,   68,   88,
  265,  270,   88,  268,  269,   88,  267,   98,   99,   98,
  275,  271,  281,  282,  269,   99,  281,  282,  283,  284,
   90,   91,   92,   93,   94,   99,  272,  261,  262,   99,
  110,  111,  266,  267,  261,  262,  270,   99,    0,  175,
  110,  111,  133,  270,  133,    7,  267,  133,  290,  283,
  133,  261,  262,  267,  296,  133,  266,  267,  267,   26,
  270,  267,   29,  261,  262,  156,  157,  156,  157,  160,
  156,  157,  267,  156,  157,  286,  160,  267,  156,  157,
  171,  263,  171,  259,  260,  171,  160,  269,  171,  180,
  160,  180,  267,  171,  180,  175,  269,  180,  160,   66,
  270,   68,  180,  266,  267,  175,  288,  270,  290,  291,
  292,  293,  294,  295,  283,  284,  285,  290,  291,  292,
  293,  294,  295,   90,   91,   92,   93,   94,  275,  261,
  262,  261,  262,  100,  266,  267,  273,  267,  270,  290,
  263,  108,  275,  110,  111,  270,  269,  275,  256,  257,
  258,  259,  260,  266,  267,  263,  264,  270,  115,  116,
  117,  269,  266,  271,  272,  275,  274,  290,  291,  292,
  293,  294,  295,  275,  281,  282,  143,  144,  145,  146,
  288,  274,  290,  291,  292,  293,  294,  295,  256,  257,
  258,  113,  114,  266,  267,  263,  264,  270,  270,  267,
  269,  269,    5,  271,  272,  270,  274,  266,  175,  261,
  262,  270,  266,  267,  266,  267,  270,  266,  270,  270,
  288,  270,  290,  291,  292,  293,  294,  295,  256,  257,
  258,  283,  270,  266,  267,  263,  264,  270,  270,  267,
  270,  269,   19,  271,  272,  172,  274,  266,  267,  261,
  262,  270,  266,  267,  266,  267,  270,  172,  270,   30,
  288,  118,  290,  291,  292,  293,  294,  295,  256,  257,
  258,  283,   -1,  266,  267,  263,  264,  270,   -1,  267,
   -1,  269,   -1,  271,  272,   -1,   -1,  266,  267,  261,
  262,  270,  266,  267,  266,  267,  270,   -1,  270,   -1,
  288,   -1,  290,  291,  292,  293,  294,  295,  256,  257,
  258,  283,   -1,   -1,   -1,  263,  264,   -1,   -1,  267,
   -1,  269,   -1,  271,  272,   -1,   -1,   -1,   -1,  261,
  262,   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,
  288,   -1,  290,  291,  292,  293,  294,  295,  256,  257,
  258,  283,   -1,   -1,   -1,  263,  264,   -1,   -1,  267,
   -1,  269,   -1,  271,  272,   -1,  256,  257,  258,   -1,
   -1,   -1,   -1,  263,  264,   -1,   -1,  267,   -1,  269,
  288,  271,  290,  291,  292,  293,  294,  295,  276,  277,
  278,  279,  280,   -1,   -1,  283,   -1,   -1,  288,   -1,
  290,  291,  292,  293,  294,  295,  256,  257,  258,   -1,
   -1,   -1,   -1,  263,  264,   -1,   -1,  267,   -1,  269,
   -1,  271,  256,  257,  258,   -1,   -1,   -1,   -1,  263,
  264,   -1,   -1,  267,   -1,  269,   -1,  271,  288,   -1,
  290,  291,  292,  293,  294,  295,  276,  277,  278,  279,
  280,   -1,   -1,   -1,  288,   -1,  290,  291,  292,  293,
  294,  295,  261,  262,   -1,   -1,  265,   -1,   -1,  268,
  269,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  286,  261,  262,
   -1,   -1,  265,   -1,   -1,  268,  269,   -1,   -1,   -1,
   -1,   -1,  275,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  286,  261,  262,   -1,   -1,  265,   -1,
   -1,  268,  269,   -1,   -1,   -1,   -1,   -1,  275,  276,
  277,  278,  279,  280,  281,  282,  283,  284,  285,  286,
  261,  262,   -1,   -1,   -1,  266,  267,   -1,   -1,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,  280,
  281,  282,  283,  284,  285,  286,  261,  262,   -1,   -1,
   -1,  266,  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  279,  280,  281,  282,  283,  284,
  285,  286,  261,  262,   -1,   -1,   -1,  266,  267,   -1,
   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,  286,  261,  262,
   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,  286,  261,  262,   -1,   -1,   -1,  266,
  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  279,  280,   -1,  261,  262,   -1,  285,  286,
  266,  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,  280,  281,  282,  283,  284,  285,
  261,  262,   -1,   -1,   -1,  266,  267,   -1,   -1,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,  280,
  281,  282,  283,  284,  285,  261,  262,   -1,   -1,   -1,
  266,  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,  280,  281,  282,  283,  284,  285,
  261,  262,   -1,   -1,   -1,  266,  267,   -1,   -1,  270,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,  280,
  281,  282,  283,  284,  285,  261,  262,   -1,   -1,   -1,
  266,  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,  280,  261,  262,  283,   -1,   -1,
  266,  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,  280,  261,  262,  283,   -1,   -1,
  266,  267,   -1,   -1,  270,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,  280,  281,  282,  261,  262,   -1,
   -1,   -1,  266,  267,   -1,   -1,  270,   -1,   -1,   -1,
   -1,   -1,  276,  277,  278,  279,  280,  281,  282,  261,
  262,   -1,   -1,   -1,  266,  267,   -1,   -1,  270,   -1,
   -1,   -1,  261,  262,  276,  277,  278,  279,  280,  281,
  282,  270,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=298;
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
"TYPE","EOF","UNKNOWN",
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
"caster : id ARROW TYPE",
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
"factor : COLOR",
"factor : NUMBER",
"factor : STRINGLIT",
"factor : TRUE",
"factor : FALSE",
"factor : id",
"factor : LPAREN val RPAREN",
"var : VAR",
"param : VAR",
"id : fun_call",
"id : meth_call",
"id : prop_access",
"id : caster",
"id : ID",
};

//#line 683 "arthur.yacc"
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
        case Tokens.TYPE: return TYPE;
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
//#line 686 "Parser.java"
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
                                                        ParseNode funDef = new ParseNode("Function");
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
                                                      ParseNode funCall = new ParseNode("Fun call");
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
//#line 265 "arthur.yacc"
{
                                                      ParseNode methCall = new ParseNode("Method call");
                                                      ParseNode ob = (ParseNode) val_peek(2).obj;
                                                      methCall.addChild(ob);
                                                      ParseNode fun = (ParseNode) val_peek(0).obj;
                                                      methCall.addChild(fun);
                                                      yyval = new ParserVal(methCall);
                                                    }
break;
case 30:
//#line 276 "arthur.yacc"
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
case 31:
//#line 297 "arthur.yacc"
{
                                                      Identifier i = (Identifier) val_peek(0).obj;
                                                      ParseNode id = new ParseNode("Property");
                                                      id.addChild(new ParseNode(i.name, id));
                                                      yyval = new ParserVal(id);
                                                    }
break;
case 32:
//#line 306 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 33:
//#line 310 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 34:
//#line 314 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 35:
//#line 318 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("arguments")); }
break;
case 36:
//#line 319 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 37:
//#line 323 "arthur.yacc"
{
                                                        ParseNode params = new ParseNode("arguments");
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = new ParserVal(params);
                                                    }
break;
case 38:
//#line 330 "arthur.yacc"
{
                                                        ParseNode params = (ParseNode) val_peek(2).obj;
                                                        ParseNode ex = (ParseNode) val_peek(0).obj;
                                                        ex.setParent(params);
                                                        params.addChild(ex);
                                                        yyval = val_peek(2);
                                                    }
break;
case 39:
//#line 340 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 40:
//#line 341 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 41:
//#line 342 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 42:
//#line 343 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 43:
//#line 344 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 44:
//#line 345 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 45:
//#line 346 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 46:
//#line 347 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 47:
//#line 348 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 48:
//#line 349 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 49:
//#line 350 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 50:
//#line 354 "arthur.yacc"
{
                                                      ParseNode e = (ParseNode) val_peek(1).obj;
                                                      ParseNode r = new ParseNode("return");
                                                      r.addChild(e);
                                                      yyval = new ParserVal(r);
                                                    }
break;
case 51:
//#line 360 "arthur.yacc"
{
                                                      ParseNode r = new ParseNode("return");
                                                      yyval = new ParserVal(r);
                                                    }
break;
case 52:
//#line 364 "arthur.yacc"
{
                                                        System.out.println("ERROR: return statement must be in the form: " +
                                                        "LOL");
                                                        errorCount++;
                                                        yyval = new ParserVal(new ParseNode("fucked return stmt"));
                                                    }
break;
case 53:
//#line 373 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 54:
//#line 377 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 55:
//#line 378 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 56:
//#line 379 "arthur.yacc"
{ System.out.println("Not an expression !!!! jesus");
                                                      errorCount++;
                                                      yyval = new ParserVal(new ParseNode("fucked expression stmt"));
                                                    }
break;
case 57:
//#line 386 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 58:
//#line 387 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 59:
//#line 388 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 60:
//#line 392 "arthur.yacc"
{
                                                      ParseNode list = (ParseNode) val_peek(1).obj;
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      list.addChild(s);
                                                      yyval = val_peek(1);
                                                    }
break;
case 61:
//#line 398 "arthur.yacc"
{
                                                      ParseNode s = (ParseNode) val_peek(0).obj;
                                                      ParseNode list = new ParseNode("stmt_list");
                                                      list.addChild(s);
                                                      yyval = new ParserVal(list);
                                                    }
break;
case 62:
//#line 407 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 63:
//#line 408 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 64:
//#line 409 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 65:
//#line 410 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 66:
//#line 411 "arthur.yacc"
{ yyval = new ParserVal(new ParseNode("")); }
break;
case 67:
//#line 415 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 68:
//#line 416 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 69:
//#line 417 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 70:
//#line 421 "arthur.yacc"
{ ParseNode bool = new ParseNode("true"); yyval = new ParserVal(bool); }
break;
case 71:
//#line 422 "arthur.yacc"
{ ParseNode bool = new ParseNode("false"); yyval = new ParserVal(bool); }
break;
case 72:
//#line 423 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 73:
//#line 424 "arthur.yacc"
{
                                                      ParseNode and = new ParseNode("and");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      and.addChild(b1); b1.setParent(and);
                                                      and.addChild(b2); b2.setParent(and);
                                                      yyval = new ParserVal(and);
                                                    }
break;
case 74:
//#line 431 "arthur.yacc"
{
                                                      ParseNode or = new ParseNode("or");
                                                      ParseNode b1 = (ParseNode) val_peek(2).obj; ParseNode b2 = (ParseNode) val_peek(0).obj;
                                                      or.addChild(b1); b1.setParent(or);
                                                      or.addChild(b2); b2.setParent(or);
                                                      yyval = new ParserVal(or);
                                                    }
break;
case 75:
//#line 438 "arthur.yacc"
{
                                                      ParseNode b = (ParseNode) val_peek(0).obj;
                                                      ParseNode not = new ParseNode("not");
                                                      not.addChild(b); not.setParent(b.parent); b.setParent(not);
                                                      yyval = new ParserVal(not);
                                                    }
break;
case 76:
//#line 447 "arthur.yacc"
{
                                                      ParseNode number = new ParseNode("number");
                                                      Num n = (Num) val_peek(0).obj;
                                                      ParseNode val = new ParseNode(n.val.toString());
                                                      number.addChild(val);
                                                      yyval = new ParserVal(number); }
break;
case 77:
//#line 453 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 78:
//#line 454 "arthur.yacc"
{
                                                      ParseNode lt = new ParseNode("less than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lt.addChild(ne1); ne1.setParent(lt);
                                                      lt.addChild(ne2); ne2.setParent(lt);
                                                      yyval = new ParserVal(lt);
                                                    }
break;
case 79:
//#line 461 "arthur.yacc"
{
                                                      ParseNode lte = new ParseNode("less than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      lte.addChild(ne1); ne1.setParent(lte);
                                                      lte.addChild(ne2); ne2.setParent(lte);
                                                      yyval = new ParserVal(lte);
                                                    }
break;
case 80:
//#line 468 "arthur.yacc"
{
                                                      ParseNode gt = new ParseNode("greater than");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gt.addChild(ne1); ne1.setParent(gt);
                                                      gt.addChild(ne2); ne2.setParent(gt);
                                                      yyval = new ParserVal(gt);
                                                    }
break;
case 81:
//#line 475 "arthur.yacc"
{
                                                      ParseNode gte = new ParseNode("greater than or equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      gte.addChild(ne1); ne1.setParent(gte);
                                                      gte.addChild(ne2); ne2.setParent(gte);
                                                      yyval = new ParserVal(gte);
                                                    }
break;
case 82:
//#line 482 "arthur.yacc"
{
                                                      ParseNode eq2x = new ParseNode("is equal to");
                                                      ParseNode ne1 = (ParseNode) val_peek(2).obj; ParseNode ne2 = (ParseNode) val_peek(0).obj;
                                                      eq2x.addChild(ne1); ne1.setParent(eq2x);
                                                      eq2x.addChild(ne2); ne2.setParent(eq2x);
                                                      yyval = new ParserVal(eq2x);
                                                    }
break;
case 83:
//#line 492 "arthur.yacc"
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
case 84:
//#line 502 "arthur.yacc"
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
case 85:
//#line 513 "arthur.yacc"
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
case 86:
//#line 524 "arthur.yacc"
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
case 87:
//#line 535 "arthur.yacc"
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
case 88:
//#line 546 "arthur.yacc"
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
case 89:
//#line 564 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 90:
//#line 568 "arthur.yacc"
{
                                                        ParseNode plus = new ParseNode("+");
                                                        plus.addChild((ParseNode)val_peek(2).obj);
                                                        plus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(plus);
                                                    }
break;
case 91:
//#line 574 "arthur.yacc"
{
                                                        ParseNode minus = new ParseNode("-");
                                                        minus.addChild((ParseNode)val_peek(2).obj);
                                                        minus.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(minus);
                                                    }
break;
case 92:
//#line 580 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 93:
//#line 584 "arthur.yacc"
{
                                                        ParseNode times = new ParseNode("*");
                                                        times.addChild((ParseNode)val_peek(2).obj);
                                                        times.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(times);
                                                    }
break;
case 94:
//#line 590 "arthur.yacc"
{
                                                        ParseNode div = new ParseNode("/");
                                                        div.addChild((ParseNode)val_peek(2).obj);
                                                        div.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(div);
                                                    }
break;
case 95:
//#line 596 "arthur.yacc"
{
                                                        ParseNode mod = new ParseNode("%");
                                                        mod.addChild((ParseNode)val_peek(2).obj);
                                                        mod.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(mod);
                                                    }
break;
case 96:
//#line 602 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 97:
//#line 606 "arthur.yacc"
{
                                                        ParseNode exp = new ParseNode("^");
                                                        exp.addChild((ParseNode)val_peek(2).obj);
                                                        exp.addChild((ParseNode)val_peek(0).obj);
                                                        yyval = new ParserVal(exp);
                                                    }
break;
case 98:
//#line 612 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 99:
//#line 615 "arthur.yacc"
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
case 100:
//#line 624 "arthur.yacc"
{
                                                        Num n = (Num) val_peek(0).obj;
                                                        ParseNode number = new ParseNode("number");
                                                        number.addChild(new ParseNode(n.val.toString(), number));
                                                        yyval = new ParserVal(number);
                                                    }
break;
case 101:
//#line 630 "arthur.yacc"
{
                                                        StringLit s = (StringLit) val_peek(0).obj;
                                                        ParseNode string = new ParseNode("String");
                                                        string.addChild(new ParseNode(s.val, string));
                                                        yyval = new ParserVal(string);
                                                    }
break;
case 102:
//#line 636 "arthur.yacc"
{ ParseNode t = new ParseNode("true"); yyval = new ParserVal(t); }
break;
case 103:
//#line 637 "arthur.yacc"
{ ParseNode f = new ParseNode("false"); yyval = new ParserVal(f); }
break;
case 104:
//#line 638 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 105:
//#line 639 "arthur.yacc"
{ yyval = val_peek(1); }
break;
case 106:
//#line 643 "arthur.yacc"
{
                                                        Var v = (Var) val_peek(0).obj;
                                                        ParseNode var = new ParseNode("variable");
                                                        var.addChild(new ParseNode(v.typeName(), var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        yyval = new ParserVal(var);
                                                    }
break;
case 107:
//#line 653 "arthur.yacc"
{
                                                        Var v = (Var) val_peek(0).obj;
                                                        ParseNode var = new ParseNode("parameter");
                                                        var.addChild(new ParseNode(v.typeName(), var));
                                                        var.addChild(new ParseNode(v.id, var));
                                                        yyval = new ParserVal(var);
                                                    }
break;
case 108:
//#line 663 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 109:
//#line 664 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 110:
//#line 665 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 111:
//#line 666 "arthur.yacc"
{ yyval = val_peek(0); }
break;
case 112:
//#line 667 "arthur.yacc"
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
//#line 1694 "Parser.java"
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
