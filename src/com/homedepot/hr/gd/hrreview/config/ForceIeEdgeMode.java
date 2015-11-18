package com.homedepot.hr.gd.hrreview.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ForceIeEdgeMode implements Filter {
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      if(response instanceof HttpServletResponse) {
             HttpServletResponse res = (HttpServletResponse) response;
             res.addHeader("X-UA-Compatible", "IE=Edge");
      }
        chain.doFilter(request, response);
    }

      @Override
    public void destroy() { 
    }

      @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}

