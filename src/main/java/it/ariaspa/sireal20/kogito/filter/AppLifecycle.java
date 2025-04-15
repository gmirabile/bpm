package it.ariaspa.sireal20.kogito.filter;


import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AppLifecycle {
    private static final Logger auditLog = LoggerFactory.getLogger("AUDIT");

    void onStart(@Observes StartupEvent ev) {
        auditLog.info("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent ev) {
        auditLog.info("The application is stopping...");
    }

}
