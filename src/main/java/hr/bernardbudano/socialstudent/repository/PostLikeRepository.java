package hr.bernardbudano.socialstudent.repository;

import hr.bernardbudano.socialstudent.model.PostLike;
import hr.bernardbudano.socialstudent.model.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends PagingAndSortingRepository<PostLike, PostLikeId> {

    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

}

