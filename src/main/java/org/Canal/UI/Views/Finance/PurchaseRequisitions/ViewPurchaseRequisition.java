package org.Canal.UI.Views.Finance.PurchaseRequisitions;

import org.Canal.Models.BusinessUnits.PurchaseOrder;
import org.Canal.Models.BusinessUnits.PurchaseRequisition;
import org.Canal.UI.Elements.*;
import org.Canal.UI.Views.ViewLocation;
import org.Canal.Utils.DesktopState;
import org.Canal.Utils.Engine;
import org.Canal.Utils.LockeStatus;
import org.Canal.Utils.RefreshListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * /ORDS/PR/$[PR_ID]
 */
public class ViewPurchaseRequisition extends LockeState {

    private DesktopState desktop;
    private PurchaseRequisition requisition;
    private RefreshListener refreshListener;

    public ViewPurchaseRequisition(PurchaseRequisition requisition, DesktopState desktop, RefreshListener refreshListener) {

        super("Purchase Requisitions", "/ORDS/PR/$", true, true, true, true);
        setFrameIcon(new ImageIcon(CreatePurchaseRequisition.class.getResource("/icons/purchasereqs.png")));
        this.requisition = requisition;
        this.desktop = desktop;
        this.refreshListener = refreshListener;

        CustomTabbedPane tabs = new CustomTabbedPane();
        tabs.addTab("Header Information", new ImageIcon(ViewLocation.class.getResource("/icons/info.png")), headerInfo());
        tabs.addTab("Purchase Orders", new ImageIcon(ViewLocation.class.getResource("/icons/purchaseorders.png")), purchaseOrders());
        tabs.addTab("Activity", new ImageIcon(ViewLocation.class.getResource("/icons/activity.png")), activity());

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
        add(headerPanel(), BorderLayout.NORTH);
    }

    private JPanel headerInfo() {

        Form f = new Form();
        f.addInput(Elements.coloredLabel("ID", UIManager.getColor("Label.foreground")), new Copiable(requisition.getId()));
        f.addInput(Elements.coloredLabel("Created", UIManager.getColor("Label.foreground")), new Copiable(requisition.getCreated()));
        f.addInput(Elements.coloredLabel("Creator (Owner)", UIManager.getColor("Label.foreground")),  new Copiable(requisition.getOwner()));
        f.addInput(Elements.coloredLabel("Purchase Req. #", UIManager.getColor("Label.foreground")),  new Copiable(requisition.getNumber()));
        f.addInput(Elements.coloredLabel("Supplier ID", UIManager.getColor("Label.foreground")),  new Copiable(requisition.getSupplier()));
        f.addInput(Elements.coloredLabel("Buyer", UIManager.getColor("Label.foreground")),  new Copiable(requisition.getBuyer()));
        f.addInput(Elements.coloredLabel("Max Spend", UIManager.getColor("Label.foreground")),  new Copiable(String.valueOf(requisition.getMaxSpend())));
        f.addInput(Elements.coloredLabel("Single Order?", UIManager.getColor("Label.foreground")),  new Copiable(String.valueOf(requisition.isSingleOrder())));
        f.addInput(Elements.coloredLabel("Valid From", UIManager.getColor("Label.foreground")),  new Copiable(requisition.getStart()));
        f.addInput(Elements.coloredLabel("To", UIManager.getColor("Label.foreground")),  new Copiable(requisition.getEnd()));
        f.addInput(Elements.coloredLabel("Notes", UIManager.getColor("Label.foreground")),  new Copiable(requisition.getNotes()));
        f.addInput(Elements.coloredLabel("Status", UIManager.getColor("Label.foreground")),  new Copiable(String.valueOf(requisition.getStatus())));
        f.addInput(Elements.coloredLabel("Created", UIManager.getColor("Label.foreground")),  new Copiable(requisition.getCreated()));
        return f;
    }

