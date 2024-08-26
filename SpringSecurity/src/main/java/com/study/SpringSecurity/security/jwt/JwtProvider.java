package com.study.SpringSecurity.security.jwt;

import com.study.SpringSecurity.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;

    // 매개변수 secret: application.yml의 jwt.secret의 값
    public JwtProvider(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)); // secret을 key값으로 바꿈(암호화 후)
    }

    public String removeBearer(String token) {
        return token.substring("Bearer ".length());
    }

    public String generateUserToken(User user) {
        Date expireDate = new Date(new Date().getTime() + (1000l * 60 * 60 * 24 * 30));

        String token = Jwts.builder()
                .claim("userId", user.getId()) // key, value
                .expiration(expireDate) // 만료시간(30일)
                .signWith(key, SignatureAlgorithm.HS256) // key값으로 서명, 토큰을 어떤 알고리즘으로 암호화할지 선택
                .compact();

        return token;
    }

    public Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(key)
                .build();

        return jwtParser.parseClaimsJws(token).getPayload();

    }
}
