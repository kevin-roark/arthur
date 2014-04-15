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

import com.sun.jimi.core.*;

class OptionDialog extends Dialog implements ActionListener
{
    /* initialize to default decode method */
    private int decodeMode = Jimi.ASYNCHRONOUS | Jimi.VIRTUAL_MEMORY;
    private boolean adjustAspect = false;

    /* checkboxes */
    private final CheckboxGroup one = new CheckboxGroup();
    private final CheckboxGroup two = new CheckboxGroup();

    private final Checkbox asynch   = new Checkbox("ASYNCHRONOUS", true, one);
    private final Checkbox synch    = new Checkbox("SYNCHRONOUS", false, one);
    private final Checkbox in_mem   = new Checkbox("IN_MEMORY", false, two);
    private final Checkbox vr_mem   = new Checkbox("VIRTUAL_MEMORY", true, two);
    private final Checkbox one_shot = new Checkbox("ONE_SHOT", false, two);

    private final Checkbox aca = new Checkbox("Auto-correct Aspect", true);

    /* buttons and owner frame */
    private final Button btnOk = new Button("Ok");
    private final Button btnCancel = new Button("Cancel");
    private Frame owner;

    public OptionDialog(Frame owner)
    {
        super(owner, "Decoder Options");
        this.owner = owner;

        setBackground(SystemColor.window);
		setForeground(SystemColor.desktop);

        this.setLayout(new BorderLayout());
        Panel panel = new Panel();
        panel.setLayout(new BorderLayout());

        Panel group_one = new Panel();
        group_one.setLayout(new GridLayout(2,1));
		//            group_one.add(asynch, BorderLayout.NORTH);
		//            group_one.add(synch, BorderLayout.CENTER);
		//        panel.add(group_one, BorderLayout.NORTH);

        Panel group_two = new Panel();
        group_two.setLayout(new GridLayout(3,1));
		group_two.add(in_mem);
		group_two.add(vr_mem);
		group_two.add(one_shot);
        panel.add(group_two, BorderLayout.CENTER);

        panel.add(aca, BorderLayout.SOUTH);

        this.add(panel, BorderLayout.CENTER);

        Panel buttons = new Panel();
		buttons.add(btnOk);
		btnOk.addActionListener(this);
		buttons.add(btnCancel);
		btnCancel.addActionListener(this);
        this.add(buttons, BorderLayout.SOUTH);

        this.setResizable(false);

		aca.setState(false);
    }

    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();
        this.setVisible(false);
        if(cmd.equals("Ok"))
			{
				int priVal = 1, secVal = 4; // default
				/* set new decode modes*/
				Checkbox c = one.getSelectedCheckbox();

				String pri = c.getLabel();
				if(pri.equals("ASYNCHRONOUS")) {
					priVal = Jimi.ASYNCHRONOUS;
				}
				else
					if(pri.equals("SYNCHRONOUS")) {
						priVal = Jimi.SYNCHRONOUS;
					}

				Checkbox c2 = two.getSelectedCheckbox();
				String sec = c2.getLabel();
				if(sec.equals("IN_MEMORY")) {
					secVal = Jimi.IN_MEMORY;
				}
				else
					if(sec.equals("VIRTUAL_MEMORY")) {
						secVal = Jimi.VIRTUAL_MEMORY;
					}
					else
						if(sec.equals("ONE_SHOT")) {
							secVal = Jimi.ONE_SHOT;
						}
				setDecodeMode(priVal, secVal);

				if(aca.getState()) {
					adjustAspect = true;
				}
				else {
					adjustAspect = false;
				}
			}
        else {
            this.setVisible(false);
        }
    }

    public void center()
    {
        Point pos = owner.getLocationOnScreen();
        int ownerHeight = owner.getSize().height;
        int ownerWidth = owner.getSize().width;

        int cx = pos.x + (ownerWidth / 2 - (this.getSize().width / 2));
        int cy = pos.y + (ownerHeight /2 - (this.getSize().height / 2));

        this.setLocation(cx, cy);
    }

    public int getDecodeMode() {
        return decodeMode;
    }

    public boolean getAdjustAspect() {
		return adjustAspect;
    }

    public void setDecodeMode(int mode_a, int mode_b) {
        decodeMode = mode_a | mode_b;
    }
}
