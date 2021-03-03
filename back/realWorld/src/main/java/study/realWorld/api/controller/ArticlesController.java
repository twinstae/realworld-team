package study.realWorld.api.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleListDto;
import study.realWorld.api.dto.articleDtos.ArticleResponseDto;
import study.realWorld.api.dto.commentsDtos.CommentCreateDto;
import study.realWorld.api.dto.commentsDtos.CommentDto;
import study.realWorld.api.dto.commentsDtos.CommentListDto;
import study.realWorld.api.dto.commentsDtos.CommentResponseDto;
import study.realWorld.service.ArticlesService;

@Api(tags = {"1.Articles"})
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/articles")
public class ArticlesController {
    private final ArticlesService articlesService;

    @GetMapping
    public ResponseEntity<ArticleListDto> getArticles(){
        ArticleListDto articleListDto = articlesService.getPage();
        return ResponseEntity.ok(articleListDto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ArticleResponseDto> createArticle (
            @RequestBody ArticleCreateDto articleCreateDto
    ){
        ArticleDto articleDto = articlesService.create(articleCreateDto);

        return new ResponseEntity<>(
                new ArticleResponseDto(articleDto),
                HttpStatus.CREATED);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponseDto> getArticleBySlug (
            @PathVariable String slug
    ){
        ArticleDto articleDto = articlesService.findBySlug(slug);
        return ResponseEntity.ok(new ArticleResponseDto(articleDto));
    }

    @DeleteMapping("/{slug}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> deleteArticleBySlug(
            @PathVariable String slug
    ) {
        articlesService.deleteBySlug(slug);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{slug}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<ArticleResponseDto> updateArticle(
            @PathVariable String slug,
            @RequestBody ArticleCreateDto updateArticleDto
    ) {
        ArticleDto updatedArticleDto = articlesService.updateArticleBySlug(slug, updateArticleDto);

        return ResponseEntity.ok(new ArticleResponseDto(updatedArticleDto));
    }


    @PostMapping("/{slug}/favorite")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ArticleResponseDto> favoriteArticleBySlug(
            @PathVariable String slug
    ){
        ArticleDto articleDto = articlesService.favoriteArticleBySlug(slug);
        return new ResponseEntity<>(
                new ArticleResponseDto(articleDto),
                HttpStatus.OK);
    }

    @DeleteMapping("/{slug}/favorite")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ArticleResponseDto> unfavoriteArticleBySlug(
            @PathVariable String slug
    ){
        ArticleDto articleDto = articlesService.unfavoriteArticleBySlug(slug);
        return new ResponseEntity<>(
                new ArticleResponseDto(articleDto),
                HttpStatus.OK);
    }

    @GetMapping("/{slug}/comments")
    public ResponseEntity<CommentListDto> getCommentsBySlug(
            @PathVariable String slug
    ) {
        CommentListDto commentListDto = articlesService.getComments(slug);
        return ResponseEntity.ok(commentListDto);
    }

    @PostMapping("/{slug}/comments")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CommentResponseDto> addCommentToArticleBySlug(
            @PathVariable String slug,
            @RequestBody CommentCreateDto commentCreateDto
    ) {
        CommentDto commentDto = articlesService.addCommentToArticleBySlug(slug, commentCreateDto);
        return new ResponseEntity<>(
                new CommentResponseDto(commentDto),
                HttpStatus.CREATED);
    }
}

