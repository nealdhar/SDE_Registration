package edu.virginia.cs.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistrationImplTest {
    private Student mockStudent;
    private Course mockCourse;
    private Course mockCourse1;
    private Course mockCourse2;
    private Course mockCourse3;
    private RegistrationImpl registration;
    private CourseCatalog courseCatalog;
    private Prerequisite prerequisite;

    @BeforeEach
    public void setUp(){
        mockCourse = mock(Course.class);
        mockCourse1 = mock(Course.class);
        mockCourse2 = mock(Course.class);
        mockCourse3 = mock(Course.class);
        mockStudent = mock(Student.class);
        courseCatalog = mock(CourseCatalog.class);
        registration = new RegistrationImpl();
        registration.setCourseCatalog(courseCatalog);
    }

    @Test
    public void testIsEnrollmentFullWhenCapIsReached() {
        when(mockCourse1.getCurrentEnrollmentSize()).thenReturn(25);
        when(mockCourse1.getEnrollmentCap()).thenReturn(25);
        assertTrue(registration.isEnrollmentFull(mockCourse1));
    }

    @Test
    public void testIsEnrollmentFullWhenCapIsNotReached() {
        when(mockCourse2.getCurrentEnrollmentSize()).thenReturn(20);
        when(mockCourse2.getEnrollmentCap()).thenReturn(25);
        assertFalse(registration.isEnrollmentFull(mockCourse2));
    }
    @Test
    void testIsWaitListFullWhenCapIsReached() {
        when(mockCourse1.getCurrentWaitListSize()).thenReturn(10);
        when(mockCourse1.getWaitListCap()).thenReturn(10);
        assertTrue(registration.isWaitListFull(mockCourse1));
    }
    @Test
    void testIsWaitListFullWhenCapIsNotReached() {
        when(mockCourse3.getCurrentWaitListSize()).thenReturn(5);
        when(mockCourse3.getWaitListCap()).thenReturn(10);
        assertFalse(registration.isWaitListFull(mockCourse3));
    }

    @Test
    public void testGetEnrollmentStatusOPEN() {
        when(mockCourse1.getEnrollmentCap()).thenReturn(25);
        when(mockCourse1.getCurrentEnrollmentSize()).thenReturn(20);
        when(mockCourse1.getWaitListCap()).thenReturn(10);
        when(mockCourse1.getCurrentWaitListSize()).thenReturn(5);
        assertEquals(Course.EnrollmentStatus.OPEN, registration.getEnrollmentStatus(mockCourse1));

    }

    @Test
    public void testGetEnrollmentStatusWAIT_LIST() {
        when(mockCourse2.getEnrollmentCap()).thenReturn(25);
        when(mockCourse2.getCurrentEnrollmentSize()).thenReturn(25);
        when(mockCourse2.getWaitListCap()).thenReturn(10);
        when(mockCourse2.getCurrentWaitListSize()).thenReturn(5);
        assertEquals(Course.EnrollmentStatus.WAIT_LIST, registration.getEnrollmentStatus(mockCourse2));
    }
    @Test
    public void testGetEnrollmentStatusCLOSED() {
        when(mockCourse3.getEnrollmentCap()).thenReturn(25);
        when(mockCourse3.getCurrentEnrollmentSize()).thenReturn(25);
        when(mockCourse3.getWaitListCap()).thenReturn(10);
        when(mockCourse3.getCurrentWaitListSize()).thenReturn(10);
        assertEquals(Course.EnrollmentStatus.CLOSED, registration.getEnrollmentStatus(mockCourse3));
    }

    @Test
    public void testAreCoursesConflictedWithNoOverlap() {
        when(mockCourse1.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        when(mockCourse1.getMeetingStartTimeHour()).thenReturn(8);
        when(mockCourse1.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse1.getMeetingDurationMinutes()).thenReturn(90);
        
        
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(9);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(31);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(90);

        assertFalse(registration.areCoursesConflicted(mockCourse1, mockCourse2));
    }

    @Test
    public void testAreCoursesConflictedWithOverlap() {
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(12);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(30);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(75);
        
        when(mockCourse3.getMeetingDays()).thenReturn(List.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));
        when(mockCourse3.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse3.getMeetingStartTimeMinute()).thenReturn(40);
        when(mockCourse3.getMeetingDurationMinutes()).thenReturn(60);

        assertTrue(registration.areCoursesConflicted(mockCourse2, mockCourse3));
    }

    @Test
    public void testHasConflictWithStudentSchedule_WithConflict() {
        when(courseCatalog.getCoursesEnrolledIn(mockStudent)).thenReturn(List.of(mockCourse1, mockCourse2));

        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(12);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(30);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(75);

        when(mockCourse1.getMeetingDays()).thenReturn(List.of(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));
        when(mockCourse1.getMeetingDurationMinutes()).thenReturn(11);
        when(mockCourse1.getMeetingStartTimeMinute()).thenReturn(40);
        when(mockCourse1.getMeetingDurationMinutes()).thenReturn(60);

        registration.areCoursesConflicted(mockCourse1, mockCourse);
        assertTrue(registration.hasConflictWithStudentSchedule(mockCourse, mockStudent));
    }

    @Test
    public void testHasConflictWithStudentSchedule_NoConflict() {
        when(courseCatalog.getCoursesEnrolledIn(mockStudent)).thenReturn(List.of(mockCourse1, mockCourse2));

        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(12);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(30);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(75);

        when(mockCourse1.getMeetingDays()).thenReturn(List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));
        when(mockCourse1.getMeetingDurationMinutes()).thenReturn(5);
        when(mockCourse1.getMeetingStartTimeMinute()).thenReturn(40);
        when(mockCourse1.getMeetingDurationMinutes()).thenReturn(60);

        assertFalse(registration.hasConflictWithStudentSchedule(mockCourse, mockStudent));
    }

    @Test
    public void testHasStudentMeetsPrerequisitesTrue() {
        when(mockCourse1.getDepartment()).thenReturn("CS");
        when(mockCourse1.getCatalogNumber()).thenReturn(2100);
        when(mockCourse2.getDepartment()).thenReturn("CS");
        when(mockCourse2.getCatalogNumber()).thenReturn(1110);

        when(mockStudent.hasStudentTakenCourse(mockCourse1)).thenReturn(true);
        when(mockStudent.hasStudentTakenCourse(mockCourse2)).thenReturn(true);

        List<Prerequisite> prerequisites = List.of( new Prerequisite(mockCourse1, Grade.C),
                new Prerequisite(mockCourse2, Grade.B));
        assertTrue(registration.hasStudentMeetsPrerequisites(mockStudent, prerequisites));

    }

    @Test
    public void testHasStudentMeetsPrerequisitesFalse() {
        when(mockCourse1.getDepartment()).thenReturn("CS");
        when(mockCourse1.getCatalogNumber()).thenReturn(2100);
        when(mockCourse2.getDepartment()).thenReturn("CS");
        when(mockCourse2.getCatalogNumber()).thenReturn(1110);

        when(mockStudent.hasStudentTakenCourse(mockCourse1)).thenReturn(false);
        when(mockStudent.hasStudentTakenCourse(mockCourse2)).thenReturn(false);

        List<Prerequisite> prerequisites = List.of( new Prerequisite(mockCourse1, Grade.C),
                new Prerequisite(mockCourse2, Grade.B));
        assertFalse(registration.hasStudentMeetsPrerequisites(mockStudent, prerequisites));
    }

    @Test
    public void testRegisterStudentForCourseCLOSED() {
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.CLOSED);
        assertEquals(RegistrationResult.COURSE_CLOSED, registration.registerStudentForCourse(mockStudent, mockCourse));
    }

    @Test
    public void testRegisterStudentForCourseFULL() {
        when(mockCourse2.getEnrollmentCap()).thenReturn(25);
        when(mockCourse2.getCurrentEnrollmentSize()).thenReturn(25);
        when(mockCourse2.getWaitListCap()).thenReturn(5);
        when(mockCourse2.getCurrentWaitListSize()).thenReturn(10);
        doNothing().when(mockCourse2).addStudentToWaitList(mockStudent);
        assertEquals(RegistrationResult.COURSE_FULL, registration.registerStudentForCourse(mockStudent, mockCourse2));
    }

    @Test
    public void testRegisterStudentForCourseSCHEDULECONFLICT() {
       // when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.CLOSED);
       // assertEquals(RegistrationResult.SCHEDULE_CONFLICT, registration.registerStudentForCourse(mockStudent, mockCourse));
    }

    @Test
    public void testRegisterStudentForCoursePREREQNOTMET() {
    }

    @Test
    public void testRegisterStudentForCourseWAITLIST() {
       /*
        when(mockCourse2.getEnrollmentCap()).thenReturn(25);
        when(mockCourse2.getCurrentEnrollmentSize()).thenReturn(24);
        when(mockCourse2.getWaitListCap()).thenReturn(0);
        when(mockCourse2.getCurrentWaitListSize()).thenReturn(0);
        doNothing().when(mockCourse2).addStudentToWaitList(mockStudent);
        when(mockCourse2.getEnrollmentCap()).thenReturn(25);
        when(mockCourse2.getCurrentEnrollmentSize()).thenReturn(25);
        assertEquals(RegistrationResult.WAIT_LISTED, registration.registerStudentForCourse(mockStudent, mockCourse2));
        */

    }

    @Test
    public void testRegisterStudentForCourseENROLLED() {
        when(mockCourse2.getEnrollmentCap()).thenReturn(25);
        when(mockCourse2.getCurrentEnrollmentSize()).thenReturn(20);
        when(mockCourse2.getWaitListCap()).thenReturn(0);
        when(mockCourse2.getCurrentWaitListSize()).thenReturn(0);
        doNothing().when(mockCourse2).addStudentToEnrolled(mockStudent);
        assertEquals(RegistrationResult.ENROLLED, registration.registerStudentForCourse(mockStudent, mockCourse2));
    }

    @Test
    public void testDropCourseException() {
        when(mockCourse.isStudentEnrolled(mockStudent)).thenReturn(false);
        when(mockCourse.isStudentWaitListed(mockStudent)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> registration.dropCourse(mockStudent, mockCourse));
    }
    @Test
    public void testDropCourseWhenEnrolled() {
        Student waitListStudent = mock(Student.class);
        when(mockCourse.isStudentEnrolled(mockStudent)).thenReturn(true);
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.WAIT_LIST);
        when(mockCourse.isWaitListEmpty()).thenReturn(false);
        when(mockCourse.getFirstStudentOnWaitList()).thenReturn(waitListStudent);
        mockCourse.addStudentToEnrolled(waitListStudent);
        registration.dropCourse(mockStudent, mockCourse);
        //assertTrue(mockCourse.isStudentEnrolled(waitListStudent));
    }

    @Test
    public void testDropCourseWhenWaitlisted() {
        when(mockCourse.isStudentWaitListed(mockStudent)).thenReturn(true);
        registration.dropCourse(mockStudent, mockCourse);
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.CLOSED);
        mockCourse.setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
        //assertEquals(Course.EnrollmentStatus.WAIT_LIST, registration.getEnrollmentStatus(mockCourse));
    }
}