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

        String classRes = getClass().getName().replace('.', '/') + ".class";
        URL res = getClass().getResource("/" + classRes);
        System.out.println("Res: " + res);
        String rss = res.toString();
        String res2 = rss.substring(0, rss.length() - (classRes.length() + 1));
        System.out.println("Res2: " + res2);
        URL url3 = new URL("jar:" + res2 + "/org/coderthoughts/custom/classloader/");
        System.out.println("URL3: " + url3);
        URLClassLoader cl3 = new URLClassLoader(new URL[] {url3}, ClassLoader.getSystemClassLoader());
        System.out.println(cl3.getResource("Activator.class"));

        URL res3 = new URL("file:/Users/David/clones/coderthoughts/custom-classloader-bundle/bundle/target/custom-classloader-bundle-1.0.0-SNAPSHOT.jar");
        URL url4 = new URL("jar:" + res3 + "!/org/coderthoughts/custom/classloader/");
        URLClassLoader cl4 = new URLClassLoader(new URL[] {url4}, ClassLoader.getSystemClassLoader());
        System.out.println(cl4.getResource("Activator.class"));
    }

    public void stop(BundleContext context) throws Exception {
    }
}