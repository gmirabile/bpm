package it.ariaspa.sireal20.kogito.filter;


import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import it.ariaspa.sireal20.kogito.filter.model.AuditLog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AppLifecycle {
    private static final Logger auditLog = LoggerFactory.getLogger("AUDIT");

    void onStart(@Observes StartupEvent ev) {
        try {
            AuditLog audit = new AuditLog();
            audit.setApp("SIREAL BPM");
            audit.setEpochTime(System.currentTimeMillis());
            audit.setAccessMod("DIRECTCALL");
            audit.setDataOra(java.time.Instant.ofEpochMilli(audit.getEpochTime()).atZone(java.time.ZoneId.systemDefault()).format(AuditLog.formatter));
            audit.setId("app_startup");
            audit.setAccessMod("start");
            auditLog.info(audit.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        try {
            AuditLog audit = new AuditLog();
            audit.setApp("SIREAL BPM");
            audit.setEpochTime(System.currentTimeMillis());
            audit.setDataOra(java.time.Instant.ofEpochMilli(audit.getEpochTime()).atZone(java.time.ZoneId.systemDefault()).format(AuditLog.formatter));
            audit.setId("app_shutdown");
            audit.setEventDesc("shutdown");
            auditLog.info(audit.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
