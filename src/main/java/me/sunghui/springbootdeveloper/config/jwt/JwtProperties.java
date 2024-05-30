package me.sunghui.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // 자바 클래스에 프로퍼티값을 가져와서 사용하는 애너테이션
public class JwtProperties {
    // issuer 필드에는 application.yml에서 설정한 jwt.issuer 값이,
    // secretKey 에는 jwt.secret_key 값이 매핑됨
    private String issuer;
    private String secretKey;
}
