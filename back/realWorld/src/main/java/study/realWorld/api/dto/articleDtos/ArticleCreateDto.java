package study.realWorld.api.dto.articleDtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.realWorld.entity.Articles;

@Getter
@NoArgsConstructor
public class ArticleCreateDto {
    private String slug;
    private String title;
    private String description;
    private String body;

    @Builder
    public ArticleCreateDto(String title, String description, String body) {
        this.slug = toSlug(title);
        this.title = title;
        this.description = description;
        this.body = body;
    }

    public static String toSlug(String title) {
        return title.toLowerCase().replaceAll("[\\&|[\\uFE30-\\uFFA0]|\\’|\\”|\\s?,.]+", "-");
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