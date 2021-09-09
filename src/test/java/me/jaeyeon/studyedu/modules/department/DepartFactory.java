package me.jaeyeon.studyedu.modules.department;

import lombok.RequiredArgsConstructor;
import me.jaeyeon.studyedu.modules.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartFactory {

    @Autowired DepartmentService departmentService;
    @Autowired DepartmentRepository departmentRepository;

    public Department createDepart(Department department, Account account) {
        Department depart = new Department();
        depart.setName("정보통신");
        depart.setLoc("103호");
        departmentService.createDepart(depart, account);
        return depart;
    }
}
