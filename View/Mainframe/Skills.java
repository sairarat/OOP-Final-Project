package View.Mainframe;

import Model.Resume;
import Model.Skill;
import View.Constants.CustomColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static View.Constants.Font.cooperHewittBoldFont;
import static View.Constants.Font.montFont;
import static View.Mainframe.Sidebar.contentPanel;
import static View.Setup.*;

public class Skills {
    public static JPanel skillsPanel = new JPanel(new GridBagLayout());
    public static DefaultListModel<String> skillsListModel = new DefaultListModel<>();
    public static JTextField skillField = new JTextField(20);
    public static JButton addSkillButton = createContentButton("Add Skill");
    public static JButton removeSkillButton = createContentButton("Remove Skill");
    public static JList<String> skillsList = new JList<>(skillsListModel);
    static{
        // Skills Panel
        skillsPanel.setBackground(CustomColors.CONTENT_BG);
        JLabel skillsHeader = new JLabel("SKILLS");
        skillsHeader.setForeground(CustomColors.TEXT_COLOR);
        skillsHeader.setFont(cooperHewittBoldFont.deriveFont(24f));
        skillsList.setBackground(CustomColors.CONTENT_BG);
        skillsList.setForeground(CustomColors.TEXT_COLOR);
        skillsList.setFont(montFont);
        JScrollPane skillsScrollPane = new JScrollPane(skillsList);


        JLabel skillCheck = new JLabel("");
        setupTextField(skillField, skillCheck);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        skillsPanel.add(skillsHeader, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        skillsPanel.add(createLabel("Skill"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        skillsPanel.add(skillField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        skillsPanel.add(skillCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        skillsPanel.add(addSkillButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        skillsPanel.add(removeSkillButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        skillsPanel.add(skillsScrollPane, gbc);

    }
    public static void addSkillListener(ActionListener actionListener){
        addSkillButton.addActionListener(actionListener);
    }

    public static void addSkill(Resume resume){
        String skill = skillField.getText().trim();
        if (!skill.isEmpty()) {
            resume.addSkill(new Skill(skill));
            skillsListModel.addElement(skill);
            skillField.setText("");
            JOptionPane.showMessageDialog(skillsPanel, "Skill added successfully.");
        } else {
            JOptionPane.showMessageDialog(skillsPanel, "Please enter a skill.");
        }
    }

    public static void removeSkillListener(ActionListener actionListener){
        removeSkillButton.addActionListener(actionListener);
    }

    public static void removeSkill(Resume resume){
        int selectedIndex = skillsList.getSelectedIndex();
        if (selectedIndex != -1) {
            skillsListModel.remove(selectedIndex);
            resume.getSkills().remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(skillsPanel, "Please select a skill to remove.");
        }
    }
}
