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

/**
 * Class for testing JimiImageSerializer
 * The program seializes an image and writes the serialized data to "image.ser"
 */
public class Serialize
{
   public static void main(String args[]) throws Exception
   {
      if (args.length != 1)
      {
          System.err.println("Usage: Serialize <image>");
          System.exit(1);
      }

      FileOutputStream fileOutputStream = new FileOutputStream("image.ser");
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

      // get the image using JIMI
      java.awt.Image image = Jimi.getImage(args[0]);

      // now serialize it
      JimiImageSerializer jimiImageSerializer = new JimiImageSerializer(image);

      // write out as an object
      objectOutputStream.writeObject(jimiImageSerializer);
      objectOutputStream.flush();

      // convert to a byte array and write to a file (image.ser)
      //fileOutputStream.write(byteArrayOutputStream.toByteArray());
      fileOutputStream.close();
      System.out.println("\"image.ser\" written.");
      System.exit(0);
   }
}
