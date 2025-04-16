package it.ariaspa.sireal20.kogito.filter;


import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mapstruct.Qualifier;

@IfBuildProperty(
         name = "spl.http.logging.mon",
         stringValue = "enabled",
         enableIfMissing = true
)
@ConfigMapping(prefix = "spl.http.logging")
public interface LoggingFilterProperties {

    @WithName("application.name")
    @WithDefault("N/A")
    String getApplicationName() ;
    @WithName("mon.enabled")
    @WithDefault("true")
    boolean isMonEnabled();
    @WithName("mon.uri-matchers")
    @WithDefault("/**")
    String[] getMonUriMatchers();
    @WithName("mon.excluded-uri-matchers")
    @WithDefault("/favicon.ico")
    String[] getMonExcludedUriMatchers();
    @WithName("app.enabled")
    @WithDefault("false")
    boolean isAppEnabled();
    @WithName("app.uri-matchers")
    @WithDefault("/**")
    String[] getAppUriMatchers();
    @WithName("app.excluded-uri-matchers")
    @WithDefault("/favicon.ico")
    String[] getAppExcludedUriMatchers();
    @WithDefault("application/x-www-form-urlencoded,multipart/form-data")
    @WithName("app.excluded-content-types")
    String[] getAppExcludedContentTypes();
    @WithDefault("1048576")
    @WithName("app.body-max-length")
    int getAppBodyMaxLength();
}
