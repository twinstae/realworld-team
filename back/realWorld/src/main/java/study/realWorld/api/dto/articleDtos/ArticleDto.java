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

    public static ArticleDto fromEntity(Articles articles, Profile currentProfile) {

        boolean isFollow = currentProfile.isFollow(articles.getAuthor());
        boolean favorited = currentProfile.haveFavorited(articles);
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
                .favorited(favorited)
                .favoritesCount(articles.getFavoritesCount())
                .build();
    }

}
