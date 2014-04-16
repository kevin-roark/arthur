/* The following code was generated by JFlex 1.5.0-SNAPSHOT */


/** USER CODE SECTION */

package arthur.frontend;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.5.0-SNAPSHOT
 * from the specification file <tt>arthur.flex</tt>
 */
public class Lexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int VOIDNESS = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\2\1\4\1\5\1\1\22\0\1\3\1\0\1\12"+
    "\2\0\1\55\1\0\1\13\1\44\1\47\1\11\1\14\1\17\1\45"+
    "\1\15\1\10\12\7\1\0\1\46\1\16\1\54\1\20\2\0\10\6"+
    "\1\41\11\6\1\35\2\6\1\37\4\6\3\0\1\56\1\6\1\0"+
    "\1\42\1\30\1\24\1\36\1\40\1\52\1\34\1\6\1\33\2\6"+
    "\1\26\1\23\1\21\1\25\2\6\1\27\1\31\1\32\1\22\1\43"+
    "\1\53\3\6\1\50\1\0\1\51\7\0\1\4\u1fa2\0\1\4\1\4"+
    "\udfd6\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\2\2\1\3\1\4\1\5\1\6\2\1"+
    "\1\7\1\10\1\11\1\12\1\13\17\3\1\14\1\15"+
    "\1\16\1\17\1\20\1\21\1\3\1\22\1\23\1\24"+
    "\4\0\1\25\1\0\1\25\1\4\1\0\1\26\1\27"+
    "\3\3\1\30\4\3\1\31\1\3\1\32\5\3\1\33"+
    "\1\3\1\34\2\0\1\35\1\36\10\3\1\37\1\3"+
    "\1\40\2\3\4\0\3\3\1\41\2\3\1\42\3\3"+
    "\1\0\1\43\2\3\1\0\1\44\4\0\1\45\1\46"+
    "\11\0\1\47\3\0\1\50";

  private static int [] zzUnpackAction() {
    int [] result = new int[128];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\57\0\57\0\136\0\57\0\215\0\274\0\353"+
    "\0\57\0\u011a\0\u0149\0\274\0\u0178\0\u01a7\0\57\0\u01d6"+
    "\0\u0205\0\u0234\0\u0263\0\u0292\0\u02c1\0\u02f0\0\u031f\0\u034e"+
    "\0\u037d\0\u03ac\0\u03db\0\u040a\0\u0439\0\u0468\0\u0497\0\57"+
    "\0\u04c6\0\57\0\57\0\57\0\57\0\u04f5\0\u0524\0\57"+
    "\0\57\0\u0178\0\u0553\0\u0582\0\u011a\0\u011a\0\u0149\0\u0149"+
    "\0\u0178\0\u05b1\0\57\0\57\0\u05e0\0\u060f\0\u063e\0\215"+
    "\0\u066d\0\u069c\0\u06cb\0\u06fa\0\215\0\u0729\0\215\0\u0758"+
    "\0\u0787\0\u07b6\0\u07e5\0\u0814\0\57\0\u0843\0\57\0\u0872"+
    "\0\u08a1\0\u08d0\0\215\0\u08ff\0\u092e\0\u095d\0\u098c\0\u09bb"+
    "\0\u09ea\0\u0a19\0\u0a48\0\215\0\u0a77\0\215\0\u0aa6\0\u0ad5"+
    "\0\u0b04\0\u0b33\0\u0b62\0\u0b91\0\u0bc0\0\u0bef\0\u0c1e\0\215"+
    "\0\u0c4d\0\u0c7c\0\215\0\u0cab\0\u0cda\0\u0d09\0\u0d38\0\u0d67"+
    "\0\u0d96\0\u0dc5\0\u0df4\0\215\0\u0e23\0\u0e52\0\u0e81\0\u0eb0"+
    "\0\57\0\215\0\u0edf\0\u0f0e\0\u0f3d\0\u0f6c\0\u0f9b\0\u0fca"+
    "\0\u0ff9\0\u1028\0\u1057\0\57\0\u1086\0\u10b5\0\u10e4\0\57";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[128];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\2\5\1\3\1\5\1\6\1\7\1\10"+
    "\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20"+
    "\1\21\2\6\1\22\1\23\1\6\1\24\1\25\1\26"+
    "\1\27\1\30\1\6\1\31\1\32\1\33\1\34\1\35"+
    "\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45"+
    "\1\46\1\6\1\47\1\50\1\51\61\0\1\5\62\0"+
    "\2\6\11\0\23\6\6\0\2\6\12\0\1\7\5\0"+
    "\1\52\51\0\1\53\1\54\45\0\1\55\2\0\1\55"+
    "\2\0\4\55\1\56\44\55\1\57\2\0\1\57\2\0"+
    "\5\57\1\60\43\57\7\0\1\61\65\0\1\62\35\0"+
    "\1\63\56\0\1\64\10\0\2\6\11\0\1\6\1\65"+
    "\2\6\1\66\16\6\6\0\2\6\11\0\2\6\11\0"+
    "\4\6\1\67\16\6\6\0\2\6\11\0\2\6\11\0"+
    "\6\6\1\70\14\6\6\0\2\6\11\0\2\6\11\0"+
    "\17\6\1\71\3\6\6\0\2\6\11\0\2\6\11\0"+
    "\4\6\1\72\16\6\6\0\2\6\11\0\2\6\11\0"+
    "\11\6\1\73\11\6\6\0\2\6\11\0\2\6\11\0"+
    "\6\6\1\74\14\6\6\0\2\6\11\0\2\6\11\0"+
    "\23\6\6\0\1\75\1\6\11\0\2\6\11\0\4\6"+
    "\1\76\16\6\6\0\2\6\11\0\2\6\11\0\23\6"+
    "\6\0\1\6\1\77\11\0\2\6\11\0\12\6\1\100"+
    "\10\6\6\0\2\6\11\0\2\6\11\0\5\6\1\101"+
    "\15\6\6\0\2\6\11\0\2\6\11\0\2\6\1\102"+
    "\20\6\6\0\2\6\11\0\2\6\11\0\1\103\22\6"+
    "\6\0\2\6\11\0\2\6\11\0\4\6\1\104\16\6"+
    "\6\0\2\6\12\0\1\7\5\0\1\52\2\0\1\105"+
    "\44\0\2\6\11\0\21\6\1\106\1\6\6\0\2\6"+
    "\57\0\1\107\2\0\1\53\1\4\1\5\1\53\2\0"+
    "\51\53\11\54\1\110\45\54\1\0\3\62\1\0\1\62"+
    "\1\0\1\111\55\0\2\6\11\0\2\6\1\112\20\6"+
    "\6\0\2\6\11\0\2\6\11\0\11\6\1\113\11\6"+
    "\6\0\2\6\11\0\2\6\11\0\5\6\1\114\15\6"+
    "\6\0\2\6\11\0\2\6\11\0\11\6\1\115\11\6"+
    "\6\0\2\6\11\0\2\6\11\0\4\6\1\116\16\6"+
    "\6\0\2\6\11\0\2\6\11\0\6\6\1\117\14\6"+
    "\6\0\2\6\11\0\2\6\11\0\1\6\1\120\21\6"+
    "\6\0\2\6\11\0\2\6\11\0\1\6\1\121\21\6"+
    "\6\0\2\6\11\0\2\6\11\0\15\6\1\122\5\6"+
    "\6\0\2\6\11\0\2\6\11\0\10\6\1\123\12\6"+
    "\6\0\1\124\1\6\11\0\2\6\11\0\21\6\1\125"+
    "\1\6\6\0\2\6\11\0\2\6\11\0\15\6\1\126"+
    "\5\6\6\0\2\6\11\0\2\6\11\0\12\6\1\127"+
    "\10\6\6\0\2\6\11\0\2\6\11\0\5\6\1\130"+
    "\15\6\6\0\2\6\3\0\10\54\1\5\1\110\45\54"+
    "\1\0\3\131\1\0\1\131\1\0\1\132\7\0\1\133"+
    "\40\0\3\134\1\0\1\134\2\6\11\0\23\6\6\0"+
    "\2\6\11\0\2\6\11\0\4\6\1\135\16\6\6\0"+
    "\2\6\11\0\2\6\11\0\1\6\1\136\21\6\6\0"+
    "\2\6\11\0\2\6\11\0\5\6\1\112\15\6\6\0"+
    "\2\6\11\0\2\6\11\0\12\6\1\137\10\6\6\0"+
    "\2\6\11\0\2\6\11\0\17\6\1\140\3\6\6\0"+
    "\2\6\11\0\2\6\11\0\1\141\22\6\6\0\2\6"+
    "\11\0\2\6\11\0\17\6\1\142\3\6\6\0\2\6"+
    "\11\0\2\6\11\0\17\6\1\143\3\6\6\0\2\6"+
    "\11\0\2\6\11\0\13\6\1\144\7\6\6\0\2\6"+
    "\11\0\2\6\11\0\15\6\1\145\5\6\6\0\2\6"+
    "\11\0\2\6\11\0\10\6\1\146\12\6\6\0\2\6"+
    "\4\0\3\131\1\0\1\131\11\0\1\133\40\0\3\131"+
    "\1\0\1\131\1\0\1\131\7\0\1\133\40\0\3\133"+
    "\1\0\1\133\1\0\1\147\50\0\3\134\1\0\1\134"+
    "\1\150\12\0\23\150\6\0\2\150\11\0\2\6\11\0"+
    "\6\6\1\112\14\6\6\0\2\6\11\0\2\6\11\0"+
    "\6\6\1\151\14\6\6\0\2\6\11\0\2\6\11\0"+
    "\1\152\22\6\6\0\2\6\11\0\2\6\11\0\15\6"+
    "\1\112\5\6\6\0\2\6\11\0\2\6\11\0\4\6"+
    "\1\112\16\6\6\0\2\6\11\0\2\6\11\0\17\6"+
    "\1\112\3\6\6\0\2\6\4\0\3\153\1\0\1\153"+
    "\2\6\11\0\23\6\6\0\2\6\11\0\2\6\11\0"+
    "\17\6\1\154\3\6\6\0\2\6\4\0\3\155\1\0"+
    "\1\155\1\0\1\156\7\0\1\157\40\0\3\160\1\0"+
    "\1\160\2\150\11\0\23\150\1\161\5\0\2\150\11\0"+
    "\2\6\11\0\1\162\22\6\6\0\2\6\11\0\2\6"+
    "\11\0\13\6\1\112\7\6\6\0\2\6\4\0\3\153"+
    "\1\0\1\153\1\163\12\0\23\163\6\0\2\163\4\0"+
    "\3\155\1\0\1\155\11\0\1\157\40\0\3\155\1\0"+
    "\1\155\1\0\1\155\7\0\1\157\40\0\3\157\1\0"+
    "\1\157\1\0\1\164\50\0\3\160\1\0\1\160\36\0"+
    "\1\161\13\0\3\160\1\0\1\160\2\163\11\0\23\163"+
    "\1\161\5\0\2\163\4\0\3\165\1\0\1\165\1\0"+
    "\1\166\7\0\1\167\1\170\37\0\3\165\1\0\1\165"+
    "\11\0\1\167\1\170\37\0\3\165\1\0\1\165\1\0"+
    "\1\165\7\0\1\167\1\170\37\0\3\167\1\0\1\167"+
    "\1\0\1\171\4\0\1\172\1\173\27\0\1\172\31\0"+
    "\1\174\37\0\3\175\1\0\1\175\1\0\1\171\5\0"+
    "\1\173\2\0\1\176\45\0\1\171\5\0\1\173\50\0"+
    "\1\177\50\0\3\175\1\0\1\175\12\0\1\176\56\0"+
    "\1\200\37\0\3\175\1\0\1\175\1\0\1\177\10\0"+
    "\1\176\36\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4371];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\10\1\11\1\1\1\11\3\1\1\11\5\1"+
    "\1\11\20\1\1\11\1\1\4\11\2\1\2\11\4\0"+
    "\1\1\1\0\2\1\1\0\2\11\20\1\1\11\1\1"+
    "\1\11\2\0\17\1\4\0\12\1\1\0\3\1\1\0"+
    "\1\1\4\0\1\11\1\1\11\0\1\11\3\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[128];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  StringBuffer string = new StringBuffer();
  SymbolTable table = SymbolTable.getGlobalTable();

  boolean startingFunction = false;


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader
             (in, java.nio.charset.Charset.forName("UTF-8")));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 142) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

    // numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Token yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 1: 
          { throw new Error("Illegal character: " + yytext());
          }
        case 41: break;
        case 2: 
          { /* do nothing */
          }
        case 42: break;
        case 3: 
          { return new Identifier(yytext(), yyline);
          }
        case 43: break;
        case 4: 
          { Double val = Double.parseDouble(yytext()); return new Num(val, yyline);
          }
        case 44: break;
        case 5: 
          { return new Token(Tokens.DIV, yyline);
          }
        case 45: break;
        case 6: 
          { return new Token(Tokens.TIMES, yyline);
          }
        case 46: break;
        case 7: 
          { return new Token(Tokens.PLUS, yyline);
          }
        case 47: break;
        case 8: 
          { return new Token(Tokens.DOT, yyline);
          }
        case 48: break;
        case 9: 
          { return new Token(Tokens.LT, yyline);
          }
        case 49: break;
        case 10: 
          { return new Token(Tokens.COMMA, yyline);
          }
        case 50: break;
        case 11: 
          { return new Token(Tokens.GT, yyline);
          }
        case 51: break;
        case 12: 
          { return new Token(Tokens.LPAREN, yyline);
          }
        case 52: break;
        case 13: 
          { return new Token(Tokens.MINUS, yyline);
          }
        case 53: break;
        case 14: 
          { return new Token(Tokens.SEMI, yyline);
          }
        case 54: break;
        case 15: 
          { return new Token(Tokens.RPAREN, yyline);
          }
        case 55: break;
        case 16: 
          { if(!startingFunction) {
                                table = new SymbolTable(table, "block");
                              } else {
                                startingFunction = false;
                              }
                              return new Token(Tokens.LCURLY, yyline);
          }
        case 56: break;
        case 17: 
          { table = table.getPrev(); return new Token(Tokens.RCURLY, yyline);
          }
        case 57: break;
        case 18: 
          { return new Token(Tokens.EQ, yyline);
          }
        case 58: break;
        case 19: 
          { return new Token(Tokens.MOD, yyline);
          }
        case 59: break;
        case 20: 
          { return new Token(Tokens.EXP, yyline);
          }
        case 60: break;
        case 21: 
          { return new StringLit(yytext(), yyline);
          }
        case 61: break;
        case 22: 
          { return new Token(Tokens.LTE, yyline);
          }
        case 62: break;
        case 23: 
          { return new Token(Tokens.GTE, yyline);
          }
        case 63: break;
        case 24: 
          { return new Token(Tokens.OR, yyline);
          }
        case 64: break;
        case 25: 
          { return new Token(Tokens.IF, yyline);
          }
        case 65: break;
        case 26: 
          { return new Token(Tokens.DW, yyline);
          }
        case 66: break;
        case 27: 
          { return new Token(Tokens.ARROW, yyline);
          }
        case 67: break;
        case 28: 
          { return new Token(Tokens.EQX2, yyline);
          }
        case 68: break;
        case 29: 
          { return new Type(yytext(), yyline);
          }
        case 69: break;
        case 30: 
          { return new Token(Tokens.NOT, yyline);
          }
        case 70: break;
        case 31: 
          { return new Token(Tokens.ELF, yyline);
          }
        case 71: break;
        case 32: 
          { return new Token(Tokens.AND, yyline);
          }
        case 72: break;
        case 33: 
          { return new Token(Tokens.TRUE, yyline);
          }
        case 73: break;
        case 34: 
          { return new Token(Tokens.ELSE, yyline);
          }
        case 74: break;
        case 35: 
          { String[] arr = yytext().split("\\s+");
                              String type = arr[0];
                              String name = arr[1];
                              Var var = new Var(name, type, yyline);
                              table.getMap().put(name, var);
                              return var;
          }
        case 75: break;
        case 36: 
          { return new Token(Tokens.FALSE, yyline);
          }
        case 76: break;
        case 37: 
          { String text = yytext().replace("(", "");
                              String[] arr = text.split("\\s+");
                              String returnType = arr[0];
                              String name = arr[1];
                              Function fun = new Function(name, returnType, yyline);
                              table.getMap().put(name, fun);
                              return fun;
          }
        case 77: break;
        case 38: 
          { return new Token(Tokens.RETURN, yyline);
          }
        case 78: break;
        case 39: 
          { String rgb = yytext().replace("<<", "").replace(">>", "");
                              String[] arr = rgb.split(",");
                              int r = Integer.parseInt(arr[0].trim());
                              int g = Integer.parseInt(arr[1].trim());
                              int b = Integer.parseInt(arr[2].trim());
                              return new Color(r, g, b);
          }
        case 79: break;
        case 40: 
          { String rgb = yytext().replace("<<", "").replace(">>", "");
                              String[] arr = rgb.split(",");
                              int r = Integer.parseInt(arr[0].trim());
                              int g = Integer.parseInt(arr[1].trim());
                              int b = Integer.parseInt(arr[2].trim());
                              double a = Double.parseDouble(arr[3].trim());
                              return new Color(r, g, b, a);
          }
        case 80: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
              {  return new Token(Tokens.EOF);
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
