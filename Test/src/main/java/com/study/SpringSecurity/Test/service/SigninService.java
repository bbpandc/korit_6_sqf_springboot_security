package com.study.SpringSecurity.Test.service;

import com.study.SpringSecurity.Test.domain.entity.User;
import com.study.SpringSecurity.Test.dto.request.ReqSigninDto;
import com.study.SpringSecurity.Test.dto.response.RespJwtDto;
import com.study.SpringSecurity.Test.repository.UserRepository;
import com.study.SpringSecurity.Test.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SigninService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public RespJwtDto signin(ReqSigninDto dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("사용자 정보를 다시 입력하세요.")
        );
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("사용자 정보를 다시 입력하세요.");
        }
        return RespJwtDto.builder().accessToken(jwtProvider.generateUserToken(user)).build();
    }
}
