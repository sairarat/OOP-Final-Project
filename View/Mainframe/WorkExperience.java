package View.Mainframe;

import View.Constants.CustomColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static View.Constants.Font.cooperHewittBoldFont;
import static View.Constants.Font.montFont;
import static View.Setup.*;
import static View.Setup.createLabel;
import Model.Resume;
import View.Constants.CustomColors;

public class WorkExperience {
    public static JTextField companyField = new JTextField(20);
    public static JTextField roleField = new JTextField(20);
    public static JTextField durationField = new JTextField(20);
    public static JTextField achievementField = new JTextField(20);
    public static List<String> currentAchievements = new ArrayList<>();
    public static DefaultListModel<String> achievementListModel = new DefaultListModel<>();
    public static JList<String> achievementList = new JList<>(achievementListModel);
    public static DefaultListModel<String> workListModel = new DefaultListModel<>();
    public static JPanel workPanel = new JPanel(new GridBagLayout());
    public static JButton addWorkButton = createContentButton("Add Work Experience");
    static{
        // Work Experience Panel
        workPanel.setBackground(CustomColors.CONTENT_BG);

        JLabel workHeader = new JLabel("WORK EXPERIENCE");
        workHeader.setForeground(CustomColors.TEXT_COLOR);
        workHeader.setFont(cooperHewittBoldFont.deriveFont(24f));

        JList<String> workList = new JList<>(workListModel);
        workList.setBackground(CustomColors.CONTENT_BG);
        workList.setForeground(CustomColors.TEXT_COLOR);
        workList.setFont(montFont);
        JScrollPane workScrollPane = new JScrollPane(workList);


        JLabel companyCheck = new JLabel("");
        JLabel roleCheck = new JLabel("");
        JLabel durationWorkCheck = new JLabel("");
        JLabel achievementCheck = new JLabel("");

        setupTextField(companyField, companyCheck);
        setupTextField(roleField, roleCheck);
        setupTextField(durationField, durationWorkCheck);
        setupTextField(achievementField, achievementCheck);

        JButton addAchievementButton = createContentButton("Add Achievement");
        JButton removeAchievementButton = createContentButton("Remove Achievement");

        achievementList.setBackground(CustomColors.CONTENT_BG);
        achievementList.setForeground(CustomColors.TEXT_COLOR);
        achievementList.setFont(montFont);
        JScrollPane achievementScrollPane = new JScrollPane(achievementList);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        workPanel.add(workHeader, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        workPanel.add(createLabel("Company"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        workPanel.add(companyField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        workPanel.add(companyCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        workPanel.add(createLabel("Role"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        workPanel.add(roleField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        workPanel.add(roleCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        workPanel.add(createLabel("Duration"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        workPanel.add(durationField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        workPanel.add(durationWorkCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        workPanel.add(createLabel("Achievement"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        workPanel.add(achievementField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        workPanel.add(achievementCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        workPanel.add(addAchievementButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        workPanel.add(removeAchievementButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        workPanel.add(addWorkButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        workPanel.add(achievementScrollPane, gbc);

        addAchievementButton.addActionListener(e -> {
            String achievement = achievementField.getText().trim();
            if (!achievement.isEmpty()) {
                achievementListModel.addElement(achievement);
                currentAchievements.add(achievement);
                achievementField.setText("");
            } else {
                JOptionPane.showMessageDialog(workPanel, "Please enter an achievement.");
            }
        });

        removeAchievementButton.addActionListener(e -> {
            int selectedIndex = achievementList.getSelectedIndex();
            if (selectedIndex != -1) {
                achievementListModel.remove(selectedIndex);
                currentAchievements.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(workPanel, "Please select an achievement to remove.");
            }
        });



    }

    public static void addWorkListener (ActionListener actionListener){
        addWorkButton.addActionListener(actionListener);
    }
    public static void addWork(Resume resume){
        String company = companyField.getText().trim();
        String role = roleField.getText().trim();
        String duration = durationField.getText().trim();
        if (!company.isEmpty() && !role.isEmpty() && !duration.isEmpty()) {
            Model.WorkExperience exp = new Model.WorkExperience(company, role, duration);
            for (String ach : currentAchievements) {
                exp.addAchievement(ach);
            }
            resume.addWorkExperience(exp);
            workListModel.addElement(company + " - " + role + " (" + duration + ")");
            companyField.setText("");
            roleField.setText("");
            durationField.setText("");
            achievementListModel.clear();
            currentAchievements.clear();
            JOptionPane.showMessageDialog(workPanel, "Work experience added successfully.");
        } else {
            JOptionPane.showMessageDialog(workPanel, "Please fill all fields.");
        }
    }
}
