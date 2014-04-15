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
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import com.sun.jimi.core.filters.*;

public class SmartFilterDialog extends Observable implements ActionListener
{
   /* build forms in this dialog */
   private Dialog dialog;
   /* frame to use as parent for dialog */
   private Frame parent;
   /* misc stuff */
   private Panel group;
   private String filter;
   private ImageProducer producer;
   private ImageFilter imagefilter;
   private Button ok, cancel;
   private Label label;
   private TextField input, input2;
   //private Checkbox cbox; for ColorReducer dither on/off
   private List list;
   /* color and fliptype mappings */
   final static Hashtable colormap = new Hashtable(13);
   static
   {
      colormap.put("Black", Color.black);
      colormap.put("Blue", Color.blue);
      colormap.put("Cyan", Color.cyan);
      colormap.put("DarkGray", Color.darkGray);
      colormap.put("Gray", Color.gray);
      colormap.put("Green", Color.green);
      colormap.put("LightGray", Color.lightGray);
      colormap.put("Magenta", Color.magenta);
      colormap.put("Orange", Color.orange);
      colormap.put("Pink", Color.pink);
      colormap.put("Red", Color.red);
      colormap.put("White", Color.white);
      colormap.put("Yellow", Color.yellow);
   }

   final static Hashtable flipmap = new Hashtable(7);
   static
   {
      flipmap.put("Flip.FLIP_CCW", new Integer(Flip.FLIP_CCW));
      flipmap.put("Flip.FLIP_CW", new Integer(Flip.FLIP_CW));
      flipmap.put("Flip.FLIP_LR", new Integer(Flip.FLIP_LR));
      flipmap.put("Flip.FLIP_NULL", new Integer(Flip.FLIP_NULL));
      flipmap.put("Flip.FLIP_R180", new Integer(Flip.FLIP_R180));
      flipmap.put("Flip.FLIP_TB", new Integer(Flip.FLIP_TB));
      flipmap.put("Flip.FLIP_XY", new Integer(Flip.FLIP_XY));
   }

   public SmartFilterDialog(Frame parent)
   {
      dialog = new Dialog(parent, "Filter Settings", true);
      this.parent = parent;
	  
      Panel south = new Panel();

      ok = new Button("Ok");
      ok.addActionListener(this);
      south.add(ok);

      cancel = new Button("Cancel");
      cancel.addActionListener(this);
      south.add(cancel);
	  
	  south.setBackground(SystemColor.window);
	  south.setForeground(SystemColor.desktop);
	  
      dialog.add(south, BorderLayout.SOUTH);
   }

   public void createForm(String filter, ImageProducer producer)
   {
      this.filter = filter;
      this.producer = producer;
      /* base panel*/
      group = new Panel();
	  group.setBackground(SystemColor.window);
	  group.setForeground(SystemColor.desktop);
	  
      if(filter.equals("Enlarge"))
      {
         label = new Label("Enlarge Level");
         group.add(label);
         input = new TextField(4);
         group.add(input);
      }
      else
      // Flip handled at bottom of function
      if(filter.equals("Gamma"))
      {
         label = new Label("Gamma Level");
         group.add(label);
         input = new TextField(4);
         group.add(input);
      }
      else
      if(filter.equals("Margin"))
      {
         group.setLayout(new BorderLayout(2,2));

         Panel north = new Panel();
            north.setLayout(new BorderLayout(2,2));
            label = new Label("Margin Size", Label.LEFT);
            north.add(label, BorderLayout.WEST);
            input = new TextField(4);
            north.add(input, BorderLayout.EAST);
         group.add(north, BorderLayout.NORTH);

         label = new Label("Margin Color");
         group.add(label, BorderLayout.CENTER);

            list = new List();
            list.addItem("Black");
            list.addItem("Blue");
            list.addItem("Cyan");
            list.addItem("DarkGray");
            list.addItem("Gray");
            list.addItem("Green");
            list.addItem("LightGray");
            list.addItem("Magenta");
            list.addItem("Orange");
            list.addItem("Pink");
            list.addItem("Red");
            list.addItem("White");
            list.addItem("Yellow");

         group.add(list, BorderLayout.SOUTH);
      }
      else
      if(filter.equals("Oil"))
      {
         label = new Label("Oil Level");
         group.add(label);
         input = new TextField(4);
         group.add(input);
      }
      else
      if(filter.equals("Rotate"))
      {
         label = new Label("Rotate Angle");
         group.add(label);
         input = new TextField(4);
         group.add(input);
         //new Rotate(producer, 43.0d);
      }
      else
      if(filter.equals("ScaleCopy"))
      {
         label = new Label("Scale Level");
         group.add(label);
         input = new TextField(4);
         group.add(input);
         //new ScaleCopy(producer, 3);
      }
      else
      if(filter.equals("Shear"))
      {
         label = new Label("Shear Angle");
         group.add(label);
         input = new TextField(4);
         group.add(input);
         //new Shear(producer, 45.0d);
      }
      else
      if(filter.equals("Shrink"))
      {
         label = new Label("Shrink Level");
         group.add(label);
         input = new TextField(4);
         group.add(input);
         //new Shrink(producer, 3);
      }
      else
      if(filter.equals("Smooth"))
      {
         label = new Label("Smooth Level");
         group.add(label);
         input = new TextField(4);
         group.add(input);
      }
      else
      if(filter.equals("Tile"))
      {
         group.setLayout(new BorderLayout(2,2));

         Panel north = new Panel();
         label = new Label("Number of Vertical Tiles");
            north.add(label);
            input = new TextField(4);
            north.add(input);
         group.add(north, BorderLayout.NORTH);

         Panel south = new Panel();
         label = new Label("Number of Horizontal Tiles");
            south.add(label);
            input2 = new TextField(4);
            south.add(input2);
         group.add(south, BorderLayout.CENTER);
      }
      else
      {
         // this handles any non-arg (no dialog needed) filters.
         setFilterFromName(filter);
         return;
      }

      /* NOT YET IMPLEMENTED */
      //{ "Color Reducer", new com.sun.jimi.core.util.ColorReducer(256, true) }
      //{ "Composite Filter", new Object() }

      /* add grouping to center of the dialog */
      dialog.add(group, BorderLayout.CENTER);
      /* size, center and display */
      dialog.pack();
      center();
      dialog.setVisible(true);
   }

