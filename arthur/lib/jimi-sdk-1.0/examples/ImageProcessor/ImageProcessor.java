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

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.*;

/**
 * ImageProcessor class.  Displays a frame to load images into, also provides
 * a facility to apply ImageFilters to the images.
 * <p>
 * Opens a new root frame and provides its own user interface for all
 * interactions.
 **/


public class ImageProcessor
{

	private static final Color BACKGROUND_COLOR = new Color(0x30, 0x30, 0x70);
	private static final Dimension TOOL_PANEL_SIZE = new Dimension(180, 350);

	/** Menu items. **/
	private static final String MENU_FILE = "File", MENU_HELP = "Help", MENU_OPEN = "Open..", MENU_SAVE = "Save..", MENU_EXIT = "Exit",
	                            MENU_ABOUT = "About" , MENU_INSTRUCTIONS = "Instructions";

	/** Root frame. **/
	protected JFrame rootFrame_;

	/** MediaTracker used to block on image loading. **/
	protected MediaTracker mediaTracker_;

	/** Object to track which internal frame is selected. **/
	protected FocusTracker focusTracker_;

	/** Toolbar panel responsible for handling filters. **/
	protected ToolPanel toolPanel_;

	/** Pane in root frame. **/
	protected JDesktopPane rootPane_;

	/** Current working directory. **/
	private String currentDirectory_;

	// used to decide where the next internal frame will be located
	private int nr_frames;

	private static final String BUGS_WARNING =
	"Due to bugs in Swing 1.1, there are two pieces of abnormal behavior:\n" +
	"  The Toolbar will steal focus from the selected image.\n" +
	"  The \"Apply\" button will only work correctly if the Toolbar has focus.\n";

