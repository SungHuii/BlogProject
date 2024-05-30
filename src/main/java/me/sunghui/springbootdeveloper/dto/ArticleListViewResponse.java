package me.sunghui.springbootdeveloper.dto;

import lombok.Getter;
import me.sunghui.springbootdeveloper.domain.Article;


@Getter
public class ArticleListViewResponse {

    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article){
        this.id = article.getId();
        System.out.println("id: " + id);
        this.title = article.getTitle();
        System.out.println("title: " + title);
        this.content = article.getContent();
        System.out.println("content: " + content);
    }
}
