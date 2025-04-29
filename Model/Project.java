package Model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    String name, description;
    List<String> achievements;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.achievements = new ArrayList<>();
    }
    public void addAchievement(String achievement) {
        achievements.add(achievement);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<String> achievements) {
        this.achievements = achievements;
    }
}

