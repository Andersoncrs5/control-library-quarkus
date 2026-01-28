package org.control.library.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.control.library.dto.users.CreateUserDTO;
import org.control.library.dto.users.LoginUserDTO;
import org.control.library.repositories.UserRepository;
import org.control.library.utils.HelpTest;
import org.control.library.utils.res.ResponseLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class AuthControllerTest {

    @Inject
    private HelpTest helpTest;

    @Inject
    private UserRepository repository;
    private final String url = "/v1/auth";

    @BeforeEach
    @Transactional
    void setup() {
        this.repository.deleteAll();
    }

    @Test
    void shouldCreateNewUser() {
        CreateUserDTO dto = new CreateUserDTO(
                "testusername",
                "test@example.com",
                "12345678"
        );

        ResponseLogin result = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(url + "/register")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getObject("data", ResponseLogin.class);

        assertThat(result.user().getId()).isPositive();
        assertThat(result.user().getUsername()).isEqualTo(dto.username());
        assertThat(result.user().getEmail()).isEqualTo(dto.email());

        assertThat(result.token()).isNotBlank();
        assertThat(result.refreshToken()).isNotBlank();

        assertThat(result.expToken()).isInTheFuture();
        assertThat(result.expRefreshToken()).isInTheFuture();
    }

    @Test
    void shouldMakeLoginWithSuccess() {
        ResponseLogin login = this.helpTest.createNewUser();

        LoginUserDTO dto = new LoginUserDTO(login.user().getEmail(), "12345678");

        ResponseLogin result = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(url + "/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getObject("data", ResponseLogin.class);

        assertThat(result.user().getId()).isEqualTo(login.user().getId());
        assertThat(result.user().getEmail()).isEqualTo(login.user().getEmail());

        assertThat(result.token()).isNotBlank();
        assertThat(result.refreshToken()).isNotBlank();

        assertThat(result.expToken()).isInTheFuture();
        assertThat(result.expRefreshToken()).isInTheFuture();
    }

    @Test
    void shouldMakeLoginWithFailBecauseEmailWrong() {
        ResponseLogin login = this.helpTest.createNewUser();

        LoginUserDTO dto = new LoginUserDTO("pochita@gmail.com", "ola o ronco");

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(url + "/login")
                .then()
                .statusCode(401);
    }

    @Test
    void shouldMakeLoginWithFailBecausePasswordWrong() {
        ResponseLogin login = this.helpTest.createNewUser();

        LoginUserDTO dto = new LoginUserDTO(login.user().getEmail(), "ola o ronco");

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(url + "/login")
                .then()
                .statusCode(401);
    }

    @Test
    void shouldMakeLogoutWithSuccess() {
        ResponseLogin login = this.helpTest.createNewUser();

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(login.token())
                .when()
                .post(url + "/logout")
                .then()
                .statusCode(401);
    }


}
