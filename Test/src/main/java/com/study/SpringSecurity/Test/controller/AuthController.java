package com.study.SpringSecurity.Test.controller;

import com.study.SpringSecurity.Test.aspect.annotation.ValidAop;
import com.study.SpringSecurity.Test.dto.request.ReqSigninDto;
import com.study.SpringSecurity.Test.dto.request.ReqSignupDto;
import com.study.SpringSecurity.Test.service.SigninService;
import com.study.SpringSecurity.Test.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SigninService signinService;

    @Autowired
    private SignupService signupService;

    @ValidAop
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody ReqSigninDto dto, BindingResult bindingResult) {
        return ResponseEntity.ok().body(signinService.signin(dto));
    }

    @ValidAop
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody ReqSignupDto dto, BindingResult bindingResult) {
        return ResponseEntity.created(null).body(signupService.signup(dto));
    }

}
