package hr.bernardbudano.socialstudent;

import hr.bernardbudano.socialstudent.model.Role;
import hr.bernardbudano.socialstudent.model.RoleName;
import hr.bernardbudano.socialstudent.model.UserData;
import hr.bernardbudano.socialstudent.repository.RoleRepository;
import hr.bernardbudano.socialstudent.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SocialStudentApplication {

	@Autowired
	private RoleRepository roleRepository;

	/*@Configuration
	@EnableWebMvc
	protected class AppMvcConfiguration implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("http://localhost:3000")
					.allowedMethods("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE", "HEAD").allowCredentials(true);
		}

	}*/

	/*@PostConstruct
	public void insertRoles() {
		Role roleUser = new Role();
		roleUser.setName(RoleName.ROLE_USER);
		Role roleAdmin = new Role();
		roleAdmin.setName(RoleName.ROLE_ADMIN);

		roleRepository.save(roleUser);
		roleRepository.save(roleAdmin);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(SocialStudentApplication.class, args);
	}

}
