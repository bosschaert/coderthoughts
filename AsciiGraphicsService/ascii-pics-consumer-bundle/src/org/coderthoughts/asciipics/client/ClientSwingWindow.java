package org.coderthoughts.asciipics.client;

import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ClientSwingWindow {
    private JTextArea textArea;
    private JFrame frame;

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
     */
    public void createAndShow() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("OSGi Client Consumer");
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                textArea = new JTextArea();
                // Add the ubiquitous "Hello World" label.
                // JLabel label = new JLabel("Hello World");
                frame.getContentPane().add(textArea);

                // Display the window.
                frame.pack();
                frame.setSize(400, 400);
                frame.setVisible(true);
            }
        });
    }

    public void setText(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.setText(text);
            }
        });
    }

    public void hide() {
        frame.setVisible(false);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClientSwingWindow window = new ClientSwingWindow();
                window.createAndShow();
                window.setText("Hi: " + new Date());
            }
        });
    }
}
