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
    public void testIsEnrollmentFullWhenCapIsReached() {
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(25);
        when(mockCourse.getEnrollmentCap()).thenReturn(25);
        assertTrue(registration.isEnrollmentFull(mockCourse));
    }

    @Test
    public void testIsEnrollmentFullWhenCapIsNotReached() {
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(20);
        when(mockCourse.getEnrollmentCap()).thenReturn(25);
        assertFalse(registration.isEnrollmentFull(mockCourse));
    }
    @Test
    void testIsWaitListFullWhenCapIsReached() {
        when(mockCourse.getCurrentWaitListSize()).thenReturn(10);
        when(mockCourse.getWaitListCap()).thenReturn(10);
        assertTrue(registration.isWaitListFull(mockCourse));
    }
    @Test
    void testIsWaitListFullWhenCapIsNotReached() {
        when(mockCourse.getCurrentWaitListSize()).thenReturn(5);
        when(mockCourse.getWaitListCap()).thenReturn(10);
        assertFalse(registration.isWaitListFull(mockCourse));
    }

    @Test
    public void testGetEnrollmentStatusOPEN() {
    }

    @Test
    public void testGetEnrollmentStatusWAIT_LIST() {
    }
    @Test
    public void testGetEnrollmentStatusCLOSED() {
    }

    @Test
    public void TestAreCoursesConflicted() {
    }

    @Test
    public void TestHasConflictWithStudentSchedule() {
    }

    @Test
    public void TestHasStudentMeetsPrerequisites() {
    }

    @Test
    public void TestRegisterStudentForCourse() {
    }

    @Test
    public void TestDropCourse() {
    }
}