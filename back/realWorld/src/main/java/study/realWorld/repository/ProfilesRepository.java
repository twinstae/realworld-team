package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.entity.Profile;

import java.util.Optional;

@Repository
public interface ProfilesRepository extends JpaRepository<Profile,Long> {

    Optional<ProfileDto> findProfileDtoByUsername(String username);

    Optional<Profile> findProfilesByUsername(String username);

}
