package org.Canal.UI.Views.Departments;

import org.Canal.Models.HumanResources.Department;
import org.Canal.Models.SupplyChainUnits.Location;
import org.Canal.UI.Elements.*;
import org.Canal.Utils.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /DPTS/NEW
 */
public class CreateDepartment extends LockeState {

    private DesktopState desktop;
    private JTextField deptIdField;
    private JTextField deptNameField;
    private Selectable manager;
    private DatePicker openedDatePicker;
    private Selectable locations;
    private Selectable orgs;

    public CreateDepartment(DesktopState desktop){

        super("Create a Department", "/DPTS/NEW", false, true, false, true);
        setFrameIcon(new ImageIcon(CreateDepartment.class.getResource("/icons/create.png")));
        this.desktop = desktop;

        CustomTabbedPane tabs = new CustomTabbedPane();
        tabs.addTab("General", general());
        tabs.addTab("Positions", positions());

        setLayout(new BorderLayout());
        JPanel header = new JPanel(new BorderLayout());
        header.add(Elements.header("New Department", SwingConstants.LEFT), BorderLayout.NORTH);
        header.add(toolbar(), BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel toolbar() {

        JPanel tb = new JPanel();
        tb.setLayout(new BoxLayout(tb, BoxLayout.X_AXIS));
        IconButton execute = new IconButton("Create", "execute", "Create Position");
        tb.add(execute);
        tb.setBorder(new EmptyBorder(0, 5, 0, 5));
        execute.addMouseListener(new MouseAdapter() {
            @Override
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
        return tb;
    }

    private JPanel general(){

        JPanel general = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Form f = new Form();
        String genId = "D0" + (Engine.getOrganization().getDepartments().size() + 1);
        deptIdField = Elements.input(genId, 18);
        deptNameField = Elements.input(18);
        manager = Selectables.employees();
        manager.editable();
        openedDatePicker = new DatePicker();
        locations = Selectables.allLocations();
        orgs = Selectables.organizations();
        f.addInput(Elements.coloredLabel("*New Department ID", UIManager.getColor("Label.foreground")), deptIdField);
        f.addInput(Elements.coloredLabel("*Organization", UIManager.getColor("Label.foreground")), orgs);
        f.addInput(Elements.coloredLabel("Location (optional)", Constants.colors[0]), locations);
        f.addInput(Elements.coloredLabel("Name", Constants.colors[1]), deptNameField);
        f.addInput(Elements.coloredLabel("Manager", Constants.colors[2]), manager);
        f.addInput(Elements.coloredLabel("Open Date", Constants.colors[3]), openedDatePicker);
        general.add(f);
        return general;
    }

    private JPanel positions(){

        JPanel positions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        IconButton add = new IconButton("Assign Position", "add_rows", "Add Position");
        IconButton remove = new IconButton("Remove Selected", "delete_rows", "Remove Selected Position");
        buttons.add(add);
        buttons.add(Box.createHorizontalStrut(5));
        buttons.add(remove);
        add.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
        });
        remove.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
        });

        positions.add(buttons, BorderLayout.SOUTH);
        return positions;
    }
}