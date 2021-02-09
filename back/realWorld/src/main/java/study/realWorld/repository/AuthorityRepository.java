package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
