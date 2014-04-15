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
 * Provide a common interface to provide an extension mechanism for
 * replacing the default storage classes that JimiImage uses for actual
 * data storage and handling.
 *
 * @author	Robin Luiten
 * @version	$Revision: 1.1.1.1 $
 **/
public interface StorageFactory
{
	/**
	 * @param ji The JimiImageImpl class that requires the JimiImageStore implementation
	 * It is passed as a JimiImage as a convenience.
	 * @param width width of image data to store
	 * @param height height of image data to store
	 * @param cm ColorModel for the image data to store
	 * @return a JimiImageStore class implementayion suitable for holding the
	 * image data of the given width/height and ColorModel.
	 **/
	public JimiImageStore createStorage(JimiImage ji, int width, int height, ColorModel cm);
}
