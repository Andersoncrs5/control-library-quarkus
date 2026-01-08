package org.controllibrary.services.interfaces;

import io.smallrye.mutiny.Uni;
import org.controllibrary.models.UserModel;

public interface IUserService {
    Uni<UserModel> findById(Long id);
}
