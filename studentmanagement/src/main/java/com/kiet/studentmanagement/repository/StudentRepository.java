package com.kiet.studentmanagement.repository;

import com.kiet.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Search student by email
    Optional<Student> findByEmailAddressIgnoreCase(String emailAddress);

    // Search students by course
    List<Student> findByCourseNameContainingIgnoreCase(String courseName);

    // Generate merit list in descending order of average
    @Query("""
        SELECT s FROM Student s
        ORDER BY (s.mathematicsScore + s.programmingScore) DESC
    """)
    List<Student> findMeritList();

    // Mathematics >= 75 and Programming < 50
    @Query("""
        SELECT s FROM Student s
        WHERE s.mathematicsScore >= 75
        AND s.programmingScore < 50
    """)
    List<Student> findStudentsNeedingProgrammingImprovement();
}