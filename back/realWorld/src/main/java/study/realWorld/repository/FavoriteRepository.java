package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
}
