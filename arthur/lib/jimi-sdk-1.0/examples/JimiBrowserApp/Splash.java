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
import com.sun.jimi.core.Jimi;

public class Splash extends Window
{
    protected Image image;
    private String path;

    public Splash(String path, Frame parent)
    {
        super(parent);
        this.path = path;

        this.setBackground(SystemColor.desktop);
        Toolkit tk = getToolkit();
        MediaTracker tracker = new MediaTracker(this);

		image = Jimi.getImage(path);
        tracker.addImage(image, 0);
        try {
            tracker.waitForAll();
        }
        catch(InterruptedException e) { }

        /* set splash size */
        int imgWidth = image.getWidth(this);
        int imgHeight = image.getHeight(this);
        this.setSize(imgWidth, imgHeight);

        /* center splashscreen */
        Dimension screen = getToolkit().getScreenSize();
        int x = (screen.width - getSize().width) / 2;
        int y = (screen.height - getSize().height) / 2;
        this.setLocation(x, y);
        /* display splashscreen */
        this.show();
        this.toFront();
//		parent.pack();
		repaint();
		try { Thread.sleep(600); } catch (InterruptedException e) {}
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
