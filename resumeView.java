import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class Colors {
    static final Color SIDEBAR_BG = new Color(157, 178, 191);
    static final Color BUTTON_DEFAULT = new Color(82, 109, 130);
    static final Color BUTTON_HOVER = new Color(39, 55, 77);
    static final Color BUTTON_SELECTED = new Color(39, 55, 77);
    static final Color CONTENT_BG = new Color(26, 37, 56);
    static final Color TEXT_COLOR = new Color(221, 230, 237);
    static final Color BORDER_COLOR = new Color(157, 178, 191);
}

public class resumeView {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private Color customSidebarColor = Colors.SIDEBAR_BG;
    private Color customMainColor = Colors.CONTENT_BG;

    private Font cooperHewittRegularFont;
    private Font cooperHewittBoldFont;
    private Font montFont;
    private Font checkmarkFont;

    private Consumer<String[]> onPersonalInfoSubmitted; // name, email, phone, address, photoPath
    private Consumer<String[]> onEducationSubmitted; // institution, degree, duration
    private Consumer<Object[]> onWorkExperienceSubmitted; // company, role, duration, List<String> achievements
    private Consumer<Object[]> onProjectSubmitted; // name, description, List<String> achievements
    private Consumer<String> onSkillSubmitted; // skill
    private Consumer<String> onRemoveSkill; // skill
    private Consumer<String[]> onLanguageSubmitted; // language, proficiency
    private Consumer<String> onRemoveLanguage; // language
    private Consumer<List<String>> onSectionOrderSubmitted; // new section order
    private Runnable onCustomizeColors;
    private Runnable onPreviewResume;
    private Runnable onSaveResume;

    private JList<String> educationList;
    private JList<String> workList;
    private JList<String> projectList;
    private JList<String> skillsList;
    private JList<String> languagesList;

    public resumeView(resumeController controller) {
        loadFonts();
        createAndShowGUI();
    }

    private void loadFonts() {
        try {
            cooperHewittRegularFont = Font.createFont(Font.TRUETYPE_FONT, new File("CooperHewitt-Regular.ttf")).deriveFont(14f);
            cooperHewittBoldFont = Font.createFont(Font.TRUETYPE_FONT, new File("CooperHewitt-Bold.ttf")).deriveFont(14f);
            montFont = Font.createFont(Font.TRUETYPE_FONT, new File("Montserrat-Regular.ttf")).deriveFont(14f);
            checkmarkFont = new Font("Arial", Font.PLAIN, 16);
        } catch (Exception e) {
            showMessage("Failed to load custom fonts. Using system defaults.");
            cooperHewittRegularFont = new Font("SansSerif", Font.PLAIN, 14);
            cooperHewittBoldFont = new Font("SansSerif", Font.BOLD, 14);
            montFont = new Font("SansSerif", Font.PLAIN, 14);
            checkmarkFont = new Font("Arial", Font.PLAIN, 16);
        }
    }

