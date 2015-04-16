package org.coderthoughts.srperf.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

class MockBundleTools {
    private MockBundleTools() {}

    static Bundle startMockBundle(BundleContext ctx, String bsn) {
        Manifest mf = new Manifest();
        mf.getMainAttributes().putValue("Manifest-Version", "1.0");
        mf.getMainAttributes().putValue("Bundle-ManifestVersion", "2");
        mf.getMainAttributes().putValue("Bundle-SymbolicName", bsn);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JarOutputStream jos = new JarOutputStream(baos, mf);
            jos.close();

            Bundle b = ctx.installBundle("test:/" + bsn,
                    new ByteArrayInputStream(baos.toByteArray()));
            b.start();
            return b;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
