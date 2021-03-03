package study.realWorld.service;

import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleListDto;
import study.realWorld.api.dto.commentsDtos.CommentCreateDto;
import study.realWorld.api.dto.commentsDtos.CommentDto;
import study.realWorld.api.dto.commentsDtos.CommentListDto;
import study.realWorld.entity.Articles;

import java.util.List;

public interface ArticlesService {
    ArticleListDto getPage();

    ArticleDto findBySlug(String slug);

    void deleteBySlug(String slug);

    ArticleDto create(ArticleCreateDto articleCreateDto);

    ArticleDto updateArticleBySlug(String slug, ArticleCreateDto updateArticleDto);

    Articles getArticleBySlugOr404(String slug);

    ArticleDto favoriteArticleBySlug(String slug);
    ArticleDto unfavoriteArticleBySlug(String slug);

    @Transactional(readOnly = true)
    CommentListDto getComments(String slug);

    @Transactional
    CommentDto addCommentToArticleBySlug(String slug, CommentCreateDto commentCreateDto);
}
