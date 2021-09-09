package me.jaeyeon.studyedu.modules.department.validator;

import lombok.RequiredArgsConstructor;
import me.jaeyeon.studyedu.modules.department.DepartmentRepository;
import me.jaeyeon.studyedu.modules.department.form.DepartForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class DepartFormValidator implements Validator {

    private final DepartmentRepository departmentRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DepartForm signUpForm = (DepartForm) target;
        if (departmentRepository.existsByName(signUpForm.getName())) {
            errors.rejectValue("name", "invalid.name",
                    new Object[]{signUpForm.getName()}, "이미 존재하는 부서입니다.");
        }
    }
}
