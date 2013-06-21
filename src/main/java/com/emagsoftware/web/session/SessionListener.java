package com.emagsoftware.web.session;

/**
 * SessionListener
 *
 * @author huzl
 * @version 1.0.0
 */
public interface SessionListener {
    public void onAttributeChanged(MemcachedHttpSession session);
    public void onInvalidated(MemcachedHttpSession session);
}
