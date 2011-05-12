package org.coderthoughts.demo.cdiosgi.web;

import org.coderthoughts.demo.cdiosgi.api.OSGiPropertyProvider;

public class MockOSGiPP implements OSGiPropertyProvider {

    public String getProperty(String name) {
        return "mocked!!!!";
    }

}
