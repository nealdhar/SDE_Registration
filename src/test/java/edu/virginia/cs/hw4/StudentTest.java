package edu.virginia.cs.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentTest {

    private Student testStudent ;

    private Transcript testTranscript;


    private Course mockCourse ;

    Map<Course, Grade> mockCourseHistory;



    @BeforeEach
    public void setUp(){
        mockCourseHistory = (Map<Course, Grade>) mock(Map.class);
        mockCourse = mock(Course.class);
        testTranscript = getTranscript();
        testStudent = getStudent();
    }
    private Transcript getTranscript() {
        return new Transcript(mockCourseHistory);
    }
    private Student getStudent() {
        return new Student(1, "Neal", "nd2pvz@virginia.edu", testTranscript);
    }

    @Test
    public void testAddCourseGrade() {
        testStudent.addCourseGrade(mockCourse, Grade.A);
        verify(mockCourseHistory).put(mockCourse, Grade.A);
    }

    @Test
    public void testHasStudentTakenCourse() {
        mockCourseHistory.put(mockCourse, Grade.A_MINUS);
        when(testStudent.hasStudentTakenCourse(mockCourse)).thenReturn(true);
    }

    @Test
    public void getCourseGrade() {
    }

    @Test
    public void meetsPrerequisite() {
    }

    @Test
    public void getGPA() {
    }
}