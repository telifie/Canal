package org.Canal.UI.Views.Finance.SalesOrders;

import org.Canal.Models.BusinessUnits.*;
import org.Canal.Models.SupplyChainUnits.Item;
import org.Canal.UI.Elements.*;
import org.Canal.UI.Views.Controllers.Controller;
import org.Canal.Utils.Constants;
import org.Canal.Utils.Engine;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * /ORDS/SO/NEW
 */
public class ViewSalesOrder extends LockeState {

    private SalesOrder salesOrder;
    private ItemTableModel model;
    private double taxRate = 0.05;
    private JLabel netValue;
    private JLabel taxAmount;
    private JLabel totalAmount;
    private Selectable availablePurchaseRequisitions, selectSupplier, selectBillTo, selectShipTo, organizations, outboundCarriers, inboundCarriers, buyerObjexType, ledgers;
    private Copiable orderId;
    private DatePicker expectedDelivery;
    private JCheckBox commitToLedger, createOutboundDelivery, createPurchaseOrder, createInboundDelivery;
    private JTextField outboundTruckId, inboundTruckId;

    public ViewSalesOrder(SalesOrder salesOrder) {

        super("Create Sales Order", "/ORDS/SO/NEW", false, true, false, true);
        setFrameIcon(new ImageIcon(Controller.class.getResource("/icons/create.png")));
        this.salesOrder = salesOrder;

        CustomTabbedPane tabs = new CustomTabbedPane();
        tabs.addTab("Item Details", itemDetails());
        tabs.addTab("Delivery", deliveryDetails());
        tabs.addTab("Ledger", ledgerDetails());
        tabs.addTab("Purchase Order", purchaseOrderDetails());

        JPanel coreValues = orderInfoPanel();
        JPanel moreInfo = moreOrderInfoPanel();
        selectBillTo.setSelectedValue(salesOrder.getBillTo());
        selectShipTo.setSelectedValue(salesOrder.getShipTo());
        coreValues.setBorder(new EmptyBorder(10, 10, 10, 10));
        moreInfo.setBorder(new EmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());
        JPanel orderInfo = new JPanel(new BorderLayout());
        orderInfo.add(coreValues, BorderLayout.WEST);
        orderInfo.add(moreInfo, BorderLayout.EAST);
        add(orderInfo, BorderLayout.NORTH);

        add(tabs, BorderLayout.CENTER);
        JPanel orderSummary = new JPanel(new BorderLayout());
        JPanel genSummary = orderSummary();
        orderSummary.add(genSummary, BorderLayout.CENTER);
        add(orderSummary, BorderLayout.SOUTH);
        model.addTableModelListener(_ -> updateTotal());
    }

    public void setSelectedSupplier(String supplierId){
        selectSupplier.setSelectedValue(supplierId);
    }

    private JPanel orderInfoPanel(){
        Form f = new Form();
        selectBillTo = Selectables.allLocations();
        selectBillTo.editable();
        selectShipTo = Selectables.allLocations();
        selectShipTo.editable();
        selectSupplier = Selectables.allLocations();
        selectSupplier.editable();
        orderId = new Copiable("SO" + (60000000 + (Engine.orders.getSalesOrders().size() + 1)));
        f.addInput(Elements.coloredLabel("*Order ID", Constants.colors[0]), orderId);
        f.addInput(Elements.coloredLabel("Supplier", Constants.colors[1]), selectSupplier);
        f.addInput(Elements.coloredLabel("Bill To", Constants.colors[2]), selectBillTo);
        f.addInput(Elements.coloredLabel("Ship To", Constants.colors[3]), selectShipTo);
        return f;
    }

