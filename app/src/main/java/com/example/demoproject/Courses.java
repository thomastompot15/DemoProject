package com.example.demoproject;

public class Courses {

    private  String courseName;
    private  String courseDescription;
    private  String courseDuration;

    public Courses(String courseName, String courseDescription, String courseDuration) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseDuration = courseDuration;
    }

    public Courses() {
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public String getCourseDuration() {
        return courseDuration;
    }
}
