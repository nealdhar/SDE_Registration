package edu.virginia.cs.hw4;

import java.util.HashMap;
import java.util.Map;

public class Transcript {
    Student student;
    Map<Course, Grade> courseHistory;

    public Transcript(Student student) {
        this.student = student;
        this.courseHistory = new HashMap<>();
    }
    public void addCourse(Course course, Grade grade){
        courseHistory.put(course, grade);
    }

    public Transcript(Student student, Map<Course,Grade> courseHistory) {
        this.student = student;
        this.courseHistory = courseHistory;
    }

    public boolean hasCourse(Course course) {
        if (this.courseHistory.containsKey(course)) {
            return true;
        }
        return false ;
    }
    public Grade getCourseGrade(Course course) {
        if (hasCourse(course)) {
            return courseHistory.get(course);
        }
        throw new IllegalArgumentException("ERROR: Student has no grade for " + course);
    }
    public double getGPA() {
        if (courseHistory.isEmpty()) {
            throw new IllegalStateException("No courses taken, cannot get GPA");
        }
        double totalGradePoints = 0.0;
        int creditsAttempted = 0;
        for (Course course : courseHistory.keySet()) {
            Grade grade = courseHistory.get(course);
            int credits = course.getCreditHours();
            totalGradePoints += grade.gpa * credits;
            creditsAttempted += credits;
        }
        return totalGradePoints / creditsAttempted;
    }
}
