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

package com.sun.jimi.core.decoder.pcx;


import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.io.EOFException;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.sun.jimi.core.JimiDecoder;
import com.sun.jimi.core.JimiDecoderBase;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiImage;
import com.sun.jimi.core.util.LEDataInputStream;
import com.sun.jimi.core.util.JimiUtil;
import com.sun.jimi.tools.Debug;

/**
 * Decoder for the windows .pcx format (for PC Paintbrush files)
 *
 * @author	Karl Avedal
 * @version	$Revision: 1.2 $
 */
public class DCXDecoder extends JimiDecoderBase
{
	private                     JimiImage ji_;
	private                     InputStream in_;
	private                     LEDataInputStream din_;
	private int                 state;
	private int                 fileLength_;

	private int                 numberOfImages_=JimiDecoder.UNKNOWNCOUNT;
	private int                 currentImage_;

    private PCXHeader           header_;
	private byte[]              byteScanLine;

	private ColorModel          model_;

	private PCXImage[]          pi_;

	private DCXHeader           dcxheader_;

	private ByteArrayOutputStream byteOut_;

	public void initDecoder(InputStream in, JimiImage ji) throws JimiException
	{
   	    state = MOREIMAGES;

   	    currentImage_ = 0;

		this.in_  = in;

		BufferedInputStream bin = new BufferedInputStream(in, 2048);

		byteOut_ = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

        int readBytes;
        int length=0;

		try
		{
    		while ((readBytes = bin.read(buf)) != -1)
    		{
    		    length += readBytes;
    			byteOut_.write(buf);
    		}

    	}
    	catch (IOException e)
    	{
    	}

        fileLength_ = length;

		this.din_ = new LEDataInputStream(new ByteArrayInputStream(byteOut_.toByteArray()));

        din_.mark(fileLength_);

        dcxheader_ = new DCXHeader(din_);

        numberOfImages_ = dcxheader_.getNumberOfImages();

        pi_ = new PCXImage[numberOfImages_];

		this.ji_  = ji;
	}

	public int getNumberOfImages()
	{
		return numberOfImages_;
	}

	/**
	  *
	 **/
	public int getCapabilities()
	{
		return MULTIIMAGE;		// multi capabilities
	}

	/**
	  *
	 **/
	public void setJimiImage(JimiImage ji)
	{
        ji_= ji;
	}

	/**
	 *
	 *
	 **/
	public void skipImage() throws JimiException
	{
        currentImage_++;
        if (currentImage_>=numberOfImages_)
        {
            state ^= MOREIMAGES;
        }
    }

    protected void loadImage() throws JimiException
    {
	    try
		{
            if (pi_[currentImage_] == null)
            {

    		    int offset = dcxheader_.getOffset(currentImage_);
    		    int length = dcxheader_.getLength(currentImage_);

    		    if (length == -1) length = fileLength_ - offset;

                din_ = new LEDataInputStream(new ByteArrayInputStream(byteOut_.toByteArray()));
                din_.mark(fileLength_);
                din_.skip(offset);

                header_ = new PCXHeader(din_, length);

    			state |= INFOAVAIL;			// got image info

    			// and finally the image itself

                pi_[currentImage_] = new PCXImage(din_, header_);
            }
            initJimiImage(pi_[currentImage_]);

            ji_.setChannel(0);	// ensure that pixels all 0 initially

            PCXImage pi = pi_[currentImage_];

    		for (int i = 0; i<pi_[currentImage_].getHeight(); i++)
    		{


    		    int offset = i*pi.getWidth();
                ji_.setChannel(0,i,pi.getImageData(), offset, pi.getWidth());
				ji_.addCoverage(i);
            }


    		state |= IMAGEAVAIL;		// got image
    		skipImage();

		}
		catch (Exception e)
		{
			state |= ERROR;
			throw new JimiException("Error");
		}
	}

	public boolean driveDecoder() throws JimiException
	{
	    try
		{
            loadImage();
			ji_.addFullCoverage();
    		state |= INFOAVAIL | IMAGEAVAIL;

		}
		catch (JimiException e)
		{
			state |= ERROR;
			throw e;
		}

		return false;	// state change - Done actually.


	}

	public void freeDecoder() throws JimiException
	{
		in_  = null;
		din_ = null;
		ji_  = null;
	}

	public int getState()
	{
		// This does not access or reference the JimiImage therefore no lock is required
		return state;
	}

	public JimiImage getJimiImage()
	{
		return ji_;
	}


	private void initJimiImage(PCXImage pi) throws JimiException
	{
		ji_.setSize(pi.getWidth(), pi.getHeight());

		// Create a Color Model

		ji_.setColorModel(header_.getColorModel());
		System.gc();
		ji_.setPixels();

	}

}
