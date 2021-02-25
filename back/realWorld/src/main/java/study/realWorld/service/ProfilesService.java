package study.realWorld.service;

import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.api.dto.profilesDtos.ProfileListDto;
import study.realWorld.entity.Profile;

import java.util.Optional;

public interface ProfilesService {
    Optional<Profile> getCurrentProfile();

    Profile getCurrentProfileOrEmpty();

    Profile getCurrentProfileOr404();

    Optional<Profile> getProfileByUserName(String username);

    Profile getProfileByUserNameOr404(String username);

    ProfileDto findByUsername(String username);
    ProfileDto followByUsername(String username);
    ProfileDto unFollowByUsername(String username);
    ProfileListDto findProfilesFolloweesByUsername(String username);
    ProfileListDto findProfilesFollowersByUsername(String username);
}
