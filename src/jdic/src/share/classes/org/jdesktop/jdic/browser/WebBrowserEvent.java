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

package org.jdesktop.jdic.browser;

import java.util.EventObject;

/**
 * A <code>WebBrowserEvent</code> is dispatched by a <code>WebBrowser</code> object to 
 * indicate a defined WebBrowser event occured. The event is passed to every
 * <code>WebBrowserListener</code> object that registered to receive such events using 
 * WebBrowser's <code>addWebBrowserListener</code> method.
 * <p>
 * The object that implements the <code>WebBrowserListener</code> interface gets this 
 * <code>WebBrowserEvent</code> when the event occurs. The listener is therefore spared 
 * the details of processing individual WebBrowser events.
 * 
 * @see WebBrowserListener
 * @see WebBrowser
 * 
 * @author Kyle Yuan
 * @version 0.1, 03/07/17
 */ 
public class WebBrowserEvent extends EventObject
{
    private static final int WEBBROWSER_FIRST   = 3000;
    /**
     * Event fired before a navigation occurs in the given object (on either a window or frameset element).
     */
    public static final int WEBBROWSER_BEFORE_NAVIGATE         = 1 + WEBBROWSER_FIRST;
    /**
     * Event fired when a new window is to be created. 
     */
    public static final int WEBBROWSER_BEFORE_NEWWINDOW        = 2 + WEBBROWSER_FIRST;
    /**
     * Event fired when a navigation operation is beginning. 
     */
    public static final int WEBBROWSER_DOWNLOAD_STARTED        = 3 + WEBBROWSER_FIRST;
    /**
     * Event fired when a navigation operation finishes, is halted, or fails. 
     * This may be fired multiple times if a document has pop-up windows 
     * or frames.
     */
    public static final int WEBBROWSER_DOWNLOAD_COMPLETED      = 4 + WEBBROWSER_FIRST;
    /**
     * Event fired when the progress of a navigation operation is updated 
     * on the object. 
     */
    public static final int WEBBROWSER_DOWNLOAD_PROGRESS       = 5 + WEBBROWSER_FIRST;
    /**
     * Event fired when an error occurs during a navigation operation.
     */
    public static final int WEBBROWSER_DOWNLOAD_ERROR          = 6 + WEBBROWSER_FIRST;
    /**
     * Event fired when the document has loaded completely.
     */
    public static final int WEBBROWSER_DOCUMENT_COMPLETED      = 7 + WEBBROWSER_FIRST;

    /**
     * Event fired when the current URL is requested by a <code>WebBrowser</code>
     * object's <code>getURL</code> method. 
     */
    public static final int WEBBROWSER_RETURN_URL              = 21 + WEBBROWSER_FIRST;
    /**
     * Event fired when the enabled state of a command changes.
     */
    public static final int WEBBROWSER_COMMAND_STATE_CHANGE    = 22 + WEBBROWSER_FIRST;
    /**
     * Event fired when the title of a document changes.
     */
    public static final int WEBBROWSER_TITLE_CHANGE            = 23 + WEBBROWSER_FIRST;
    /**
     * Event fired when the status bar text changes.
     */
    public static final int WEBBROWSER_STATUSTEXT_CHANGE       = 24 + WEBBROWSER_FIRST;

    /**
     * Event fired when initialization of WebBrowser fails.
     */
    public static final int WEBBROWSER_INIT_FAILED             = 40 + WEBBROWSER_FIRST;    
    /**
     * Event fired when initialization of WebBrowser Window succeeds.
     */
    public static final int WEBBROWSER_INIT_WINDOW_SUCC        = 41 + WEBBROWSER_FIRST;    
    /**
     * Event fired when WebBrowser need to get Focus.
     */
    public static final int WEBBROWSER_FOCUS_REQUEST           = 42 + WEBBROWSER_FIRST;
    
    /**
     * The event's id.
     */
    int id;
    /**
     * Content of the event
     */
    String data;

    /**
     * Constructs a <code>WebBrowserEvent</code> object with source and event id.
     *
     * @param source the WebBrowser which owns this event.
     * @param id the id of the event.
     */
    public WebBrowserEvent(WebBrowser source, int id) {
        this(source, id, null);
    }

    /**
     * Constructs a <code>WebBrowserEvent</code> object with source, event id and event data.
     *
     * @param source the WebBrowser which owns this event.
     * @param id the id of the event.
     * @param data the data of the event.
     */
    public WebBrowserEvent(WebBrowser source, int id, String data) {
        super(source);
        this.id = id;
        this.data = data;
    }

    /**
     * Returns the event ID.
     */
    public int getID() {
        return id;
    }

    /**
     * Returns the event data.
     */
    public String getData() {
        return data;
    }
}
