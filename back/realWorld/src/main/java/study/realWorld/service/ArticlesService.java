package study.realWorld.service;

import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.dto.UpdateArticleDto;

public interface ArticlesService {
    ArticleDto findBySlug(String slug);

    void deleteBySlug(String slug);

    ArticleDto save(ArticleCreateDto articleCreateDto);

    ArticleDto updateArticle(ArticleDto articleDto, UpdateArticleDto updateArticleDto);
}
