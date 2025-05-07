package View.Mainframe;

import View.Constants.CustomColors;
import javax.swing.*;
import java.awt.*;

import static View.Constants.Font.cooperHewittBoldFont;
import static View.Constants.Font.montFont;
import static View.Setup.*;
import Model.Resume;

public class Certification {
    public static JTextField titleField = new JTextField(20);
    public static JTextField organizationField = new JTextField(20);
    public static JTextField dateReceivedField = new JTextField(20);
    public static JTextField dateExpiredField = new JTextField(20);
    public static JTextArea descriptionField = new JTextArea(5, 20);
    public static JButton addCertButton = createContentButton("Add Certificate");
    public static JPanel certPanel = new JPanel(new GridBagLayout());
    public static DefaultListModel<String> certListModel = new DefaultListModel<>();
    public static JList<String> certList = new JList<>(certListModel);

    static {
        // Certification Panel
        certPanel.setBackground(CustomColors.CONTENT_BG);
        JLabel certHeader = new JLabel("CERTIFICATIONS");
        certHeader.setForeground(CustomColors.TEXT_COLOR);
        certHeader.setFont(cooperHewittBoldFont.deriveFont(24f));

        certList.setBackground(CustomColors.CONTENT_BG);
        certList.setForeground(CustomColors.TEXT_COLOR);
        certList.setFont(montFont);

        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);

        JScrollPane certificationScrollPane = new JScrollPane(certList);

        JLabel certTitleCheck = new JLabel("");
        JLabel organizationCheck = new JLabel("");
        JLabel dateReceivedCheck = new JLabel("");
        JLabel dateExpiredCheck = new JLabel("");
        JLabel descriptionCheck = new JLabel("");

        setupTextField(titleField, certTitleCheck);
        setupTextField(organizationField, organizationCheck);
        setupTextField(dateReceivedField, dateReceivedCheck);
        setupTextField(dateExpiredField, dateExpiredCheck);
        setupTextArea(descriptionField, descriptionCheck);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        certPanel.add(certHeader, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        certPanel.add(createLabel("Certification Title"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        certPanel.add(titleField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        certPanel.add(certTitleCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        certPanel.add(createLabel("Organization"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        certPanel.add(organizationField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        certPanel.add(organizationCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        certPanel.add(createLabel("Date Received"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        certPanel.add(dateReceivedField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        certPanel.add(dateReceivedCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        certPanel.add(createLabel("Date Expired"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        certPanel.add(dateExpiredField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 4;
        certPanel.add(dateExpiredCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        certPanel.add(createLabel("Description"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        certPanel.add(descriptionField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 5;
        certPanel.add(descriptionCheck, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        certPanel.add(addCertButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        certPanel.add(certificationScrollPane, gbc);

    }

    public static void addCertification(Resume resume){
        String title = titleField.getText().trim();
        String organization = organizationField.getText().trim();
        String dateReceived = dateReceivedField.getText().trim();
        String dateExpired = dateExpiredField.getText().trim();
        String description = descriptionField.getText().trim();

        if (!title.isEmpty() && !organization.isEmpty() && !dateReceived.isEmpty()) {
            Model.Certification certification = new Model.Certification(title, organization, dateReceived, dateExpired, description);
            resume.addCertification(certification);
            if(!dateExpired.isEmpty()) {
                certListModel.addElement(title + " - " + organization + " (" + dateReceived + " - " + dateExpired + ")");
            }
            else{
                certListModel.addElement(title + " - " + organization + " (" + dateReceived + ")");
            }
            titleField.setText("");
            organizationField.setText("");
            dateReceivedField.setText("");
            dateExpiredField.setText("");
            descriptionField.setText("");
            JOptionPane.showMessageDialog(certPanel, "Certification added successfully.");
        } else {
            JOptionPane.showMessageDialog(certPanel, "Please fill all fields.");
        }
    }
}
