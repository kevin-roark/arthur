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

package com.sun.jimi.core.encoder.pict;

import com.sun.jimi.core.*;

/**
 * Encoder factory implementation for PICT
 * @author  Luke Gorrie
 * @version $Revision: 1.1.1.1 $ $Date: 1998/12/01 12:21:57 $
 */
public class PICTEncoderFactory extends JimiEncoderFactorySupport
{
	public static final String[] MIME_TYPES = { "image/pict" };
	public static final String[] FILENAME_EXTENSIONS = { "pict", "pct" };

	public static final String FORMAT_NAME = "Macintosh PICT (PICT)";

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
		return new PICTEncoder();
	}

}

