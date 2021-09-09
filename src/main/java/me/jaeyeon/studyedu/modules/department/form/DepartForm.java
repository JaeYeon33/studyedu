package me.jaeyeon.studyedu.modules.department.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DepartForm {

    @NotBlank
    private String name;

    private String loc;
}
