package study.realWorld.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.Articles;

import java.util.List;
import java.util.Optional;

public interface ArticlesRepository extends JpaRepository<Articles,Long> {
    @EntityGraph(attributePaths = "author")
    List<Articles> findAll();

    @EntityGraph(attributePaths = "author")
    Optional<Articles> findOneWithAuthorBySlug(String slug);
}
