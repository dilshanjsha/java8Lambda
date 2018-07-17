/**
 * 
 */
package com.alex.course;

import java.util.Map;

/**
 * @author Dilshan
 *
 */
public class MainSolution {

	public static void main(String[] args) { 
		try {
			Map<Integer, CourseUnit> courses;
			if(args!=null && args.length>0) {
				String COURSE_DETAILS_FILE = args[0];
				String COURSE_PREREQUISITES_FILE = args[1];
				courses = FileLoader.intializeCourses( COURSE_DETAILS_FILE, COURSE_PREREQUISITES_FILE);
			}else {
				courses = FileLoader.intializeCourses( CourseFileConstants.COURSE_DETAILS_FILE, 
						CourseFileConstants.COURSE_PREREQUISITES_FILE);
			}
			courses = Decider.findPossibleCourses(courses);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

