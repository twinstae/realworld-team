package study.realWorld.api.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleListDto;
import study.realWorld.api.dto.articleDtos.ArticleResponseDto;
import study.realWorld.service.ArticlesService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/articles")
public class ArticlesController {

    private final ArticlesService articlesService;

    @GetMapping
    public ResponseEntity<ArticleListDto> getArticles(){
        List<ArticleDto> articleDtoList = articlesService.getPage();

        return ResponseEntity.ok(new ArticleListDto(articleDtoList));
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(
            @RequestBody ArticleCreateDto articleCreateDto
    ){
        ArticleDto articleDto = articlesService.save(articleCreateDto);

        return new ResponseEntity<>(
                new ArticleResponseDto(articleDto),
                HttpStatus.CREATED);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponseDto> getArticleBySlug(
            @PathVariable String slug
    ){
        ArticleDto articleDto = articlesService.findBySlug(slug);
        return ResponseEntity.ok(new ArticleResponseDto(articleDto));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<?> deleteArticleBySlug(
            @PathVariable String slug
    ) {
        articlesService.deleteBySlug(slug);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{slug}")
    public ResponseEntity<ArticleResponseDto> updateArticle(
            @PathVariable String slug,
            @RequestBody ArticleCreateDto updateArticleDto
    ) {
        ArticleDto updatedArticleDto = articlesService.updateArticleBySlug(slug, updateArticleDto);

        return ResponseEntity.ok(new ArticleResponseDto(updatedArticleDto));
    }
}