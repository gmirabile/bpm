package it.ariaspa.sireal20.kogito.filter;

import io.quarkus.logging.Log;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.Providers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
@PreMatching
@Provider
public class PreMatchingCustomResponseLogger implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreMatchingCustomResponseLogger.class);

    @Context
    private Providers providers;


    public void filter(ContainerRequestContext requestContext)  {
        Log.info(requestContext.getRequest().getMethod());
        InputStream is = requestContext.getEntityStream();
        byte[] data = null;
        try {
            data = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String body = new String(data, StandardCharsets.UTF_8);
        Log.info("Pre Request Outgoing message "+ body);
        requestContext.setEntityStream(new ByteArrayInputStream(data));
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        LOGGER.info("Method {}",requestContext.getRequest().getMethod());
        String message = new String("Response Outgoing message").concat(System.lineSeparator());
        if (responseContext.getMediaType() != null)
            message = message.concat("Content-Type: ").concat(responseContext.getMediaType().toString()).concat(System.lineSeparator());
        message = message.concat("Payload: ").concat(payloadMessage(responseContext)).concat(System.lineSeparator());
        LOGGER.info(message);
    }

    private String payloadMessage(ContainerResponseContext responseContext) throws IOException {
        String message = new String();
        if (responseContext.hasEntity()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Class<?> entityClass = responseContext.getEntityClass();
            Type entityType = responseContext.getEntityType();
            Annotation[] entityAnnotations = responseContext.getEntityAnnotations();
            MediaType mediaType = responseContext.getMediaType();
            @SuppressWarnings("unchecked")
            MessageBodyWriter<Object> bodyWriter = (MessageBodyWriter<Object>) providers.getMessageBodyWriter(entityClass,
                    entityType,
                    entityAnnotations,
                    mediaType); // I retrieve the bodywriter
            bodyWriter.writeTo(responseContext.getEntity(),
                    entityClass,
                    entityType,
                    entityAnnotations,
                    mediaType,
                    responseContext.getHeaders(),
                    baos); // I use the bodywriter to write to an accessible outputStream
            message = message.concat(new String(baos.toByteArray())); // I convert the stream to a String
        }
        return message;
    }
}
