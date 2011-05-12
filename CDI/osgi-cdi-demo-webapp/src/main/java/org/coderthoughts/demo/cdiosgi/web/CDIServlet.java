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
    Hello hello;

    @Inject
    MyInterface mi;

    @Inject
    OSGiPropertyProvider osgiPropertyProvider;

    @Inject
    WarBean warBean;

    // @Produces
    // public MyInterface createMyInterface() {
    // return new MyInterface() {
    // public String getValue() {
    // return "euuuuhh";
    // }
    // };
    // }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.print("hihaho " + hello.sayHelloWorld());
        writer.print("<p/>aha " + mi.getValue());
        writer.print("\nhey! " + osgiPropertyProvider.getProperty("org.osgi.framework.executionenvironment"));
        writer.print("\nInit param: myservletparam: " + getInitParameter("myservletparam"));
        writer.print("\nInit param: myservletparam: " + warBean.getValue());

        writer.flush();
        writer.close();
    }
}
