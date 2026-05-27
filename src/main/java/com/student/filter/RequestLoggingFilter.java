package com.student.filter;

import io.quarkus.logging.Log;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.UUID;
import org.jboss.logmanager.MDC;

@Provider
public class RequestLoggingFilter implements ContainerRequestFilter {

  @Context
  UriInfo info;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    MDC.put("req.id", UUID.randomUUID().toString());
    Log.infof("%s %s", requestContext.getMethod(), info.getPath());
  }
}
