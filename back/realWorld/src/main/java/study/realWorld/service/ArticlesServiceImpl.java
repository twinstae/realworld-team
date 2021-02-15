package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Articles;
import study.realWorld.entity.User;
import study.realWorld.repository.ArticlesRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final UserService userService;

    @Override
    public List<ArticleDto> getPage(){
        List<Articles> articlesList = articlesRepository.findAll();

        return articlesList.stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ArticleDto findBySlug(String slug) {
        Articles articles = getArticleBySlugOr404(slug);
        return ArticleDto.fromEntity(articles);
    }

    @Override
    @Transactional
    public void deleteBySlug(String slug) {
        Articles articles = getArticleBySlugOr404(slug);
        articlesRepository.delete(articles);
    }

    @Transactional
    @Override
    public ArticleDto save(ArticleCreateDto articleCreateDto){
        Optional<User> user = userService.getMyUserWithAuthorities();
        if(user.isPresent()) {
            Articles articles = articlesRepository.save(articleCreateDto.toEntity(user));
            return ArticleDto.fromEntity(articles);
        } else {

        }
    }

    @Transactional
    @Override
    public ArticleDto updateArticleBySlug(String slug,ArticleCreateDto updateArticleDto) {
        Articles articles = getArticleBySlugOr404(slug);
        articles.update(updateArticleDto);
        return ArticleDto.fromEntity(articles);
    }

    private Articles getArticleBySlugOr404(String slug) {
        return articlesRepository.findOneBySlug(slug)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
