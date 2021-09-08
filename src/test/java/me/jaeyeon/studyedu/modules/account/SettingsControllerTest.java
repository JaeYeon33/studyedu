package me.jaeyeon.studyedu.modules.account;

import me.jaeyeon.studyedu.infra.MockMvcTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static me.jaeyeon.studyedu.modules.account.SettingsController.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class SettingsControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired AccountRepository accountRepository;
    @Autowired AccountService accountService;
    @Autowired PasswordEncoder passwordEncoder;


    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @WithAccount("jaeyeon")
    @DisplayName("프로필 수정하기 - 입력값 정상")
    @Test
    void updateProfile() throws Exception {
        String bio = "짧은 소개를 수정하는 경우";
        mockMvc.perform(post(ROOT + SETTINGS + PROFILE)
                        .param("bio", bio)
                        .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl(ROOT + SETTINGS + PROFILE))
                        .andExpect(flash().attributeExists("message"));

        Account jaeyeon = accountRepository.findByNickname("jaeyeon");
        assertEquals(bio, jaeyeon.getBio());

    }

    @WithAccount("jaeyeon")
    @DisplayName("프로필 수정 하기 - 입력값 에러")
    @Test
    void updateProfile_error() throws Exception {

        String bio = "자기 소개 테스트 매우 길게 매우 길게 매우 길게 매우 길게 언제 까지 길게";
        mockMvc.perform(post(ROOT + SETTINGS + PROFILE)
                .param("bio", bio)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(SETTINGS + PROFILE))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().hasErrors());

        Account jaeyeon = accountRepository.findByNickname("jaeyeon");
        assertNull(jaeyeon.getBio());
    }

    @WithAccount("jaeyeon")
    @DisplayName("프로필 수정 폼")
    @Test
    void updateProfileForm() throws Exception {
        mockMvc.perform(get(ROOT + SETTINGS + PROFILE))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"));
    }

    @WithAccount("jaeyeon")
    @DisplayName("패스워드 수정 폼")
    @Test
    void updatePassword_form() throws Exception {
        mockMvc.perform(get(ROOT + SETTINGS + PASSWORD))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("passwordForm"));
    }

    @WithAccount("jaeyeon")
    @DisplayName("패스워드 수정 - 입력값 정상")
    @Test
    void updatePassword_success() throws Exception {
        mockMvc.perform(post(ROOT + SETTINGS + PASSWORD)
                .param("newPassword", "12345678")
                .param("newPasswordConfirm", "12345678")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ROOT + SETTINGS + PASSWORD))
                .andExpect(flash().attributeExists("message"));

        Account jaeyeon = accountRepository.findByNickname("jaeyeon");
        assertTrue(passwordEncoder.matches("12345678", jaeyeon.getPassword()));
    }

    @WithAccount("jaeyeon")
    @DisplayName("패스워드 수정 - 입력값 에러 - 패스워드 불일치")
    @Test
    void updatePassword_fail() throws Exception {
        mockMvc.perform(post(ROOT + SETTINGS + PASSWORD)
                        .param("newPassword", "12345678")
                        .param("newPasswordConfirm", "11111111")
                        .with(csrf()))
                        .andExpect(status().isOk())
                        .andExpect(view().name(SETTINGS + PASSWORD))
                        .andExpect(model().hasErrors())
                        .andExpect(model().attributeExists("passwordForm"))
                        .andExpect(model().attributeExists("account"));
    }

    @WithAccount("jaeyeon")
    @DisplayName("닉네임 수정 폼")
    @Test
    void updateAccountForm() throws Exception {
        mockMvc.perform(get(ROOT + SETTINGS + ACCOUNT))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("nicknameForm"));
    }

    @WithAccount("jaeyeon")
    @DisplayName("닉네임 수정하기 - 입력값 정상")
    @Test
    void updateAccount_success() throws Exception {
        String newNickname = "sergey";
        mockMvc.perform(post(ROOT + SETTINGS + ACCOUNT)
                        .param("nickname", newNickname)
                        .with(csrf()))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl(ROOT + SETTINGS + ACCOUNT))
                        .andExpect(flash().attributeExists("message"));

        assertNotNull(accountRepository.findByNickname("sergey"));
    }

    @WithAccount("jaeyeon")
    @DisplayName("닉네임 수정하기 - 입력값 에러")
    @Test
    void updateAccount_fail() throws Exception {
        String newNickname = "¯\\_(ツ)_/¯";
        mockMvc.perform(post(ROOT + SETTINGS + ACCOUNT)
                .param("nickname", newNickname)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(SETTINGS + ACCOUNT))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("nicknameForm"));

    }


}