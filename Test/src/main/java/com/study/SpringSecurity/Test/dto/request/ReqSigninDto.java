package com.study.SpringSecurity.Test.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqSigninDto {

    @NotBlank(message = "사용자이름을 입력하세요.")
    private String username;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}
