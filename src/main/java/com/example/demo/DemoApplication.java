package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


//	@Bean
//	public CommandLineRunner createDefaultAdmin(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
//		return args -> {
//			if (adminRepository.findByUsername("testuser").isEmpty()) {
//				AdminEntity admin = new AdminEntity();
//				admin.setUsername("testuser");
//				admin.setPassword(passwordEncoder.encode("testpass"));
//				admin.setFullName("Тестовый Пользователь");
//				admin.setRole(AdminRole.SUPER_ADMIN);
//				adminRepository.save(admin);
//				System.out.println("Тестовый пользователь 'testuser' успешно создан!");
//			}
//		};
//	}
}
