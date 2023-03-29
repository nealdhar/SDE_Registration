package edu.virginia.cs.hw4;

import java.time.DayOfWeek;
import java.util.List;

public class RegistrationImpl implements Registration {
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
        if (!isEnrollmentFull(course)) {
            return Course.EnrollmentStatus.OPEN;
        } else if (isEnrollmentFull(course) && !isWaitListFull(course)) {
            return Course.EnrollmentStatus.WAIT_LIST;
        } else {
            return Course.EnrollmentStatus.CLOSED;
        }
    }

    @Override
    public boolean areCoursesConflicted(Course first, Course second) {
        for (DayOfWeek day : first.getMeetingDays()) {
            if (second.getMeetingDays().contains(day)) {
                int firstStartTimeInMinutes = first.getMeetingStartTimeHour() * 60 + first.getMeetingStartTimeMinute();
                int firstEndTimeInMinutes = firstStartTimeInMinutes + first.getMeetingDurationMinutes();
                int secondStartTimeInMinutes = second.getMeetingStartTimeHour() * 60 + second.getMeetingStartTimeMinute();
                int secondEndTimeInMinutes = secondStartTimeInMinutes + second.getMeetingDurationMinutes();
                if (firstStartTimeInMinutes < secondEndTimeInMinutes && secondStartTimeInMinutes < firstEndTimeInMinutes) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasConflictWithStudentSchedule(Course course, Student student) {
        for(Course enrolledCourse : courseCatalog.getCoursesEnrolledIn(student)) {
            if(areCoursesConflicted(course, enrolledCourse)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasStudentMeetsPrerequisites(Student student, List<Prerequisite> prerequisites) {
        for (Prerequisite prerequisite : prerequisites) {
            if (!student.hasStudentTakenCourse(prerequisite.course)) { //!student.meetsPrerequisite(course)
                return false;
            }
        }
        return true;
    }

    @Override
    public RegistrationResult registerStudentForCourse(Student student, Course course) {
        if (course.getEnrollmentStatus() == Course.EnrollmentStatus.CLOSED) {
            return RegistrationResult.COURSE_CLOSED;
        }
        if (isEnrollmentFull(course) && isWaitListFull(course)) {
            return RegistrationResult.COURSE_FULL;
        }
        if (hasConflictWithStudentSchedule(course, student)) {
            return RegistrationResult.SCHEDULE_CONFLICT;
        }
        if (!hasStudentMeetsPrerequisites(student, course.getPrerequisites())) {
            return RegistrationResult.PREREQUISITE_NOT_MET;
        }
        if (!isEnrollmentFull(course)) {
            course.addStudentToEnrolled(student);
            if (isEnrollmentFull(course)) {
                course.setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
            }
            return RegistrationResult.ENROLLED;

        } else {
            course.addStudentToWaitList(student);
            if (isWaitListFull(course)) {
                course.setEnrollmentStatus(Course.EnrollmentStatus.CLOSED);
            }
            return RegistrationResult.WAIT_LISTED;
        }
    }

    @Override
    public void dropCourse(Student student, Course course) {
        if (!course.isStudentEnrolled(student) && !course.isStudentWaitListed(student)) {
            throw new IllegalArgumentException("Student is not enrolled or wait-listed in the course");
        }

        if (course.isStudentEnrolled(student)) {
            course.removeStudentFromEnrolled(student);
            if (course.getEnrollmentStatus() == Course.EnrollmentStatus.WAIT_LIST) {
                if (!course.isWaitListEmpty()) {
                    Student firstWaitListedStudent = course.getFirstStudentOnWaitList();
                    course.addStudentToEnrolled(firstWaitListedStudent);
                    course.setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
                } else if (!isEnrollmentFull(course)) { // enroll student from wait list is enrollment is not full
                    course.setEnrollmentStatus(Course.EnrollmentStatus.OPEN);
                }
            }
        } else if (course.isStudentWaitListed(student)) {
            course.removeStudentFromWaitList(student);
            if (course.getEnrollmentStatus() == Course.EnrollmentStatus.CLOSED) {
                course.setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
            }
        }
    }
}
