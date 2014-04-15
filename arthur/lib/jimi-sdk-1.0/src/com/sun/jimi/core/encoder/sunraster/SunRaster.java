package com.sun.jimi.core.encoder.sunraster;

import com.sun.jimi.core.Extension;

/**
 * Extension class for SunRaster encoding support.
 * @author Luke Gorrie
 * @version $Revision: 1.1.1.1 $ $Date: 1998/12/01 12:21:57 $
 **/
public class SunRaster implements Extension
{

	private static final String VERSION_STRING = "1.0.1";

	private static final String[] DECODERS = { };
	private static final String[] ENCODERS = { "ras" };

	public String getName()
	{
		return "Sun Raster (.RAS) Encoder";
	}

	public String getVendor()
	{
		return "Sun Microsystems, Inc.";
	}

	public String getVersion()
	{
		return getName() + " " + VERSION_STRING;
	}

	public String getInfo()
	{
		return "Encodes as 24BIT non-palette images, and 8BIT palette-images (with optional " +
		"RLE compression)";
	}

	public boolean isCompatibleWithCoreVersion(String version)
	{
		return version.equals("1.0");
	}

	public String[] getDecoderNames()
	{
		return DECODERS;
	}

	public String[] getEncoderNames()
	{
		return ENCODERS;
	}

	public String getDecoderImplementationName(String name)
	{
		return null;
	}

	public String getEncoderImplementationName(String name)
	{
		String returnString = null;
		if (name.equalsIgnoreCase(ENCODERS[0]))
		{
			// return (SunRasterEncoder.class).getName(); 1.0 incompatible
			try
		 	{
				returnString = (Class.forName("com.sun.jimi.core.encoder.sunraster.SunRasterEncoder").getName());
			}
			catch (ClassNotFoundException e)
			{	}
		}
		else
			returnString = null;
		return returnString;			
	}

}
