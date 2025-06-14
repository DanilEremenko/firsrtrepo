package org.example.ui;

import org.example.model.Product;
import org.example.service.MaterialService;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ProductListDialog extends JDialog {
    private JTable table = new JTable();

    public ProductListDialog(Frame owner, int materialId) {
        super(owner, "Продукция, использующая материал", true);
        setSize(600, 400);
        setLayout(new BorderLayout());

        table.setModel(new ProductTableModel());
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadProducts(materialId);
    }

    private void loadProducts(int materialId) {
        List<Product> products = new MaterialService().getProductsUsingMaterial(materialId);
        ((ProductTableModel)table.getModel()).setData(products);
    }
}
