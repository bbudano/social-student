package hr.bernardbudano.socialstudent.controller;

import hr.bernardbudano.socialstudent.dto.PostDto;
import hr.bernardbudano.socialstudent.model.Post;
import hr.bernardbudano.socialstudent.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    @ApiOperation("Returns all posts (pageable)")
    public Page<PostDto> getAllPosts(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                     @RequestParam(name = "size", defaultValue = "50", required = false) Integer size) {
        final Page<Post> entityPage = postService.findAll(PageRequest.of(page, size));
        return entityPage.map(PostDto::fromEntity);
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
