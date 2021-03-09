package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.commentsDtos.CommentCreateDto;
import study.realWorld.api.dto.commentsDtos.CommentDto;
import study.realWorld.api.dto.commentsDtos.CommentListDto;
import study.realWorld.api.exception.NoAuthorizationException;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Comment;
import study.realWorld.entity.Profile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final ArticlesService articlesService;
    private final ProfilesService profilesService;

    @Override
    @Transactional(readOnly = true)
    public CommentListDto getComments(String slug) {
        Articles articles = articlesService.getArticleBySlugOr404(slug);
        Optional<Profile> currentProfile = profilesService.getCurrentProfile(); // 현재 내 프로필 찾았다.

        List<CommentDto> commentDtoList = articles.getComments().stream()
                .map(comment ->{
                    boolean isFollowing =
                            currentProfile.map(current->current.isFollow(comment.getAuthor()))
                                    .orElse(false);
                    return CommentDto.fromEntity(comment, isFollowing);
                })
                .collect(Collectors.toList());

        return CommentListDto.builder()
                .comments(commentDtoList)
                .build();
    }

    @Override
    @Transactional
    public CommentDto addCommentToArticleBySlug(String slug, CommentCreateDto commentCreateDto) {
        Articles article = articlesService.getArticleBySlugOr404(slug);
        Profile currentProfile = profilesService.getCurrentProfileOr404();

        Comment comment = commentCreateDto.toEntity(currentProfile, article);

        return CommentDto.fromEntity(comment, false);
    }

    @Override
    @Transactional
    public void deleteBySlugAndCommentId(String slug, Long commentId) {
        Comment comment = articlesService.getArticleBySlugOr404(slug).getCommentById(commentId);
        Profile currentProfile = profilesService.getCurrentProfileOr404();
        checkProfileIsTheAuthor(currentProfile, comment);
        comment.delete();
    }

    private void checkProfileIsTheAuthor(Profile currentProfile, Comment comment) {
        if (!currentProfile.isCommentedBy(comment)){
            throw new NoAuthorizationException();
        }
    }
}
