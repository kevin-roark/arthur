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
import java.io.*;
import java.net.*;
import java.util.*;

import com.sun.jimi.core.*;
import com.sun.jimi.core.component.*;
import com.sun.jimi.core.raster.JimiRasterImage;
import com.sun.jimi.core.util.*;

/**
  * An image diplayer and directory browser which uses <strong>J.I.M.I.</strong> to
  * display images within a directory, and to save those images in any other format.
  * The images are cached to make reloading faster, however there is a problem with
  * this in that it is easy to run out of memory with cached images.
  *
  * @author Activated Intelligence
  */
public final class JimiBrowserApp implements ActionListener, ItemListener
{
	// filter action
    private FilterListener fl;
    // image loading time
	private double loadtime;
	// decoder options
	private OptionDialog options;
	// open image from url dialog
	private URLDialog urld;
	// app version string
	public static final String TITLE = "JIMI Image Browser 2.0";
    // adjust aspect
    protected boolean adjust;

	// The maximum size of the browser when it is first loaded.
	static final Dimension MAX_BROWSER_INIT_SIZE = new Dimension(600, 600);

	// The frame in which everything resides.
	protected static Frame displayFrame;

	//The canvas in which we display the images.
	protected JimiCanvas jCanvas;

	// show the status of the operations taking place
	protected Label statusBar;

	/** 
	 * The names displayed in the list aren't the fully qualified path names, 
	 * this maps between the two.
	 */
	protected Hashtable imageNameMap = new Hashtable(4);

	// MediaTracker for blocking on image loading.
	protected MediaTracker mediaTracker;

	// Where we last loaded an image from
	protected String currentDirectory;

	// Checkable MenuItems we need to keep track of
	protected CheckboxMenuItem menuFaster;
	protected CheckboxMenuItem menuSmoother;

	protected MenuItem next, prev;

	protected Menu pageMenu;

	// The frame size starts out as 3/4 of the screen real estate when the program is started
	protected Dimension DEFAULT_FRAME_SIZE;

	// The frame is initially centered on the screen, based upon the screen size when the program is started
	protected Point DEFAULT_FRAME_LOCATION;

	// The menu which lists of all the images currently in the cache.
	protected Menu imageListMenu;

	// The <code>ActionListener</code> which monitors the <code>imageListMenu</code> to determine which image to load.
	protected final LoadImageListener LoadImageListener = new LoadImageListener();

	// Am I resetting my picture lock?
	private final LoadingLock lock = new LoadingLock();

    // initializer code
	{
		Dimension temp = Toolkit.getDefaultToolkit().getScreenSize();

    	DEFAULT_FRAME_SIZE = new Dimension(temp.width * 3 / 4, temp.height * 3 / 4);

		if(DEFAULT_FRAME_SIZE.width > MAX_BROWSER_INIT_SIZE.width) {
			DEFAULT_FRAME_SIZE.width = MAX_BROWSER_INIT_SIZE.width;
		}
		if(DEFAULT_FRAME_SIZE.height > MAX_BROWSER_INIT_SIZE.height) {
			DEFAULT_FRAME_SIZE.height = MAX_BROWSER_INIT_SIZE.height;
		}
		DEFAULT_FRAME_LOCATION = new Point((temp.width - DEFAULT_FRAME_SIZE.width) / 2, (temp.height - DEFAULT_FRAME_SIZE.height) / 2);
	}

	/** To run the program you may either specify a directory, or rely upon
	  * the default directory
	  *
	  * @param args	The directory to browse
	  */
	public static void main(String[] args)
	{
		Splash splash = new Splash("applicationIcons/splash.gif", new Frame());
		VMMControl.setVMMThreshold(10 * 1024 * 1024);
		JimiBrowserApp app = new JimiBrowserApp(args);
		splash.setVisible(false);
		splash.dispose();
		splash = null;
	}

