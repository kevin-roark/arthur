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

import java.util.Enumeration;
import java.util.Hashtable;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.ImageConsumer;
import java.awt.image.ColorModel;
import java.awt.Image;

import com.sun.jimi.core.util.JimiUtil;


/**
 * The JimiSink class implements an ImageConsumer which can be attached
 * to an Image or ImageProducer object to retrieve image information and data.
 *
 * The following operations may be performed with it
 * <ul>
 * <li>retrieve everything but the pixel data from the Image. Width, Height, ColorModel
 * Properties.
 * <li>retrieve everything about the image including the pixel data.
 * <li>retrieve just the image pixel data for the case where a JimiImage
 * is supplied as destination for pixel data.
 * <li>retrieve a subset of the pixels in the image
 * </ul>
 *
 * This class implements ImageConsumer and models its interface and operation along
 * the lines of the <code>java.awt.image.PixelGrabber</code>.
 *
 * This class takes notice of the properties set on it unlike PixelGrabber. It only
 * takes properites where the key object is of type String - this is the most common
 * case anyway so this shouldnt be any real limitation.
 *
 *
 * @author	Robin Luiten
 * @version	$Revision: 1.1.1.1 $
 */
class JimiSink implements ImageConsumer
{
	int dstOff;
	int dstScan;
	private boolean grabbing;
	private int flags;

	private final int GRABBEDBITS = (ImageObserver.FRAMEBITS
																	 | ImageObserver.ALLBITS);
	private final int DONEBITS = (GRABBEDBITS
																| ImageObserver.ERROR);

	ImageProducer prod_;	// producer to retrieve data from

	JimiImage ji_;			// data storage

	boolean initJI_;		// flag indicating JI needs to be initialised
	// if clear then only setChannel() methods called on ji.

	boolean fetchData_;		// image pixel data should be retreived from producer
	// therfore JimiImage should be used and setPixels() called
	// to allocated data space.

	int dstX_;
	int dstY_;
	int dstW_;
	int dstH_;

	boolean	forceRGB_;		// flag indicates all data is being written to
	// JimiImage in default RGB mode.

	/**
	 * @param prod ImageProducer to collect image information and data from.
	 * @param fetchData flag indicates that space should be allocated
	 * for retrieving the image data pixels. If not set then this Consumer
	 * collects only the width / height / colormodel and properties information
	 * and will never allocate space for and retrieve the image pixel data.
	 **/
	public JimiSink(ImageProducer prod, boolean fetchData)
	{
		this(prod, new JimiImageImpl(null), true, fetchData);
	}

	/**
	 * There is a chance that the JimiImage that contains the image data is 
	 * not the JimiImage passed as a parameter here if the image data received 
	 * via Consumer interface causes the image data format to have to be 
	 * converted across to default RGB mode at some point in its receiving.
	 * Always call getJimiImage() to get the JimiImage object with the data.
	 *
	 * @param prod ImageProducer to collect image information and data from.
	 * @param ji JimiImage object to use to store retreived image data.
	 * @param initJI if false then ji is assumed to have width/height/colormodel
	 * set and also have setPixels() allready called to allocate data space.
	 **/
	public JimiSink(ImageProducer prod, JimiImage ji, boolean initJI)
	{
		this(prod, ji, initJI, true);
	}

	public JimiSink(ImageProducer prod, JimiImage ji, boolean initJI, boolean fetchData)
	{
		forceRGB_ = false;
		prod_ = prod;
		ji_ = ji;
		initJI_ = initJI;
		fetchData_ = fetchData;

		// what we are grabbing
		dstX_ = 0;
		dstY_ = 0;

		if (!initJI)
		{
			dstW_ = ji.getWidth();
			dstH_ = ji.getHeight();
		}
		else
		{
			dstW_ = -1;		// unknown width / height
			dstH_ = -1;		// unknown width / height
		}
//System.out.println("JimiSink() - fetch data " + fetchData_);
	}

// The following constructors are useful in there own
// right however we currently do not require them therefore they are not
// implemented.
//	public JimiSink(ImageProducer prod, JimiImage ji)
//  public JimiSink(ImageProducer prod, int x, int y, int w, int h, boolean forceRGB)
//  public JimiSink(ImageProducer prod, JimiImage ji, int x, int y, int w, int h, boolean forceRGB)

