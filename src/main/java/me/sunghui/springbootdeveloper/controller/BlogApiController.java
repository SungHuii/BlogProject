package me.sunghui.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.sunghui.springbootdeveloper.domain.Article;
import me.sunghui.springbootdeveloper.dto.AddArticleRequest;
import me.sunghui.springbootdeveloper.dto.ArticleResponse;
import me.sunghui.springbootdeveloper.dto.UpdateArticleRequest;
import me.sunghui.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {
    private final BlogService blogService;


//    // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
//    @PostMapping("/api/articles")
//    // @RequestBody로 요청 본문 값 매핑
//    // 현재 인증 정보를 가져오는 principal 객체를 파라미터로 추가
//    // 인증 객체에서 유저 이름을 가져온 뒤 save() 메서드로 넘겨줌
//    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal){
//        Article savedArticle = blogService.save(request, principal.getName());
//        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
//    }
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal) {
        Article savedArticle = blogService.save(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }


    // /api/articles GET 요청이 오면 글 전체를 조회하는 findAll() 메서드를 호출한 다음
    // 응답용 객체인 ArticleResponse로 파싱해 body에 담아 클라이언트에게 전송
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        // stream 적용.
        // 여러 데이터가 모여있는 컬렉션을 간편하게 처리하기 위한
        // 자바 8에서 추가된 기능임

        return ResponseEntity.ok().body(articles);
    }

    // 글 하나 조회
    @GetMapping("/api/articles/{id}")
    // URL 경로에서 값 추출 ({id}에 해당하는 값이 id로 들어옴)
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        // PathVariable 애너테이션은 URL 에서 값을 가져오는 애너테이션이다.
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    // 글 삭제
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);

        return ResponseEntity.ok().build();
    }

    // 글 수정 메서드
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request){
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}