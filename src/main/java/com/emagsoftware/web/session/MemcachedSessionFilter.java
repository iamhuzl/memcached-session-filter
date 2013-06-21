/*
 * @(#)MemcachedSessionFilter.java 1.0.0 12/11/16
 * Copyright 2012© Emagsoftware Technology Co., Ltd. All Rights reserved.
 */

package com.emagsoftware.web.session;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * rewrite HttpServletRequest,change getSession method,use memcached session implement
 *
 * @author huzl
 * @version 1.0.0
 */

public class MemcachedSessionFilter implements Filter {
    public static final String[] IGNORE_SUFFIX = new String[]{".png", ".jpg", ".jpeg", ".gif", ".css", ".js", ".html", ".htm"};
    private MemcachedSessionManager sessionManager;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void setSessionManager(MemcachedSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //igonre image,css or javascript file request
        if (shouldFilter(request) == false) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        RequestEventSubject eventSubject = new RequestEventSubject();
        SessionHttpServletRequestWrapper requestWrapper = new SessionHttpServletRequestWrapper(request, response, sessionManager, eventSubject);
        try {
            filterChain.doFilter(requestWrapper, servletResponse);
        } finally {
            //when request is completed,refresh session event,write cookie or save into memcached
            eventSubject.completed(request, response);
        }
    }

    /**
     * igonre image,css or javascript file request
     *
     * @param request HttpServletRequest
     * @return
     */
    private boolean shouldFilter(HttpServletRequest request) {
        String uri = request.getRequestURI().toLowerCase();
        for (String suffix : IGNORE_SUFFIX) {
            if (uri.endsWith(suffix)) return false;
        }
        return true;
    }

    public void destroy() {

    }
}
