package study.realWorld.api.dto.profilesDtos;


import lombok.*;
import study.realWorld.api.dto.articleDtos.ArticleDto;
import study.realWorld.entity.Follow;
import study.realWorld.entity.Profile;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@NoArgsConstructor
@Getter
public class ProfileListDto {
    private List<ProfileDto> profileList;
    private int profileCount;

    @Builder
    ProfileListDto(List<Profile> profileList, int profileCount){
        this.profileList = profileList.stream()
                .map(entity -> ProfileDto.fromEntity(entity, true))  // true는 내가 그 profile을 팔로우하고 있는지와 관련 없음
                .collect(Collectors.toList());
        System.out.println("in the list dto 생성자");
        System.out.println(this.profileList);
        this.profileCount = profileCount;
    }

}
