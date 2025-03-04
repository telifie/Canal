package org.Canal.UI.Views;

import org.Canal.Models.Objex;
import org.Canal.UI.Elements.Elements;
import org.Canal.UI.Elements.Form;
import org.Canal.UI.Elements.LockeState;
import org.Canal.Utils.Constants;
import org.Canal.Utils.Json;
import org.Canal.Utils.LockeStatus;
import org.Canal.Utils.Pipe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Archiver extends LockeState {

    public Archiver(String objex){
        super("Archiver", objex + "/ARCHV", false, true, false, true);

        JTextField objexIdField = Elements.input(20);
        Form f = new Form();
        f.addInput(Elements.coloredLabel("Objex ID", Constants.colors[0]), objexIdField);
        setLayout(new BorderLayout());
        add(f, BorderLayout.CENTER);
        JButton confirmDeletion = Elements.button("Confirm Objex Archival");
        add(confirmDeletion, BorderLayout.SOUTH);
        add(Elements.header("Delete a " + objex, SwingConstants.LEFT), BorderLayout.NORTH);
        confirmDeletion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                File[] fs = Pipe.list(objex);
                for(File f : fs){
                    if(f.getName().endsWith(objex.toLowerCase().replaceAll("/", "."))){
                        Objex o = Json.load(f.getPath(), Objex.class);
                        if(o.getId().equals(objexIdField.getText())){
                            o.setStatus(LockeStatus.ARCHIVED);
                            //TODO
                        }
                    }
                }
                //Doesn't exist
            }
        });
    }
}
