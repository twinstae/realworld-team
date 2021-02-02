package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    boolean findByEmail(String email);
}
