/*
 * Copyright 1998,1999 by Sun Microsystems, Inc.
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiWriter;
import com.sun.jimi.core.options.PNGOptions;

import java.awt.image.ImageProducer;

/**
 * Simple class for saving PNG files with or without compression
 * as a demonstration of format-specific option usage.
 */
public class CustomOption
{
	public static void main(String[] args)
	{
		if (args.length < 3) {
			System.err.println("Convert a file to PNG with compression on or off.");
			System.err.println("Usage: CustomOption <source> <dest> <max|default|none>");
			System.exit(1);
		}
		String source = args[0];
		String dest = args[1];
		String compression = args[2];

		// only handle PNG files
		if (!dest.endsWith("png")) {
			dest += ".png";
			System.out.println("Overriding to PNG, output file: " + dest);
		}

		try {
			PNGOptions options = new PNGOptions();
			if (compression.equals("max")) {
				options.setCompressionType(PNGOptions.COMPRESSION_MAX);
			}
			else if (compression.equals("none")) {
				options.setCompressionType(PNGOptions.COMPRESSION_NONE);
			}
			else {
				options.setCompressionType(PNGOptions.COMPRESSION_DEFAULT);
			}
			ImageProducer image = Jimi.getImageProducer(args[0]);
			JimiWriter writer = Jimi.createJimiWriter(dest);
			writer.setSource(image);
			writer.setOptions(options);
			writer.putImage(dest);
		}
		catch (JimiException je) {
			System.err.println("Error: " + je);
		}
	}
}

