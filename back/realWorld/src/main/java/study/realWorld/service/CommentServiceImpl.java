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

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{


    private final ArticlesService articlesService;
    private final ProfilesService profileService;
    private final CommentRepository commentRepository;

    @Override
    public CommentListDto getComments(String slug) {

        return null;
    }

    @Override
    @Transactional
    public CommentDto save(String slug,CommentCreateDto commentCreateDto) {
        Articles article = articlesService.getArticleBySlugOr404(slug); // slug로 article 찾았다.
        Profile profile = profileService.getCurrentProfileOr404(); // 현재 내 프로필 찾았다.
        Comment comment = commentRepository.save(commentCreateDto.toEntity(profile,article)); //Comment 저장하겠다.

        return CommentDto.fromEntity(comment);
    }

}