	/** Create a new browser using the command line arguments invoked when the class was
	  * "run"
	  *
	  * @see main
	  * @param args The directory to browse
	  */
	public JimiBrowserApp(String[] args)
	{
		if(args.length != 0) {
			currentDirectory = args[0];
		}
		displayFrame = new Frame(JimiBrowserApp.TITLE);
		displayFrame.setIconImage(Jimi.getImage("applicationIcons/minilogoblack.gif"));
		displayFrame.addWindowListener(new WindowAdapter()
									   {
										   public void windowClosing(WindowEvent we)
											   {
												   displayFrame.setVisible(false);
												   displayFrame.dispose();
												   System.exit(0);
											   }
									   });
	
		mediaTracker = new MediaTracker(displayFrame);

		displayFrame.setLayout(new BorderLayout());
		displayFrame.setForeground(SystemColor.window);
		displayFrame.setBackground(SystemColor.desktop);

		//Create the display area for the Images to be displayed on
		jCanvas = new JimiCanvas();
		jCanvas.setProgressListener(new StatusListener("Loading", "loaded"));
		jCanvas.setBackground(SystemColor.window);
		jCanvas.setForeground(SystemColor.desktop);
		jCanvas.setWillSizeToFit(true);

		displayFrame.add(jCanvas, BorderLayout.CENTER);

		//Create the Menu's
		MenuBar mBar = createMenuBar();
		displayFrame.setMenuBar(mBar);

		// statusbar
  		statusBar = new Label("Ready", Label.CENTER);
		statusBar.setForeground(SystemColor.activeCaptionText);
		statusBar.setBackground(SystemColor.activeCaption);
		displayFrame.add(statusBar, BorderLayout.SOUTH);

		/* preload option and url dialogs */
		options = new OptionDialog(displayFrame);
		adjust = options.getAdjustAspect();
		urld = new URLDialog(displayFrame, this);

		// size it, show it
		displayFrame.setSize(DEFAULT_FRAME_SIZE);
		displayFrame.setLocation(DEFAULT_FRAME_LOCATION);
		displayFrame.show();
	}

	/** Provides status reporting through a Label on the viewframe.
	  *
	  * @param aString What do we want to report?
	  */
	protected void setStatus(String aString) {
		statusBar.setText(aString);
	}

	protected void openImage(String path)
	{
		jCanvas.setLoadingFlags(options.getDecodeMode() | Jimi.SYNCHRONOUS);
		jCanvas.setAspectAdjust(options.getAdjustAspect());
		if (isURL(path)) {
			try {
				jCanvas.setImageLocation(new URL(path));
			}
			catch (IOException e) {
				setStatus("Invalid URL: " + path);
			}
		}
		else {
			jCanvas.setImagePath(path);
		}
		next.setEnabled(jCanvas.hasNextImage());
		prev.setEnabled(jCanvas.hasPreviousImage());
		pageMenu.setEnabled(next.isEnabled() || prev.isEnabled());

		String shortName = path;
		if (path.indexOf(File.separatorChar) != -1) {
			shortName = path.substring(path.lastIndexOf(File.separatorChar) + 1);
		}

		/* add shortcut */
		if (!imageNameMap.contains(path)) {
			MenuItem mi =  new MenuItem(shortName);
			mi.addActionListener(LoadImageListener);
			imageListMenu.add(mi);
			imageNameMap.put(shortName, path);
		}
	}

	/**
	 * simple method to adjust aspect ratio on tiff images
	 */
	protected Image getAspectAdjustedImage(Image image)
	{
		try
			{
				adjust = options.getAdjustAspect();
				if(adjust)
					{
						image = Toolkit.getDefaultToolkit().createImage(JimiUtils.aspectAdjust(Jimi.createRasterImage(image.getSource())));
						GraphicsUtils.waitForImage(image);
					}
			}
		catch(JimiException e) { System.err.println(e); }
		return image;
	}

	public static Frame getDisplayFrame() {
		return displayFrame;
	}

