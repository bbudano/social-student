package hr.bernardbudano.socialstudent.controller;

import hr.bernardbudano.socialstudent.dto.comment.CommentDto;
import hr.bernardbudano.socialstudent.dto.comment.CreateCommentRequest;
import hr.bernardbudano.socialstudent.dto.comment.CreateCommentResponse;
import hr.bernardbudano.socialstudent.dto.post.CreatePostRequest;
import hr.bernardbudano.socialstudent.dto.post.GetPost;
import hr.bernardbudano.socialstudent.dto.post.PostDto;
import hr.bernardbudano.socialstudent.model.*;
import hr.bernardbudano.socialstudent.repository.PostLikeRepository;
import hr.bernardbudano.socialstudent.service.CommentService;
import hr.bernardbudano.socialstudent.service.PostLikeService;
import hr.bernardbudano.socialstudent.service.PostService;
import hr.bernardbudano.socialstudent.service.UserDataService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostLikeService postLikeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserDataService userDataService;

    @PostMapping
    @ApiOperation("Creates new post")
    @ResponseStatus(HttpStatus.OK)
    public Post create(@RequestBody CreatePostRequest request, Authentication authentication) {

        // TODO: post not blank validation

        UserData author = userDataService.findByUsername(authentication.getName());
        Post post = CreatePostRequest.toEntity(request, author);

        return postService.create(post);
    }

    @GetMapping
    @ApiOperation("Returns all posts (pageable)")
    public Page<PostDto> getAllPosts(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                     @RequestParam(name = "size", defaultValue = "50", required = false) Integer size) {
        final Page<Post> entityPage = postService.findAllByOrderByPostedOnDesc(PageRequest.of(page, size));
        return entityPage.map(PostDto::fromEntity);
    }

    @GetMapping("/{id}")
    @ApiOperation("Returns post found by id")
    public GetPost getPostById(@NotNull @PathVariable Long id) {

        GetPost post = GetPost.fromEntity(postService.findById(id));
        List<CommentDto> comments = new ArrayList<>();
        commentService.getCommentsByPostSorted(id).forEach(comment -> {
            comments.add(CommentDto.fromEntity(comment));
        });
        post.setComments(comments);

        return post;
    }

    @PostMapping("/{id}/comment")
    public CreateCommentResponse createComment(
            @PathVariable Long id,
            @RequestBody CreateCommentRequest request,
            Authentication authentication) {
        UserData author = userDataService.findByUsername(authentication.getName());
        Post post = postService.findById(id);
        Comment comment = commentService.create(CreateCommentRequest.toEntity(request, author, post));

        return CreateCommentResponse.fromEntity(comment);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(
            @PathVariable Long postId,
            Authentication authentication) {
        UserData user = userDataService.findByUsername(authentication.getName());
        Post post = postService.findById(postId);

        if(postLikeService.existsByPostIdAndUserId(postId, user.getId())) {
            return ResponseEntity.badRequest().body("User " + user.getUsername() + " already likes post " + postId);
        }

        PostLikeId postLikeId = new PostLikeId(postId, user.getId());
        PostLike postLike = new PostLike(postLikeId, post, user);
        postLikeService.create(postLike);

        return ResponseEntity.ok("Post liked successfully");
    }

    @DeleteMapping("/{postId}/unlike")
    public void unlikePost(
            @PathVariable Long postId,
            Authentication authentication) {
        PostLike postLike = postLikeService.findByPostIdAndUserId(postId,
                userDataService.findByUsername(authentication.getName()).getId());

        postLikeService.delete(postLike);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId,
                           Authentication authentication) {
        Post post = postService.findById(postId);

        if(!post.getAuthor().getUsername().equals(authentication.getName())){
            return ResponseEntity.badRequest().body("Only owner or admin can delete post");
        }

        Iterator<Comment> iteratorComments = post.getComments().iterator();
        while(iteratorComments.hasNext()) {
            Comment comment = iteratorComments.next();
            post.removeComment(iteratorComments, comment);
        }

        Iterator<PostLike> iteratorLikes = post.getLikes().iterator();
        while(iteratorLikes.hasNext()) {
            PostLike postLike = iteratorLikes.next();
            post.removeLike(iteratorLikes, postLike);
        }

        postService.delete(post);
        return ResponseEntity.ok("Post deleted successfully");
    }

}
