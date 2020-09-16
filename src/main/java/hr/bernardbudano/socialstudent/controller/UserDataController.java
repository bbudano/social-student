package hr.bernardbudano.socialstudent.controller;

import hr.bernardbudano.socialstudent.dto.UpdateUserInfoRequest;
import hr.bernardbudano.socialstudent.dto.post.PostDto;
import hr.bernardbudano.socialstudent.dto.user.GetUserProfileResponse;
import hr.bernardbudano.socialstudent.dto.user.UserDto;
import hr.bernardbudano.socialstudent.model.Role;
import hr.bernardbudano.socialstudent.model.RoleName;
import hr.bernardbudano.socialstudent.model.UserData;
import hr.bernardbudano.socialstudent.repository.RoleRepository;
import hr.bernardbudano.socialstudent.service.UserDataService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "UserData controller")
@RequestMapping("/api/user")
public class UserDataController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/{username}")
    public GetUserProfileResponse getUserProfile(@NotNull  @PathVariable("username") String username) {
        UserData user = userDataService.findByUsername(username);
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role not found."));
        boolean isAdmin = user.getRoles().contains(adminRole);

        List<PostDto> posts = new ArrayList<>();
        user.getPosts().sort((post1, post2) -> post2.getPostedOn().compareTo(post1.getPostedOn()));
        user.getPosts().forEach(post -> {
            posts.add(PostDto.fromEntity(post));
        });

        return new GetUserProfileResponse(UserDto.fromEntity(user), isAdmin, posts);
    }

    @PatchMapping("/updateAvatar")
    @Transactional
    public ResponseEntity<?> updateUserAvatar(@RequestBody String avatarUrl, Authentication authentication) {
        UserData user = userDataService.findByUsername(authentication.getName());
        user.setAvatarUrl(avatarUrl);
        return ResponseEntity.ok("Avatar updated successfully");
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
