package study.realWorld.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.realWorld.TestingUtil;
import study.realWorld.api.dto.commentsDtos.CommentDto;
import study.realWorld.api.dto.commentsDtos.CommentListDto;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Comment;
import study.realWorld.entity.Profile;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CommentServiceTest extends TestingUtil {


    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    ProfilesService profilesService;

    @Autowired
    ArticlesService articlesService;

    @DisplayName("slug를 이용하여 article을 찾고, 거기에 comment를 추가하고, CommentDto를 반환한다.")
    @Test
    public void addCommentToArticleBySlugTest() throws Exception {

        createUserAndArticleInit(); // 유저 생성, article 생성

        CommentDto commentResponseDto = commentService.addCommentToArticleBySlug(createDto.getSlug(),commentCreateDto);

        assertThat(commentResponseDto.getBody()).isEqualTo("이 글은 참 좋군요.");
        assertThat(commentResponseDto.getAuthor().isFollowing()).isFalse();
    }


    @DisplayName("slug를 이용하여 article을 찾고, 해당 article들을 List로 가져온다. ")
    @Test
    public void getCommentsTest() throws Exception {
        createUserAndArticleInit(); // 유저 생성, article 생성

        commentService.addCommentToArticleBySlug(createDto.getSlug(),commentCreateDto);
       commentService.addCommentToArticleBySlug(createDto.getSlug(),commentCreateDto2);

        CommentListDto responseCommentListDto = commentService.getComments(createDto.getSlug());

        assertThat(responseCommentListDto.getComments().size()).isEqualTo(2);
        assertThat(responseCommentListDto.getComments().get(0).getBody()).isEqualTo("이 글은 참 좋군요.");
        assertThat(responseCommentListDto.getComments().get(1).getBody()).isEqualTo("안좋아요");
    }

    @Test
    public void deleteBySlugAndCommentIdTest() throws Exception {
        createUserAndArticleInit(); // 유저 생성, article 생성
        commentService.addCommentToArticleBySlug(createDto.getSlug(),commentCreateDto);//댓글 생성

        CommentDto commentDto = commentService.getComments(createDto.getSlug()).getComments().get(0);

        commentService.deleteBySlugAndCommentId(createDto.getSlug(), commentDto.getId());

        CommentListDto responseCommentListDto = commentService.getComments(createDto.getSlug());
        assertThat(responseCommentListDto.getComments().isEmpty());
    }


}
