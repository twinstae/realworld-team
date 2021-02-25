package study.realWorld.api.dto.articleDtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Profile;
import study.realWorld.entity.User;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class ArticleCreateDto {
    private String slug;
    private String title;
    private String description;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public Articles toEntity(Profile profile){
        Articles article = Articles
                .builder()
                .slug(toSlug(this.title))
                .title(this.title)
                .description(this.description)
                .body(this.body)
                .author(profile)
                .build();
        profile.addArticle(article);
        return article;
    }
}