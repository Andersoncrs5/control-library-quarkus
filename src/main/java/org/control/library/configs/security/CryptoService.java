package org.control.library.configs.security;

import com.password4j.Password;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotBlank;

@ApplicationScoped
public class CryptoService {

    public String encode(@NotBlank String rawPassword) {
        return Password.hash(rawPassword)
                .withArgon2()
                .getResult();
    }

    public boolean verify(@NotBlank String rawPassword, @NotBlank String encodedPassword) {
        return Password.check(rawPassword, encodedPassword).withArgon2();
    }
}
