package study.realWorld.api.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import study.realWorld.api.dto.commentsDtos.CommentCreateDto;
import study.realWorld.api.dto.commentsDtos.CommentDto;
import study.realWorld.api.dto.commentsDtos.CommentListDto;
import study.realWorld.api.dto.commentsDtos.CommentResponseDto;
import study.realWorld.service.CommentService;
import study.realWorld.service.CommentServiceImpl;

@Api(tags = {"Comment"})
@RequiredArgsConstructor
@RestController
@RequestMapping(ArticlesController.API_ARTICLES_URL+"/{slug}/comments")
public class CommentController{
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<CommentListDto> getArticlesCommentsBySlug(
            @PathVariable String slug
    ) {
        CommentListDto commentListDto = commentService.getComments(slug);
        return ResponseEntity.ok(commentListDto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CommentResponseDto> addCommentToArticleBySlug(
            @PathVariable String slug,
            @RequestBody CommentCreateDto commentCreateDto
    ) {
        CommentDto commentDto = commentService.addCommentToArticleBySlug(slug, commentCreateDto);
        return new ResponseEntity<>(
                new CommentResponseDto(commentDto),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{comment_id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> deleteArticleBySlug(
            @PathVariable String slug,
            @PathVariable Long comment_id
    ) {
        commentService.deleteBySlugAndCommentId(slug,comment_id);
        return ResponseEntity.noContent().build();
    }
}
