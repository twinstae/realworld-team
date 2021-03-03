package study.realWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.realWorld.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
