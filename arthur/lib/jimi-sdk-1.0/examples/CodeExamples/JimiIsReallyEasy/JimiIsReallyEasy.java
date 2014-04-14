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

import com.sun.jimi.core.*;
import java.awt.Image;

public class JimiIsReallyEasy
{
	public static void main(String[] args) throws JimiException
	{
		if (args.length < 2) {
			System.err.println("Usage: JimiIsReallyEasy <source> <dest>");
		}
		else {
			Image image = Jimi.getImage(args[0]);
			Jimi.putImage(image, args[1]);
			System.exit(0);
		}
	}
}
