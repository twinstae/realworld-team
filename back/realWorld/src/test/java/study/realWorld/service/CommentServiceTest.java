package study.realWorld.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import study.realWorld.TestingUtil;
import study.realWorld.api.dto.commentsDtos.CommentDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CommentServiceTest extends TestingUtil {


    @Autowired
    CommentService commentService;

    @DisplayName("slug를 이용하여 article을 찾고, 거기에 comment를 추가하고, CommentDto를 반환한다.")
    @Test
    public void addCommentToArticleBySlugTest() throws Exception {

        createUserAndArticleInit(); // 유저 생성, article 생성

        CommentDto commentResponseDto = commentService.addCommentToArticleBySlug(createDto.getSlug(),commentCreateDto);

        assertThat(commentResponseDto.getBody()).isEqualTo("이 글은 참 좋군요.");
        assertThat(commentResponseDto.getAuthor().isFollowing()).isFalse();
    }


}
