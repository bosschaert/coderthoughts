package org.coderthoughts.demo.cdiosgi.api;

/** Provides OSGi Framework Properties. */
public interface OSGiPropertyProvider {
    String getProperty(String name);
}
