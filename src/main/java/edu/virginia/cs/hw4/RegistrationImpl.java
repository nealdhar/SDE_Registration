package edu.virginia.cs.hw4;

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
        if(course.getEnrollmentCap() <= course.getCurrentEnrollmentSize()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isWaitListFull(Course course) {
        if(course.getWaitListCap() <= course.getCurrentWaitListSize()) {
            return true;
        }
        return false;
    }

    @Override
    public Course.EnrollmentStatus getEnrollmentStatus(Course course) {
        return course.getEnrollmentStatus();
    }

    @Override
    public boolean areCoursesConflicted(Course first, Course second) {

        return false;
    }

    @Override
    public boolean hasConflictWithStudentSchedule(Course course, Student student) {
        return false;
    }

    @Override
    public boolean hasStudentMeetsPrerequisites(Student student, List<Prerequisite> prerequisites) {
        return false;
    }

    @Override
    public RegistrationResult registerStudentForCourse(Student student, Course course) {
        return null;
    }

    @Override
    public void dropCourse(Student student, Course course) {

    }
}
