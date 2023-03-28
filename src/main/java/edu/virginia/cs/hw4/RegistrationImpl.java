package edu.virginia.cs.hw4;

import java.time.DayOfWeek;
import java.util.List;

public class RegistrationImpl implements Registration {
    //TODO: Implement class
    private CourseCatalog courseCatalog ;

    @Override
    public CourseCatalog getCourseCatalog() {
        return courseCatalog;
    }

    @Override
    public void setCourseCatalog(CourseCatalog courseCatalog) {
        this.courseCatalog = courseCatalog;
    }

    @Override
    public boolean isEnrollmentFull(Course course) {
        if (course.getEnrollmentCap() <= course.getCurrentEnrollmentSize()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isWaitListFull(Course course) {
        if (course.getWaitListCap() <= course.getCurrentWaitListSize()) {
            return true;
        }
        return false;
    }

    @Override
    public Course.EnrollmentStatus getEnrollmentStatus(Course course) {
        if(course.getCurrentEnrollmentSize() < course.getEnrollmentCap()) {
            return Course.EnrollmentStatus.OPEN;
        }
        else if (!isWaitListFull(course)) {
            return Course.EnrollmentStatus.WAIT_LIST;
        }
        else {
            return Course.EnrollmentStatus.CLOSED;
        }
    }

    @Override
    public boolean areCoursesConflicted(Course first, Course second) {
        for (DayOfWeek day : first.getMeetingDays()) {
            if (second.getMeetingDays().contains(day)) {
                int firstStart = first.getMeetingStartTimeHour() * 60 + first.getMeetingStartTimeMinute();
                int firstEnd = firstStart + first.getMeetingDurationMinutes();
                int secondStart = second.getMeetingStartTimeHour() * 60 + second.getMeetingStartTimeMinute();
                int secondEnd = secondStart + second.getMeetingDurationMinutes();
                if (firstStart <= secondEnd && secondStart <= firstEnd) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasConflictWithStudentSchedule(Course course, Student student) {
        return false;
    }

    @Override
    public boolean hasStudentMeetsPrerequisites(Student student, List<Prerequisite> prerequisites) {
        return true;
    }

    @Override
    public RegistrationResult registerStudentForCourse(Student student, Course course) {
       return null;
    }

    @Override
    public void dropCourse(Student student, Course course){
    }
}
