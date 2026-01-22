package org.control.library.unit;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.control.library.models.RoleModel;
import org.control.library.models.UserModel;
import org.control.library.models.UserRoleModel;
import org.control.library.repositories.UserRoleRepository;
import org.control.library.services.providers.UserRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
public class UserRoleServiceTest {

    @Inject
    UserRoleService service;

    @InjectMock
    UserRoleRepository repository;

    UserModel user = new UserModel();
    RoleModel role = new RoleModel();
    UserRoleModel userRole = new UserRoleModel();

    @BeforeEach
    void setup() {
        user.setId(5736475346564365465L);
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setPassword("5435763457434554646745");
        user.setActive(true);
        user.setVersion(1L);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());

        role.setId(111111146564365465L);
        role.setName("SUPER_ADM");
        role.setActive(true);
        role.setActive(true);
        role.setVersion(1L);
        role.setCreatedAt(OffsetDateTime.now());
        role.setUpdatedAt(OffsetDateTime.now());

        userRole.setId(5736475346599965465L);
        userRole.setRole(role);
        userRole.setUser(user);
        userRole.setVersion(1L);
        userRole.setCreatedAt(OffsetDateTime.now());
        userRole.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    void shouldCreateNewUserRole() {
        doNothing().when(repository).persist(userRole);

        UserRoleModel model = this.service.create(user, role);

        assertThat(model.getRole().getId()).isEqualTo(userRole.getRole().getId());
        assertThat(model.getUser().getId()).isEqualTo(userRole.getUser().getId());

        verify(repository, times(1)).persist(any(UserRoleModel.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnUserRoleModelWhenFindByUserAndRole() {
        when(repository.findByUserAndRole(user, role)).thenReturn(Optional.of(userRole));

        Optional<UserRoleModel> optional = this.service.findByUserAndRole(user, role);

        assertThat(optional.isPresent()).isTrue();

        verify(repository, times(1)).findByUserAndRole(user, role);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnNullWhenFindByUserAndRole() {
        when(repository.findByUserAndRole(user, role)).thenReturn(Optional.empty());

        Optional<UserRoleModel> optional = this.service.findByUserAndRole(user, role);

        assertThat(optional.isEmpty()).isTrue();

        verify(repository, times(1)).findByUserAndRole(user, role);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldDeleteUserRoleModel() {
        doNothing().when(repository).delete(userRole);

        this.service.delete(userRole);

        verify(repository, times(1)).delete(userRole);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnTrueWhenExistsByUserAndRole() {
        when(repository.existsByUserAndRole(user, role)).thenReturn(true);

        Boolean exists = this.service.existsByUserAndRole(user, role);

        assertThat(exists).isTrue();

        verify(repository, times(1)).existsByUserAndRole(user, role);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnFalseWhenExistsByUserAndRole() {
        when(repository.existsByUserAndRole(user, role)).thenReturn(false);

        Boolean exists = this.service.existsByUserAndRole(user, role);

        assertThat(exists).isFalse();

        verify(repository, times(1)).existsByUserAndRole(user, role);
        verifyNoMoreInteractions(repository);
    }

}
