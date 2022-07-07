package com.rollin.config;

import com.rollin.model.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SecurityService {
    @Value("${jwt.secret_key}")
    String SECRET_KEY;
    @Value("${jwt.expTime}")
    long expTime;
    public String createToken(UserDto userDto){
        if(expTime<=0){
            throw new RuntimeException();
        }
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        log.info(SECRET_KEY);
        Map<String,Object> map = new HashMap<>();
        map.put("name",userDto.getName());
        map.put("img",userDto.getImg());
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signatureKey =
                new SecretKeySpec(
                        secretKeyBytes
                        ,signatureAlgorithm.getJcaName()
                );
        return Jwts.builder()
                .setClaims(map)
                .setSubject(userDto.getId().toString())
                .signWith(signatureKey,signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis()+expTime))
                .compact();
    }

    public String getSubject(String tokenBearer){

        String token = tokenBearer.substring("Bearer ".length());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public Integer getIdAtToken() {
        // header 에서 빼오는 거
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String id = request.getHeader("Authorization");
        return Integer.parseInt(getSubject(id));
    }

}