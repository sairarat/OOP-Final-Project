import java.util.ArrayList;
import java.util.List;

public class resumeModel {
    public static class PersonalInfo {
        String name, email, phone, address, photoPath;

        PersonalInfo(String name, String email, String phone, String address, String photoPath) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.photoPath = photoPath;
        }
    }

    public static class Education {
        String institution, degree, duration;

        Education(String institution, String degree, String duration) {
            this.institution = institution;
            this.degree = degree;
            this.duration = duration;
        }
    }

    public static class WorkExperience {
        String company, role, duration;
        List<String> achievements = new ArrayList<>();

        WorkExperience(String company, String role, String duration) {
            this.company = company;
            this.role = role;
            this.duration = duration;
        }

        public void addAchievement(String achievement) {
            achievements.add(achievement);
        }
    }

    public static class Project {
        String name, description;
        List<String> achievements = new ArrayList<>();

        Project(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public void addAchievement(String achievement) {
            achievements.add(achievement);
        }
    }

    public static class Skill {
        String name;

        Skill(String name) {
            this.name = name;
        }
    }

    public static class Language {
        String name, proficiency;

        Language(String name, String proficiency) {
            this.name = name;
            this.proficiency = proficiency;
        }
    }

    public static class Resume {
        PersonalInfo personalInfo;
        List<Education> educations = new ArrayList<>();
        List<WorkExperience> workExperiences = new ArrayList<>();
        List<Project> projects = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();
        List<Language> languages = new ArrayList<>();
        List<String> sectionOrder = new ArrayList<>();

        Resume() {
            this.personalInfo = new PersonalInfo("", "", "", "", "");
            sectionOrder.add("Education");
            sectionOrder.add("Work Experience");
            sectionOrder.add("Projects");
            sectionOrder.add("Skills");
            sectionOrder.add("Languages");
        }

        void setPersonalInfo(PersonalInfo info) {
            this.personalInfo = info;
        }

        void addEducation(Education education) {
            educations.add(education);
        }

        void addWorkExperience(WorkExperience exp) {
            workExperiences.add(exp);
        }

        void addProject(Project project) {
            projects.add(project);
        }

        void addSkill(Skill skill) {
            skills.add(skill);
        }

        void removeSkill(String skillName) {
            skills.removeIf(skill -> skill.name.equals(skillName));
        }

        void addLanguage(Language language) {
            languages.add(language);
        }

        void removeLanguage(String languageName) {
            languages.removeIf(lang -> lang.name.equals(languageName));
        }

        void reorderSection(List<String> newOrder) {
            sectionOrder.clear();
            sectionOrder.addAll(newOrder);
        }

        public PersonalInfo getPersonalInfo() {
            return personalInfo;
        }

        public List<Education> getEducations() {
            return educations;
        }

        public List<WorkExperience> getWorkExperiences() {
            return workExperiences;
        }

        public List<Project> getProjects() {
            return projects;
        }

        public List<Skill> getSkills() {
            return skills;
        }

        public List<Language> getLanguages() {
            return languages;
        }

        public List<String> getSectionOrder() {
            return sectionOrder;
        }
    }
}