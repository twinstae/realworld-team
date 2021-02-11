package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Articles;
import study.realWorld.repository.ArticlesRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;

    @Override
    public List<ArticleDto> getPage(){
        List<Articles> articlesList = articlesRepository.findAll();

        return articlesList.stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

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
    public ArticleDto updateArticleBySlug(String slug,ArticleCreateDto updateArticleDto) {

        Articles articles = getArticleBySlugOr404(slug);
        articles.update(updateArticleDto);

        return ArticleDto.fromEntity(articles);
    }

    protected Articles getArticleBySlugOr404(String slug) {
        return articlesRepository.findOneBySlug(slug)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
