DROP SCHEMA lr_10_java CASCADE;

CREATE SCHEMA lr_10_java;

CREATE TABLE LR_10_java.Student
(
    student_id      BIGSERIAL    NOT NULL PRIMARY KEY,
    student_name    VARCHAR(100) NOT NULL,
    passport_series VARCHAR(4)   NOT NULL,
    passport_number VARCHAR(6)   NOT NULL
);

ALTER TABLE LR_10_java.Student
    ADD CONSTRAINT unique_passport
        UNIQUE (passport_series, passport_number);


CREATE TABLE LR_10_java.Subject
(
    subject_id   BIGSERIAL    NOT NULL PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL
);

CREATE TABLE LR_10_java.Progress
(
    progress_id BIGSERIAL NOT NULL PRIMARY KEY,
    student_id  BIGINT    NOT NULL REFERENCES LR_10_java.Student (student_id),
    subject_id  BIGINT    NOT NULL REFERENCES LR_10_java.Subject (subject_id),
    assessment  INTEGER   NOT NULL
);

ALTER TABLE LR_10_java.Progress
    ADD CONSTRAINT check_assessment
        CHECK (assessment >= 2 AND assessment <= 5);

ALTER TABLE LR_10_java.Progress
    ADD CONSTRAINT fk_student
        FOREIGN KEY (student_id)
            REFERENCES LR_10_java.Student (student_id)
            ON DELETE CASCADE;

INSERT INTO LR_10_java.Student(student_name, passport_series, passport_number)
VALUES ('Егор', '5436', '234254'),
       ('Даниил', '2342', '345346'),
       ('Дмитрий', '4534', '853452'),
       ('Михаил', '3954', '953454'),
       ('Алексей', '5843', '459345');

INSERT INTO LR_10_java.Subject (subject_name)
VALUES ('Математика'),
       ('Русский язык'),
       ('История'),
       ('Английский язык');

INSERT INTO LR_10_java.Progress (student_id, subject_id, assessment)
VALUES (1, 2, 4),
       (2, 2, 4),
       (1, 1, 2),
       (3, 4, 5),
       (3, 1, 3),
       (4, 2, 4);

SELECT s.*
FROM LR_10_java.Progress p
         JOIN LR_10_java.Student s ON p.student_id = s.student_id
         JOIN LR_10_java.Subject sb ON p.subject_id = sb.subject_id
WHERE sb.subject_name = 'Русский язык'
  AND p.assessment > 3;

SELECT sb.subject_name, AVG(assessment)
FROM LR_10_java.Progress p
JOIN LR_10_java.Subject sb ON p.subject_id = sb.subject_id
WHERE  sb.subject_name = 'Математика'
group by sb.subject_name;

SELECT s.student_name, AVG(assessment)
FROM LR_10_java.Progress p
         JOIN LR_10_java.Student s ON p.student_id = s.student_id
WHERE  s.student_name = 'Егор'
group by s.student_name;

SELECT sb.subject_name, COUNT(sb.subject_name)
FROM LR_10_java.Progress p
JOIN LR_10_java.Student s ON p.student_id = s.student_id
JOIN LR_10_java.Subject sb ON p.subject_id = sb.subject_id
GROUP BY sb.subject_name
ORDER BY COUNT(sb.subject_name) DESC
LIMIT 3;

select * from lr_10_java.Progress;

SELECT DISTINCT s.student_name, sb.subject_name
FROM lr_10_java.Progress p
JOIN lr_10_java.Student s ON p.student_id = s.student_id
JOIN lr_10_java.Subject sb ON p.subject_id = sb.subject_id
WHERE p.assessment = 2
OFFSET 1;




