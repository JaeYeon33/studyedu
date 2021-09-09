package me.jaeyeon.studyedu.modules.department;

import lombok.*;
import me.jaeyeon.studyedu.modules.account.Account;
import me.jaeyeon.studyedu.modules.professor.Professor;

import javax.persistence.*;
import java.net.URLEncoder;
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

    private String loc;

    @ManyToOne
    private Account account;
}
