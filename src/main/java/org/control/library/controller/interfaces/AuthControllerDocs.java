package org.control.library.controller.interfaces;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.control.library.dto.users.CreateUserDTO;
import org.control.library.dto.users.LoginUserDTO;

public interface AuthControllerDocs {
    @POST
    @Path("/login")
    Response login(LoginUserDTO dto);

    @POST
    @Path("/register")
    Response create(CreateUserDTO dto);

    @GET
    @Path("/logout")
    Response logout();
}
