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
import java.util.Optional;
import java.util.function.BiConsumer;
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
                .map(articles ->
                        getArticleDtoFromArticlesAndProfile(articles, Optional.empty()))
                .collect(Collectors.toList());
        long articlesCount = articlesRepository.count();

        return ArticleListDto.builder()
                .articles(articleDtoList)
                .articlesCount(articlesCount)
                .build();
    }

    private ArticleDto getArticleDtoFromArticlesAndProfile(Articles articles, Optional<Profile> optionalProfile) {
        return optionalProfile.map((currentProfile)->{
            boolean isFollow = profileService.isFollow(currentProfile, articles.getAuthor());
            boolean favorited = profileService.haveFavorited(currentProfile, articles);

            return ArticleDto.fromEntity(articles, isFollow, favorited);
        }).orElse(ArticleDto.fromEntity(articles, false, false));
    }


    private ArticleDto getArticleDtoBySlugThenStrategy(String slug, BiConsumer<Profile, Articles> strategy){
        Articles articles = getArticleBySlugOr404(slug);
        Optional<Profile> currentProfile = profileService.getCurrentProfile();

        currentProfile.ifPresent(profile -> strategy.accept(profile, articles));

        return getArticleDtoFromArticlesAndProfile(articles, currentProfile);
    }

    @Override
    public Articles getArticleBySlugOr404(String slug) {
        return articlesRepository.findOneWithAuthorBySlug(slug)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDto findBySlug(String slug) {
        return getArticleDtoBySlugThenStrategy(slug, (profile, articles) -> {});
    }

    @Override
    @Transactional
    public void deleteBySlug(String slug) {
        getArticleDtoBySlugThenStrategy(slug,
                (currentProfile, articles)->{
            checkProfileIsArticlesAuthor(currentProfile, articles);
            articlesRepository.delete(articles);
        });
    }

    @Override
    @Transactional
    public ArticleDto create(ArticleCreateDto articleCreateDto){
        Profile profile = profileService.getCurrentProfileOr404();
        Articles articles = articlesRepository.save(articleCreateDto.toEntity(profile));
        return getArticleDtoFromArticlesAndProfile(articles, Optional.of(profile));
    }

    @Override
    @Transactional
    public ArticleDto updateArticleBySlug(String slug, ArticleCreateDto updateArticleDto) {
        return getArticleDtoBySlugThenStrategy(slug,
                (currentProfile, articles)->{
            checkProfileIsArticlesAuthor(currentProfile, articles);
            articles.update(updateArticleDto);
        });
    }

    private void checkProfileIsArticlesAuthor(Profile currentProfile, Articles articles) {
        if (! articles.getAuthor().equals(currentProfile)){
            throw new NoAuthorizationException();
        }
    }

    @Override
    @Transactional
    public ArticleDto favoriteArticleBySlug(String slug) {
        return getArticleDtoBySlugThenStrategy(slug, Profile::favorite).afterFavorite();
    }

    @Override
    @Transactional
    public ArticleDto unfavoriteArticleBySlug(String slug) {
        return getArticleDtoBySlugThenStrategy(slug, Profile::unfavorite).afterUnFavorite();
    }
}
