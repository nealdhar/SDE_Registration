package edu.virginia.cs.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentTest {

    private Student testStudent ;
    private Transcript testTranscript;
    private Course mockCourse ;
    Map<Course, Grade> mockCourseHistory;

    @BeforeEach
    public void setUp(){
        mockCourseHistory = (Map<Course, Grade>) mock(Map.class);
        mockCourse = mock(Course.class);
        testTranscript = new Transcript(testStudent, mockCourseHistory);
        testStudent = getStudent();
    }
    private Transcript getTranscript() {
        return new Transcript(testStudent, mockCourseHistory);
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
        testStudent.hasStudentTakenCourse(mockCourse);
        verify(mockCourseHistory).containsKey(mockCourse);
    }

    @Test
    public void testGetCourseGradeWithoutTakingClass() {
        when(testStudent.hasStudentTakenCourse(mockCourse)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () ->
                testStudent.getCourseGrade(mockCourse));
    }

    @Test
    public void testGetCourseGradeWithTakingClass() {

    }

    @Test
    public void testMeetsPrerequisite() {
    }

    @Test
    public void testGetGPA() {
    }
}