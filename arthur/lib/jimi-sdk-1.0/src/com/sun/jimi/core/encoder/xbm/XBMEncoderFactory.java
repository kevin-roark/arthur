/*
 * Copyright (c) 1998 by Sun Microsystems, Inc.
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

package com.sun.jimi.core.encoder.xbm;

import com.sun.jimi.core.*;

/**
 * Encoder factory implementation for XBM
 * @author  Luke Gorrie
 * @version $Revision: 1.1 $ $Date: 1999/01/20 11:28:57 $
 */
public class XBMEncoderFactory extends JimiEncoderFactorySupport
{
	public static final String[] MIME_TYPES = { "image/xbm" };
	public static final String[] FILENAME_EXTENSIONS = { "xbm" };

	public static final String FORMAT_NAME = "X Bitmap (XBM)";

	/**
	 * Get the mime-types for the format.  This type should not include any "x-" even for
	 * non-standard formats.
	 * @return the mimetypes
	 */
	public String[] getMimeTypes()
	{
		return MIME_TYPES;
	}

	/**
	 * Get the filename-extensions belonging to this format.  e.g, "jpg", "jpeg"
	 * @return an array of extension strings
	 */
	public String[] getFilenameExtensions()
	{
		return FILENAME_EXTENSIONS;
	}

	/**
	 * Get the name of the format.  e.g., "Graphics Interchange Format (GIF)"
	 * @return the name
	 */
	public String getFormatName()
	{
		return FORMAT_NAME;
	}

	/**
	 * Check whether there is multi-image support.
	 * @return true if the encoder can encode multiple images
	 */
	public boolean canEncodeMultipleImages()
	{
		return false;
	}

	/**
	 * Instantiate an encoder for the format.
	 * @return a decoder instance
	 */
	public JimiEncoder createEncoder()
	{
		return new XBMEncoder();
	}

}

