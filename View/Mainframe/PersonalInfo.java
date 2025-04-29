package View.Mainframe;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static View.Constants.Font.cooperHewittBoldFont;
import static View.Setup.*;
import static View.Setup.createLabel;
import Model.Resume;
import View.Constants.CustomColors;

public class PersonalInfo {
    public static JLabel photoLabel = new JLabel();;
    public static JPanel  personalPanel = new JPanel(new GridBagLayout());
    public static JButton uploadPhotoButton = createContentButton("Upload Photo");;
    public static JTextField nameField = new JTextField(20);
    public static JTextField titleField = new JTextField(20);
    public static JTextField emailField = new JTextField(20);
    public static JTextField phoneField = new JTextField(20);
    public static JTextField addressField = new JTextField(20);
    public static JTextArea profileField = new JTextArea(5, 20);

    static{
        personalPanel.setBackground(CustomColors.CONTENT_BG);
        photoLabel.setPreferredSize(new Dimension(150, 150));
        photoLabel.setBorder(BorderFactory.createLineBorder(CustomColors.BORDER_COLOR));

        JLabel personalHeader = new JLabel("PERSONAL INFO");
        personalHeader.setForeground(CustomColors.TEXT_COLOR);
        personalHeader.setFont(cooperHewittBoldFont.deriveFont(24f));

        profileField.setLineWrap(true);
        profileField.setWrapStyleWord(true);
        JScrollPane profileScrollPane = new JScrollPane(profileField);

        JLabel nameCheck = new JLabel("");
        JLabel titleCheck = new JLabel("");
        JLabel emailCheck = new JLabel("");
        JLabel phoneCheck = new JLabel("");
        JLabel addressCheck = new JLabel("");
        JLabel profileCheck = new JLabel("");

        setupTextField(nameField, nameCheck);
        setupTextField(titleField, titleCheck);
        setupTextField(emailField, emailCheck);
        setupTextField(phoneField, phoneCheck);
        setupTextField(addressField, addressCheck);
        setupTextArea(profileField, profileCheck);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        personalPanel.add(personalHeader, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        personalPanel.add(createLabel("Name"), gbc);
        gbc.gridx = 1;
        personalPanel.add(nameField, gbc);
        gbc.gridx = 2;
        personalPanel.add(nameCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        personalPanel.add(createLabel("Title"), gbc);
        gbc.gridx = 1;
        personalPanel.add(titleField, gbc);
        gbc.gridx = 2;
        personalPanel.add(titleCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        personalPanel.add(createLabel("Email"), gbc);
        gbc.gridx = 1;
        personalPanel.add(emailField, gbc);
        gbc.gridx = 2;
        personalPanel.add(emailCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        personalPanel.add(createLabel("Phone Number"), gbc);
        gbc.gridx = 1;
        personalPanel.add(phoneField, gbc);
        gbc.gridx = 2;
        personalPanel.add(phoneCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        personalPanel.add(createLabel("Address"), gbc);
        gbc.gridx = 1;
        personalPanel.add(addressField, gbc);
        gbc.gridx = 2;
        personalPanel.add(addressCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        personalPanel.add(createLabel("Profile"), gbc);
        gbc.gridx = 1;
        personalPanel.add(profileScrollPane, gbc);
        gbc.gridx = 2;
        personalPanel.add(profileCheck, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        personalPanel.add(createLabel("Photo"), gbc);
        gbc.gridx = 1;
        personalPanel.add(uploadPhotoButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        personalPanel.add(photoLabel, gbc);

    }

    public static void uploadPhoto(Frame frame, Resume resume) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(imageFilter);
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    resume.getPersonalInfo().setPhoto(scaledImage);
                    photoLabel.setIcon(new ImageIcon(scaledImage));
                    JOptionPane.showMessageDialog(personalPanel, "Photo uploaded successfully.");
                } else {
                    JOptionPane.showMessageDialog(personalPanel, "Invalid image file.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(personalPanel, "Error loading image: " + ex.getMessage());
            }
        }
    }
}
