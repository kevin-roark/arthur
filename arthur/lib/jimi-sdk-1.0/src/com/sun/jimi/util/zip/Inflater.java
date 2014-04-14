package com.sun.jimi.util.zip;


































// -*-java-*-

import java.io.*;


// The private class "huft" stores all the data for decoding tables.
class huft extends Object
{
  public int[] exop;/* number of extra bits or operation */
  public int[] bits;/* number of bits in this code or subcode */
  public int[] base;/* literal, length base, or distance base */
  public huft[] next;/* pointer to next level of table */

  public huft(int size)
  {
    exop = new int[size];
    bits = new int[size];
    base = new int[size];
    next = new huft[size];
  }
}

// The private class "parameterhack" is used for functions that need
// to return more than one value, since java can't take parameters by
// reference
class parameterhack extends Object
{
  public int m;
  huft t;
}

/**
 * A class to uncompress data stored in the DEFLATE , as described in
 * RFC 1951.
 * <p>
 * DEFLATE is the compression method used in the popular <b>gzip</b>
 * and <b>(pk)zip</b> utilities.  This class will not read zip or gzip files
 * directly, but it provides most of the necessary functions.
 * <p>
 * This class is a direct translation of the inflate portion of the
 * freely available <a href="http://quest.jpl.nasa.gov/zlib/zlib.html">zlib</a>
 * general purpose compression library, bearing the copyright:
 * <pre>
 * Copyright (C) 1995-1996 Jean-loup Gailly and Mark Adler
 *
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 *
 * Jean-loup Gailly        Mark Adler
 * gzip@prep.ai.mit.edu    madler@alumni.caltech.edu
 * </pre>
 * Please do not contact either of the above authors regarding the java
 * version of this library.
 *
 * @version 1.0.4
 * @author Eric Frias (efrias@vt.edu)
 */
public class Inflater extends Object{

  /**
   * the array which contains the compressed data
   */
  public byte[] array_in;
  /**
   * the index of the first byte of compressed data in <TT>array_in</TT>
   */
  public int next_in;
  /**
   * the number of bytes available in <TT>array_in</TT>
   */
  public int avail_in;
  /**
   * the total number of bytes that have been read so far
   */
  public int total_in;

  /**
   * the array which will recieve the uncompressed data
   */
  public byte[] array_out;
  /**
   * the index of the first available byte in <TT>array_out</TT>
   */
  public int next_out;
  /**
   * the number of free bytes in <TT>array_out</TT>
   */
  public int avail_out;
  /**
   * the total number of bytes that have been output so far
   */
  public int total_out;

  // Whether or not to wrap the deflate stream with the zlib fields,
  // (header and adler32)
  boolean wrap;

  // The sliding window, and variables for keeping track of what it contains
  private byte[] window;
  private int read;
  private int write;
  private int end;
  
  long bitb;
  int bitk;
  
  public static final int Z_NO_FLUSH = 0;
  /**
   * Specifies that the Inflater should flush as much of the output as
   * possible to the output buffer
   */
  public static final int Z_PARTIAL_FLUSH = 1;
  public static final int Z_SYNC_FLUSH = 2;
  public static final int Z_FULL_FLUSH = 3;
  /**
   * Informs inflate() that this is the last of the data.
   */
  public static final int Z_FINISH = 4;

  /**
   * Indicates that no errors were encountered
   */
  public static final int Z_OK = 0;
  /**
   * Indicates that the end of the stream has been reached, and all
   * uncompressed data has been flushed
   */
  public static final int Z_STREAM_END = 1;
  /**
   * Indicates that a preset dictionary is needed before decompression
   * continues
   */
  public static final int Z_NEED_DICT = 2;
  public static final int Z_ERRNO = -1;
  /**
   * Indicates that the stream was inconsistent (for example, if array_in
   * or array_out were null)
   */
  public static final int Z_STREAM_ERROR  = -2;
  /**
   * Indicates that the input data was corrupted
   */
  public static final int Z_DATA_ERROR  = -3;
  /**
   * Indicates that no progress was made, or there was not enough room in
   * the output buffer when Z_FINISH is used
   */
  public static final int Z_BUF_ERROR = -5;

  private static final int BMAX = 15;
  private static final int N_MAX = 288;
  
  // the state we're in
  private static final int TYPE = 0;/* get type bits (3, including end bit)*/
  private static final int LENS = 1;/* get lengths for stored */
  private static final int STORED = 2;/* processing stored block */
  private static final int TABLE = 3;/* get table lengths */
  private static final int BTREE = 4;/* get bit lengths tree
					for a dynamic block */
  private static final int DTREE = 5;/* get length, distance trees
					for a dynamic block */
  private static final int CODES = 6;/* processing fixed
					or dynamic block */
  private static final int DRY = 7;/* output remaining window bytes */
  private static final int DONE = 8;/* finished last block, done */
  private static final int BAD = 9;/* got a data error--stuck here */

  /* waiting for "i:"=input, "o:"=output, "x:"=nothing */
  private static final int START = 0;/* x: set up for LEN */
  private static final int LEN = 1;/* i: get length/literal/eob next */
  private static final int LENEXT = 2;/* i: getting length extra (have base) */
  private static final int DIST = 3;/* i: get distance next */
  private static final int DISTEXT = 4; /* i: getting distance extra */
  private static final int COPY = 5;/* o: copying bytes in window,
				       waiting for space */
  private static final int LIT = 6;/* o: got literal,
				      waiting for output space */
  private static final int WASH = 7;/* o: got eob, possibly still
				       output waiting */
  private static final int END = 8;/* x: got eob and all data flushed */
  private static final int BADCODE = 9;/* x: got error */


  private static final int METHOD = 0;/* waiting for method byte */
  private static final int FLAG = 1;/* waiting for flag byte */
  private static final int DICT4 = 2;/* four dictionary check bytes to go */
  private static final int DICT3 = 3;/* three dictionary check bytes to go */
  private static final int DICT2 = 4;/* two dictionary check bytes to go */
  private static final int DICT1 = 5;/* one dictionary check byte to go */
  private static final int DICT0 = 6;/* waiting for inflateSetDictionary */
  private static final int BLOCKS = 7;/* decompressing blocks */
  private static final int CHECK4 = 10;/* four check bytes to go */
  private static final int CHECK3 = 11;/* three check bytes to go */
  private static final int CHECK2 = 12;/* two check bytes to go */
  private static final int CHECK1 = 13;/* one check byte to go */
  /* DONE and BAD defined above */

