package org.controllibrary.services.interfaces;

import org.controllibrary.models.RoleModel;
import org.controllibrary.models.UserModel;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.Map;

public interface ITokenService {
    String generateToken(UserModel user, List<RoleModel> roles);
    String generateRefreshToken(UserModel user);
    String validateToken(String token);
    Map<String, Object> extractAllClaims(String token);
    Long getUserIdFromToken();
}
