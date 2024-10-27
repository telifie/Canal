package org.Canal.UI.Elements;

import org.Canal.Models.SupplyChainUnits.Flex;
import org.Canal.Models.SupplyChainUnits.Item;
import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemTableModel extends AbstractTableModel {

    private final List<Item> items;
    private final String[] columnNames = {"Item Name", "Item", "Quantity", "Price", "Total"};
    private final Class<?>[] columnTypes = {String.class, Flex.class, Integer.class, Double.class, Double.class};
    private final List<Object[]> data;

    public ItemTableModel(List<Item> items) {
        this.items = items;
        data = new ArrayList<>();
        for (Item item : items) {
            data.add(new Object[]{item.getName(), item, 1, item.getPrice(), item.getPrice()});
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            Item selectedItem = (Item) value;
            data.get(rowIndex)[columnIndex] = selectedItem;
            data.get(rowIndex)[0] = selectedItem.getName();
            data.get(rowIndex)[3] = selectedItem.getPrice();
            double price = Double.valueOf(data.get(rowIndex)[3].toString());
            int quantity = Integer.valueOf(data.get(rowIndex)[2].toString());
            data.get(rowIndex)[4] = price * quantity;
        } else if (columnIndex == 2) {
            int quantity = Integer.valueOf(value.toString());
            data.get(rowIndex)[2] = quantity;
            double price = Double.valueOf(data.get(rowIndex)[3].toString());
            data.get(rowIndex)[4] = price * quantity;
        } else {
            data.get(rowIndex)[columnIndex] = value;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    public void addRow(Item item) {
        data.add(new Object[]{item.getName(), item, 1, item.getPrice(), item.getPrice()});
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            data.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public String getTotalPrice() {
        double total = 0.0;
        for (Object[] row : data) {
            total += Double.valueOf(row[4].toString());
        }
        return new DecimalFormat("#.00").format(total);
    }
}