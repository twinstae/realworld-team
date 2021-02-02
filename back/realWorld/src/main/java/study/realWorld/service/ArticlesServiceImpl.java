package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.ArticleResponseDto;
import study.realWorld.entity.Articles;
import study.realWorld.repository.ArticlesRepository;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;

    @Override
    public ArticleResponseDto findBySlug(String slug) {
        Articles articles = articlesRepository.findOneBySlug(slug);

        return ArticleResponseDto.fromEntity(articles);
    }
}
