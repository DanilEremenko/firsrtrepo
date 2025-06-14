package org.example.ui;

import org.example.model.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private List<Product> data = new ArrayList<>();
    private String[] cols = {"Артикул", "Наименование", "Тип", "Мин. цена"};

    public void setData(List<Product> list) {
        data = list;
        fireTableDataChanged();
    }

    public int getRowCount() { return data.size(); }
    public int getColumnCount() { return cols.length; }
    public String getColumnName(int col) { return cols[col]; }

    public Object getValueAt(int row, int col) {
        Product p = data.get(row);
        return switch(col) {
            case 0 -> p.getArticle();
            case 1 -> p.getName();
            case 2 -> p.getType().getName();
            case 3 -> p.getMinPartnerPrice();
            default -> null;
        };
    }
}