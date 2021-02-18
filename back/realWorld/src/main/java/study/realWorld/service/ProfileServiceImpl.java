package study.realWorld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.api.exception.ResourceNotFoundException;
import study.realWorld.repository.ProfilesRepository;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfilesService{

    private final ProfilesRepository profilesRepository;

    @Override
    public ProfileDto findByUsername(String username) {
       return profilesRepository.findProfilesByUsername(username)
               .orElseThrow(ResourceNotFoundException::new);
    }
}
