import javax.swing.*;
import java.awt.*;
import java.awt.print.*;

import Model.*;
import View.*;

import static View.Generator.*;
import static View.Mainframe.Sidebar.*;

import static View.Setup.*;

public class MainUi {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Resume Builder by Group 6");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        Resume resume = new Resume(new PersonalInfo("", "", "", "", "", "", null));

        // Personal Info Panel
//      View.Mainframe.PersonalInfo personalInfo = new View.Mainframe.PersonalInfo();

        View.Mainframe.PersonalInfo.uploadPhotoListener(e -> {
            View.Mainframe.PersonalInfo.uploadPhoto(frame, resume);
        });
        contentPanel.add(View.Mainframe.PersonalInfo.personalPanel, "Personal Info");

        // Education Panel
        View.Mainframe.Education.addEducationListener(e -> {
            View.Mainframe.Education.addEducation(resume);
        });
        contentPanel.add(View.Mainframe.Education.educationPanel, "Education");

        // Work Experience Panel
        View.Mainframe.WorkExperience.addWorkListener(e -> {
            View.Mainframe.WorkExperience.addWork(resume);
        });
        contentPanel.add(View.Mainframe.WorkExperience.workPanel, "Work Experience");

        // Projects Panel
        View.Mainframe.Project.addProjectListener(e -> {
            View.Mainframe.Project.addProject(resume);
        });
        contentPanel.add(View.Mainframe.Project.projectPanel, "Projects");

        // Skills Panel
        View.Mainframe.Skills.addSkillListener(e -> {
            View.Mainframe.Skills.addSkill(resume);
        });

        View.Mainframe.Skills.removeSkillListener(e -> {
            View.Mainframe.Skills.removeSkill(resume);
        });
        contentPanel.add(View.Mainframe.Skills.skillsPanel, "Skills");

        // Languages Panel
        View.Mainframe.Language.addLanguageListener(e -> {
            View.Mainframe.Language.addLanguage(resume);
        });

        View.Mainframe.Language.removeLanguageListener(e -> {
            View.Mainframe.Language.removeLanguage(resume);
        });
        contentPanel.add(View.Mainframe.Language.languagesPanel, "Languages");

