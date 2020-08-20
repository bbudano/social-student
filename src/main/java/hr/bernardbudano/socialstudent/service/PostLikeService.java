package hr.bernardbudano.socialstudent.service;

import hr.bernardbudano.socialstudent.model.Post;
import hr.bernardbudano.socialstudent.model.PostLike;
import hr.bernardbudano.socialstudent.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;

    public PostLike create(final PostLike postLike) {
        return postLikeRepository.save(postLike);
    }

    public void delete(final PostLike postLike) {
        postLikeRepository.delete(postLike);
    }

    public boolean existsByPostIdAndUserId(final Long postId, final Long userId) {
        return postLikeRepository.existsByPostIdAndUserId(postId, userId);
    }

    public PostLike findByPostIdAndUserId(final Long postId, final Long userId) {
        return postLikeRepository.findByPostIdAndUserId(postId, userId).orElseThrow(() -> new RuntimeException("Like not found"));
    }

}
