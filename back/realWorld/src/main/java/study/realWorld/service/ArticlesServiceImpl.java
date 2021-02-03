package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Articles;
import study.realWorld.repository.ArticlesRepository;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;

    @Override
    public ArticleDto findBySlug(String slug) {
        Articles articles = articlesRepository.findOneBySlug(slug).orElseThrow(ResourceNotFoundException::new);

        return ArticleDto.fromEntity(articles);
    }

    @Override
    public void deleteBySlug(String slug) {
        Articles articles = articlesRepository.findOneBySlug(slug).orElseThrow(ResourceNotFoundException::new);
        articlesRepository.delete(articles);
    }
}
