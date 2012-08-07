package org.coderthoughts.osgi.service.consumer;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConsumerWindow extends Frame {
    private static final long serialVersionUID = 1L;
    private TextArea textArea;

    public ConsumerWindow() {
        super("OSGi Service Consumer");
        setSize(600, 600);
        textArea = new TextArea();
        add(textArea, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
    }

    public void setText(String text) {
        textArea.setText(text);
    }
}
