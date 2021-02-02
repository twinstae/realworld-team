package study.realWorld.service;

import study.realWorld.api.dto.ArticleResponseDto;

public interface ArticlesService {

    ArticleResponseDto findBySlug(String slug);
}
