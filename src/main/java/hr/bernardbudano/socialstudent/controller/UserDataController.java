package hr.bernardbudano.socialstudent.controller;

import hr.bernardbudano.socialstudent.dto.UpdateUserInfoRequest;
import hr.bernardbudano.socialstudent.model.UserData;
import hr.bernardbudano.socialstudent.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserDataController {

    @Autowired
    private UserDataService userDataService;

    @PatchMapping
    @Transactional
    public ResponseEntity<?> updateUserInfo(UpdateUserInfoRequest request, Authentication authentication) {
        UserData user = userDataService.findByUsername(authentication.getPrincipal().toString());

        if(request.getBio().isPresent()){
            user.setBio(request.getBio().get());
        }
        if(request.getWebsite().isPresent()){
            user.setWebsite(request.getWebsite().get());
        }
        if(request.getLinkedinProfile().isPresent()){
            user.setLinkedinProfile(request.getLinkedinProfile().get());
        }
        if(request.getFacebookProfile().isPresent()){
            user.setFacebookProfile(request.getFacebookProfile().get());
        }

        return ResponseEntity.ok("User info updated");
    }

}
