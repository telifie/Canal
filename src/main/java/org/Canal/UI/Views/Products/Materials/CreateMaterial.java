package org.Canal.UI.Views.Products.Materials;

import org.Canal.Models.SupplyChainUnits.Item;
import org.Canal.UI.Elements.Copiable;
import org.Canal.UI.Elements.Selectable;
import org.Canal.UI.Elements.Selectables;
import org.Canal.UI.Elements.Label;
import org.Canal.UI.Elements.*;
import org.Canal.UI.Elements.Form;
import org.Canal.UI.Elements.LockeState;
import org.Canal.UI.Views.Controllers.Controller;
import org.Canal.Utils.Constants;
import org.Canal.Utils.Engine;
import org.Canal.Utils.Pipe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /MTS/NEW
 */
public class CreateMaterial extends LockeState {

    private Copiable orgIdField;
    private JTextField materialIdField, materialNameField, materialPriceField, materialColor, itemWidth, itemLength, itemHeight, itemWeight, tax, exciseTax, upc, iniVolumeField;
    private JCheckBox isBatched, isSkud;
    private Selectable selectedVendor;

    public CreateMaterial(){
        super("Create Material", "/MTS/NEW", false, true, false, true);
        Constants.checkLocke(this, true, true);
        setFrameIcon(new ImageIcon(Controller.class.getResource("/icons/create.png")));
        Form f1 = new Form();
        Form f2 = new Form();
        selectedVendor = Selectables.vendors();
        selectedVendor.editable();
        materialIdField = new JTextField("M0" + (1000 + (Engine.getItems().size() + 1)));
        orgIdField = new Copiable(Engine.getOrganization().getId());
        materialNameField = new JTextField("Black Shirt");
        materialPriceField = new JTextField("1.00");
        isBatched = new JCheckBox("Item expires");
        isSkud = new JCheckBox("Item has unique SKU");
        upc = new JTextField();
        f1.addInput(new Label("*Item ID", new Color(240, 240, 240)), materialIdField);
        f1.addInput(new Label("*Org ID", new Color(240, 240, 240)), orgIdField);
        f1.addInput(new Label("Item Name", Constants.colors[0]), materialNameField);
        f1.addInput(new Label("Vendor", Constants.colors[1]), selectedVendor);
        f1.addInput(new Label("Batched", Constants.colors[2]), isBatched);
        f1.addInput(new Label("Price", Constants.colors[4]), materialPriceField);
        f1.addInput(new Label("SKU'd Product", Constants.colors[5]), isSkud);
        f1.addInput(new Label("UPC", Constants.colors[5]), upc);
        itemWidth = new JTextField("0");
        itemLength = new JTextField("0");
        itemHeight = new JTextField("0");
        itemWeight = new JTextField("0");
        tax = new JTextField("0");
        exciseTax = new JTextField("0");
        materialColor = new JTextField("Black");
        iniVolumeField = new JTextField("100");
        f2.addInput(new Label("Ini. Volume", UIManager.getColor("Label.foreground")), iniVolumeField);
        f2.addInput(new Label("Color", UIManager.getColor("Label.foreground")), materialColor);
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
        JPanel main = new JPanel(new BorderLayout());
        main.add(Elements.header("Materials are consumable", SwingConstants.LEFT), BorderLayout.NORTH);
        main.add(biPanel, BorderLayout.CENTER);
        main.add(actionsBar(), BorderLayout.SOUTH);
        add(main);
    }

    private JPanel actionsBar() {
        JPanel tb = new JPanel();
        tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
        IconButton saveitem = new IconButton("Create Material", "start", "Commit Material");
        saveitem.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                commitItem();
            }
        });
        tb.add(saveitem);
        tb.setBorder(new EmptyBorder(5, 5, 5, 5));
        return tb;
    }

    protected void commitItem() {
        Item newMaterial = new Item();
        newMaterial.setId(materialIdField.getText());
        newMaterial.setOrg(orgIdField.getText());
        newMaterial.setName(materialNameField.getText());
        newMaterial.setUpc(upc.getText());
        newMaterial.setVendor(selectedVendor.getSelectedValue());
        newMaterial.setColor(materialColor.getText());
        newMaterial.setBatched(isBatched.isSelected());
        newMaterial.setSkud(isSkud.isSelected());
        newMaterial.setPrice(Double.parseDouble(materialPriceField.getText()));
        newMaterial.setWidth(Double.parseDouble(itemWidth.getText()));
        newMaterial.setLength(Double.parseDouble(itemLength.getText()));
        newMaterial.setHeight(Double.parseDouble(itemHeight.getText()));
        newMaterial.setWeight(Double.parseDouble(itemWeight.getText()));
        newMaterial.setTax(Double.parseDouble(itemWeight.getText()));
        newMaterial.setExciseTax(Double.parseDouble(exciseTax.getText()));
        Pipe.save("/MTS", newMaterial);
        dispose();
        JOptionPane.showMessageDialog(this, "Material has been created");
    }
}