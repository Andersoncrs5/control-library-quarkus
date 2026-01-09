package org.controllibrary.service;

import org.controllibrary.utils.crypto.CryptoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CryptoServiceTest {

    private CryptoService cryptoService;

    @BeforeEach
    void setUp() {
        this.cryptoService = new CryptoService();
    }

    @Test
    @DisplayName("Should generate a valid Argon2 hash that is not equal to raw password")
    void shouldEncodePassword() {
        String rawPassword = "secret_password_123";

        String encodedPassword = cryptoService.encode(rawPassword);

        assertThat(encodedPassword)
                .isNotNull()
                .isNotBlank()
                .isNotEqualTo(rawPassword)
                .contains("$argon2");
    }

    @Test
    @DisplayName("Should return true when passwords match")
    void shouldVerifyPasswordsMatch() {
        String rawPassword = "my_secure_password";
        String encodedPassword = cryptoService.encode(rawPassword);

        boolean matches = cryptoService.verify(rawPassword, encodedPassword);

        assertThat(matches).isTrue();
    }

    @Test
    @DisplayName("Should return false when passwords do not match")
    void shouldNotVerifyWrongPassword() {
        String rawPassword = "correct_password";
        String wrongPassword = "wrong_password";
        String encodedPassword = cryptoService.encode(rawPassword);

        boolean matches = cryptoService.verify(wrongPassword, encodedPassword);

        assertThat(matches).isFalse();
    }
}