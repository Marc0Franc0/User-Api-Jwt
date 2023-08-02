package com.app;

import com.app.model.ERole;
import com.app.model.RoleEntity;
import com.app.model.UserEntity;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class UserApiJwtApplication {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserApiJwtApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {

			UserEntity userEntity = UserEntity.builder()
					.username("marco")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			userRepository.save(userEntity);
		};
	}
}
