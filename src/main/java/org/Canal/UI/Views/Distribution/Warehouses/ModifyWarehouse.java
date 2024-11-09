package org.Canal.UI.Views.Distribution.Warehouses;

import org.Canal.Models.SupplyChainUnits.Warehouse;

import javax.swing.*;
import java.awt.*;

/**
 * /WHS/MOD
 */
public class ModifyWarehouse extends JInternalFrame {

    public ModifyWarehouse(Warehouse warehouse) {
        super(warehouse.getId() + " - " + warehouse.getName(), false, true, false, true);
        setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
        setFrameIcon(new ImageIcon(ModifyWarehouse.class.getResource("/icons/modify.png")));
    }
}