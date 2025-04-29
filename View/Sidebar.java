package View;

import javax.swing.*;
import java.awt.*;

import View.Constants.CustomColors;

import static View.Setup.*;

import static View.Constants.Font.cooperHewittBoldFont;

public class Sidebar {
    Sidebar(){
        // Modified sidebar panel with BoxLayout
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(CustomColors.SIDEBAR_BG);
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        sidebarPanel.setPreferredSize(new Dimension(250, sidebarPanel.getPreferredSize().height));

        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(CustomColors.CONTENT_BG);

        JButton personalInfoButton = createSidebarButton("PERSONAL INFO");
        JButton educationButton = createSidebarButton("EDUCATION");
        JButton workExperienceButton = createSidebarButton("WORK EXPERIENCE");
        JButton projectsButton = createSidebarButton("PROJECTS");
        JButton skillsButton = createSidebarButton("SKILLS");
        JButton languagesButton = createSidebarButton("LANGUAGES");
        JButton customizeColorsButton = createSidebarButton("CUSTOMIZE COLORS");
        JButton previewButton = createSidebarButton("PREVIEW RESUME");
        JButton saveButton = createSidebarButton("SAVE RESUME");

        saveButton.setFont(cooperHewittBoldFont);
        previewButton.setFont(cooperHewittBoldFont);
        customizeColorsButton.setFont(cooperHewittBoldFont);

        // Set alignment and spacing for buttons
        personalInfoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        educationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        workExperienceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        projectsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        skillsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        languagesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customizeColorsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        previewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add buttons with spacing
        sidebarPanel.add(personalInfoButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(educationButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(workExperienceButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(projectsButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(skillsButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(languagesButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(customizeColorsButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(previewButton);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(saveButton);

        JButton[] sidebarButtons = {personalInfoButton, educationButton, workExperienceButton, projectsButton, skillsButton, languagesButton};

    }
}
