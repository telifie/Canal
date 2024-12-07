package org.Canal.UI.Views.Distribution.DistributionCenters;

import org.Canal.UI.Elements.Elements;
import org.Canal.UI.Elements.Windows.Form;
import org.Canal.UI.Elements.Label;
import org.Canal.UI.Elements.Windows.LockeState;
import org.Canal.UI.Views.Controllers.Controller;
import org.Canal.Utils.DesktopState;
import org.Canal.Utils.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /DCSS/F
 */
public class FindDistributionCenter extends LockeState {

    public FindDistributionCenter(DesktopState desktop) {
        super("Find DC", "/", false, true, false, true);
        setFrameIcon(new ImageIcon(Controller.class.getResource("/icons/find.png")));
        Form f = new Form();
        JTextField direct = new JTextField(10);
        direct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String inputText = direct.getText().trim();
                    if (!inputText.isEmpty()) {
                        Engine.router("/DCSS/" + direct.getText(), desktop);
                        dispose();
                    }
                }
            }
        });
        f.addInput(new Label("Dist. Center ID", UIManager.getColor("Label.foreground")), direct);
        JPanel main = new JPanel(new BorderLayout());
        main.add(f, BorderLayout.CENTER);
        JButton find = Elements.button("Open");
        main.add(find, BorderLayout.SOUTH);
        add(main);
        find.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                desktop.put(Engine.router("/DCSS/" + direct.getText(), desktop));
                dispose();
            }
        });
    }
}