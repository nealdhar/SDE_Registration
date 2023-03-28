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
    public void isEnrollmentFullWhenCapIsReached() {
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(25);
        when(mockCourse.getEnrollmentCap()).thenReturn(25);
        assertTrue(registration.isEnrollmentFull(mockCourse));
    }

    @Test
    public void isEnrollmentFullWhenCapIsNotReached() {
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(20);
        when(mockCourse.getEnrollmentCap()).thenReturn(25);
        assertFalse(registration.isEnrollmentFull(mockCourse));
    }
    @Test
    void isWaitListFullWhenCapIsReached() {
        when(mockCourse.getCurrentWaitListSize()).thenReturn(10);
        when(mockCourse.getWaitListCap()).thenReturn(10);
        assertTrue(registration.isWaitListFull(mockCourse));
    }
    @Test
    void isWaitListFullWhenCapIsNotReached() {
        when(mockCourse.getCurrentWaitListSize()).thenReturn(5);
        when(mockCourse.getWaitListCap()).thenReturn(10);
        assertFalse(registration.isWaitListFull(mockCourse));
    }



    @Test
    public void getEnrollmentStatus() {
    }

    @Test
    public void areCoursesConflicted() {
    }

    @Test
    public void hasConflictWithStudentSchedule() {
    }

    @Test
    public void hasStudentMeetsPrerequisites() {
    }

    @Test
    public void registerStudentForCourse() {
    }

    @Test
    public void dropCourse() {
    }
}