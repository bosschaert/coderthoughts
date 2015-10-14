package org.coderthoughts.servlet.recording;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RecordingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    List<String> invocations = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null)
            pathInfo = "";
        if (pathInfo.startsWith("/"))
            pathInfo = pathInfo.substring(1);

        String res;

        switch (pathInfo) {
        case "":
            res = listRecordings();
            break;
        default:
            res = recordInvocation(req);
            break;
        }

        resp.setHeader("Content-Type", "text/html");
        resp.setHeader("Content-Length", Integer.toString(res.length()));

        resp.getWriter().write(res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private synchronized String recordInvocation(HttpServletRequest req) throws IOException {
        String qs = req.getQueryString();
        if (qs == null)
            qs = "";

        InputStream is = req.getInputStream();
        Scanner scanner = new Scanner(is);
        scanner.useDelimiter("\\A");

        String s = "" + new Date() + " " + req.getPathInfo() + "?" + qs + "#" + (scanner.hasNext() ? scanner.next() : "");
        scanner.close();
        is.close();

        invocations.add(s);
        return "Invocation recorded: " + s;
    }

    private synchronized String listRecordings() {
        StringBuilder sb = new StringBuilder("<H1>Recorded invocations</H1>");

        sb.append("<small><ul>");
        for (String s : invocations) {
            sb.append("<li>" + s);
        }
        sb.append("</ul></small>");
        return sb.toString();
    }
}
