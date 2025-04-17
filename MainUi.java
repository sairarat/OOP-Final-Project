import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.datatransfer.*;

public class MainUi {
    // Data structures for resume sections
    static class PersonalInfo {
        String name, email, phone, address;

        PersonalInfo(String name, String email, String phone, String address) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }
    }

    static class WorkExperience {
        String company, role, duration;
        List<String> achievements;

        WorkExperience(String company, String role, String duration) {
            this.company = company;
            this.role = role;
            this.duration = duration;
            this.achievements = new ArrayList<>();
        }

        void addAchievement(String achievement) {
            achievements.add(achievement);
        }
    }

    static class Project {
        String name, description;
        List<String> achievements;

        Project(String name, String description) {
            this.name = name;
            this.description = description;
            this.achievements = new ArrayList<>();
        }

        void addAchievement(String achievement) {
            achievements.add(achievement);
        }
    }

    static class Skill {
        String name;

        Skill(String name) {
            this.name = name;
        }
    }

    // Resume class to hold all sections
    static class Resume {
        PersonalInfo personalInfo;
        List<WorkExperience> workExperiences = new ArrayList<>();
        List<Project> projects = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();
        List<String> sectionOrder = new ArrayList<>();

        Resume(PersonalInfo personalInfo) {
            this.personalInfo = personalInfo;
            sectionOrder.add("Work Experience");
            sectionOrder.add("Projects");
            sectionOrder.add("Skills");
        }

        void addWorkExperience(WorkExperience exp) {
            workExperiences.add(exp);
        }

        void addProject(Project project) {
            projects.add(project);
        }

        void addSkill(Skill skill) {
            skills.add(skill);
        }

        void setSectionOrder(List<String> order) {
            sectionOrder.clear();
            sectionOrder.addAll(order);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    static void createAndShowGUI() {
        JFrame frame = new JFrame("Resume Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Resume object to store data
        Resume resume = new Resume(new PersonalInfo("", "", "", ""));

        // Tabbed pane for different sections
        JTabbedPane tabbedPane = new JTabbedPane();

        // Personal Info Panel
        JPanel personalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField addressField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        personalPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        personalPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        personalPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        personalPanel.add(emailField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        personalPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        personalPanel.add(phoneField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        personalPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        personalPanel.add(addressField, gbc);

        tabbedPane.addTab("Personal Info", personalPanel);

        // Work Experience Panel
        JPanel workPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> workListModel = new DefaultListModel<>();
        JList<String> workList = new JList<>(workListModel);
        JScrollPane workScrollPane = new JScrollPane(workList);

        JPanel workInputPanel = new JPanel(new GridBagLayout());
        JTextField companyField = new JTextField(20);
        JTextField roleField = new JTextField(20);
        JTextField durationField = new JTextField(20);
        JTextField achievementField = new JTextField(20);
        JButton addAchievementButton = new JButton("Add Achievement");
        JButton removeAchievementButton = new JButton("Remove Achievement");
        JButton addWorkButton = new JButton("Add Work Experience");

        List<String> currentAchievements = new ArrayList<>();
        DefaultListModel<String> achievementListModel = new DefaultListModel<>();
        JList<String> achievementList = new JList<>(achievementListModel);
        JScrollPane achievementScrollPane = new JScrollPane(achievementList);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        workInputPanel.add(new JLabel("Company:"), gbc);
        gbc.gridx = 1;
        workInputPanel.add(companyField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        workInputPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        workInputPanel.add(roleField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        workInputPanel.add(new JLabel("Duration:"), gbc);
        gbc.gridx = 1;
        workInputPanel.add(durationField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        workInputPanel.add(new JLabel("Achievement:"), gbc);
        gbc.gridx = 1;
        workInputPanel.add(achievementField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        workInputPanel.add(addAchievementButton, gbc);
        gbc.gridx = 1;
        workInputPanel.add(removeAchievementButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        workInputPanel.add(addWorkButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        workInputPanel.add(achievementScrollPane, gbc);

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

        addWorkButton.addActionListener(e -> {
            String company = companyField.getText().trim();
            String role = roleField.getText().trim();
            String duration = durationField.getText().trim();
            if (!company.isEmpty() && !role.isEmpty() && !duration.isEmpty()) {
                WorkExperience exp = new WorkExperience(company, role, duration);
                for (String ach : currentAchievements) {
                    exp.addAchievement(ach);
                }
                resume.addWorkExperience(exp);
                workListModel.addElement(company + " - " + role);
                companyField.setText("");
                roleField.setText("");
                durationField.setText("");
                achievementListModel.clear();
                currentAchievements.clear();
                JOptionPane.showMessageDialog(workPanel, "Work experience added successfully.");
            } else {
                JOptionPane.showMessageDialog(workPanel, "Please fill all fields.");
            }
        });

        workPanel.add(workInputPanel, BorderLayout.NORTH);
        workPanel.add(workScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Work Experience", workPanel);

        // Projects Panel
        JPanel projectPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> projectListModel = new DefaultListModel<>();
        JList<String> projectList = new JList<>(projectListModel);
        JScrollPane projectScrollPane = new JScrollPane(projectList);

        JPanel projectInputPanel = new JPanel(new GridBagLayout());
        JTextField projectNameField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JTextField projectAchievementField = new JTextField(20);
        JButton addProjectAchievementButton = new JButton("Add Achievement");
        JButton removeProjectAchievementButton = new JButton("Remove Achievement");
        JButton addProjectButton = new JButton("Add Project");

        List<String> currentProjectAchievements = new ArrayList<>();
        DefaultListModel<String> projectAchievementListModel = new DefaultListModel<>();
        JList<String> projectAchievementList = new JList<>(projectAchievementListModel);
        JScrollPane projectAchievementScrollPane = new JScrollPane(projectAchievementList);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        projectInputPanel.add(new JLabel("Project Name:"), gbc);
        gbc.gridx = 1;
        projectInputPanel.add(projectNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        projectInputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        projectInputPanel.add(descriptionField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        projectInputPanel.add(new JLabel("Achievement:"), gbc);
        gbc.gridx = 1;
        projectInputPanel.add(projectAchievementField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        projectInputPanel.add(addProjectAchievementButton, gbc);
        gbc.gridx = 1;
        projectInputPanel.add(removeProjectAchievementButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        projectInputPanel.add(addProjectButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        projectInputPanel.add(projectAchievementScrollPane, gbc);

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

        addProjectButton.addActionListener(e -> {
            String name = projectNameField.getText().trim();
            String description = descriptionField.getText().trim();
            if (!name.isEmpty() && !description.isEmpty()) {
                Project project = new Project(name, description);
                for (String ach : currentProjectAchievements) {
                    project.addAchievement(ach);
                }
                resume.addProject(project);
                projectListModel.addElement(name);
                projectNameField.setText("");
                descriptionField.setText("");
                projectAchievementListModel.clear();
                currentProjectAchievements.clear();
                JOptionPane.showMessageDialog(projectPanel, "Project added successfully.");
            } else {
                JOptionPane.showMessageDialog(projectPanel, "Please fill all fields.");
            }
        });

        projectPanel.add(projectInputPanel, BorderLayout.NORTH);
        projectPanel.add(projectScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Projects", projectPanel);

        // Skills Panel
        JPanel skillsPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> skillsListModel = new DefaultListModel<>();
        JList<String> skillsList = new JList<>(skillsListModel);
        JScrollPane skillsScrollPane = new JScrollPane(skillsList);

        JPanel skillInputPanel = new JPanel(new FlowLayout());
        JTextField skillField = new JTextField(20);
        JButton addSkillButton = new JButton("Add Skill");
        JButton removeSkillButton = new JButton("Remove Skill");

        skillInputPanel.add(new JLabel("Skill:"));
        skillInputPanel.add(skillField);
        skillInputPanel.add(addSkillButton);
        skillInputPanel.add(removeSkillButton);

        addSkillButton.addActionListener(e -> {
            String skill = skillField.getText().trim();
            if (!skill.isEmpty()) {
                resume.addSkill(new Skill(skill));
                skillsListModel.addElement(skill);
                skillField.setText("");
                JOptionPane.showMessageDialog(skillsPanel, "Skill added successfully.");
            } else {
                JOptionPane.showMessageDialog(skillsPanel, "Please enter a skill.");
            }
        });

        removeSkillButton.addActionListener(e -> {
            int selectedIndex = skillsList.getSelectedIndex();
            if (selectedIndex != -1) {
                skillsListModel.remove(selectedIndex);
                resume.skills.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(skillsPanel, "Please select a skill to remove.");
            }
        });

        skillsPanel.add(skillInputPanel, BorderLayout.NORTH);
        skillsPanel.add(skillsScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Skills", skillsPanel);

        // Section Order Panel
        JPanel orderPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> orderListModel = new DefaultListModel<>();
        orderListModel.addElement("Work Experience");
        orderListModel.addElement("Projects");
        orderListModel.addElement("Skills");
        JList<String> orderList = new JList<>(orderListModel);
        orderList.setDragEnabled(true);
        orderList.setDropMode(DropMode.INSERT);
        orderList.setTransferHandler(new ListTransferHandler());
        JScrollPane orderScrollPane = new JScrollPane(orderList);
        orderPanel.add(new JLabel("Drag to reorder sections:"), BorderLayout.NORTH);
        orderPanel.add(orderScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Section Order", orderPanel);

        // Save Button
        JButton saveButton = new JButton("Save Resume");
        saveButton.addActionListener(e -> {
            // Update personal info
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all personal info fields.");
                return;
            }
            resume.personalInfo = new PersonalInfo(name, email, phone, address);

            // Validate at least one section has content
            if (resume.workExperiences.isEmpty() && resume.projects.isEmpty() && resume.skills.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please add at least one work experience, project, or skill.");
                return;
            }

            // Update section order
            List<String> newOrder = new ArrayList<>();
            for (int i = 0; i < orderListModel.size(); i++) {
                newOrder.add(orderListModel.get(i));
            }
            resume.setSectionOrder(newOrder);

            // Save to file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("resume.txt"));
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String fileName = selectedFile.getAbsolutePath();
                if (!fileName.endsWith(".txt")) {
                    fileName += ".txt";
                    selectedFile = new File(fileName);
                }
                // Check for file overwrite
                if (selectedFile.exists()) {
                    int result = JOptionPane.showConfirmDialog(frame,
                            "File already exists. Do you want to overwrite it?",
                            "Confirm Overwrite",
                            JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                generateTextFile(resume, fileName);
                JOptionPane.showMessageDialog(frame, "Resume saved as " + fileName);
            }
        });

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(saveButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    static void generateTextFile(Resume resume, String fileName) {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            // Add personal info
            writer.println(resume.personalInfo.name.toUpperCase());
            writer.println(resume.personalInfo.email);
            writer.println(resume.personalInfo.phone);
            writer.println(resume.personalInfo.address);
            writer.println();

            // Add sections in specified order
            for (String section : resume.sectionOrder) {
                if (section.equals("Work Experience") && !resume.workExperiences.isEmpty()) {
                    writer.println("WORK EXPERIENCE");
                    writer.println("--------------");
                    for (WorkExperience exp : resume.workExperiences) {
                        writer.println(exp.company + " - " + exp.role + " (" + exp.duration + ")");
                        for (String achievement : exp.achievements) {
                            writer.println("  * " + achievement);
                        }
                        writer.println();
                    }
                } else if (section.equals("Projects") && !resume.projects.isEmpty()) {
                    writer.println("PROJECTS");
                    writer.println("--------");
                    for (Project project : resume.projects) {
                        writer.println(project.name);
                        writer.println(project.description);
                        for (String achievement : project.achievements) {
                            writer.println("  * " + achievement);
                        }
                        writer.println();
                    }
                } else if (section.equals("Skills") && !resume.skills.isEmpty()) {
                    writer.println("SKILLS");
                    writer.println("------");
                    for (Skill skill : resume.skills) {
                        writer.println("  * " + skill.name);
                    }
                    writer.println();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error generating text file: " + e.getMessage());
        }
    }

    // Custom TransferHandler for drag-and-drop reordering
    static class ListTransferHandler extends TransferHandler {
        private int index = -1;

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            if (!(c instanceof JList)) {
                return null;
            }
            JList<?> list = (JList<?>) c;
            index = list.getSelectedIndex();
            if (index == -1) {
                return null;
            }
            Object selectedValue = list.getSelectedValue();
            if (selectedValue == null) {
                return null;
            }
            return new StringSelection(selectedValue.toString());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            JList<?> list = (JList<?>) support.getComponent();
            if (!(list.getModel() instanceof DefaultListModel)) {
                JOptionPane.showMessageDialog(null, "List model is not DefaultListModel.");
                return false;
            }

            @SuppressWarnings("unchecked")
            DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            int dropIndex = dl.getIndex();

            if (index < 0 || index >= model.getSize() || dropIndex < 0 || dropIndex > model.getSize()) {
                JOptionPane.showMessageDialog(null, "Invalid index for drag-and-drop.");
                return false;
            }

            try {
                String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                if (data == null || data.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No data transferred.");
                    return false;
                }
                model.remove(index);
                model.add(dropIndex, data);
                return true;
            } catch (UnsupportedFlavorException e) {
                JOptionPane.showMessageDialog(null, "Unsupported data flavor: " + e.getMessage());
                return false;
            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(null, "IO error during transfer: " + e.getMessage());
                return false;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Unexpected error during drag-and-drop: " + e.getMessage());
                return false;
            }
        }
    }
}