package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class Style {
    public static final Font MAIN_FONT = new Font("Constantia", Font.PLAIN, 14);
    public static final Color MAIN_BG = Color.WHITE;
    public static final Color SECONDARY_BG = new Color(175, 214, 255); // #AED0FF
    public static final Color ACCENT_COLOR = new Color(29, 71, 107); // #1D476B

    public static void applyButtonStyle(JButton button) {
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(MAIN_FONT);
    }

    public static void applyDialogStyle(JDialog dialog) {
        dialog.getContentPane().setBackground(MAIN_BG);
        dialog.setFont(MAIN_FONT);
    }

    public static void applyFrameStyle(JFrame frame) {
        frame.getContentPane().setBackground(MAIN_BG);
        frame.setFont(MAIN_FONT);
    }
}