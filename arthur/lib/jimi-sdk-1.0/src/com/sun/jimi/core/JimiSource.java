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

import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;
import java.awt.image.ColorModel;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiDecoder;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiImage;

/**
 * This class operates in a similar capacity to the AWT MemoryImageSource class.
 * This class has the following extensions beyond the functionality of 
 * MemoryImageSource
 * <ul>
 * <li>A JimiImage object can be the source for the image data produced
 * <li>An initialised JimiDecoder can be the source for the image data produced
 * <li>Properties set on the JimiImage or loaded from the image file are 
 * sent to registered ImageConsumers. [unlike MemoryImageSource]
 * <li>Loads an image by driving a JimiDecoder without producing data 
 * to any image consumers by using the startProduction method 
 * which takes no parameters.
 * </ul>
 *
 * For every image consumer a seperate thread is spawned off which sends
 * the data to that ImageConsumer.
 *
 * @author	Robin Luiten
 * @version	$Revision: 1.1.1.1 $
 **/
public class JimiSource implements ImageProducer, Runnable
{
	/** JimiImage object for holding decoded image data to be sent **/
	JimiImage ji_;

	/** collection of threads used to produce data to ImageConumsers **/
	Hashtable producerThreads_ = new Hashtable();

	/** Thread for driving of the image data decoding **/
	Thread decoder_;

	/** jimi decoder which is driven to load image data */
	JimiDecoder jd_;

	/** decoder state **/
	int dState_;

	/** flag indicating that the driver decoder thread is finished **/
	boolean doneDriving_;

	/**
	 * @param jd This JimiDecoder must have been initialised by
	 * calling initDecoder() before creation of this JimiSource.
	 * @see com.sun.jimi.core.JimiDecoder#initDecoder
	 **/
	public JimiSource(JimiDecoder jd)
	{
		jd_ = jd;
		ji_ = jd_.getJimiImage();
		doneDriving_ = false;
	}

	/**
	 * @param ji This JimiImage must have all the required data
	 * fields set to the desired image data.
	 **/
	public JimiSource(JimiImage ji)
	{
		ji_ = ji;
		// a decoder is not required
	}

	/**
	 * wait until the driver thread of the JimiSource is finished driving
	 * the JimiDecoder.
	 **/
	public synchronized void waitDone() throws JimiException
	{
		while (!doneDriving_)
		{
		    try
		    {
    			wait();
    		}
    		catch (InterruptedException e)
    		{
    		    throw new JimiException(e.getMessage());
    		}
		}
	}

	/** start the decoder thread **/
	public synchronized void start()
	{
		if (decoder_ == null)
			decoder_ = new Thread(this, "JimiSource run()");
		if (!doneDriving_ && (!decoder_.isAlive()))
			decoder_.start();
	}

	/**
	 * This method drives the JimiDecoder to load the image data into
	 * the JimiImage. This method is not used if this class is constructed
	 * with a JimiImage object.
	 **/
	public void run()
	{
		try
		{
			// at this point initDecoder on JimiDecoder has occurred
			if (jd_ != null)
			{
				do
				{
					while (jd_.driveDecoder())
						;/** drive loader */

					dState_ = jd_.getState();

					if ((dState_ & JimiDecoder.ERROR) != 0)	// bail out.
					{
						ji_.setError(true);
						break;	// giveup
					}
				} while ((dState_ & JimiDecoder.IMAGEAVAIL) == 0);
			}

		}
		catch (JimiException e)
		{
			ji_.setError(true);
		}
		finally
		{
			synchronized (this)
			{
				doneDriving_ = true;
				notifyAll();
			}
		}
	}

	/**
	 * @return the JimiImage structure used as the source of the data.
	 **/
	public synchronized JimiImage getJimiImage()
	{
		return ji_;
	}

