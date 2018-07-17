package com.alex.course;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Dilshan
 *
 */
public class Decider {

	/**
	 * Given a list of courses (@param courses), looping through the list to find the courses which has its prerequisites fulfilled
	 * 
	 * a. loop through courses list
	 * b. when there is no or all prerequisites match add it to completed list and remove from them from the pending list
	 * c. while looping when there is nothing to remove from the pending list break the loop
	 *  
	 * @param courses Map of all courses where the key is courseId and the value is the Course object
	 */
	public static Map<Integer, CourseUnit> findPossibleCourses(Map<Integer, CourseUnit> courses) {

		Map<Integer, CourseUnit> cloneCourses = new HashMap<Integer, CourseUnit>(courses); // pending list to avoid ConcurrentModificationException 
		Set<CourseUnit> finalCompletionOrder = new LinkedHashSet<CourseUnit>(); // completed list
		Set<CourseUnit> currentMatchingListInALoop = new HashSet<CourseUnit>(); 

		try {
			do {
				currentMatchingListInALoop.clear(); // cleared for next loop
				// a. loop (again) through courses
				cloneCourses.forEach((currentCourseId, currentCourse) -> {
					Set<CourseUnit> currentCoursePrereq = currentCourse.getPrerequisiteCourses();

					// b. when there are no or all prerequisites match, add it to completionOrder and remove from cloneCourses
					if (currentCoursePrereq.isEmpty()||finalCompletionOrder.containsAll(currentCoursePrereq)) {
						// current course has its prerequisites fulfilled
						currentMatchingListInALoop.add(currentCourse);
					}else {
						currentCourse.incrementCompletionLevel(1);
					}

				});

				finalCompletionOrder.addAll(currentMatchingListInALoop);
				currentMatchingListInALoop.forEach((completedCourse) -> {
					cloneCourses.remove(completedCourse.getCourseId());// removed out of for-each to avoid ConcurrentModificationException
				});
				// c. when there is nothing to remove from cloneCourses in a loop, then break
			} while (!currentMatchingListInALoop.isEmpty());


			if(currentMatchingListInALoop.isEmpty() && !cloneCourses.isEmpty()) {
				cloneCourses.forEach((currentCourseId, currentCourse) -> {
					currentCourse.setCompletionLevel(0);
				});
			}

			finalCompletionOrder.forEach(course -> {
				// add the completed courses to pending list to set the byRef value for courses 
				cloneCourses.put(course.getCourseId(), course);
			});
			courses= new HashMap<Integer, CourseUnit>(cloneCourses); 
			printByLevel(courses);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courses;
	}


	/**
	 * printing output by level of completion
	 * a. group courseIds by level of completion
	 * b. print course details for each level of completion
	 * @param courses
	 */
	public static StringBuilder printByLevel(Map<Integer, CourseUnit> courses) {

		//a. group courseIds by level of completion
		Map<Integer, ArrayList<Integer>> courseByLevel = new HashMap<Integer, ArrayList<Integer>>();
		courses.forEach((courseId, unit) -> {
			ArrayList<Integer> courseIds = courseByLevel.get(unit.getCompletionLevel());

			if(courseIds!=null) {
				courseIds = courseByLevel.get(unit.getCompletionLevel());
			}else {
				courseIds = new ArrayList<Integer>();
			}
			courseIds.add(courseId);
			courseByLevel.put(unit.getCompletionLevel(), courseIds);
		});

		
		//b. print course details for each level of completion
		StringBuilder sb = new StringBuilder();
		courseByLevel.forEach((level,courseIds) -> {
			
			if(level==0) {
				sb.append("These courses have a cyclic dependency and can't be completed: \n");
			}else {
				sb.append("\n\n Courses at Completion Level "+level+" : \n");
			}
			courseIds.forEach((currCourseId)->{
				CourseUnit currentCourse = courses.get(currCourseId);
				sb.append(currentCourse.toString()).append("\n");
			});
		});

		
		System.out.println(sb.toString()); // to console
		return sb;
	}













}