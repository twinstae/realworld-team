package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.api.dto.profilesDtos.ProfileListDto;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Profile;
import study.realWorld.repository.FavoriteRepository;
import study.realWorld.repository.FollowRepository;
import study.realWorld.repository.ProfilesRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfilesService{
    private final ProfilesRepository profilesRepository;
    private final UserService userService;

    private ProfileDto getProfileDtoContext(String username, BiConsumer<Profile, Profile> strategy){
        Profile currentUserProfile = getCurrentProfileOr404();
        Profile targetUserProfile = getProfileByUserNameOr404(username);

        strategy.accept(currentUserProfile, targetUserProfile);

        boolean isFollowed = currentUserProfile.isFollow(targetUserProfile);
        return ProfileDto.fromEntity(targetUserProfile, isFollowed);
    }

    @Override
    public Optional<Profile> getCurrentProfile() {
        if(userService.isMyUserPresent()){
            return Optional.of(userService.getMyUserWithProfile().getProfile());
        }
        return Optional.empty();
    }

    @Override
    public Profile getCurrentProfileOrEmpty() {
        return getCurrentProfile().orElse(Profile.empty());
    }

    @Override
    public Profile getCurrentProfileOr404() {
        return getCurrentProfile().orElseThrow(RuntimeException::new);
    }

    @Override
    public Optional<Profile> getProfileByUserName(String username){
        return profilesRepository.findOneByUsername(username);
    }

    @Override
    public Profile getProfileByUserNameOr404(String username) {
        return getProfileByUserName(username)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileDto findByUsername(String username) {
        return getProfileDtoContext(username, (a, b)->{});
    }

    @Override
    @Transactional
    public ProfileDto followByUsername(String username) {
        return getProfileDtoContext(username, Profile::follow);
    }

    @Override
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
