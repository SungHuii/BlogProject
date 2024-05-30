//package me.sunghui.springbootdeveloper.service;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import me.sunghui.springbootdeveloper.domain.Article;
//import me.sunghui.springbootdeveloper.dto.AddArticleRequest;
//import me.sunghui.springbootdeveloper.dto.UpdateArticleRequest;
//import me.sunghui.springbootdeveloper.repository.BlogRepository;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
//@Service // 빈으로 등록
//public class BlogService {
//    private final BlogRepository blogRepository;
//
//    // 블로그 글 추가 메서드
//    // 수정 : 유저 이름을 추가로 입력받고 toEntity() 의 인수로 전달받은
//    // 유저 이름을 반환하도록 수정함
//    public Article save(AddArticleRequest request, String userName) {
//        return blogRepository.save(request.toEntity(userName));
//    }
//
//    // 블로그 글 전체 조회
//    public List<Article> findAll() {
//        return blogRepository.findAll();
//        // JPA 지원 메서드 findAll() 호출해 article 테이블에 저장되어 있는
//        // 모든 데이터 조회
//    }
//
//    // 블로그 글 하나 조회
//    // 엔티티를 조회하고 없으면 IllegalArgumentException 예외를 발생함
//    public Article findById(long id) {
//        return blogRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
//    }
//
//    // 블로그 글 삭제
//    // 본인 글이 아닌데 삭제를 시도하는 경우 예외 발생시키도록 코드 수정
//    public void delete(long id) {
//        Article article = blogRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
//    }
//
//    // 블로그 글 수정
//    @Transactional // 트랜잭션 메서드.
//    // 트랜잭션 : 데이터베이스의 데이터를 바꾸기 위해 묶은 작업의 단위
//    // 매칭한 메서드를 하나의 트랜잭션으로 묶는 역할
//    // 본인 글이 아닌데 수정을 시도하는 경우 예외 발생시키도록 코드 수정
//    public Article update(long id, UpdateArticleRequest request){
//        Article article = blogRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
//
//        authorizeArticleAuthor(article); // 게시글 작성한 유저인지 확인
//        article.update(request.getTitle(), request.getContent());
//
//        return article;
//    }
//
//    // 게시글을 작성한 유저인지 확인하는 메서드
//    private static void authorizeArticleAuthor(Article article){
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        if(!article.getAuthor().equals(userName)) {
//            throw new IllegalArgumentException("not authorized");
//        }
//    }
//}

package me.sunghui.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.sunghui.springbootdeveloper.domain.Article;
import me.sunghui.springbootdeveloper.dto.AddArticleRequest;
import me.sunghui.springbootdeveloper.dto.UpdateArticleRequest;
import me.sunghui.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}