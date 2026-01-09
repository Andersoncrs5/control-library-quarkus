package org.controllibrary.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import org.controllibrary.models.RoleModel;
import org.controllibrary.repositories.RoleRepository;
import org.controllibrary.services.providers.RoleService;
import org.controllibrary.utils.exceptions.ModelNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@QuarkusTest
public class RoleServiceTest {

    @Inject
    RoleService service;

    @InjectMock
    RoleRepository repository;

    RoleModel role = new RoleModel();

    @BeforeEach
    void setup() {
        role.setId(5736475346564365465L);
        role.setName("USER_ROLE");
        role.setActive(true);
        role.setVersion(1L);
        role.setCreatedAt(OffsetDateTime.now());
        role.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    void shouldFindById() {
        when(repository.findById(this.role.getId())).thenReturn(Uni.createFrom().item(this.role));

        service.findById(this.role.getId())
                .onItem()
                .invoke(x -> {
                    assertThat(x).isEqualTo(this.role);

                    verify(repository, times(1)).findById(this.role.getId());
                    verifyNoMoreInteractions(repository);
                })
                .subscribe()
                .asCompletionStage()
                .join();
    }

    @Test
    void shouldFindByIdNotFound() {
        when(repository.findById(this.role.getId())).thenReturn(Uni.createFrom().nullItem());

        this.service.findById(this.role.getId())
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .awaitFailure()
                .assertFailedWith(ModelNotFoundException.class, "Role not found");

        verify(repository, times(1)).findById(this.role.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldFindByName() {
        when(repository.findByName(this.role.getName()))
                .thenReturn(Uni.createFrom().item(this.role));

        this.service.findByName(this.role.getName())
                .onItem()
                .invoke(x -> {
                    assertThat(x).isEqualTo(this.role);

                    verify(repository, times(1)).findByName(this.role.getName());
                    verifyNoMoreInteractions(repository);
                })
                .subscribe()
                .asCompletionStage()
                .join();
    }

    @Test
    void shouldFindByNameNotFound() {
        when(repository.findByName(this.role.getName()))
                .thenReturn(Uni.createFrom().nullItem());

        this.service.findByName(this.role.getName())
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .awaitFailure()
                .assertFailedWith(ModelNotFoundException.class, "Role not found");

        verify(repository, times(1)).findByName(this.role.getName());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldExistsByName() {
        when(repository.existsByName(this.role.getName()))
                .thenReturn(Uni.createFrom().item(true));

        service.existsByName(this.role.getName())
                .onItem()
                .invoke(x -> {
                    assertThat(x).isTrue();
                    verify(repository, times(1)).existsByName(this.role.getName());
                    verifyNoMoreInteractions(repository);
                })
                .subscribe().asCompletionStage().join();

    }

    @Test
    void shouldDeleteUser() {
        when(repository.deleteById(this.role.getId()))
                .thenReturn(Uni.createFrom().item(true));

        service.delete(this.role.getId())
                .onItem()
                .invoke(x -> {
                    assertThat(x).isTrue();

                    verify(repository, times(1)).deleteById(this.role.getId());
                    verifyNoMoreInteractions(repository);
                })
                .subscribe()
                .asCompletionStage()
                .join();
    }

}
