package com.kiet.studentmanagement.controller;

import com.kiet.studentmanagement.entity.Student;
import com.kiet.studentmanagement.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 1. Add student
    @PostMapping
    public ResponseEntity<Student> addStudent(
            @Valid @RequestBody Student student) {

        Student savedStudent = studentService.addStudent(student);

        return new ResponseEntity<>(
                savedStudent, HttpStatus.CREATED);
    }

    // 2. Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // 3. Search student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // 4. Search student by email
    @GetMapping("/search/email")
    public Student getStudentByEmail(
            @RequestParam String email) {

        return studentService.getStudentByEmail(email);
    }

    // 5. Search students by course
    @GetMapping("/search/course")
    public List<Student> getStudentsByCourse(
            @RequestParam String course) {

        return studentService.getStudentsByCourse(course);
    }

    // 6. Generate merit list
    @GetMapping("/merit-list")
    public List<Student> getMeritList() {
        return studentService.getMeritList();
    }

    // 7. Find students with same technical skills
    @GetMapping("/same-skills")
    public Map<String, List<Student>> getStudentsWithSameSkills() {
        return studentService.getStudentsWithSameSkills();
    }

    // 8. Students needing programming improvement
    @GetMapping("/programming-improvement")
    public List<Student> getStudentsNeedingImprovement() {
        return studentService.getStudentsNeedingImprovement();
    }

    // 9. Update student
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student student) {

        return studentService.updateStudent(id, student);
    }

    // 10. Delete student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}