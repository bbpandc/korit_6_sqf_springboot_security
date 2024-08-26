package com.study.SpringSecurity.Test.service;

import com.study.SpringSecurity.Test.domain.entity.Role;
import com.study.SpringSecurity.Test.domain.entity.User;
import com.study.SpringSecurity.Test.dto.request.ReqSignupDto;
import com.study.SpringSecurity.Test.repository.RoleRepository;
import com.study.SpringSecurity.Test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User signup(ReqSignupDto reqSignupDto) {
        User user = reqSignupDto.toEntity(passwordEncoder);
        Role role = roleRepository.findByName("ROLE_USER").orElseGet(
                () -> roleRepository.save(Role.builder().name("ROLE_USER").build())
        );
        user.setRoles(Set.of(role));
        user = userRepository.save(user);

        return user;
    }

    public boolean isDuplicatedUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
