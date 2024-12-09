package org.Canal.UI.Views.Items;

import org.Canal.Models.SupplyChainUnits.Item;
import org.Canal.Models.SupplyChainUnits.Location;
import org.Canal.UI.Elements.Inputs.Selectable;
import org.Canal.UI.Elements.Inputs.Selectables;
import org.Canal.UI.Elements.Label;
import org.Canal.UI.Elements.*;
import org.Canal.UI.Elements.Windows.Form;
import org.Canal.UI.Elements.Windows.LockeState;
import org.Canal.UI.Views.Controllers.Controller;
import org.Canal.Utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * /ITS/NEW
 */
public class CreateItem extends LockeState implements Includer {

    private JTextField itemIdField;
    private JTextField itemNameField;
    private JTextField itemPriceField;
    private JTextField itemColor;
    private JTextField tax;
    private JTextField exciseTax;
    private JTextField upc;
    private JTextField uomField;
    private JTextField packagingUnitField;
    private UOMField itemWidth, itemLength, itemHeight, itemWeight;
    private JCheckBox isBatched, isRentable, isSkud, isConsumable;
    private Selectable orgIdField, selectedVendor;
    private DesktopState desktop;

    public CreateItem(DesktopState desktop){
        super("Item Builder", "/ITS/NEW", false, true, false, true);
        this.desktop = desktop;
        Constants.checkLocke(this, true, true);
        setFrameIcon(new ImageIcon(Controller.class.getResource("/icons/create.png")));
        Form f1 = new Form();
        Form f2 = new Form();
        HashMap<String, String> availableVendors = new HashMap<>();
        for(Location vs : Engine.getLocations("VEND")){
            availableVendors.put(vs.getId(), vs.getId());
        }
        selectedVendor = new Selectable(availableVendors);
        selectedVendor.editable();
        itemIdField = Elements.input("X0" + (1000 + (Engine.getItems().size() + 1)));
        orgIdField = Selectables.organizations();
        itemNameField = Elements.input("Black Shirt");
        itemPriceField = Elements.input("1.00");
        isBatched = new JCheckBox(" Item expires");
        isRentable = new JCheckBox(" Item can be rented");
        isSkud = new JCheckBox(" Item has unique SKU");
        upc = Elements.input();
        uomField = Elements.input();
        packagingUnitField = Elements.input();
        f1.addInput(new Label("*New Item ID", new Color(240, 240, 240)), itemIdField);
        f1.addInput(new Label("*Organization", new Color(240, 240, 240)), orgIdField);
        f1.addInput(new Label("Item Name", Constants.colors[0]), itemNameField);
        f1.addInput(new Label("Vendor", Constants.colors[1]), selectedVendor);
        f1.addInput(new Label("Batched", Constants.colors[2]), isBatched);
        f1.addInput(new Label("Rentable", Constants.colors[3]), isRentable);
        f1.addInput(new Label("Price", Constants.colors[4]), itemPriceField);
        f1.addInput(new Label("SKU'd Product", Constants.colors[5]), isSkud);
        f1.addInput(new Label("UPC", Constants.colors[6]), upc);
        itemWidth = new UOMField();
        itemLength = new UOMField();
        itemHeight = new UOMField();
        itemWeight = new UOMField();
        tax = Elements.input("0");
        exciseTax = Elements.input("0");
        itemColor = Elements.input("Black");
        isConsumable = new JCheckBox();
        f2.addInput(new Label("Consumable?", UIManager.getColor("Label.foreground")), isConsumable);
        f2.addInput(new Label("Color", UIManager.getColor("Label.foreground")), itemColor);
        f2.addInput(new Label("Width", UIManager.getColor("Label.foreground")), itemWidth);
        f2.addInput(new Label("Length", UIManager.getColor("Label.foreground")), itemLength);
        f2.addInput(new Label("Height", UIManager.getColor("Label.foreground")), itemHeight);
        f2.addInput(new Label("Weight", UIManager.getColor("Label.foreground")), itemWeight);
        f2.addInput(new Label("Tax", UIManager.getColor("Label.foreground")), tax);
        f2.addInput(new Label("Excise Tax", UIManager.getColor("Label.foreground")), exciseTax);
        JPanel biPanel = new JPanel(new GridLayout(1, 2));
        f1.setBorder(new EmptyBorder(5, 5, 5, 5));
        f2.setBorder(new EmptyBorder(5, 5, 5, 5));
        biPanel.add(f1);
        biPanel.add(f2);
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.add(Elements.header("Item Builder", SwingConstants.LEFT), BorderLayout.CENTER);
        topBar.add(modifiers(), BorderLayout.SOUTH);
        setLayout(new BorderLayout());
        add(topBar, BorderLayout.NORTH);
        add(biPanel, BorderLayout.CENTER);
        add(actionsBar(), BorderLayout.SOUTH);
    }

