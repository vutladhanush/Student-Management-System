
package com.studentms.service;

import com.studentms.dto.CourseRequest;
import com.studentms.dto.CourseResponse;
import com.studentms.dto.StudentResponse;
import com.studentms.entity.Course;
import com.studentms.entity.Student;
import com.studentms.exception.DuplicateResourceException;
import com.studentms.exception.ResourceNotFoundException;
import com.studentms.repository.CourseRepository;
import com.studentms.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository,
                         StudentRepository studentRepository) {

        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    // CREATE COURSE
    public CourseResponse create(CourseRequest request) {

        if (courseRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Course code already exists");
        }

        Course course = new Course();

        course.setCode(request.getCode());
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCredits(request.getCredits());

        Course savedCourse = courseRepository.save(course);

        return toResponse(savedCourse);
    }

    // GET ALL COURSES
    public List<CourseResponse> getAll() {

        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map(this::toResponse)
                .toList();
    }

    // GET COURSE BY ID
    public CourseResponse getById(Long id) {

        Course course = findCourse(id);

        return toResponse(course);
    }

    // UPDATE COURSE
    public CourseResponse update(Long id, CourseRequest request) {

        Course course = findCourse(id);

        if (courseRepository.existsByCodeAndIdNot(request.getCode(), id)) {
            throw new DuplicateResourceException("Course code already used");
        }

        course.setCode(request.getCode());
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCredits(request.getCredits());

        Course updatedCourse = courseRepository.save(course);

        return toResponse(updatedCourse);
    }

    // DELETE COURSE
    public void delete(Long id) {

        Course course = findCourse(id);

        for (Student student : course.getStudents()) {
            student.getCourses().remove(course);
        }

        courseRepository.delete(course);
    }

    // GET STUDENTS OF A COURSE
    public List<StudentResponse> getStudentsOfCourse(Long courseId) {

        findCourse(courseId);

        List<Student> students =
                studentRepository.findByCoursesId(courseId);

        return students.stream()
                .map(student -> new StudentResponse(
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        student.getPhone()
                ))
                .toList();
    }

    // FIND COURSE BY ID
    private Course findCourse(Long id) {

        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course", id));
    }

    // CONVERT COURSE ENTITY TO RESPONSE DTO
    private CourseResponse toResponse(Course course) {

        return new CourseResponse(
                course.getId(),
                course.getCode(),
                course.getTitle(),
                course.getDescription(),
                course.getCredits()
        );
    }
}
