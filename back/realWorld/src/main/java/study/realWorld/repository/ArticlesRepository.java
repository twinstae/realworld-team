package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.Articles;

import java.util.Optional;

public interface ArticlesRepository extends JpaRepository<Articles,Long> {

    Optional<Articles> findOneBySlug(String slug);
}
