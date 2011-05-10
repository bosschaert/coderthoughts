package org.coderthoughts.cdi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CDIServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    Hello hello;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.print("hehehehe " + hello.sayHelloWorld());
        writer.flush();
        writer.close();
    }
}
