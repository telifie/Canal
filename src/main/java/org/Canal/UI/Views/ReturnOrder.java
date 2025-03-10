package org.Canal.UI.Views;

import org.Canal.Models.BusinessUnits.PurchaseOrder;
import org.Canal.UI.Elements.*;
import org.Canal.Utils.Constants;
import org.Canal.Utils.DesktopState;
import org.Canal.Utils.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /ORDS/RTRN
 */
public class ReturnOrder extends LockeState {

    private JTextField poField;
    private JTextField onField;
    private Selectable ats;
    private Selectable availablePutaway;

    public ReturnOrder(DesktopState desktop){
        super("Return Order", "/ORDS/RTRN", false, true, false, true);
        Constants.checkLocke(this, true, true);
        Form f = new Form();
        poField = Elements.input(12);
        onField = Elements.input(12);
        f.addInput(Elements.coloredLabel("Purchase Order #", Constants.colors[0]), poField);
        f.addInput(Elements.coloredLabel("[or] Order #", Constants.colors[1]), onField);
        ats = Selectables.allLocations();
        f.addInput(Elements.coloredLabel("Receiving Location", Constants.colors[2]), ats);
        f.addInput(Elements.coloredLabel("Putaway Area", Constants.colors[3]), availablePutaway);
        setLayout(new BorderLayout());
        add(f, BorderLayout.CENTER);
        JButton receive = Elements.button("Receive Order");
        receive.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String po = poField.getText();
                String on = onField.getText();
                String rlid = ats.getSelectedValue();
                if(po.isEmpty() && on.isEmpty()){
                    JOptionPane.showMessageDialog(null, "PO or Order Number required!", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    if(po.isEmpty()){
                        PurchaseOrder spo = Engine.orders.getPurchaseOrder(po);
                        if(spo == null){
                            JOptionPane.showMessageDialog(null, "PO or Order Number required!", "Error", JOptionPane.ERROR_MESSAGE);
                            if(!rlid.equals(spo.getVendor())){
                                int override = JOptionPane.showConfirmDialog(null, "The receiving location and buyer do not match. Override?");
                                if(override == JOptionPane.NO_OPTION){

                                }else{

                                }
                            }
                        }
                    }
                }
            }
        });
    }
}