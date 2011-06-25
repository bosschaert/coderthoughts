package org.coderthoughts.demo.web.osgi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.coderthoughts.demo.web.osgi.api.StockQuote;

public class DemoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        try {
            writer.print("<HTML>Obtaining stock quote for @@@@@@@@ use proper data type! 'ACME'<p/>");
            StockQuote quote = OSGiServiceConnector.getInvoker().getStockQuote("ACME");

            writer.print("ACME Price: " + quote.getCurrency() + " " + quote.getPrice());
            writer.print(" (delay: " + quote.getDelay() + " minutes)</HTML>");
        } catch (Exception e) {
            e.printStackTrace(writer);
        } finally {
            writer.close();
        }
    }
}