  private static final int Z_DEFLATED = 8;/* the only defined
					     compression method */
  private static final int PRESET_DICT = 0x20;/* bit mask for preset
						 dictionaries */
  

  int state_mode;
  int state_sub_method;
  long state_sub_check_was;
  long state_sub_check_need;
  int state_sub_marker;
  boolean state_nowrap;
  int state_wbits;
  boolean checkfn;
  Adler32 adler;
  
  /* The original copyright notices, in unicode and in ascii below*/
  public static final String Copyright = " inflate 1.0.4 Copyright (c) 1995-1996 Mark Adler ";
  public static final byte copyright[] = {
    0x20, 0x69, 0x6e, 0x66, 0x6c, 0x61, 0x74, 0x65, 0x20, 0x31, 0x2e, 0x30,
    0x2e, 0x34, 0x20, 0x43, 0x6f, 0x70, 0x79, 0x72, 0x69, 0x67, 0x68, 0x74,
    0x20, 0x31, 0x39, 0x39, 0x35, 0x2d, 0x31, 0x39, 0x39, 0x36, 0x20, 0x4d,
    0x61, 0x72, 0x6b, 0x20, 0x41, 0x64, 0x6c, 0x65, 0x72, 0x20, 0x00
  };  
  
  // b & inflate_mask[n] masks the lower n bits
  private static final int inflate_mask[] = {
    0x0000,
    0x0001, 0x0003, 0x0007, 0x000f, 0x001f, 0x003f, 0x007f, 0x00ff,
    0x01ff, 0x03ff, 0x07ff, 0x0fff, 0x1fff, 0x3fff, 0x7fff, 0xffff
  };

  /* Table for deflate from PKZIP's appnote.txt. */
  /* Order of the bit length code lengths */ 
  private static final int  border[] = {
    16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};
  
  /* Copy lengths for literal codes 257..285 */
  private static final int cplens[] = {
    3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31,
    35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258, 0, 0};
  
  /* Extra bits for literal codes 257..285 */
  private static final int cplext[] = { 
    0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2,
    3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0, 192, 192};

  /* Copy offsets for distance codes 0..29 */
  private static final int cpdist[] = {
    1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193,
    257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145,
    8193, 12289, 16385, 24577};

  /* Extra bits for distance codes */
  private static final int cpdext[] = {
    0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6,
    7, 7, 8, 8, 9, 9, 10, 10, 11, 11,
    12, 12, 13, 13};

  
  // state dependent variables -- prefixed by the state they're applicable in
  int stored_left; // the number of bytes left if the file is stored

  int dtree_table; // table lengths
  int dtree_index; // index into blens (or border)
  int[] dtree_blens;
  int dtree_bb; // the bit length tree depth
  huft dtree_tb;
  int dtree_tboff;
  
  // prebuilt fixed tables
  boolean fixed_built;
  int fixed_bl;
  int fixed_bd;
  huft fixed_tl;
  huft fixed_td;
  
  // other variables
  int mode;
  boolean last;
  public String msg;
  
  // inflate_codes_state stuff
  int c_mode;/* current inflate_codes mode */
  /* if LEN or DIST, where in tree */
  huft c_sub_code_tree;/* the base of the tree */
  int c_sub_code_toff;/* the current index into the tree */
  int c_sub_code_need;/* bits needed */
  int c_sub_lit;/* if LIT, literal */
  int c_len;/* the length */
  int c_sub_copy_get;/* bits to get for extra */
  int c_sub_copy_dist;/* distance back to copy from */

  byte c_lbits; /* ltree bits decoded per branch */
  byte c_dbits; /* dtree bits decoder per branch */
  huft c_ltree; /* literal/length/eob tree */
  huft c_dtree; /* distance tree */
  
  /**
   * Constructs an inflater object.
   *
   * @param nowrap Does not wrap the DEFLATE stream with the ZLIB information
   */
  public Inflater(boolean nowrap)
  {
    // Initialize the input and output arrays
    array_in = null;
    avail_in = 0;
    total_in = 0;
    next_in = 0;

    array_out = null;
    avail_out = 0;
    total_out = 0;
    next_out = 0;
    
    // Initialize the sliding window
    window = new byte[32 * 1024];
    end = 32 * 1024;
    read = 0;
    write = 0;
    msg = null;
    state_wbits = 15;

    // If we are expecting a raw DEFLATE stream, there will be no
    // adler32 at the end, so we don't need to waste CPU cycles
    // checking it.
    if(nowrap)
      {
	wrap = false;
	checkfn = false;
	adler = null;
      }
    else
      {
	wrap = true;
	checkfn = true;
	adler = new Adler32();	
      }
    
    // and the precomputed tables that aren't
    fixed_built = false;
  }

  /**
   * Constructs a new Inflater object.  Same as Inflater(false)
   */
  public Inflater()
  {
    this(false);
  }
  
  /**
   * Reset the internal state of the object so that it can be used
   * for another decompression run
   */
  public synchronized void reset()
  {
    // TODO: I really ought to double-check and see if this really works.
    long c = 0;
    total_in = total_out = 0;
    msg = null;
    state_mode = state_nowrap ? BLOCKS : METHOD;
    c = inflate_blocks_reset(c);
  }

  /**
   * Tells the Inflater where to take input from
   *
   * @param buf the array containing the compressed data
   * @param off the index of the first byte in the array
   * @param len the number of bytes of data in the array
   */
  public synchronized void setInput(byte buf[],
                                    int off,
                                    int len)
  {
    array_in = buf;
    next_in = off;
    avail_in = len;
  }

  /**
   * Tells how many bytes are remaining in the input array
   *
   * @returns the number of good bytes in the input array
   */
  public synchronized int getRemaining()
  {
    return avail_in;
  }

  /**
   * Tells whether or not more input is needed
   *
   * @returns true if more input is needed to continue
   */
  public synchronized boolean needsInput()
  {
    if(avail_in == 0 &&
       state_mode != DONE && state_mode != BAD)
      return true;
    return false;
  }

  /**
   * Tells whether or not a preset dictionary is needed (only for
   * ZLIB streams)
   * Currently not implemented.
   *
   * @returns true if a dictionary is needed
   */ 
  public synchronized boolean needsDictionary()
  {
    // TODO: See how bad it would be to add this...
    return false;
  }  
  
