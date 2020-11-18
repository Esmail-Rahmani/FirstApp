package model;

public class Achievement {
    private int id;
    private String achievementTitle;
    private String achievementDescription;
    private String achievementDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAchievementTitle() {
        return achievementTitle;
    }

    public void setAchievementTitle(String achievementTitle) {
        this.achievementTitle = achievementTitle;
    }

    public String getAchievementDescription() {
        return achievementDescription;
    }

    public void setAchievementDescription(String achievementDescription) {
        this.achievementDescription = achievementDescription;
    }

    public String getAchievementDate() {
        return achievementDate;
    }

    public void setAchievementDate(String achievementDate) {
        this.achievementDate = achievementDate;
    }

    public Achievement() {
    }

    public Achievement( String achievementTitle, String achievementDescription, String achievementDate) {

        this.achievementTitle = achievementTitle;
        this.achievementDescription = achievementDescription;
        this.achievementDate = achievementDate;
    }
}
