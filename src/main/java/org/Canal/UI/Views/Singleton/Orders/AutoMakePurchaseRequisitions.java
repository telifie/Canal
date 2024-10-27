package org.Canal.UI.Views.Singleton.Orders;

import org.Canal.Models.BusinessUnits.PurchaseRequisition;
import org.Canal.Models.SupplyChainUnits.Location;
import org.Canal.UI.Elements.*;
import org.Canal.UI.Elements.Button;
import org.Canal.UI.Elements.Label;
import org.Canal.Utils.Constants;
import org.Canal.Utils.Engine;
import org.Canal.Utils.Pipe;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AutoMakePurchaseRequisitions extends JInternalFrame {

    private JPanel checkboxPanel;
    private ArrayList<Location> locations;
    private ArrayList<JCheckBox> checkboxes;

    public AutoMakePurchaseRequisitions() {
        setTitle("AutoMake Purchase Reqs.");
        locations = Engine.getCostCenters();
        locations.addAll(Engine.getDistributionCenters());
        locations.addAll(Engine.getVendors());
        locations.addAll(Engine.getWarehouses());
        this.checkboxes = new ArrayList<>();
        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        addCheckboxes();
        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setBorder(new TitledBorder("Buyers"));
        JPanel main = new JPanel(new GridLayout(1, 2));
        main.add(scrollPane);
        Form addtlInfo = new Form();
        addtlInfo.setBorder(new TitledBorder("Addtl. Info"));
        JTextField maxSpendField = new JTextField(10);
        JCheckBox isSingleOrder = new JCheckBox("");
        Selectable supplier = Selectables.allLocations();
        supplier.editable();
        DatePicker prStartDateField = new DatePicker();
        DatePicker prEndDateField = new DatePicker();
        JTextArea prNotesField = new JTextArea();
        addtlInfo.addInput(new Label("*Created", UIManager.getColor("Label.foreground")), new Copiable(Constants.now()));
        addtlInfo.addInput(new Label("Max Spend", Constants.colors[9]), maxSpendField);
        addtlInfo.addInput(new Label("[or] Single Order", UIManager.getColor("Label.foreground")), isSingleOrder);
        addtlInfo.addInput(new Label("Supplier/Vendor", Constants.colors[7]), supplier);
        addtlInfo.addInput(new Label("Valid From", Constants.colors[6]), prStartDateField);
        addtlInfo.addInput(new Label("To", Constants.colors[5]), prEndDateField);
        addtlInfo.addInput(new Label("Notes", Constants.colors[4]), prNotesField);
        main.add(addtlInfo);
        JLabel description = Labels.h3("Creates a Purchase Req. for each buyer from selected supplier with info.");
        description.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(description, BorderLayout.NORTH);
        add(main, BorderLayout.CENTER);
        Button createPrs = new Button("AutoMake Purchase Reqs.");
        getRootPane().setDefaultButton(createPrs);
        createPrs.addActionListener(_ -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (JCheckBox checkbox : checkboxes) {
                if (checkbox.isSelected()) {
                    String genId = "PR" + (10000000 + (Engine.realtime.getPurchaseRequisitions().size() + 1));
                    PurchaseRequisition newPr = new PurchaseRequisition(genId, genId, "U10001", supplier.getSelectedValue(), checkbox.getActionCommand(), genId, Double.valueOf(maxSpendField.getText()), dateFormat.format(prStartDateField.getSelectedDate()), dateFormat.format(prEndDateField.getSelectedDate()), prNotesField.getText());
                    Pipe.save("/PR", newPr);
                }
            }
            dispose();
            JOptionPane.showMessageDialog(null, "AutoMake Purchase Reqs Complete");
        });
        add(createPrs, BorderLayout.SOUTH);
        setIconifiable(true);
        setClosable(true);
    }

    private void addCheckboxes() {
        for (Location location : locations) {
            String displayText = location.getId() + " - " + location.getName();
            JCheckBox checkbox = new JCheckBox(displayText);
            checkbox.setActionCommand(String.valueOf(location.getId())); // Set the value as ID
            checkboxes.add(checkbox);
            checkboxPanel.add(checkbox);
        }
    }
}
