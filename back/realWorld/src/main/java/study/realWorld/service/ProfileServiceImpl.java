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

    // followRepository
    private final ProfilesRepository profilesRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public ProfileDto findByUsername(String username) {
        String myUserName = userService.getMyUser().getUserName();
        Profile currentUserProfile = getProfileByUserNameOr404(myUserName);
        Profile targetUserProfile = getProfileByUserNameOr404(username);
        boolean isFollowed = targetUserProfile.isFollow(currentUserProfile);

        return ProfileDto.fromEntity(targetUserProfile, isFollowed);
    }

    @Transactional
    Profile getProfileByUserNameOr404(String username){
        return profilesRepository.findOneByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public ProfileDto followByUsername(String username) {
        String myUserName = userService.getMyUser().getUserName();
        Profile currentUserProfile = getProfileByUserNameOr404(myUserName);
        Profile targetUserProfile = getProfileByUserNameOr404(username);
        currentUserProfile.follow(targetUserProfile);
        boolean isFollowed = targetUserProfile.isFollow(currentUserProfile);

        return ProfileDto.fromEntity(targetUserProfile, isFollowed);
    }

    public ProfileDto unFollowByUsername(String username) {
        String myUserName = userService.getMyUser().getUserName();
        Profile currentUserProfile = getProfileByUserNameOr404(myUserName);
        Profile targetUserProfile = getProfileByUserNameOr404(username);
        currentUserProfile.unfollow(targetUserProfile);
        boolean isFollowed = targetUserProfile.isFollow(currentUserProfile);

        return ProfileDto.fromEntity(targetUserProfile, isFollowed);
    }



    @Override
    @Transactional(readOnly = true)
    public ProfileListDto findByFollowsByUsername(String username){
        Profile targetUserProfile = getProfileByUserNameOr404(username);

        return ProfileListDto
                .builder()
                .followeeRelations(targetUserProfile.getFolloweeRelations())
                .followerRelations(targetUserProfile.getFollowerRelations())
                .build();
    }

    // followees, followers count

//    @Transactional
//    @Override
//    public ProfileDto save(ProfileCreateDto profileCreateDto) {
//        User currentUser =  userService.getMyUser(); //현재 접속한 user
//        Profile findProfile =profilesRepository.findProfilesByUsername(currentUser.getUserName())
//                .orElseThrow(ResourceNotFoundException::new);
//        Profile profile = profilesRepository.save(ProfileCreateDto.toEntity(currentUser));
//        return ProfileDto.fromEntity(profile);
//    }

}
