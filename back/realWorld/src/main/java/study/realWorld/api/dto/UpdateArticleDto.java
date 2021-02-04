package study.realWorld.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.realWorld.entity.Articles;

@Getter
@NoArgsConstructor
public class UpdateArticleDto {

    private String slug;
    private String title;
    private String description;
    private String body;

    @Builder
    public UpdateArticleDto(String slug, String title, String description, String body) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
    }

    public Articles toEntity(){
        return Articles
                .builder()
                .slug(this.slug)
                .title(this.title)
                .description(this.description)
                .body(this.body)
                .build();
    }
}