  public final long inflate_blocks_reset(long c)
  {
    if(checkfn)
      c = adler.getValue();
    
    if(mode == BTREE || mode == DTREE)
      dtree_blens = null;
    if(mode == CODES)
      {
	c_sub_code_tree = null;
	c_ltree = null;
	c_dtree = null;
      }
    mode = TYPE;
    bitk = 0;
    bitb = 0;
    read = write = 0;
    if(checkfn)
      adler.reset();
    return c;
  }

  /**
   * Inflates the compressed data to the specified output location.
   *
   * @param buf the array in which to store the data
   * @param off the index of the first available output location
   * @param len how many bytes are available there
   */
  public synchronized int inflate(byte[] buf, int off, int len)
    throws DataFormatException
  {
    array_out = buf;
    next_out = off;
    avail_out = len;

    if(avail_in <= 0)
      return 0;
    int retval = inflate_unlocked(Z_PARTIAL_FLUSH);
    if(retval < 0)
      throw new DataFormatException(msg);
    return len - avail_out;
  }

  /**
   * Returns the current Adler32 Checksum of the stream
   */
  public synchronized int getAdler()
  {
    if(adler != null)
      return (int) adler.getValue();
    return 1;
  }
  /**
   * Returns the number of bytes processed by the Inflater object so far
   */
  public synchronized int getTotalIn()
  {
    return total_in;
  }
  /**
   * Returns the number of uncompressed data generated by the Inflater
   * object so far
   */
  public synchronized int getTotalOut()
  {
    return total_out;
  }
  
  /**
   * Returns true if the end of the data stream has been reached
   */
  public synchronized boolean finished()
  {
    return state_mode == DONE;
  }
  
  /**
   * Enables or disables the computation of the Adler32 CRC.
   * Disabling the Adler32 speeds things up, but doesn't provide
   * the data integrity check.
   *
   * @param check if true, enables Adler32 checking
   */
  public synchronized void enableAdler(boolean check)
  {
    checkfn = check;
  }
  
  /*
   * inflates the input buffer to the output buffer
   *
   * @param f flush mode, usually Z_PARTIAL_FLUSH or Z_FINISH
   */
  public final int inflate_unlocked(int f)
  {
    int r;
    int tmp;
    int b;
    
    if(next_in < 0 || f < 0)
      return Z_STREAM_ERROR;
    r = Z_BUF_ERROR;
    while(true)
      switch(state_mode)
	{
	case METHOD:
	  {if(avail_in == 0)return r;r=Z_OK;};
	  state_sub_method = ((int)(0xff & (array_in[next_in++])));total_in++;avail_in--;;
	  if((state_sub_method & 0xf) != Z_DEFLATED)
	    {
	      state_mode = BAD;
	      msg = "unknown compression method";
	      state_sub_marker = 5;
	      break;
	    }
	  if((state_sub_method >> 4) + 8 > state_wbits)
	    {
	      state_mode = BAD;
	      msg = "invalid window size";
	      state_sub_marker = 5;
	      break;
	    }
	  state_mode = FLAG;
	case FLAG:
	  {if(avail_in == 0)return r;r=Z_OK;};
	  b = ((int)(0xff & (array_in[next_in++])));total_in++;avail_in--;;
	  if((((state_sub_method << 8) | b) % 31) != 0)
	    {
	      state_mode = BAD;
	      msg = "incorrect header check";
	      state_sub_marker = 5;
	      break;
	    }
	  if((b & PRESET_DICT) == 0)
	    {
	      state_mode = BLOCKS;
	      break;
	    }
	  state_mode = DICT4;
	case DICT4:
	  {if(avail_in == 0)return r;r=Z_OK;};
	  tmp = ((int)(0xff & (array_in[next_in++])));total_in++;avail_in--;;
	  state_sub_check_need = tmp;
	  state_sub_check_need <<= 8;
	  state_mode = DICT3;
	case DICT3:
	  {if(avail_in == 0)return r;r=Z_OK;};
	  tmp = ((int)(0xff & (array_in[next_in++])));total_in++;avail_in--;;
	  state_sub_check_need |= tmp;
	  state_sub_check_need <<= 8;
	  state_mode = DICT2;
	case DICT2:
	  {if(avail_in == 0)return r;r=Z_OK;};
	  tmp = ((int)(0xff & (array_in[next_in++])));total_in++;avail_in--;;
	  state_sub_check_need |= tmp;
	  state_sub_check_need <<= 8;
	  state_mode = DICT1;
	case DICT1:
	  {if(avail_in == 0)return r;r=Z_OK;};
	  tmp = ((int)(0xff & (array_in[next_in++])));total_in++;avail_in--;;
	  state_sub_check_need |= tmp;
	  state_mode = DICT0;
	  return Z_NEED_DICT;
	case DICT0:
	  state_mode = BAD;
	  msg = "need dictionary";
	  state_sub_marker = 0;
	  return Z_STREAM_ERROR;
	case BLOCKS:
	  r = inflate_blocks(r);
	  if(r == Z_DATA_ERROR)
	    {
	      state_mode = BAD;
	      state_sub_marker = 0;
	      break;
	    }
	  if(r != Z_STREAM_END)
	    return r;
	  r = Z_OK;
	  state_sub_check_was = inflate_blocks_reset(state_sub_check_was);
	  if(state_nowrap)
	    {
	      state_mode = DONE;
	      break;
	    }
	  state_mode = CHECK4;
	case CHECK4:
	  {if(avail_in == 0)return r;r=Z_OK;};
	  tmp = ((int)(0xff & (array_in[next_in++])));total_in++;avail_in--;;
	  state_sub_check_need = tmp;
	  state_sub_check_need <<= 8;
	  state_mode = CHECK3;
	case CHECK3:
	  {if(avail_in == 0)return r;r=Z_OK;};
	  tmp = ((int)(0xff & (array_in[next_in++])));total_in++;avail_in--;;
	  state_sub_check_need |= tmp;
	  state_sub_check_need <<= 8;
	  state_mode = CHECK2;
	case CHECK2:
	  {if(avail_in == 0)return r;r=Z_OK;};
	  tmp = ((int)(0xff & (array_in[next_in++])));total_in++;avail_in--;;
	  state_sub_check_need |= tmp;
	  state_sub_check_need <<= 8;
	  state_mode = CHECK1;
	case CHECK1:
	  {if(avail_in == 0)return r;r=Z_OK;};
	  tmp = ((int)(0xff & (array_in[next_in++])));total_in++;avail_in--;;
	  state_sub_check_need |= tmp;
	  if(checkfn &&
	     state_sub_check_was != state_sub_check_need)
	    {
	      state_mode = BAD;
	      msg = "incorrect data check" + state_sub_check_was + " != " + state_sub_check_need;
	      state_sub_marker = 5;
	      break;
	    }
	  state_mode = DONE;
	case DONE:
	  return Z_STREAM_END;
	case BAD:
	  return Z_DATA_ERROR;
	default:
	  return Z_STREAM_ERROR;
	}
  }

