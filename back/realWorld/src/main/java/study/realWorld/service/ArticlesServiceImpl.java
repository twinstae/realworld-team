package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.entity.Articles;
import study.realWorld.repository.ArticlesRepository;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;

    @Override
    public ArticleDto findBySlug(String slug) {
        Articles articles = articlesRepository.findOneBySlug(slug);

        return ArticleDto.fromEntity(articles);
    }

    @Override
    public void deleteBySlug(String slug) {
        Articles articles = articlesRepository.findOneBySlug(slug);
        articlesRepository.delete(articles);
    }
}
