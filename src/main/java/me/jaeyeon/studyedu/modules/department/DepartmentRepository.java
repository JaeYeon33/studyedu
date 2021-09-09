package me.jaeyeon.studyedu.modules.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByName(String name);

    Department findByName(String name);
}
