/*
 * To Consider:
 * file-saving only discovers format from the filename given.  would
 * a choice-box be better?
 * List-selection should probably be on single-click rather than double-click.
 * Control-panel looks very bland.  Could use a spruce-up.
 * dialogs might be better than the status bar for some messages, eg errors
 * -Luke
 */

package com.sun.jimi.tools.imageviewer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.component.JimiCanvas;

/**
 * Image viewing class.  On creation, a "control panel" frame will pop up for
 * loading images.  Any number of images can be loaded and saved through it.
 * @author Luke Gorrie
 * @author Chris Lucas
 * @version $Revision: 1.1.1.1 $
 **/
public class ImageViewer implements ActionListener
{

	/** Initial number of rows in the list. **/
	protected static final int INITIAL_LIST_ROWS = 7;

	/** Default width for control panel. **/
	protected static final int CONTROL_PANEL_WIDTH = 450;

	/** Frame for main panel. **/
	protected Frame mainFrame_;

	/** Frame for viewer panel. **/
	protected Frame viewerFrame_;

	/*
	 * Components for main control panel
	 */

	/** Container for controls. **/
	protected Panel controlPanel_;

	/** List for showing which images are loaded. **/
	protected List bufferList_;

	/** "Load" button. **/
	protected Button loadButton_;

	/** "Save" button. **/
	protected Button saveButton_;

	/** Status message. **/
	protected Label statusMessage_;



	/** Image cache. **/
	protected Hashtable imageCache_;

	/** Mappings between filenames stored in the List, and fully-qualified names. **/
	protected Hashtable imageNameMap_;

	/** MediaTracker for blocking on image loading. **/
	protected MediaTracker mediaTracker_;

	/** Canvas that actually displays the images. **/
	protected JimiCanvas displayCanvas_;

	protected String currentDirectory_;

	// instance initializer
	{
		// instantiate instance fields
		imageCache_ = new Hashtable();
		imageNameMap_ = new Hashtable();
	}

	public ImageViewer()
	{
		// initialise the control panel
		initPanel();

		// show the control panel's frame
		mainFrame_.pack();
		mainFrame_.setSize(CONTROL_PANEL_WIDTH, mainFrame_.getSize().height);
		mainFrame_.show();

	}

	protected void initPanel()
	{
		mainFrame_ = new Frame("JimiViewer control panel");
		mainFrame_.addWindowListener(new DieOnWindowClose());

		// used by waitImageLoad()
		mediaTracker_ = new MediaTracker(mainFrame_);

		viewerFrame_ = new Frame("JimiViewer display");
		viewerFrame_.addWindowListener(new WindowHider());
		// scrolling has some problems, so I'm only cropping
		displayCanvas_ = new JimiCanvas();
		displayCanvas_.setResizePolicy(JimiCanvas.CROP_AS_NECESSARY);
		displayCanvas_.setWillSizeToFit(true);

		viewerFrame_.setLayout(new BorderLayout());
		viewerFrame_.add("Center", displayCanvas_);

		controlPanel_ = new Panel();
		controlPanel_.setBackground(Color.lightGray);

		mainFrame_.setLayout(new BorderLayout());
		mainFrame_.add("Center", controlPanel_);

		// instantiate control panel's widgets
		bufferList_ = new List(INITIAL_LIST_ROWS);
		loadButton_ = new Button("Load");
		saveButton_ = new Button("Save");
		statusMessage_ = new Label("Ready.");

		// register event listeners
		bufferList_.addActionListener(this);
		loadButton_.addActionListener(this);
		saveButton_.addActionListener(this);

		// gridbag layout code for control panel
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		controlPanel_.setLayout(layout);

		c.weightx = c.weighty = 1.0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		//c.ipadx = 200;

		layout.setConstraints(bufferList_, c);
		controlPanel_.add(bufferList_);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.weighty = 0;
		c.gridwidth = 1;

		layout.setConstraints(statusMessage_, c);
		controlPanel_.add(statusMessage_);

		c.ipadx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.SOUTHEAST;

		c.weighty = 0;

		layout.setConstraints(loadButton_, c);
		controlPanel_.add(loadButton_);

		c.weightx = 0;

		layout.setConstraints(saveButton_, c);
		controlPanel_.add(saveButton_);

	}

