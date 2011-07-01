package org.coderthoughts.osgi.cloud.api;

public interface OSGiFrameworkPublisher {
    String getProperty(String key);

    /**
     * Set a property
     * @param key The key
     * @param value Set to null to remove the property.
     */
    void setProperty(String key, String value);
}
