package com.kiet.studentmanagement.service;

import com.kiet.studentmanagement.entity.Student;
import com.kiet.studentmanagement.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Add new student
    public Student addStudent(Student student) {

        student.setStudentId(null);

        if (studentRepository.findByEmailAddressIgnoreCase(
                student.getEmailAddress()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already exists");
        }

        return studentRepository.save(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Search student by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));
    }

    // Search student by email
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmailAddressIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found"));
    }

    // Search students by course
    public List<Student> getStudentsByCourse(String course) {
        return studentRepository
                .findByCourseNameContainingIgnoreCase(course);
    }

    // Generate merit list
    public List<Student> getMeritList() {
        return studentRepository.findMeritList();
    }

    // Find students with same technical skills
    public Map<String, List<Student>> getStudentsWithSameSkills() {

        List<Student> students = studentRepository.findAll();

        return students.stream()
                .filter(s -> s.getSkills() != null)
                .flatMap(student -> student.getSkills().stream()
                        .map(skill -> new AbstractMap.SimpleEntry<>(
                                skill.trim().toLowerCase(), student)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(
                                Map.Entry::getValue,
                                Collectors.toList()
                        )
                ))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    // Students needing programming improvement
    public List<Student> getStudentsNeedingImprovement() {
        return studentRepository
                .findStudentsNeedingProgrammingImprovement();
    }

    // Update student
    @Transactional
    public Student updateStudent(Long id, Student updatedStudent) {

        Student existingStudent = getStudentById(id);

        if (!existingStudent.getEmailAddress()
                .equalsIgnoreCase(updatedStudent.getEmailAddress())) {

            if (studentRepository.findByEmailAddressIgnoreCase(
                    updatedStudent.getEmailAddress()).isPresent()) {

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Email already exists");
            }
        }

        existingStudent.setStudentName(updatedStudent.getStudentName());
        existingStudent.setEmailAddress(updatedStudent.getEmailAddress());
        existingStudent.setCourseName(updatedStudent.getCourseName());
        existingStudent.setMathematicsScore(
                updatedStudent.getMathematicsScore());
        existingStudent.setProgrammingScore(
                updatedStudent.getProgrammingScore());
        existingStudent.setSkills(
                updatedStudent.getSkills() == null
                        ? new HashSet<>()
                        : updatedStudent.getSkills());

        return studentRepository.save(existingStudent);
    }

    // Delete student
    public String deleteStudent(Long id) {

        Student student = getStudentById(id);

        studentRepository.delete(student);

        return "Student deleted successfully";
    }
}