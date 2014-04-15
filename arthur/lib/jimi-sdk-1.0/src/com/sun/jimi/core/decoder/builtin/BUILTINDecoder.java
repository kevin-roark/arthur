/*
 * Copyright 1998 by Sun Microsystems, Inc.
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

package com.sun.jimi.core.decoder.builtin;

import java.io.InputStream;

import com.sun.jimi.core.JimiDecoderBase;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiImage;

/**
 * Dummy implementation of JIMIDecoder to handle detection of the
 * builtin decoders in a consistent manner
 **/
public class BUILTINDecoder extends JimiDecoderBase
{
	public void initDecoder(InputStream in, JimiImage ji) throws JimiException
	{
	}

	public boolean driveDecoder() throws JimiException
	{
		return false;
	}

	public void freeDecoder() throws JimiException
	{
	}

	public int getState()
	{
		return ERROR;
	}

	public JimiImage getJimiImage()
	{
		return null;
	}
}
