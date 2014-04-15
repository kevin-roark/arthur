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

import com.sun.jimi.core.filters.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Class for creating Image filters and applying them to Images
 * over an ImageProcessor
 * @see ImageProcessor
 **/
public class ToolPanel extends JPanel implements ItemListener
{

	/*
	 * Options constants
	 */
	// used by resizing filters
	public static final String WIDTH = "Width", HEIGHT = "Height";
	// for color reduction
	public static final String MAX_COLORS = "Max. Colors";
	// for tiling
	public static final String HORIZ_TILES = "Horiz. Tiles", VERT_TILES = "Vert. Tiles";
	// for sheering
	public static final String SHEER_DEGREES = "Angle (degress)";
	// measures of magnitude
	public static final String
	SMOOTH_LEVEL = "Level (1-10)",
		OIL_LEVEL = "Level (1-5)",
		GAMMA_LEVEL = "Level (-100-100)";


	protected JComboBox comboBox_;
	protected JButton applyButton_;
	protected JButton resetButton_;
	protected ImageProcessor imageProcessor_;
	protected OptionsPanel options_;

	// indices into array matching identifiers to string-names
	private static final int
	RESET = 0, COLOR_REDUCE = 1, FS_COLOR_REDUCE = 2, EDGE_DETECT = 3, FLIP = 4,
		GAMMA = 5, GRAY = 6, INVERT = 7, MARGIN = 8, OIL = 9, RESIZE = 10, ROTATE = 11, SHEAR = 12,
		SMOOTH = 13, TILE = 14;

	// names of available filters
	private static final String[] CHOICE_NAMES =
{ "Reset", "Color Reduce", "C.Reduce/Dither", "Edge Detect", "Flip", "Gamma", "Gray",
  "Invert", "Margin", "Oil", "Resize", "Rotate", "Shear", "Smooth", "Tile" };

	/**
	 * Create a ToolPanel for a given ImageProcessor.
	 * @param imageProcessor The ImageProcessor to apply filters to.
	 **/
	public ToolPanel(ImageProcessor imageProcessor)
	{
		imageProcessor_ = imageProcessor;

		// create widgets
		String[] available_choices = new String[CHOICE_NAMES.length - 1];
		System.arraycopy(CHOICE_NAMES, 1, available_choices, 0, available_choices.length);
		comboBox_ = new JComboBox(available_choices);
		comboBox_.setSelectedIndex(0);
		comboBox_.addItemListener(this);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		applyButton_ = new JButton("Apply");
		applyButton_.addActionListener(new ApplyButtonListener(this));
		resetButton_ = new JButton("Reset");
		resetButton_.addActionListener(new ResetButtonListener());

		panel.add(applyButton_, BorderLayout.NORTH);
		panel.add(resetButton_, BorderLayout.SOUTH);

		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, comboBox_);

		// TODO: This JPanel is just padding, in its place should be some kind of
		// panel for choosing filter properties
		options_ = new OptionsPanel();
		add(BorderLayout.CENTER, options_);

//		add(BorderLayout.SOUTH, applyButton_);

