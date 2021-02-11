package study.realWorld.api.dto.articleDtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.realWorld.entity.Articles;

@Getter
@NoArgsConstructor
public class ArticleDto {
    private String slug;
    private String title;
    private String description;
    private String body;

    @Builder
    public ArticleDto(String slug, String title, String description, String body) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
    }

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
