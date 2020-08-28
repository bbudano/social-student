package hr.bernardbudano.socialstudent.controller;

import hr.bernardbudano.socialstudent.dto.UpdateUserInfoRequest;
import hr.bernardbudano.socialstudent.dto.post.PostDto;
import hr.bernardbudano.socialstudent.dto.user.GetUserResponse;
import hr.bernardbudano.socialstudent.dto.user.UserDto;
import hr.bernardbudano.socialstudent.model.UserData;
import hr.bernardbudano.socialstudent.service.UserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserDataController {

    @Autowired
    private UserDataService userDataService;

    @GetMapping("/{username}")
    public GetUserResponse  getUser(@NotNull  @PathVariable("username") String username) {
        UserData user = userDataService.findByUsername(username);

        List<PostDto> posts = new ArrayList<>();
        user.getPosts().forEach(post -> {
            posts.add(PostDto.fromEntity(post));
        });

        return new GetUserResponse(UserDto.fromEntity(user), posts);
    }

    @PatchMapping
    @Transactional
    public ResponseEntity<?> updateUserInfo(@RequestBody UpdateUserInfoRequest request, Authentication authentication) {

        UserData user = userDataService.findByUsername(authentication.getName());

        if(request.getBio().isPresent()){
            user.setBio(request.getBio().get());
        }
        if(request.getWebsite().isPresent()){
            user.setWebsite(request.getWebsite().get());
        }
        if(request.getLinkedinProfile().isPresent()){
            user.setLinkedinProfile(request.getLinkedinProfile().get());
        }
        if(request.getGithubProfile().isPresent()){
            user.setGithubProfile(request.getGithubProfile().get());
        }

        return ResponseEntity.ok("User info updated");
    }

}
