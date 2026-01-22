package org.control.library.services.interfaces;

import org.control.library.models.RoleModel;
import org.control.library.models.UserModel;
import org.control.library.utils.annotations.valids.globals.isModelInitialized.IsModelInitialized;

import java.util.List;
import java.util.Map;

public interface ITokenService {
    String generateToken(@IsModelInitialized UserModel user, List<RoleModel> roles);
    String generateRefreshToken(@IsModelInitialized UserModel user);
    Map<String, Object> extractAllClaims(String token);
    String validateToken(String token);
}
