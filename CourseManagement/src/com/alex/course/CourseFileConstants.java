package com.alex.course;

public final class CourseFileConstants {
	/**
	 * Course Id, Course Title present in this file
	 */
    public static final String COURSE_DETAILS_FILE = "courses.csv";

    /**
     * Header names in course id : course name file 
     */
    public static final String COURSE_ID_COL = "id";    
    public static final String COURSE_TITLE_COL = "title"; 
    
    /**
     * Course Id, Prerequisite Course Id present in this file
     */
    public static final String COURSE_PREREQUISITES_FILE = "prerequisites.csv";
    
    /**
     * Header names in course id : prerequisite course id file
     */
    public static final String COURSE_COURSE_COL = "course";    
    public static final String COURSE_PREREQUISITES_COL = "prerequisite"; 
}