    private JPanel modifiers(){
        JPanel tb = new JPanel(new BorderLayout());
        tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
        JLabel materialModifer = Elements.link("Materials (0)", "Add/Remove Materials");
        JLabel componentModifier = Elements.link("Components (0)", "Add/Remove Components");
        JLabel deviations = Elements.link("Deviations (0)", "Add/Remove Deviations");
        JLabel priceVariants = Elements.link("Price Variants (0)", "Add/Remove Price Variance");
        tb.add(materialModifer);
        tb.add(componentModifier);
        tb.add(deviations);
        tb.add(priceVariants);
        tb.setBorder(new EmptyBorder(5, 5, 5, 5));
        materialModifer.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            }
        });
        componentModifier.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            }
        });
        deviations.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            }
        });
        priceVariants.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            }
        });
        return tb;
    }

    private JPanel actionsBar() {
        JPanel tb = new JPanel();
        tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
        IconButton materials = new IconButton("+ Materials", "areas", "Add an area cost center");
        IconButton components = new IconButton("+ Components", "component", "Print labels for properties");
        IconButton addPriceVariance = new IconButton("+ Price Var.", "autoprice", "");
        IconButton autoprice = new IconButton("Autoprice", "autoprice", "");
        IconButton generateUPC = new IconButton("Make UPC", "label", "");
        IconButton saveitem = new IconButton("Create Item", "start", "");
        materials.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                desktop.put(new CreateInclusion("Include Material"));
            }
        });
        components.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                desktop.put(new CreateInclusion("Include Component"));
            }
        });
        addPriceVariance.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                desktop.put(new CreateInclusion("Add Price Variant"));
            }
        });
        saveitem.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                commitItem();
            }
        });
        tb.add(materials);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(components);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(autoprice);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(addPriceVariance);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(generateUPC);
        tb.add(Box.createHorizontalStrut(5));
        tb.add(saveitem);
        tb.setBorder(new EmptyBorder(5, 5, 5, 5));
        return tb;
    }

    protected void commitItem() {
        Item newItem = new Item();
        newItem.setId(itemIdField.getText());
        newItem.setOrg(orgIdField.getSelectedValue());
        newItem.setName(itemNameField.getText());
        newItem.setUpc(upc.getText());
        newItem.setVendor(selectedVendor.getSelectedValue());
        newItem.setColor(itemColor.getText());
        newItem.setBatched(isBatched.isSelected());
        newItem.setRentable(isRentable.isSelected());
        newItem.setSkud(isSkud.isSelected());
        newItem.setConsumable(isConsumable.isSelected());
        newItem.setPrice(Double.parseDouble(itemPriceField.getText()));
        newItem.setWidth(Double.parseDouble(itemWidth.getValue()));
        newItem.setLength(Double.parseDouble(itemLength.getValue()));
        newItem.setHeight(Double.parseDouble(itemHeight.getValue()));
        newItem.setWeight(Double.parseDouble(itemWeight.getValue()));
        newItem.setTax(Double.parseDouble(itemWeight.getValue()));
        newItem.setExciseTax(Double.parseDouble(exciseTax.getText()));
        Pipe.save("/ITS", newItem);
        dispose();
        JOptionPane.showMessageDialog(this, "Item has been created");
    }

    @Override
    public void commitInclusion(String component, String use, String uom) {

    }
}