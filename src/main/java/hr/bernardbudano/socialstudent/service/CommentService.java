package hr.bernardbudano.socialstudent.service;

import hr.bernardbudano.socialstudent.model.Comment;
import hr.bernardbudano.socialstudent.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService extends AbstractService<Comment, CommentRepository> {

    @Autowired
    private CommentRepository commentRepository;

    public CommentService(CommentRepository repository) {
        super(repository);
    }

    public List<Comment> getCommentsByPostSorted(final Long postId) {
        return commentRepository.findByPostIdOrderByPostedOn(postId);
    }

}
