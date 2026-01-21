package org.control.library.unit;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.control.library.models.RoleModel;
import org.control.library.repositories.RoleRepository;
import org.control.library.services.providers.RoleService;
import org.control.library.utils.exception.ModelNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        role.setId(111111146564365465L);
        role.setName("SUPER_ADM");
        role.setActive(true);
        role.setActive(true);
        role.setVersion(1L);
        role.setCreatedAt(OffsetDateTime.now());
        role.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    void shouldReturnTrueWhenCheckIfExistsRoleByName() {
        when(repository.existsByName(this.role.getName())).thenReturn(true);

        Boolean exists = this.service.existsByName(this.role.getName());

        assertThat(exists).isTrue();

        verify(repository, times(1)).existsByName(this.role.getName());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnFalseWhenCheckIfExistsRoleByName() {
        when(repository.existsByName(this.role.getName())).thenReturn(false);

        Boolean exists = this.service.existsByName(this.role.getName());

        assertThat(exists).isFalse();

        verify(repository, times(1)).existsByName(this.role.getName());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnNullWhenGetByNameSimple() {
        when(repository.findByName(role.getName())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                () -> this.service.getByNameSimple(role.getName()));

        verify(repository, times(1)).findByName(role.getName());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnRoleWhenGetByNameSimple() {
        when(repository.findByName(role.getName())).thenReturn(Optional.of(role));

        RoleModel model = this.service.getByNameSimple(role.getName());

        assertThat(model.getId()).isEqualTo(role.getId());

        verify(repository, times(1)).findByName(role.getName());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldDeleteRole() {
        doNothing().when(repository).delete(this.role);

        this.service.delete(role);

        verify(repository, times(1)).delete(role);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldCreateNewRole() {
        doNothing().when(repository).persist(any(RoleModel.class));

        RoleModel roleModel = this.service.create(role);

        assertThat(roleModel.getId()).isEqualTo(role.getId());

        verify(repository, times(1)).persist(any(RoleModel.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnNullWhenGetByName() {
        when(repository.findByName(role.getName())).thenReturn(Optional.empty());

        Optional<RoleModel> optional = this.service.getByName(role.getName());

        assertThat(optional.isEmpty()).isTrue();

        verify(repository, times(1)).findByName(role.getName());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnRoleWhenGetByName() {
        when(repository.findByName(role.getName())).thenReturn(Optional.of(role));

        var model = this.service.getByName(role.getName());

        assertThat(model.isEmpty()).isFalse();

        verify(repository, times(1)).findByName(role.getName());
        verifyNoMoreInteractions(repository);
    }

}
