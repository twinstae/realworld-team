package study.realWorld.api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.dto.ArticleListDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.dto.ArticleResponseDto;
import study.realWorld.service.ArticlesService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/articles")
public class ArticlesController {

    private final ArticlesService articlesService;

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

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponseDto> getArticleBySlug(@PathVariable String slug){
        ArticleDto articleDto = articlesService.findBySlug(slug);
        return ResponseEntity.ok(new ArticleResponseDto(articleDto));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<?> deleteArticleBySlug(@PathVariable String slug) {
        articlesService.deleteBySlug(slug);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(
            @RequestBody ArticleCreateDto articleCreateDto
            ){
        ArticleDto articleDto = articlesService.save(articleCreateDto);

        return new ResponseEntity<>(
                new ArticleResponseDto(articleDto),
                new HttpHeaders(),
                HttpStatus.CREATED);
    }

}