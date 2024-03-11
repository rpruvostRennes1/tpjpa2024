package jpa.servlet;

import java.util.HashSet;
import java.util.Set;

import jpa.rest.SwaggerResource;
import jpa.rest.TicketResource;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/")
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();


        // SWAGGER endpoints
        resources.add(OpenApiResource.class);

        //Your own resources.
        resources.add(TicketResource.class);

        resources.add(SwaggerResource.class);
        return resources;
    }
}
