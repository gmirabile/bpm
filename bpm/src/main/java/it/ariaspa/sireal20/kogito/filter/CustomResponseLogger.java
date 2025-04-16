package it.ariaspa.sireal20.kogito.filter;

import io.opentelemetry.api.trace.Span;
import io.quarkus.logging.Log;
import it.ariaspa.sireal20.kogito.filter.model.AppLog;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

@Provider
public class CustomResponseLogger implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger("APP");

    @Context
    private Providers providers;


    @Override
    public void filter(ContainerRequestContext requestContext)  {
        long startTime = System.currentTimeMillis();
        String server="N/A";
        try {
            server = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException var3) {

        }
        AppLog app = new AppLog(AppLog.Type.START_SRV,1024);
        app.setTimestamp(startTime);
        app.setServer(server);
        app.setIdDc(Span.current().getSpanContext().getTraceId()+","+Span.current().getSpanContext().getSpanId());
        //app.setIdCoop(user.getTrackId());
        InputStream is = requestContext.getEntityStream();
        byte[] data = null;
        try {
            data = is.readAllBytes();
        } catch (IOException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        app.setIn( new String(data, StandardCharsets.UTF_8));
        requestContext.setEntityStream(new ByteArrayInputStream(data));
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        long startTime = System.currentTimeMillis();
        String server="N/A";
        try {
            server = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException var3) {

        }
        AppLog app = new AppLog(AppLog.Type.START_SRV,1024);
        app.setTimestamp(startTime);
        app.setServer(server);
        app.setIdDc(Span.current().getSpanContext().getTraceId()+","+Span.current().getSpanContext().getSpanId());
        InputStream is = requestContext.getEntityStream();
        byte[] data = null;
        try {
            data = is.readAllBytes();
        } catch (IOException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        app.setIn( new String(data, StandardCharsets.UTF_8));
        LOGGER.info(app.toString());
        app = new AppLog(AppLog.Type.END_SRV,1024);
        app.setTimestamp(startTime);
        app.setServer(server);
        app.setIdDc(Span.current().getSpanContext().getTraceId()+","+Span.current().getSpanContext().getSpanId());
        app.setOut(payloadMessage(responseContext));
        LOGGER.info(app.toString());
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
