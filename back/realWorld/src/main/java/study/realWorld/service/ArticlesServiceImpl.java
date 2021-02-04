package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.ArticleCreateDto;
import study.realWorld.api.dto.ArticleDto;
import study.realWorld.api.dto.UpdateArticleDto;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Articles;
import study.realWorld.repository.ArticlesRepository;

import javax.transaction.Transactional;

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

    @Transactional
    public ArticleDto save(ArticleCreateDto articleCreateDto){
        Articles articles = articlesRepository.save(articleCreateDto.toEntity());
        return ArticleDto.fromEntity(articles);
    }

    @Transactional
    public ArticleDto updateArticle(ArticleDto articleDto,UpdateArticleDto updateArticleDto) {

        UpdateArticleDto updatedArticleDto = UpdateArticleDto
                .builder()
                .slug(articleDto.getSlug())
                .title(updateArticleDto.getTitle())
                .description(updateArticleDto.getDescription())
                .body(updateArticleDto.getBody())
                .build();

        Articles articles = articlesRepository.save(updatedArticleDto.toEntity());

        return ArticleDto.fromEntity(articles);
    }
}
