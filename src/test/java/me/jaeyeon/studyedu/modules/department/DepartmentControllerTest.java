package me.jaeyeon.studyedu.modules.department;

import lombok.With;
import me.jaeyeon.studyedu.infra.MockMvcTest;
import me.jaeyeon.studyedu.modules.account.WithAccount;
import me.jaeyeon.studyedu.modules.department.form.DepartForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class DepartmentControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired DepartmentService departmentService;
    @Autowired DepartFactory departFactory;

    @AfterEach
    void afterEach() {
        departmentRepository.deleteAll();
    }

    @Test
    @WithAccount("jayeon")
    @DisplayName("학과 개설 폼 조회")
    void createDepart() throws Exception {
        mockMvc.perform(get("/add-department"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/form"))
                .andExpect(model().attributeExists("departForm"));

    }

    @Test
    @WithAccount("jaeyeon")
    @DisplayName("학과 개설 폼 - 실패")
    void createDepart_fail() throws Exception {
        mockMvc.perform(post("/add-department")
                .param("name", "")
                .param("loc", "103호")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("department/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("departForm"))
                .andExpect(model().attributeExists("account"));

        Department test = departmentRepository.findByName("정보통신");
        assertNull(test);
    }

    @Test
    @WithAccount("jaeyeon")
    @DisplayName("학과 개설 폼 - 성공")
    void createDepart_success() throws Exception {
            mockMvc.perform(post("/add-department")
                    .param("name", "정보통신")
                    .param("loc", "103호관")
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));

        Department test = departmentRepository.findByName("정보통신");
        assertNotNull(test);
    }

    @Test
    @WithAccount("jaeyeon")
    @DisplayName("학과 관리 리스트")
    void department_list() throws Exception {
        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/list"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("departments"));
    }
}