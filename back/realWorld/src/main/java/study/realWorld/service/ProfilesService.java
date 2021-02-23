package study.realWorld.service;

import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.api.dto.profilesDtos.ProfileListDto;

public interface ProfilesService {
    ProfileDto findByUsername(String username);
    ProfileDto followByUsername(String username);
    ProfileDto unFollowByUsername(String username);
    ProfileListDto findProfilesFolloweesByUsername(String username);
    ProfileListDto findProfilesFollowersByUsername(String username);
}
