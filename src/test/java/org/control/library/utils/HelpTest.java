package org.control.library.utils;

import jakarta.enterprise.context.ApplicationScoped;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import org.control.library.dto.users.CreateUserDTO;
import org.control.library.utils.res.ResponseLogin;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@ApplicationScoped
public class HelpTest {

    public ResponseLogin createNewUser() {
        CreateUserDTO dto = new CreateUserDTO(
                "testusername",
                "test@example.com",
                "12345678"
        );

        ResponseLogin result = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/v1/auth/register")
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

        return result;
    }

}
