package org.Canal.UI.Views.Distribution.Warehouses;

import org.Canal.Models.BusinessUnits.OrderLineItem;
import org.Canal.Models.BusinessUnits.PurchaseOrder;
import org.Canal.Models.SupplyChainUnits.*;
import org.Canal.UI.Elements.IconButton;
import org.Canal.UI.Elements.Labels;
import org.Canal.UI.Views.Finance.AcceptPayment;
import org.Canal.UI.Views.AreasBins.CreateArea;
import org.Canal.UI.Views.Orders.ReceiveOrder;
import org.Canal.Utils.Canal;
import org.Canal.Utils.DesktopState;
import org.Canal.Utils.Engine;
import org.Canal.Utils.RefreshListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class WarehouseView extends JInternalFrame implements RefreshListener {

    private Warehouse warehouse;
    private JTree dataTree;
    private DesktopState desktop;

    public WarehouseView(Warehouse loc, DesktopState desktop) {
        this.warehouse = loc;
        this.desktop = desktop;
        setTitle("Warehouse / " + loc.getId() + " - " + loc.getName());
        setLayout(new BorderLayout());
        JPanel tb = createToolBar();
        add(tb, BorderLayout.NORTH);
        JPanel dataView = new JPanel(new BorderLayout());
        JTextField cmd = new JTextField("/WHS/" + warehouse.getId());
        dataView.add(cmd, BorderLayout.NORTH);
        JScrollPane tableScrollPane = new JScrollPane(makeOverview());
        dataView.add(tableScrollPane, BorderLayout.CENTER);
        dataTree = createTree();
        dataTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath path = dataTree.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        Canal orgNode = (Canal) node.getUserObject();
                        Engine.router(orgNode.getTransaction(), desktop);
                    }
                }
            }
        });
        JScrollPane treeScrollPane = new JScrollPane(dataTree);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScrollPane, dataView);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.2);
        add(splitPane, BorderLayout.CENTER);
        setIconifiable(true);
        setClosable(true);
        setResizable(true);
        setMaximizable(true);
    }

    private JPanel makeOverview(){
        JPanel kanbanBoard = new JPanel();
        kanbanBoard.setLayout(new GridLayout(1, 3, 10, 10));
        ArrayList<String[]> ibd = new ArrayList<>();
        for(PurchaseOrder ibdo : Engine.getOrders(warehouse.getId())){
            double c = 0;
            for(OrderLineItem oli : ibdo.getItems()){
                c += oli.getQuantity();
            }
            ibd.add(new String[]{
                    ibdo.getOrderId(),
                    c + " Total Items | $" + ibdo.getTotal() + " Total",
                    "Expected Delivery: " + ibdo.getExpectedDelivery(),
                    "Receive"
            });
        }
        ArrayList<String[]> obd = new ArrayList<>();
        ArrayList<String[]> ipt = new ArrayList<>();
        ArrayList<String[]> myt = new ArrayList<>();
        kanbanBoard.add(createColumn("Inbound Deliveries", ibd));
        kanbanBoard.add(createColumn("Outbound Deliveries", obd));
        kanbanBoard.add(createColumn("In Progress Tasks", ipt));
        kanbanBoard.add(createColumn("Your Tasks", myt));
        return kanbanBoard;
    }

    private JPanel createColumn(String title, ArrayList<String[]> tasks) {
        JPanel columnPanel = new JPanel();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(title);
        titledBorder.setTitleFont(UIManager.getFont("h3.font"));
        columnPanel.setBorder(titledBorder);
        columnPanel.setLayout(new BorderLayout());
        JPanel tasksContainer = new JPanel();
        tasksContainer.setLayout(new BoxLayout(tasksContainer, BoxLayout.Y_AXIS));
        for (String[] taskInfo : tasks) {
            JPanel taskPanel = new JPanel();
            taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
            JLabel primaryLabel = Labels.h3(taskInfo[0]);
            JLabel secondaryLabel = Labels.label(taskInfo[1]);
            JLabel tertiaryLabel = Labels.label(taskInfo[2]);
            taskPanel.add(primaryLabel);
            taskPanel.add(secondaryLabel);
            taskPanel.add(tertiaryLabel);
            JButton openButton = new JButton(taskInfo[3]);
            taskPanel.add(openButton);
            taskPanel.setBackground(UIManager.getColor("Panel.background").darker());
            taskPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            taskPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            taskPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, taskPanel.getPreferredSize().height));
            taskPanel.setMinimumSize(new Dimension(tasksContainer.getWidth(), taskPanel.getPreferredSize().height));
            tasksContainer.add(taskPanel);
            tasksContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        JScrollPane sp = new JScrollPane(tasksContainer, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setBorder(null);
        columnPanel.add(sp, BorderLayout.CENTER);
        return columnPanel;
    }


    private JPanel createToolBar() {
        JPanel tb = new JPanel();
        tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
        IconButton acceptPayment = new IconButton("Accept $", "order", "Receiving an order or taking payment");
        IconButton payBill = new IconButton("Pay Bill", "bill", "Receiving a bill from a vendor");
        IconButton inventory = new IconButton("Inventory", "inventory", "Inventory of items in cost center");
        IconButton receive = new IconButton("Receive", "receive", "Receive an Inbound Delivery");
        IconButton areas = new IconButton("+ Areas", "areas", "Add an area cost center");
        IconButton label = new IconButton("", "label", "Print labels for properties");
        IconButton pos = new IconButton("", "pos", "Launch Point-of-Sale");
        IconButton refresh = new IconButton("", "refresh", "Reload from store");
        acceptPayment.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                desktop.put(new AcceptPayment());
            }
        });
        receive.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                desktop.put(new ReceiveOrder(warehouse.getId(), desktop));
            }
        });
        refresh.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            Engine.load();
            Canal rootNode = createRootNode();
            DefaultMutableTreeNode rootTreeNode = createTreeNodes(rootNode);
            DefaultTreeModel model = (DefaultTreeModel) dataTree.getModel();
            model.setRoot(rootTreeNode);
            expandAllNodes(dataTree);
            revalidate();
            repaint();
            }
        });
        areas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Location whs = new Location();
                whs.setId(warehouse.getId());
                new CreateArea(desktop, whs);
            }
        });
        tb.add(Box.createHorizontalStrut(5));
        tb.add(acceptPayment);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(payBill);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(inventory);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(receive);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(areas);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(label);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(refresh);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(pos);
        return tb;
    }

    private JTree createTree() {
        Canal rootNode = createRootNode();
        DefaultMutableTreeNode rootTreeNode = createTreeNodes(rootNode);
        DefaultTreeModel treeModel = new DefaultTreeModel(rootTreeNode);
        JTree tree = new JTree(treeModel);
        tree.setCellRenderer(new CustomTreeCellRenderer());
        expandAllNodes(tree);
        return tree;
    }

    private Canal createRootNode() {

        Canal[] customers = new Canal[Engine.getCustomers(warehouse.getOrg()).size()];
        for (int i = 0; i < Engine.getCustomers(warehouse.getOrg()).size(); i++) {
            Location l = Engine.getCustomers(warehouse.getOrg()).get(i);
            customers[i] = new Canal(l.getId() + " - " + l.getName(), false, "/CSTS/" + l.getId(), Color.PINK, null);
        }

        Canal[] vendors = new Canal[Engine.getVendors(warehouse.getOrg()).size()];
        for (int i = 0; i < Engine.getVendors(warehouse.getOrg()).size(); i++) {
            Location l = Engine.getVendors(warehouse.getOrg()).get(i);
            vendors[i] = new Canal(l.getId() + " - " + l.getName(), false, "/VEND/" + l.getId(), Color.CYAN, null);
        }

        Canal[] items = new Canal[Engine.getItems(warehouse.getOrg()).size()];
        for (int i = 0; i < Engine.getItems(warehouse.getOrg()).size(); i++) {
            Item l = Engine.getItems(warehouse.getOrg()).get(i);
            items[i] = new Canal(l.getId() + " - " + l.getName(), false, "/ITS/" + l.getId(), new Color(147, 70, 3), null);
        }
        Canal[] areas = new Canal[Engine.getAreas(warehouse.getId()).size()];
        for (int i = 0; i < Engine.getAreas(warehouse.getId()).size(); i++) {
            Area l = Engine.getAreas(warehouse.getId()).get(i);
            areas[i] = new Canal(l.getId() + " - " + l.getValue("name"), false, "/ITS/" + l.getId(), new Color(147, 70, 3), null);
        }

        return new Canal(warehouse.getId() + " - " + warehouse.getName(), true, "/ORGS", new Canal[]{
                new Canal("Areas", true, "/AREAS", areas),
                new Canal("Bins", true, "/BNS", null),
                new Canal("Items", true, "/ITS", items),
                new Canal("Customers", true, "/CSTS", customers),
                new Canal("Materials", true, "/MTS", null),
                new Canal("Orders", true, "/ORDS", null),
                new Canal("Vendors", true, "/VEND", vendors),
                new Canal("Employees", true, "/EMPS", null),
                new Canal("Reports", true, "/RPTS", new Canal[]{
                    new Canal("Annual Ledger Report", false, "/RPTS/LGS/ANNUM", null),
                    new Canal("Monthly Ledger Report", false, "/RPTS/MONTH", null),
                    new Canal("CC Ledger", false, "/RPTS/CCS/LGS", null),
                    new Canal("Annual Labor", false, "/RPTS/ANUM_LBR", null),
                    new Canal("Monthly Labor", false, "/RPTS/MONTH_LBR", null),
                    new Canal("Daily Labor", false, "/RPTS/DL_LBR", null),
                    new Canal("Current Inventory", false, "/RPTS/CRNT_INV", null),
                    new Canal("Inventory Count", false, "/RPTS/COUNT_INV", null),
                }),
        });
    }

    private DefaultMutableTreeNode createTreeNodes(Canal node) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node);
        if (node.getChildren() != null) {
            for (Canal child : node.getChildren()) {
                treeNode.add(createTreeNodes(child));
            }
        }
        return treeNode;
    }

    @Override
    public void onRefresh() {

    }

    static class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            Canal orgNode = (Canal) treeNode.getUserObject();
            if (orgNode.getStatus()) {
                setIcon(UIManager.getIcon("FileView.directoryIcon"));
            } else {
                setIcon(UIManager.getIcon("FileView.fileIcon"));
            }
            setForeground(orgNode.getColor());
            return component;
        }
    }

    private void expandAllNodes(JTree tree) {
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
    }
}