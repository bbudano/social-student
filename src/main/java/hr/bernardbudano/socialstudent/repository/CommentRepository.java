package hr.bernardbudano.socialstudent.repository;

import hr.bernardbudano.socialstudent.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment WHERE post_id = :postId ORDER BY posted_on DESC", nativeQuery = true)
    List<Comment> findByPostIdOrderByPostedOn(Long postId);

}
