//Title:       AbstractDemo JIMI Example.
//Version:     1.1b
//Copyright:   Copyright (c) 1998 Sun Microsystems, Inc
//Author:      Thomas Isaksen ( thomas@activated.com )
//Description: A simple applet to show the use of JIMI filters.

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

public abstract class AbstractDemo extends Applet implements ActionListener, ItemListener
{
	Button btnFilter, btnClear;
	Image image;
	JimiCanvas canvas;
	ImageProducer source;
	ImageProducer filterSource;
	ImageFilter filter;
	double value = 1.0;
	Hashtable imagemap;

	public void init()
	{
		this.setLayout(new BorderLayout(0,0));
		// create a scrollpane.
//      ScrollPane pane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		// we need a jimicanvas to render on
		canvas = new JimiCanvas();
//      canvas.setWillSizeToFit(true);
//		canvas.setResizePolicy(JimiCanvas.SCROLL);
		// add canvas to pane
//      pane.add(canvas);
		// set some display polycies
//      this.add(pane, BorderLayout.CENTER);
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
			while((filename = br.readLine()) != null) {
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

	public void actionPerformed(ActionEvent e)
	{
		if (this.source == null) {
			return;
		}
		Object source = e.getSource();
		if(source == btnFilter)
		{
			applyFilter(getFilter());
			value++;
		}
		else
			if(source == btnClear)
			{
				reset();
				value = 1.0;
			}
	}

	public void itemStateChanged(ItemEvent e)
	{
		String file = ((String)e.getItem()).trim();
		if(file.startsWith("#") || file.startsWith("##")) {
			return;
		}

		String path = (String)imagemap.get(file);
		/* if the path is not in the map we got ourselfs a problem */
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
		/* get image*/
//      image = Jimi.getImage(url);
		source = Jimi.getImageProducer(url);
		filterSource = source;
		image = createImage(source);
//	  canvas.setImageProducer(image.getSource());
		/* wait for image to load */
		MediaTracker tracker = new MediaTracker(this);
//      tracker.addImage(image, 0);
//      try {
//        tracker.waitForAll();
//      }
//      catch(InterruptedException ie) { showStatus(ie.toString()); }
		/* get imageproducer for this image */
		source = image.getSource();
		/* display image */
//      canvas.setImage(image);
		canvas.setImageProducer(source);
//	  image.flush();
//	  canvas.setImageLocation(url);
//	  validateTree();
	}

	/**
	 * Override this one in the other applets
	 *
	 * See each applets source code for implementation
	 * details.
	 */
	public abstract ImageFilter getFilter();

	/**
	 * create new image based on data from JIMI filter.
	 */
	protected void applyFilter(ImageFilter filter)
	{
		setBusy("Applying Filter, please wait...", canvas.getGraphics());
		FilteredImageSource fis = new FilteredImageSource(filterSource, filter);
		filterSource = fis;
		Image new_image = createImage(fis);
		if(new_image != null)
		{
			canvas.setImage(new_image);
//         new_image.flush();

		}
		clear();
	}

	/* inform mr user that we're doing some business */
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
	protected void reset() {
		canvas.setImage(image);
		filterSource = source;
//	   canvas.setImageProducer(source);
	}
	protected void clear()
	{
	}
}
