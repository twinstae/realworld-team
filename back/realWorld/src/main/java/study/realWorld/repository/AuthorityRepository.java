package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Optional<Authority> findByAuthorityName(String authorityName);
}
