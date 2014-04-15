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

public class Main
{
	private static final String SWING_WARNING =
	"This demonstartion requires JDK1.2, or JDK 1.1.x with Swing 1.1.";


	public static void main(String[] args)
	{
		try {
			Class c = Class.forName("javax.swing.JFrame");
			if (c == null) throw new RuntimeException();
		}
		catch (Exception e) {
			System.err.println(SWING_WARNING);
			System.exit(1);
		}
		ImageProcessor ip = new ImageProcessor();
		for (int i = 0; i < args.length; i++)
			ip.openImage(args[i]);
	}
}

