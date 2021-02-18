package study.realWorld.api.dto.articleDtos;

import lombok.*;
import study.realWorld.api.dto.userDtos.UserDto;
import study.realWorld.entity.Articles;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private String slug;
    private String title;
    private String description;
    private String body;
    private UserDto author;

    public static ArticleDto fromEntity(Articles articles) {

        return ArticleDto
                .builder()
                .slug(articles.getSlug())
                .title(articles.getTitle())
                .description(articles.getDescription())
                .body(articles.getBody())
                .author(UserDto.fromUser(articles.getAuthor()))
                .build();
    }

}
