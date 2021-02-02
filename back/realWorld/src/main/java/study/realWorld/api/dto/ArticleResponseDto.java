package study.realWorld.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.realWorld.entity.Articles;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    private String slug;
    private String title;
    private String description;
    private String body;

    @Builder
    public ArticleResponseDto(String slug,String title, String description, String body) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
    }

    public static ArticleResponseDto fromEntity(Articles articles) {

        return ArticleResponseDto
                .builder()
                .slug(articles.getSlug())
                .title(articles.getTitle())
                .description(articles.getDescription())
                .body(articles.getBody())
                .build();
    }

}
