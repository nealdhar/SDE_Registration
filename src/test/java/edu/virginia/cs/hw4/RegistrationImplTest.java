package edu.virginia.cs.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationImplTest {
    private Student mockStudent;
    private Course mockCourse;
    private RegistrationImpl registration;
    private CourseCatalog courseCatalog;

    @BeforeEach
    public void setUp(){
        mockCourse = mock(Course.class);
        mockStudent = mock(Student.class);
        courseCatalog = new CourseCatalog();
        registration = new RegistrationImpl();
        registration.setCourseCatalog(courseCatalog);
    }

    @Test
    void isEnrollmentFullWhenCapIsReached() {
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(25);
        when(mockCourse.getEnrollmentCap()).thenReturn(25);
        assertTrue(registration.isEnrollmentFull(mockCourse));
    }

    @Test
    void isEnrollmentFullWhenCapIsNotReached() {
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(20);
        when(mockCourse.getEnrollmentCap()).thenReturn(25);
        assertFalse(registration.isEnrollmentFull(mockCourse));
    }


    @Test
    void isWaitListFull() {
    }

    @Test
    void getEnrollmentStatus() {
    }

    @Test
    void areCoursesConflicted() {
    }

    @Test
    void hasConflictWithStudentSchedule() {
    }

    @Test
    void hasStudentMeetsPrerequisites() {
    }

    @Test
    void registerStudentForCourse() {
    }

    @Test
    void dropCourse() {
    }
}