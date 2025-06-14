package org.example.ui;

import org.example.model.Material;
import org.example.service.MaterialService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MaterialTableModel extends AbstractTableModel {
    private List<Material> data = new ArrayList<>();
    private String[] cols = {"ID","Название","Тип","Остаток","Мин","Цена","Закупка"};
    public void setData(List<Material> list) { data=list; fireTableDataChanged(); }
    public Material getMaterialAt(int row) { return data.get(row); }
    public int getRowCount() { return data.size(); }
    public int getColumnCount() { return cols.length; }
    public String getColumnName(int col) { return cols[col]; }
    public Object getValueAt(int r,int c) {
        Material m=data.get(r);
        return switch(c) {
            case 0 -> m.getId();
            case 1 -> m.getName();
            case 2 -> m.getType().getName();
            case 3 -> m.getQuantity();
            case 4 -> m.getMinQuantity();
            case 5 -> m.getPrice();
//            case 6 -> new MaterialService().calculatePurchase(m);
            default -> null;
        };
    }
}