	public ImageProcessor()
	{
		focusTracker_ = new FocusTracker();
		initRootFrame();
		initMenu();
		initToolPanel();
		mediaTracker_ = new MediaTracker(rootFrame_);
		rootFrame_.setVisible(true);
		JOptionPane.showMessageDialog(rootFrame_, BUGS_WARNING, "Note about Swing problems",
									  JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Initialize the root JFrame
	 **/
	protected void initRootFrame()
	{
		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screen_size.width * 3 / 4;
		int height = screen_size.height * 3 / 4;

		rootPane_ = new JDesktopPane();
		rootPane_.setPreferredSize(new java.awt.Dimension(width * 3, height * 3));
		rootPane_.setBackground(BACKGROUND_COLOR);
		JScrollPane temp = new JScrollPane(rootPane_, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		rootFrame_ = new JFrame("Image Processor");
		rootFrame_.setContentPane(temp);
		rootFrame_.addWindowListener(new WindowCloser());

		// size and place root frame appropriately

		rootFrame_.setBounds((screen_size.width - width) / 2,
												 (screen_size.height - height) / 2, width, height);
	}

	/**
	 * Initialize menubar.
	 **/
	protected void initMenu()
	{
		// create File menu
		JMenu menu = new JMenu(MENU_FILE);

		// menu item wrappers
		JMenuItem open_item = new JMenuItem(MENU_OPEN);
		JMenuItem save_item = new JMenuItem(MENU_SAVE);
		JMenuItem exit_item = new JMenuItem(MENU_EXIT);

		// listen for ActionEvents indicating the items have been chosen
		ActionListener listener = new MenuHandler();
		open_item.addActionListener(listener);
		save_item.addActionListener(listener);
		exit_item.addActionListener(listener);

		// add items to menu
		menu.add(open_item);
		menu.add(save_item);
		menu.addSeparator();
		menu.add(exit_item);


		// create a menubar and put it in the frame
		JMenuBar menu_bar = new JMenuBar();
		menu_bar.add(menu);

		menu = new JMenu(MENU_HELP);
		JMenuItem about_item = new JMenuItem(MENU_ABOUT);
		about_item.addActionListener(new ActionListener()  {

			public void actionPerformed(ActionEvent e)  {
				JOptionPane.showMessageDialog(rootFrame_,
					"This demonstration is intended to show some of the image loading,\n" +
					"image saving, and image processing power of JIMI.",
					"About the demo",
					JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menu.add(about_item);
		JMenuItem instructions = new JMenuItem(MENU_INSTRUCTIONS);
		instructions.addActionListener(new ActionListener()  {

			public void actionPerformed(ActionEvent e)  {
				JOptionPane.showMessageDialog(rootFrame_,
					"In order to operate on an image, you must first ensure that the frame\n" +
					"which displays is the frame with the focus (on top).",
					"Instructions",
					JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menu.add(instructions);
		menu_bar.add(menu);

		//menu.addActionListener(new MenuHandler());
		rootFrame_.setJMenuBar(menu_bar);
	}

	/**
	 * Initialize the toolbar panel.
	 **/
	protected void initToolPanel()
	{
		toolPanel_ = new ToolPanel(this);

		// create an internal frame to place it in
		JInternalFrame frame = new JInternalFrame("Tool Panel");
		frame.setIconifiable(false);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.CENTER, toolPanel_);
		frame.pack();

		// fixed size and starting location
		frame.setSize(new Dimension(TOOL_PANEL_SIZE.width, TOOL_PANEL_SIZE.height));
		rootPane_.add(frame, JLayeredPane.PALETTE_LAYER);
		Dimension pane_size = rootFrame_.getSize();
		frame.setLocation(pane_size.width - TOOL_PANEL_SIZE.width - 30, 10);

		try{
			frame.setSelected(true);
		}
		catch (PropertyVetoException e)
		{
			// don't care
		}
	}

	public ImageInternalFrame getSelectedImageFrame()
	{
		return (ImageInternalFrame)focusTracker_.getFocusHolder();
	}

	/**
	 * Opens a new Image and places it in its own internal frame.  A FileDialog is
	 * used to determine the filename to use.
	 **/
	public synchronized void openImage()
	{
		// create a FileDialog
		FileDialog dialog = new FileDialog(rootFrame_, "Load Image");
		dialog.setMode(FileDialog.LOAD);

		// remember PWD
		if (currentDirectory_ != null)
			dialog.setDirectory(currentDirectory_);

		// prompt user
		dialog.show();

		String short_name = dialog.getFile();

		// if cancelled
		if (short_name == null)
			return;

		// store PWD
		currentDirectory_ = dialog.getDirectory();

		openImage(currentDirectory_ + File.separatorChar + short_name);
	}

	/**
	 * Opens an image and places it in its own internal frame.
	 * @param filename The name of the image file to load.
	 **/
	public void openImage(String filename)
	{
		Image img;
		// set cursor to WAIT_CURSOR for the duration of the image loading
		rootFrame_.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try
		{
			img = loadImage(filename);
		}
		// if the file fails to load, show an error dialog
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(rootFrame_, "Error: " + e.getMessage(),
																		"Error loading \""+filename+"\"", JOptionPane.ERROR_MESSAGE);
			return;
		}
		finally
		{
			rootFrame_.setCursor(Cursor.getDefaultCursor());
		}

		// determine the unqualified filename
		String short_name = filename;
		if (short_name.indexOf(File.separator) != -1)
			short_name = short_name.substring(short_name.lastIndexOf(File.separator) + 1);

		// image was loaded ok - create an internal frame to put it in
		ImageInternalFrame f = new ImageInternalFrame(rootFrame_, short_name, img);
		f.addInternalFrameListener(focusTracker_);
		focusTracker_.setFocusHolder(f);
		// place frame
		int x, y;
		x = y = 20 * nr_frames;
		if ((x >= (rootFrame_.getSize().width - 20)) ||
				(y >= (rootFrame_.getSize().height - 20)))
		{
			nr_frames = 0;
			x = y = 0;
		}
		else
		{
			nr_frames++;
		}
		f.setLocation(x, y);
		f.setDoubleBuffered(false);
		rootPane_.add(f, JLayeredPane.DEFAULT_LAYER);
		f.toFront();
		try {
			f.setSelected(true);
		} catch (Exception e) {}
	}

	private static final String LICENSE_ADVICE =
	"The format you are attempting to save in is not available.\n";


	protected void saveImage()
	{
		// pull image
		Image image = getSelectedImageFrame().getImage();
		FileDialog dialog = new FileDialog(rootFrame_, "Save Image");
		dialog.setMode(FileDialog.SAVE);

		// set a current directory
		if (currentDirectory_ != null)
			dialog.setDirectory(currentDirectory_);

		// prompt user for directory
		dialog.show();

		String shortname = dialog.getFile();

		// if the save was cancelled
		if (shortname == null)
			return;

		try
		{
			saveImage(dialog.getDirectory() + File.separator + shortname,
								image);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(rootFrame_, LICENSE_ADVICE, "Error: " + e.getMessage(),
										  JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	/**
	 * Saves an image to a specified filename using Jimi.
	 * @param filename The filename to write the image to.  Encoding format
	 * is determined from filename, or defaults to PNG.
	 * @param image The image to save.
	 **/
	protected void saveImage(String filename, Image image) throws IOException
	{
		try
		{
			Jimi.putImage(image, filename);
		}
		catch (JimiException jimiException)
		{
			throw new IOException("Error saving image: " + jimiException.getMessage());
		}
	}

	/**
	 * Read an Image through Jimi and block until it is fully loaded.
	 * @param filename The filename to read from.
	 * @return The Image loaded.
	 **/
	protected Image loadImage(String filename) throws IOException {

		Image image = Jimi.getImage(filename);
		mediaTracker_.addImage(image, 0);

		try
		{
			mediaTracker_.waitForAll();
		}
		catch(InterruptedException e) {
			//Ignore
		}

		mediaTracker_.removeImage(image);

		// was there an error in image loading?
		if ((image.getWidth(null) < 0) || (image.getHeight(null) < 0))
		{
			throw new IOException("Image loading failed.");
		}

		return image;
	}

	/**
	 * Small class to handle the File menu.
	 **/
	private class MenuHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String action = e.getActionCommand();
			// if "Open" was chosen, load an image
			if (action.equals(MENU_OPEN))
				ImageProcessor.this.openImage();
			else if(action.equals(MENU_EXIT))  {
				System.runFinalizersOnExit(true);
				System.exit(0);
			} else
				ImageProcessor.this.saveImage();
		}
	}

	/**
	 * Window-Listener to exit the program if the main frame is closed.
	 **/
	private class WindowCloser extends WindowAdapter
	{
		public void windowClosing(WindowEvent event)
		{
			System.runFinalizersOnExit(true);
			System.exit(0);
		}
	}
}

/**
 * Tracks which internal frame has focus and should be the target of actions.
 * It may be more sensible to enumerate the internal frames and just see which is selected.
 **/
class FocusTracker extends InternalFrameAdapter
{
	private JInternalFrame focusHolder_;

	public void setFocusHolder(JInternalFrame target)
	{
		focusHolder_ = target;
	}

	public void internalFrameActivated(InternalFrameEvent e)
	{
		Object source = e.getSource();
		if (source instanceof JInternalFrame)
			focusHolder_ = (JInternalFrame)source;
	}

	public void internalFrameClosing(InternalFrameEvent e)
	{
		if (focusHolder_ == e.getSource()) {
			focusHolder_ = null;
		}
		if (e.getSource() instanceof ImageInternalFrame) {
			((ImageInternalFrame)e.getSource()).flush();
		}
	}

	public JInternalFrame getFocusHolder()
	{
		return focusHolder_;
	}

}

