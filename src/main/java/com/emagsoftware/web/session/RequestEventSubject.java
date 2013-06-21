/*
 * @(#)RequestEventContainer.java 1.0.0 12/11/16
 * Copyright 2012© Emagsoftware Technology Co., Ltd. All Rights reserved.
 */

package com.emagsoftware.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Event Observer Design pattern of Subject
 *
 * @author huzl
 * @version 1.0.0
 */
public class RequestEventSubject {
    private RequestEventObserver listener;

    public void attach(RequestEventObserver eventObserver) {
        listener = eventObserver;
    }

    public void detach() {
        listener = null;
    }

    public void completed(HttpServletRequest servletRequest, HttpServletResponse response) {
        if(listener != null)
            listener.completed(servletRequest, response);
    }
}
