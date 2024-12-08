package org.Canal.UI.Views.HR.Teams;

import org.Canal.UI.Elements.Elements;
import org.Canal.UI.Elements.Inputs.Selectable;
import org.Canal.UI.Elements.Inputs.Selectables;
import org.Canal.UI.Elements.Label;
import org.Canal.UI.Elements.Windows.Form;
import org.Canal.UI.Elements.Windows.LockeState;
import org.Canal.Utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * /TMS/NEW
 */
public class CreateTeam extends LockeState {

    public CreateTeam() {
        super("Create Team", "/TMS/NEW", false, true, false, true);
        setLayout(new BorderLayout());
        Form f = new Form();

        JTextField teamNameField = Elements.input(15);
        Selectable departments = Selectables.departments();
        Selectable leaders = Selectables.employees();
        f.addInput(new Label("Team Name", Constants.colors[10]), teamNameField);
        f.addInput(new Label("Department", Constants.colors[9]), departments);
        f.addInput(new Label("Leader", Constants.colors[8]), leaders);

        add(f, BorderLayout.CENTER);
        JButton make = Elements.button("Make Team");
        add(make, BorderLayout.SOUTH);
        make.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
    }
}