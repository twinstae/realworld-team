package study.realWorld.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
