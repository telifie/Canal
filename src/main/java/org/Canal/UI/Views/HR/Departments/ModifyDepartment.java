package org.Canal.UI.Views.HR.Departments;

import org.Canal.UI.Elements.Button;
import org.Canal.UI.Elements.Elements;
import org.Canal.Utils.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /DPTS/MOD
 */
public class ModifyDepartment extends JInternalFrame {

    public ModifyDepartment() {
        super("", false, true, false, true);
        setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
        setFrameIcon(new ImageIcon(ModifyDepartment.class.getResource("/icons/modify.png")));
        JTable table = createTable();
        setLayout(new BorderLayout());
        add(Elements.header("/DPTS OBJEX EDITOR", SwingConstants.LEFT), BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
        Button save = new Button("Save");
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        add(save, BorderLayout.SOUTH);
    }

    private JTable createTable() {
        String[] columns = new String[]{"Property", "Value"};
        String[][] data = {
                {"id", ""},
                {"organization", ""},
                {"location", ""},
                {"department", ""},
                {"name", ""},
        };
        JTable table = new JTable(data, columns);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Engine.adjustColumnWidths(table);
        return table;
    }
}