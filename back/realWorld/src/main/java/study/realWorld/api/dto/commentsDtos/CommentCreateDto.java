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

    public Comment toEntity(Profile author, Articles articles){
        return Comment
                .builder()
                .body(this.body)
                .author(author)
                .articles(articles)
                .build();
    }
}
