package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleListDto;
import study.realWorld.api.exception.NoAuthorizationException;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Profile;
import study.realWorld.repository.ArticlesRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final ProfilesService profileService;

    @Override
    @Transactional(readOnly = true)
    public ArticleListDto getPage(){
        List<ArticleDto> articleDtoList = articlesRepository.findAll().stream()
                .map(this::getArticleDtoFromArticlesAndProfile)
                .collect(Collectors.toList());
        long articlesCount = articlesRepository.count();

        return ArticleListDto.builder()
                .articles(articleDtoList)
                .articlesCount(articlesCount)
                .build();
    }

    private ArticleDto getArticleDtoFromArticlesAndProfile(Articles articles) {
        Profile profile = profileService.getCurrentProfileOr404();
        return ArticleDto.fromEntity(
                articles,
                profile.haveFavorited(articles),
                profile.isFollow(articles.getAuthor())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDto findBySlug(String slug) {
        Articles articles = getArticleBySlugOr404(slug);
        return getArticleDtoFromArticlesAndProfile(articles);
    }

    @Override
    @Transactional
    public void deleteBySlug(String slug) {
        Articles articles = getArticleBySlugOr404(slug);
        checkCurrentProfileIsTheAuthor(articles);

        articlesRepository.delete(articles);
    }

    @Override
    @Transactional
    public ArticleDto save(ArticleCreateDto articleCreateDto){
        Profile profile = profileService.getCurrentProfileOr404();
        Articles articles = articlesRepository.save(articleCreateDto.toEntity(profile));

        return getArticleDtoFromArticlesAndProfile(articles);
    }

    @Override
    @Transactional
    public ArticleDto updateArticleBySlug(String slug, ArticleCreateDto updateArticleDto) {
        Articles articles = getArticleBySlugOr404(slug);
        checkCurrentProfileIsTheAuthor(articles);

        articles.update(updateArticleDto);

        return getArticleDtoFromArticlesAndProfile(articles);
    }

    private void checkCurrentProfileIsTheAuthor(Articles articles) {
        Profile currentProfile = profileService.getCurrentProfileOr404();
        if (! articles.getAuthor().equals(currentProfile)){
            throw new NoAuthorizationException();
        }
    }
    @Override
    public Articles getArticleBySlugOr404(String slug) {
        return articlesRepository.findOneWithAuthorBySlug(slug)
                .orElseThrow(ResourceNotFoundException::new);
    }
    @Override
    public ArticleDto favoriteArticleBySlug(String slug) {
        Articles articles = getArticleBySlugOr404(slug); //일단 article을 찾는다.
        Profile profile = profileService.getCurrentProfileOr404();
        profile.favorite(articles);

        return getArticleDtoFromArticlesAndProfile(articles);
    }
}
