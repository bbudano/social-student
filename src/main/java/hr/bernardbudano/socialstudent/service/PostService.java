package hr.bernardbudano.socialstudent.service;

import hr.bernardbudano.socialstudent.model.Post;
import hr.bernardbudano.socialstudent.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService extends AbstractService<Post, PostRepository> {

    @Autowired
    private PostRepository postRepository;

    public PostService(PostRepository repository) {
        super(repository);
    }

    public Page<Post> findAllByOrderByPostedOnDesc(Pageable pageable) {
        return postRepository.findAllByOrderByPostedOnDesc(pageable);
    }

    public Post findById(final Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found."));
    }

}
