import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

public class Main {
    // Data structures for resume sections
    static class PersonalInfo {
        String name, email, phone, address;

        PersonalInfo(String name, String email, String phone, String address) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }
    }

    static class WorkExperience {
        String company, role, duration;
        List<String> achievements;

        WorkExperience(String company, String role, String duration) {
            this.company = company;
            this.role = role;
            this.duration = duration;
            this.achievements = new ArrayList<>();
        }

        void addAchievement(String achievement) {
            achievements.add(achievement);
        }
    }

    static class Project {
        String name, description;
        List<String> achievements;

        Project(String name, String description) {
            this.name = name;
            this.description = description;
            this.achievements = new ArrayList<>();
        }

        void addAchievement(String achievement) {
            achievements.add(achievement);
        }
    }

    static class Skill {
        String name;

        Skill(String name) {
            this.name = name;
        }
    }

    // Resume class to hold all sections
    static class Resume {
        PersonalInfo personalInfo;
        List<WorkExperience> workExperiences = new ArrayList<>();
        List<Project> projects = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();
        List<String> sectionOrder = new ArrayList<>();

        Resume(PersonalInfo personalInfo) {
            this.personalInfo = personalInfo;
            sectionOrder.add("Work Experience");
            sectionOrder.add("Projects");
            sectionOrder.add("Skills");
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

        void reorderSections() {
            System.out.println("Current section order: " + sectionOrder);
            System.out.println("Enter new order (e.g., 1=Work Experience, 2=Projects, 3=Skills, input as 1 2 3):");
            Scanner scanner = new Scanner(System.in);
            String[] newOrder = scanner.nextLine().split(" ");
            List<String> tempOrder = new ArrayList<>();
            for (String idx : newOrder) {
                int i = Integer.parseInt(idx);
                if (i == 1) tempOrder.add("Work Experience");
                else if (i == 2) tempOrder.add("Projects");
                else if (i == 3) tempOrder.add("Skills");
            }
            sectionOrder.clear();
            sectionOrder.addAll(tempOrder);
            System.out.println("New section order: " + sectionOrder);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Collect personal info
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your phone:");
        String phone = scanner.nextLine();
        System.out.println("Enter your address:");
        String address = scanner.nextLine();

        PersonalInfo personalInfo = new PersonalInfo(name, email, phone, address);
        Resume resume = new Resume(personalInfo);

        // Collect work experiences
        while (true) {
            System.out.println("Add a work experience? (y/n):");
            if (!scanner.nextLine().equalsIgnoreCase("y")) break;
            System.out.println("Enter company:");
            String company = scanner.nextLine();
            System.out.println("Enter role:");
            String role = scanner.nextLine();
            System.out.println("Enter duration (e.g., Jan 2020 - Dec 2021):");
            String duration = scanner.nextLine();
            WorkExperience exp = new WorkExperience(company, role, duration);
            System.out.println("Add achievements? (y/n):");
            while (scanner.nextLine().equalsIgnoreCase("y")) {
                System.out.println("Enter achievement:");
                exp.addAchievement(scanner.nextLine());
                System.out.println("Add another achievement? (y/n):");
            }
            resume.addWorkExperience(exp);
        }

        // Collect projects
        while (true) {
            System.out.println("Add a project? (y/n):");
            if (!scanner.nextLine().equalsIgnoreCase("y")) break;
            System.out.println("Enter project name:");
            String projectName = scanner.nextLine();
            System.out.println("Enter project description:");
            String description = scanner.nextLine();
            Project project = new Project(projectName, description);
            System.out.println("Add achievements? (y/n):");
            while (scanner.nextLine().equalsIgnoreCase("y")) {
                System.out.println("Enter achievement:");
                project.addAchievement(scanner.nextLine());
                System.out.println("Add another achievement? (y/n):");
            }
            resume.addProject(project);
        }

        // Collect skills
        while (true) {
            System.out.println("Add a skill? (y/n):");
            if (!scanner.nextLine().equalsIgnoreCase("y")) break;
            System.out.println("Enter skill:");
            resume.addSkill(new Skill(scanner.nextLine()));
        }

        // Reorder sections
        System.out.println("Do you want to reorder sections? (y/n):");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            resume.reorderSections();
        }

        // Generate text file
        System.out.println("Enter output text file name (e.g., resume.txt):");
        String textFileName = scanner.nextLine();
        generateTextFile(resume, textFileName);

        System.out.println("Resume generated as " + textFileName);
    }
    static void generateTextFile(Resume resume, String fileName) {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            // Add personal info
            writer.println(resume.personalInfo.name.toUpperCase());
            writer.println(resume.personalInfo.email);
            writer.println(resume.personalInfo.phone);
            writer.println(resume.personalInfo.address);
            writer.println();

            // Add sections in specified order
            for (String section : resume.sectionOrder) {
                if (section.equals("Work Experience")) {
                    writer.println("WORK EXPERIENCE");
                    writer.println("--------------");
                    for (WorkExperience exp : resume.workExperiences) {
                        writer.println(exp.company + " - " + exp.role + " (" + exp.duration + ")");
                        for (String achievement : exp.achievements) {
                            writer.println("  * " + achievement);
                        }
                        writer.println();
                    }
                } else if (section.equals("Projects")) {
                    writer.println("PROJECTS");
                    writer.println("--------");
                    for (Project project : resume.projects) {
                        writer.println(project.name);
                        writer.println(project.description);
                        for (String achievement : project.achievements) {
                            writer.println("  * " + achievement);
                        }
                        writer.println();
                    }
                } else if (section.equals("Skills")) {
                    writer.println("SKILLS");
                    writer.println("------");
                    for (Skill skill : resume.skills) {
                        writer.println("  * " + skill.name);
                    }
                    writer.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Error generating text file: " + e.getMessage());
        }
    }
}