package me.jaeyeon.studyedu.modules.department;

import lombok.RequiredArgsConstructor;
import me.jaeyeon.studyedu.modules.account.Account;
import me.jaeyeon.studyedu.modules.account.CurrentUser;
import me.jaeyeon.studyedu.modules.department.form.DepartForm;
import me.jaeyeon.studyedu.modules.department.validator.DepartFormValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final DepartFormValidator departFormValidator;

    @InitBinder("departForm")
    public void departmentInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(departFormValidator);
    }

    @GetMapping("/add-department")
    public String newSignUpForm(@CurrentUser Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new DepartForm());
        return "department/form";
    }

    @PostMapping("/add-department")
    public String newSignUpSubmit(@CurrentUser Account account,
                                  @Valid DepartForm departForm,
                                  Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "department/form";
        }

        departmentService.createDepart(modelMapper.map(departForm, Department.class), account);
        return "redirect:/";
    }

    @GetMapping("/departments")
    public String list(@CurrentUser Account account, Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute(account);
        model.addAttribute("departments", departments);
        return "department/list";
    }


    @GetMapping("/department/detail/{departId}")
    public String departList(@CurrentUser Account account,
                             @PathVariable("departId") Department department,
                             Model model) {

        model.addAttribute(account);
        model.addAttribute(department);
        return "department/detail";
    }

    @GetMapping("/department/detail/{departId}/edit")
    public String updateForm(@CurrentUser Account account,
                             @PathVariable("departId") Department department,
                             Model model) {

        model.addAttribute(account);
        model.addAttribute(department);
        model.addAttribute(modelMapper.map(department, DepartForm.class));
        return "department/edit";
    }
}
