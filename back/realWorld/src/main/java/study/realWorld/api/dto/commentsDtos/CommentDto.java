package study.realWorld.api.dto.commentsDtos;

import lombok.*;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Comment;
import study.realWorld.entity.Profile;

import java.time.LocalDateTime;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String body;
    private ProfileDto author;


    public static CommentDto fromEntity(Comment comment, boolean isFollowing) {
        return CommentDto
                .builder()
                .body(comment.getBody())
                .author(
                        ProfileDto.fromEntity(
                                comment.getAuthor(),
                                isFollowing
                        )
                )
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
