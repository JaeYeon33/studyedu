package me.jaeyeon.studyedu.modules.department;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.jaeyeon.studyedu.modules.professor.Professor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Department {

    @Id @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @Column(name = "dname", nullable = false)
    private String name;

    @Column(nullable = false)
    private String loc;

}
