package study.realWorld.api.dto.profilesDtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.api.dto.userDtos.UserDto;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Follow;
import study.realWorld.entity.Profile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private String username;
    private String image;
    private boolean following;

    public static ProfileDto fromEntity(Profile profile, Boolean isFollowed) {
        return ProfileDto
                .builder()
                .username(profile.getUsername())
                .image(profile.getImage())
                .following(isFollowed)
                .build();
    }
}