  private final void inflate_codes_new(int bl, int bd, huft tl, huft td)
  {
    c_mode = START;
    c_lbits = (byte) bl;
    c_dbits = (byte) bd;
    c_ltree = tl;
    c_dtree = td;
  }
  private final void inflate_codes_free()
  {
    c_ltree = null;
    c_dtree = null;
  }
  
  /*
   * Given a list of code lengths and a maximum table size, make a set of
   * tables to decode that set of codes.  Return Z_OK on success, Z_BUF_ERROR
   * if the given code set is incomplete (the tables are still built in this
   * case), Z_DATA_ERROR if the input is invalid (all zero length codes or an
   * over-subscribed set of lengths), or Z_MEM_ERROR if not enough memory.
   */
  private int huft_build(int[] b, int n, int s,
			 int[] d, int[] e, parameterhack ph)
  {
    // register ints
    int i;/* counter, current code */
    int j;/* counter */
    int k;/* number of bits in current code */
    int p;/* pointer into c[], b[], or v[] */
    int a;/* counter for codes of length k */
    int[] c = new int[BMAX+1];/* bit length count table */
    int f;/* i repeats in table every f entries */
    int g;/* maximum code length */
    int h;/* table level */
    int l;/* bits per table (returned in m) */
    huft q;/* points to current table */
    huft r;/* table entry for structure assignment */
    huft[] u = new huft[BMAX];/* table stack */
    int[] v = new int[N_MAX];/* values in order of bit length */
    int w;/* bits before this table == (l * h) */
    int[] x = new int[BMAX + 1];/* bit offsets, then code stack */
    int xp;/* pointer into x */
    int y;/* number of dummy codes added */
    int z;/* number of entries in current table */
    int m = ph.m;
    huft t = ph.t;
    
    /* Generate counts for each bit length */
    p = 0;
    /* clear c[]--assume BMAX+1 is 16 */
    c[p++] = 0;c[p++] = 0;c[p++] = 0;c[p++] = 0;
    c[p++] = 0;c[p++] = 0;c[p++] = 0;c[p++] = 0;
    c[p++] = 0;c[p++] = 0;c[p++] = 0;c[p++] = 0;
    c[p++] = 0;c[p++] = 0;c[p++] = 0;c[p++] = 0;

    p=0;i=n;
    do{
      c[b[p++]]++;/* assume all entries <= BMAX */
    } while ((--i) != 0);

    if(c[0] == n)/* null input--all zero length codes */
      {
	ph.t = t = null;
	ph.m = m = 0;
	return Z_OK;
      }

    /* Find minimum and maximum length, bound *m by those */
    l = m;
    for(j=1;j<= BMAX; j++)
      if(c[j] != 0)
	break;
    k = j; // minumum code len

    if(l < j)
      l = j;
    for(i = BMAX;i != 0;i--)
      if(c[i] != 0)
	break;
    g = i; // maximum code len
    if(l > i)
      l = i;
    ph.m = m = l;
    
    
    /* Adjust last length count to fill out codes, if needed */
    for(y = 1 << j;j < i; j++,y <<= 1)
      if((y -= c[j]) < 0)
	return Z_DATA_ERROR;
    if((y -= c[i]) < 0)
      return Z_DATA_ERROR;
    c[i] += y;

    /* Generate starting offsets into the value table for each length */
    x[1] = j = 0;
    p = 1;
    xp = 2;
    while(--i != 0)
      x[xp++] = (j += c[p++]);

    /* Make a table of values in order of bit lengths */
    p=0; i=0;
    do{
      if((j = b[p++]) != 0)
	v[x[j]++] = i;
    } while((++i) < n);

    /* Generate the Huffman codes and for each, make the table entries */
    x[0] = i = 0;/* first Huffman code is zero */
    p = 0;/* grab values in bit order */
    h = -1;/* no tables yet--level -1 */
    w = -l;/* bits decoded == (l * h) */
    u[0] = null;/* just to keep compilers happy */
    q = null;/* ditto */
    z = 0;/* ditto */
    
    /* go through the bit lengths (k already is bits in shortest code) */
    for(;k <= g;k++)
      {
	a = c[k];
	while(a-- != 0)
	  {
	    /* here i is the Huffman code of length k bits for value *p */
	    /* make tables up to required level */
	    while(k > w + l)
	      {
		h++;
		w += l;/* previous table always l bits */

		/* compute minimum size table less than or equal to l bits */
		z = g - w;
		z = z > l? l: z;/* table size upper limit */
		if((f = 1 << (j = k - w)) > a + 1)/* try a k-w bit table */
		  {/* too few codes for k-w bit table */
		    f -= a + 1;/* deduct codes from patterns left */
		    xp = k;
		    if(j < z)
		      while( ++j < z)/* try smaller tables up to z bits */
			{
			  if((f <<= 1) <= c[++xp])
			    break;/* enough codes to use up j bits */
			  f -= c[xp];/* else deduct codes from patterns */
			}
		  }
		z = 1 << j;

		// allocate and link in new table
		q = new huft(z);
		
		if(t == null)
		  ph.t = t = q;
		u[h] = q;

		/* connect to last table, if there is one */
		if(h != 0)
		  {
		    x[h] = i;
		    r = u[h-1];
		    int oldj = j;
		    j = i >> (w - l);
		    r.bits[j] = l;
		    r.exop[j] = oldj;
		    r.next[j] = q;
		  }
	      }


	    /* set up table entry in r */
	    int rbits = (k-w);
	    int rexop;
	    int rbase = 0;

	    // save a copy of *p
	    int starp = v[p];
	    // TODO: double-check:  was if(p >= v + n), but isn't
	    // p == v == 0?
	    if(p >= n) 
	      rexop = (int)(128 + 64); /* out of values--invalid code */
	    else if(starp < s)
	      {
		rexop = (int)(starp < 256 ? 0 : 32 + 64);/* 256 is
							    end-of-block */
		rbase = starp;/* simple code is just the value */
		p++;
	      }
	    else
	      {
		rexop = (int)(e[starp - s] + 16 + 64);/* non-simple--look
							 up in lists */
		rbase = d[starp - s];
		p++;
	      }

	    /* fill code-like entries with r */
	    f = 1 << (k - w);
	    for(j = i >> w; j < z; j += f)
	      {
		q.exop[j] = rexop;
		q.base[j] = rbase;
		q.bits[j] = rbits;
	      }

	    /* backwards increment the k-bit code i */
	    for(j = 1 << (k - 1);(i & j) != 0;j >>= 1)
	      i ^= j;
	    i ^= j;

	    /* backup over finished tables */ 
	    while((i & ((1 << w) - 1)) != x[h])
	      {
		h--; /* don't need to update q */
		w -= l;
	      }
	  }
      }

    return (y != 0 && g != 1) ? Z_BUF_ERROR : Z_OK;
  }

