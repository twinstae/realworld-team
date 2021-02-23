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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfilesService{

    private final ProfilesRepository profilesRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public ProfileDto findByUsername(String username) {
        String myUserName = userService.getMyUserName();
        Profile currentUserProfile = getProfileByUserNameOr404(myUserName);
        Profile targetUserProfile = getProfileByUserNameOr404(username);

        boolean isFollowed = currentUserProfile.isFollow(targetUserProfile);

        return ProfileDto.fromEntity(targetUserProfile, isFollowed);
    }

    Profile getProfileByUserNameOr404(String username){
        return profilesRepository.findOneByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public ProfileDto followByUsername(String username) {
        String myUserName = userService.getMyUserName();

        Profile currentUserProfile = getProfileByUserNameOr404(myUserName);
        Profile targetUserProfile = getProfileByUserNameOr404(username);

        currentUserProfile.follow(targetUserProfile);

        return ProfileDto.fromEntity(targetUserProfile, true);
    }

    @Transactional
    public ProfileDto unFollowByUsername(String username) {
        String myUserName =  userService.getMyUserName();
        Profile currentUserProfile = getProfileByUserNameOr404(myUserName);
        Profile targetUserProfile = getProfileByUserNameOr404(username);

        currentUserProfile.unfollow(targetUserProfile);

        return ProfileDto.fromEntity(targetUserProfile, false);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileListDto findProfilesFolloweesByUsername(String username){
        Profile targetUserProfile = getProfileByUserNameOr404(username);

        System.out.println("in the 서비스");
        System.out.println(targetUserProfile.getFollowings());

        return ProfileListDto
                .builder()
                .profileList(targetUserProfile.getFollowings())
                .profileCount(targetUserProfile.getFollowings().size())
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
