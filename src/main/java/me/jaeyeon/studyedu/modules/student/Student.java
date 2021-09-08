package me.jaeyeon.studyedu.modules.student;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.jaeyeon.studyedu.modules.department.Department;
import me.jaeyeon.studyedu.modules.professor.Professor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Student {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String userid;

    private int grade;
    private String idnum;
    private LocalDateTime birthdate;
    private int height;
    private int weight;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Professor professor;
}
