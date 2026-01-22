package org.control.library.controller.provider;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.control.library.controller.interfaces.AuthControllerDocs;

@Path("/v1/auth")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class AuthController implements AuthControllerDocs {
}
