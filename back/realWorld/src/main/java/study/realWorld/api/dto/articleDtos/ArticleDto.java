package study.realWorld.api.dto.articleDtos;

import lombok.*;
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

    public static ArticleDto fromEntity(Articles articles) {

        return ArticleDto
                .builder()
                .slug(articles.getSlug())
                .title(articles.getTitle())
                .description(articles.getDescription())
                .body(articles.getBody())
                .build();
    }

}
