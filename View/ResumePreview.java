package View;

import javax.swing.*;
import java.awt.*;

import Model.*;

import static View.Constants.Font.*;
import static View.Constants.CustomColors.*;
import static View.Setup.*;

public class ResumePreview {
    public void previewShow(JFrame parent, Resume resume){
        JDialog previewDialog = new JDialog(parent, "Resume Preview", true);
        previewDialog.setSize(700, 900);
        previewDialog.setLayout(new BorderLayout());

        JPanel previewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int width = getWidth();
                int sidebarWidth = (int) (width * 0.3);
                int mainWidth = width - sidebarWidth;
                int margin = 20;
                int lineHeight = 20;
                int indent = 10;

                // Draw sidebar background
                g2d.setColor(customSidebarColor);
                g2d.fillRect(0, 0, sidebarWidth, getHeight());

                // Draw main content background
                g2d.setColor(customMainColor);
                g2d.fillRect(sidebarWidth, 0, mainWidth, getHeight());

                // Sidebar content
                int sidebarY = margin;
                int sidebarX = margin;

                if (resume.getPersonalInfo().getPhoto() != null) {
                    g2d.drawImage(resume.getPersonalInfo().getPhoto(), sidebarX, sidebarY, this);
                    sidebarY += 170; // Photo height + spacing
                }

                // Profile Section
                g2d.setFont(cooperHewittBoldFont.deriveFont(14f));
                g2d.setColor(new java.awt.Color(0, 0, 0));
                g2d.drawString("PROFILE", sidebarX, sidebarY);
                sidebarY += lineHeight;
                g2d.setFont(montFont.deriveFont(12f));
                String[] profileLines = wrapText(resume.getPersonalInfo().getProfile(), g2d, sidebarWidth - 2 * margin);
                for (String line : profileLines) {
                    g2d.drawString(line, sidebarX, sidebarY);
                    sidebarY += lineHeight;
                }
                sidebarY += lineHeight;

                // Contact Section
                g2d.setFont(cooperHewittBoldFont.deriveFont(14f));
                g2d.drawString("CONTACT", sidebarX, sidebarY);
                sidebarY += lineHeight;
                g2d.setFont(montFont.deriveFont(12f));
                g2d.drawString(resume.getPersonalInfo().getEmail(), sidebarX, sidebarY);
                sidebarY += lineHeight;
                g2d.drawString(resume.getPersonalInfo().getPhone(), sidebarX, sidebarY);
                sidebarY += lineHeight;
                g2d.drawString(resume.getPersonalInfo().getAddress(), sidebarX, sidebarY);
                sidebarY += lineHeight * 2;

                // Education Section
                if (!resume.getEducations().isEmpty()) {
                    g2d.setFont(cooperHewittBoldFont.deriveFont(14f));
                    g2d.drawString("EDUCATION", sidebarX, sidebarY);
                    sidebarY += lineHeight;
                    g2d.setFont(montFont.deriveFont(12f));
                    for (Education edu : resume.getEducations()) {
                        g2d.drawString(edu.getDuration(), sidebarX, sidebarY);
                        sidebarY += lineHeight;
                        g2d.drawString(edu.getInstitution(), sidebarX, sidebarY);
                        sidebarY += lineHeight;
                        g2d.drawString(edu.getDegree(), sidebarX, sidebarY);
                        sidebarY += lineHeight * 2;
                    }
                }

                // Skills Section
                if (!resume.getSkills().isEmpty()) {
                    g2d.setFont(cooperHewittBoldFont.deriveFont(14f));
                    g2d.drawString("SKILLS", sidebarX, sidebarY);
                    sidebarY += lineHeight;
                    g2d.setFont(montFont.deriveFont(12f));
                    for (Skill skill : resume.getSkills()) {
                        g2d.drawString(skill.getName(), sidebarX + indent, sidebarY);
                        sidebarY += lineHeight;
                    }
                    sidebarY += lineHeight;
                }

                // Languages Section
                if (!resume.getLanguages().isEmpty()) {
                    g2d.setFont(cooperHewittBoldFont.deriveFont(14f));
                    g2d.drawString("LANGUAGES", sidebarX, sidebarY);
                    sidebarY += lineHeight;
                    g2d.setFont(montFont.deriveFont(12f));
                    for (Language lang : resume.getLanguages()) {
                        g2d.drawString(lang.getName() + " - " + lang.getProficiency(), sidebarX + indent, sidebarY);
                        sidebarY += lineHeight;
                    }
                    sidebarY += lineHeight;
                }

                // Main content
                int mainX = sidebarWidth + margin;
                int mainY = margin + 20;

                g2d.setFont(cooperHewittBoldFont.deriveFont(24f));
                g2d.drawString(resume.getPersonalInfo().getName().toUpperCase(), mainX, mainY);
                mainY += lineHeight + 10;
                g2d.setFont(cooperHewittRegularFont.deriveFont(16f));
                g2d.drawString(resume.getPersonalInfo().getTitle(), mainX, mainY);
                mainY += lineHeight * 2;

                // Work Experience Section
                if (!resume.getWorkExperiences().isEmpty()) {
                    g2d.setFont(cooperHewittBoldFont.deriveFont(14f));
                    g2d.drawString("WORK EXPERIENCE", mainX, mainY);
                    mainY += lineHeight;
                    g2d.setFont(montFont.deriveFont(12f));
                    for (WorkExperience exp : resume.getWorkExperiences()) {
                        g2d.drawString(exp.getDuration(), mainX, mainY);
                        mainY += lineHeight;
                        g2d.drawString(exp.getCompany() + " - " + exp.getRole(), mainX, mainY);
                        mainY += lineHeight;
                        for (String achievement : exp.getAchievements()) {
                            g2d.drawString("  * " + achievement, mainX + indent, mainY);
                            mainY += lineHeight;
                        }
                        mainY += lineHeight;
                    }
                }

                // Projects Section
                if (!resume.getProjects().isEmpty()) {
                    g2d.setFont(cooperHewittBoldFont.deriveFont(14f));
                    g2d.drawString("PROJECTS", mainX, mainY);
                    mainY += lineHeight;
                    g2d.setFont(montFont.deriveFont(12f));
                    for (Project project : resume.getProjects()) {
                        g2d.drawString(project.getName(), mainX, mainY);
                        mainY += lineHeight;
                        g2d.drawString(project.getDescription(), mainX, mainY);
                        mainY += lineHeight;
                        for (String achievement : project.getAchievements()) {
                            g2d.drawString("  * " + achievement, mainX + indent, mainY);
                            mainY += lineHeight;
                        }
                        mainY += lineHeight;
                    }
                }

                // Set preferred size for scrolling
                setPreferredSize(new Dimension(width, Math.max(sidebarY, mainY) + margin));
            }
        };
        previewPanel.setBackground(new java.awt.Color(243, 243, 243));

        JScrollPane scrollPane = new JScrollPane(previewPanel);
        previewDialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        previewDialog.add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            previewDialog.dispose();
            saveButton.doClick();
        });

        cancelButton.addActionListener(e -> previewDialog.dispose());

        previewDialog.setLocationRelativeTo(parent);
        previewDialog.setVisible(true);

    }

    public void previewCheck(){

    }

}
