package edu.virginia.cs.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationImplTest {
    private Student mockStudent;
    private Course mockCourse;
    private RegistrationImpl registration;
    private CourseCatalog courseCatalog;
    private Course.EnrollmentStatus enrollmentStatus;

    @BeforeEach
    public void setUp(){
        mockCourse = mock(Course.class);
        mockStudent = mock(Student.class);
        courseCatalog = mock(CourseCatalog.class);
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
        when(mockCourse.getEnrollmentCap()).thenReturn(25);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(20);
        when(mockCourse.getWaitListCap()).thenReturn(10);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(5);

        Course.EnrollmentStatus expectedEnrollmentStatus = Course.EnrollmentStatus.OPEN;
        Course.EnrollmentStatus actualEnrollmentStatus = registration.getEnrollmentStatus(mockCourse);
        assertEquals(expectedEnrollmentStatus, actualEnrollmentStatus);

    }

    @Test
    public void testGetEnrollmentStatusWAIT_LIST() {
        when(mockCourse.getEnrollmentCap()).thenReturn(25);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(25);
        when(mockCourse.getWaitListCap()).thenReturn(10);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(5);

        Course.EnrollmentStatus expectedEnrollmentStatus = Course.EnrollmentStatus.WAIT_LIST;
        Course.EnrollmentStatus actualEnrollmentStatus = registration.getEnrollmentStatus(mockCourse);
        assertEquals(expectedEnrollmentStatus, actualEnrollmentStatus);
    }
    @Test
    public void testGetEnrollmentStatusCLOSED() {
        when(mockCourse.getEnrollmentCap()).thenReturn(25);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(25);
        when(mockCourse.getWaitListCap()).thenReturn(10);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(10);

        Course.EnrollmentStatus expectedEnrollmentStatus = Course.EnrollmentStatus.CLOSED;
        Course.EnrollmentStatus actualEnrollmentStatus = registration.getEnrollmentStatus(mockCourse);
        assertEquals(expectedEnrollmentStatus, actualEnrollmentStatus);
    }

    @Test
    public void TestAreCoursesConflictedWithNoOverlap() {
        Course first = mock(Course.class);
        when(first.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        when(first.getMeetingStartTimeHour()).thenReturn(8);
        when(first.getMeetingStartTimeMinute()).thenReturn(0);
        when(first.getMeetingDurationMinutes()).thenReturn(90);

        Course second = mock(Course.class);
        when(second.getMeetingDays()).thenReturn(List.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));
        when(second.getMeetingStartTimeHour()).thenReturn(9);
        when(second.getMeetingStartTimeMinute()).thenReturn(31);
        when(second.getMeetingDurationMinutes()).thenReturn(90);

        assertFalse(registration.areCoursesConflicted(first, second));
    }

    @Test
    public void TestAreCoursesConflictedWithOverlap() {
        Course first = mock(Course.class);
        when(first.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        when(first.getMeetingStartTimeHour()).thenReturn(12);
        when(first.getMeetingStartTimeMinute()).thenReturn(30);
        when(first.getMeetingDurationMinutes()).thenReturn(75);

        Course second = mock(Course.class);
        when(second.getMeetingDays()).thenReturn(List.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));
        when(second.getMeetingStartTimeHour()).thenReturn(11);
        when(second.getMeetingStartTimeMinute()).thenReturn(40);
        when(second.getMeetingDurationMinutes()).thenReturn(60);

        assertTrue(registration.areCoursesConflicted(first, second));
    }

    @Test
    public void TestHasConflictWithStudentScheduleTrue() {
        Course enrolledCourse1 = mock(Course.class);
        Course enrolledCourse2 = mock(Course.class);
        when(courseCatalog.getCoursesEnrolledIn(mockStudent)).thenReturn(List.of(enrolledCourse1, enrolledCourse2));

        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(12);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(30);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(75);

        when(enrolledCourse1.getMeetingDays()).thenReturn(List.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));
        when(enrolledCourse1.getMeetingDurationMinutes()).thenReturn(11);
        when(enrolledCourse1.getMeetingStartTimeMinute()).thenReturn(40);
        when(enrolledCourse1.getMeetingDurationMinutes()).thenReturn(60);

        registration.areCoursesConflicted(enrolledCourse1, mockCourse);
        assertTrue(registration.hasConflictWithStudentSchedule(mockCourse, mockStudent));
    }

    @Test
    public void TestHasConflictWithStudentScheduleFalse() {
        Course enrolledCourse1 = mock(Course.class);
        Course enrolledCourse2 = mock(Course.class);
        when(courseCatalog.getCoursesEnrolledIn(mockStudent)).thenReturn(List.of(enrolledCourse1, enrolledCourse2));

        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(8);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(90);

        when(enrolledCourse1.getMeetingDays()).thenReturn(List.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));
        when(enrolledCourse1.getMeetingDurationMinutes()).thenReturn(5);
        when(enrolledCourse1.getMeetingStartTimeMinute()).thenReturn(0);
        when(enrolledCourse1.getMeetingDurationMinutes()).thenReturn(90);

        registration.areCoursesConflicted(enrolledCourse1, mockCourse);
        assertFalse(registration.hasConflictWithStudentSchedule(mockCourse, mockStudent));
    }

    @Test
    public void TestHasStudentMeetsPrerequisitesTrue() {
    }

    @Test
    public void TestHasStudentMeetsPrerequisitesFalse() {
    }

    @Test
    public void TestRegisterStudentForCourseCLOSED() {
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.CLOSED);
        assertEquals(RegistrationResult.COURSE_CLOSED, registration.registerStudentForCourse(mockStudent, mockCourse));
    }

    @Test
    public void TestRegisterStudentForCourseFULL() {
        when(mockCourse.getEnrollmentCap()).thenReturn(25);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(25);
        when(mockCourse.getWaitListCap()).thenReturn(10);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(10);
       // assertTrue(registration.isEnrollmentFull(mockCourse));
       // assertTrue(registration.isWaitListFull(mockCourse));
        assertEquals(RegistrationResult.COURSE_FULL, registration.registerStudentForCourse(mockStudent, mockCourse));
    }

    @Test
    public void TestRegisterStudentForCourseSCHEDULECONFLICT() {
       // when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.CLOSED);
       // assertEquals(RegistrationResult.SCHEDULE_CONFLICT, registration.registerStudentForCourse(mockStudent, mockCourse));
    }

    @Test
    public void TestRegisterStudentForCoursePREREQNOTMET() {
    }

    @Test
    public void TestRegisterStudentForCourseWAITLIST() {
    }

    @Test
    public void TestRegisterStudentForCourseENROLLED() {
    }

    @Test
    public void TestDropCourseWhenEnrolled() {
    }

    @Test
    public void TestDropCourseWhenWaitlisted() {
    }
}