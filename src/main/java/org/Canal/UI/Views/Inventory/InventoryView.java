package org.Canal.UI.Views.Inventory;

import org.Canal.Models.SupplyChainUnits.Item;
import org.Canal.Models.SupplyChainUnits.StockLine;
import org.Canal.UI.Elements.CustomTable;
import org.Canal.UI.Elements.Elements;
import org.Canal.UI.Elements.IconButton;
import org.Canal.UI.Views.Controllers.CheckboxBarcodeFrame;
import org.Canal.Utils.DesktopState;
import org.Canal.Utils.Engine;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * /STK
 */
public class InventoryView extends JInternalFrame {

    private String location;
    private CustomTable table;
    private DesktopState desktop;

    public InventoryView(DesktopState desktop, String location) {
        super("Location Inventory", true, true, true, true);
        this.location = location;
        this.desktop = desktop;
        setFrameIcon(new ImageIcon(InventoryView.class.getResource("/icons/purchasereqs.png")));
        JPanel tb = createToolBar();
        JPanel holder = new JPanel(new BorderLayout());
        table = createTable();
        JScrollPane tableScrollPane = new JScrollPane(table);
        holder.add(Elements.header(location + " Inventory", SwingConstants.LEFT), BorderLayout.NORTH);
        holder.add(tb, BorderLayout.SOUTH);
        setLayout(new BorderLayout());
        add(holder, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private CustomTable createTable() {
        String[] columns = new String[]{"Location", "Type", "ID", "Name", "Org. Qty.", "Qty.", "Price", "Value", "Area", "Bin", "Receipt", "Status"};
        ArrayList<Object[]> stks = new ArrayList<>();
        for (StockLine sl : Engine.getInventory(location).getStockLines()) {
            Item i = Engine.getItem(sl.getId());
            stks.add(new String[]{
                    location,
                    sl.getObjex(),
                    sl.getId(),
                    i.getName(),
                    String.valueOf(sl.getQuantity()),
                    String.valueOf(sl.getQuantity()),
                    String.valueOf(i.getPrice()),
                    String.valueOf(i.getPrice() * sl.getQuantity()),
                    sl.getArea(),
                    sl.getBin(),
                    sl.getReceipt(),
                    String.valueOf(sl.getStatus())
            });
        }
        return new CustomTable(columns, stks);
    }

    private JPanel createToolBar() {
        JPanel tb = new JPanel();
        tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
        IconButton export = new IconButton("", "export", "Export as CSV");
        IconButton blockPo = new IconButton("Block", "block", "Block/Pause PO, can't be used");
        IconButton move = new IconButton("Move", "start", "Resume/Activate PO");
        IconButton archivePo = new IconButton("Archive", "archive", "Archive PO, removes");
        IconButton label = new IconButton("Barcodes", "label", "Print labels for org properties");
        JTextField filterValue = Elements.input(location, 10);
        tb.add(export);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(blockPo);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(move);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(archivePo);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(label);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(filterValue);
        tb.setBorder(new EmptyBorder(5, 5, 5, 5));
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String[] printables = new String[Engine.orderProcessing.getPurchaseOrder().size()];
                for (int i = 0; i < Engine.orderProcessing.getPurchaseOrder().size(); i++) {
                    printables[i] = Engine.orderProcessing.getPurchaseOrder().get(i).getOrderId();
                }
                new CheckboxBarcodeFrame(printables);
            }
        });
        return tb;
    }
}