    private JScrollPane purchaseOrders(){

        String[] columns = new String[]{
                "ID",
                "Description",
                "Ordered",
                "Exp Delivery",
                "Bill To",
                "Ship To",
                "Sold To",
                "Trans",
                "Customer",
                "Vendor",
                "Items",
                "Net Value",
                "Tax Rate",
                "Tax Amount",
                "Total",
                "Status",
                "Created"
        };
        ArrayList<Object[]> data = new ArrayList<>();
        for (PurchaseOrder po : Engine.getPurchaseOrders()) {
            if(po.getPurchaseRequisition().equals(requisition.getNumber())) {
                data.add(new Object[]{
                        po.getId(),
                        po.getName(),
                        po.getOrderedOn(),
                        po.getExpectedDelivery(),
                        po.getBillTo(),
                        po.getShipTo(),
                        po.getSoldTo(),
                        po.getTransaction(),
                        po.getCustomer(),
                        po.getVendor(),
                        po.getItems().size(),
                        po.getNetValue(),
                        po.getTaxRate(),
                        po.getTaxAmount(),
                        po.getTotal(),
                        po.getStatus(),
                        po.getCreated()
                });
            }
        }
        CustomTable ibds = new CustomTable(columns, data);
        return new JScrollPane(ibds);
    }

    private JScrollPane activity(){

//        String[] columns = new String[]{
//                "User",
//                "Type",
//                "Created",
//                "Status",
//        };
//        ArrayList<Object[]> data = new ArrayList<>();
//        data.add(new Object[]{});
//        CustomTable ibds = new CustomTable(columns, data);
        return new JScrollPane();
    }

    private JPanel headerPanel(){

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.add(Elements.header("Viewing Pur Req " + requisition.getNumber(), SwingConstants.LEFT), BorderLayout.NORTH);

        JPanel tb = new JPanel();
        tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
        IconButton block = new IconButton("Block", "block", "Block/Pause PR, can't be used");
        IconButton suspend = new IconButton("Suspend", "suspend", "Suspend PR, can't be used");
        IconButton activate = new IconButton("Start", "start", "Resume/Activate PR");
        IconButton archive = new IconButton("Archive", "archive", "Archive PR");
        IconButton label = new IconButton("Barcodes", "label", "Print labels");
        IconButton print = new IconButton("Print", "print", "Print Pur. Req.");
        tb.add(block);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(suspend);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(activate);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(archive);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(label);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(print);
        tb.setBorder(new EmptyBorder(5, 5, 5, 5));
        block.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){

                int cnf = JOptionPane.showConfirmDialog(null, "Set Purchase Requisision Status to BLOCKED?", "Confirm status change?", JOptionPane.YES_NO_OPTION);
                if(cnf == JOptionPane.YES_OPTION) {
                    requisition.setStatus(LockeStatus.BLOCKED);
                    requisition.save();
                    if(refreshListener != null){
                        refreshListener.refresh();
                    }
                }
            }
        });
        suspend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){

                int cnf = JOptionPane.showConfirmDialog(null, "Set Purchase Requisision Status to SUSPENDED?", "Confirm status change?", JOptionPane.YES_NO_OPTION);
                if(cnf == JOptionPane.YES_OPTION) {
                    requisition.setStatus(LockeStatus.SUSPENDED);
                    requisition.save();
                    if(refreshListener != null){
                        refreshListener.refresh();
                    }
                }

            }
        });
        activate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){

                int cnf = JOptionPane.showConfirmDialog(null, "Set Purchase Requisision Status to ACTIVE?", "Confirm status change?", JOptionPane.YES_NO_OPTION);
                if(cnf == JOptionPane.YES_OPTION) {
                    requisition.setStatus(LockeStatus.ACTIVE);
                    requisition.save();
                    if(refreshListener != null){
                        refreshListener.refresh();
                    }
                }
            }
        });
        archive.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){

                int cnf = JOptionPane.showConfirmDialog(null, "If you archive this purchase requisition, it will no longer be able to be used.", "Confirm archival?", JOptionPane.YES_NO_OPTION);
                if(cnf == JOptionPane.YES_OPTION) {
                    requisition.setStatus(LockeStatus.ARCHIVED);
                    requisition.save();
                    if(refreshListener != null){
                        refreshListener.refresh();
                    }
                }
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){

            }
        });
        print.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){

            }
        });
        buttons.add(tb, BorderLayout.SOUTH);
        return buttons;
    }
}