package hr.bernardbudano.socialstudent.service;

import hr.bernardbudano.socialstudent.model.UserData;
import hr.bernardbudano.socialstudent.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDataService extends AbstractService<UserData, UserDataRepository> {

    @Autowired
    private UserDataRepository userDataRepository;

    public UserDataService(UserDataRepository repository) {
        super(repository);
    }

    public UserData findByUsername(String username){
        return userDataRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));
    }
}