	/**
	 * Only valid if this JimiSource has been constructed with a
	 * JimiDecoder. This method causes the class to retrieve the 
	 * image data into a JimiImage object and no data is sent 
	 * to any consumers unless they were registered before this 
	 * method was called.
	 **/
    public void startProduction()
	{
		start();	// start image decoding
	}

// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
// #      Below here are the methods in the ImageConsumer API      #
// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

    public synchronized void addConsumer(ImageConsumer ic)
    {
		if (producerThreads_.contains(ic))
		    return;

		JimiProducer jp = new JimiProducer(ic, ji_, this);
		Thread th = new Thread(jp, "JimiSource ic " + ic.toString());
		producerThreads_.put(ic, th);

		start();		// ensure that decoder started
		th.start();		// start producer
	}

    public synchronized boolean isConsumer(ImageConsumer ic)
    {
		return producerThreads_.contains(ic);
	}

    public synchronized void removeConsumer(ImageConsumer ic)
    {
		if (isConsumer(ic))
		{
			Thread th = (Thread)producerThreads_.get(ic);
			if (th.isAlive())
			{
				th.stop();	// stop the send thread
			}
			producerThreads_.remove(ic);
		}
	}

    public void requestTopDownLeftRightResend(ImageConsumer ic)
    {
		addConsumer(ic);
    }

    public void startProduction(ImageConsumer ic)
    {
		addConsumer(ic);
	}
}

/**
 * This class handles the sending of all data to a single image consumer.
 * All data to be sent is retrieved from 
 * A seperate Thread should be started for each image consumer that needs
 * data sent to it via this class.
 *
 * @author	Robin Luiten
 * @version	$Revision: 1.1.1.1 $
 **/
class JimiProducer implements Runnable
{
	/** The JimiSource  which spawned this Object/Thread **/
	JimiSource js_;

	/** consumer to be sent the data **/
	ImageConsumer ic_;

	/** The source of the image data to be sent **/
	JimiImage ji_;

	JimiProducer(ImageConsumer ic, JimiImage ji, JimiSource js)
	{
		ic_ = ic;
		ji_ = ji;
		js_ = js;
	}

	public void run()
	{

		// wait until the image information is available
		try // being paranoid :o)
		{
			while (ji_.getColorModel() == null || ji_.getWidth() < 0 || ji_.getHeight() < 0)
			{
				checkSendError(ic_);
				ji_.waitObj();
			}

			initConsumer(ic_);

			ji_.waitStorage();

			int width = ji_.getWidth();
			int height = ji_.getHeight();
			ColorModel cm = ji_.getColorModel();

			// pump out data
			for (int i = 0; i < height;)
			{
				checkSendError(ic_);
				i += ji_.sendPixels(i, ic_);
			}
			ic_.imageComplete(ImageConsumer.STATICIMAGEDONE);
		}
		catch (InterruptedException e)
		{
			ic_.imageComplete(ImageConsumer.IMAGEERROR);
		}
		catch (JimiException e)
		{
// development testing
//			e.printStackTrace();
			ic_.imageComplete(ImageConsumer.IMAGEERROR);
		}
	}

	/** check for and forward any errors on the JimiImage to consumer */
	private void checkSendError(ImageConsumer ic)
	{
		if (ji_.getError())
		{
			ic.imageComplete(ImageConsumer.IMAGEERROR);
		}

		if (ji_.getAbort())
		{
			ic.imageComplete(ImageConsumer.IMAGEABORTED);
		}
	}

	/**
	 * currently does not handle animating option or any different
	 * order production due to order that decoder fills JimiImage
	 */
    private void initConsumer(ImageConsumer ic)
    {
	    ic.setDimensions(ji_.getWidth(), ji_.getHeight());
	    ic.setProperties(ji_.getProperties());
	    ic.setColorModel(ji_.getColorModel());
//System.out.println("ic.setColorModel() " + ji.getColorModel().toString());
	    ic.setHints(ImageConsumer.TOPDOWNLEFTRIGHT |
					ImageConsumer.COMPLETESCANLINES |
					ImageConsumer.SINGLEPASS |
					ImageConsumer.SINGLEFRAME);
	}
}

