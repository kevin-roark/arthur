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

package com.sun.jimi.core.raster.imagetype;

import java.awt.image.*;

import com.sun.jimi.core.*;
import com.sun.jimi.core.raster.*;

/**
 * "Image Type" class for 8bpp palette images.  This class is used to check whether an image
 * is byte-based and uses a palette-based ColorModel (IndexColorModel).
 * @author  Luke Gorrie
 * @version $Revision: 1.1.1.1 $ $Date: 1998/12/01 12:21:58 $
 */
public class BytePaletteImage
{
	public static boolean checkImage(JimiImage image)
	{
		return (image instanceof ByteRasterImage)
			&& (((ByteRasterImage)image).getColorModel() instanceof IndexColorModel);
	}

	public static ByteRasterImage convertImage(JimiImage image, boolean dither)
		throws JimiException
	{
		JimiRasterImage rasterImage = Jimi.createRasterImage(image);
		if (checkImage(rasterImage)) {
			return (ByteRasterImage)rasterImage;
		}
		else {
			JimiImageColorReducer reducer = new JimiImageColorReducer(256);
			if (dither) {
				rasterImage = reducer.colorReduceFS(rasterImage);
			}
			else {
				rasterImage = reducer.colorReduce(rasterImage);
			}
		}
	}
}

