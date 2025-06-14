package org.example.ui;

import org.example.model.Material;
import org.example.model.MaterialType;
import org.example.service.MaterialService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MaterialEditDialog extends JDialog {
    private final Material material;
    private final MaterialService materialService = new MaterialService();

    private JTextField nameField;
    private JComboBox<MaterialType> typeCombo;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField minQuantityField;
    private JTextField packSizeField;
    private JTextField unitField;

    public MaterialEditDialog(JFrame parent, Material material) {
        super(parent, material == null ? "Добавить материал" : "Редактировать материал", true);
        this.material = material == null ? new Material() : material;

        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initUI();
        Style.applyDialogStyle(this);
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Наименование:"));
        nameField = new JTextField(material.getName());
        formPanel.add(nameField);

        formPanel.add(new JLabel("Тип материала:"));
        typeCombo = new JComboBox<>();
        loadMaterialTypes();
        if (material.getType() != null) {
            typeCombo.setSelectedItem(material.getType());
        }
        formPanel.add(typeCombo);

        formPanel.add(new JLabel("Цена:"));
        priceField = new JTextField(String.valueOf(material.getPrice()));
        formPanel.add(priceField);

        formPanel.add(new JLabel("Количество:"));
        quantityField = new JTextField(String.valueOf(material.getQuantity()));
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Мин. количество:"));
        minQuantityField = new JTextField(String.valueOf(material.getMinQuantity()));
        formPanel.add(minQuantityField);

        formPanel.add(new JLabel("Размер упаковки:"));
        packSizeField = new JTextField(String.valueOf(material.getPackSize()));
        formPanel.add(packSizeField);

        formPanel.add(new JLabel("Единица измерения:"));
        unitField = new JTextField(material.getUnit());
        formPanel.add(unitField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Сохранить");
        Style.applyButtonStyle(saveButton);
        saveButton.addActionListener(e -> saveMaterial());
        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton("Отмена");
        Style.applyButtonStyle(cancelButton);
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadMaterialTypes() {
        try {
            List<Material> materials = materialService.getAllMaterials();
            materials.stream()
                    .map(Material::getType)
                    .distinct()
                    .forEach(typeCombo::addItem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка загрузки типов материалов: " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveMaterial() {
        try {
            material.setName(nameField.getText());
            material.setType((MaterialType) typeCombo.getSelectedItem());
            material.setPrice(Double.parseDouble(priceField.getText()));
            material.setQuantity(Double.parseDouble(quantityField.getText()));
            material.setMinQuantity(Double.parseDouble(minQuantityField.getText()));
            material.setPackSize(Double.parseDouble(packSizeField.getText()));
            material.setUnit(unitField.getText());

            materialService.saveMaterial(material);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка сохранения: " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}