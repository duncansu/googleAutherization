package com.example.demo.lib;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JWTUtil {

    private String secretKey="duncansuduncansuduncansuduncansuduncansuduncansuduncansuduncansu";
    private int lifeTime;

    public String Sign(String userId,String email) {
        Claims claims = Jwts.claims();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, lifeTime);
        claims.setExpiration(calendar.getTime());
        claims.put("userId", userId);
        claims.put("email",email);

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder().setClaims(claims).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public Map<String,String> Verify(String token) {
        String userId;
        Map<String,String>userId1=new HashMap<>();
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            userId1.put("userid",claims.get("userId").toString());
            userId1.put("useremail",claims.get("email").toString());
        } catch (Exception e) {
            userId1 = null;

        }
        return userId1;
    }
}
