




USE Student_Performance_DB;


SHOW DATABASES;






SHOW TABLES;
DESC Students;


USE Student_Performance_DB;





SELECT * FROM Students;







USE Student_Performance_DB;


SHOW TABLES;
DESC Scores;



SELECT * FROM Scores;



USE Student_Performance_DB;



SHOW TABLES;



SELECT * FROM Technical_Skills;

SHOW TABLES;




SELECT * FROM Student_Performance;


SELECT
    student_id,
    student_name,
    programming,
    mathematics,
    average_score
FROM Student_Performance;


SELECT
    student_name,
    average_score,
    performance_category
FROM Student_Performance;


SELECT
    student_name,
    programming,
    mathematics,
    stronger_subject
FROM Student_Performance;

SELECT *
FROM Student_Performance
WHERE student_id = 101;
SELECT *
FROM Student_Performance
WHERE email = 'priya@gmail.com';
SELECT *
FROM Student_Performance
WHERE course = 'B.Tech IT';

SELECT
    student_id,
    student_name,
    course,
    average_score,
    performance_category
FROM Student_Performance
ORDER BY average_score DESC;


SELECT
    skill_name,
    COUNT(DISTINCT student_id) AS total_students,
    GROUP_CONCAT(student_id ORDER BY student_id)
    AS student_ids
FROM Technical_Skills
GROUP BY skill_name
HAVING COUNT(DISTINCT student_id) > 1;


SELECT
    s.student_id,
    s.student_name,
    sc.programming,
    sc.mathematics
FROM Students s
JOIN Scores sc
ON s.student_id = sc.student_id
WHERE sc.programming < 50
AND sc.mathematics >= 75;




SELECT * FROM Student_Performance
ORDER BY average_score DESC;