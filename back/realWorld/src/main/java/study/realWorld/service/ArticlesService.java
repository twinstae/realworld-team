package study.realWorld.service;

import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.dto.ArticleDto;

public interface ArticlesService {
    ArticleDto findBySlug(String slug);

    void deleteBySlug(String slug);

    ArticleDto save(ArticleCreateDto articleCreateDto);
}
