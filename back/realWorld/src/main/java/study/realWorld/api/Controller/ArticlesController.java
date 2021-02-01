package study.realWorld.api.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.realWorld.api.dto.ArticleListDto;
import study.realWorld.api.dto.ArticleResponseDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticlesController {

    @GetMapping(path = "/api/articles")
    public ResponseEntity getArticles(){
        ArticleResponseDto article1 = ArticleResponseDto
                .builder()
                .title("제목")
                .description("개요")
                .body("내용")
                .build();
        ArticleResponseDto article2 = ArticleResponseDto
                .builder()
                .title("title")
                .description("description")
                .body("body")
                .build();

        List<ArticleResponseDto> data = new ArrayList<ArticleResponseDto>();

        data.add(article1);
        data.add(article2);

        return ResponseEntity.ok(new ArticleListDto(data));
    }
}