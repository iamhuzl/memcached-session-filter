/*
 * @(#)MemcachedHttpSession.java 1.0.0 12/11/16
 * Copyright 2012© Emagsoftware Technology Co., Ltd. All Rights reserved.
 */

package com.emagsoftware.web.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HttpSession Object in memcached
 *
 * @author huzl
 * @version 1.0.0
 */
public class MemcachedHttpSession implements HttpSession, Serializable {
    private static final long serialVersionUID = 1L;

    protected long creationTime = 0L;
    protected String id;
    protected int maxInactiveInterval;
    protected long lastAccessedTime = 0;
    protected transient boolean expired = false;
    protected transient boolean isNew = false;
    protected transient boolean isDirty = false;
    private transient SessionListener listener;

    private Map<String, Object> data = new HashMap<String, Object>();

    public void setListener(SessionListener listener) {
        this.listener = listener;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public String getId() {
        return id;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public void setMaxInactiveInterval(int i) {
        this.maxInactiveInterval = i;
    }

    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public HttpSessionContext getSessionContext() {
        return null;
    }

    public Object getAttribute(String key) {
        return data.get(key);
    }

    public Object getValue(String key) {
        return data.get(key);
    }

    public Enumeration getAttributeNames() {
        final Iterator iterator = data.keySet().iterator();
        return new Enumeration() {
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            public Object nextElement() {
                return iterator.next();
            }
        };
    }

    public String[] getValueNames() {
        String[] names = new String[data.size()];
        return data.keySet().toArray(names);
    }

    public void setAttribute(String s, Object o) {
        data.put(s, o);
        isDirty = true;
    }

    public void putValue(String s, Object o) {
        data.put(s, o);
        isDirty = true;
    }

    public void removeAttribute(String s) {
        data.remove(s);
        isDirty = true;
    }

    public void removeValue(String s) {
        data.remove(s);
        isDirty = true;
    }

    public void invalidate() {
        expired = true;
		isDirty = true;
        if(listener != null)
            listener.onInvalidated(this);
    }

    public boolean isNew() {
        return isNew;
    }


}
