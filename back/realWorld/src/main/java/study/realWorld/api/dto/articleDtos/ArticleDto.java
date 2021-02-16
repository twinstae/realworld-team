package study.realWorld.api.dto.articleDtos;

import lombok.*;
import study.realWorld.entity.Articles;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class ArticleDto {
    private final String slug;
    private final String title;
    private final String description;
    private final String body;

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
