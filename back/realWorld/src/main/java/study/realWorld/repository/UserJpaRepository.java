package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
