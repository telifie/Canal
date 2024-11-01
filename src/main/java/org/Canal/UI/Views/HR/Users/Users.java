package org.Canal.UI.Views.HR.Users;

import org.Canal.Models.HumanResources.User;
import org.Canal.UI.Elements.Button;
import org.Canal.UI.Views.HR.Employees.CreateEmployee;
import org.Canal.Utils.DesktopState;
import org.Canal.Utils.Engine;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * /USRS
 */
public class Users extends JInternalFrame {

    private DefaultListModel<User> listModel;

    public Users(DesktopState desktop) {
        setTitle("Users");
        setFrameIcon(new ImageIcon(Users.class.getResource("/icons/employees.png")));
        listModel = new DefaultListModel<>();
        JList<User> list = new JList<>(listModel);
        list.setCellRenderer(new UserRenderer());
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = list.locationToIndex(e.getPoint());
                    if (selectedIndex != -1) {
                        User item = listModel.getElementAt(selectedIndex);
                        Engine.router("/USRS/" + item.getId(), desktop);
                    }
                }
            }
        });
        JTextField direct = new JTextField();
        direct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String inputText = direct.getText().trim();
                    System.out.println(inputText);
                    if (!inputText.isEmpty()) {
                        Engine.router("/USRS/" + inputText, desktop);
                    }
                }
            }
        });
        Button nla = new Button("Add User");
        nla.addActionListener(_ -> desktop.put(new CreateEmployee(desktop)));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(direct, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(nla, BorderLayout.SOUTH);
        add(mainPanel);
        loadFlexes();
        setIconifiable(true);
        setClosable(true);
    }

    private void loadFlexes(){
        Engine.load();
        ArrayList<User> found = Engine.getUsers();
        listModel.removeAllElements();
        for (User f : found) {
            listModel.addElement(f);
        }
    }

    class UserRenderer extends JPanel implements ListCellRenderer<User> {

        private JLabel employeeId;
        private JLabel userId;
        private JLabel accessCount;

        public UserRenderer() {
            setLayout(new GridLayout(4, 1));
            employeeId = new JLabel();
            userId = new JLabel();
            add(employeeId);
            add(userId);
            setBorder(new EmptyBorder(5, 5, 5, 5));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected, boolean cellHasFocus) {
            employeeId.setText(value.getEmployee());
            userId.setText(value.getId());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }
}