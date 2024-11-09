package org.Canal.UI.Views.Finance.CostCenters;

import org.Canal.Models.SupplyChainUnits.Location;
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
 * /CCS
 */
public class CostCenters extends JInternalFrame {

    private DefaultListModel<Location> listModel;

    public CostCenters(DesktopState desktop) {
        super("Cost Centers", false, true, false, true);
        setFrameIcon(new ImageIcon(CostCenters.class.getResource("/icons/distribution_centers.png")));
        listModel = new DefaultListModel<>();
        JList<Location> list = new JList<>(listModel);
        list.setCellRenderer(new CostCenterRenderer());
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
                            desktop.put(Engine.router("/CCS/" + l.getId(), desktop));
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
                    if (!inputText.isEmpty()) {
                        desktop.put(Engine.router("/CCS/" + inputText, desktop));
                    }
                }
            }
        });
        setLayout(new BorderLayout());
        add(direct, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        loadLocations();
    }

    private void loadLocations(){
        listModel.removeAllElements();
        ArrayList<Location> found = Engine.getCostCenters();
        for (Location loc : found) {
            listModel.addElement(loc);
        }
    }

    static class CostCenterRenderer extends JPanel implements ListCellRenderer<Location> {

        private JLabel ccName;
        private JLabel ccId;
        private JLabel line1;
        private JLabel line2;

        public CostCenterRenderer() {
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