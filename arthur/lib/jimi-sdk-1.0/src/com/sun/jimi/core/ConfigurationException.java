/*
 * Copyright 1998 by Sun Microsystems, Inc.
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

package com.sun.jimi.core;

/** Thrown if Jimi has not been setup properly
  */
public class ConfigurationException extends JimiException {

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String s)	{
		super(s);
	}

}
