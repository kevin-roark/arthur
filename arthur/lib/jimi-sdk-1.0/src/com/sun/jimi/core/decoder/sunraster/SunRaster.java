/*
 * Copyright 1997 by Sun Microsystems, Inc.
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

package com.sun.jimi.core.decoder.sunraster;

import com.sun.jimi.core.Extension;

/** Temporary sunraster extension <br><strong>DEBUGGING ONLY</strong>
  *
  * @version $version$
  * @author Christian Lucas
  */
public class SunRaster implements Extension {

	private final String[] enc = { };
	private final String[] dec = { "ras" };

	public String[] getEncoderNames() {

		return enc;

	}

	public String[] getDecoderNames() {

		return dec;

	}

	public String getEncoderImplementationName(String encoderName) {

		return null;

	}

	public String getDecoderImplementationName(String decoderName) {

		if(decoderName.equals("ras")) {

			return "com.sun.jimi.core.decoder.sunraster.SunRasterDecoder";

		} else {

			return null;

		}

	}

	public String getInfo() {

		return null;

	}

	public String getVersion() {

		return "1";

	}

	public String getVendor() {

		return "Sun Microsystems, Inc.";

	}

	public String getName() {

		return "Debug : Sun Raster";

	}

	/** This must be changed!!!! Right now it always returns true.
	  * CL
	  */
	public boolean isCompatibleWithCoreVersion(String version) {

		return true;

	}

}
