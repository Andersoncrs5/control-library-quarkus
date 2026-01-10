package org.controllibrary.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.controllibrary.models.RoleModel;
import org.controllibrary.models.UserModel;
import org.controllibrary.models.UserRoleModel;
import org.controllibrary.repositories.UserRoleRepository;
import org.controllibrary.services.providers.UserRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@QuarkusTest
public class UserRoleServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserRoleServiceTest.class);
    @Inject
    UserRoleService service;

    @InjectMock
    UserRoleRepository repository;

    UserModel user = new UserModel();
    RoleModel role = new RoleModel();
    UserRoleModel userRole = new UserRoleModel();

    @BeforeEach
    void setup() {
        role.setId(5736475346564365465L);
        role.setName("USER_ROLE");
        role.setActive(true);
        role.setVersion(1L);
        role.setCreatedAt(OffsetDateTime.now());
        role.setUpdatedAt(OffsetDateTime.now());

        user.setId(5736475346564365465L);
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setPassword("5435763457");
        user.setActive(true);
        user.setVersion(1L);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());

        userRole.setId(5716475346564365465L);
        userRole.setRole(this.role);
        userRole.setUser(this.user);
        userRole.setVersion(1L);
        userRole.setCreatedAt(OffsetDateTime.now());
        userRole.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    void shouldReturnRolesOfUSer() {
        var roles = List.of(this.role);
        when(repository.findRolesByUser(this.user))
                .thenReturn(Uni.createFrom().item(roles));

        this.service.getByAllRolesByUser(this.user)
                .onItem()
                .invoke(x -> {
                   assertThat(x).isEqualTo(roles);

                   verify(repository, times(1)).findRolesByUser(this.user);
                   verifyNoMoreInteractions(repository);
                })
                .subscribe()
                .asCompletionStage()
                .join();

    }

    @Test
    void shouldDeleteUserRole() {
        when(repository.deleteById(this.userRole.getId()))
                .thenReturn(Uni.createFrom().item(true));

        this.service.delete(this.userRole.getId())
                .onItem()
                .invoke(x -> {
                    assertThat(x).isTrue();

                    verify(this.repository, times(1)).deleteById(this.userRole.getId());
                    verifyNoMoreInteractions(repository);
                })
                .subscribe()
                .asCompletionStage()
                .join();
    }

    @Test
    void shouldAddedRoleToUser() {
        when(repository.persist(any(UserRoleModel.class)))
                .thenReturn(Uni.createFrom().item(this.userRole));

        this.service.create(this.user, this.role)
                .onItem()
                .invoke(x -> {
                    assertThat(x).isEqualTo(this.userRole);

                    verify(repository, times(1)).persist(any(UserRoleModel.class));
                    verifyNoMoreInteractions(repository);
                })
                .subscribe()
                .asCompletionStage()
                .join();
    }

    @Test
    void shouldReturnTrueWhenExistsUserRoleByUserAndRole() {
        when(repository.existsByUserAndRole(this.user, this.role))
                .thenReturn(Uni.createFrom().item(true));

        this.service.existsByUserAndRole(user, role)
                .onItem()
                .invoke(x -> {
                    assertThat(x).isTrue();

                    verify(repository, times(1)).existsByUserAndRole(user, role);
                    verifyNoMoreInteractions(repository);
                })
                .subscribe()
                .asCompletionStage()
                .join();
    }

    @Test
    void shouldReturnFalseWhenExistsUserRoleByUserAndRole() {
        when(repository.existsByUserAndRole(this.user, this.role))
                .thenReturn(Uni.createFrom().item(false));

        this.service.existsByUserAndRole(user, role)
                .onItem()
                .invoke(x -> {
                    assertThat(x).isFalse();

                    verify(repository, times(1)).existsByUserAndRole(user, role);
                    verifyNoMoreInteractions(repository);
                })
                .subscribe()
                .asCompletionStage()
                .join();
    }

}
