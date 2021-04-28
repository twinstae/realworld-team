package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.Articles;
import study.realWorld.entity.Favorite;
import study.realWorld.entity.Profile;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsCountByProfileAndArticle(Profile profile, Articles article);
}
