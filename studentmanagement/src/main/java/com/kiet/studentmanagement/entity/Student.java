package com.kiet.studentmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @NotBlank(message = "Student name is required")
    @Column(name = "student_name", nullable = false)
    private String studentName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @NotBlank(message = "Course name is required")
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotNull(message = "Mathematics score is required")
    @DecimalMin(value = "0.0", message = "Score cannot be below 0")
    @DecimalMax(value = "100.0", message = "Score cannot exceed 100")
    @Column(name = "mathematics_score", nullable = false)
    private Double mathematicsScore;

    @NotNull(message = "Programming score is required")
    @DecimalMin(value = "0.0", message = "Score cannot be below 0")
    @DecimalMax(value = "100.0", message = "Score cannot exceed 100")
    @Column(name = "programming_score", nullable = false)
    private Double programmingScore;

    @ElementCollection
    @CollectionTable(
            name = "student_skills",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "skill")
    private Set<String> skills = new HashSet<>();

    public Student() {
    }

    // Calculate average score
    @Transient
    public double getAverageScore() {
        if (mathematicsScore == null || programmingScore == null) {
            return 0.0;
        }

        return (mathematicsScore + programmingScore) / 2.0;
    }

    // Performance category
    @Transient
    public String getPerformanceCategory() {
        double average = getAverageScore();

        if (average >= 85) {
            return "Excellent";
        } else if (average >= 70) {
            return "Good";
        } else if (average >= 50) {
            return "Satisfactory";
        } else {
            return "Needs Improvement";
        }
    }

    // Identify stronger subject
    @Transient
    public String getStrongerSubject() {
        if (mathematicsScore == null || programmingScore == null) {
            return "Not Available";
        }

        if (mathematicsScore > programmingScore) {
            return "Mathematics";
        } else if (programmingScore > mathematicsScore) {
            return "Programming";
        } else {
            return "Both Equal";
        }
    }

    // Getters and Setters

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public Double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }
}