	/** Saves the image which is currently displayed on the canvas as a new file name.
	  *
	  * @see openImage
	  */
	protected void saveImage()
	{
		Image image;

		synchronized(lock) {
			image = jCanvas.getImage();
		}

		if(image == null)
			{
				setStatus("No image");
				return;
			}

		FileDialog dialog = new FileDialog(displayFrame, "Save Image");
		dialog.setMode(FileDialog.SAVE);
		dialog.setDirectory(currentDirectory);
		dialog.show();

		String directory = dialog.getDirectory();
		String shortName = dialog.getFile();
		String fileName  = directory + File.separatorChar + shortName;

		if(shortName == null)
			{
				setStatus("Save Cancelled");
				return;
			}

		currentDirectory = directory;

		try
			{
				displayFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				JimiWriter jw = Jimi.createJimiWriter(fileName);
				jw.setProgressListener(new StatusListener("Saving", "saved"));
				jw.setSource(image);
				jw.putImage(fileName);
				openImage(fileName);

				//add it to the listing of images
				MenuItem mi = new MenuItem(shortName);
				mi.addActionListener(LoadImageListener);
				imageListMenu.add(mi);

				imageNameMap.put(shortName, fileName);
				setStatus(shortName);
			}
		catch(JimiException je)
			{
				showLicenseAdvice();
			}
		finally	{
			displayFrame.setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private static final String LICENSE_ADVICE =
	"The format you are attempting to save in is not available.\n";

	private void showLicenseAdvice()
	{
		final Dialog dialog = new Dialog(displayFrame, true);
		dialog.setLayout(new BorderLayout());
		TextArea ta = new TextArea(LICENSE_ADVICE);
		ta.setBackground(Color.white);
		ta.setForeground(Color.black);
		dialog.add(ta, BorderLayout.CENTER);
		Button b = new Button("Okay");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		dialog.add(b, BorderLayout.SOUTH);
		dialog.pack();
		dialog.show();
	}

	private boolean isURL(String string)
	{
		if(!(string.indexOf("://") != -1))
			return false;
		return true;
	}


	/** To simplify the code the menu is created in this subroutine.  <br>
	  * The menu of images we've already loaded is created here, but not populated
	  * until we add those images.
	  *
	  * @returns MenuBar What can we do with this application?
	  */
	protected MenuBar createMenuBar()
	{
		MenuBar mBar = new MenuBar();

		Menu menu = new Menu("File");
		MenuItem mi = new MenuItem("Open");
		mi.addActionListener(this);
		menu.add(mi);

		mi = new MenuItem("Open from URL");
		mi.addActionListener(this);
		menu.add(mi);

		mi = new MenuItem("Save");
		mi.addActionListener(this);
		menu.add(mi);

		String version = System.getProperty("java.version");
		if(version.indexOf("1.2") != -1)
			{
				menu.addSeparator();

				mi = new MenuItem("Print");
				mi.addActionListener(this);
				menu.add(mi);
			}

		menu.addSeparator();

		mi = new MenuItem("Exit");
		mi.addActionListener(this);
		menu.add(mi);

		mBar.add(menu);

		menu = new Menu("Options");

		Menu innerMenu = new Menu("Image Scaling");
		menu.add(innerMenu);
		// create a ScalingListenter to handle this stuff.
		ScalingListener sl = new ScalingListener(jCanvas);
		ItemToActionAdapter il = new ItemToActionAdapter(sl);
		// on wit the menu then
		CheckboxMenuItem cmi;
		CheckboxGroup group = new CheckboxGroup();
		cmi = new CheckboxMenuItem("Fit Images");
		cmi.addItemListener(il);
		innerMenu.add(cmi);

		cmi = new CheckboxMenuItem("Fit Width");
		cmi.addItemListener(il);
		innerMenu.add(cmi);

		cmi = new CheckboxMenuItem("Scale Images");
		cmi.addItemListener(il);
		innerMenu.add(cmi);

		cmi = new CheckboxMenuItem("Scroll");
		cmi.addItemListener(il);
		innerMenu.add(cmi);
		
		cmi = new CheckboxMenuItem("Crop");
		cmi.addItemListener(il);
		innerMenu.add(cmi);
		
		cmi = new CheckboxMenuItem("Low-memory scroll (Paged)");
		cmi.addItemListener(il);
		cmi.setState(true);
		il.setSelection(cmi);
		innerMenu.add(cmi);
		/*
		 * justification menu
		 */
		innerMenu = new Menu("Justification");

		menu.add(innerMenu);

		String[] directions = new String[]
			{
				"North",
					"Northeast",
					"East",
					"Southeast",
					"South",
					"Southwest",
					"West",
					"Northwest",
					"Center"
					};
		// create a listener object
		JustificationListener jl = new JustificationListener(jCanvas);
		
		il = new ItemToActionAdapter(jl);
		for(int i = 0; i < directions.length; i++)
			{
				cmi = new CheckboxMenuItem(directions[i]);
				cmi.addItemListener(il);
				// center
				if (i == directions.length - 1) {
					cmi.setState(true);
					il.setSelection(cmi);
				}
				innerMenu.add(cmi);
			}
		
		/*
		 * Initialize the "scaling mode" menu
		 */
		innerMenu = new Menu("Scaling Mode");
		menu.add(innerMenu);

		menuSmoother = new CheckboxMenuItem("Smoother");
		menuSmoother.addItemListener(this);
		innerMenu.add(menuSmoother);

		menuFaster = new CheckboxMenuItem("Faster");
		menuFaster.setState(true);		
		menuFaster.addItemListener(this);
		innerMenu.add(menuFaster);

		// separator
		menu.addSeparator();

		mi = new MenuItem("Refresh");
		mi.addActionListener(this);
		menu.add(mi);

		mi = new MenuItem("Garbage Collect");
		mi.addActionListener(this);
		menu.add(mi);

		/* decode options menuitem */
		mi = new MenuItem("Decoding Options");
		mi.addActionListener(this);
		menu.add(mi);
		mBar.add(menu);

        /* image menu*/
        imageListMenu = new Menu("Images");
		mBar.add(imageListMenu);
		
		/*********************************************************/
		// filter menu
		fl = new FilterListener(jCanvas);
		Menu filters = new Menu("Filters");
		/* filters */
		mi = new MenuItem("Edge Detect");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("Enlarge");
		mi.addActionListener(fl);
		filters.add(mi);

		Menu page = pageMenu = new Menu("Page");

		PageMenuListener pl = new PageMenuListener();

		next = mi = new MenuItem("Next");
		mi.addActionListener(pl);
		page.add(mi);

		prev = mi = new MenuItem("Prev");
		mi.addActionListener(pl);
		page.add(mi);

		page.addSeparator();

		mi = new MenuItem("First");
		mi.addActionListener(pl);
		page.add(mi);

		mi = new MenuItem("Last");
		mi.addActionListener(pl);
		page.add(mi);

		page.setEnabled(false);

		mBar.add(page);

		/* flipmenu */
		Menu flip = new Menu("Flip");
		// submenu for fliptypes
		mi = new MenuItem("Flip.FLIP_CCW");
		mi.addActionListener(fl);
		flip.add(mi);

		mi = new MenuItem("Flip.FLIP_CW");
		mi.addActionListener(fl);
		flip.add(mi);

		mi = new MenuItem("Flip.FLIP_LR");
		mi.addActionListener(fl);
		flip.add(mi);

		mi = new MenuItem("Flip.FLIP_NULL");
		mi.addActionListener(fl);
		flip.add(mi);

		mi = new MenuItem("Flip.FLIP_R180");
		mi.addActionListener(fl);
		flip.add(mi);

		mi = new MenuItem("Flip.FLIP_TB");
		mi.addActionListener(fl);
		flip.add(mi);

		mi = new MenuItem("Flip.FLIP_XY");
		mi.addActionListener(fl);
		flip.add(mi);

     	filters.add(flip);

		/* </flipmenu> */

		mi = new MenuItem("Gamma");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("Gray");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("Invert");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("Margin");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("Oil");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("Rotate");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("ScaleCopy");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("Shear");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("Shrink");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("Smooth");
		mi.addActionListener(fl);
		filters.add(mi);
		mi = new MenuItem("Tile");
		mi.addActionListener(fl);
		filters.add(mi);
		/*********************************************************/		

		return mBar;
	}

	private void showCachedImage(Image image)
	{
		Image oldImage = jCanvas.getImage();
		jCanvas.setImage(image);
		oldImage.flush();
	}

	/** To simplify things, we listen to our own action events that occur in the
	  * main menus.  The list of loaded images is handled by a separate <code>
	  * ActionListener</code>
	  *
	  * @see ImageListListenerClass
	  * @see ImageListListener
	  * @param e	One of our menus was selected
	  */
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();

		if(command.equals("Open"))
			{
				//File theFile = null;
				FileDialog dialog = new FileDialog(displayFrame, "Open Image", FileDialog.LOAD);
				if(currentDirectory != null) {
					dialog.setDirectory(currentDirectory);
				}
				dialog.show();
				String dir = dialog.getDirectory();
				String file = dialog.getFile();
				if( dir != null )
					{
						currentDirectory = dir;
						if (file != null) {
							openImage(dir.concat(file));
						}
					}
			}
		else
			if(command.equals("Open from URL"))
				{
					urld.setVisible(true);
					urld.pack();
					urld.center();
				}
			else
				if(command.equals("Save"))
					{
						saveImage();
					}
				else
					if(command.equals("Print"))
						{
							/* this will look !%#!%& in 1.1.x */
							Toolkit t = displayFrame.getToolkit();
							PrintJob pj = t.getPrintJob(displayFrame, "JIMI Image...", null);
							if(pj == null) {
								return;
							}
							Graphics page = pj.getGraphics();
							Image image = jCanvas.getImage();
							//int imgWidth = image.getWidth(null);
							//int imgHeight = image.getHeight(null);
							// paintjob
							page.drawImage(image, 0, 0, null);
							displayFrame.print(page);
							/* All done */
							page.dispose();
							pj.end();

						}
					else
						if(command.equals("Exit"))
							{
								synchronized(this)
									{
										//we dispose of the frame
										displayFrame.setVisible(false);
										displayFrame.dispose();
										//we exit
										System.exit(0);
									}
							}
						else
							if(command.equals("Refresh")) {
								jCanvas.setImage(jCanvas.getImage());
							}
							else
								if(command.equals("Garbage Collect")) {
									java.lang.Runtime.getRuntime().gc();
								}
								else
									if(command.equals("Decoding Options"))
										{
											options.setVisible(true);
											options.pack();
											options.center();
										}
	}

    public void itemStateChanged( ItemEvent e )
    {
		if(e.getSource() == menuSmoother)
			{
				jCanvas.setScalingPolicy( JimiCanvas.AREA_AVERAGING );
				menuFaster.setState( false );
				menuSmoother.setState( true );
			}
		else
			if(e.getSource() == menuFaster)
				{
					jCanvas.setScalingPolicy( JimiCanvas.REPLICATE );
					menuFaster.setState( true );
					menuSmoother.setState( false );
				}
    }

	/** 
	 * A helper class to listen for menu selection events in the Image list menu.
	 */
	private class LoadImageListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			openImage((String)JimiBrowserApp.this.imageNameMap.get(e.getActionCommand()));
		}
	}

	protected class PageMenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();

			if (command.equals("Next")) {
				jCanvas.nextImage();
			}
			else if (command.equals("Prev")) {
				jCanvas.previousImage();
			}
			else if (command.equals("Last")) {
				jCanvas.lastImage();
			}
			else if (command.equals("First")) {
				jCanvas.firstImage();
			}
			next.setEnabled(jCanvas.hasNextImage());
			prev.setEnabled(jCanvas.hasPreviousImage());
			pageMenu.setEnabled(next.isEnabled() || prev.isEnabled());
		}
	}

	/** 
	 * Synched externally
	 */
	protected static final class LoadingLock
	{
		protected int count = 0;

		LoadingLock() { super(); }

		boolean hasLoaders() { return count > 0; }

		void addLoad() { count++; }

		void removeLoad() { count--; }
	}

	protected class StatusListener implements ProgressListener
	{

		int PROGRESS_INCREMENT = 7;
		long timeStamp;

		int prevProgress;

		protected String v, n;

		public StatusListener(String v, String n)
		{
			this.v = v;
			this.n = n;
		}

		/**
		 * Indicate that the task being monitored has begun.
		 */
		public void setStarted()
		{
			setStatus(v + " image...");
			prevProgress = -1;
			timeStamp = System.currentTimeMillis();
			displayFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}

		/**
		 * Set the progress-level as a percentage.
		 * @param progress a number between 0 and 100 representing the current
		 * level of progress
		 */
		public void setProgressLevel(int progress)
		{
			progress /= PROGRESS_INCREMENT;
			if (progress > prevProgress) {
				prevProgress = progress;
			}
		}

		/**
		 * Indicate that the task being monitored has completed.
		 */
		public void setFinished()
		{
			displayFrame.setCursor(Cursor.getDefaultCursor());
			long time = System.currentTimeMillis() - timeStamp;
			long millis = time % 1000;
			String millisString = String.valueOf(millis);
			while( millisString.length() < 3) {
				millisString = "0" + millisString;
			}
			setStatus("Image "+n+" successfully in " + (time / 1000) + "." + millisString + " secs.");
			prevProgress = -1;
		}

		/**
		 * Indicate that the operation has been aborted.
		 */
		public void setAbort()
		{
			displayFrame.setCursor(Cursor.getDefaultCursor());
			setStatus(v+" failed.");
			prevProgress = -1;
		}

		/**
		 * Indicate that the operation has been aborted.
		 * @param reason the reason the operation was aborted
		 */
		public void setAbort(String reason)
		{
			displayFrame.setCursor(Cursor.getDefaultCursor());
			setAbort();
		}
	}

	class ItemToActionAdapter implements ItemListener
	{
		protected ActionListener actionListener;
		protected CheckboxMenuItem selection;

		public ItemToActionAdapter(ActionListener listener)
		{
			actionListener = listener;
		}

		public void itemStateChanged(ItemEvent e)
		{
			if (selection != null) {
				selection.setState(false);
			}
			CheckboxMenuItem checkbox = (CheckboxMenuItem)e.getSource();
			checkbox.setState(true);
			selection = checkbox;
			ActionEvent event = new ActionEvent(selection, ActionEvent.ACTION_PERFORMED,
												selection.getLabel());
			actionListener.actionPerformed(event);
		}

		public void setSelection(CheckboxMenuItem item)
		{
			selection = item;
		}
	}
}
