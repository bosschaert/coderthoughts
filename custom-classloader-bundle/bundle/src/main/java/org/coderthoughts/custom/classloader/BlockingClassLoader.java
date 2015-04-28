package org.coderthoughts.custom.classloader;

import java.security.SecureClassLoader;

public class BlockingClassLoader extends SecureClassLoader {
    private final String[] packageBlocks;

    public BlockingClassLoader(ClassLoader parent, String ... packageBlocks) {
        super(parent);

        this.packageBlocks = packageBlocks;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        int idx = name.lastIndexOf('.');
        if (idx > 0) {
            String pkgName = name.substring(0, idx);
            for (String s : packageBlocks) {
                if (s.equals(pkgName)) {
                    System.out.println("Blocking class load for: " + name + " from going up in the tree");
                    throw new ClassNotFoundException(name);
                }
            }
        }

        return super.loadClass(name, resolve);
    }
}
