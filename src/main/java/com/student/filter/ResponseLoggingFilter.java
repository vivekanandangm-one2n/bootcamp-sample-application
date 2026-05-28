package com.student.filter;

import io.quarkus.logging.Log;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ResponseLoggingFilter implements ContainerResponseFilter {

  @Context
  UriInfo info;

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) throws IOException {
    Log.infof("%s %s > %d", requestContext.getMethod(), info.getPath(),
        responseContext.getStatus());
  }
}
