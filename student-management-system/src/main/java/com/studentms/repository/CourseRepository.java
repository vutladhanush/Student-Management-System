package com.studentms.repository;

import com.studentms.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);

    Page<Course> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<Course> findByStudentsId(Long studentId);
}
