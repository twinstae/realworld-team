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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfilesService{
    private final ProfilesRepository profilesRepository;
    private final UserService userService;

    private ProfileDto context(String username, BiConsumer<Profile, Profile> strategy){
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
        return context(username, (a, b)->{});
    }

    @Transactional
    public ProfileDto followByUsername(String username) {
        return context(username, Profile::follow);
    }

    @Transactional
    public ProfileDto unFollowByUsername(String username) {
        return context(username, Profile::unfollow);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileListDto findProfilesFolloweesByUsername(String username){
        Profile targetUserProfile = getProfileByUserNameOr404(username);

        return ProfileListDto
                .builder()
                .profileList(targetUserProfile.getFollowees())
                .profileCount(targetUserProfile.getFollowees().size())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileListDto findProfilesFollowersByUsername(String username){
        Profile targetUserProfile = getProfileByUserNameOr404(username);

        return ProfileListDto
                .builder()
                .profileList(targetUserProfile.getFollowers())
                .profileCount(targetUserProfile.getFollowers().size())
                .build();
    }
}
