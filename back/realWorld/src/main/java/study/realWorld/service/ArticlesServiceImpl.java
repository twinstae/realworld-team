package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleListDto;
import study.realWorld.api.exception.NoAuthorizationException;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Favorite;
import study.realWorld.entity.Profile;
import study.realWorld.entity.User;
import study.realWorld.repository.ArticlesRepository;

import org.springframework.transaction.annotation.Transactional;
import study.realWorld.repository.FavoriteRepository;
import study.realWorld.repository.ProfilesRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final UserService userService;
    private final ProfilesRepository profilesRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    @Transactional(readOnly = true)
    public ArticleListDto getPage(){
        List<ArticleDto> articleDtoList = articlesRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
        long articlesCount = articlesRepository.count();

        return ArticleListDto.builder()
                .articles(articleDtoList)
                .articlesCount(articlesCount)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDto findBySlug(String slug) {
        Articles articles = getArticleBySlugOr404(slug);
        return ArticleDto.fromEntity(articles,articles,favorited,favoriteCount);
    }

    @Override
    @Transactional
    public void deleteBySlug(String slug) {
        Articles articles = getArticleBySlugOr404(slug);
        checkCurrentUserIsTheAuthor(articles);

        articlesRepository.delete(articles);
    }

    @Transactional
    @Override
    public ArticleDto save(ArticleCreateDto articleCreateDto){
        User currentUser = userService.getMyUser();
        Articles articles = articlesRepository.save(articleCreateDto.toEntity(currentUser));
        return ArticleDto.fromEntity(articles,articles,favorited,favoriteCount);
    }

    @Transactional
    @Override
    public ArticleDto updateArticleBySlug(String slug, ArticleCreateDto updateArticleDto) {
        Articles articles = getArticleBySlugOr404(slug);
        checkCurrentUserIsTheAuthor(articles);

        articles.update(updateArticleDto);
        long favoriteCount = articles.getFavoriteList().size();
        boolean favorited = profile.isFavorited(articles);
        return ArticleDto.fromEntity(articles,articles,favorited,favoriteCount);
    }

    private void checkCurrentUserIsTheAuthor(Articles articles) {
        User currentUser = userService.getMyUser();
        if (! articles.getAuthor().equals(currentUser)){
            throw new NoAuthorizationException();
        }
    }

    private Articles getArticleBySlugOr404(String slug) {
        return articlesRepository.findOneWithAuthorBySlug(slug)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public ArticleDto addFavoriteArticle(String slug) {
        Articles articles = getArticleBySlugOr404(slug); //일단 article을 찾는다.
        User currentUser = userService.getMyUser(); // 로그인되어 있는 user를 찾는다.
        Profile profile =profilesRepository.findOneByUsername(currentUser.getUserName())
                .orElseThrow(ResourceNotFoundException::new); // 로그인 되어 있는 유저의 Profile을 찾는다.
        Favorite favorite = new Favorite(profile,articles); // 찾아놓은 article과 profile에 favorite을 생성한다.
        profile.addFavorite(favorite);
        articles.addFavorite(favorite);
        long favoriteCount = articles.getFavoriteList().size();
        boolean favorited = profile.isFavorited(articles);

        return ArticleDto.fromEntity(articles,favorited,favoriteCount);
    }

}
