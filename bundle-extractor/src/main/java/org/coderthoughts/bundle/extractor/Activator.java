package org.coderthoughts.bundle.extractor;

import javax.swing.JOptionPane;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        String result = JOptionPane.showInputDialog("Specify target location for bundles");
        System.out.println("Storing bundles in: " + result);
    }

    @Override
    public void stop(BundleContext context) throws Exception {

    }

}
