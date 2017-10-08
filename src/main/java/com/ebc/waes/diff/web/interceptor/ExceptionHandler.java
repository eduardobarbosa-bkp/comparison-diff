package com.ebc.waes.diff.web.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This class is responsible to map the exceptions to json
 * @version 1.0
 * @author eduardo.costa
 * @see javax.ws.rs.ext.ExceptionMapper
 * @since 08/10/2017
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public Response toResponse(Exception exception) {
        Throwable cause = exception.getCause() == null ? exception : exception.getCause();
        String error = getMessage(cause);
        logger.error(error, cause);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorMessage(error)).type(MediaType.APPLICATION_JSON_TYPE.withCharset("utf-8")).build();
    }

    private String getMessage(Throwable throwable){
        return throwable.getMessage() != null ? throwable.getMessage() : throwable.toString();
    }
}