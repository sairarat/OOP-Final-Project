package Model;

import java.util.ArrayList;
import java.util.List;

public class WorkExperience {

    String company, role, duration;
    List<String> achievements;

    public WorkExperience(String company, String role, String duration) {
        this.company = company;
        this.role = role;
        this.duration = duration;
        this.achievements = new ArrayList<>();
    }

    public void addAchievement(String achievement) {
        achievements.add(achievement);
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<String> achievements) {
        this.achievements = achievements;
    }
}
