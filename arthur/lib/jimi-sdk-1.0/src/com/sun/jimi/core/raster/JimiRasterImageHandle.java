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

package com.sun.jimi.core.raster;

import com.sun.jimi.core.JimiImage;
import com.sun.jimi.core.ImageAccessException;
import com.sun.jimi.core.JimiImageHandle;

/**
 * Handle to act as a non-blocking "Future" for raster images
 * @author  Luke Gorrie
 * @version $Revision: 1.1.1.1 $ $Date: 1998/12/01 12:21:57 $
 */
public class JimiRasterImageHandle extends JimiImageHandle implements JimiRasterImage
{
	public JimiRasterImage rasterImage;

	public int getWidth()
	{
		return rasterImage == null ? JimiImage.UNKNOWN : rasterImage.getWidth();
	}

	public int getHeight()
	{
		return rasterImage == null ? JimiImage.UNKNOWN : rasterImage.getHeight();
	}

	public void getRectangleRGB(int x, int y, int width, int height, int[] buffer,
															int offset, int scansize) throws ImageAccessException
	{
		ensureRasterImageSet();
		rasterImage.getRectangleRGB(x, y, width, height, buffer, offset, scansize);
	}

	public void getRowRGB(int y, int[] buffer, int offset) throws ImageAccessException
	{
		ensureRasterImageSet();
		getRowRGB(y, buffer, offset);
	}

	protected void ensureRasterImageSet() throws ImageAccessException
	{
		if (rasterImage == null) {
			throw new ImageAccessException("Wrapped image is not set.");
		}
	}

	public void setRasterImage(JimiRasterImage rasterImage)
	{
		this.rasterImage = rasterImage;
		setJimiImage(rasterImage);
	}
}

