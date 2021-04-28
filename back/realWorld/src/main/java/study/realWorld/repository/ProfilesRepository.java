package study.realWorld.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.realWorld.api.dto.profilesDtos.ProfileDto;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Profile;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfilesRepository extends JpaRepository<Profile,Long> {
    Optional<Profile> findOneByUsername(String username);


}
