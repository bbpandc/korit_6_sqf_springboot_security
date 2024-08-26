package com.study.SpringSecurity.Test.dto.request;

import com.study.SpringSecurity.Test.domain.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Pattern;

@Data
public class ReqSignupDto {

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "사용자이름은 이메일 형식이여야합니다. ")
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$", message = "비밀번호는 8자이상 16자이하의 영대소문, 숫자를 포함하여야합니다.")
    private String password;
    private String checkPassword;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "이름은 영어여야합니다.")
    private String name;

    public User toEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
    }
}
