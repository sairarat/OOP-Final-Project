package View.Mainframe;

import javax.swing.*;
import java.awt.*;

import static View.Constants.CustomColors.*;
import static View.Constants.Font.cooperHewittBoldFont;
import static View.Setup.createSidebarButton;
import View.Constants.CustomColors;

public class Sidebar {
    public static JPanel sidebarPanel = new JPanel();
    public static JPanel contentPanel = new JPanel(new CardLayout());
    public static JButton personalInfoButton = createSidebarButton("PERSONAL INFO");
    public static JButton educationButton = createSidebarButton("EDUCATION");
    public static JButton workExperienceButton = createSidebarButton("WORK EXPERIENCE");
    public static JButton certificationButton = createSidebarButton("CERTIFICATION");
    public static JButton skillsButton = createSidebarButton("SKILLS");
    public static JButton languagesButton = createSidebarButton("LANGUAGES");
    public static JButton customizeColorsButton = createSidebarButton("CUSTOMIZE COLORS");
    public static JButton previewButton = createSidebarButton("PREVIEW RESUME");
    public static JButton saveButton = createSidebarButton("SAVE RESUME");
    public static JButton[] sidebarButtons = {personalInfoButton, educationButton, workExperienceButton, certificationButton, skillsButton, languagesButton};
    public static JButton sidebarColorButton = new JButton("Choose Color");
    public static JButton mainColorButton = new JButton("Choose Color");
    public static JLabel sidebarColorLabel = new JLabel("Sidebar Color:");
    public static JLabel mainColorLabel = new JLabel("Main Content Color:");
    public static JButton applyButton = new JButton("Apply");
    public static GridBagConstraints colorGbc = new GridBagConstraints();
    public static JDialog colorDialog;

    static{
        // Modified sidebar panel with BoxLayout
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(CustomColors.SIDEBAR_BG);
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        sidebarPanel.setPreferredSize(new Dimension(250, sidebarPanel.getPreferredSize().height));
        contentPanel.setBackground(CustomColors.CONTENT_BG);

        saveButton.setFont(cooperHewittBoldFont);
        previewButton.setFont(cooperHewittBoldFont);
        customizeColorsButton.setFont(cooperHewittBoldFont);

        // Set alignment and spacing for buttons
        personalInfoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        educationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        workExperienceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        certificationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        sidebarPanel.add(certificationButton);
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
    }

    public static void initColorDialog(JFrame frame){

        colorGbc.gridx = 0;
        colorGbc.gridy = 0;
        colorDialog.add(sidebarColorLabel, colorGbc);
        colorGbc.gridx = 1;
        colorDialog.add(sidebarColorButton, colorGbc);
        colorGbc.gridx = 0;
        colorGbc.gridy = 1;
        colorDialog.add(mainColorLabel, colorGbc);
        colorGbc.gridx = 1;
        colorDialog.add(mainColorButton, colorGbc);
        colorGbc.gridx = 0;
        colorGbc.gridy = 2;
        colorGbc.gridwidth = 2;
        colorGbc.anchor = GridBagConstraints.CENTER;
        colorDialog.add(applyButton, colorGbc);

        colorDialog.setLocationRelativeTo(frame);
        colorDialog.setVisible(true);
    }

    public static void customizeColors(JFrame frame){
        colorDialog = new JDialog(frame);
        colorDialog.setSize(400, 300);
        colorDialog.setLayout(new GridBagLayout());
        colorGbc.insets = new Insets(10, 10, 10, 10);
        colorGbc.anchor = GridBagConstraints.WEST;

        sidebarColorButton.setBackground(customSidebarColor);
        mainColorButton.setBackground(customMainColor);
    }

    public static void mainColor(){
        Color newColor = JColorChooser.showDialog(colorDialog, "Choose Main Content Color", customMainColor);
        if (newColor != null) {
            customMainColor = newColor;
            mainColorButton.setBackground(newColor);
        }
    }

    public static void sidebarColor(){
        Color newColor = JColorChooser.showDialog(colorDialog, "Choose Sidebar Color", customSidebarColor);
        if (newColor != null) {
            customSidebarColor = newColor;
            sidebarColorButton.setBackground(newColor);
        }
    }

    public static void applyButton(){
        colorDialog.dispose();
    }
}
