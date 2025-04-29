package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;

import static View.Mainframe.Sidebar.*;
import static View.Setup.updateButtonSelection;
import static View.Mainframe.PersonalInfo.*;
import static View.Mainframe.Education.*;
import static View.Mainframe.WorkExperience.*;
import static View.Mainframe.Project.*;
import static View.Mainframe.Skills.*;
import static View.Mainframe.Language.*;
import static View.ResumeOutput.*;
import Model.PersonalInfo;
import Model.Resume;


public class Controller {

    public static void createAndShowGUI() {
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
        uploadPhotoButton.addActionListener(e -> uploadPhoto(frame, resume));
        contentPanel.add(personalPanel, "Personal Info");

        // Education Panel
        addEducationButton.addActionListener(e -> addEducation(resume));
        contentPanel.add(educationPanel, "Education");

        // Work Experience Panel
        addWorkButton.addActionListener(e -> addWork(resume));
        contentPanel.add(workPanel, "Work Experience");

        // Projects Panel
        addProjectButton.addActionListener(e -> addProject(resume));
        contentPanel.add(projectPanel, "Projects");

        // Skills Panel
        addSkillButton.addActionListener(e -> addSkill(resume));
        removeSkillButton.addActionListener(e -> removeSkill(resume));
        contentPanel.add(skillsPanel, "Skills");

        // Languages Panel
        addLanguageButton.addActionListener(e -> addLanguage(resume));
        removeLanguageButton.addActionListener(e -> removeLanguage(resume));
        contentPanel.add(languagesPanel, "Languages");

        // Customize Colors Action
        customizeColorsButton.addActionListener(e -> {
            customizeColors(frame);

            // Sidebar
            sidebarColorButton.addActionListener(e1 -> sidebarColor());

            // Main Content
            mainColorButton.addActionListener(e1 -> mainColor());

            // Apply Button
            applyButton.addActionListener(e1 -> applyButton());
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
            if(previewCheck(frame, resume)){
                previewShow(frame, resume);
            }
        });

        // Save Resume Action
        saveButton.addActionListener(e -> {
            if(previewCheck(frame, resume)){
                JOptionPane.showMessageDialog(frame, "You will now be prompted to print your resume.\nEnsure the option \"Print to PDF\" is selected.");
                try {
                    View.ResumeOutput.printToPdf(resume, "resume.pdf", frame);
                    JOptionPane.showMessageDialog(frame, "Resume saved successfully!");
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving resume: " + ex.getMessage());
                }
            }
        });

        // Set up the sidebar and content panels
        frame.add(sidebarPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

}