    private void createAndShowGUI() {
        frame = new JFrame("Resume Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(customSidebarColor);
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        sidebarPanel.setPreferredSize(new Dimension(250, sidebarPanel.getPreferredSize().height));

        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(customMainColor);

        JButton personalInfoButton = createSidebarButton("PERSONAL INFO");
        JButton educationButton = createSidebarButton("EDUCATION");
        JButton workButton = createSidebarButton("WORK EXPERIENCE");
        JButton projectButton = createSidebarButton("PROJECTS");
        JButton skillsButton = createSidebarButton("SKILLS");
        JButton languagesButton = createSidebarButton("LANGUAGES");
        JButton sectionOrderButton = createSidebarButton("SECTION ORDER");
        JButton customizeColorsButton = createSidebarButton("CUSTOMIZE COLORS");
        JButton previewButton = createSidebarButton("PREVIEW RESUME");
        JButton saveButton = createSidebarButton("SAVE RESUME");

        saveButton.setFont(cooperHewittBoldFont);
        previewButton.setFont(cooperHewittBoldFont);
        customizeColorsButton.setFont(cooperHewittBoldFont);
        sectionOrderButton.setFont(cooperHewittBoldFont);

        personalInfoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        educationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        workButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        projectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        skillsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        languagesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sectionOrderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customizeColorsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        previewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebarPanel.add(personalInfoButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(educationButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(workButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(projectButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(skillsButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(languagesButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(sectionOrderButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(customizeColorsButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(previewButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(saveButton);

        JButton[] sidebarButtons = {personalInfoButton, educationButton, workButton, projectButton, skillsButton, languagesButton, sectionOrderButton};

        contentPanel.add(createPersonalInfoPanel(), "Personal Info");
        contentPanel.add(createEducationPanel(), "Education");
        contentPanel.add(createWorkPanel(), "Work Experience");
        contentPanel.add(createProjectPanel(), "Projects");
        contentPanel.add(createSkillsPanel(), "Skills");
        contentPanel.add(createLanguagesPanel(), "Languages");
        contentPanel.add(createSectionOrderPanel(), "Section Order");

        frame.add(sidebarPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        personalInfoButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Personal Info");
            updateButtonSelection(personalInfoButton, sidebarButtons);
        });
        educationButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Education");
            updateButtonSelection(educationButton, sidebarButtons);
        });
        workButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Work Experience");
            updateButtonSelection(workButton, sidebarButtons);
        });
        projectButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Projects");
            updateButtonSelection(projectButton, sidebarButtons);
        });
        skillsButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Skills");
            updateButtonSelection(skillsButton, sidebarButtons);
        });
        languagesButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Languages");
            updateButtonSelection(languagesButton, sidebarButtons);
        });
        sectionOrderButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Section Order");
            updateButtonSelection(sectionOrderButton, sidebarButtons);
        });
        customizeColorsButton.addActionListener(e -> {
            showColorCustomizationDialog();
            if (onCustomizeColors != null) onCustomizeColors.run();
        });
        previewButton.addActionListener(e -> {
            if (onPreviewResume != null) onPreviewResume.run();
        });
        saveButton.addActionListener(e -> {
            if (onSaveResume != null) onSaveResume.run();
        });

        updateButtonSelection(personalInfoButton, sidebarButtons);
        frame.setVisible(true);
    }

    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(customMainColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel header = new JLabel("PERSONAL INFO");
        header.setForeground(Colors.TEXT_COLOR);
        header.setFont(cooperHewittBoldFont.deriveFont(24f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(header, gbc);

        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextArea profileField = new JTextArea(5, 20);
        profileField.setLineWrap(true);
        profileField.setWrapStyleWord(true);
        JScrollPane profileScrollPane = new JScrollPane(profileField);
        JButton uploadPhotoButton = createContentButton("Upload Photo");
        JLabel photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(150, 150));
        photoLabel.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));

        JLabel nameCheck = new JLabel("");
        JLabel emailCheck = new JLabel("");
        JLabel phoneCheck = new JLabel("");
        JLabel addressCheck = new JLabel("");
        JLabel profileCheck = new JLabel("");

        setupTextField(nameField, nameCheck);
        setupTextField(emailField, emailCheck);
        setupTextField(phoneField, phoneCheck);
        setupTextField(addressField, addressCheck);
        setupTextArea(profileField, profileCheck);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createLabel("Name"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        gbc.gridx = 2;
        panel.add(nameCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createLabel("Email"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        gbc.gridx = 2;
        panel.add(emailCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(createLabel("Phone"), gbc);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);
        gbc.gridx = 2;
        panel.add(phoneCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(createLabel("Address"), gbc);
        gbc.gridx = 1;
        panel.add(addressField, gbc);
        gbc.gridx = 2;
        panel.add(addressCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(createLabel("Profile"), gbc);
        gbc.gridx = 1;
        panel.add(profileScrollPane, gbc);
        gbc.gridx = 2;
        panel.add(profileCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(createLabel("Photo"), gbc);
        gbc.gridx = 1;
        panel.add(uploadPhotoButton, gbc);
        gbc.gridy = 7;
        panel.add(photoLabel, gbc);

        final String[] photoPath = {""};
        uploadPhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    photoPath[0] = file.getAbsolutePath();
                    ImageIcon image = new ImageIcon(photoPath[0]);
                    Image scaledImage = image.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    photoLabel.setIcon(new ImageIcon(scaledImage));
                    showMessage("Photo uploaded successfully.");
                } catch (Exception ex) {
                    showMessage("Error loading image: " + ex.getMessage());
                }
            }
        });

        JButton submitButton = createContentButton("Save Personal Info");
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            if (onPersonalInfoSubmitted != null) {
                onPersonalInfoSubmitted.accept(new String[]{
                        nameField.getText().trim(),
                        emailField.getText().trim(),
                        phoneField.getText().trim(),
                        addressField.getText().trim(),
                        photoPath[0]
                });
            }
        });

        return panel;
    }

    private JPanel createEducationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(customMainColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel header = new JLabel("EDUCATION");
        header.setForeground(Colors.TEXT_COLOR);
        header.setFont(cooperHewittBoldFont.deriveFont(24f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(header, gbc);

        JTextField institutionField = new JTextField(20);
        JTextField degreeField = new JTextField(20);
        JTextField durationField = new JTextField(20);

        JLabel institutionCheck = new JLabel("");
        JLabel degreeCheck = new JLabel("");
        JLabel durationCheck = new JLabel("");

        setupTextField(institutionField, institutionCheck);
        setupTextField(degreeField, degreeCheck);
        setupTextField(durationField, durationCheck);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createLabel("Institution"), gbc);
        gbc.gridx = 1;
        panel.add(institutionField, gbc);
        gbc.gridx = 2;
        panel.add(institutionCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createLabel("Degree"), gbc);
        gbc.gridx = 1;
        panel.add(degreeField, gbc);
        gbc.gridx = 2;
        panel.add(degreeCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(createLabel("Duration"), gbc);
        gbc.gridx = 1;
        panel.add(durationField, gbc);
        gbc.gridx = 2;
        panel.add(durationCheck, gbc);

        JButton addButton = createContentButton("Add Education");
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(addButton, gbc);

        educationList = new JList<>();
        educationList.setBackground(customMainColor);
        educationList.setForeground(Colors.TEXT_COLOR);
        educationList.setFont(montFont);
        JScrollPane educationScrollPane = new JScrollPane(educationList);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        panel.add(educationScrollPane, gbc);

        addButton.addActionListener(e -> {
            if (onEducationSubmitted != null) {
                onEducationSubmitted.accept(new String[]{
                        institutionField.getText().trim(),
                        degreeField.getText().trim(),
                        durationField.getText().trim()
                });
                institutionField.setText("");
                degreeField.setText("");
                durationField.setText("");
            }
        });

        return panel;
    }

    private JPanel createWorkPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(customMainColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel header = new JLabel("WORK EXPERIENCE");
        header.setForeground(Colors.TEXT_COLOR);
        header.setFont(cooperHewittBoldFont.deriveFont(24f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(header, gbc);

        JTextField companyField = new JTextField(20);
        JTextField roleField = new JTextField(20);
        JTextField durationField = new JTextField(20);
        JTextField achievementField = new JTextField(20);

        JLabel companyCheck = new JLabel("");
        JLabel roleCheck = new JLabel("");
        JLabel durationCheck = new JLabel("");
        JLabel achievementCheck = new JLabel("");

        setupTextField(companyField, companyCheck);
        setupTextField(roleField, roleCheck);
        setupTextField(durationField, durationCheck);
        setupTextField(achievementField, achievementCheck);

        DefaultListModel<String> achievementListModel = new DefaultListModel<>();
        JList<String> achievementList = new JList<>(achievementListModel);
        achievementList.setBackground(customMainColor); // <-- fixed
        achievementList.setForeground(Colors.TEXT_COLOR);
        achievementList.setFont(montFont);
        JScrollPane achievementScrollPane = new JScrollPane(achievementList);


        List<String> currentAchievements = new ArrayList<>();

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createLabel("Company"), gbc);
        gbc.gridx = 1;
        panel.add(companyField, gbc);
        gbc.gridx = 2;
        panel.add(companyCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createLabel("Role"), gbc);
        gbc.gridx = 1;
        panel.add(roleField, gbc);
        gbc.gridx = 2;
        panel.add(roleCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(createLabel("Duration"), gbc);
        gbc.gridx = 1;
        panel.add(durationField, gbc);
        gbc.gridx = 2;
        panel.add(durationCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(createLabel("Achievement"), gbc);
        gbc.gridx = 1;
        panel.add(achievementField, gbc);
        gbc.gridx = 2;
        panel.add(achievementCheck, gbc);

        JButton addAchievementButton = createContentButton("Add Achievement");
        JButton removeAchievementButton = createContentButton("Remove Achievement");
        JButton addWorkButton = createContentButton("Add Work Experience");

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(addAchievementButton, gbc);
        gbc.gridx = 1;
        panel.add(removeAchievementButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        panel.add(achievementScrollPane, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(addWorkButton, gbc);

        workList = new JList<>();
        workList.setBackground(customMainColor);
        workList.setForeground(Colors.TEXT_COLOR);
        workList.setFont(montFont);
        JScrollPane workScrollPane = new JScrollPane(workList);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        panel.add(workScrollPane, gbc);

        addAchievementButton.addActionListener(e -> {
            String achievement = achievementField.getText().trim();
            if (!achievement.isEmpty()) {
                achievementListModel.addElement(achievement);
                currentAchievements.add(achievement);
                achievementField.setText("");
            } else {
                showMessage("Please enter an achievement.");
            }
        });

        removeAchievementButton.addActionListener(e -> {
            int selectedIndex = achievementList.getSelectedIndex();
            if (selectedIndex != -1) {
                achievementListModel.remove(selectedIndex);
                currentAchievements.remove(selectedIndex);
            } else {
                showMessage("Please select an achievement to remove.");
            }
        });

        addWorkButton.addActionListener(e -> {
            if (onWorkExperienceSubmitted != null) {
                onWorkExperienceSubmitted.accept(new Object[]{
                        companyField.getText().trim(),
                        roleField.getText().trim(),
                        durationField.getText().trim(),
                        new ArrayList<>(currentAchievements)
                });
                companyField.setText("");
                roleField.setText("");
                durationField.setText("");
                achievementListModel.clear();
                currentAchievements.clear();
            }
        });

        return panel;
    }

    private JPanel createProjectPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(customMainColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel header = new JLabel("PROJECTS");
        header.setForeground(Colors.TEXT_COLOR);
        header.setFont(cooperHewittBoldFont.deriveFont(24f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(header, gbc);

        JTextField nameField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JTextField achievementField = new JTextField(20);

        JLabel nameCheck = new JLabel("");
        JLabel descriptionCheck = new JLabel("");
        JLabel achievementCheck = new JLabel("");

        setupTextField(nameField, nameCheck);
        setupTextField(descriptionField, descriptionCheck);
        setupTextField(achievementField, achievementCheck);

        DefaultListModel<String> achievementListModel = new DefaultListModel<>();
        JList<String> achievementList = new JList<>(achievementListModel);
        achievementList.setBackground(customMainColor);
        achievementList.setForeground(Colors.TEXT_COLOR);
        achievementList.setFont(montFont);
        JScrollPane achievementScrollPane = new JScrollPane(achievementList);

        List<String> currentAchievements = new ArrayList<>();

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createLabel("Project Name"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        gbc.gridx = 2;
        panel.add(nameCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createLabel("Description"), gbc);
        gbc.gridx = 1;
        panel.add(descriptionField, gbc);
        gbc.gridx = 2;
        panel.add(descriptionCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(createLabel("Achievement"), gbc);
        gbc.gridx = 1;
        panel.add(achievementField, gbc);
        gbc.gridx = 2;
        panel.add(achievementCheck, gbc);

        JButton addAchievementButton = createContentButton("Add Achievement");
        JButton removeAchievementButton = createContentButton("Remove Achievement");
        JButton addProjectButton = createContentButton("Add Project");

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(addAchievementButton, gbc);
        gbc.gridx = 1;
        panel.add(removeAchievementButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        panel.add(achievementScrollPane, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(addProjectButton, gbc);

        projectList = new JList<>();
        projectList.setBackground(customMainColor);
        projectList.setForeground(Colors.TEXT_COLOR);
        projectList.setFont(montFont);
        JScrollPane projectScrollPane = new JScrollPane(projectList);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        panel.add(projectScrollPane, gbc);

        addAchievementButton.addActionListener(e -> {
            String achievement = achievementField.getText().trim();
            if (!achievement.isEmpty()) {
                achievementListModel.addElement(achievement);
                currentAchievements.add(achievement);
                achievementField.setText("");
            } else {
                showMessage("Please enter an achievement.");
            }
        });

        removeAchievementButton.addActionListener(e -> {
            int selectedIndex = achievementList.getSelectedIndex();
            if (selectedIndex != -1) {
                achievementListModel.remove(selectedIndex);
                currentAchievements.remove(selectedIndex);
            } else {
                showMessage("Please select an achievement to remove.");
            }
        });

        addProjectButton.addActionListener(e -> {
            if (onProjectSubmitted != null) {
                onProjectSubmitted.accept(new Object[]{
                        nameField.getText().trim(),
                        descriptionField.getText().trim(),
                        new ArrayList<>(currentAchievements)
                });
                nameField.setText("");
                descriptionField.setText("");
                achievementListModel.clear();
                currentAchievements.clear();
            }
        });

        return panel;
    }

    private JPanel createSkillsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(customMainColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel header = new JLabel("SKILLS");
        header.setForeground(Colors.TEXT_COLOR);
        header.setFont(cooperHewittBoldFont.deriveFont(24f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(header, gbc);

        JTextField skillField = new JTextField(20);
        JLabel skillCheck = new JLabel("");
        setupTextField(skillField, skillCheck);

        JButton addSkillButton = createContentButton("Add Skill");
        JButton removeSkillButton = createContentButton("Remove Skill");

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createLabel("Skill"), gbc);
        gbc.gridx = 1;
        panel.add(skillField, gbc);
        gbc.gridx = 2;
        panel.add(skillCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(addSkillButton, gbc);
        gbc.gridx = 1;
        panel.add(removeSkillButton, gbc);

        skillsList = new JList<>();
        skillsList.setBackground(customMainColor);
        skillsList.setForeground(Colors.TEXT_COLOR);
        skillsList.setFont(montFont);
        JScrollPane skillsScrollPane = new JScrollPane(skillsList);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        panel.add(skillsScrollPane, gbc);

        addSkillButton.addActionListener(e -> {
            String skill = skillField.getText().trim();
            if (onSkillSubmitted != null && !skill.isEmpty()) {
                onSkillSubmitted.accept(skill);
                skillField.setText("");
            } else {
                showMessage("Please enter a skill.");
            }
        });

        removeSkillButton.addActionListener(e -> {
            String selectedSkill = skillsList.getSelectedValue();
            if (selectedSkill != null && onRemoveSkill != null) {
                onRemoveSkill.accept(selectedSkill);
            } else {
                showMessage("Please select a skill to remove.");
            }
        });

        return panel;
    }

    private JPanel createLanguagesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(customMainColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel header = new JLabel("LANGUAGES");
        header.setForeground(Colors.TEXT_COLOR);
        header.setFont(cooperHewittBoldFont.deriveFont(24f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(header, gbc);

        JTextField languageField = new JTextField(20);
        JComboBox<String> proficiencyComboBox = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced", "Fluent"});
        proficiencyComboBox.setBackground(customMainColor);
        proficiencyComboBox.setForeground(Colors.TEXT_COLOR);
        proficiencyComboBox.setFont(montFont);

        JLabel languageCheck = new JLabel("");
        setupTextField(languageField, languageCheck);

        JButton addLanguageButton = createContentButton("Add Language");
        JButton removeLanguageButton = createContentButton("Remove Language");

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createLabel("Language"), gbc);
        gbc.gridx = 1;
        panel.add(languageField, gbc);
        gbc.gridx = 2;
        panel.add(languageCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createLabel("Proficiency"), gbc);
        gbc.gridx = 1;
        panel.add(proficiencyComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(addLanguageButton, gbc);
        gbc.gridx = 1;
        panel.add(removeLanguageButton, gbc);

        languagesList = new JList<>();
        languagesList.setBackground(customMainColor);
        languagesList.setForeground(Colors.TEXT_COLOR);
        languagesList.setFont(montFont);
        JScrollPane languagesScrollPane = new JScrollPane(languagesList);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        panel.add(languagesScrollPane, gbc);

        addLanguageButton.addActionListener(e -> {
            String language = languageField.getText().trim();
            String proficiency = (String) proficiencyComboBox.getSelectedItem();
            if (onLanguageSubmitted != null && !language.isEmpty()) {
                onLanguageSubmitted.accept(new String[]{language, proficiency});
                languageField.setText("");
                proficiencyComboBox.setSelectedIndex(0);
            } else {
                showMessage("Please enter a language.");
            }
        });

        removeLanguageButton.addActionListener(e -> {
            String selectedLanguage = languagesList.getSelectedValue();
            if (selectedLanguage != null && onRemoveLanguage != null) {
                String languageName = selectedLanguage.split(" \\(")[0];
                onRemoveLanguage.accept(languageName);
            } else {
                showMessage("Please select a language to remove.");
            }
        });

        return panel;
    }

    private JPanel createSectionOrderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(customMainColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel header = new JLabel("SECTION ORDER");
        header.setForeground(Colors.TEXT_COLOR);
        header.setFont(cooperHewittBoldFont.deriveFont(24f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(header, gbc);

        DefaultListModel<String> sectionOrderModel = new DefaultListModel<>();
        sectionOrderModel.addElement("Education");
        sectionOrderModel.addElement("Work Experience");
        sectionOrderModel.addElement("Projects");
        sectionOrderModel.addElement("Skills");
        sectionOrderModel.addElement("Languages");

        JList<String> sectionOrderList = new JList<>(sectionOrderModel);
        sectionOrderList.setBackground(customMainColor);
        sectionOrderList.setForeground(Colors.TEXT_COLOR);
        sectionOrderList.setFont(montFont);
        sectionOrderList.setDragEnabled(true);
        sectionOrderList.setDropMode(DropMode.INSERT);
        sectionOrderList.setTransferHandler(new ListItemTransferHandler());

        JScrollPane sectionOrderScrollPane = new JScrollPane(sectionOrderList);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(sectionOrderScrollPane, gbc);

        JButton saveOrderButton = createContentButton("Save Section Order");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(saveOrderButton, gbc);

        saveOrderButton.addActionListener(e -> {
            if (onSectionOrderSubmitted != null) {
                List<String> newOrder = new ArrayList<>();
                ListModel<String> model = sectionOrderList.getModel();
                for (int i = 0; i < model.getSize(); i++) {
                    newOrder.add(model.getElementAt(i));
                }
                onSectionOrderSubmitted.accept(newOrder);
            }
        });

        return panel;
    }


    private void showColorCustomizationDialog() {
        JDialog colorDialog = new JDialog(frame, "Customize Colors", true);
        colorDialog.setSize(400, 300);
        colorDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel sidebarColorLabel = new JLabel("Sidebar Color:");
        JLabel mainColorLabel = new JLabel("Main Content Color:");
        JButton sidebarColorButton = new JButton("Choose Color");
        JButton mainColorButton = new JButton("Choose Color");
        JButton applyButton = new JButton("Apply");

        sidebarColorButton.setBackground(customSidebarColor);
        mainColorButton.setBackground(customMainColor);

        sidebarColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(colorDialog, "Choose Sidebar Color", customSidebarColor);
            if (newColor != null) {
                customSidebarColor = newColor;
                sidebarColorButton.setBackground(newColor);
                sidebarPanel.setBackground(newColor);
            }
        });

        mainColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(colorDialog, "Choose Main Content Color", customMainColor);
            if (newColor != null) {
                customMainColor = newColor;
                mainColorButton.setBackground(newColor);
                contentPanel.setBackground(newColor);
                for (Component comp : contentPanel.getComponents()) {
                    comp.setBackground(newColor);
                }
            }
        });

        applyButton.addActionListener(e -> colorDialog.dispose());

        gbc.gridx = 0;
        gbc.gridy = 0;
        colorDialog.add(sidebarColorLabel, gbc);
        gbc.gridx = 1;
        colorDialog.add(sidebarColorButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        colorDialog.add(mainColorLabel, gbc);
        gbc.gridx = 1;
        colorDialog.add(mainColorButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        colorDialog.add(applyButton, gbc);

        colorDialog.setLocationRelativeTo(frame);
        colorDialog.setVisible(true);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Colors.BUTTON_DEFAULT);
        button.setForeground(Colors.TEXT_COLOR);
        button.setFont(cooperHewittRegularFont);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.getBackground() != Colors.BUTTON_SELECTED) {
                    button.setBackground(Colors.BUTTON_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.getBackground() != Colors.BUTTON_SELECTED) {
                    button.setBackground(Colors.BUTTON_DEFAULT);
                }
            }
        });

        return button;
    }

    private void updateButtonSelection(JButton selectedButton, JButton[] buttons) {
        for (JButton button : buttons) {
            if (button == selectedButton) {
                button.setBackground(Colors.BUTTON_SELECTED);
                button.setFont(cooperHewittBoldFont);
            } else {
                button.setBackground(Colors.BUTTON_DEFAULT);
                button.setFont(cooperHewittRegularFont);
            }
        }
    }

    private JButton createContentButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Colors.BUTTON_DEFAULT);
        button.setForeground(Colors.TEXT_COLOR);
        button.setFont(montFont.deriveFont(12f));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        return button;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Colors.BORDER_COLOR);
        label.setFont(montFont);
        return label;
    }

    private void setupTextField(JTextField field, JLabel checkLabel) {
        field.setBackground(customMainColor);
        field.setForeground(Colors.TEXT_COLOR);
        field.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        field.setCaretColor(Colors.TEXT_COLOR);
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
                    checkLabel.setText("\u2714");
                    checkLabel.setForeground(Colors.TEXT_COLOR);
                    checkLabel.setFont(checkmarkFont);
                } else {
                    checkLabel.setText("");
                }
            }
        });
    }

    private void setupTextArea(JTextArea area, JLabel checkLabel) {
        area.setBackground(customMainColor);
        area.setForeground(Colors.TEXT_COLOR);
        area.setBorder(BorderFactory.createLineBorder(Colors.BORDER_COLOR));
        area.setCaretColor(Colors.TEXT_COLOR);
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
                    checkLabel.setForeground(Colors.TEXT_COLOR);
                    checkLabel.setFont(checkmarkFont);
                } else {
                    checkLabel.setText("");
                }
            }
        });
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void showPreview(String content) {
        JDialog previewDialog = new JDialog(frame, "Resume Preview", true);
        previewDialog.setSize(600, 400);
        previewDialog.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);
        textArea.setBackground(customMainColor);
        textArea.setForeground(Colors.TEXT_COLOR);
        textArea.setFont(montFont);
        JScrollPane scrollPane = new JScrollPane(textArea);

        previewDialog.add(scrollPane, BorderLayout.CENTER);
        previewDialog.setLocationRelativeTo(frame);
        previewDialog.setVisible(true);
    }

    public void setListModels(DefaultListModel<String> educationModel, DefaultListModel<String> workModel,
                              DefaultListModel<String> projectModel, DefaultListModel<String> skillsModel,
                              DefaultListModel<String> languagesModel) {
        educationList.setModel(educationModel);
        workList.setModel(workModel);
        projectList.setModel(projectModel);
        skillsList.setModel(skillsModel);
        languagesList.setModel(languagesModel);
    }

    public void setOnPersonalInfoSubmitted(Consumer<String[]> action) {
        this.onPersonalInfoSubmitted = action;
    }

    public void setOnEducationSubmitted(Consumer<String[]> action) {
        this.onEducationSubmitted = action;
    }

    public void setOnWorkExperienceSubmitted(Consumer<Object[]> action) {
        this.onWorkExperienceSubmitted = action;
    }

    public void setOnProjectSubmitted(Consumer<Object[]> action) {
        this.onProjectSubmitted = action;
    }

    public void setOnSkillSubmitted(Consumer<String> action) {
        this.onSkillSubmitted = action;
    }

    public void setOnRemoveSkill(Consumer<String> action) {
        this.onRemoveSkill = action;
    }

    public void setOnLanguageSubmitted(Consumer<String[]> action) {
        this.onLanguageSubmitted = action;
    }

    public void setOnRemoveLanguage(Consumer<String> action) {
        this.onRemoveLanguage = action;
    }

    public void setOnSectionOrderSubmitted(Consumer<List<String>> action) {
        this.onSectionOrderSubmitted = action;
    }

    public void setOnCustomizeColors(Runnable action) {
        this.onCustomizeColors = action;
    }

    public void setOnPreviewResume(Runnable action) {
        this.onPreviewResume = action;
    }

    public void setOnSaveResume(Runnable action) {
        this.onSaveResume = action;
    }
}

// Helper class for drag-and-drop in section order panel
class ListItemTransferHandler extends TransferHandler {
    private int index = -1;

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) return false;

        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        int dropIndex = dl.getIndex();
        JList<String> list = (JList<String>) support.getComponent();
        DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();

        try {
            String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
            if (index != -1) {
                model.remove(index);
                model.add(dropIndex, data);
                index = -1;
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    protected StringSelection createTransferable(JComponent c) {
        JList<String> list = (JList<String>) c;
        index = list.getSelectedIndex();
        String data = list.getSelectedValue();
        return new StringSelection(data);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }
}
