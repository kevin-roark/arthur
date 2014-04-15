package com.sun.jimi.core.encoder.sunraster;

import com.sun.jimi.core.EncoderOptions;
import com.sun.jimi.core.InvalidOptionException;

/**
 * Class for setting SunRaster-encoding options.
 * @author  Luke Gorrie
 * @version $Revision: 1.1.1.1 $ $Date: 1998/12/01 12:21:57 $
 **/
public class SunRasterEncoderOptions extends EncoderOptions
{

	public static final String
	  COMPRESSION_NONE = SunRasterEncoder.COMPRESSION_NONE,
	  COMPRESSION_RLE  = SunRasterEncoder.COMPRESSION_RLE;

	/**
	 * Get the Class of the encoder implementation.
	 * @return The Class object representing the encoder.
	 **/
	public Class getEncoderClass()
	{
		return SunRasterEncoder.class;
	}

	/**
	 * Sets the compression type to encode with.
	 * @see #COMPRESSION_NONE
	 * @see #COMPRESSION_RLE
	 * @param compressionType The type of compression to use.  Must be one of the COMPRESSION_
	 * constants.
	 **/
	public void setCompressionType(String compressionType) throws InvalidOptionException
	{
		// no compression
		if (compressionType.equals(COMPRESSION_NONE))
			setProperty(SunRasterEncoder.OPTION_COMPRESSION, compressionType);
		// RLE compression
		else if (compressionType.equals(COMPRESSION_RLE))
			setProperty(SunRasterEncoder.OPTION_COMPRESSION, compressionType);
		// invalid option
		else
			throw new InvalidOptionException("Invalid compression type: " + compressionType);
	}

}

