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

import java.io.*;
import java.awt.*;
import java.awt.Toolkit.*;
import com.sun.jimi.core.*;
import com.sun.jimi.core.util.*;
import java.lang.Integer;

/**
 * Class for testing JimiImageSerializer.  Takes an input file with serialized image
 * data, deserializes the image and prints out its dimesions as a test.
 */
public class Deserialize
{
   public static void main(String[] args) throws Exception
   {
      if (args.length != 1)
      {
         System.err.println("Usage: Deserialize <serialized image file>");
         System.exit(1);
      }

      FileInputStream file_in = new FileInputStream(args[0]);
      ObjectInputStream object_in = new ObjectInputStream(file_in);

      // read the file and deserialize it
      JimiImageSerializer ser = (JimiImageSerializer)object_in.readObject();
      Image image = ser.getImage();

      GraphicsUtils.waitForImage(new Canvas(), image);

      // print out image dimensions as a test
	  System.out.println("Writing deserialized image to \"image.png\"");
	  Jimi.putImage(image, "image.png");

      file_in.close();
      System.exit(0);
   }
}
