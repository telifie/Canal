package org.Canal.UI.Elements;

import org.Canal.Utils.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Copiable extends JTextField {
    public Copiable(String value) {
        super(value);
        setFont(new Font(UIManager.getFont("Label.font").getName(), Font.PLAIN, Engine.getConfiguration().getFontSize()));
        setEditable(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String text = getText();
                    copyToClipboard(text);
                }
            }
        });
    }

    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        System.out.println("Copied to clipboard: " + text);
    }

    public String value() {
        return getText();
    }
}