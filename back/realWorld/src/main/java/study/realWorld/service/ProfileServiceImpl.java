package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.entity.Profile;
import study.realWorld.entity.User;
import study.realWorld.repository.ProfilesRepository;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfilesService{

    private final ProfilesRepository profilesRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public ProfileDto findByUsername(String username) {
        User currentUser =  userService.getMyUser(); //현재 접속한 user

        Profile currentUserProfile = profilesRepository.findOneByUsername(currentUser.getUserName())
                .orElseThrow(ResourceNotFoundException::new);

        Profile targetUserProfile = profilesRepository.findOneByUsername(username).orElseThrow(RuntimeException::new);
        boolean isFollowed = targetUserProfile.isFollow(currentUserProfile);
        return ProfileDto.fromEntity(targetUserProfile,isFollowed);
    }


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