  private final int inflate_trees_bits(int[] c, parameterhack ph)
  {
    int  r;

    r = huft_build(c, 19, 19, null, null, ph);
    if(r == Z_DATA_ERROR)
      msg = "oversubscribed dynamic bit lengths tree";
    else if(r == Z_BUF_ERROR)
      {
	ph.t = null;
	msg = "incomplete dynamic huffman bit lengths tree";
	r = Z_DATA_ERROR;
      }
    return r;
  }

  private final int inflate_trees_dynamic(int nl, int nd,
					  int[] c, parameterhack phl,
					  parameterhack phd)
  {
    int r;

    /* build literal/length tree */
    if(( r = huft_build(c, nl, 257, cplens, cplext, phl)) != Z_OK)
      {
	if(r == Z_DATA_ERROR)
	  msg = "oversubscribed literal/length tree";
	else if(r == Z_BUF_ERROR)
	  {
	    phl.t = null;
	    msg = "incomplete literal/length tree";
	    r = Z_DATA_ERROR;
	  }
	return r;
      }

    int[] big_hack = new int[nd];
    System.arraycopy(c, nl, big_hack, 0, nd);
    /* build distance tree */
    if((r = huft_build(big_hack, nd, 0, cpdist, cpdext, phd)) != Z_OK)
      {
	if(r == Z_DATA_ERROR)
	  msg = "oversubscribed literal/length tree";
	else if(r == Z_BUF_ERROR)
	  {
	    phd.t = null;
	    msg = "incomplete literal/length tree";
	    r = Z_DATA_ERROR;
	  }
	return r;
      }

    return Z_OK;
  }
  
  private final void inflate_trees_fixed()
  {
    if(!fixed_built)
      {
	int k;
	int[] c = new int[288];
	parameterhack ph = new parameterhack();
	
	for(k = 0; k < 144; k++)
	  c[k] = 8;
	for(;k< 256;k++)
	  c[k] = 9;
	for(;k< 280; k++)
	  c[k] = 7;
	for(;k<288;k++)
	  c[k] = 8;
	
	ph.t = null;
	ph.m = 7;
	huft_build(c, 288, 257, cplens, cplext, ph);
	fixed_bl = ph.m;
	fixed_tl = ph.t;
	
	for(k=0;k<30;k++)
	  c[k]=5;
	ph.t = null;
	ph.m = 5;
	huft_build(c, 30, 0, cpdist, cpdext, ph);
	
	fixed_bd = ph.m;
	fixed_td = ph.t;

	fixed_built = true;
      }
  }
  
  private final static void trace(String s)
  {
    System.out.println(s);
  }
  private final String toChar(byte b)
  {
    byte[] bt = new byte[1];
    bt[0] = b;
    return new String(bt, 0);
  }
  protected int inflate_flush(int r)
  {
    int n;
    int p;
    int q;

    /* local copies of source and destination pointers */
    p = next_out;
    q = read;
    
    /* compute number of bytes to copy as far as end of window */
    n = (int) ((q <= write ? write: end) - q);
    if( n > avail_out)
      n = avail_out;
    if(n != 0 &&
       r == Z_BUF_ERROR)
      r = Z_OK;

    /* update counters */
    avail_out -= n;
    total_out += n;

    /* update check information */
    if(checkfn)
      adler.update(window, q, n);
    
    /* copy as far as end of window */
    System.arraycopy(window, q, array_out, p, n);
    p += n;
    q += n;

    /* see if more to copy at beginning of window */
    if(q == end)
      {
	/* wrap pointers */
	q = 0;
	if(write == end)
	  write = 0;

	/* compute bytes to copy */
	n = (int)(write - q);
	if(n > avail_out)
	  n = avail_out;
	if(n != 0 &&
	   r == Z_BUF_ERROR)
	  r = Z_OK;
	

	/* update counters */
	avail_out -= n;
	total_out += n;

	/* update check information */
	if(checkfn)
	  adler.update(window, q, n);
	
	/* copy */
	System.arraycopy(window, q, array_out, p, n);
	p += n;
	q += n;
      }
    /* update pointers */
    next_out = p;
    read = q;

    /* done */    
    return r;
  }

