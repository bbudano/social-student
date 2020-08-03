package hr.bernardbudano.socialstudent.repository;

import hr.bernardbudano.socialstudent.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    UserData findByUsername(String username);

}
