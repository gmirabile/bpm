package it.ariaspa.sireal20.kogito.filter;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanId;
import io.quarkus.vertx.web.RouteFilter;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.RoutingContext;
import it.ariaspa.sireal20.kogito.filter.model.AntPathRequestMatcher;
import it.ariaspa.sireal20.kogito.filter.model.AppLog;
import it.ariaspa.sireal20.kogito.filter.model.MonLog;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class LoggingRouteFilter {

    private static final Logger logMon = LoggerFactory.getLogger("MON");
    private static final Logger logApp = LoggerFactory.getLogger("APP");
    @Inject
    private LoggingFilterProperties properties;
    private final Collection<AntPathRequestMatcher> monUriMatchers = new ArrayList();
    private final Collection<AntPathRequestMatcher> monExcludedUriMatchers = new ArrayList();
    private final Collection<AntPathRequestMatcher> appUriMatchers = new ArrayList();
    private final Collection<AntPathRequestMatcher> appExcludedUriMatchers = new ArrayList();
    private final Collection<String> appExcludedContentTypes = new ArrayList();
    private String server;

    @PostConstruct
    public void init() {
        Arrays.stream(properties.getMonUriMatchers()).forEach((uri) -> this.monUriMatchers.add(new AntPathRequestMatcher(uri)));
        Arrays.stream(properties.getMonExcludedUriMatchers()).forEach((uri) -> this.monExcludedUriMatchers.add(new AntPathRequestMatcher(uri)));
        Arrays.stream(properties.getAppUriMatchers()).forEach((uri) -> this.appUriMatchers.add(new AntPathRequestMatcher(uri)));
        Arrays.stream(properties.getAppExcludedUriMatchers()).forEach((uri) -> this.appExcludedUriMatchers.add(new AntPathRequestMatcher(uri)));
        Arrays.stream(properties.getAppExcludedContentTypes()).forEach((contentType) -> this.appExcludedContentTypes.add(contentType));
    }


    @RouteFilter(100)
    void myFilter(RoutingContext rc) {
        long startTime = System.currentTimeMillis();
        try {
            this.server = InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException var3) {
            this.server = "N/A";
        }
        String request = rc.request().uri();
        MonLog mon = new MonLog(MonLog.Type.START_SRV);
        AppLog appLog = new AppLog(AppLog.Type.START_SRV,1024);
        @Nullable User user=rc.user();

        boolean isMon = this.properties.isMonEnabled() && this.monUriMatchers.stream().anyMatch((uri) -> uri.matches(request)) && this.monExcludedUriMatchers.stream().noneMatch((uri) -> uri.matches(request));
        boolean isApp = isMon && this.properties.isAppEnabled() && this.appUriMatchers.stream().anyMatch((uri) -> uri.matches(request)) && this.appExcludedUriMatchers.stream().noneMatch((uri) -> uri.matches(request));
        if (isMon){
            mon.setTimestamp(startTime);
            mon.setApp(this.properties.getApplicationName());
            mon.setServer(this.server);
            mon.setMet( rc.request().method() + "#" + rc.request().uri());
            mon.setVer("N/A");
            mon.setIdCoop(Span.current().getSpanContext().getTraceId()+","+Span.current().getSpanContext().getSpanId());
            mon.setIdDc(rc.request().getHeader("X-B3-TraceId")+","+rc.request().getHeader("X-B3-SpanId"));
            mon.setClient(rc.request().remoteAddress().host());
            mon.setInfoApp(this.properties.getApplicationName());
            mon.setInfoIdDc(!SpanId.isValid(rc.request().getHeader("X-B3-SpanId")) ? "CREATO" : "PROPAGATO[" + rc.request().getHeader("X-B3-AppId") + "]");
            mon.setTimestamp(System.currentTimeMillis());
            mon.setCanale("REST");
            if(user!=null){
                mon.setUser(user.principal().getString("username"));
                mon.setUser(user.principal().getString("username"));
                mon.setUser(user.principal().getString("username"));
            }
/*
            logMon.info("Request: " + rc.request().method() + " " + rc.request().absoluteURI());
            logMon.info("Path: " + rc.currentRoute().getName());
            logMon.info("Query params: " + rc.queryParams());
            logMon.info("Headers: " + rc.request().headers());
            logMon.info("Body: " + rc.getBodyAsString());*/
        }
        if(isMon){
            logMon.info(mon.toString());
        }
        if(isApp){
            logApp.info(appLog.toString());
        }
        rc.response().endHandler(
                buffer -> {
                    long endTime = System.currentTimeMillis();
                /*    if(buffer!= null)
                        Log.info("Response body: " + buffer.toString());
                    else
                        Log.info("Response body: null");
                    Log.info("Uri of response: " + rc.request().absoluteURI());
                    Log.info("Response status code: " + rc.response().getStatusCode());
                    Log.info("Response Message code: " + rc.response().getStatusMessage());*/
                }
        );
        rc.request().body().onComplete(bufferHandler -> {
            String body = bufferHandler.toString();
       /*     Log.info("Body: " + body);
            //Log.info("Response status message: " + rc.response().send().onComplete(event -> {event.result().})getStatusMessage());
            rc.queryParams().forEach( (t, u) -> {
                Log.info("Param Key: " +t+" Value: " + u);
            });*/
            // `body` now contains you what you POST'ed
        }).onFailure(throwable ->
        {
            // Handle the error
      /*      Log.error("Error reading request body: " + throwable.getMessage());
            // You can also log the request URI and method if needed
            Log.error("Uri: " + rc.request().absoluteURI());
            Log.error("Method: " + rc.request().method());
            Log.error("Error: " + throwable.getMessage());*/
        }
        );



        if(isMon){
            logMon.info(mon.toString());
        }
        if(isApp){
            logApp.info(appLog.toString());
        }
        rc.next();
    }
}
