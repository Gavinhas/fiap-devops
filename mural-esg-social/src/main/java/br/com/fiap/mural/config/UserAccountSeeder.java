package br.com.fiap.mural.config;

import br.com.fiap.mural.user.AppUser;
import br.com.fiap.mural.user.AppUserRepository;
import br.com.fiap.mural.user.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAccountSeeder implements CommandLineRunner {

    private final AppUserRepository users;
    private final PasswordEncoder encoder;

    @Value("${app.seed.admin.username:admin}")
    private String adminUsername;

    @Value("${app.seed.admin.password:admin}")
    private String adminPassword;

    @Value("${app.seed.user.username:usuario}")
    private String userUsername;

    @Value("${app.seed.user.password:usuario}")
    private String userPassword;

    public UserAccountSeeder(AppUserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (!users.existsByUsername(adminUsername)) {
            users.save(new AppUser(adminUsername, encoder.encode(adminPassword), Role.ADMIN));
        }
        if (!users.existsByUsername(userUsername)) {
            users.save(new AppUser(userUsername, encoder.encode(userPassword), Role.USER));
        }
    }
}
