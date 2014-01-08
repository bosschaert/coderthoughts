package org.coderthoughts.jbosgi.repo.gogo.command;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

import org.jboss.osgi.repository.RepositoryReader;
import org.jboss.osgi.repository.RepositoryStorage;
import org.jboss.osgi.repository.RepositoryXMLReader;
import org.jboss.osgi.repository.XPersistentRepository;
import org.jboss.osgi.resolver.XResource;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.repository.Repository;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
    private ServiceTracker<Repository, Repository> repositoryTracker;

    @Override
    public void start(BundleContext context) throws Exception {
        Dictionary<String, Object> props = new Hashtable<String, Object>();
        props.put("osgi.command.function", new String[] { "addxml" });
        props.put("osgi.command.scope", "jbr");
        context.registerService(getClass().getName(), this, props);

        repositoryTracker = new ServiceTracker<Repository, Repository>(context, Repository.class.getName(), null);
        repositoryTracker.open();
    }

    public void addxml(String url) throws Exception {
        System.out.println("Adding repository.xml to JBoss OSGi Repository: " + url);

        XPersistentRepository xpr = null;
        for (Object svc : repositoryTracker.getServices()) {
            if (svc instanceof XPersistentRepository) {
                xpr = (XPersistentRepository) svc;
                break;
            }
        }
        if (xpr == null) {
            System.out.println("JBoss OSGi Repository Service not found");
            return;
        }

        RepositoryStorage rs = xpr.getRepositoryStorage();
        InputStream repoXmlStream = fixRelativeURLs(url);
        RepositoryReader reader = RepositoryXMLReader.create(repoXmlStream);
        XResource resource = reader.nextResource();
        while (resource != null) {
            System.out.println("  Adding resource: " + resource.getIdentityCapability().getSymbolicName());
            try {
                rs.addResource(resource);
            } catch (Exception e) {
                System.out.println("Resource not added: " + resource.getIdentityCapability().getSymbolicName() + " reason: " + e.getMessage());
            }
            resource = reader.nextResource();
        }
        System.out.println("Finished adding repository.xml");
    }

    private InputStream fixRelativeURLs(String url) throws Exception {
        // /* */ if (true) return new URL(url).openStream();


        int idx = url.lastIndexOf('/');
        String urlBase = url.substring(0, idx + 1);

        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS domLS = (DOMImplementationLS) registry.getDOMImplementation("LS");
        LSParser lsParser = domLS.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);

        Document doc = lsParser.parseURI(url);
        System.out.println("Parsed XML: " + doc.getDocumentElement().getChildNodes().getLength());

        XPath xp = XPathFactory.newInstance().newXPath();
        xp.setNamespaceContext(new MyNamespaceContext("http://www.osgi.org/xmlns/repository/v1.0.0", "r"));
        NodeList nodes = (NodeList) xp.evaluate("/r:repository/r:resource/r:capability[@namespace='osgi.content']/r:attribute[@name='url']",
                doc, XPathConstants.NODESET);

        boolean mods = false;
        for (int i=0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            NamedNodeMap attrs = node.getAttributes();
            Node val = attrs.getNamedItem("value");
            String value = val.getTextContent();
            if (value.indexOf(':') < 0) {
                // This is a relative URL
                mods = true;

                System.out.print("Updating osgi.content capability url from " + value + " to ");
                value = urlBase + value;
                System.out.println(value);
                val.setTextContent(value);
            }
        }

        if (mods) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            LSOutput lsOutput = domLS.createLSOutput();
            lsOutput.setEncoding("UTF-8");
            lsOutput.setByteStream(baos);

            LSSerializer lsSerializer = domLS.createLSSerializer();
            lsSerializer.write(doc, lsOutput);
            return new ByteArrayInputStream(baos.toByteArray());
        } else {
            return new URL(url).openStream();
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        repositoryTracker.close();
    }

    static class MyNamespaceContext implements NamespaceContext {
        private final String [] prefixes;
        private final String uri;

        MyNamespaceContext(String uri, String ... prefixes) {
            this.uri = uri;
            this.prefixes = prefixes;
        }

        @Override
        public String getNamespaceURI(String prefix) {
            return uri;
        }

        @Override
        public String getPrefix(String namespaceURI) {
            return prefixes[0];
        }

        @Override
        public Iterator<?> getPrefixes(String namespaceURI) {
            return Arrays.asList(prefixes).iterator();
        }

    }
}
