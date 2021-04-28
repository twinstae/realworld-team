package study.realWorld.api.dto.articleDtos;

import lombok.*;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.api.dto.userDtos.UserDto;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Profile;

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
    private ProfileDto author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean favorited;
    private int favoritesCount;

    public static ArticleDto fromEntity(Articles articles, boolean isFollow, boolean favorited) {
        int favoritesCount = articles.favoritesCount;
        return ArticleDto
                .builder()
                .slug(articles.getSlug())
                .title(articles.getTitle())
                .description(articles.getDescription())
                .body(articles.getBody())
                .author(
                        ProfileDto.fromEntity(
                            articles.getAuthor(),
                            isFollow
                        )
                )
                .createdAt(articles.getCreatedAt())
                .updatedAt(articles.getUpdatedAt())
                .favorited(favoritesCount != 0 && favorited)
                .favoritesCount(favoritesCount)
                .build();
    }
    public ArticleDto afterFavorite(){
        this.favoritesCount += 1;
        this.favorited = true;
        return this;
    }
    public ArticleDto afterUnFavorite(){
        this.favoritesCount -= 1;
        this.favorited = false;
        return this;
    }
}
