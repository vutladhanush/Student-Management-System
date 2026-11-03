package com.studentms.repository;

import com.studentms.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    // Search by name (case-insensitive, partial match) with pagination and sorting
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Student> findByCoursesId(Long courseId);
}
