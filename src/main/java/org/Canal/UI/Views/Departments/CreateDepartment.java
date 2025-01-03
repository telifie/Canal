package org.Canal.UI.Views.Departments;

import org.Canal.Models.HumanResources.Department;
import org.Canal.Models.SupplyChainUnits.Location;
import org.Canal.UI.Elements.Elements;
import org.Canal.UI.Elements.DatePicker;
import org.Canal.UI.Elements.Selectable;
import org.Canal.UI.Elements.Selectables;
import org.Canal.UI.Elements.Label;
import org.Canal.UI.Elements.Form;
import org.Canal.UI.Elements.LockeState;
import org.Canal.Utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /DPTS/NEW
 */
public class CreateDepartment extends LockeState {

    public CreateDepartment(DesktopState desktop){
        super("Create Department", "/DPTS/NEW", false, true, false, true);
        setFrameIcon(new ImageIcon(CreateDepartment.class.getResource("/icons/create.png")));
        Form f = new Form();
        String genId = "D0" + (Engine.getOrganization().getDepartments().size() + 1);
        JTextField deptIdField = Elements.input(genId, 18);
        JTextField deptNameField = Elements.input(18);
        Selectable manager = Selectables.employees();
        manager.editable();
        DatePicker openedDatePicker = new DatePicker();
        Selectable locations = Selectables.allLocations();
        Selectable orgs = Selectables.organizations();
        f.addInput(new Label("*New Department ID", UIManager.getColor("Label.foreground")), deptIdField);
        f.addInput(new Label("*Organization", UIManager.getColor("Label.foreground")), orgs);
        f.addInput(new Label("Location (optional)", Constants.colors[0]), locations);
        f.addInput(new Label("Name", Constants.colors[1]), deptNameField);
        f.addInput(new Label("Manager", Constants.colors[2]), manager);
        f.addInput(new Label("Open Date", Constants.colors[3]), openedDatePicker);
        setLayout(new BorderLayout());
        add(f, BorderLayout.CENTER);
        JButton cr = Elements.button("Process");
        add(cr, BorderLayout.SOUTH);
        cr.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Department newDepartment = new Department();
                newDepartment.setId(deptIdField.getText());
                newDepartment.setOrganization(orgs.getSelectedValue());
                newDepartment.setLocation(locations.getSelectedValue());
                newDepartment.setName(deptNameField.getText());
                Location selectedOrg = Engine.getLocation(orgs.getSelectedValue(), "ORGS");
                selectedOrg.addDepartment(newDepartment);
                selectedOrg.save();
                dispose();
                JOptionPane.showMessageDialog(null, "Department Created in ORG " + orgs.getSelectedValue());
                Engine.router("/DPTS/" + deptIdField.getText(), desktop);
            }
        });
    }
}