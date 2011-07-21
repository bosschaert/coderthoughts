package org.coderthoughts.demo.web.osgi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class DemoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private BundleContext bundleContext;

    @Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		bundleContext = (BundleContext) config.getServletContext().getAttribute("osgi-bundlecontext");
	}

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.print("<HTML>Hello from an OSGi Web Application Bundle<p/>");
        
        writer.print("Using BundleContext looked up in Servlet Context to list available OSGi Bundles:<UL>");
        for (Bundle bundle : bundleContext.getBundles()) {
        	writer.print("<LI>" + bundle.getSymbolicName());
        }
        writer.write("</UL>");
        writer.close();
    }
}
