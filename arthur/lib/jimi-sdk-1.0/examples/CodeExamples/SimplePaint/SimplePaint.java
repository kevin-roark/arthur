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

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.component.JimiCanvas;

/**
 * A simple painting program to demonstrate saving images which are scribbled onto
 * a canvas using the mouse.
 */
public class SimplePaint implements ActionListener
{
   /** a frame to display the painting canvas in */
   protected Frame displayFrame;
   /** a JimiCanvas to render the image */
   protected PaintingCanvas displayCanvas;
   /** "Save" button */
   protected Button saveButton;
   /** the name of the file the image will be saved to */
   protected String filename;

   /**
    * Create a painting program, which will display its own frame for painting in.
    * @param filename the name of the file to save the image to
    * @param width the width of the drawing canvas
    * @param height the height of the drawing canvas
    */

   public SimplePaint(String filename, int width, int height)
   {
      this.filename = filename;

      // create the widgets
      displayFrame = new Frame("Jimi Paint");
      displayCanvas = new PaintingCanvas();
      saveButton = new Button("Save " + filename);

      // listen for "Save" events
      saveButton.addActionListener(this);

      // layout the components
      setupGUI();

      // show the frame
      displayFrame.show();

	  // frame closer
	  displayFrame.addWindowListener(new WindowAdapter()
									 {
										 public void windowClosing(WindowEvent we)
											 {
												 displayFrame.setVisible(false);
												 displayFrame.dispose();
												 System.exit(0);
											 }
									 });

      // create a blank image and show it in the canvas
      Image blank = displayFrame.createImage(width, height);
      displayCanvas.image = blank;
      displayFrame.pack();
   }

   /**
    * Setup and layout the components
    */
   protected void setupGUI()
   {
      // add and layout the canvas to the frame
      displayFrame.setLayout(new BorderLayout());
      displayFrame.add(BorderLayout.CENTER, displayCanvas);
      displayFrame.add(BorderLayout.SOUTH, saveButton);
   }

   /**
    * Save the picture.  This is where Jimi is used!
    */
   protected synchronized void saveImage()
   {
      try
      {
         // let Jimi do all the work of saving
         System.out.print("Saving picture... ");
         System.out.flush();

         // this line is the only involvement from Jimi!
         Jimi.putImage(displayCanvas.image, filename);

         System.out.println("Picture saved okay!");
      }
      // an exception probably means out of disk space, etc
      catch (Exception e)
      {
         System.out.println("Picture save failed: " + e.getMessage());
      }
   }

   /**
    * Method from ActionListener interface for when the "Save" button is pressed.
    */
   public void actionPerformed(ActionEvent event)
   {
      saveImage();
   }

   public static void main(String[] args)
   {
      new SimplePaint("painting.png", 300, 300);
   }

}

/**
 * A canvas to show the image in
 */
class PaintingCanvas extends Component
{
   public Image image;

   public PaintingCanvas()
   {
      new CanvasDrawer();
   }

   /**
    * Paint the image
    */
   public void paint(Graphics g)
   {
      if (image != null)
      {
         g.drawImage(image, 0, 0, this);
      }
   }

   /**
    * We want to resize to fit the whole image if possible.
    */
   public Dimension getPreferredSize()
   {
      return (image == null) ?
         new Dimension(0, 0) :
         new Dimension(image.getWidth(null), image.getHeight(null));
   }

   /**
    * A simple event-listening class to draw lines following dragging of the mouse
    */
   class CanvasDrawer extends MouseAdapter implements MouseMotionListener
   {
      protected PaintingCanvas canvas;
      protected int lastX, lastY;

      /**
       * Create a canvas-drawer to scribble into a given canvas.
       * @param canvas the canvas to scribble into
       */
       public CanvasDrawer()
       {
          this.canvas = PaintingCanvas.this;
          canvas.addMouseMotionListener(this);
          canvas.addMouseListener(this);
       }

     /**
      * When the mouse is dragged, we need to draw a line
      */
      public synchronized void mouseDragged(MouseEvent event)
      {
         Graphics graphics = canvas.image.getGraphics();
         graphics.setColor(Color.black);
         graphics.drawLine(lastX, lastY, event.getX(), event.getY());

		 Rectangle r = new Rectangle(lastX, lastY);
		 r.add(event.getX(), event.getY());
		 canvas.repaint(r.x, r.y, r.width + 1, r.height + 1);

         setPosition(event.getX(), event.getY());
      }

     /**
      * When the mouse is pressed we record where, so we have a reference point while it drags
      */
      public synchronized void mousePressed(MouseEvent event)
      {
         setPosition(event.getX(), event.getY());
      }

     /**
      * Empty implementation for MouseMotionListener interface
      */
      public void mouseMoved(MouseEvent event)
      {
      }

     /**
      * Set the last position of a mouse press or drag as a reference point
      * for the start of the next line.
      */
      protected void setPosition(int x, int y)
      {
         lastX = x;
         lastY = y;
      }
   }
}


