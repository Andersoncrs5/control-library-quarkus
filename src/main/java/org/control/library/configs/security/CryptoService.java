package org.control.library.configs.security;

import com.password4j.Password;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CryptoService {

    public String encode(String rawPassword) {
        return Password.hash(rawPassword)
                .withArgon2()
                .getResult();
    }

    public boolean verify(String rawPassword, String encodedPassword) {
        return Password.check(rawPassword, encodedPassword).withArgon2();
    }
}
