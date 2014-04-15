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

public class JustificationListener implements ActionListener
{
	private JimiCanvas canvas;
	
	public JustificationListener(JimiCanvas canvas)	{
		this.canvas = canvas;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		
		if(command.equals("Center")) {
			canvas.setJustificationPolicy(JimiCanvas.CENTER);
		}
		else
		if(command.equals("North"))	{
			canvas.setJustificationPolicy(JimiCanvas.NORTH);
		}
		else
		if(command.equals("South"))	{
			canvas.setJustificationPolicy(JimiCanvas.SOUTH);
		}
		else
		if(command.equals("East")) {
			canvas.setJustificationPolicy(JimiCanvas.EAST);
		}
		else
		if(command.equals("West")) {
			canvas.setJustificationPolicy(JimiCanvas.WEST);
		}
		else
		if(command.equals("Northeast"))	{
			canvas.setJustificationPolicy(JimiCanvas.NORTHEAST);
		}
		else
		if(command.equals("Northwest"))	{
			canvas.setJustificationPolicy(JimiCanvas.NORTHWEST);
		}
		else
		if(command.equals("Southeast"))	{
			canvas.setJustificationPolicy(JimiCanvas.SOUTHEAST);
		}
		else
		if(command.equals("Southwest"))	{
			canvas.setJustificationPolicy(JimiCanvas.SOUTHWEST);
		}
	}
}
