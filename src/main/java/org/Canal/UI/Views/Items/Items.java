package org.Canal.UI.Views.Items;

import org.Canal.Models.SupplyChainUnits.Item;
import org.Canal.UI.Elements.Elements;
import org.Canal.UI.Elements.Windows.LockeState;
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
 * /ITS
 */
public class Items extends LockeState {

    private DefaultListModel<Item> listModel;

    public Items(DesktopState desktop) {

        super("Items", "/ITS", false, true, false, true);
        setFrameIcon(new ImageIcon(Items.class.getResource("/icons/items.png")));
        listModel = new DefaultListModel<>();
        JList<Item> list = new JList<>(listModel);
        list.setCellRenderer(new ItemRenderer());
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = list.locationToIndex(e.getPoint());
                    if (selectedIndex != -1) {
                        Item item = listModel.getElementAt(selectedIndex);
                        desktop.put(Engine.router("/ITS/" + item.getId(), desktop));
                    }
                }
            }
        });
        JTextField direct = Elements.input();
        direct.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String inputText = direct.getText().trim();
                    if (!inputText.isEmpty()) {
                        desktop.put(Engine.router("/ITS/" + inputText, desktop));
                    }
                }
            }
        });
        setLayout(new BorderLayout());
        add(direct, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        loadFlexes();
    }

    private void loadFlexes(){
        ArrayList<Item> found = Engine.getItems();
        listModel.removeAllElements();
        for (Item f : found) {
            listModel.addElement(f);
        }
    }

    static class ItemRenderer extends JPanel implements ListCellRenderer<Item> {

        private JLabel itemName;
        private JLabel itemId;
        private JLabel itemPrice;

        public ItemRenderer() {
            setLayout(new GridLayout(3, 1));
            itemName = new JLabel();
            itemId = new JLabel();
            itemPrice = new JLabel();
            itemName.setFont(new Font("Arial", Font.BOLD, 16));
            itemId.setFont(new Font("Arial", Font.PLAIN, 12));
            itemPrice.setFont(new Font("Arial", Font.BOLD, 12));
            add(itemName);
            add(itemId);
            add(itemPrice);
            setBorder(new EmptyBorder(5, 5, 5, 5));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index, boolean isSelected, boolean cellHasFocus) {
            itemName.setText(value.getName());
            itemId.setText(value.getId());
            itemPrice.setText("$" + value.getPrice());
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