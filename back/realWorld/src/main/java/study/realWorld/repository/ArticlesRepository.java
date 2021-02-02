package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.Articles;

public interface ArticlesRepository extends JpaRepository<Articles,Long> {

    Articles findOneBySlug(String slug);
}
