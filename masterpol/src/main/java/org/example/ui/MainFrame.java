package org.example.ui;

import org.example.model.Material;
import org.example.service.MaterialService;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final MaterialService service = new MaterialService();
    private final JPanel container;

    public MainFrame() {
        super("Учет материалов | Образ плюс");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(container);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton addButton = new JButton("Добавить материал");
        addButton.addActionListener(e -> {
            MaterialEditDialog dialog = new MaterialEditDialog(this, null);
            dialog.setVisible(true);
            loadCards();
        });
        controlPanel.add(addButton);
        add(controlPanel, BorderLayout.SOUTH);

        loadCards();
        setVisible(true);

        Style.applyFrameStyle(this);
        Style.applyButtonStyle(addButton);

        try {
            ImageIcon logo = new ImageIcon(getClass().getResource("/logo.png"));
            setIconImage(logo.getImage());
            JLabel logoLabel = new JLabel(logo);
            add(logoLabel);
        } catch (Exception e) {
            System.out.println("Логотип не найден");
        }
    }

    private void loadCards() {
        container.removeAll();
        for (Material m : service.getAllMaterials()) {
            container.add(new MaterialCard(m, service, this));
            container.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        container.revalidate();
        container.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}