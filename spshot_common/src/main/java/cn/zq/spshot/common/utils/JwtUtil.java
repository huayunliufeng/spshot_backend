package cn.zq.spshot.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author 华韵流风
 * @date 2018/4/11
 */
@Component
@ConfigurationProperties("jwt.config")
@EnableConfigurationProperties
public class JwtUtil {

    private String key;

    private long ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JWT
     *
     * @param id      id
     * @param subject subject
     * @return String
     */
    public String createJwt(String id, String subject, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key).claim("roles", roles);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtStr jwtStr
     * @return Claims
     */
    public Claims parseJwt(String jwtStr) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtStr).getBody();
            Date expiration = claims.getExpiration();
            if (expiration.before(Calendar.getInstance().getTime())) {
                return null;
            }
        } catch (Exception e) {
            claims = null;
            e.printStackTrace();
        }
        return claims;
    }

}
