package me.jaeyeon.studyedu.modules.professor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.jaeyeon.studyedu.modules.department.Department;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Professor {

    @Id @GeneratedValue
    private Long id;

    private String professorName;
    private String professorUserId;
    private String position;
    private int sal;
    private LocalDateTime hiredate;
    private int comm;

    @ManyToOne
    private Department department;

}
