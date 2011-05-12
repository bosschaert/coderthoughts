package org.coderthoughts.demo.cdiosgi.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.coderthoughts.demo.cdiosgi.api.OSGiPropertyProvider;
import org.coderthoughts.demo.cdiosgi.api.WarBean;

public class CDIServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    OSGiPropertyProvider osgiPropertyProvider;

    @Inject
    WarBean warBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.print("Coming From OSGi org.osgi.framework.executionenvironment property value: "
                + osgiPropertyProvider.getProperty("org.osgi.framework.executionenvironment"));
        writer.print("\nWarBean value: " + warBean.getValue());

        writer.flush();
        writer.close();
    }
}