	public synchronized void startGrabbing()
	{
		if ((flags & DONEBITS) != 0)
		{
			return;
		}
		if (!grabbing)
		{
			grabbing = true;
			flags &= ~(ImageObserver.ABORT);
			prod_.startProduction(this);
		}
	}

	public synchronized void abortGrabbing()
	{
//try {throw new Exception();}
//catch(Exception e){e.printStackTrace();}
		imageComplete(IMAGEABORTED);
	}

	public boolean grabPixels() throws InterruptedException
	{
		return grabPixels(0);
	}

	public synchronized boolean grabPixels(long ms) throws InterruptedException
	{
//System.out.println("grabPixels(" + ms + ")");
		if ((flags & DONEBITS) != 0)
		{
			return (flags & GRABBEDBITS) != 0;
		}
		long end = ms + System.currentTimeMillis();
		if (!grabbing)
		{
			grabbing = true;
			flags &= ~(ImageObserver.ABORT);
			prod_.startProduction(this);
		}

		while (grabbing)
		{
			long timeout;
			if (ms == 0)
			{
				timeout = 0;
			}
			else
			{
				timeout = end - System.currentTimeMillis();
				if (timeout <= 0)
				{
					break;
				}
			}
			wait(timeout);
		}
		if ((flags & GRABBEDBITS) != 0)
			ji_.addFullCoverage();

		return (flags & GRABBEDBITS) != 0;
	}

	public synchronized int getStatus()
	{
		return flags;
	}

	public synchronized int getWidth()
	{
		return (dstW_ < 0) ? -1 : dstW_;
	}

	public synchronized int getHeight()
	{
		return (dstH_ < 0) ? -1 : dstH_;
	}

//    public synchronized Object getPixels()
	public synchronized JimiImage getJimiImage()
	{
		return ji_;
	}

	public synchronized ColorModel getColorModel()
	{
		return ji_.getColorModel();
	}

	public void setDimensions(int width, int height)
	{
//System.out.println("setDimensions(" + width + ", " + height + ")");
		if (dstW_ < 0)
		{
			dstW_ = width - dstX_;
		}
		if (dstH_ < 0)
		{
			dstH_ = height - dstY_;
		}

		if (dstW_ <= 0 || dstH_ <= 0)
		{
			imageComplete(STATICIMAGEDONE);
		}
		else if (initJI_ && forceRGB_)
		{
			ji_.setSize(dstW_, dstH_);
			ji_.setColorModel(ColorModel.getRGBdefault());
			try
			{
				if (fetchData_)
					ji_.setPixels();					// allocate storage
			}
			catch (JimiException e)
			{
				abortGrabbing();
			}
			initJI_ = false;
		}

		flags |= (ImageObserver.WIDTH | ImageObserver.HEIGHT);
	}

	public void setHints(int hints)
	{
		// we dont care about order etc.
		return;
	}

	/**
	 * This method ignores any properties that have Key objects
	 * which are not instances of String.
	 **/
	public void setProperties(Hashtable props)
	{
//System.out.println("setProperties() " + props.toString());
		Enumeration eKeys;
		Object elem;

		eKeys = props.keys(); 
		while (eKeys.hasMoreElements())
		{
			elem = eKeys.nextElement();
			if (elem instanceof String)
			{
				try
				{
					ji_.setProperty((String)elem, props.get(elem));
				}
				catch (InvalidOptionException e)
				{
					// invalid options are ignored as we cannnot do
					// anything useful with any errors found. a JimiImage
					// really has no checking of options so wont throw any.
				}
			}
		}
		return;
	}

	public void setColorModel(ColorModel model)
	{
		ji_.setColorModel(model);
	}


	private void convertToRGB()
	{
		try
		{
			ji_.setRGBDefault(true);	// data retrieved in RGB default format

			java.awt.Rectangle coveredArea = ji_.getCoverageBounds();
						
			for (int x = coveredArea.x; x < coveredArea.width + coveredArea.x; x++) {
				for (int y = coveredArea.y; y < coveredArea.height + coveredArea.y; y++) {
				
					ji_.setChannel(x, y, ji_.getChannel(x, y));
					
				}
			}
			
		}
		catch (JimiException e)
		{
			abortGrabbing();
		}
	}

