
package com.studentms.controller;

import com.studentms.dto.CourseResponse;
import com.studentms.dto.StudentRequest;
import com.studentms.dto.StudentResponse;
import com.studentms.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // CREATE STUDENT
    @PostMapping
    public ResponseEntity<StudentResponse> create(
            @Valid @RequestBody StudentRequest request) {

        StudentResponse response = studentService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET ALL STUDENTS
    @GetMapping
    public List<StudentResponse> getAll() {
        return studentService.getAll();
    }

    // GET STUDENT BY ID
    @GetMapping("/{id}")
    public StudentResponse getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    // UPDATE STUDENT
    @PutMapping("/{id}")
    public StudentResponse update(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {

        return studentService.update(id, request);
    }

    // DELETE STUDENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        studentService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // ENROLL STUDENT IN COURSE
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Map<String, String>> enroll(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        studentService.enroll(studentId, courseId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Student enrolled successfully"));
    }

    // REMOVE STUDENT FROM COURSE
    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> unenroll(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        studentService.unenroll(studentId, courseId);

        return ResponseEntity.noContent().build();
    }

    // GET COURSES OF A STUDENT
    @GetMapping("/{studentId}/courses")
    public List<CourseResponse> getCourses(
            @PathVariable Long studentId) {

        return studentService.getCoursesOfStudent(studentId);
    }
}
