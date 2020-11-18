package model;

public class Education {
    private int id;
    private String courseTitle;
    private String instituteName;
    private String startDate;
    private String endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Education() {
    }

    public Education(String courseTitle, String instituteName, String startDate, String endDate) {
        this.courseTitle = courseTitle;
        this.instituteName = instituteName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
