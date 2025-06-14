package org.example.ui;

import org.example.model.Material;
import org.example.service.MaterialService;
import javax.swing.*;
import java.awt.*;

public class MaterialCard extends JPanel {
    private static final Color BACKGROUND = Color.WHITE;
    private static final Color ACCENT = new Color(29, 71, 107);
    private static final Font FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 14);

    public MaterialCard(Material material, MaterialService service, JFrame parentFrame) {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        setBackground(BACKGROUND);

        add(createInfoPanel(material, service), BorderLayout.CENTER);
        add(createActionPanel(material, service, parentFrame), BorderLayout.EAST);
    }

    private JPanel createInfoPanel(Material material, MaterialService service) {
        JPanel panel = new JPanel(new GridLayout(6, 1, 2, 2));
        panel.setBackground(BACKGROUND);

        JLabel nameLabel = new JLabel(material.getName());
        nameLabel.setFont(HEADER_FONT);
        panel.add(nameLabel);

        panel.add(createInfoRow("Тип:", material.getType().getName()));
        panel.add(createInfoRow("Цена:", String.format("%.2f р/%s", material.getPrice(), material.getUnit())));
        panel.add(createInfoRow("На складе:", String.format("%.2f %s", material.getQuantity(), material.getUnit())));
        panel.add(createInfoRow("Мин. запас:", String.format("%.2f %s", material.getMinQuantity(), material.getUnit())));
        panel.add(createInfoRow("В упаковке:", String.format("%.2f %s", material.getPackSize(), material.getUnit())));

        try {
            double required = service.calculateRequiredMaterialQuantity(material.getId());
            panel.add(createInfoRow("Требуется:", String.format("%.2f %s", required, material.getUnit())));
        } catch (Exception e) {
            panel.add(createInfoRow("Требуется:", "ошибка"));
        }

        return panel;
    }

    private JPanel createActionPanel(Material material, MaterialService service, JFrame parentFrame) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.setBackground(BACKGROUND);

        JButton editButton = new JButton("Ред.");
        styleButton(editButton);
        editButton.addActionListener(e -> openEditDialog(material, parentFrame));

        JButton productsButton = new JButton("Использ.");
        styleButton(productsButton);
        productsButton.addActionListener(e -> showProducts(material.getId(), service, parentFrame));

        panel.add(editButton);
        panel.add(productsButton);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(FONT);
        button.setBackground(ACCENT);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(80, 25));
        button.setMaximumSize(new Dimension(80, 25));
        button.setMinimumSize(new Dimension(80, 25));
    }

    private JLabel createInfoRow(String label, String value) {
        JLabel row = new JLabel(label + " " + value);
        row.setFont(FONT);
        return row;
    }

    private void openEditDialog(Material material, JFrame parentFrame) {
        MaterialEditDialog dialog = new MaterialEditDialog(parentFrame, material);
        dialog.setVisible(true);
    }

    private void showProducts(int materialId, MaterialService service, JFrame parentFrame) {
        ProductUsageDialog dialog = new ProductUsageDialog(materialId, service);
        dialog.setVisible(true);
    }
}