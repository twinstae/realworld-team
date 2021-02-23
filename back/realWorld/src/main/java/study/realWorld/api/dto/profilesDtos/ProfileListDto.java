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
    private List<Profile> profileList;
    private int profilesCount;
}
