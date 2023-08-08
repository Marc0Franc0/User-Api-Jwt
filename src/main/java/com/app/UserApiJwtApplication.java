package com.app;

import com.app.model.ERole;
import com.app.dto.PersonalData;
import com.app.model.RoleEntity;
import com.app.model.UserEntity;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${user.admin.username}")
	String usernameAdmin;
	@Value("${user.admin.password}")
	String passwordAdmin;

	public static void main(String[] args) {
		SpringApplication.run(UserApiJwtApplication.class, args);
	}

@Bean
	CommandLineRunner init() {
		return args -> {

			UserEntity userEntity = UserEntity.builder()
					.username(usernameAdmin)
					.password(passwordEncoder.encode(passwordAdmin))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.personalData(new PersonalData
							("","",0,""))
					.build();

			userRepository.save(userEntity);
		};
	}
}
