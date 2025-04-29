package Model;

import java.util.ArrayList;
import java.util.List;

public class Resume {
    PersonalInfo personalInfo;
    List<Education> educations = new ArrayList<>();
    List<WorkExperience> workExperiences = new ArrayList<>();
    List<Project> projects = new ArrayList<>();
    List<Skill> skills = new ArrayList<>();
    List<Language> languages = new ArrayList<>();

    public Resume(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public void addEducation(Education edu) {
        educations.add(edu);
    }

    public void addWorkExperience(WorkExperience exp) {
        workExperiences.add(exp);
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void addLanguage(Language language) {
        languages.add(language);
    }

    public Model.PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public List<Model.Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Model.WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public List<Model.Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Model.Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Model.Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
}
