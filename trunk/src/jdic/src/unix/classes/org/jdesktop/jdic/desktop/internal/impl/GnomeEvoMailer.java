/*
 * Copyright (C) 2004 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 */ 

package org.jdesktop.jdic.desktop.internal.impl;

import java.io.IOException;
import java.util.Iterator;

import org.jdesktop.jdic.desktop.Message;
import org.jdesktop.jdic.desktop.internal.LaunchFailedException;
import org.jdesktop.jdic.desktop.internal.MailerService;

/**
 * Represents the Evolution implementation of MailerService interface for Gnome.
 * 
 * @see     GnomeMozMailer
 */
public class GnomeEvoMailer implements MailerService {
    // Location of Evolution
    private String evoLocation;

    /**
     * Constructor, set default mozLocation as Evolution could be found in PATH
     */
    public GnomeEvoMailer() {
        evoLocation = "evolution";
    }

    /**
     * Constructor, initialize with a given path of Evolution location
     */
    public GnomeEvoMailer(String location) {
        evoLocation = location;
    }

    /**
     * Launches evolution message compose window with information contained in msg prefilled
     *
     * @throws LaunchFailedException if the process fails
     */
    public void open(Message msg) throws LaunchFailedException {
        String[] cmdArray = new String[2];
        cmdArray[0] = evoLocation;
        cmdArray[1] = "mailto:?";

        cmdArray[1] += constructMailto(msg.getToAddrs(), msg.getCcAddrs(), msg.getBccAddrs(), msg.getSubject(), msg.getBody(), msg.getAttachments());

        try {
            Runtime.getRuntime().exec(cmdArray);
            Thread.sleep(2000);
        } catch (IOException e1) {
            throw new LaunchFailedException("Cannot launch Evolution composer via commandline:" + e1);
        } catch (InterruptedException iE) {}
    }
	                               
    /**
     * Launches a "blank" Evolution compose window
     *
     * @throws LaunchFailedException if the process fails
     */
    public void open() throws LaunchFailedException {
        String[] cmdArray = new String[2];
        cmdArray[0] = evoLocation;
        cmdArray[1] = "mailto:?";
	
        try {
            Runtime.getRuntime().exec(cmdArray);
            Thread.sleep(2000);
        } catch (IOException e2) {
            throw new LaunchFailedException("Cannot launch Evolution composer via commandline:" + e2);
        } catch (InterruptedException iE) {}
    }
    
    /**
     * Constructs commandline Arguments according to the various fields' values
     *
     * @return mailto argument string
     */
    private String constructMailto(Iterator to, Iterator cc, Iterator bcc, String subject, String body, Iterator attach) {
        String mailto = new String();
      
        if(to != null) {
            while(to.hasNext()) {
                mailto = mailto + "to=" + ((String)to.next()) + "&";
            }
        }
	
        if(cc != null) {
            while(cc.hasNext()) {
                mailto = mailto + "cc=" + ((String)cc.next()) + "&";
            }
        }
	
        if(bcc != null) {
            while (bcc.hasNext()) {
                mailto = mailto + "bcc=" + ((String)bcc.next()) + "&";
            }
        }
        	
        if(subject != null)
            mailto = mailto + "subject=" + URLUTF8Encoder.encode(subject) + "&";
        if(body != null)
            mailto = mailto + "body=" + URLUTF8Encoder.encode(body) + "&";
	
        if(attach != null) {
            while(attach.hasNext()) {
                mailto = mailto + "attach=" + ((String)attach.next()) + "&";
            }
        }
	
        return mailto; 
     }
}

