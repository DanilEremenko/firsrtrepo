package org.example.ui;

import org.example.model.Product;
import org.example.service.MaterialService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ProductUsageDialog extends JDialog {
    private static final Color HEADER_COLOR = new Color(29, 71, 107);
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font TABLE_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Color BUTTON_COLOR = new Color(29, 71, 107);

    public ProductUsageDialog(int materialId, MaterialService service) {
        setTitle("Продукция, использующая материал");
        setModal(true);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI(materialId, service);
    }

    private void initUI(int materialId, MaterialService service) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Заголовок
        JLabel titleLabel = new JLabel("Продукция, в которой используется материал");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Таблица продукции
        String[] columnNames = {"ID", "Артикул", "Наименование", "Тип продукции",
                "Мин. цена партнера", "Можно произвести (шт)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        styleTable(table);

        try {
            List<Product> products = service.getProductsUsingMaterial(materialId);
            for (Product product : products) {
//                int productCount = service.calculateProductCount(
//                        product.getType().getId(),
//                        service.getMaterialById(materialId).getType().getId(),
//                        service.getMaterialById(materialId).getQuantity(),
//                        1.0, // param1 - примерное значение (должно быть из БД или интерфейса)
//                        1.0  // param2 - примерное значение
//                );

                model.addRow(new Object[]{
                        product.getId(),
                        product.getArticle(),
                        product.getName(),
                        product.getType().getName(),
                        String.format("%.2f р.", product.getMinPartnerPrice()),
                        "н/д"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка загрузки данных: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Кнопка закрытия
        JButton closeButton = new JButton("Закрыть");
        styleButton(closeButton);
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void styleTable(JTable table) {
        table.setFont(TABLE_FONT);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();
        header.setFont(HEADER_FONT);
        header.setBackground(HEADER_COLOR);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
    }

    private void styleButton(JButton button) {
        button.setFont(TABLE_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 30));
    }
}