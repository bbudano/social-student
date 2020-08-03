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

@Service
public class UserDataService extends AbstractService<UserData, UserDataRepository>  implements UserDetailsService {

    @Autowired
    private UserDataRepository userDataRepository;

    public UserDataService(UserDataRepository repository) {
        super(repository);
    }

    public UserData findByUsername(String username){
        return userDataRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData = userDataRepository.findByUsername(username);
        return new User(userData.getUsername(), userData.getPassword(), new ArrayList<>());
    }
}
