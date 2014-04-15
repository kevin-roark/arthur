package com.sun.jimi.core.encoder.jpg12;

import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import java.awt.image.BufferedImage;
import java.awt.image.codec.JPEGImageEncoder;
import java.awt.image.codec.JPEGParam;

import com.sun.jimi.core.Jimi12Ext;


import com.sun.jimi.core.InvalidOptionException;
import com.sun.jimi.core.JimiEncoderBase;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiImage;
import com.sun.jimi.core.util.P;

import com.sun.jimi.util.ArrayEnumeration;
import com.sun.jimi.util.IntegerRange;
import com.sun.jimi.core.OptionsObject;


/**
 *	This is an implementation of JPGEncoder that wraps around the
 *	functionality provided by the new code available in JDK1.2 beta3
 *
 * @author	Robin Luiten
 * @version	$Revision: 1.1.1.1 $
 */
public class JPGEncoder extends JimiEncoderBase implements OptionsObject
{
	/** decoder return state */
	private int state_;

	OutputStream out_;

	static final int DEFAULT_QUALITY = 75;
    int quality_ = DEFAULT_QUALITY;

	/**
	 * initialise this Encoder
	 **/
	public void initSpecificEncoder(OutputStream out, JimiImage ji) throws JimiException
	{
		this.out_ = out;
		this.state_ = 0;
	}
	
	/* PROPERTIES HANDLING */

	public OptionsObject getOptionsObject() {
	
		return (OptionsObject)this;
		
	}
	
	static final String QUALITY_OPTION_NAME = "quality";
	static final IntegerRange POSSIBLE_QUALITY_VALUES = new IntegerRange(0, 100);
	
	static final String[] PROPERTY_NAMES = { QUALITY_OPTION_NAME };

	public Enumeration getPropertyNames()
	{
		return new ArrayEnumeration(PROPERTY_NAMES);
	}
	
	public Object getProperty(String key)  {
		
		if(key.equalsIgnoreCase(QUALITY_OPTION_NAME)) {
		
			return new Integer(quality_);
		}
		
		return null;
		
	}

	public void setProperty(String key, Object val) throws InvalidOptionException  {
		
		if (key.equalsIgnoreCase(QUALITY_OPTION_NAME)) {
		
			int value;
			try {
			
				value = ((Integer)val).intValue();
				
			} catch(ClassCastException cce) {
				
				throw new InvalidOptionException("Value must be a java.lang.Integer");
				
			}
			
			if (!POSSIBLE_QUALITY_VALUES.isInRange(value)) {
			
				throw new InvalidOptionException("Value must be between " +
					POSSIBLE_QUALITY_VALUES.getLeastValue() + " and " + 
					POSSIBLE_QUALITY_VALUES.getGreatestValue());
					
			}
			
			quality_ = value;
			
		} else {
		
			throw new InvalidOptionException("No such option");
		
		}
		
	}

	public Object getPossibleValuesForProperty(String name) throws InvalidOptionException  {
		
		if(name.equalsIgnoreCase(QUALITY_OPTION_NAME)) {
		
			return POSSIBLE_QUALITY_VALUES;
			
		}
		
		throw new InvalidOptionException("No such option");
	}

	public String getPropertyDescription(String name) throws InvalidOptionException {
	
		if (name.equalsIgnoreCase(QUALITY_OPTION_NAME)) {
		
			return "A lower quality image is smaller, but has more information loss";
			
		}
		
		throw new InvalidOptionException("No such option");
		
	}
	
	public void clearProperties()  {

		quality_ = DEFAULT_QUALITY; //default
				
	}

	public boolean driveEncoder() throws JimiException
	{
		BufferedImage bi;
		JPEGImageEncoder jpegEnc;
	
		bi = Jimi12Ext.getBufferedImage(getJimiImage());
        jpegEnc = new JPEGImageEncoder(bi, out_);

        JPEGParam params = new JPEGParam();

        params.setWidth (bi.getWidth());
        params.setHeight(bi.getHeight());
		params.setQuality(quality_, true);	// force baseline for now.
        jpegEnc.setJPEGParam(params);
        
		try
		{
	        // Encode the image...
	        jpegEnc.encode();
		}
		catch (IOException e)
		{
			state_ |= ERROR;
			throw new JimiException("Error Encoding");
		}

		state_ |= DONE;
		return false;						// finished
	}

	public void freeEncoder() throws JimiException
	{
		out_ = null;
		super.freeEncoder();
	}

	public int getState()
	{
		return this.state_;
	}

}