	/**
	 * The setPixels method is part of the ImageConsumer API which
	 * this class must implement to retrieve the pixels.
	 */
	public void setPixels(int srcX, int srcY, int srcW, int srcH,
												ColorModel model,
												byte pixels[], int srcOff, int srcScan)
	{
/* System.out.println("setPixels() B " +
	 " x " +  srcX +
	 " y " +  srcY +
	 " w " +  srcW +
	 " h " +  srcH +
	 " model " +  model +
	 " srcOff " +  srcOff +
	 " srcScan " +  srcScan ); */

		// handle clipping to data we are collecting
		if (srcY < dstY_)
		{
			int diff = dstY_ - srcY;
			if (diff >= srcH)
				return;
			srcOff += srcScan * diff;
			srcY += diff;
			srcH -= diff;
		}
		if (srcY + srcH > dstY_ + dstH_)
		{
			srcH = (dstY_ + dstH_) - srcY;
			if (srcH <= 0)
				return;
		}
		if (srcX < dstX_)
		{
			int diff = dstX_ - srcX;
			if (diff >= srcW)
				return;
			srcOff += diff;
			srcX += diff;
			srcW -= diff;
		}
		if (srcX + srcW > dstX_ + dstW_)
		{
			srcW = (dstX_ + dstW_) - srcX;
			if (srcW <= 0)
				return;
		}

		try
		{

			if (initJI_)
			{
				ji_.setSize(dstW_, dstH_);
				ji_.setColorModel(model);
				if (fetchData_)
					ji_.setPixels();					// allocate storage
				initJI_ = false;
			}

			// this picksup a change and also detects if we have done a conversion
			// because getColorModel() returns the default model in thata case.
			// We only care if they are the exact same color model instance.
			if (model != ji_.getColorModel())
			{
				// need to convert JimiImage to RGB default model
				// then all future setPixels() must be converted to RGB
				// before being applied to JimiImage.
				// This is a rare case, and will not occur in any Jimi
				// initiated case, however is required for completeness.
				if (!JimiUtil.isRGBDefault(ji_.getColorModel()))
					convertToRGB();

				int[] bufI = new int[srcW];	// buffer a row.
				ColorModel cm = ji_.getColorModel();
				int rowRemainder = srcScan - srcW;
				for (int i = srcY; i < (srcY + srcH); ++i)
				{
					for (int j = 0; j < srcW; ++j)
					{
						bufI[j] = cm.getRGB(pixels[srcOff++]&0xFF);
					}
					srcOff += rowRemainder;
				}
				ji_.setChannel(srcX, srcY, srcW, srcH, bufI, srcOff, srcScan);
				ji_.addCoverage(srcX, srcY, srcW, srcH);
			}
			else
			{
				// place data
				if (fetchData_)
				{
					ji_.setChannel(0, srcX, srcY, srcW, srcH, pixels, srcOff, srcScan);
					ji_.addCoverage(srcX, srcY, srcW, srcH);
				}
				else	// we have all data and dont care about any pixel data
					stopProducer();
			}

		}
		catch (JimiException e)
		{
			abortGrabbing();
		}

		flags |= ImageObserver.SOMEBITS;
	}

