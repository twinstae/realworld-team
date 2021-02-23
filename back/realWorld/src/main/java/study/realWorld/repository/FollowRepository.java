package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {}
