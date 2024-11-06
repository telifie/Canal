package org.Canal.UI.Views.Transportation.OutboundDeliveryOrders;

import org.Canal.UI.Elements.Button;
import org.Canal.UI.Elements.Label;
import org.Canal.UI.Elements.Windows.Form;
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
 * /TRANS/ODO/F
 */
public class FindOutboundDeliveryOrder extends JInternalFrame {

    public FindOutboundDeliveryOrder(DesktopState desktop) {
        super("Find ODO", false, true, false, true);
        setFrameIcon(new ImageIcon(Controller.class.getResource("/icons/find.png")));
        JTextField direct = new JTextField(10);
        direct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String inputText = direct.getText().trim();
                    if (!inputText.isEmpty()) {
                        desktop.put(Engine.router("/TRANS/ODO/" + direct.getText(), desktop));
                        dispose();
                    }
                }
            }
        });
        Form f = new Form();
        f.addInput(new Label("Search Value", UIManager.getColor("Label.foreground")), direct);
        setLayout(new BorderLayout());
        add(f, BorderLayout.CENTER);
        Button find = new Button("Find");
        add(find, BorderLayout.SOUTH);
        find.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                desktop.put(Engine.router("/TRANS/ODO/" + direct.getText(), desktop));
                dispose();
            }
        });
    }
}