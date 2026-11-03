
package com.studentms.service;

import com.studentms.dto.CourseResponse;
import com.studentms.dto.StudentRequest;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository,
                          CourseRepository courseRepository) {

        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // CREATE STUDENT
    public StudentResponse create(StudentRequest request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        Student student = new Student();

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());

        Student savedStudent = studentRepository.save(student);

        return toResponse(savedStudent);
    }

    // GET ALL STUDENTS
    public List<StudentResponse> getAll() {

        List<Student> students = studentRepository.findAll();

        return students.stream()
                .map(this::toResponse)
                .toList();
    }

    // GET STUDENT BY ID
    public StudentResponse getById(Long id) {

        Student student = findStudent(id);

        return toResponse(student);
    }

    // UPDATE STUDENT
    public StudentResponse update(Long id, StudentRequest request) {

        Student student = findStudent(id);

        if (studentRepository.existsByEmailAndIdNot(
                request.getEmail(), id)) {

            throw new DuplicateResourceException("Email already used");
        }

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());

        Student updatedStudent = studentRepository.save(student);

        return toResponse(updatedStudent);
    }

    // DELETE STUDENT
    public void delete(Long id) {

        Student student = findStudent(id);

        student.getCourses().clear();

        studentRepository.delete(student);
    }

    // ENROLL STUDENT IN COURSE
    public void enroll(Long studentId, Long courseId) {

        Student student = findStudent(studentId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course", courseId));

        if (student.getCourses().contains(course)) {
            throw new DuplicateResourceException(
                    "Student already enrolled in this course");
        }

        student.getCourses().add(course);

        studentRepository.save(student);
    }

    // REMOVE STUDENT FROM COURSE
    public void unenroll(Long studentId, Long courseId) {

        Student student = findStudent(studentId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course", courseId));

        student.getCourses().remove(course);

        studentRepository.save(student);
    }

    // GET COURSES OF A STUDENT
    public List<CourseResponse> getCoursesOfStudent(Long studentId) {

        findStudent(studentId);

        List<Course> courses =
                courseRepository.findByStudentsId(studentId);

        return courses.stream()
                .map(course -> new CourseResponse(
                        course.getId(),
                        course.getCode(),
                        course.getTitle(),
                        course.getDescription(),
                        course.getCredits()
                ))
                .toList();
    }

    // FIND STUDENT BY ID
    private Student findStudent(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student", id));
    }

    // CONVERT STUDENT ENTITY TO RESPONSE DTO
    private StudentResponse toResponse(Student student) {

        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getPhone()
        );
    }
}
