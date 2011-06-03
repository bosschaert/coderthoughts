package org.coderthoughts.asciipics.client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class ClientWindow extends Frame {
    private static final long serialVersionUID = 1L;
    private TextArea textArea;

    public ClientWindow() {
        super("OSGi Service Consumer");
        try {
            setSize(600, 600);
            textArea = new TextArea();
            Font courier = new Font("Courier", Font.PLAIN, 12);
            textArea.setFont(courier);
            add(textArea, BorderLayout.CENTER);
            // ta.setEditable(false);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    setVisible(false);
                }
            });
            setVisible(true);
        } catch (Throwable th) {
            // need to sort this out
        }
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public static void main(String[] args) {
        ClientWindow app = new ClientWindow();
        app.setVisible(true);
        app.setText("Now: " + new Date() + "\n" + "Hello");
    }
}
