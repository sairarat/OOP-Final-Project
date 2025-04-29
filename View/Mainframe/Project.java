package View.Mainframe;

import View.Constants.CustomColors;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static View.Constants.Font.cooperHewittBoldFont;
import static View.Constants.Font.montFont;
import static View.Setup.*;
import Model.Resume;

public class Project {
    public static JTextField projectNameField = new JTextField(20);
    public static JTextField descriptionField = new JTextField(20);
    public static JTextField projectAchievementField = new JTextField(20);
    public static JButton addProjectButton = createContentButton("Add Project");
    public static JPanel projectPanel = new JPanel(new GridBagLayout());
    public static DefaultListModel<String> projectListModel = new DefaultListModel<>();
    public static DefaultListModel<String> projectAchievementListModel = new DefaultListModel<>();
    public static List<String> currentProjectAchievements = new ArrayList<>();

    static {
        // Projects Panel
        projectPanel.setBackground(CustomColors.CONTENT_BG);
        JLabel projectHeader = new JLabel("PROJECTS");
        projectHeader.setForeground(CustomColors.TEXT_COLOR);
        projectHeader.setFont(cooperHewittBoldFont.deriveFont(24f));


        JList<String> projectList = new JList<>(projectListModel);
        projectList.setBackground(CustomColors.CONTENT_BG);
        projectList.setForeground(CustomColors.TEXT_COLOR);
        projectList.setFont(montFont);
        JScrollPane projectScrollPane = new JScrollPane(projectList);


        JLabel projectNameCheck = new JLabel("");
        JLabel descriptionCheck = new JLabel("");
        JLabel projectAchievementCheck = new JLabel("");

        setupTextField(projectNameField, projectNameCheck);
        setupTextField(descriptionField, descriptionCheck);
        setupTextField(projectAchievementField, projectAchievementCheck);

        JButton addProjectAchievementButton = createContentButton("Add Achievement");
        JButton removeProjectAchievementButton = createContentButton("Remove Achievement");


        JList<String> projectAchievementList = new JList<>(projectAchievementListModel);
        projectAchievementList.setBackground(CustomColors.CONTENT_BG);
        projectAchievementList.setForeground(CustomColors.TEXT_COLOR);
        projectAchievementList.setFont(montFont);
        JScrollPane projectAchievementScrollPane = new JScrollPane(projectAchievementList);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        projectPanel.add(projectHeader, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        projectPanel.add(createLabel("Project Name"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        projectPanel.add(projectNameField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        projectPanel.add(projectNameCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        projectPanel.add(createLabel("Description"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        projectPanel.add(descriptionField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        projectPanel.add(descriptionCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        projectPanel.add(createLabel("Achievement"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        projectPanel.add(projectAchievementField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        projectPanel.add(projectAchievementCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        projectPanel.add(addProjectAchievementButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        projectPanel.add(removeProjectAchievementButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        projectPanel.add(addProjectButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        projectPanel.add(projectAchievementScrollPane, gbc);

        addProjectAchievementButton.addActionListener(e -> {
            String achievement = projectAchievementField.getText().trim();
            if (!achievement.isEmpty()) {
                projectAchievementListModel.addElement(achievement);
                currentProjectAchievements.add(achievement);
                projectAchievementField.setText("");
            } else {
                JOptionPane.showMessageDialog(projectPanel, "Please enter an achievement.");
            }
        });

        removeProjectAchievementButton.addActionListener(e -> {
            int selectedIndex = projectAchievementList.getSelectedIndex();
            if (selectedIndex != -1) {
                projectAchievementListModel.remove(selectedIndex);
                currentProjectAchievements.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(projectPanel, "Please select an achievement to remove.");
            }
        });
    }

    public static void addProject(Resume resume){
        String name = projectNameField.getText().trim();
        String description = descriptionField.getText().trim();
        if (!name.isEmpty() && !description.isEmpty()) {
            Model.Project project = new Model.Project(name, description);
            for (String ach : currentProjectAchievements) {
                project.addAchievement(ach);
            }
            resume.addProject(project);
            projectListModel.addElement(name + " - " + description);
            projectNameField.setText("");
            descriptionField.setText("");
            projectAchievementListModel.clear();
            currentProjectAchievements.clear();
            JOptionPane.showMessageDialog(projectPanel, "Project added successfully.");
        } else {
            JOptionPane.showMessageDialog(projectPanel, "Please fill all fields.");
        }
    }
}
