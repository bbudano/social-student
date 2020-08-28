package hr.bernardbudano.socialstudent.controller;

import hr.bernardbudano.socialstudent.dto.PostLikeDto;
import hr.bernardbudano.socialstudent.dto.user.CredentialsDto;
import hr.bernardbudano.socialstudent.model.Role;
import hr.bernardbudano.socialstudent.model.RoleName;
import hr.bernardbudano.socialstudent.model.UserData;
import hr.bernardbudano.socialstudent.repository.RoleRepository;
import hr.bernardbudano.socialstudent.security.jwt.JwtUtils;
import hr.bernardbudano.socialstudent.security.payload.request.LoginRequest;
import hr.bernardbudano.socialstudent.security.payload.request.SignupRequest;
import hr.bernardbudano.socialstudent.security.payload.response.JwtResponse;
import hr.bernardbudano.socialstudent.security.payload.response.MessageResponse;
import hr.bernardbudano.socialstudent.security.payload.response.MessageType;
import hr.bernardbudano.socialstudent.security.payload.response.UserInfoResponse;
import hr.bernardbudano.socialstudent.security.service.UserDetailsImpl;
import hr.bernardbudano.socialstudent.service.UserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        if(!userDataService.existsByUsername(loginRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(MessageType.USERNAME_ERROR,"Invalid username"));
        }
        if(loginRequest.getUsername() == "") {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(MessageType.USERNAME_ERROR, "Username must not be blank"));
        }
        if(loginRequest.getPassword() == "") {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(MessageType.PASSWORD_ERROR, "Password must not be blank"));
        }
        UserData user = userDataService.findByUsername(loginRequest.getUsername());
        if(!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(MessageType.PASSWORD_ERROR, "Invalid password"));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userDataService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(MessageType.USERNAME_ERROR, "Username is already taken!"));
        }
        if (userDataService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(MessageType.EMAIL_ERROR, "Email is already in use!"));
        }

        UserData user = new UserData(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userDataService.create(user);

        return ResponseEntity.ok(new MessageResponse(MessageType.OK, "User registered successfully!"));
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        UserData userData = userDataService.findByUsername(authentication.getName());

        CredentialsDto credentials = new CredentialsDto(
                userData.getUsername(),
                userData.getEmail(),
                userData.getBio(),
                userData.getJoinedOn(),
                userData.getWebsite(),
                userData.getGithubProfile(),
                userData.getLinkedinProfile()
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        List<PostLikeDto> likes = new ArrayList<>();
        userData.getLikedPosts().forEach(likedPost -> {
            PostLikeDto postLikeDto = new PostLikeDto(likedPost.getUser().getUsername(), likedPost.getPost().getId());
            likes.add(postLikeDto);
        });

        return ResponseEntity.ok(new UserInfoResponse(
                credentials,
                roles,
                likes
        ));
    }

    @PostMapping("/giveAdmin/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public ResponseEntity<?> giveUserAdminPermissions(@NotNull @PathVariable String username) {
        UserData user = userDataService.findByUsername(username);

        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).get();
        user.getRoles().add(adminRole);

        return ResponseEntity.ok("User " + username + " now has ADMIN PERMISSIONS.");
    }


    // Test APIs
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
