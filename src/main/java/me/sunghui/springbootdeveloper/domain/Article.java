package me.sunghui.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // 엔티티의 생성 및 수정 시간을 자동으로 감시하고 기록
@Entity // 엔티티로 지정
@Getter // getter 애너테이션을 통해 클래스 필드에 대해 별도 코드없이 모든 필드에 대한 접근자 메서드 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 접근제어자가 protected인 기본 생성자 생성
public class Article {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false) // 'title' 이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    // 글에 글쓴이 추가
    @Column(name = "author", nullable = false)
    private String author;

    @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // lombok 에서 지원하는 애너테이션
    // 생성자 위에 애너테이션 입력시 빌더 패턴 방식으로 객체 생성 가능
    @Builder // 빌더 패턴으로 객체 생성
    public Article(String author, String title, String content){
        this.author = author;
        this.title = title;
        this.content = content;
    }

    // 엔티티에 요청받은 내용으로 값을 수정하는 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