	/**
	 * <strong>Not implemented!</strong>
	 **/
	protected void saveImage()
	{
		FileDialog dialog = new FileDialog(mainFrame_, "Image saver");
		dialog.setMode(FileDialog.SAVE);
		dialog.show();
		String filename = dialog.getDirectory() + File.separatorChar + dialog.getFile();
		String file = dialog.getFile();

		setStatus("Saving " + file + "...");

		try {
			writeImage(filename);
			setStatus(file + " saved successfully.");
		}
		catch (IOException e)
		{
			setStatus(e.toString());
		}
	}

	protected void writeImage(String filename) throws IOException
	{
		String encoding;
		if (filename.indexOf(".") != -1)
			encoding = filename.substring(filename.lastIndexOf(".") + 1);
		else
			throw new IOException("Unable to detect encoding format.");

		try {
			Jimi.putImage(encoding, displayCanvas_.getImage(), filename);
		}
		catch (JimiException e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * Pops up a file dialog prompting the user to load an image.
	 **/
	protected synchronized void loadImage()
	{
		// find out which image to load
		FileDialog dialog = new FileDialog(mainFrame_, "Image loader");
		if (currentDirectory_ != null)
			dialog.setDirectory(currentDirectory_);

		dialog.show();

		String filename = dialog.getDirectory() + File.separatorChar + dialog.getFile();
		String file = dialog.getFile();

		// if the load was cancelled in the dialog
		if (file == null)
			return;

		currentDirectory_ = dialog.getDirectory();

		setStatus("Loading " + file + "...");
		try {
			// load and display the image
			openImage(filename);
			setStatus(file + " loaded successfully.");
		}
		// raised if it was an invalid image file, etc
		catch (IOException e) {
			setStatus(e.toString());
			return;
		}

		// map filename to fully-qualified name
		imageNameMap_.put(file, filename);

		// add to list
		bufferList_.add(file);
		bufferList_.select(bufferList_.getItemCount() - 1);

	}

	/**
	 * Opens an image and makes sure it is fully loaded.
	 * @exception If something goes wrong while loading the image.
	 * @return The image.
	 */
	protected synchronized void openImage(String filename) throws IOException
	{
		Image image = null;

		// does the image exist in the cache?
		if (imageCache_.containsKey(filename))
			image = (Image)imageCache_.get(filename);

		// read entire image
		if (image == null) {
			image = Jimi.getImage(filename);
			waitImageLoad(image);

			// was there an error in image loading?
			if ((image.getWidth(null) < 0) || (image.getHeight(null) < 0))
				throw new IOException("An exception was raised while opening the image.");

			// store in cache
			imageCache_.put(filename, image);
		}

		// commit image to display
		setImage(image);
	}

	protected synchronized void setImage(Image image)
	{
		//displayCanvas_.setImage(image);

		// JimiCanvas isn't coping with multiple setImage() calls too well,
		// creating a new one each time as a work-around
		displayCanvas_ = new JimiCanvas(image);
		displayCanvas_.setResizePolicy(JimiCanvas.CROP_AS_NECESSARY);
		displayCanvas_.setWillSizeToFit(true);
		viewerFrame_.removeAll();
		viewerFrame_.add("Center", displayCanvas_);
		viewerFrame_.pack();
		viewerFrame_.show();
	}

	/*
	 * ActionListener implementation
	 */

	public synchronized void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source == loadButton_)
			loadImage();

		if (source == saveButton_)
			saveImage();

		if (source == bufferList_){
			// get fully-qualified filename
			String filename = (String)imageNameMap_.get(e.getActionCommand());
			try {
				openImage(filename);
			}
			catch (IOException ioe) {
				// image is in cache, so this will never arise
			}
		}
	}

	/**
	 * Block on image loading.
	 **/
	protected void waitImageLoad(Image image)
	{
		mediaTracker_.addImage(image, 0);
		try {
			mediaTracker_.waitForAll();
		}
		catch (InterruptedException e) {
			// ignore
		}
	}

	/**
	 * Show a message in the status line.
	 **/
	protected void setStatus(String status)
	{
		statusMessage_.setText(status);
	}

	/**
	 * Kills the program via System.exit() when the window is closed.
	 **/
	class DieOnWindowClose extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			e.getWindow().dispose();
			System.exit(0);
		}
	}

	class WindowHider extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			e.getWindow().hide();
		}
	}

}

