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

import java.awt.event.*;
import com.sun.jimi.core.component.*;

public class ScalingListener implements ActionListener
{
	private JimiCanvas canvas;
	
	public ScalingListener(JimiCanvas canvas) {
		this.canvas = canvas;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		
		if(command.equals("Fit Images")) {
			canvas.setResizePolicy(JimiCanvas.BEST_FIT);
		}
		else
		if(command.equals("Fit Width")) {
			canvas.setResizePolicy(JimiCanvas.FIT_WIDTH);
		}
		else
		if(command.equals("Scale Images")) {
			canvas.setResizePolicy(JimiCanvas.SCALE);
		}
		else
		if(command.equals("Scroll")) {
			canvas.setResizePolicy(JimiCanvas.SCROLL);
		}
		else
		if(command.equals("Crop")) {
			canvas.setResizePolicy(JimiCanvas.CROP);
		}
		else
		if(command.equals("Low-memory scroll (Paged)")) {
			canvas.setResizePolicy(JimiCanvas.PAGED);
		}
		else
		if(command.equals("Multipage")) {
//			canvas.setResizePolicy(JimiCanvas.MULTIPAGE);
		}
	}
}