   public void actionPerformed(ActionEvent e)
   {
      Object source = e.getSource();

      if(source == ok)
      {
         setFilterFromName(filter);
         dialog.remove(group);
         dialog.setVisible(false);
         dialog.dispose();
      }
      else
      if(source == cancel)
      {
         dialog.remove(group);
         dialog.setVisible(false);
         dialog.dispose();
      }
   }

   /* return filter based on */
   public void setFilterFromName(String name)
   {
      if(filter.equals("Edge Detect"))
      {
         imagefilter = new EdgeDetect(producer);
      }
      else
      if(filter.equals("Enlarge"))
      {
         int enlarge = Integer.parseInt(input.getText());
         imagefilter = new Enlarge(producer, enlarge);
      }
      else
      // flipfilter detection.
      if(filter.equals("Flip.FLIP_CCW"))
      {
         int flip = ((Integer)flipmap.get(name)).intValue();
         imagefilter = new Flip(producer, flip);
      }
      else
      if(filter.equals("Flip.FLIP_CW"))
      {
         int flip = ((Integer)flipmap.get(name)).intValue();
         imagefilter = new Flip(producer, flip);
      }
      else
      if(filter.equals("Flip.FLIP_LR"))
      {
         int flip = ((Integer)flipmap.get(name)).intValue();
         imagefilter = new Flip(producer, flip);
      }
      else
      if(filter.equals("Flip.FLIP_NULL"))
      {
         int flip = ((Integer)flipmap.get(name)).intValue();
         imagefilter = new Flip(producer, flip);
      }
      else
      if(filter.equals("Flip.FLIP_R180"))
      {
         int flip = ((Integer)flipmap.get(name)).intValue();
         imagefilter = new Flip(producer, flip);
      }
      else
      if(filter.equals("Flip.FLIP_TB"))
      {
         int flip = ((Integer)flipmap.get(name)).intValue();
         imagefilter = new Flip(producer, flip);
      }
      else
      if(filter.equals("Flip.FLIP_XY"))
      {
         int flip = ((Integer)flipmap.get(name)).intValue();
         imagefilter = new Flip(producer, flip);
      }
      else
      if(filter.equals("Gamma"))
      {
         /*
         * this one can take a double value,
         * multple consturctors.
         */
         int gamma = Integer.parseInt(input.getText());
         imagefilter = new Gamma(producer, gamma);
      }
      else
      if(filter.equals("Gray"))
      {
         imagefilter = new Gray();
      }
      else
      if(filter.equals("Invert"))
      {
         imagefilter = new Invert(producer);
      }
      else
      if(filter.equals("Margin"))
      {
         int margin = Integer.parseInt(input.getText());
         Color color = (Color)colormap.get(list.getSelectedItem());
         imagefilter = new Margin(producer, color, margin);
      }
      else
      if(filter.equals("Oil"))
      {
         int oil = Integer.parseInt(input.getText());
         imagefilter = new Oil(producer, oil);
      }
      else
      if(filter.equals("Rotate"))
      {
         /* this one can take a double value */
         int rotate = Integer.parseInt(input.getText());
         imagefilter = new com.sun.jimi.core.filters.Rotate(rotate);
      }
      else
      if(filter.equals("ScaleCopy"))
      {
         /* this one can take a double value and two constructors */
         int scalecopy = Integer.parseInt(input.getText());
         imagefilter = new ScaleCopy(producer, scalecopy);
      }
      else
      if(filter.equals("Shear"))
      {
         /* this one can take a double value */
         int shear = Integer.parseInt(input.getText());
         imagefilter = new Shear(producer, shear);
      }
      else
      if(filter.equals("Shrink"))
      {
         int shrink = Integer.parseInt(input.getText());
         imagefilter = new Shrink(producer, shrink);
      }
      else
      if(filter.equals("Smooth"))
      {
         int smooth = Integer.parseInt(input.getText());
         imagefilter = new Smooth(producer, smooth);
      }
      else
      if(filter.equals("Tile"))
      {
         /*
         * create an image from the producer to retrieve
         * image height and image width :)
         */
         Image img = parent.createImage(producer);
		 com.sun.jimi.core.util.GraphicsUtils.waitForImage(img);
         /* need an image width/height value here */
         int imgH = img.getHeight(null);
         int imgW = img.getWidth(null);
         int vTiles = Integer.parseInt(input.getText());
         int hTiles = Integer.parseInt(input2.getText());
         imagefilter = new Tile(producer, imgW * hTiles, imgH * vTiles);
         // garbage
         img.flush();
      }

      /* aigth, filter settings ok, let's tell mom. */
      setChanged();
      notifyObservers(imagefilter);
   }

   /* center dialog based on parent location */
   public void center()
   {
       Point pos = parent.getLocationOnScreen();
       int ownerHeight = parent.getSize().height;
       int ownerWidth = parent.getSize().width;
       int cx = pos.x + (ownerWidth / 2 - (dialog.getSize().width / 2));
       int cy = pos.y + (ownerHeight /2 - (dialog.getSize().height / 2));
       dialog.setLocation(cx, cy);
   }
}
