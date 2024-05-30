package me.sunghui.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.sunghui.springbootdeveloper.domain.Article;
import me.sunghui.springbootdeveloper.dto.ArticleListViewResponse;
import me.sunghui.springbootdeveloper.dto.ArticleViewResponse;
import me.sunghui.springbootdeveloper.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles); // 블로그 글 리스트 저장

        return "articleList"; // articleList.html이라는 뷰 조회
    }

    // 블로그 글을 반환할 메서드
    // 인자 id에 URL로 넘어온 값을 받아 findById() 메서드로 넘겨 글을 조회하고,
    // 화면에서 사용할 모델에 데이터를 저장한 다음, 보여줄화면의 템플릿 이름을 반환한다.
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
