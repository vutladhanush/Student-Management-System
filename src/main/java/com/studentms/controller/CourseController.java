
package com.studentms.controller;

import com.studentms.dto.CourseRequest;
import com.studentms.dto.CourseResponse;
import com.studentms.dto.StudentResponse;
import com.studentms.service.CourseService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // CREATE COURSE
    @PostMapping
    public ResponseEntity<CourseResponse> create(
            @Valid @RequestBody CourseRequest request) {

        CourseResponse response = courseService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET ALL COURSES
    @GetMapping
    public List<CourseResponse> getAll() {
        return  courseService.getAll();
    }

    // GET COURSE BY ID
    @GetMapping("/{id}")
    public CourseResponse getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    // UPDATE COURSE
    @PutMapping("/{id}")
    public CourseResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {

        return courseService.update(id, request);
    }

    // DELETE COURSE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        courseService.delete(id);

        return ResponseEntity.noContent().build();
    }

    // GET STUDENTS OF A COURSE
    @GetMapping("/{courseId}/students")
    public List<StudentResponse> getStudents(
            @PathVariable Long courseId) {

        return courseService.getStudentsOfCourse(courseId);
    }
}
