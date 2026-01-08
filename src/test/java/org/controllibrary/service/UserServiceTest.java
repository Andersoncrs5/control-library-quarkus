package org.controllibrary.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.assertj.core.api.Assertions;
import org.controllibrary.dtos.users.CreateUserDTO;
import org.controllibrary.models.UserModel;
import org.controllibrary.repositories.UserRepository;
import org.controllibrary.services.providers.UserService;
import org.controllibrary.utils.exceptions.ModelNotFoundException;
import org.controllibrary.utils.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import org.mockito.InjectMocks;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@QuarkusTest
public class UserServiceTest {

    @Inject
    UserService service;

    @InjectMock
    UserRepository repository;

    @Inject
    UserMapper mapper;

    UserModel user = new UserModel();

    @BeforeEach
    void setup() {
        user.setId(5736475346564365465L);
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setPassword("5435763457");
        user.setActive(true);
        user.setVersion(1L);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    @DisplayName("It should return a user when the ID exists.")
    void shouldFindUserById() {
        when(repository.findById(this.user.getId())).thenReturn(Uni.createFrom().item(this.user));

        service.findById(this.user.getId())
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .awaitItem()
                .assertItem(this.user)
                .assertCompleted();

        verify(repository, times(1)).findById(this.user.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("It should throw a ModelNotFoundException when the user does not exist.")
    void shouldFindByIdNotFound() {
        when(repository.findById(this.user.getId())).thenReturn(Uni.createFrom().nullItem());

        service.findById(this.user.getId())
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .awaitFailure()
                .assertFailedWith(ModelNotFoundException.class, "User not found");

        verify(repository, times(1)).findById(this.user.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should return true when user is deleted successfully")
    void shouldDeleteUser() {
        // 1. Arrange
        when(repository.deleteById(this.user.getId()))
                .thenReturn(Uni.createFrom().item(true));

        service.delete(this.user.getId())
                .onItem().invoke(result -> {
                    assertThat(result).isTrue();
                    verify(repository, times(1)).deleteById(this.user.getId());
                    verifyNoMoreInteractions(repository);
                })
                .subscribe().asCompletionStage().join();
    }

    @Test
    @DisplayName("Check if an email address exists and it should return true.")
    void shouldExistsByEmailTrue() {
        when(repository.existsByEmail(this.user.getEmail()))
                .thenReturn(Uni.createFrom().item(true));

        service.existsByEmail(this.user.getEmail())
                .onItem()
                .invoke(x -> {
                    assertThat(x).isTrue();
                    verify(repository, times(1)).existsByEmail(this.user.getEmail());
                    verifyNoMoreInteractions(repository);
                })
                .subscribe().asCompletionStage().join();
    }

    @Test
    @DisplayName("Check if an email address exists and it should return false.")
    void shouldExistsByEmailFalse() {
        when(repository.existsByEmail(this.user.getEmail()))
                .thenReturn(Uni.createFrom().item(false));

        service.existsByEmail(this.user.getEmail())
                .onItem()
                .invoke(x -> {
                    assertThat(x).isFalse();
                    verify(repository, times(1)).existsByEmail(this.user.getEmail());
                    verifyNoMoreInteractions(repository);
                })
                .subscribe().asCompletionStage().join();
    }

    @Test
    @DisplayName("Should create a new user and return the persisted model")
    void shouldCreateNewUser() {
        CreateUserDTO dto = new CreateUserDTO(
                this.user.getUsername(),
                this.user.getEmail(),
                this.user.getPassword()
        );

        when(repository.persist(any(UserModel.class)))
                .thenReturn(Uni.createFrom().item(this.user));

        UserModel createdUser = service.create(dto)
                .await().indefinitely();

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId())
                .isNotNull()
                .isEqualTo(this.user.getId());
        assertThat(createdUser.getUsername()).isEqualTo(dto.username());

        verify(repository, times(1)).persist(any(UserModel.class));
        verifyNoMoreInteractions(repository);
    }

}
