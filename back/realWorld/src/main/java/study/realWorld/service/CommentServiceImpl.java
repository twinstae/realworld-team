package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.commentsDtos.CommentCreateDto;
import study.realWorld.api.dto.commentsDtos.CommentDto;
import study.realWorld.api.dto.commentsDtos.CommentListDto;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Comment;
import study.realWorld.entity.Profile;
import study.realWorld.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{


    private final ArticlesService articlesService;
    private final ProfilesService profileService;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public CommentListDto getComments(String slug) {

        Articles articles = articlesService.getArticleBySlugOr404(slug);
        Profile profile = profileService.getCurrentProfileOr404(); // 현재 내 프로필 찾았다.
        ArticleDto articleDto =getArticleDtoFromArticlesAndProfile(articles, Optional.of(profile));

        List<CommentDto> commentDtoList = articles.getComments().stream()
                .map(comment ->
                        CommentDto.fromEntity(comment,articleDto.getAuthor().isFollowing()))
                .collect(Collectors.toList());

        return CommentListDto.builder()
                .comments(commentDtoList)
                .build();
    }



    @Override
    @Transactional
    public CommentDto save(String slug,CommentCreateDto commentCreateDto) {
        Articles article = articlesService.getArticleBySlugOr404(slug); // slug로 article 찾았다.
        Profile profile = profileService.getCurrentProfileOr404(); // 현재 내 프로필 찾았다.
        Comment comment = commentRepository.save(commentCreateDto.toEntity(profile,article)); //Comment 저장하겠다.

        ArticleDto articleDto =getArticleDtoFromArticlesAndProfile(article, Optional.of(profile));
        return CommentDto.fromEntity(comment,articleDto.getAuthor().isFollowing());
    }

    private ArticleDto getArticleDtoFromArticlesAndProfile(Articles articles, Optional<Profile> optionalProfile) {
        return optionalProfile.map((currentProfile)->{
            boolean isFollow = profileService.isFollow(currentProfile, articles.getAuthor());
            boolean favorited = profileService.haveFavorited(currentProfile, articles);

            return ArticleDto.fromEntity(articles, isFollow, favorited);
        }).orElse(ArticleDto.fromEntity(articles, false, false));
    }


}
