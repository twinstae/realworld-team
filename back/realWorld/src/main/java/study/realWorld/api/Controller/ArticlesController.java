package study.realWorld.api.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.realWorld.api.dto.ArticleListDto;
import study.realWorld.api.dto.ArticleDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/articles")
public class ArticlesController {


    @GetMapping
    public ResponseEntity<ArticleListDto> getArticles(){
        ArticleDto article1 = ArticleDto
                .builder()
                .title("제목")
                .description("개요")
                .body("내용")
                .build();
        ArticleDto article2 = ArticleDto
                .builder()
                .title("title")
                .description("description")
                .body("body")
                .build();

        List<ArticleDto> data = new ArrayList<ArticleDto>();

        data.add(article1);
        data.add(article2);

        return ResponseEntity.ok(new ArticleListDto(data));
    }

    @GetMapping
    public ResponseEntity<> getArticleBySlug(){

    }
}