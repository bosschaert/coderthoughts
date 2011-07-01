package org.coderthoughts.mycloudapp.common;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends Frame {
    private static final long serialVersionUID = 1L;
    private TextArea textArea;

    public Window(String title) {
        super(title);

        try {
            setSize(300, 300);
            textArea = new TextArea();
            Font courier = new Font("Courier", Font.PLAIN, 12);
            textArea.setFont(courier);
            add(textArea, BorderLayout.CENTER);
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

    public void addText(String text) {
        textArea.append(text);
        textArea.append("\n");
    }

    public static void main(String ... args) {
        Window app = new Window("Testing");
        app.setVisible(true);
        app.addText("test");
        app.addText("testoo");
    }
}
