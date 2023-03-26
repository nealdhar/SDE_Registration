package edu.virginia.cs.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
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
        testTranscript = getTranscript();
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
        assertThrows(IllegalArgumentException.class, () -> testStudent.getCourseGrade(mockCourse));
    }

    @Test
    public void testGetCourseGradeWithTakingClass() {
        when(testStudent.hasStudentTakenCourse(mockCourse)).thenReturn(true);
        when(mockCourseHistory.get(mockCourse)).thenReturn(Grade.A_MINUS);
        testStudent.getCourseGrade(mockCourse);
        verify(mockCourseHistory).get(mockCourse);
    }

    @Test
    public void testMeetsPrerequisiteWithNotTakingPrerequisite() {
        Prerequisite prerequisite = new Prerequisite(mockCourse, Grade.C);
        when(mockCourseHistory.containsKey(mockCourse)).thenReturn(false);
        assertFalse(testStudent.meetsPrerequisite(prerequisite));
        verify(mockCourseHistory).containsKey(mockCourse);
    }

    @Test
    public void testMeetsPrerequisiteWithTakenClassNotReceivedMinGrade() {
        Prerequisite prerequisite = new Prerequisite(mockCourse, Grade.C);
        when(mockCourseHistory.containsKey(mockCourse)).thenReturn(true);
        when(mockCourseHistory.get(mockCourse)).thenReturn(Grade.C_MINUS);
        assertFalse(testStudent.meetsPrerequisite(prerequisite));
        //verify(mockCourseHistory).containsKey(mockCourse);
        verify(mockCourseHistory).get(mockCourse);
    }

    @Test
    public void testMeetsPrerequisiteWithAllConditionsMet() {
        Prerequisite prerequisite = new Prerequisite(mockCourse, Grade.C);
        when(mockCourseHistory.containsKey(mockCourse)).thenReturn(true);
        when(mockCourseHistory.get(mockCourse)).thenReturn(Grade.C_PLUS);
        assertTrue(testStudent.meetsPrerequisite(prerequisite));
        //verify(mockCourseHistory).containsKey(mockCourse);
        verify(mockCourseHistory).get(mockCourse);
    }

    @Test
    public void testGetGPANoCoursesTaken() {
        when(mockCourseHistory.isEmpty()).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> testStudent.getGPA());

    }
    @Test
    public void testGetGPAOneCreditCourse() {
        Map<Course, Grade> courseHistory = new HashMap<>();
        Transcript transcript = new Transcript(testStudent, courseHistory);
        testStudent = new Student(1, "Neal", "nd2pvz@virginia.edu", transcript);

        when(mockCourse.getCreditHours()).thenReturn(3);
        testStudent.addCourseGrade(mockCourse, Grade.B_PLUS);
        double expectedGPA = Grade.B_PLUS.gpa;

        when(mockCourse.getCreditHours()).thenReturn(3);
        assertEquals(expectedGPA, testStudent.getGPA(), 0.0001);

        verify(mockCourse, times(1)).getCreditHours();
    }

    @Test
    public void testGetGPAMultipleCourses() {
        Map<Course, Grade> courseHistory = new HashMap<>();
        Transcript transcript = new Transcript(testStudent, courseHistory);
        testStudent = new Student(1, "Neal", "nd2pvz@virginia.edu", transcript);

        Course course1 = mock(Course.class);
        when(course1.getCreditHours()).thenReturn(3);
        testStudent.addCourseGrade(course1, Grade.B_PLUS);

        Course course2 = mock(Course.class);
        when(course2.getCreditHours()).thenReturn(4);
        testStudent.addCourseGrade(course2, Grade.A_MINUS);

        double expectedGPA = (Grade.B_PLUS.gpa * 3 + Grade.A_MINUS.gpa * 4) / 7;
        assertEquals(expectedGPA, testStudent.getGPA(), 0.0001);

        verify(course1, times(1)).getCreditHours();
        verify(course2, times(1)).getCreditHours();
    }
}