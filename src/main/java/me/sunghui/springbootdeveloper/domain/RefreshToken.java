package me.sunghui.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 리프레시 토큰 도메인 구현
// 리프레시 토큰은 데이터베이스에 저장하는 정보이므로 엔티티와 리포지터리를 추가해야 함
// 엔티티와 매핑되는 테이블 구조
// 컬럼          자료형       null허용      키       설명
// id            BIGINT          N      기본키     일련번호, 기본키
// user_Id       BIGINT          N                유저 ID
// refresh_token varchar(255)    N                토큰값
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public RefreshToken(Long userId, String refreshToken){
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken){
        this.refreshToken = newRefreshToken;
        return this;
    }
}
