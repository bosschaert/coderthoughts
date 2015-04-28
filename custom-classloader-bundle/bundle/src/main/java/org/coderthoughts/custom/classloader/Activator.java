package org.coderthoughts.custom.classloader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        Class<?> clz = com.sun.nio.file.ExtendedCopyOption.class;
        System.out.println("Class name: " + clz.getName());
        System.out.println("Classloader: " + clz.getClassLoader());

        URL url = getClass().getResource("/altjar/alt.jar");
        System.out.println("Alt jar: " + url);

        ClassLoader bl = new BlockingClassLoader(getClass().getClassLoader(), "com.sun.nio.file");
        URLClassLoader ccl = new URLClassLoader(new URL[] {url}, bl);
        Class<?> clz2 = ccl.loadClass("com.sun.nio.file.ExtendedCopyOption");
        System.out.println("Class name: " + clz2.getName());
        System.out.println("Classloader: " + clz2.getClassLoader());

        Method m = clz2.getDeclaredMethod("doit");
        System.out.println("Returned from custom class: " + m.invoke(null));
    }

    public void stop(BundleContext context) throws Exception {
    }
}