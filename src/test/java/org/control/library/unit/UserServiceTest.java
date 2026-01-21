package org.control.library.unit;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.control.library.models.UserModel;
import org.control.library.repositories.UserRepository;
import org.control.library.services.providers.UserService;
import org.control.library.utils.exception.ModelNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
public class UserServiceTest {

    @Inject
    UserService service;

    @InjectMock
    UserRepository repository;

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
    @DisplayName("It should return a user when the ID exists METHOD: findByIdOptional")
    void shouldFindUserByIdOptional() {
        when(repository.findByIdOptional(this.user.getId())).thenReturn(Optional.of(this.user));

        Optional<UserModel> opt = this.service.getById(user.getId());

        assertThat(opt.isPresent()).isTrue();

        verify(repository, times(1)).findByIdOptional(this.user.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("It should return null when the ID exists METHOD: findByIdOptional")
    void shouldFindUserByIdOptionalReturnNull() {
        when(repository.findByIdOptional(this.user.getId())).thenReturn(Optional.empty());

        Optional<UserModel> opt = this.service.getById(user.getId());

        assertThat(opt.isEmpty()).isTrue();

        verify(repository, times(1)).findByIdOptional(this.user.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowModelNotFoundWhenFindUserById() {
        when(repository.findByIdOptional(this.user.getId())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                () -> this.service.getByIdSimple(this.user.getId()));

        verify(repository, times(1)).findByIdOptional(this.user.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("It should return a user when the ID exists METHOD: findById")
    void shouldFindUserById() {
        when(repository.findByIdOptional(this.user.getId())).thenReturn(Optional.of(this.user));

        UserModel model = this.service.getByIdSimple(user.getId());

        assertThat(model.getId()).isEqualTo(this.user.getId());

        verify(repository, times(1)).findByIdOptional(this.user.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldDeleteUserModel() {
        doNothing().when(repository).delete(this.user);

        this.service.delete(this.user);

        verify(repository, times(1)).delete(this.user);
        verifyNoMoreInteractions(repository);
    }

}