  public final int inflate_codes(int r)
  {
    int j;/* temporary storage */
    huft t;/* temporary pointer */
    int toff;
    int e;/* extra bits or operation */
    long b;/* bit buffer */
    int k;/* bits in bit buffer */
    byte[] a;
    int p;/* input data pointer */
    int n;/* bytes available there */
    byte[] o;
    int q;/* output window write pointer */
    int m;/* bytes to end of window or read pointer */
    int f;/* pointer to copy strings from */

    /* copy input/output information to locals ({{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} macro restores) */
    {{p=next_in;n=avail_in;b=bitb;k=bitk;a=array_in;} {q=write;m=(int)(q<read?read-q-1:end-q);o=window;}};

    /* process input and output based on current state */
    while(true)
      /* waiting for "i:"=input, "o:"=output, "x:"=nothing */
      switch(c_mode)
	{
	case START: /* x: set up for LEN */
	  if(m >= 258 && n >= 10)
	    {
	      {{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}};
	      r = inflate_fast(c_lbits, c_dbits, c_ltree, c_dtree);
	      {{p=next_in;n=avail_in;b=bitb;k=bitk;a=array_in;} {q=write;m=(int)(q<read?read-q-1:end-q);o=window;}};
	      if(r != Z_OK)
		{
		  c_mode = r == Z_STREAM_END ? WASH : BADCODE;
		  break;
		}
	    }
	  c_sub_code_need = c_lbits;
	  c_sub_code_tree = c_ltree;
	  c_sub_code_toff = 0;
	  c_mode = LEN;
	case LEN:/* i: get length/literal/eob next */
	  j = c_sub_code_need;
	  {while(k<(j)){{if(n != 0)r=Z_OK;else {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}};b |= (a[p++] & 0xff) << k;n--;k+=8;}};
	  t = c_sub_code_tree;
	  toff = c_sub_code_toff + ((int)b & inflate_mask[j]);

	  {b>>=(t.bits[toff]);k-=(t.bits[toff]);};
	  e = (int) (t.exop[toff]);
	  if(e == 0)/* literal */
	    {
	      c_sub_lit = t.base[toff];
	      c_mode = LIT;
	      break;
	    }
	  if((e & 16) != 0)/* length */
	    {
	      c_sub_copy_get = e & 15;
	      c_len = t.base[toff];
	      c_mode = LENEXT;
	      break;
	    }
	  if((e & 64) == 0)/* next table */
	    {
	      c_sub_code_need = e;
	      c_sub_code_tree = t.next[toff];
	      c_sub_code_toff = 0;
	      break;
	    }
	  if((e & 32) != 0)/* end of block */
	    {
	      c_mode = WASH;
	      break;
	    }
	  c_mode = BADCODE;/* invalid code */
	  msg = "invalid literal/length code";
	  r = Z_DATA_ERROR;
	  {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	case LENEXT:/* i: getting length extra (have base) */
	  j = c_sub_copy_get;
	  {while(k<(j)){{if(n != 0)r=Z_OK;else {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}};b |= (a[p++] & 0xff) << k;n--;k+=8;}};
	  c_len += (int)(b & inflate_mask[j]);
	  {b>>=(j);k-=(j);};
	  c_sub_code_need = c_dbits;
	  c_sub_code_tree = c_dtree;
	  c_sub_code_toff = 0;
	  c_mode = DIST;
	case DIST: /* i: get distance next */
	  j = c_sub_code_need;

	  {while(k<(j)){{if(n != 0)r=Z_OK;else {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}};b |= (a[p++] & 0xff) << k;n--;k+=8;}};
	  t = c_sub_code_tree;
	  toff = c_sub_code_toff + ((int)(b & inflate_mask[j]));
	  {b>>=(t.bits[toff]);k-=(t.bits[toff]);};
	  e = (int)t.exop[toff];
	  if((e & 16) != 0)/* distance */
	    {
	      c_sub_copy_get = e & 15;
	      c_sub_copy_dist = t.base[toff];
	      c_mode = DISTEXT;
	      break;
	    }
	  if((e & 64) == 0)/* next table */
	    {
	      c_sub_code_need = e;
	      c_sub_code_tree = t.next[toff];
	      c_sub_code_toff = 0;
	      break;
	    }
	  c_mode = BADCODE;/* invalid code */
	  msg = "invalid distance code";
	  r = Z_DATA_ERROR;
	  {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	case DISTEXT:/* i: getting distance extra */
	  j = c_sub_copy_get;
	  {while(k<(j)){{if(n != 0)r=Z_OK;else {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}};b |= (a[p++] & 0xff) << k;n--;k+=8;}};
	  c_sub_copy_dist += (int)(b & inflate_mask[j]);
	  {b>>=(j);k-=(j);};
	  c_mode = COPY;
	case COPY:/* o: copying bytes in window, waiting for space */
	  f = q - c_sub_copy_dist;
	  if(q < c_sub_copy_dist)
	    f = end - (c_sub_copy_dist - q);
	  while(c_len != 0)
	    {
	      {if(m==0){{if(q==end && read != 0){q=0;m=(int)(q<read?read-q-1:end-q);}} if(m==0){{{write=q;} r=inflate_flush(r); {q=write;m=(int)(q<read?read-q-1:end-q);o=window;}} {if(q==end && read != 0){q=0;m=(int)(q<read?read-q-1:end-q);}} if(m==0) {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}}}r=Z_OK;};
	      {o[q++]=(byte)(window[f++]);m--;};
	      if(f == end)
		f = 0;
	      c_len--;
	    }
	  c_mode = START;
	  break;
	case LIT:/* o: got literal, waiting for output space */
	  {if(m==0){{if(q==end && read != 0){q=0;m=(int)(q<read?read-q-1:end-q);}} if(m==0){{{write=q;} r=inflate_flush(r); {q=write;m=(int)(q<read?read-q-1:end-q);o=window;}} {if(q==end && read != 0){q=0;m=(int)(q<read?read-q-1:end-q);}} if(m==0) {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}}}r=Z_OK;};
	  {o[q++]=(byte)(c_sub_lit);m--;};
	  c_mode = START;
	  break;
	case WASH: /* o: got eob, possibly more output */
	  {{write=q;} r=inflate_flush(r); {q=write;m=(int)(q<read?read-q-1:end-q);o=window;}};
	  if(read != write)
	    {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	  c_mode = END;
	case END:
	  r = Z_STREAM_END;
	  {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	case BADCODE:
	  r = Z_DATA_ERROR;
	  {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	default:
	  r = Z_STREAM_ERROR;
	  {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	}
      
  }

  /*
   * Called with number of bytes left to write in window at least 258
   * (the maximum string length) and number of input bytes available
   * at least ten.  The ten bytes are six bytes for the longest length/
   * distance pair plus four bytes for overloading the bit buffer.
   */
  public final int inflate_fast(int bl, int bd, huft tl, huft td)
  {
    huft t;/* temporary pointer */
    int toff;
    int e;/* extra bits or operation */
    long b;/* bit buffer */
    int k;/* bits in bit buffer */
    byte[] a;
    int p;/* input data pointer */
    int n;/* bytes available there */
    byte[] o;
    int q;/* output window write pointer */
    int m;/* bytes to end of window or read pointer */
    int ml;/* mask for literal/length tree */
    int md;/* mask for distance tree */
    int c;/* bytes to copy */
    int d; /* distance back to copy from */
    int r;/* copy source pointer */

    /* load input, output, bit values */
    {{p=next_in;n=avail_in;b=bitb;k=bitk;a=array_in;} {q=write;m=(int)(q<read?read-q-1:end-q);o=window;}};

    /* initialize masks */
    ml = inflate_mask[bl];
    md = inflate_mask[bd];

    /* do until not enough input or output space for fast loop */
    do {/* assume called with m >= 258 && n >= 10 */
      /* get literal/length code */
      {while(k<(20)){b |= (((a[p++] & 0xff)) << k);n--;k+=8;}};/* max bits for literal/length code */
      t = tl;
      if((e = t.exop[(toff = ((int)b & ml))]) == 0)
	{
	  {b>>=(t.bits[toff]);k-=(t.bits[toff]);};
	  {o[q++]=(byte)((byte)(t.base[toff]));m--;};
	  continue;
	}
      do{
	{b>>=(t.bits[toff]);k-=(t.bits[toff]);};
	if((e & 16) != 0)
	  {
	    /* get extra bits for length */
	    e &= 15;
	    c = t.base[toff] + ((int)(b & inflate_mask[e]));
	    {b>>=(e);k-=(e);};
	    /* decode distance base of block to copy */
	    {while(k<(15)){b |= (((a[p++] & 0xff)) << k);n--;k+=8;}};/* max bits for distance code */
	    t = td;
	    e = t.exop[(toff = (int)(b & md))];
	    do{
	      {b>>=(t.bits[toff]);k-=(t.bits[toff]);};
	      if((e & 16) != 0)
		{
		  /* get extra bits to add to distance base */
		  e &= 15;
		  {while(k<(e)){b |= (((a[p++] & 0xff)) << k);n--;k+=8;}};/* get extra bits (up to 13) */
		  d = t.base[toff] + ((int)(b & inflate_mask[e]));
		  {b>>=(e);k-=(e);};

		  m -= c;
		  if(q >= d)/* offset before dest */
		    {/*  just copy */
		      r = q - d;
		      o[q++] = window[r++];c--;/* minimum count is three, */
		      o[q++] = window[r++];c--;/*  so unroll loop a little */
		    }
		  else /* else offset after destination */
		    {
		      e = d - q;/* bytes from offset to end */
		      r = end - e; /* pointer to offset */
		      if(c > e)
			{
			  c -= e; /* copy to end of window */
			  do{
			    o[q++] = window[r++];
			  } while((--e) != 0);
			  r = 0; /* copy rest from start of window */
			}
		      
		    }
		  do{ /* copy all or what's left */
		    // TODO: later, evaluate whether or not arraycopy is faster
		    o[q++] = window[r++];
		  } while((--c) != 0);
		  break;
		}
	      else if((e & 64) == 0)
		{
		  t = t.next[toff];
		  e = t.exop[(toff = ((int)(b & inflate_mask[e])))];
		}
	      else
		{
		  msg = "invalid distance code";
		  {n+=(c=k>>3);p-=c;k&=7;};
		  {{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}};
		  return Z_DATA_ERROR;
		}
	    } while(true);
	    break;
	  }
	if((e & 64) == 0)
	  {
	    t = t.next[toff];
	    if((e = t.exop[(toff = ((int)(b & inflate_mask[e])))]) == 0)
	      {
		{b>>=(t.bits[toff]);k-=(t.bits[toff]);};
		o[q++] = (byte)(t.base[toff]);
		m--;
		break;
	      }
	  }
	else if((e & 32) != 0)
	  {
	    {n+=(c=k>>3);p-=c;k&=7;};
	    {{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}};
	    return Z_STREAM_END;
	  }
	else
	  {
	    msg = "invalid literal/length code";
	    {n+=(c=k>>3);p-=c;k&=7;};
	    {{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}};
	    return Z_DATA_ERROR;
	  }
      }while(true);
    }while( m >= 258 && n >= 10);

    {n+=(c=k>>3);p-=c;k&=7;};
    {{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}};
    
    return Z_OK;
  }
  
  public final int inflate_blocks(int r)
  {
    int t;/* temporary storage */
    long b;/* bit buffer */
    int k;/* bits in bit buffer */
    byte[] a;
    int p;/* input data pointer */
    int n;/* bytes available there */
    byte[] o;
    int q;/* output window write pointer */
    int m;/* bytes to end of window or read pointer */

    /* copy input/output information to locals ({{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} macro restores) */
    {{p=next_in;n=avail_in;b=bitb;k=bitk;a=array_in;} {q=write;m=(int)(q<read?read-q-1:end-q);o=window;}};

    /* process input based on current state */
    while(true)
      {
	switch(mode)
	  {
	  case TYPE:
	    {while(k<(3)){{if(n != 0)r=Z_OK;else {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}};b |= (a[p++] & 0xff) << k;n--;k+=8;}};
	    t = (int)(b & 7);
	    last = (t & 1) == 1;
	    switch(t >> 1)
	      {
	      case 0: //stored
		{b>>=(3);k-=(3);};
		// go to byte boundary
		t = k & 7;
		{b>>=(t);k-=(t);};
		mode = LENS;/* get length of stored block */
		break;
	      case 1: // fixed
		inflate_trees_fixed();
		inflate_codes_new(fixed_bl, fixed_bd, fixed_tl, fixed_td);
		{b>>=(3);k-=(3);};
		mode = CODES;
		break;
	      case 2: // dynamic
		{b>>=(3);k-=(3);};
		mode = TABLE;
		break;
	      case 3: // illegal
		{
		  {b>>=(3);k-=(3);};
		  mode = BAD;
		  msg = "Invalid block type";
		  r = Z_DATA_ERROR;
		  {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
		}
	      }
	    break;
	  case LENS:
	    {while(k<(32)){{if(n != 0)r=Z_OK;else {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}};b |= (a[p++] & 0xff) << k;n--;k+=8;}};
	    if((((~b) >> 16) & 0xffff) != (b & 0xffff))
	      {
		mode = BAD;
		msg = "Invalid stored block lengths";
		r = Z_DATA_ERROR;
		{{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	      }
	    stored_left = (int)(b & 0xffff);
	    b = k = 0; /* dump bits manually */
	    mode = stored_left == 0?(last?DRY:TYPE):STORED;
	    break;
	  case STORED:
	    if(n == 0)
	      {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	    {if(m==0){{if(q==end && read != 0){q=0;m=(int)(q<read?read-q-1:end-q);}} if(m==0){{{write=q;} r=inflate_flush(r); {q=write;m=(int)(q<read?read-q-1:end-q);o=window;}} {if(q==end && read != 0){q=0;m=(int)(q<read?read-q-1:end-q);}} if(m==0) {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}}}r=Z_OK;};

	    t = stored_left;
	    if(t>n) t= n;
	    if(t>m) t = m;
	    System.arraycopy(a,p,o,q,t);
	    p += t;
	    n -= t;
	    q += t;
	    m -= t;
	    if((stored_left -= t) != 0)
	      break;
	    mode = last?DRY:TYPE;
	    break;
	  case TABLE:
	    {while(k<(14)){{if(n != 0)r=Z_OK;else {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}};b |= (a[p++] & 0xff) << k;n--;k+=8;}};
	    dtree_table = t = (int)(b & 0x3fff);

	    // next block: #ifndef PKZIP_BUG_WORKAROUND
	    if((t & 0x1f) > 29 || ((t >> 5) & 0x1f) > 29)
	      {
		mode = BAD;
		msg = "too many length or distance symbols";
		r = Z_DATA_ERROR;
		{{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	      }
	    
	    t = 258 + (t & 0x1f) + ((t >> 5) & 0x1f);
	    if(t < 19)
	      t = 19;
	    dtree_blens = new int[t];
	    {b>>=(14);k-=(14);};
	    dtree_index = 0;
	    mode = BTREE;
	  case BTREE:
	    while(dtree_index < (4 + (dtree_table >> 10)))
	      {
		{while(k<(3)){{if(n != 0)r=Z_OK;else {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}};b |= (a[p++] & 0xff) << k;n--;k+=8;}};
		dtree_blens[border[dtree_index++]] = (int)(b & 7);
		{b>>=(3);k-=(3);};
	      }
	    while(dtree_index < 19)
	      dtree_blens[border[dtree_index++]] = 0;
	    dtree_bb = 7;
	    parameterhack ph = new parameterhack();
	    ph.m = dtree_bb;
	    ph.t = null;
	    t = inflate_trees_bits(dtree_blens, ph);
	    dtree_bb = ph.m;
	    dtree_tb = ph.t;
	    if(t != Z_OK)
	      {
		r = t;
		if(r == Z_DATA_ERROR)
		  mode = BAD;
		{{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	      }
	    dtree_index = 0;
	    mode = DTREE;
	  case DTREE:
	    t = dtree_table;
	    while(dtree_index < 258 + (t&0x1f) + ((t >> 5) & 0x1f))
	      {
		huft h;
		int hoff;
		int i, j, c;

		t = dtree_bb;
		{while(k<(t)){{if(n != 0)r=Z_OK;else {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}};b |= (a[p++] & 0xff) << k;n--;k+=8;}};
		h = dtree_tb;
		hoff = (int)(b & inflate_mask[t]);
		t = h.bits[hoff];
		c = h.base[hoff];
		if(c<16)
		  {
		    {b>>=(t);k-=(t);};
		    dtree_blens[dtree_index++] = c;
		  }
		else
		  {
		    i = c == 18? 7 : c - 14;
		    j = c == 18 ? 11 : 3;
		    {while(k<((t + i))){{if(n != 0)r=Z_OK;else {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);}};b |= (a[p++] & 0xff) << k;n--;k+=8;}};
		    {b>>=(t);k-=(t);};
		    j += (int)(b & inflate_mask[i]);
		    {b>>=(i);k-=(i);};
		    i = dtree_index;
		    t = dtree_table;
		    if(i + j > 258 + (t & 0x1f) + ((t >> 5) & 0x1f) ||
		       (c == 16 && i < 1))
		      {
			mode = BAD;
			msg = "invalid bit length repeat";
			r = Z_DATA_ERROR;
			{{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
		      }
		    c = c == 16? dtree_blens[i - 1] : 0;
		    do{
		      dtree_blens[i++] = c;
		    } while(--j != 0);
		    dtree_index = i;
		  }
		// set up for the next loop
		t = dtree_table;
	      }
	    dtree_tb = null;
	    {
	      parameterhack phl = new parameterhack();
	      parameterhack phd = new parameterhack();

	      phl.m = 9;phl.t = null; /* must be <= 9 for lookahead
					 assumptions */
	      phd.m = 6;phl.t = null; /* must be <= 9 for lookahead
					 assumptions */

	      t = dtree_table;
	      
	      t = inflate_trees_dynamic(257 + (t & 0x1f), 1+ ((t >> 5) & 0x1f),
					dtree_blens, phl, phd);
	      if(t != Z_OK)
		{
		  if(t == Z_DATA_ERROR)
		    mode = BAD;
		  r = t;
		  {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
		}
	      inflate_codes_new(phl.m, phd.m, phl.t, phd.t);
	      dtree_blens = null;
	    }
	    mode = CODES;  
	  case CODES:
	    {{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}};
	    if((r = inflate_codes(r)) != Z_STREAM_END)
	      return inflate_flush(r);
	    r = Z_OK;
	    inflate_codes_free();
	    {{p=next_in;n=avail_in;b=bitb;k=bitk;a=array_in;} {q=write;m=(int)(q<read?read-q-1:end-q);o=window;}};

	    if(!last)
	      {
		mode = TYPE;
		break;
	      }
	    if(k > 7) /* return unused byte, if any */
	      {
		k -= 8;
		n++;
		p--; /* can always return one */
	      }
	    mode = DRY;
	  case DRY:
	    {{write=q;} r=inflate_flush(r); {q=write;m=(int)(q<read?read-q-1:end-q);o=window;}};
	    if(read != write)
	      {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	    mode = DONE;
	  case DONE:
	    r = Z_STREAM_END;
	    {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	  case BAD:
	    r = Z_STREAM_ERROR;
	    {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	  default:
	    r = Z_STREAM_ERROR;
	    {{{bitb=b;bitk=k;} {avail_in=n;total_in+= p - next_in;next_in=p;} {write=q;}} return inflate_flush(r);};
	  }
      }
  }
  
  public static void main(String args[])
  {
    try
      {  
	FileInputStream in = new FileInputStream("test.out");
	FileOutputStream out = new FileOutputStream("test.new");
	
	byte[] inbuffer = new byte[1024];
	byte[] outbuffer = new byte[1024];

	int obytes;
	
	Inflater i = new Inflater();
	
	do{
	  if(!i.finished() &&
	     i.getRemaining() == 0)
	    {
	      int nbytes = in.read(inbuffer);
	      if(nbytes < 0)
		throw new IOException("input file truncated");
		
	      i.setInput(inbuffer, 0, nbytes);
	    }
	  
	  obytes = i.inflate(outbuffer, 0, outbuffer.length);

	  if(obytes != 0)
	    out.write(outbuffer,0,obytes);
	} while(!i.finished() && obytes != 0);
      }
    catch(DataFormatException e)
      {
	System.err.println("Caught a DataFormatException, reason: " + e.getMessage());
      }
    catch(IOException e)
      {
	System.err.println("Caught a IOException, reason: " + e.getMessage());
      }
    finally
      {
      }
  }
}
