package it.ariaspa.sireal20.kogito.filter.model;

import io.netty.handler.codec.http.HttpResponseStatus;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MonLog {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        private Type type;
        private long timestamp;
        private String server;
        private String app;
        private String met;
        private String ver;
        private String liv;
        private String esito;
        private String esito1;
        private String esito2;
        private String idDc;
        private String idCoop;
        private String user;
        private String ruolo;
        private String str;
        private String infoIdDc;
        private String canale;
        private String rv;
        private long elapsed;
        private String client;
        private String infoApp;
        private long threads;
        private Map<String, Long> tps = new HashMap();

        public MonLog(Type type) {
            this.type = type;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            Instant instant = Instant.ofEpochMilli(this.timestamp);
            sb.append("|dataOra=");
            sb.append(instant.atZone(ZoneId.systemDefault()).format(formatter));
            sb.append("|timestamp=");
            sb.append(this.timestamp);
            sb.append("|server=");
            sb.append(this.server);
            sb.append("|app=");
            sb.append(this.app);
            sb.append("|met=");
            sb.append(this.met);
            sb.append("|ver=");
            sb.append(this.ver);
            if (this.type != MonLog.Type.START_SRV && this.type != MonLog.Type.START_CLI && this.liv != null) {
                if ("INFO".equals(this.liv)) {
                    sb.append("|liv=INFO|esito=OK");
                } else {
                    sb.append("|liv=");
                    sb.append(this.liv);
                    sb.append("|esito=");
                    sb.append(this.esito);
                    if (this.esito1 != null) {
                        sb.append("|esito1=");
                        sb.append(this.esito1);
                        if (this.esito2 != null) {
                            sb.append("|esito2=");
                            sb.append(this.esito2);
                        }
                    }
                }
            } else {
                sb.append("|liv=N/A|esito=N/A");
            }

            sb.append("|ID_DC=");
            sb.append(this.idDc);
            if (this.idCoop != null) {
                sb.append("|ID_COOP=");
                sb.append(this.idCoop);
            }

            sb.append("|user=");
            sb.append(this.user);
            sb.append("|ruolo=");
            sb.append(this.ruolo);
            sb.append("|str=");
            sb.append(this.str);
            sb.append("|INFO_ID_DC=");
            sb.append(this.infoIdDc);
            sb.append("|canale=");
            sb.append(this.canale);
            sb.append("|RV=");
            sb.append(this.rv);
            if (this.type != MonLog.Type.START_SRV && this.type != MonLog.Type.START_CLI) {
                sb.append("|elapsed=");
                sb.append(this.elapsed);
            } else {
                sb.append("|elapsed=0");
            }

            if (this.type != MonLog.Type.START_SRV && this.type != MonLog.Type.END_SRV) {
                sb.append("|DIR=CLI");
                sb.append("|CLIENT=");
                sb.append(this.client);
            } else {
                sb.append("|DIR=SRV");
            }

            if (this.infoApp != null) {
                sb.append("|infoApp=");
                sb.append(this.infoApp);
            }

            if (this.threads > 0L) {
                sb.append("|threads=");
                sb.append(this.threads);
            }

            for(Map.Entry<String, Long> entry : this.tps.entrySet()) {
                sb.append("|TP_");
                sb.append((String)entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }

            return sb.toString().replace("[\r\n]", "");
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

        public void setApp(String app) {
            this.app = app;
        }

        public void setMet(String met) {
            this.met = met;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public void setLiv(String liv) {
            if (!"INFO".equals(liv) && !"WARNING".equals(liv) && !"ALERT".equals(liv) && !"ERROR".equals(liv)) {
                this.liv = null;
            } else {
                this.liv = liv;
            }

        }

        public void setEsito(String esito) {
            this.esito = esito;
        }

        public void setEsito(Throwable esito) {
            while(esito.getCause() != null) {
                esito = esito.getCause();
            }

            this.liv = "ERROR";
            this.esito = esito.getClass().getName();
        }

        public void setEsito(HttpResponseStatus esito) {
            if (esito == null) {
                this.liv = "N/A";
                this.esito = "N/A";
            } else if ( !(esito.code() >= 200 && esito.code() < 400)) {
                this.liv = "ERROR";
                this.esito = esito.toString();
            } else {
                this.liv = "INFO";
                this.esito = "OK";
            }

        }

        public void setEsito1(String esito1) {
            this.esito1 = esito1;
        }

        public void setEsito2(String esito2) {
            this.esito2 = esito2;
        }

        public void setIdDc(String idDc) {
            this.idDc = idDc;
        }

        public void setIdCoop(String idCoop) {
            this.idCoop = idCoop;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public void setRuolo(String ruolo) {
            this.ruolo = ruolo;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public void setInfoIdDc(String infoIdDc) {
            this.infoIdDc = infoIdDc;
        }

        public void setCanale(String canale) {
            this.canale = canale;
        }

        public void setRv(String rv) {
            this.rv = rv;
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

        public void setThreads(long threads) {
            this.threads = threads;
        }

        public void setTp(String key, long elapsed) {
            this.tps.put(key, elapsed);
        }

        public void setTpDb(long elapsed) {
            this.tps.put("DB", elapsed);
        }

        public void setExtra(MonExtra extra) {
            if (extra != null) {
                this.setEsito1(extra.getEsito1());
                this.setEsito2(extra.getEsito2());
                this.setInfoApp(extra.getInfoApp());
                this.setThreads(extra.getThreads());
                this.tps.putAll(extra.getTps());
            }

        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public String getServer() {
            return this.server;
        }

        public String getApp() {
            return this.app;
        }

        public String getMet() {
            return this.met;
        }

        public String getVer() {
            return this.ver;
        }

        public String getLiv() {
            return this.liv;
        }

        public String getEsito() {
            return this.esito;
        }

        public String getEsito1() {
            return this.esito1;
        }

        public String getEsito2() {
            return this.esito2;
        }

        public String getIdDc() {
            return this.idDc;
        }

        public String getIdCoop() {
            return this.idCoop;
        }

        public String getUser() {
            return this.user;
        }

        public String getRuolo() {
            return this.ruolo;
        }

        public String getStr() {
            return this.str;
        }

        public String getInfoIdDc() {
            return this.infoIdDc;
        }

        public String getCanale() {
            return this.canale;
        }

        public String getRv() {
            return this.rv;
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

        public long getThreads() {
            return this.threads;
        }

        public long getTp(String key) {
            return (Long)this.tps.get(key);
        }

        public long getTpDb() {
            return (Long)this.tps.get("DB");
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
