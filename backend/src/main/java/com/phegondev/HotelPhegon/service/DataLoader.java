package com.phegondev.HotelPhegon.service;

import com.phegondev.HotelPhegon.entity.User;
import com.phegondev.HotelPhegon.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Supondo que sua entidade se chama User e o repositório UserRepository
@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Injeção via construtor (melhor prática)
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existe um usuário ADMIN para não criar duplicatas
        if (userRepository.findByRole("ADMIN").isEmpty()) {
            System.out.println("Criando usuário ADMIN inicial...");

            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@hotelphegon.com");
            // Hasheando a senha antes de salvar!
            admin.setPassword(passwordEncoder.encode("minhaSenhaForte123"));
            admin.setRole("ADMIN");
            admin.setPhoneNumber("000000000");

            userRepository.save(admin);

            System.out.println("Usuário ADMIN criado com sucesso.");
        }
    }
}