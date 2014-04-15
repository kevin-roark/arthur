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

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import com.sun.jimi.core.util.GraphicsUtils;
import com.sun.jimi.core.component.*;
import com.sun.jimi.core.filters.*;

public class FilterListener implements ActionListener, Observer
{
   final private SmartFilterDialog sfd  = new SmartFilterDialog(JimiBrowserApp.getDisplayFrame());
   private JimiCanvas canvas;
   private ImageProducer producer;
   private ImageProducer oldProducer;
   private Image img;

   public FilterListener(JimiCanvas canvas)
   {
      this.canvas = canvas;
      sfd.addObserver(this);
   }

   public void actionPerformed(ActionEvent e)
   {
   	   //System.out.println("resetting");
	   if(oldProducer != null) 
	   {
	      Image oldImage = canvas.createImage(oldProducer);
	      GraphicsUtils.waitForImage(oldImage);
	      canvas.setImage(oldImage);
	      //oldImage.flush();
	     //oldImage = null;
	   }

      String cmd = e.getActionCommand();
	  img = canvas.getImage();

      if(img == null) {
         return;
      }
      producer = img.getSource();

      oldProducer = producer;
      /* display filter setting dialog */
      sfd.createForm(cmd, producer);
      // clean up.
      img.flush();
   }

   /*
   * this method will receive the imagefitler whenver
   * the user is done with whatever he is doing and click
   * the 'Ok' button in the smartFilterdialogi
   */
   public void update(Observable o, Object object) {
      applyFilter((ImageFilter)object);
   }

   /*
   * a simple reset function to clear all saved data.
   */
   public void reset()
   {
	   producer = null;
       oldProducer = null;
   }

   /**
   * create new image based on data from JIMI filter.
   */
   protected void applyFilter(ImageFilter filter)
   {
      if(filter == null) {
         return;
      }
      /*
      * first time this method is called we have to initialize
      * the filtermap by getting the image producer from the source.
      */
      FilteredImageSource fis = new FilteredImageSource(producer, filter);
      Image new_image = canvas.createImage(fis);
	  GraphicsUtils.waitForImage(new_image);
      if(new_image != null) {
         canvas.setImage(new_image);
      }
      /* help the garbage man */
	  //NOTE: this is weird, if i flush and null ref this image it will
	  //cause flicker on the image in the canvas... `???? what's up with that?
      //new_image.flush();
      //new_image = null;
   }
}
