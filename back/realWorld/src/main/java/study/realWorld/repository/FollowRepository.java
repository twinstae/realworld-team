package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.Follow;
import study.realWorld.entity.Profile;
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsCountByFromProfileAndToProfile(Profile fromProfile, Profile toProfile);
}
