package org.Canal.UI.Views.Finance.Customers;

import org.Canal.Models.SupplyChainUnits.Location;
import org.Canal.UI.Elements.Button;
import org.Canal.UI.Views.Managers.Controller;
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
 * /CSTS
 */
public class Customers extends JInternalFrame {

    private DefaultListModel<Location> listModel;

    public Customers(DesktopState desktop) {
        setTitle("Customers");
        setFrameIcon(new ImageIcon(Controller.class.getResource("/icons/customers.png")));
        listModel = new DefaultListModel<>();
        JList<Location> list = new JList<>(listModel);
        list.setCellRenderer(new CustomerRenderer());
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = list.locationToIndex(e.getPoint());
                    if (selectedIndex != -1) {
                        Location l = listModel.getElementAt(selectedIndex);
                        if (l != null) {
                            desktop.put(Engine.router("/CSTS/" + l.getId(), desktop));
                        } else {
                            JOptionPane.showMessageDialog(null, "Location Not Found");
                        }
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
                        desktop.put(Engine.router("/CSTS/" + inputText, desktop));
                    }
                }
            }
        });
        Button nla = new Button("Create Customer");
        nla.addActionListener(e -> desktop.put(Engine.router("/CSTS/NEW", desktop)));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(direct, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(nla, BorderLayout.SOUTH);
        add(mainPanel);
        setResizable(false);
        setIconifiable(true);
        setClosable(true);
        loadCustomers();
    }

    private void loadCustomers(){
        listModel.removeAllElements();
        Engine.load();
        ArrayList<Location> found = Engine.getCustomers();
        for (Location customer : found) {
            listModel.addElement(customer);
        }
    }

    class CustomerRenderer extends JPanel implements ListCellRenderer<Location> {

        private JLabel ccName;
        private JLabel ccId;
        private JLabel line1;
        private JLabel line2;

        public CustomerRenderer() {
            setLayout(new GridLayout(4, 1));
            ccName = new JLabel();
            ccId = new JLabel();
            line1 = new JLabel();
            line2 = new JLabel();
            ccName.setFont(new Font("Arial", Font.BOLD, 16));
            ccId.setFont(new Font("Arial", Font.PLAIN, 12));
            line1.setFont(new Font("Arial", Font.PLAIN, 12));
            line2.setFont(new Font("Arial", Font.PLAIN, 12));
            add(ccName);
            add(ccId);
            add(line1);
            add(line2);
            setBorder(new EmptyBorder(5, 5, 5, 5));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Location> list, Location value, int index, boolean isSelected, boolean cellHasFocus) {
            ccName.setText(value.getName());
            ccId.setText(value.getId());
            line1.setText(value.getLine1());
            line2.setText(value.getCity() + ", " + value.getState() + " " + value.getPostal() + " " + value.getCountry());
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