package study.realWorld.api.dto.commentsDtos;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Comment;
import study.realWorld.entity.Profile;

@Getter
@ToString
@NoArgsConstructor
public class CommentCreateDto {

    private String body;


    @Builder
    public CommentCreateDto(String body) {
        this.body = body;
    }

    public Comment toEntity(Profile profile, Articles articles){
        Comment comment = Comment
                .builder()
                .body(this.body)
                .profile(profile)
                .articles(articles)
                .build();
        profile.addComment(comment);
        articles.addComment(comment);
        return comment;
    }
}
