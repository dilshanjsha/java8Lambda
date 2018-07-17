/**
 * 
 */
package com.alex.course;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dilshan
 * Course Details:   
 *  courseId, courseTitle, prerequisiteCourses, completionLevel 
 */
public class CourseUnit {

	private int courseId;
	private String courseTitle;
	private Set<CourseUnit> prerequisiteCourses;
	private int completionLevel = 1;

	/**
	 * Initialization for a Course
	 * @param courseId
	 * @param title
	 */
	public CourseUnit(int courseId, String title) {
		this.courseId = courseId;
		this.courseTitle = title;
		this.prerequisiteCourses = new HashSet<>();
	}


	public CourseUnit(int courseId) {
		// to directly load from csv file
		this.courseId = courseId;
		this.courseTitle = "";
		this.prerequisiteCourses = new HashSet<>();
	}


	/**
	 * @return the courseId
	 */
	public int getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the courseTitle
	 */
	public String getCourseTitle() {
		return courseTitle;
	}

	/**
	 * @param courseTitle the courseTitle to set
	 */
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	/**
	 * @return the prerequisiteCourses
	 */
	public Set<CourseUnit> getPrerequisiteCourses() {
		return prerequisiteCourses;
	}

	/**
	 * @param prerequisiteCourses the prerequisiteCourses to set
	 */
	public void setPrerequisiteCourses(Set<CourseUnit> prerequisiteCourses) {
		this.prerequisiteCourses = prerequisiteCourses;
	}

    /**
     * Adds a prerequisiteCourse to this course
     * @param prerequisiteCourse the prerequisiteCourse to be added
     */
    public void addPrerequisiteCourse(CourseUnit prerequisiteCourse) {
        this.prerequisiteCourses.add(prerequisiteCourse);
    }

	/**
	 * @return the completionLevel
	 */
	public int getCompletionLevel() {
		return completionLevel;
	}

	/**
	 * @param completionLevel the completionLevel to set
	 */
	public void setCompletionLevel(int completionLevel) {
		this.completionLevel = completionLevel;
	}

	/**
	 * @param completionLevel the completionLevel to set
	 */
	public void incrementCompletionLevel(int incrementBy) {
		this.completionLevel += incrementBy;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder(String.format(
				"[CourseId: %d, title: %s , completion Level: %d ", courseId, courseTitle, completionLevel));

		sb.append("prerequisites: ");
		for(CourseUnit preReq : this.prerequisiteCourses) {
			sb.append(preReq.getCourseId());
			sb.append(" - ");
			sb.append(preReq.getCourseTitle());
			sb.append(" ");
		}
		sb.append("]");

		return sb.toString();
	}

}
