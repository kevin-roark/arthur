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

package com.sun.jimi.core.filters;

import java.awt.image.*;

/**
 * ReplicateScaleFilter extension for maintaining aspect ratio.
 * @author  Luke Gorrie
 * @version $Revision: 1.2 $ $Date: 1999/04/07 16:09:29 $
 */
public class AspectReplicateScaleFilter extends ReplicatingScaleFilter
{
	protected int fixedDimension;
	protected int maxWidth;
	protected int maxHeight;
	/** set to true if the image is already sized correctly */
	protected boolean noScaling;

	public AspectReplicateScaleFilter(int width, int height)
	{
		super(width, height);
		maxWidth = width;
		maxHeight = height;
	}

	public void setDimensions(int imageWidth, int imageHeight)
	{
		srcWidth = imageWidth;
		srcHeight = imageHeight;
		if (imageWidth <= maxWidth && imageHeight <= maxHeight) {
			destWidth = imageWidth;
			destHeight = imageHeight;
			noScaling = true;
			consumer.setDimensions(imageWidth, imageHeight);
		}
		else {
			double xProportion = imageWidth / (double)maxWidth;
			double yProportion = imageHeight / (double)maxHeight;
			int thumbnailWidth;
			int thumbnailHeight;
			if (xProportion < yProportion) {
				thumbnailHeight = maxHeight;
				thumbnailWidth = (int)(imageWidth / yProportion);
			}
			else {
				thumbnailWidth = maxWidth;
				thumbnailHeight = (int)(imageHeight / xProportion);
			}
			destWidth = thumbnailWidth;
			destHeight = thumbnailHeight;
			consumer.setDimensions(destWidth, destHeight);
		}
	}
    public void setPixels(int x, int y, int w, int h,
						  ColorModel model, byte pixels[], int off,
						  int scansize)
	{
		if (noScaling) {
			consumer.setPixels(x, y, w, h, model, pixels, off, scansize);
		}
		else {
			super.setPixels(x, y, w, h, model, pixels, off, scansize);
		}
	}

    public void setPixels(int x, int y, int w, int h,
						  ColorModel model, int pixels[], int off,
						  int scansize)
	{
		if (noScaling) {
			consumer.setPixels(x, y, w, h, model, pixels, off, scansize);
		}
		else {
			super.setPixels(x, y, w, h, model, pixels, off, scansize);
		}
	}

}

