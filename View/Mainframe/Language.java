package View.Mainframe;

import javax.swing.*;
import java.awt.*;

import static View.Constants.Font.cooperHewittBoldFont;
import static View.Constants.Font.montFont;
import static View.Setup.*;
import static View.Setup.createLabel;
import Model.Resume;
import View.Constants.CustomColors;

public class Language {
    public static JPanel languagesPanel = new JPanel(new GridBagLayout());
    public static DefaultListModel<String> languagesListModel = new DefaultListModel<>();
    public static JTextField languageField = new JTextField(20);
    public static JComboBox<String> proficiencyComboBox = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced", "Fluent"});
    public static JButton addLanguageButton = createContentButton("Add Language");
    public static JButton removeLanguageButton = createContentButton("Remove Language");
    public static JList<String> languagesList = new JList<>(languagesListModel);

    static{
        // Languages Panel

        languagesPanel.setBackground(CustomColors.CONTENT_BG);
        JLabel languagesHeader = new JLabel("LANGUAGES");
        languagesHeader.setForeground(CustomColors.TEXT_COLOR);
        languagesHeader.setFont(cooperHewittBoldFont.deriveFont(24f));

        languagesList.setBackground(CustomColors.CONTENT_BG);
        languagesList.setForeground(CustomColors.TEXT_COLOR);
        languagesList.setFont(montFont);
        JScrollPane languagesScrollPane = new JScrollPane(languagesList);

        proficiencyComboBox.setBackground(CustomColors.CONTENT_BG);
        proficiencyComboBox.setForeground(CustomColors.TEXT_COLOR);
        proficiencyComboBox.setFont(montFont);

        JLabel languageCheck = new JLabel("");
        setupTextField(languageField, languageCheck);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        languagesPanel.add(languagesHeader, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        languagesPanel.add(createLabel("Language"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        languagesPanel.add(languageField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        languagesPanel.add(languageCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        languagesPanel.add(createLabel("Proficiency"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        languagesPanel.add(proficiencyComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        languagesPanel.add(addLanguageButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        languagesPanel.add(removeLanguageButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        languagesPanel.add(languagesScrollPane, gbc);
    }

    public static void addLanguage(Resume resume){
        String language = languageField.getText().trim();
        String proficiency = (String) proficiencyComboBox.getSelectedItem();
        if (!language.isEmpty()) {
            Model.Language lang = new Model.Language(language, proficiency);
            resume.addLanguage(lang);
            languagesListModel.addElement(language + " (" + proficiency + ")");
            languageField.setText("");
            proficiencyComboBox.setSelectedIndex(0);
            JOptionPane.showMessageDialog(languagesPanel, "Language added successfully.");
        } else {
            JOptionPane.showMessageDialog(languagesPanel, "Please enter a language.");
        }
    }

    public static void removeLanguage(Resume resume){
        int selectedIndex = languagesList.getSelectedIndex();
        if (selectedIndex != -1) {
            languagesListModel.remove(selectedIndex);
            resume.getLanguages().remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(languagesPanel, "Please select a language to remove.");
        }
    }
}
