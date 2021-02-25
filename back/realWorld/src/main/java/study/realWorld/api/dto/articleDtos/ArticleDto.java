package study.realWorld.api.dto.articleDtos;

import lombok.*;
import study.realWorld.api.dto.userDtos.UserDto;
import study.realWorld.entity.Articles;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean favorited;
    private Long favoritesCount;

    public static ArticleDto fromEntity(Articles articles,boolean favorited,Long favoritesCount) {

        return ArticleDto
                .builder()
                .slug(articles.getSlug())
                .title(articles.getTitle())
                .description(articles.getDescription())
                .body(articles.getBody())
                .author(UserDto.fromUser(articles.getAuthor()))
                .createdAt(articles.getCreatedAt())
                .updatedAt(articles.getUpdatedAt())
                .favorited(favorited)
                .favoritesCount(favoritesCount)
                .build();
    }

}
