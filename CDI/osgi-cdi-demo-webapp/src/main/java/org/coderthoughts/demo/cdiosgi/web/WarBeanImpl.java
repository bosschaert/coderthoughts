package org.coderthoughts.demo.cdiosgi.web;

import org.coderthoughts.demo.cdiosgi.api.WarBean;

public class WarBeanImpl implements WarBean {
    public String getValue() {
        return "A value coming from a bean inside a war";
    }
}
