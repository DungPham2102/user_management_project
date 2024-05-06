package com.springsecuritywithjwt.springsecuritywithjwt;

import com.springsecuritywithjwt.springsecuritywithjwt.entity.Role;
import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;
import com.springsecuritywithjwt.springsecuritywithjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringsecuritywithjwtApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringsecuritywithjwtApplication.class, args);
	}

	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if(null == adminAccount){
			User user = new User();

			user.setEmail("admin@gmail.com");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
			for(int i = 0; i < 20; i++) {
				User user1 = new User();
				user1.setEmail("user" + i + "@gmail.com");
				user1.setFirstName("user" + i);
				user1.setLastName("user" + i);
				user1.setRole(Role.USER);
				user1.setPassword(new BCryptPasswordEncoder().encode("123"));
				userRepository.save(user1);
			}
		}
	}
}
