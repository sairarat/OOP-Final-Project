package View.Mainframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static View.Constants.Font.cooperHewittBoldFont;
import static View.Constants.Font.montFont;
import static View.Setup.*;
import static View.Setup.createLabel;
import Model.Resume;
import View.Constants.CustomColors;

public class Education {
    public static JPanel educationPanel = new JPanel(new GridBagLayout());
    public static JTextField institutionField = new JTextField(20);
    public static JTextField degreeField= new JTextField(20);
    public static JTextField educationDurationField= new JTextField(20);
    public static DefaultListModel<String> educationListModel = new DefaultListModel<>();
    public static JList<String> educationList = new JList<>(educationListModel);
    public static JButton addEducationButton = createContentButton("Add Educational Background");
    static {
        //Education Panel
        //Model.Education education = new Model.Education("", "", "");
        educationPanel.setBackground(CustomColors.CONTENT_BG);

        JLabel educationHeader = new JLabel("EDUCATION");
        educationHeader.setForeground(CustomColors.TEXT_COLOR);
        educationHeader.setFont(cooperHewittBoldFont.deriveFont(24f));

        educationList.setBackground(CustomColors.CONTENT_BG);
        educationList.setForeground(CustomColors.TEXT_COLOR);
        educationList.setFont(montFont);

        JScrollPane educationScrollPane = new JScrollPane(educationList);
        GridBagConstraints gbc;

        JLabel institutionCheck = new JLabel("");
        JLabel degreeCheck = new JLabel("");
        JLabel durationCheck = new JLabel("");

        setupTextField(institutionField, institutionCheck);
        setupTextField(degreeField, degreeCheck);
        setupTextField(educationDurationField, durationCheck);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        educationPanel.add(educationHeader, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        educationPanel.add(createLabel("Institution"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        educationPanel.add(institutionField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        educationPanel.add(institutionCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        educationPanel.add(createLabel("Degree"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        educationPanel.add(degreeField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        educationPanel.add(degreeCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        educationPanel.add(createLabel("Duration"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        educationPanel.add(educationDurationField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        educationPanel.add(durationCheck, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        educationPanel.add(addEducationButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        educationPanel.add(educationScrollPane, gbc);
    }

    public static void addEducation(Resume resume){
        String institution = institutionField.getText().trim();
        String degree = degreeField.getText().trim();
        String duration = educationDurationField.getText().trim();
        if (!institution.isEmpty() && !degree.isEmpty() && !duration.isEmpty()) {
            Model.Education edu = new Model.Education(institution, degree, duration);
            resume.addEducation(edu);
            educationListModel.addElement(institution + " - " + degree + " (" + duration + ")");
            institutionField.setText("");
            degreeField.setText("");
            educationDurationField.setText("");
            JOptionPane.showMessageDialog(educationPanel, "Education added successfully.");
        } else {
            JOptionPane.showMessageDialog(educationPanel, "Please fill all fields.");
        }
    }

    public static void addEducationListener(ActionListener actionListener){
        addEducationButton.addActionListener(actionListener);
    }
}


