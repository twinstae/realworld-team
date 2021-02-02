package study.realWorld.service;

import study.realWorld.api.dto.ArticleDto;

public interface ArticlesService {
    ArticleDto findBySlug(String slug);
}
