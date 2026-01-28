package org.control.library.utils.res.swagger;

import org.control.library.utils.res.ResponseHTTP;
import org.control.library.utils.res.ResponseLogin;

public record ResponseLoginSwagger(
        ResponseHTTP<ResponseLogin> login
) {
}
