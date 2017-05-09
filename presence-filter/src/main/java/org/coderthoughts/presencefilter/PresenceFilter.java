package org.coderthoughts.presencefilter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

@Component(property = {HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN + "=/",
        Constants.SERVICE_RANKING + ":Integer=99999",
        HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT + "=(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME + "="
                + FilterServletContext.NAME + ")"})
public class PresenceFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        writer.write("<HTML><BODY><H1>YoHoo</H1></BODY></HTML>");

//        System.out.println("Hello!!");
//        chain.doFilter(req, response);
//        response.getWriter().write("<H2>BLHA!!</H2>");
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
