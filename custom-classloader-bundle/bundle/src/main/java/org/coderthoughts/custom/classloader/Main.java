package org.coderthoughts.custom.classloader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {
    public static void main(String ... args) throws Exception {
        URL res = Main.class.getResource("/altjar/alt.jar");
        System.out.println("URL: " + res);
        URLClassLoader cl = new URLClassLoader(new URL[] {res}, Main.class.getClassLoader());
        System.out.println(cl.getResource("org/blah/FooBar.class"));

        URL embedded = new URL("jar:" + res + "!/org/blah/");
        URLClassLoader cl2 = new URLClassLoader(new URL[] {embedded}, Main.class.getClassLoader());
        System.out.println(cl2.getResource("FooBar.class"));
        Class<?> fooBar = cl2.loadClass("FooBar");
        Method m = fooBar.getMethod("go");
        System.out.println("Method result go(): " + m.invoke(null));
    }
}
