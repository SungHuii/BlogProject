package me.sunghui.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sunghui.springbootdeveloper.domain.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;

    private String content;

    // 작성자를 추가로 저장하기 위해 수정
    public Article toEntity(String author){ // 생성자를 사용해 객체 생성
        // 빌더 패턴을 사용해 DTO를 엔티티로 만들어주는 메서드
        // 추후에 블로그 글을 추가할 때 저장할 엔티티로 변환하는 용도
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
