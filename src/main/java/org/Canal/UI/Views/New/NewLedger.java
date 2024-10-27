package org.Canal.UI.Views.New;

import org.Canal.Models.BusinessUnits.Ledger;
import org.Canal.Utils.RefreshListener;
import org.Canal.UI.Elements.Input;
import org.Canal.Utils.Pipe;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class NewLedger extends JInternalFrame {

    private RefreshListener refreshListener;

    public NewLedger() {
        this.refreshListener = refreshListener;
        setTitle("Create Ledger");
//        setIconImage(new ImageIcon(OrgView.class.getResource("/icons/add_ledger.png")).getImage());
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        Input name = new Input("Ledger Name");
        name.setValue("FY" + LocalDate.now().getYear());
        Input id = new Input("Ledger ID");
        id.setValue(String.valueOf(LocalDate.now().getYear()));
        JButton create = new JButton("Create");
        panel.add(name);
        panel.add(id);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(create, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(create);
        setResizable(false);
        create.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Ledger l = new Ledger(id.value(), name.value());
                Pipe.save("/LGS", l);
                if(refreshListener != null){
                    refreshListener.onRefresh();
                    dispose();
                }
            }
        });
    }
}