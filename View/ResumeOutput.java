package View;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.PrintWriter;

import Model.*;
import static View.Constants.Font.*;
import static View.Constants.CustomColors.*;
import static View.Setup.*;

public class ResumeOutput {
    public static void previewShow(JFrame parent, Resume resume){
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

                // Certification Section
                if (!resume.getCertifications().isEmpty()) {
                    g2d.setFont(cooperHewittBoldFont.deriveFont(14f));
                    g2d.drawString("CERTIFICATIONS", mainX, mainY);
                    mainY += lineHeight;
                    g2d.setFont(montFont.deriveFont(12f));
                    for (Certification cert : resume.getCertifications()) {
                        g2d.drawString(cert.getTitle(), mainX, mainY);
                        mainY += lineHeight;

                        g2d.drawString(cert.getOrganization(), mainX, mainY);
                        mainY += lineHeight;

                        if(!cert.getDateExpired().isEmpty()){
                            g2d.drawString(cert.getDateReceived() + " - " + cert.getDateExpired(), mainX, mainY);
                        }
                        else{
                            g2d.drawString(cert.getDateReceived(), mainX, mainY);
                        }
                        mainY += lineHeight;

                        String[] descriptionLines = wrapText(cert.getDescription(), g2d, mainWidth);
                        for (String line : descriptionLines) {
                            g2d.drawString(line, mainX, mainY);
                            mainY += lineHeight;
                        }
//                        g2d.drawString(cert.getDescription(), mainX, mainY);
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
        JButton closeButton = new JButton("Close");
        buttonPanel.add(closeButton);
        previewDialog.add(buttonPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(e -> previewDialog.dispose());

        previewDialog.setLocationRelativeTo(parent);
        previewDialog.setVisible(true);

    }

    public static boolean previewCheck(JFrame frame, Resume resume){
        String name = View.Mainframe.PersonalInfo.nameField.getText().trim();
        String title = View.Mainframe.PersonalInfo.titleField.getText().trim();
        String email = View.Mainframe.PersonalInfo.emailField.getText().trim();
        String phone = View.Mainframe.PersonalInfo.phoneField.getText().trim();
        String address = View.Mainframe.PersonalInfo.addressField.getText().trim();
        String profile = View.Mainframe.PersonalInfo.profileField.getText().trim();
        // Check if all personal info fields are filled
        if (name.isEmpty() || title.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || profile.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all personal info fields.");
            return false;
        }
        resume.setPersonalInfo(new PersonalInfo(name, title, email, phone, address, profile, resume.getPersonalInfo().getPhoto()));

        if (resume.getEducations().isEmpty() && resume.getWorkExperiences().isEmpty() && resume.getCertifications().isEmpty() && resume.getSkills().isEmpty() && resume.getLanguages().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please add at least one education, work experience, certification, skill, or language.");
            return false;
        }
        return true;
    }

    public static void generateTextFile(Resume resume, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println(resume.getPersonalInfo().getName().toUpperCase());
            writer.println(resume.getPersonalInfo().getTitle());
            writer.println();
            writer.println("PROFILE");
            writer.println(resume.getPersonalInfo().getProfile());
            writer.println();
            writer.println("CONTACT");
            writer.println(resume.getPersonalInfo().getEmail());
            writer.println(resume.getPersonalInfo().getPhone());
            writer.println(resume.getPersonalInfo().getAddress());
            writer.println();

            if (!resume.getEducations().isEmpty()) {
                writer.println("EDUCATION");
                for (Education edu : resume.getEducations()) {
                    writer.println(edu.getDuration());
                    writer.println(edu.getInstitution());
                    writer.println(edu.getDegree());
                    writer.println();
                }
            }

            if (!resume.getWorkExperiences().isEmpty()) {
                writer.println("WORK EXPERIENCE");
                for (WorkExperience exp : resume.getWorkExperiences()) {
                    writer.println(exp.getDuration());
                    writer.println(exp.getCompany() + " - " + exp.getRole());
                    for (String achievement : exp.getAchievements()) {
                        writer.println("  * " + achievement);
                    }
                    writer.println();
                }
            }

            if (!resume.getCertifications().isEmpty()) {
                writer.println("CERTIFICATION");
                for (Certification certification : resume.getCertifications()) {
                    writer.println(certification.getTitle());
                    writer.println(certification.getOrganization());
                    if(!certification.getDateExpired().isEmpty()){
                        writer.println(certification.getDateReceived() + " - " + certification.getDateExpired());
                    }
                    else{
                        writer.println(certification.getDateReceived());
                    }
                    writer.println(certification.getDescription());
                    writer.println();
                    writer.println();
                }
            }

            if (!resume.getSkills().isEmpty()) {
                writer.println("SKILLS");
                for (Skill skill : resume.getSkills()) {
                    writer.println("  * " + skill.getName());
                }
                writer.println();
            }

            if (!resume.getLanguages().isEmpty()) {
                writer.println("LANGUAGES");
                for (Language lang : resume.getLanguages()) {
                    writer.println("  * " + lang.getName() + " (" + lang.getProficiency() + ")");
                }
                writer.println();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generating text file: " + e.getMessage(), e);
        }
    }

    public static void printToPdf(Resume resume, String fileName, JFrame frame) throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                double width = pageFormat.getImageableWidth();
                double height = pageFormat.getImageableHeight();

                int sidebarWidth = (int) (width * 0.3);
                int mainWidth = (int) (width - sidebarWidth);
                int margin = 20;
                int lineHeight = 20;
                int indent = 10;

                // Draw sidebar background
                g2d.setColor(customSidebarColor);
                g2d.fillRect(0, 0, sidebarWidth, (int) height);

                // Draw main content background
                g2d.setColor(customMainColor);
                g2d.fillRect(sidebarWidth, 0, mainWidth, (int) height);

                // Draw watermark
                g2d.setFont(cooperHewittBoldFont.deriveFont(50f));
                g2d.setColor(new Color(150, 150, 150, 100));
                String watermark = "Resume Builder";
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(watermark);
                int textHeight = fm.getAscent();
                g2d.rotate(Math.toRadians(-45), width / 2, height / 2);
                g2d.drawString(watermark, (float) (width - textWidth) / 2, (float) (height + textHeight) / 2);
                g2d.rotate(Math.toRadians(45), width / 2, height / 2);

                // Sidebar content
                int sidebarY = margin;
                int sidebarX = margin;

                if (resume.getPersonalInfo().getPhoto() != null) {
                    g2d.drawImage(resume.getPersonalInfo().getPhoto(), sidebarX, sidebarY, null);
                    sidebarY += 170;
                }

                // Profile Section
                g2d.setFont(cooperHewittBoldFont.deriveFont(14f));
                g2d.setColor(Color.BLACK);
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
                int mainY = margin;

                mainY += lineHeight;
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

                // Certification Section
                if (!resume.getCertifications().isEmpty()) {
                    g2d.setFont(cooperHewittBoldFont.deriveFont(14f));
                    g2d.drawString("CERTIFICATIONS", mainX, mainY);
                    mainY += lineHeight;
                    g2d.setFont(montFont.deriveFont(12f));
                    for (Certification cert : resume.getCertifications()) {
                        g2d.drawString(cert.getTitle(), mainX, mainY);
                        mainY += lineHeight;

                        g2d.drawString(cert.getOrganization(), mainX, mainY);
                        mainY += lineHeight;

                        if(!cert.getDateExpired().isEmpty()){
                            g2d.drawString(cert.getDateReceived() + " - " + cert.getDateExpired(), mainX, mainY);
                        }
                        else{
                            g2d.drawString(cert.getDateReceived(), mainX, mainY);
                        }
                        mainY += lineHeight;

                        String[] descriptionLines = wrapText(cert.getDescription(), g2d, mainWidth);
                        for (String line : descriptionLines) {
                            g2d.drawString(line, mainX, mainY);
                            mainY += lineHeight;
                        }
//                        g2d.drawString(cert.getDescription(), mainX, mainY);
                        mainY += lineHeight;
                    }
                }
                return Printable.PAGE_EXISTS;
            }
        });
        job.setJobName(fileName);

        if (job.printDialog()) {
            job.print();
        } else {
            JOptionPane.showMessageDialog(frame, "PDF printing canceled. Saving as text instead.");
            generateTextFile(resume, fileName.replace(".pdf", ".txt"));
        }
    }
}
