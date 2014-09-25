package org.foo.bar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.junit.Test;

public class MyUnitTest {
    @Test
    public void testSimpleJSFunction() throws Exception {
        assertEquals("2", testJavaScript("function testFunction() { return 1+1; }"));
    }

    @Test
    public void testAJAX() throws Exception {
        String jsResult = testJavaScript("function testFunction() { "
                + "var xmlhttp = new XMLHttpRequest();"
                + "xmlhttp.onreadystatechange = function() {"
                + "  if (xmlhttp.readyState == 4) {"
                + "    if(xmlhttp.status == 200) {"
                + "      document.getElementById('test_result').innerHTML = xmlhttp.responseText;"
                + "    } else {"
                + "      document.getElementById('test_result').innerHTML = 'Something went wrong';"
                + "    }"
                + "  }"
                + "};"
                + "xmlhttp.open('GET', 'http://www.osgi.org/Main/HomePage', true);"
                + "xmlhttp.send();"
                + "}");
        assertTrue("Was: " + jsResult, jsResult.contains("The Dynamic Module System for Java"));
    }

    public String testJavaScript(String script) throws Exception {
        return testJavaScript(script, false);
    }

    public String testJavaScript(String script, boolean async) throws Exception {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html>"
                + "<body onload='myFunction()'>"
                + "<script>"
                + "function myFunction() { "
                + "  var res = testFunction();"
                + "  if (!(res === undefined)) {"
                + "    document.getElementById('test_result').innerHTML = res;"
                + "  }"
                + "}");
        html.append(script);
        html.append("</script>"
                + "<p id='test_result'>undefined</p>"
                + "</body>"
                + "</html>");

        File f = File.createTempFile("jstest-", ".tmp");

        try (OutputStream fos = new FileOutputStream(f)) {
            fos.write(html.toString().getBytes());
            fos.close();

            WebClient wc = new WebClient();
            HtmlPage page = wc.getPage(f.toURI().toURL());

            String result = page.getHtmlElementById("test_result").asText();
            int count = 30;
            while (count-- > 0 && "undefined".equals(result)) {
                Thread.sleep(500);
                result = page.getHtmlElementById("test_result").asText();
            }
            return result;
        } finally {
            f.delete();
        }
    }
}
