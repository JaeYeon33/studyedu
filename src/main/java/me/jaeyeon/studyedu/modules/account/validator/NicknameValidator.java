package me.jaeyeon.studyedu.modules.account.validator;

import lombok.RequiredArgsConstructor;
import me.jaeyeon.studyedu.modules.account.Account;
import me.jaeyeon.studyedu.modules.account.AccountRepository;
import me.jaeyeon.studyedu.modules.account.form.NicknameForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class NicknameValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NicknameForm nicknameForm = (NicknameForm) target;
        Account byNickname = accountRepository.findByNickname(nicknameForm.getNickname());
        if (byNickname != null) {
            errors.rejectValue("nickname", "wrong.value", "입력하신 닉네임은 사용할 수 없습니다.");
        }
    }
}
