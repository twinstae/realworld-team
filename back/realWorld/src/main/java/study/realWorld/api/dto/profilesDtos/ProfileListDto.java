package study.realWorld.api.dto.profilesDtos;


import lombok.*;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.entity.Follow;
import study.realWorld.entity.Profile;

import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProfileListDto {

    private List<Follow> followeeRelations;
    private List<Follow> followerRelations;
    private int followeesCount;
    private int followersCount;

    public static ProfileListDto fromEntity(Profile profile) {
        return ProfileListDto
                .builder()
                .followeeRelations(profile.getFolloweeRelations())
                .followerRelations(profile.getFollowerRelations())
                .followeesCount(profile.getFolloweeRelations().size())
                .followersCount(profile.getFollowerRelations().size())
                .build();
    }
}
