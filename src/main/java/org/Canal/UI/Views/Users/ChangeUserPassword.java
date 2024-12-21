package org.Canal.UI.Views.Users;

import org.Canal.UI.Elements.Elements;
import org.Canal.UI.Elements.Copiable;
import org.Canal.UI.Elements.Label;
import org.Canal.UI.Elements.Form;
import org.Canal.UI.Elements.LockeState;
import org.Canal.Utils.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

/**
 * /USRS/CHG_PSSWD
 */
public class ChangeUserPassword extends LockeState {

    public ChangeUserPassword(){
        super("Change Password", "/USRS/CHG_PSSWD", false, true, false, true);
        setFrameIcon(new ImageIcon(ChangeUserPassword.class.getResource("/icons/login.png")));
        if(Engine.getEmployees().isEmpty()){
            JOptionPane.showMessageDialog(null, "No employees to attach to!");
            try {
                setClosed(true);
            } catch (PropertyVetoException e) {
                throw new RuntimeException(e);
            }
        }
        JPasswordField currentPasswordField = new JPasswordField(15);
        JPasswordField newPasswordField = new JPasswordField(15);
        JPasswordField repeatNewPasswordField = new JPasswordField(15);
        Form f = new Form();
        f.addInput(new Label("User ID", UIManager.getColor("Label.foreground")), new Copiable(Engine.getAssignedUser().getId()));
        f.addInput(new Label("Current Password", UIManager.getColor("Label.foreground")), currentPasswordField);
        f.addInput(new Label("New Password", UIManager.getColor("Label.foreground")), newPasswordField);
        f.addInput(new Label("Repeat New Password", UIManager.getColor("Label.foreground")), repeatNewPasswordField);
        JButton updateUserPassword = Elements.button("Update Password");
        setLayout(new BorderLayout());
        add(f, BorderLayout.CENTER);
        add(updateUserPassword, BorderLayout.SOUTH);
        updateUserPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
}