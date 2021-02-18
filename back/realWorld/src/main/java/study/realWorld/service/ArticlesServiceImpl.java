package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.articleDtos.ArticleCreateDto;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.articleDtos.ArticleListDto;
import study.realWorld.api.exception.NoAuthorizationException;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Articles;
import study.realWorld.entity.User;
import study.realWorld.repository.ArticlesRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final UserService userService;

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
        return ArticleDto.fromEntity(articles);
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
        return ArticleDto.fromEntity(articles);
    }

    @Transactional
    @Override
    public ArticleDto updateArticleBySlug(String slug, ArticleCreateDto updateArticleDto) {
        Articles articles = getArticleBySlugOr404(slug);
        checkCurrentUserIsTheAuthor(articles);

        articles.update(updateArticleDto);
        return ArticleDto.fromEntity(articles);
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
}
