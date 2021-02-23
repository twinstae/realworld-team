package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.api.dto.profilesDtos.ProfileListDto;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Profile;
import study.realWorld.repository.ProfilesRepository;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfilesService{
    private final ProfilesRepository profilesRepository;
    private final UserService userService;

    private ProfileDto getProfileDtoContext(String username, BiConsumer<Profile, Profile> strategy){
        String myUserName = userService.getMyUserName();
        Profile currentUserProfile = getProfileByUserNameOr404(myUserName);
        Profile targetUserProfile = getProfileByUserNameOr404(username);

        strategy.accept(currentUserProfile, targetUserProfile);

        boolean isFollowed = currentUserProfile.isFollow(targetUserProfile);
        return ProfileDto.fromEntity(targetUserProfile, isFollowed);
    }

    Profile getProfileByUserNameOr404(String username){
        return profilesRepository.findOneByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileDto findByUsername(String username) {
        return getProfileDtoContext(username, (a, b)->{});
    }

    @Transactional
    public ProfileDto followByUsername(String username) {
        return getProfileDtoContext(username, Profile::follow);
    }

    @Transactional
    public ProfileDto unFollowByUsername(String username) {
        return getProfileDtoContext(username, Profile::unfollow);
    }


    private ProfileListDto getProfileListDtoContext(String username, Function<Profile, List<Profile>> strategy) {
        Profile targetUserProfile = getProfileByUserNameOr404(username);

        List<Profile> profileList = strategy.apply(targetUserProfile);
        return ProfileListDto
                .builder()
                .profileList(profileList)
                .profileCount(profileList.size())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileListDto findProfilesFolloweesByUsername(String username){
        return getProfileListDtoContext(username, Profile::getFollowees);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileListDto findProfilesFollowersByUsername(String username){
        return getProfileListDtoContext(username, Profile::getFollowers);
    }
}
