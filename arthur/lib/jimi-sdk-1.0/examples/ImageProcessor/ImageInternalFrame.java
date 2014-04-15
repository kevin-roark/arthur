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

import com.sun.jimi.core.component.JimiCanvasLW;
import com.sun.jimi.core.util.GraphicsUtils;
import com.sun.jimi.core.util.ColorReducer;
import com.sun.jimi.core.JimiException;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * An InternalFrame for rendering an image and applying filters to it.
 **/
public class ImageInternalFrame extends JInternalFrame implements ImageObserver
{

	// original image
	private Image originalImage_;
	// canvas to render image in
	private JimiCanvasLW jCanvas_;

	// frame title
	private String title_;

	// old image (added to save the old image before a filter is applied)
	private Image old_image_;

	private JFrame root_;

	public ImageInternalFrame(JFrame root, String title, Image image)
	{
		super(title);

		root_ = root;

		// store the title to be able to restore it
		title_ = title;

		originalImage_ = image;

		// initialize widgets
		initFrame();
		initCanvas();

		pack();
	}

	/**
	 * Set the image to be displayed.
	 * @param image The image to display.
	 **/
	public synchronized void setImage(Image image)
	{
		originalImage_ = image;
		setCanvasImage(image);
	}

	/**
	 * Get the Image being displayed.
	 * @return The Image.
	 **/
	public Image getImage()
	{
		return jCanvas_.getImage();
	}

	/**
	 * Applies a filter to the image current image and displays the result.
	 * @param filter The ImageFilter to apply.
	 **/
	public synchronized void applyFilter(ImageFilter filter)
	{
		try {
			setTitle("Applying..");
			root_.setCursor(Cursor.WAIT_CURSOR);
			old_image_ = getImage();
			
			ImageProducer source = new FilteredImageSource(old_image_.getSource(), filter);
			Image new_image = createImage(source);
			GraphicsUtils.waitForImage(new_image);
			
			setCanvasImage(new_image);
			setTitle(title_);
		}
		finally {
			root_.setCursor(Cursor.getDefaultCursor());
		}
	}

	public boolean imageUpdate(Image img, int infoflasgs, int x, int y, int width, int height)
	{
		setCanvasImage(img);
		setTitle(title_);
		// if the previous image was the result of a filter, flush it
		if (old_image_ != originalImage_)
			old_image_.flush();
        return false;
	}

	/**
	 * Perform color reduction.
	 **/
	public synchronized void reduceColors(int colors, boolean dither)
	{
		setTitle("Applying..");
		ColorReducer reducer = new ColorReducer(colors, dither);
		Image img = null;
		try {
			img = reducer.getColorReducedImage(getImage());
		}
		catch (JimiException e)
		{
			// don't care
		}
		if (img != null)
			setCanvasImage(img);
		setTitle(title_);
	}

	/**
	 * Revets to the original image, effectively undoing all filters.
	 **/
	public synchronized void revertImage()
	{
		setCanvasImage(originalImage_);
	}

	/**
	 * Flush out all data, forgetting references and rendering the frame inoperable.
	 */
	public void flush()
	{
		if (originalImage_ != null) {
			originalImage_.flush();
			originalImage_ = null;
		}
		if (old_image_ != null) {
			old_image_.flush();
			old_image_ = null;
		}
		jCanvas_ = null;
		removeAll();
	}

	/**
	 * Sets various properties in 'this' JInternalFrame.
	 **/
	protected void initFrame()
	{
		setResizable(false);
		setClosable(true);
		setIconifiable(false);
	}

	/**
	 * Initializes the JimiCanvas and adds it to 'this' Frame.
	 **/
	protected void initCanvas()
	{
		// create and configure canvas
		jCanvas_ = new JimiCanvasLW();
		jCanvas_.setJustificationPolicy(JimiCanvasLW.CENTER);
		jCanvas_.setResizePolicy(JimiCanvasLW.CROP_AS_NECESSARY);
		jCanvas_.setWillSizeToFit(true);
		jCanvas_.setImage(originalImage_);

		// add the canvas to the container
		Container content_pane = getContentPane();
		content_pane.setLayout(new BorderLayout());
		content_pane.add(BorderLayout.CENTER, jCanvas_);
	}

	/**
	 * Sets the image being displayed in the underlying JimiCanvas.
	 **/
	protected void setCanvasImage(Image image)
	{
		jCanvas_.setImage(image);
		validateTree();
		jCanvas_.repaint();
		getContentPane().setSize(jCanvas_.getPreferredSize().width, jCanvas_.getPreferredSize().height);
		pack();
	}

}

