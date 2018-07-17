package com.alex.course;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 
 * @author Dilshan
 *
 */
public class FileLoader {

	/**
	 * Record separator for a csv file
	 */
	private static final String COMMA = ",";

	/**
	 * 
	 */
	public FileLoader() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param csvFilePath
	 * @return
	 */
	public static List<CourseUnit> processCoursesFile(String csvFilePath, Map<Integer, CourseUnit> courses) {
		List<CourseUnit> inputList = new ArrayList<CourseUnit>();
		try{
			System.out.println("File read .\\"+csvFilePath+" ");
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			InputStream inputCsvFS = cl.getResourceAsStream(csvFilePath) ;
			BufferedReader csvFileData = new BufferedReader(new InputStreamReader(inputCsvFS));
			String headers = csvFileData.readLine();// header
			if(headers.contains("title")) {
				inputList = csvFileData.lines().map(mapToCourseIdTitle).collect(Collectors.toList());
			}
			else if(headers.contains("prereq")) {
				inputList = csvFileData.lines().map(line->process(line,courses)).collect(Collectors.toList());
			}
			csvFileData.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}

		return inputList ;
	}

	/**
	 * function to map a row in courses.csv to CourseUnit object
	 */
	public static Function<String, CourseUnit> mapToCourseIdTitle = (line) -> {
		String[] p = line.split(COMMA);// a CSV has comma separated lines
		int courseId = 0;
		String title = "";
		if (p[0] != null && p[0].trim().length() > 0) {
			courseId=(Integer.parseInt(p[0].trim()));//<-- this is the first column in the csv file	
		}
		if (p[1] != null && p[1].trim().length() > 0) {
			title=(p[1].trim());//<-- this is the second column in the csv file
		}
		CourseUnit courseUnit = new CourseUnit(courseId,title);
		return courseUnit;
	};

	

	/**
	 * function to map a row in prerequisites.csv to CourseUnit object
	 * @param line
	 * @param courses to find the course object which has its title set
	 * mapToCourseIdTitle function would be called before this method 
	 * calling order precedes by filename CourseFileConstants.COURSE_DETAILS_FILE
	 * @return
	 */
	public static  CourseUnit process(String line, Map<Integer, CourseUnit> courses) {
		String[] p = line.split(COMMA);// a CSV has comma separated lines
		int courseId = 0;
		int preReqId = 0;
		if (p[0] != null && p[0].trim().length() > 0) {
			courseId = (Integer.parseInt(p[0].trim()));//<-- this is the first column in the csv file	
		}
		if (p[1] != null && p[1].trim().length() > 0) {
			preReqId = Integer.parseInt(p[1].trim());//<-- this is the second column in the csv file
		}
		CourseUnit courseUnit = new CourseUnit(courseId);
		CourseUnit prereqUnit = courses.get(preReqId);
		courseUnit.addPrerequisiteCourse(prereqUnit);
		// TODO: get pre req obj from courses title map
		return courseUnit;
	}
	/**
	 * convert the list of courses to map with course id as key
	 * @param courseList
	 * @param courses
	 * @return
	 */
	public static Map<Integer, CourseUnit> listToTitleMap(List<CourseUnit> courseList){
		Map<Integer, CourseUnit> courses = new HashMap<Integer, CourseUnit>();
		courseList.forEach((course)-> {
			courses.put(course.getCourseId(), course);
		});

		return courses;
	}

	/**
	 * convert the list of courses to map with course id as key
	 * @param courseList
	 * @param courses
	 * @return
	 */
	public static Map<Integer, CourseUnit> listToPreReqMap(List<CourseUnit> courseList, Map<Integer, CourseUnit> courses){
		

		try {
			courseList.forEach((course)-> {
				Set<CourseUnit> preReqs = course.getPrerequisiteCourses();
				CourseUnit temp = courses.get(course.getCourseId());
				preReqs.forEach(tempPre ->{
					temp.addPrerequisiteCourse(tempPre);	
				});
				courses.put(course.getCourseId(), temp); // prereq set
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courses;
	}

	/**
	 * load course details from csv file using Java 8
	 * @param coursesFilePath
	 * @param prerequisitesFilePath
	 * @return
	 */
	public static Map<Integer, CourseUnit> intializeCourses(String coursesFilePath,
			String prerequisitesFilePath){

		Map<Integer, CourseUnit> courses = new HashMap<Integer, CourseUnit>();
		System.out.println("\n\n\n************************************************************");
		System.out.println("************************************************************\n");
		List<CourseUnit> coursesList = processCoursesFile(coursesFilePath,courses);
		courses = listToTitleMap(coursesList);
		List<CourseUnit> prereqList = processCoursesFile(prerequisitesFilePath,courses);
		courses = listToPreReqMap(prereqList,courses);

		return courses;
	}
}
