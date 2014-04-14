//Title:       ColorReducerDemo JIMI Example.
//Version:     1.0
//Copyright:   Copyright (c) 1998 Sun Microsystems, Inc.
//Author:      Thomas Isaksen ( thomas@activated.com )
//Description: A simple applet to show the use of the ColorReducer filter.

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;
import java.util.Vector;
import java.util.Hashtable;
import java.io.*;

import com.sun.jimi.core.*;
import com.sun.jimi.core.filters.*;
import com.sun.jimi.core.component.*;
import com.sun.jimi.core.util.*;

public class ColorReducerDemo extends Applet implements ActionListener, ItemListener
{
   Button btnFilter, btnClear;
   JimiCanvas canvas;
   Image image;
   int colors = 256;
   boolean dither = true;
   Hashtable imagemap;

   public void init()
   {
      this.setLayout(new BorderLayout(0,0));

      // load image
      URL url = null;
      try {
          url = new URL(getDocumentBase(), "jimilogo.gif");
      }
      catch(MalformedURLException e) { showStatus(e.toString()); }
      // jimi goes to work
      image = Jimi.getImage(url);
      MediaTracker tracker = new MediaTracker(this);
      tracker.addImage(image, 0);
      try {
        tracker.waitForID(0);
      }
      catch(InterruptedException e) { System.err.println(e); }

      // we need a jimicanvas to render on
      canvas = new JimiCanvas();
      // set some display polycies
      canvas.setJustificationPolicy(JimiCanvas.CENTER);
      canvas.setResizePolicy(JimiCanvas.SCROLL);
      canvas.setWillSizeToFit(true);
      canvas.setImage(image);
      this.add(canvas, BorderLayout.CENTER);

      // click this one for action :-)
      Panel tools = new Panel(new BorderLayout());
      tools.setBackground(Color.gray);
          Panel pnlButton = new Panel();
          /* buttons */
          btnFilter = new Button("Apply Filter");
          btnFilter.addActionListener(this);
          pnlButton.add(btnFilter);
          btnClear = new Button("Reset");
          btnClear.addActionListener(this);
          pnlButton.add(btnClear);
      tools.add(pnlButton, BorderLayout.NORTH);
      /* file selector */
      Choice chooser = new Choice();
      chooser.addItemListener(this);
      /* fetch filenames */
      boolean one = true;
      boolean two = false;
      imagemap = new Hashtable(10);
      String filename[] = getImageNames();
      for(int i=0; i<filename.length; i++)
      {
        String item = filename[i].trim();
        if(item.startsWith("##"))
        {
            two = true;
            one = false;
            chooser.addItem("    "+item);
        }
        else
        if(item.startsWith("#"))
        {
            one = true;
            two = false;
            /* this is a category, mark it as such */
            chooser.addItem(item);
        }
        else
        {
            /* strip path and map to short filename */
            String shortname = item.substring(item.lastIndexOf("/")+1);
            imagemap.put(shortname, item);
            if(one) {
                chooser.addItem("    "+shortname);
            }
            else
            if(two) {
                chooser.addItem("        "+shortname);
            }
        }
      }
      tools.add(chooser, BorderLayout.CENTER);
      this.add(tools, BorderLayout.SOUTH);
   }

   private String[] getImageNames()
   {
       String fn[] = null;
       URL url = null;
       try
       {
           url = new URL(getDocumentBase(), "files.txt");
           URLConnection uc = url.openConnection();
           BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
           Vector files = new Vector();
           String filename = null;
           while((filename = br.readLine()) != null)
           {
              files.addElement(filename);
           }
           fn = new String[files.size()];
           files.copyInto(fn);
           /* help the garbage man */
           files = null;
           url = null;
           uc = null;
           filename = null;
       }
       catch(FileNotFoundException e) { System.err.println(e); }
       catch(IOException e) { System.err.println(e); }
       return fn;
   }

   public void itemStateChanged(ItemEvent e)
   {
      String file = ((String)e.getItem()).trim();
      if(file.startsWith("#") || file.startsWith("##"))
      {
        showStatus("Sorry pal, Select an Image instead.");
        return;
      }

      String path = (String)imagemap.get(file);
      /* if the path is in in the map we got ourselfs a problem */
      if(path == null)
      {
        showStatus("Something is wrong with the path "+path);
        return;
      }
      setBusy("Loading "+file+" ... Please wait!", canvas.getGraphics());
      URL url = null;
      try {
         url = new URL(getDocumentBase(), path);
      }
      catch(MalformedURLException bug) { System.err.println(bug); }
      image = Jimi.getImage(url);
      MediaTracker tracker = new MediaTracker(this);
      tracker.addImage(image, 0);
      try {
        tracker.waitForID(0);
      }
      catch(InterruptedException ie) { showStatus(ie.toString()); }
      canvas.setImage(image);
   }

   public void actionPerformed(ActionEvent e)
   {
      Object source = e.getSource();
      if(source == btnFilter)
      {
        setBusy("Processing... please wait!", canvas.getGraphics());
        if(colors <= 1) {
            colors = 32;
        }
        /* create reducer objecty */
        ColorReducer reducer = new ColorReducer(colors, dither);
        // get new image
        try
        {
            Image new_image = reducer.getColorReducedImage(image);
            canvas.setImage(new_image);
            new_image.flush();
            new_image = null;
        }
        catch(JimiException je) { System.err.println(je); }
        /* reduce colors by 50% for each click.*/
//        colors -= colors/2;
		colors /= 2;
        /*
        * set dither, if it is true it will
        * be set to false and vice versa..
        */
        dither = !dither;
      }
      else
      if(source == btnClear)
      {
        clear();
        colors = 32;
      }
   }

   protected void setBusy(String msg, Graphics screen)
   {
       FontMetrics fm = getFontMetrics(getFont());
       screen.setColor(Color.black);
       screen.fillRect(0, 0, canvas.getSize().width, canvas.getSize().height);
       screen.setColor(Color.red);
       int stringWidth = fm.stringWidth(msg);
       int stringHeight = fm.getHeight();
       int cx = (getSize().width / 2) - (stringWidth / 2);
       int cy = (getSize().height / 2) - (stringHeight / 2);
       screen.drawString(msg, cx, cy);
   }

   /* set the original image */
   protected void clear() {
      canvas.setImage(image);
   }
}
