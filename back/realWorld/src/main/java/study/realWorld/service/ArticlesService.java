package study.realWorld.service;

import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;

import java.util.List;

public interface ArticlesService {
    List<ArticleDto> getPage();

    ArticleDto findBySlug(String slug);

    void deleteBySlug(String slug);

    ArticleDto save(ArticleCreateDto articleCreateDto);

    ArticleDto updateArticleBySlug(String slug, ArticleCreateDto updateArticleDto);
}
