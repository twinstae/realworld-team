package study.realWorld.service;

import org.springframework.stereotype.Service;
import study.realWorld.api.dto.ArticleResponseDto;
import study.realWorld.entity.Articles;
import study.realWorld.repository.ArticlesRepository;

@Service
public class ArticlesServiceImpl implements ArticlesService {

    private ArticlesRepository articlesRepository;

    @Override
    public ArticleResponseDto findBySlug(String slug) {
        Articles articles = articlesRepository.findOneBySlug(slug);
        return ArticleResponseDto.fromEntity(articles);
    }
}
