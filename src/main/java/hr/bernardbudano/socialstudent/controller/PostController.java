package hr.bernardbudano.socialstudent.controller;

import hr.bernardbudano.socialstudent.dto.comment.CommentDto;
import hr.bernardbudano.socialstudent.dto.comment.CreateCommentRequest;
import hr.bernardbudano.socialstudent.dto.comment.CreateCommentResponse;
import hr.bernardbudano.socialstudent.dto.post.CreatePostRequest;
import hr.bernardbudano.socialstudent.dto.post.GetPost;
import hr.bernardbudano.socialstudent.dto.post.PostDto;
import hr.bernardbudano.socialstudent.model.*;
import hr.bernardbudano.socialstudent.security.payload.response.MessageResponse;
import hr.bernardbudano.socialstudent.security.payload.response.MessageType;
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
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation("Creates new post")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody CreatePostRequest request, Authentication authentication) {
        if(request.getBody() == "") {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(MessageType.POST_ERROR, "Post can not be empty"));
        }

        UserData author = userDataService.findByUsername(authentication.getName());
        Post post = CreatePostRequest.toEntity(request, author);

        return ResponseEntity.ok(PostDto.fromEntity(postService.create(post)));
    }

    @GetMapping
    @ApiOperation("Returns all posts (pageable)")
    public Page<PostDto> getAllPosts(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                     @RequestParam(name = "size", defaultValue = "50", required = false) Integer size) {
        final Page<Post> entityPage = postService.findAllByOrderByPostedOnDesc(PageRequest.of(page, size));
        return entityPage.map(PostDto::fromEntity);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation("Returns post found by id")
    public GetPost getPostById(@NotNull @PathVariable Long id) {

        GetPost post = GetPost.fromEntity(postService.findById(id));
        List<CommentDto> comments = new ArrayList<>();
        commentService.getCommentsByPostSorted(id).forEach(comment -> {
            comments.add(CommentDto.fromEntity(comment));
        });
        post.setComments(comments);
        post.setCommentCount(comments.size());

        return post;
    }

    @PostMapping("/{id}/comment")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createComment(
            @PathVariable Long id,
            @RequestBody CreateCommentRequest request,
            Authentication authentication) {
        if(request.getBody() == "") {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(MessageType.COMMENT_ERROR, "Comment can not be empty"));
        }

        UserData author = userDataService.findByUsername(authentication.getName());
        Post post = postService.findById(id);
        Comment comment = commentService.create(CreateCommentRequest.toEntity(request, author, post));

        return ResponseEntity.ok(CreateCommentResponse.fromEntity(comment));
    }

    @GetMapping("/{postId}/like")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
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

        return ResponseEntity.ok(new PostDto(
            post.getId(), post.getBody(), post.getAuthor().getUsername(), post.getPostedOn(), post.getLikes().size(), post.getComments().size()
        ));
    }

    @GetMapping("/{postId}/unlike")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public PostDto unlikePost(
            @PathVariable Long postId,
            Authentication authentication) {
        PostLike postLike = postLikeService.findByPostIdAndUserId(postId,
                userDataService.findByUsername(authentication.getName()).getId());

        postLikeService.delete(postLike);

        Post post = postService.findById(postId);
        return new PostDto(
                post.getId(), post.getBody(), post.getAuthor().getUsername(), post.getPostedOn(), post.getLikes().size(), post.getComments().size()
        );
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
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
