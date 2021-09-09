package me.jaeyeon.studyedu.modules.department;

import lombok.RequiredArgsConstructor;
import me.jaeyeon.studyedu.modules.account.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department createDepart(Department department, Account account) {
        department.setAccount(account);
        departmentRepository.save(department);
        return department;
    }
}
