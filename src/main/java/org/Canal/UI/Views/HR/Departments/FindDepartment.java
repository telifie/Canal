package org.Canal.UI.Views.HR.Departments;

import org.Canal.UI.Elements.Elements;
import org.Canal.UI.Elements.Form;
import org.Canal.UI.Elements.Label;
import org.Canal.UI.Elements.LockeState;
import org.Canal.UI.Views.Controllers.Controller;
import org.Canal.Utils.DesktopState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /DPTS/F
 */
public class FindDepartment extends LockeState {

    public FindDepartment(DesktopState desktop) {
        super("Find Department", "/DPTS/F", false, true, false, true);
        setFrameIcon(new ImageIcon(Controller.class.getResource("/icons/departments.png")));
        JTextField direct = new JTextField(10);
        direct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String inputText = direct.getText().trim();
                    if (!inputText.isEmpty()) {
                        //TODO Logic
                        dispose();
                    }
                }
            }
        });
        Form f = new Form();
        f.addInput(new Label("Department Name [or] ID", UIManager.getColor("Label.foreground")), direct);
        setLayout(new BorderLayout());
        add(f, BorderLayout.CENTER);
        JButton find = Elements.button("Find");
        add(find, BorderLayout.SOUTH);
        find.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //TODO Logic
                dispose();
            }
        });
        setClosable(true);
    }
}