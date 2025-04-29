package View;

import View.Constants.CustomColors;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static View.Constants.Font.*;

public class Setup {

    public static JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(CustomColors.BUTTON_DEFAULT);
        button.setForeground(CustomColors.TEXT_COLOR);
        button.setFont(cooperHewittRegularFont);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.getBackground() != CustomColors.BUTTON_SELECTED) {
                    button.setBackground(CustomColors.BUTTON_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.getBackground() != CustomColors.BUTTON_SELECTED) {
                    button.setBackground(CustomColors.BUTTON_DEFAULT);
                }
            }
        });

        return button;
    }

    public static void updateButtonSelection(JButton selectedButton, JButton[] buttons) {
        for (JButton button : buttons) {
            if (button == selectedButton) {
                button.setBackground(CustomColors.BUTTON_SELECTED);
                button.setFont(cooperHewittBoldFont);
            } else {
                button.setBackground(CustomColors.BUTTON_DEFAULT);
                button.setFont(cooperHewittRegularFont);
            }
        }
    }

    public static JButton createContentButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(CustomColors.BUTTON_DEFAULT);
        button.setForeground(CustomColors.TEXT_COLOR);
        button.setFont(montFont.deriveFont(12f));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        return button;
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(CustomColors.BORDER_COLOR);
        label.setFont(montFont);
        return label;
    }

    public static void setupTextField(JTextField field, JLabel checkLabel) {
        field.setBackground(CustomColors.CONTENT_BG);
        field.setForeground(CustomColors.TEXT_COLOR);
        field.setBorder(BorderFactory.createLineBorder(CustomColors.BORDER_COLOR));
        field.setCaretColor(CustomColors.TEXT_COLOR);
        field.setFont(montFont);
        field.setHorizontalAlignment(JTextField.CENTER);
        checkLabel.setVerticalAlignment(SwingConstants.CENTER);
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCheck();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCheck();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCheck();
            }

            private void updateCheck() {
                if (!field.getText().trim().isEmpty()) {
                    checkLabel.setText("✔");
                    checkLabel.setForeground(CustomColors.TEXT_COLOR);
                    checkLabel.setFont(checkmarkFont);
                } else {
                    checkLabel.setText("");
                }
            }
        });
    }

    public static void setupTextArea(JTextArea area, JLabel checkLabel) {
        area.setBackground(CustomColors.CONTENT_BG);
        area.setForeground(CustomColors.TEXT_COLOR);
        area.setBorder(BorderFactory.createLineBorder(CustomColors.BORDER_COLOR));
        area.setCaretColor(CustomColors.TEXT_COLOR);
        area.setFont(montFont);
        checkLabel.setVerticalAlignment(SwingConstants.CENTER);
        area.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCheck();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCheck();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCheck();
            }

            private void updateCheck() {
                if (!area.getText().trim().isEmpty()) {
                    checkLabel.setText("\u2714");
                    checkLabel.setForeground(CustomColors.TEXT_COLOR);
                    checkLabel.setFont(checkmarkFont);
                } else {
                    checkLabel.setText("");
                }
            }
        });
    }

    public static String[] wrapText(String text, Graphics2D g2d, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            String testLine = line + (!line.isEmpty() ? " " : "") + word;
            if (g2d.getFontMetrics().stringWidth(testLine) <= maxWidth) {
                line.append(!line.isEmpty() ? " " : "").append(word);
            } else {
                if (!line.isEmpty()) {
                    lines.add(line.toString());
                }
                line = new StringBuilder(word);
            }
        }
        if (!line.isEmpty()) {
            lines.add(line.toString());
        }
        return lines.toArray(new String[0]);
    }
}