	/**
	 * The setPixels method is part of the ImageConsumer API which
	 * this class must implement to retrieve the pixels.
	 */
	public void setPixels(int srcX, int srcY, int srcW, int srcH,
												ColorModel model,
												int pixels[], int srcOff, int srcScan)
	{
/* System.out.println("setPixels() I " +
	 " x " +  srcX +
	 " y " +  srcY +
	 " w " +  srcW +
	 " h " +  srcH +
	 " model " +  model +
	 " srcOff " +  srcOff +
	 " srcScan " +  srcScan ); */

		// handle clipping to data we are collecting
		if (srcY < dstY_)
		{
			int diff = dstY_ - srcY;
			if (diff >= srcH)
			{
				return;
			}
			srcOff += srcScan * diff;
			srcY += diff;
			srcH -= diff;
		}
		if (srcY + srcH > dstY_ + dstH_)
		{
			srcH = (dstY_ + dstH_) - srcY;
			if (srcH <= 0)
			{
				return;
			}
		}
		if (srcX < dstX_)
		{
			int diff = dstX_ - srcX;
			if (diff >= srcW)
			{
				return;
			}
			srcOff += diff;
			srcX += diff;
			srcW -= diff;
		}
		if (srcX + srcW > dstX_ + dstW_)
		{
			srcW = (dstX_ + dstW_) - srcX;
			if (srcW <= 0)
			{
				return;
			}
		}

		try
		{
			if (initJI_)
			{
				ji_.setSize(dstW_, dstH_);
				ji_.setColorModel(model);
				if (fetchData_)
					ji_.setPixels();					// allocate storage
				initJI_ = false;
			}

			// this picksup a change and also detects if we have done a conversion
			// because getColorModel() returns the default model in thata case.
			if (model != ji_.getColorModel())
			{
				// need to convert JimiImage to RGB default model
				// then all future setPixels() must be converted to RGB
				// before being applied to JimiImage.
				// This is a rare case, and will not occur in any Jimi
				// initiated case, however is required for completeness
				// need to convert JimiImage to RGB default model
				// then all future setPixels() must be converted to RGB
				// before being applied to JimiImage.
				// This is a rare case, and will not occur in any Jimi
				// initiated case, however is required for completeness.
				if (!JimiUtil.isRGBDefault(ji_.getColorModel()))
					convertToRGB();

				/*
					int[] bufI = new int[srcW];	// buffer a row.
					ColorModel cm = ji_.getColorModel();
			    int rowRemainder = srcScan - srcW;
					for (int i = srcY; i < (srcY + srcH); ++i)
					{
					for (int j = 0; j < srcW; ++j)
					{
					bufI[j] = cm.getRGB(pixels[srcOff++]&0xFF);
					}
					srcOff += rowRemainder;
					}
				*/
				// place data
				if (fetchData_)
				{
					ji_.setChannel(srcX, srcY, srcW, srcH, pixels, srcOff, srcScan);
					ji_.addCoverage(srcX, srcY, srcW, srcH);
				}
				else	// we have all data and dont care about any pixel data
					stopProducer();
				
			}
			else
			{
				// place data
				if (fetchData_)
				{
					ji_.setChannel(srcX, srcY, srcW, srcH, pixels, srcOff, srcScan);
					ji_.addCoverage(srcX, srcY, srcW, srcH);
				}
				else	// we have all data and dont care about any pixel data
					stopProducer();
			}
		}
		catch (JimiException e)
		{
			e.printStackTrace();
			abortGrabbing();
		}

		flags |= ImageObserver.SOMEBITS;
	}


	public synchronized void stopProducer()
	{
		grabbing = false;
		prod_.removeConsumer(this);
		notifyAll();
	}

	/**
	 * The imageComplete method is part of the ImageConsumer API which
	 * this class must implement to retrieve the pixels.
	 */
	public synchronized void imageComplete(int status)
	{
		grabbing = false;
		switch (status)
		{
		 default:
		 case IMAGEERROR:
			 flags |= ImageObserver.ERROR | ImageObserver.ABORT;
			 break;

		 case IMAGEABORTED:
			 flags |= ImageObserver.ABORT;
			 break;

		 case STATICIMAGEDONE:
			 flags |= ImageObserver.ALLBITS;
			 break;
		 case SINGLEFRAMEDONE:
			 flags |= ImageObserver.FRAMEBITS;
			 break;
		}

		ji_.addFullCoverage();
		prod_.removeConsumer(this);
		notifyAll();
	}

	/**
	 * DEPRECATED:  Replaced by getStatus().
	 */
	public synchronized int status()
	{
		return flags;
	}
}

/*
	$Log: JimiSink.java,v $
	Revision 1.1.1.1  1998/12/01 12:21:54  luke
	imported

	Revision 1.1.1.1  1998/09/01 02:48:47  luke
	Imported from jimi_1_0_Release codebase
	
	Revision 1.4  1998/07/21 04:20:51  luke
	Changed setColorModel() to change the JimiImage's ColorModel, rather
	than doing nothing.  This avoids an artificial "ABORT" code being
	generated and causing a fatal-to-collection error.

	Revision 1.3  1998/06/30 18:20:01  chris
	JimiSink's convertToRGB method was totally fucked up.
	It ALWAYS created a deadlock when called since it waited for coverage,
	and yet the thread that created the covered data is the same thread
	as the thread that called convertToRGB.
	It also would NEVER work.  The changes to the JimiImage were never
	sent to the client since only an instance variable was changed.  And
	not the client's copy of JimiImage.
	Both bugs Fixed.

*/

