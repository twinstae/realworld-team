package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByEmail(String email);
}