        // Customize Colors Action
        View.Mainframe.Sidebar.customizeColorsListener(e -> {
            View.Mainframe.Sidebar.customizeColors();

            // Sidebar
            View.Mainframe.Sidebar.sidebarColorListener(e1 -> View.Mainframe.Sidebar.sidebarColor());

            // Main Content
            View.Mainframe.Sidebar.mainColorListener(e1 -> View.Mainframe.Sidebar.mainColor());

            // Apply Button
            View.Mainframe.Sidebar.applyButtonListener(e1 -> View.Mainframe.Sidebar.applyButton());
            //applyButton.addActionListener(e1 -> colorDialog.dispose());

            initColorDialog(frame);
        });

        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        personalInfoButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Personal Info")  ;
            updateButtonSelection(personalInfoButton, sidebarButtons);
        });
        educationButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Education");
            updateButtonSelection(educationButton, sidebarButtons);
        });
        workExperienceButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Work Experience");
            updateButtonSelection(workExperienceButton, sidebarButtons);
        });
        projectsButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Projects");
            updateButtonSelection(projectsButton, sidebarButtons);
        });
        skillsButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Skills");
            updateButtonSelection(skillsButton, sidebarButtons);
        });
        languagesButton.addActionListener(e -> {
            cardLayout.show(contentPanel, "Languages");
            updateButtonSelection(languagesButton, sidebarButtons);
        });

        updateButtonSelection(personalInfoButton, sidebarButtons);

        // Preview Resume Action
        previewButton.addActionListener(e -> {
            String name = View.Mainframe.PersonalInfo.nameField.getText().trim();
            String title = View.Mainframe.PersonalInfo.titleField.getText().trim();
            String email = View.Mainframe.PersonalInfo.emailField.getText().trim();
            String phone = View.Mainframe.PersonalInfo.phoneField.getText().trim();
            String address = View.Mainframe.PersonalInfo.addressField.getText().trim();
            String profile = View.Mainframe.PersonalInfo.profileField.getText().trim();
            // Check if all personal info fields are filled
            if (name.isEmpty() || title.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || profile.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all personal info fields.");
                return;
            }
            resume.setPersonalInfo(new PersonalInfo(name, title, email, phone, address, profile, resume.getPersonalInfo().getPhoto()));

            if (resume.getEducations().isEmpty() && resume.getWorkExperiences().isEmpty() && resume.getProjects().isEmpty() && resume.getSkills().isEmpty() && resume.getLanguages().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please add at least one education, work experience, project, skill, or language.");
                return;
            }

            ResumePreview resumePreview = new ResumePreview();
            resumePreview.previewShow(frame, resume);
        });

        // Save Resume Action
        saveButton.addActionListener(e -> {
            String name = View.Mainframe.PersonalInfo.nameField.getText().trim();
            String title = View.Mainframe.PersonalInfo.titleField.getText().trim();
            String email = View.Mainframe.PersonalInfo.emailField.getText().trim();
            String phone = View.Mainframe.PersonalInfo.phoneField.getText().trim();
            String address = View.Mainframe.PersonalInfo.addressField.getText().trim();
            String profile = View.Mainframe.PersonalInfo.profileField.getText().trim();

            if (name.isEmpty() || title.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || profile.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all personal info fields.");
                return;
            }
            resume.setPersonalInfo(new PersonalInfo(name, title, email, phone, address, profile, resume.getPersonalInfo().getPhoto()));

            if (resume.getEducations().isEmpty() && resume.getWorkExperiences().isEmpty() && resume.getProjects().isEmpty() && resume.getSkills().isEmpty() && resume.getLanguages().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please add at least one education, work experience, project, skill, or language.");
                return;
            }

            JOptionPane.showMessageDialog(frame, "You will now be prompted to print your resume.\nEnsure the option \"Print to PDF\" is selected.");

            try {
                printToPdf(resume, "resume.pdf", frame);
                JOptionPane.showMessageDialog(frame, "Resume saved successfully!");
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving resume: " + ex.getMessage());
            }
//
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setSelectedFile(new File("resume.pdf"));
//            FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
//            FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF Files (*.pdf)", "pdf");
//            fileChooser.addChoosableFileFilter(txtFilter);
//            fileChooser.addChoosableFileFilter(pdfFilter);
//            fileChooser.setFileFilter(pdfFilter);
//
//            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
//                File selectedFile = fileChooser.getSelectedFile();
//                String fileName = selectedFile.getAbsolutePath();
//
//                if (fileChooser.getFileFilter() == txtFilter) {
//                    if (!fileName.toLowerCase().endsWith(".txt")) {
//                        fileName += ".txt";
//                        selectedFile = new File(fileName);
//                    }
//                } else {
//                    if (!fileName.toLowerCase().endsWith(".pdf")) {
//                        fileName += ".pdf";
//                        selectedFile = new File(fileName);
//                    }
//                }
//
//                if (selectedFile.exists()) {
//                    int result = JOptionPane.showConfirmDialog(frame,
//                            "File already exists. Do you want to overwrite it?",
//                            "Confirm Overwrite",
//                            JOptionPane.YES_NO_OPTION);
//                    if (result != JOptionPane.YES_OPTION) {
//                        return;
//                    }
//                }
//
//                try {
//                    if (fileName.toLowerCase().endsWith(".txt")) {
//                        generateTextFile(resume, fileName);
//                        JOptionPane.showMessageDialog(frame, "Resume saved as " + fileName);
//                    } else {
//                        printToPdf(resume, fileName, frame);
//                        JOptionPane.showMessageDialog(frame, "Resume printed to PDF: " + fileName + "\nEnsure a PDF printer is selected.");
//                    }
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(frame, "Error saving resume: " + ex.getMessage());
//                }
//            }
        });

        frame.add(sidebarPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}