		add(panel, BorderLayout.SOUTH);
	}

	/**
	 * Applies the currently selected ImageFilter to the selected image in
	 * the ImageProcessor.
	 **/
	public void applyFilter()
	{
		requestFocus();
		ImageInternalFrame frame = imageProcessor_.getSelectedImageFrame();

		if (frame == null) return;

		int filternr = comboBox_.getSelectedIndex() + 1;
		ImageProducer src = frame.getImage().getSource();

		if (!options_.commitOptions())
			return;

		// big switch between effects
		switch (filternr)
		{
	  case RESET:
		  frame.revertImage();
		  break;
	  case COLOR_REDUCE:
		  frame.reduceColors(options_.getProperty(MAX_COLORS), false);
		  break;
	  case FS_COLOR_REDUCE:
		  frame.reduceColors(options_.getProperty(MAX_COLORS), true);
		  break;
	  case EDGE_DETECT:
		  frame.applyFilter(new EdgeDetect(src));
		  break;
	  case RESIZE:
		  int resize_w = options_.getProperty(WIDTH);
		  int resize_h = options_.getProperty(HEIGHT);
		  AreaAveragingScaleFilter resize = new AreaAveragingScaleFilter(resize_w, resize_h);
		  frame.applyFilter(resize);
		  break;
	  case FLIP:
		  frame.applyFilter(new Flip(src, Flip.FLIP_TB));
		  break;
	  case GAMMA:
		  double gamma_level = options_.getProperty(GAMMA_LEVEL);
		  gamma_level = Math.min(Math.max(-100, gamma_level), 100);
		  gamma_level = 1 + (gamma_level / 100.0);
		  frame.applyFilter(new Gamma(src, gamma_level));
		  break;
	  case GRAY:
		  frame.applyFilter(new Gray());
		  break;
	  case INVERT:
		  frame.applyFilter(new Invert(src));
		  break;
	  case MARGIN:
		  frame.applyFilter(new Margin(src, Color.black, 10));
		  break;
	  case OIL:
		  int oil_level = options_.getProperty(OIL_LEVEL);
		  oil_level = Math.min(Math.max(1, oil_level), 5);
		  frame.applyFilter(new Oil(src, oil_level));
		  break;
	  case SHEAR:
		  frame.applyFilter(new Shear(src, options_.getProperty(SHEER_DEGREES)));
		  break;
	  case SMOOTH:
		  int smooth_level = options_.getProperty(SMOOTH_LEVEL);
		  smooth_level = Math.min(Math.max(1, smooth_level), 10);
		  frame.applyFilter(new Smooth(src, smooth_level));
		  break;
	  case TILE:
		  int horiz_tiles = options_.getProperty(HORIZ_TILES);
		  int vert_tiles = options_.getProperty(VERT_TILES);
		  Image tile_image = frame.getImage();
		  int tile_w = tile_image.getWidth(null);
		  int tile_h = tile_image.getHeight(null);
		  frame.applyFilter(new Tile(src, tile_w * horiz_tiles, tile_h * vert_tiles));
		  break;
	  case ROTATE:
		  int rotate_angle = options_.getProperty(SHEER_DEGREES);
		  frame.applyFilter(new Rotate(rotate_angle));
		  break;
		}
	}

	protected Image getImage()
	{
		ImageInternalFrame frame = imageProcessor_.getSelectedImageFrame();
		if (frame != null)
			return frame.getImage();
		else
			return null;
	}

	public void itemStateChanged(ItemEvent event)
	{
		if (event.getStateChange() == ItemEvent.DESELECTED)
			return;

		Object item = event.getItem();
		if (item.equals(CHOICE_NAMES[RESIZE]))
		{
			String[] options = { WIDTH, HEIGHT };
			String[] defaults;
			Image img = getImage();
			if (img != null)
				defaults = new String[] {String.valueOf(img.getWidth(null)),String.valueOf(img.getHeight(null))};
			else
				defaults = new String[] {"", ""};

			options_.setOptions(options, defaults);
		}
		else if (item.equals(CHOICE_NAMES[SMOOTH]))
		{
			String[] options = { SMOOTH_LEVEL };
			String[] defaults = { "1" };
			options_.setOptions(options, defaults);
		}
		else if (item.equals(CHOICE_NAMES[OIL]))
		{
			String[] options = { OIL_LEVEL };
			String[] defaults = { "1" };
			options_.setOptions(options, defaults);
		}
		else if (item.equals(CHOICE_NAMES[GAMMA]))
		{
			String[] options = { GAMMA_LEVEL };
			String[] defaults = { "3" };
			options_.setOptions(options, defaults);
		}
		else if (item.equals(CHOICE_NAMES[TILE]))
		{
			String[] options = { HORIZ_TILES, VERT_TILES };
			String[] defaults;
			defaults = new String[] {"1", "1"};
			options_.setOptions(options, defaults);
		}
		else if (item.equals(CHOICE_NAMES[SHEAR]))
		{
			String[] options = { SHEER_DEGREES };
			String[] defaults = { "0" };
			options_.setOptions(options, defaults);
		}
		else if ((item.equals(CHOICE_NAMES[COLOR_REDUCE])) ||
				 (item.equals(CHOICE_NAMES[FS_COLOR_REDUCE])))
		{
			String[] options = { MAX_COLORS };
			String[] defaults = { "256" };
			options_.setOptions(options, defaults);
		}
		else if (item.equals(CHOICE_NAMES[ROTATE]))
		{
			String[] options = { SHEER_DEGREES };
			String[] defaults = { "45" };
			options_.setOptions(options, defaults);
		}
		else
		{
			options_.setOptions(new String[0], new String[0]);
		}
	}

	public void reset()
{
		ImageInternalFrame frame = imageProcessor_.getSelectedImageFrame();

		if (frame == null) return;

		frame.revertImage();
}

	/**
	 * NOTE: Needn't take a reference to the ToolPanel in the constructor.
	 **/
	private class ApplyButtonListener implements ActionListener
	{
		private ToolPanel panel_;

		public ApplyButtonListener(ToolPanel panel)
		{
			panel_ = panel;
		}

		public void actionPerformed(ActionEvent e)
		{
			panel_.applyFilter();
		}
	}

	private class ResetButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			ToolPanel.this.reset();
		}

	}

	class OptionsPanel extends JPanel
	{
		protected GridBagLayout layout_;
		protected Hashtable componentToPropertyMap_ = new Hashtable();
		protected Hashtable propertyToValueMap_ = new Hashtable();
		protected Vector listeners_ = new Vector();

		public OptionsPanel()
		{
			Border border = BorderFactory.createEtchedBorder();
			setBorder(BorderFactory.createTitledBorder(border, "Filter Options"));

			//setOptions(new String[] { "foo", "bar" }, new String[] { "baz", "blah" });
			//setOptions(new String[] { "boo", "bar" }, new String[] { "baz", "blah" });
		}

		/**
		 * Fetches up-to-date options.
		 * @return True if all options are valid.
		 **/
		public boolean commitOptions()
		{
			boolean valid = true;
			synchronized (listeners_)
			{
				int size = listeners_.size();
				for (int i = 0; valid && (i < size); i++)
				{
					PropertyCommitListener listener = (PropertyCommitListener)listeners_.elementAt(i);
					valid |= listener.commitProperty();
				}
			}
			return valid;
		}

		protected void resetLayout()
		{
			layout_ = new GridBagLayout();
			removeAll();
			setLayout(layout_);
		}

		protected void setOptions(String[] names, String[] defaults)
		{
			resetLayout();
			String name, initial;
			for (int i = 0; i < names.length; i++)
			{
				name = names[i];
				initial = defaults[i];

				JTextField field = new JTextField();
				field.setText(initial);

				listeners_.addElement(new PropertyCommitListener(field, name));

				JLabel label = new JLabel(name);
				label.setForeground(Color.black);

				componentToPropertyMap_.put(field, name);
				propertyToValueMap_.put(name, initial);
			
				addComponents(name, label, field);
			}
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.gridheight = 1;
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.weighty = gbc.weightx = 1.0;
			JPanel pad = new JPanel();
			layout_.setConstraints(pad, gbc);
			add(pad);
			validate();
		}

		protected void addComponents(String name, JLabel label, JTextField field)
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridheight = 1;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.weightx = 1.0;
			gbc.weighty = 0.0;
			gbc.fill = GridBagConstraints.HORIZONTAL;

			gbc.insets = new Insets(5, 0, 0, 0);
			layout_.setConstraints(label, gbc);
			add(label);

			gbc.insets = new Insets(0, 0, 0, 0);
			layout_.setConstraints(field, gbc);
			add(field);

		}

		public void setProperty(String name, String value)
		{
			propertyToValueMap_.put(name, value);
		}

		public int getProperty(String name)
		{
			return Integer.parseInt((String)propertyToValueMap_.get(name));
		}

		private class PropertyCommitListener
		{
			private JTextField textField_;
			private String property_;

			public PropertyCommitListener(JTextField field, String propertyName)
			{
				textField_ = field;
				property_ = propertyName;
			}

			public boolean commitProperty()
			{
				JTextField textfield = textField_;
				String text = textfield.getText();
				int value = 0;
				boolean bad_value = false;
				try {
					value = Integer.parseInt(text);
				}
				catch (NumberFormatException e)
				{
					bad_value = true;
				}
				if (bad_value)
					textfield.selectAll();
				else
					OptionsPanel.this.setProperty(property_, text);

				return !bad_value;
			}
		}

	}

}	