    private JPanel moreOrderInfoPanel(){
        Form f = new Form();
        JTextField ordered = new Copiable(LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        expectedDelivery = new DatePicker();
        f.addInput(Elements.coloredLabel("*Ordered", UIManager.getColor("Label.foreground")), ordered);
        f.addInput(Elements.coloredLabel("Expected Delivery", UIManager.getColor("Label.foreground")), expectedDelivery);
        f.addInput(Elements.coloredLabel("Status", UIManager.getColor("Label.foreground")), new Copiable("DRAFT"));
        return f;
    }

    private JPanel orderSummary(){
        Form f = new Form();
        DecimalFormat df = new DecimalFormat("#0.00");
        netValue = new JLabel("$" + model.getTotalPrice());
        netValue.setFont(UIManager.getFont("h3.font"));
        f.addInput(new JLabel("Net Value"), netValue);
        taxAmount = new JLabel("$" + df.format(taxRate * Double.parseDouble(model.getTotalPrice())));
        taxAmount.setFont(UIManager.getFont("h3.font"));
        f.addInput(new JLabel("Tax Amount"), taxAmount);
        totalAmount = Elements.h2("$" + df.format(taxRate * Double.parseDouble(model.getTotalPrice()) + Double.parseDouble(model.getTotalPrice())));
        totalAmount.setForeground(new Color(33, 124, 13));
        f.addInput(Elements.h2("Total"), totalAmount);
        return f;
    }

    private void updateTotal(){
        DecimalFormat df = new DecimalFormat("#0.00");
        netValue.setText("$" + model.getTotalPrice());
        taxAmount.setText("$" + df.format(taxRate * Double.parseDouble(model.getTotalPrice())));
        totalAmount.setText("$" + df.format(taxRate * Double.parseDouble(model.getTotalPrice()) + Double.parseDouble(model.getTotalPrice())));
    }

    private JPanel itemDetails(){
        JPanel p = new JPanel(new BorderLayout());
        ArrayList<Item> items = Engine.products.getProducts();
        if(items.isEmpty()){
            JOptionPane.showMessageDialog(this, "No products found", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
        model = new ItemTableModel(Collections.singletonList(items.getFirst()));
        JTable table = new JTable(model);
        TableColumn col1 = table.getColumnModel().getColumn(1);
        TableColumn col2 = table.getColumnModel().getColumn(2);
        TableColumn col3 = table.getColumnModel().getColumn(3);
        TableColumn col4 = table.getColumnModel().getColumn(4);

        TableCellRenderer centerRenderer = new CenteredRenderer();
        col1.setCellRenderer(centerRenderer);
        col2.setCellRenderer(centerRenderer);
        col3.setCellRenderer(centerRenderer);
        col4.setCellRenderer(centerRenderer);
        JComboBox<Item> itemComboBox = new JComboBox<>(items.toArray(new Item[0]));
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(itemComboBox));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(UIManager.getColor("Panel.background"));
        IconButton addButton = new IconButton("", "add_rows", "Add products");
        addButton.addActionListener((ActionEvent _) -> {
            if (!items.isEmpty()) {
                model.addRow(items.getFirst());
            }
        });
        IconButton removeButton = new IconButton("", "delete_rows", "Remove selected product");
        removeButton.addActionListener((ActionEvent _) -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
            }
        });
        buttonPanel.add(removeButton);
        buttonPanel.add(addButton);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(buttonPanel, BorderLayout.NORTH);
        return p;
    }

    private JPanel deliveryDetails(){
        Form p = new Form();
        createOutboundDelivery = new JCheckBox();
        if((boolean) Engine.codex("ORDS/SO", "use_deliveries")){
            createOutboundDelivery.setSelected(true);
            createOutboundDelivery.setEnabled(false);
        }
        outboundCarriers = Selectables.carriers();
        outboundTruckId = Elements.input();
        p.addInput(Elements.coloredLabel("Create Outbound Delivery (ODO) for Supplier", Constants.colors[9]), createOutboundDelivery);
        p.addInput(Elements.coloredLabel("Carrier", Constants.colors[8]), outboundCarriers);
        p.addInput(Elements.coloredLabel("Truck ID/Number", Constants.colors[7]), outboundTruckId);
        return p;
    }

    private JPanel ledgerDetails(){
        Form f = new Form();
        commitToLedger = new JCheckBox();
        if((boolean) Engine.codex("ORDS/SO", "commit_to_ledger")){
            commitToLedger.setSelected(true);
            commitToLedger.setEnabled(false);
        }
        organizations = Selectables.organizations();
        buyerObjexType = Selectables.locationObjex("/CCS");
        ledgers = Selectables.ledgers();
        f.addInput(Elements.coloredLabel("Commit to Ledger", Constants.colors[9]), commitToLedger);
        f.addInput(Elements.coloredLabel("Trans. Type (Receiving location type)", Constants.colors[8]), buyerObjexType);
        f.addInput(Elements.coloredLabel("Purchasing Org.", Constants.colors[7]), organizations);
        f.addInput(Elements.coloredLabel("Ledger", Constants.colors[6]), ledgers);
        return f;
    }

    private JPanel purchaseOrderDetails(){
        Form f = new Form();
        createPurchaseOrder = new JCheckBox();
        if((boolean) Engine.codex("ORDS/SO", "auto_create_po")){
            createPurchaseOrder.setSelected(true);
            createPurchaseOrder.setEnabled(false);
        }
        HashMap<String, String> prs = new HashMap<>();
        for(PurchaseRequisition pr1 : Engine.orders.getPurchaseRequisitions()){
            prs.put(pr1.getId(), pr1.getId());
        }
        availablePurchaseRequisitions = new Selectable(prs);
        availablePurchaseRequisitions.editable();
        createInboundDelivery = new JCheckBox();
        if((boolean) Engine.codex("ORDS/SO", "create_buyer_inbound")){
            createInboundDelivery.setSelected(true);
            createInboundDelivery.setEnabled(false);
        }
        inboundCarriers = Selectables.carriers();
        inboundTruckId = Elements.input();
        f.addInput(Elements.coloredLabel("Create Purchase Order?", Constants.colors[9]), createPurchaseOrder);
        f.addInput(Elements.coloredLabel("Purchase Requisition", Constants.colors[8]), availablePurchaseRequisitions);
        f.addInput(Elements.coloredLabel("Create IDO", Constants.colors[7]), createInboundDelivery);
        f.addInput(Elements.coloredLabel("Transporation Carrier", Constants.colors[6]), inboundCarriers);
        f.addInput(Elements.coloredLabel("Truck ID/Number", Constants.colors[5]), inboundTruckId);
        return f;
    }
}