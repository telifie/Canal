package org.Canal.UI.Views.Finance.CostCenters;

import org.Canal.Models.SupplyChainUnits.Area;
import org.Canal.Models.SupplyChainUnits.Item;
import org.Canal.Models.SupplyChainUnits.Location;
import org.Canal.Models.SupplyChainUnits.Vendor;
import org.Canal.UI.Elements.Button;
import org.Canal.UI.Elements.IconButton;
import org.Canal.UI.Views.Areas.CreateArea;
import org.Canal.UI.Views.Bins.CreateBin;
import org.Canal.UI.Views.Orders.PurchaseOrders.CreatePurchaseOrder;
import org.Canal.UI.Views.Areas.AutoMakeAreasAndBins;
import org.Canal.Utils.Locke;
import org.Canal.Utils.DesktopState;
import org.Canal.Utils.Engine;
import org.Canal.Utils.RefreshListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /CCS/$[COST_CENTER_ID]
 */
public class CostCenterView extends JInternalFrame implements RefreshListener {

    private Location thisCostCenter;
    private JTree dataTree;
    private DesktopState desktop;

    public CostCenterView(Location loc, DesktopState desktop) {
        super("Cost Center / " + loc.getId() + " - " + loc.getName(), true, true, true, true);
        this.thisCostCenter = loc;
        this.desktop = desktop;
        setLayout(new BorderLayout());
        JPanel tb = createToolBar();
        add(tb, BorderLayout.NORTH);
        JTable table = createTable();
        JScrollPane tableScrollPane = new JScrollPane(table);
        JPanel dataView = new JPanel(new BorderLayout());
        dataView.add(tableScrollPane, BorderLayout.CENTER);
        dataTree = createTree();
        dataTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath path = dataTree.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        Locke orgNode = (Locke) node.getUserObject();
                        desktop.put(Engine.router(orgNode.getTransaction(), desktop));
                    }
                }
            }
        });
        JScrollPane treeScrollPane = new JScrollPane(dataTree);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, dataView);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.3);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createToolBar() {
        JPanel tb = new JPanel();
        tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
        IconButton order = new IconButton("Order", "create", "Order from a vendor");
        IconButton payBill = new IconButton("Pay Bill", "bill", "Receiving a bill from a vendor");
        IconButton inventory = new IconButton("Inventory", "inventory", "Inventory of items in cost center");
        IconButton addArea = new IconButton("+ Area", "areas", "Add an area cost center");
        IconButton addBin = new IconButton("+ Bin", "bins", "Add an area cost center");
        IconButton autoMake = new IconButton("Auto Make Areas/Bins", "automake", "Make areas and bins from templates");
        IconButton pos = new IconButton("POS", "pos", "Launch Point-of-Sale");
        IconButton label = new IconButton("", "label", "Print labels for properties");
        order.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                desktop.put(new CreatePurchaseOrder());
            }
        });
        addArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                desktop.put(new CreateArea(thisCostCenter.getId()));
            }
        });
        addBin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                desktop.put(new CreateBin(thisCostCenter.getId()));
            }
        });
        autoMake.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                desktop.put(new AutoMakeAreasAndBins());
            }
        });
        tb.add(order);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(payBill);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(inventory);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(addArea);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(addBin);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(autoMake);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(pos);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(label);
        tb.setBorder(new EmptyBorder(5, 5, 5, 5));
        return tb;
    }

    private JTable createTable() {
        String[] columns = new String[]{"Property", "Value"};
        String[][] data = {
                {"Id", thisCostCenter.getId()},
                {"Tie", thisCostCenter.getTie()},
                {"Name", thisCostCenter.getName()},
                {"Address Line 1", thisCostCenter.getLine1()},
                {"City", thisCostCenter.getCity()},
                {"State", thisCostCenter.getState()},
                {"Postal", thisCostCenter.getPostal()},
                {"Country", thisCostCenter.getCountry()},
                {"Tax Exempt Status", String.valueOf(thisCostCenter.isTaxExempt())}
        };
        JTable table = new JTable(data, columns);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Engine.adjustColumnWidths(table);
        return table;
    }

    private JTree createTree() {
        Locke rootNode = createRootNode();
        DefaultMutableTreeNode rootTreeNode = createTreeNodes(rootNode);
        DefaultTreeModel treeModel = new DefaultTreeModel(rootTreeNode);
        JTree tree = new JTree(treeModel);
        tree.setCellRenderer(new CustomTreeCellRenderer());
        return tree;
    }

    private Locke createRootNode() {

        Locke[] customers = new Locke[Engine.getCustomers(thisCostCenter.getTie()).size()];
        for (int i = 0; i < Engine.getCustomers(thisCostCenter.getTie()).size(); i++) {
            Location l = Engine.getCustomers(thisCostCenter.getTie()).get(i);
            customers[i] = new Locke(l.getId() + " - " + l.getName(), false, "/CSTS/" + l.getId(), Color.PINK, null);
        }
        Locke[] vendors = new Locke[Engine.getVendors(thisCostCenter.getTie()).size()];
        for (int i = 0; i < Engine.getVendors(thisCostCenter.getTie()).size(); i++) {
            Vendor l = Engine.getVendors(thisCostCenter.getTie()).get(i);
            vendors[i] = new Locke(l.getId() + " - " + l.getName(), false, "/VEND/" + l.getId(), Color.CYAN, null);
        }
        Locke[] items = new Locke[Engine.getItems(thisCostCenter.getTie()).size()];
        for (int i = 0; i < Engine.getItems(thisCostCenter.getTie()).size(); i++) {
            Item l = Engine.getItems(thisCostCenter.getTie()).get(i);
            items[i] = new Locke(l.getId() + " - " + l.getName(), false, "/ITS/" + l.getId(), new Color(147, 70, 3), null);
        }
        Locke[] areas = new Locke[Engine.getAreas(thisCostCenter.getId()).size()];
        for (int i = 0; i < Engine.getAreas(thisCostCenter.getId()).size(); i++) {
            Area l = Engine.getAreas(thisCostCenter.getId()).get(i);
            areas[i] = new Locke(l.getId() + " - " + l.getName(), false, "/ITS/" + l.getId(), new Color(147, 70, 3), null);
        }
        return new Locke(thisCostCenter.getId() + " - " + thisCostCenter.getName(), true, "/ORGS", new Locke[]{
                new Locke("Areas", true, "/AREAS", areas),
                new Locke("Bins", true, "/BNS", null),
                new Locke("Items", true, "/ITS", items),
                new Locke("Customers", true, "/CSTS", customers),
                new Locke("Materials", true, "/MTS", null),
                new Locke("Orders", true, "/ORDS", null),
                new Locke("Vendors", true, "/VEND", vendors),
                new Locke("Employees", true, "/EMPS", null),
                new Locke("Reports", true, "/RPTS", new Locke[]{
                    new Locke("Annual Ledger Report", false, "/RPTS/LGS/ANNUM", null),
                    new Locke("Monthly Ledger Report", false, "/RPTS/MONTH", null),
                    new Locke("CC Ledger", false, "/RPTS/CCS/LGS", null),
                    new Locke("Annual Labor", false, "/RPTS/ANUM_LBR", null),
                    new Locke("Monthly Labor", false, "/RPTS/MONTH_LBR", null),
                    new Locke("Daily Labor", false, "/RPTS/DL_LBR", null),
                    new Locke("Current Inventory", false, "/RPTS/CRNT_INV", null),
                    new Locke("Inventory Count", false, "/RPTS/COUNT_INV", null),
                }),
        });
    }

    private DefaultMutableTreeNode createTreeNodes(Locke node) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node);
        if (node.getChildren() != null) {
            for (Locke child : node.getChildren()) {
                treeNode.add(createTreeNodes(child));
            }
        }
        return treeNode;
    }

    @Override
    public void onRefresh() {
        Locke rootNode = createRootNode();
        DefaultMutableTreeNode rootTreeNode = createTreeNodes(rootNode);
        DefaultTreeModel model = (DefaultTreeModel) dataTree.getModel();
        model.setRoot(rootTreeNode);
        revalidate();
        repaint();
    }

    static class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            Locke orgNode = (Locke) treeNode.getUserObject();
            if (orgNode.getStatus()) {
                setIcon(UIManager.getIcon("FileView.directoryIcon"));
            } else {
                setIcon(UIManager.getIcon("FileView.fileIcon"));
            }
            setForeground(orgNode.getColor());
            return component;
        }
    }
}