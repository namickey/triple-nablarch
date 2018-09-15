package com.nablarch.example.http.filter;

import nablarch.core.repository.SystemRepository;
import nablarch.fw.web.servlet.WebFrontController;

import javax.servlet.*;
import java.io.IOException;

public class HttpWebFrontController implements Filter {

    /** {@link WebFrontController} */
    private WebFrontController controller;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        controller = SystemRepository.get("httpFrontController");
        controller.setServletFilterConfig(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        controller.doFilter(servletRequest, servletResponse, filterChain);
    }

    @Override
    public void destroy() {
        controller.destroy();
    }
}
