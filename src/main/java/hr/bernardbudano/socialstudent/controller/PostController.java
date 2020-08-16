package hr.bernardbudano.socialstudent.controller;

import hr.bernardbudano.socialstudent.dto.comment.CommentDto;
import hr.bernardbudano.socialstudent.dto.comment.CreateCommentRequest;
import hr.bernardbudano.socialstudent.dto.post.CreatePostRequest;
import hr.bernardbudano.socialstudent.dto.post.GetPost;
import hr.bernardbudano.socialstudent.dto.post.PostDto;
import hr.bernardbudano.socialstudent.model.Comment;
import hr.bernardbudano.socialstudent.model.Post;
import hr.bernardbudano.socialstudent.model.UserData;
import hr.bernardbudano.socialstudent.service.CommentService;
import hr.bernardbudano.socialstudent.service.PostService;
import hr.bernardbudano.socialstudent.service.UserDataService;
import io.swagger.annotations.ApiOperation;
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
import java.util.List;

@RestController
@RequestMapping(path = "/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserDataService userDataService;

    @PostMapping
    @ApiOperation("Creates new post")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody CreatePostRequest request, Authentication authentication) {

        // TODO: post not blank validation

        UserData author = userDataService.findByUsername(authentication.getName());
        Post post = CreatePostRequest.toEntity(request, author);

        postService.create(post);

        // TODO: implement response class
        return ResponseEntity.ok("Post created successfully");
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
    public ResponseEntity<?> createComment(
            @PathVariable Long id,
            @RequestBody CreateCommentRequest request,
            Authentication authentication) {

        UserData author = userDataService.findByUsername(authentication.getName());
        Post post = postService.findById(id);

        commentService.create(CreateCommentRequest.toEntity(request, author, post));

        // TODO: implement response class
        return ResponseEntity.ok("Comment posted successfully");
    }

    @GetMapping("/all")
    public String all() {
        return "Public content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String onlyUser(){
        return "User content";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String onlyAdmin(){
        return "Admin content";
    }

}
