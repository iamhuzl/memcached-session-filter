package com.emagsoftware.web.session;

/**
 * SessionListener  interface adaptor
 *
 * @author huzl
 * @version 1.0.0
 */
public class SessionListenerAdaptor implements SessionListener {

    public void onAttributeChanged(MemcachedHttpSession session) {
    }

    public void onInvalidated(MemcachedHttpSession session) {
    }
}
