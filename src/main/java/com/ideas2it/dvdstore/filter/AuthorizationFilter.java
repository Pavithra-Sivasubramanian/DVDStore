package com.ideas2it.dvdstore.filter;

import java.io.IOException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ideas2it.dvdstore.common.Constants;
import com.ideas2it.dvdstore.log.DVDStoreLogger;

public class AuthorizationFilter implements Filter {
    private DVDStoreLogger logger = new DVDStoreLogger();
    
    public void init(FilterConfig config) throws ServletException {
    }
    
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getRequestURL().toString();
        HttpSession session = request.getSession(false);
        if (null == session) {
            if(!(url.endsWith("DVDStore"))) { 
                request.getRequestDispatcher("/")
                    .forward(request, response);
            } else {
                chain.doFilter(req, res);
            }
        } else if ((null == session.getAttribute("userId")) 
                && (!(url.endsWith("add"))) 
                && (null == request.getParameter("email")) )  {
            request.getRequestDispatcher("/")
                .forward(request, response);
        } else  {
            response.setHeader("Cache-Control", "no-cache, no-store"); 
            response.setHeader("Pragma", "no-cache"); 
            response.setDateHeader("Expires", 0);
            chain.doFilter(req, res);
        }
    }

    public void destroy() {
    }
}
