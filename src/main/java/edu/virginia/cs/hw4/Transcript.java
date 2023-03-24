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
    public Transcript(Student student, Map<Course,Grade> courseHistory) {
        this.student = student;
        this.courseHistory = courseHistory;
    }
    public void addCourse(Course course, Grade grade){
        courseHistory.put(course, grade);
    }

    public Transcript(Map<Course, Grade> courseHistory){
        this.courseHistory = courseHistory;
    }
}
