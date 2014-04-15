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
package com.sun.jimi.core;

import java.awt.image.ColorModel;

/**
 * Jimi default implementation of StorageFactory.<p>
 * This class will be access directly from JimiImageImpl if the StorageFactory
 * constructor parameter to JimiImageImpl is null.<p>
 *
 * @author	Robin Luiten
 * @version	$Revision: 1.1.1.1 $
 **/
public class JimiStorageFactory implements StorageFactory
{
	public JimiStorageFactory()
	{
	}

	/**
	 * Jimi default storage creation facility.
	 **/
	public JimiImageStore createStorage(JimiImage ji, int width, int height, ColorModel cm)
	{
		JimiImageStore jis;

		if (cm.getPixelSize() == 1)
			jis = new JimiMemPackedByte(ji, width, height, cm);
		else if (cm.getPixelSize() <= 8)
			jis = new JimiMemByte(ji, width, height, cm);
		else
			jis = new JimiMemInt(ji, width, height, cm);
		return jis;

	}
}
