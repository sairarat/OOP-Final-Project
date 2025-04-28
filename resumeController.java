import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class resumeController {
    private resumeModel.Resume resume;
    private resumeView view;
    private DefaultListModel<String> educationListModel;
    private DefaultListModel<String> workListModel;
    private DefaultListModel<String> projectListModel;
    private DefaultListModel<String> skillsListModel;
    private DefaultListModel<String> languagesListModel;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{1,4}?[-.\\s]?\\d{1,3}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}$");

    public resumeController() {
        resume = new resumeModel.Resume();
        educationListModel = new DefaultListModel<>();
        workListModel = new DefaultListModel<>();
        projectListModel = new DefaultListModel<>();
        skillsListModel = new DefaultListModel<>();
        languagesListModel = new DefaultListModel<>();
        view = new resumeView(this);

        view.setOnPersonalInfoSubmitted(personalInfo -> {
            String name = personalInfo[0];
            String email = personalInfo[1];
            String phone = personalInfo[2];
            String address = personalInfo[3];
            String photoPath = personalInfo[4];

            if (validatePersonalInfo(name, email, phone)) {
                resume.setPersonalInfo(new resumeModel.PersonalInfo(name, email, phone, address, photoPath));
                view.showMessage("Personal info saved!");
            } else {
                view.showMessage("Please provide a valid name, email, and phone number.");
            }
        });

        view.setOnEducationSubmitted(educationInfo -> {
            String institution = educationInfo[0];
            String degree = educationInfo[1];
            String duration = educationInfo[2];

            if (validateFields(institution, degree, duration)) {
                resume.addEducation(new resumeModel.Education(institution, degree, duration));
                educationListModel.addElement(institution + " - " + degree + " (" + duration + ")");
                view.showMessage("Education added!");
            } else {
                view.showMessage("Please fill all education fields.");
            }
        });


        view.setOnWorkExperienceSubmitted(workInfo -> {
            String company = (String) workInfo[0];
            String role = (String) workInfo[1];
            String duration = (String) workInfo[2];
            List<String> achievements = (List<String>) workInfo[3];

            if (validateFields(company, role, duration)) {
                resumeModel.WorkExperience work = new resumeModel.WorkExperience(company, role, duration);
                achievements.forEach(work::addAchievement);
                resume.addWorkExperience(work);
                workListModel.addElement(company + " - " + role + " (" + duration + ")");
                view.showMessage("Work experience added!");
            } else {
                view.showMessage("Please fill all work experience fields.");
            }
        });


        view.setOnProjectSubmitted(projectInfo -> {
            String name = (String) projectInfo[0];
            String description = (String) projectInfo[1];
            List<String> achievements = (List<String>) projectInfo[2];

            if (validateFields(name, description)) {
                resumeModel.Project project = new resumeModel.Project(name, description);
                achievements.forEach(project::addAchievement);
                resume.addProject(project);
                projectListModel.addElement(name + " - " + description);
                view.showMessage("Project added!");
            } else {
                view.showMessage("Please fill all project fields.");
            }
        });


        view.setOnSkillSubmitted(skill -> {
            if (!skill.trim().isEmpty()) {
                resume.addSkill(new resumeModel.Skill(skill));
                skillsListModel.addElement(skill);
                view.showMessage("Skill added!");
            } else {
                view.showMessage("Please enter a skill.");
            }
        });

        view.setOnRemoveSkill(skill -> {
            resume.removeSkill(skill);
            skillsListModel.removeElement(skill);
            view.showMessage("Skill removed!");
        });

        view.setOnLanguageSubmitted(languageInfo -> {
            String language = languageInfo[0];
            String proficiency = languageInfo[1];

            if (!language.trim().isEmpty()) {
                resume.addLanguage(new resumeModel.Language(language, proficiency));
                languagesListModel.addElement(language + " (" + proficiency + ")");
                view.showMessage("Language added!");
            } else {
                view.showMessage("Please enter a language.");
            }
        });


        view.setOnRemoveLanguage(language -> {
            resume.removeLanguage(language);
            for (int i = 0; i < languagesListModel.size(); i++) {
                if (languagesListModel.get(i).startsWith(language + " (")) {
                    languagesListModel.remove(i);
                    break;
                }
            }
            view.showMessage("Language removed!");
        });

        view.setOnSectionOrderSubmitted(newOrder -> {
            resume.reorderSection(newOrder);
            view.showMessage("Section order updated!");
        });

        view.setOnPreviewResume(() -> previewResume());
        view.setOnSaveResume(() -> saveResumeToTextFile("resume.txt"));

        view.setListModels(educationListModel, workListModel, projectListModel, skillsListModel, languagesListModel);
    }


    private boolean validatePersonalInfo(String name, String email, String phone) {
        return !name.trim().isEmpty() &&
                EMAIL_PATTERN.matcher(email.trim()).matches() &&
                PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    private boolean validateFields(String... fields) {
        for (String field : fields) {
            if (field.trim().isEmpty()) return false;
        }
        return true;
    }

    public void saveResumeToTextFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(resume.personalInfo.name.toUpperCase() + "\n");
            writer.write(resume.personalInfo.email + "\n");
            writer.write(resume.personalInfo.phone + "\n");
            writer.write(resume.personalInfo.address + "\n");
            if (!resume.personalInfo.photoPath.isEmpty()) {
                writer.write("Photo: " + resume.personalInfo.photoPath + "\n");
            }
            writer.write("\n");

            for (String section : resume.sectionOrder) {
                if (section.equals("Education")) {
                    writer.write("EDUCATION\n");
                    for (resumeModel.Education edu : resume.educations) {
                        writer.write(edu.institution + " - " + edu.degree + " (" + edu.duration + ")\n");
                    }
                    writer.write("\n");
                } else if (section.equals("Work Experience")) {
                    writer.write("WORK EXPERIENCE\n");
                    for (resumeModel.WorkExperience work : resume.workExperiences) {
                        writer.write(work.company + " - " + work.role + " (" + work.duration + ")\n");
                        for (String achievement : work.achievements) {
                            writer.write("  * " + achievement + "\n");
                        }
                        writer.write("\n");
                    }
                } else if (section.equals("Projects")) {
                    writer.write("PROJECTS\n");
                    for (resumeModel.Project project : resume.projects) {
                        writer.write(project.name + "\n");
                        writer.write(project.description + "\n");
                        for (String achievement : project.achievements) {
                            writer.write("  * " + achievement + "\n");
                        }
                        writer.write("\n");
                    }
                } else if (section.equals("Skills")) {
                    writer.write("SKILLS\n");
                    for (resumeModel.Skill skill : resume.skills) {
                        writer.write("  * " + skill.name + "\n");
                    }
                    writer.write("\n");
                } else if (section.equals("Languages")) {
                    writer.write("LANGUAGES\n");
                    for (resumeModel.Language language : resume.languages) {
                        writer.write("  * " + language.name + " (" + language.proficiency + ")\n");
                    }
                    writer.write("\n");
                }
            }
            view.showMessage("Resume saved!");
        } catch (IOException e) {
            view.showMessage("Error saving resume: " + e.getMessage());
        }
    }

    private void previewResume() {
        StringBuilder preview = new StringBuilder();
        preview.append(resume.personalInfo.name.toUpperCase()).append("\n");
        preview.append(resume.personalInfo.email).append("\n");
        preview.append(resume.personalInfo.phone).append("\n");
        preview.append(resume.personalInfo.address).append("\n");
        if (!resume.personalInfo.photoPath.isEmpty()) {
            preview.append("Photo: ").append(resume.personalInfo.photoPath).append("\n");
        }
        preview.append("\n");

        for (String section : resume.sectionOrder) {
            if (section.equals("Education")) {
                preview.append("EDUCATION\n");
                for (resumeModel.Education edu : resume.educations) {
                    preview.append(edu.institution).append(" - ").append(edu.degree)
                            .append(" (").append(edu.duration).append(")\n");
                }
                preview.append("\n");
            } else if (section.equals("Work Experience")) {
                preview.append("WORK EXPERIENCE\n");
                for (resumeModel.WorkExperience work : resume.workExperiences) {
                    preview.append(work.company).append(" - ").append(work.role)
                            .append(" (").append(work.duration).append(")\n");
                    for (String achievement : work.achievements) {
                        preview.append("  * ").append(achievement).append("\n");
                    }
                    preview.append("\n");
                }
            } else if (section.equals("Projects")) {
                preview.append("PROJECTS\n");
                for (resumeModel.Project project : resume.projects) {
                    preview.append(project.name).append("\n");
                    preview.append(project.description).append("\n");
                    for (String achievement : project.achievements) {
                        preview.append("  * ").append(achievement).append("\n");
                    }
                    preview.append("\n");
                }
            } else if (section.equals("Skills")) {
                preview.append("SKILLS\n");
                for (resumeModel.Skill skill : resume.skills) {
                    preview.append("  * ").append(skill.name).append("\n");
                }
                preview.append("\n");
            } else if (section.equals("Languages")) {
                preview.append("LANGUAGES\n");
                for (resumeModel.Language language : resume.languages) {
                    preview.append("  * ").append(language.name)
                            .append(" (").append(language.proficiency).append(")\n");
                }
                preview.append("\n");
            }
        }

        view.showPreview(preview.toString());
    }
}
