package it.ariaspa.sireal20.kogito.filter.model;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AppLog {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private Type type;
    private int bodyMaxLength;
    private long timestamp;
    private String server;
    private String idDc;
    private String idCoop;
    private long elapsed;
    private String client;
    private String infoApp;
    private String in;
    private String out;

    public AppLog(Type type, int bodyMaxLength) {
        this.type = type;
        this.bodyMaxLength = bodyMaxLength;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Instant instant = Instant.ofEpochMilli(this.timestamp);
        sb.append("|dataOra=");
        sb.append(instant.atZone(ZoneId.systemDefault()).format(formatter));
        sb.append("|timestamp=");
        sb.append(this.timestamp);
        sb.append("|server=");
        sb.append(this.server == null ? this.server : this.server.replace("[\r\n]", ""));
        sb.append("|ID_DC=");
        sb.append(this.idDc);
        if (this.idCoop != null) {
            sb.append("|ID_COOP=");
            sb.append(this.idCoop.replace("[\r\n]", ""));
        }

        if (this.type != AppLog.Type.START_SRV && this.type != AppLog.Type.START_CLI) {
            sb.append("|elapsed=");
            sb.append(this.elapsed);
        } else {
            sb.append("|elapsed=0");
        }

        if (this.type != AppLog.Type.START_SRV && this.type != AppLog.Type.END_SRV) {
            sb.append("|DIR=CLI");
            sb.append("|CLIENT=");
            sb.append(this.client.replace("[\r\n]", ""));
        } else {
            sb.append("|DIR=SRV");
        }

        if (this.infoApp != null) {
            sb.append("|infoApp=");
            sb.append(this.infoApp.replace("[\r\n]", ""));
        }

        if (this.type != AppLog.Type.START_SRV && this.type != AppLog.Type.START_CLI) {
            sb.append("|OUT=");
            sb.append(StringUtils.defaultString(this.out));
        }else  {
            sb.append("|IN=");
            sb.append(StringUtils.defaultString(this.in));
        }


        return sb.toString();
    }

    public void setTimestamp(long timestamp) {
        if (timestamp > 0L) {
            this.timestamp = timestamp;
        } else {
            this.timestamp = 0L;
        }

    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setIdDc(String idDc) {
        this.idDc = idDc;
    }

    public void setIdCoop(String idCoop) {
        this.idCoop = idCoop;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setInfoApp(String infoApp) {
        this.infoApp = infoApp;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public void setExtra(AppExtra extra) {
        if (extra != null) {
            this.setInfoApp(extra.getInfoApp());
        }

    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getServer() {
        return this.server;
    }

    public String getIdDc() {
        return this.idDc;
    }

    public String getIdCoop() {
        return this.idCoop;
    }

    public long getElapsed() {
        return this.elapsed;
    }

    public String getClient() {
        return this.client;
    }

    public String getInfoApp() {
        return this.infoApp;
    }

    public String getIn() {
        return this.in;
    }

    public String getOut() {
        return this.out;
    }

    public static enum Type {
        START_SRV,
        END_SRV,
        START_CLI,
        END_CLI;

        private Type() {
        }
    }
}
