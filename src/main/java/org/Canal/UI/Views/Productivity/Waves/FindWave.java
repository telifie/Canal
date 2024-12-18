package org.Canal.UI.Views.Productivity.Waves;

import org.Canal.UI.Elements.Elements;
import org.Canal.UI.Elements.Label;
import org.Canal.UI.Elements.Form;
import org.Canal.UI.Elements.LockeState;
import org.Canal.UI.Views.Controllers.Controller;
import org.Canal.Utils.DesktopState;
import org.Canal.Utils.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /MVMT/WVS/F
 */
public class FindWave extends LockeState {

    public FindWave(DesktopState desktop) {
        super("Find Wave", "/MVMT/WVS/F", false, true, false, true);
        setFrameIcon(new ImageIcon(Controller.class.getResource("/icons/find.png")));
        Form f = new Form();
        JTextField empIdField = new JTextField(10);
        JTextField empNameField = new JTextField(10);
        JTextField empFindLimit = new JTextField("1", 10);
        f.addInput(new Label("User ID", UIManager.getColor("Label.foreground")), empNameField);
        f.addInput(new Label("[or] Name", UIManager.getColor("Label.foreground")), empIdField);
        f.addInput(new Label("Hit Limit", UIManager.getColor("Label.foreground")), empFindLimit);
        JPanel main = new JPanel(new BorderLayout());
        main.add(f, BorderLayout.CENTER);
        JButton find = Elements.button("Find");
        main.add(find, BorderLayout.SOUTH);
        add(main);
        find.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                desktop.put(Engine.router("/MVMT/" + empIdField.getText(), desktop));
                dispose();
            }
        });
    }
}