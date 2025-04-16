package it.ariaspa.sireal20.kogito.filter.model;

import java.util.HashMap;
import java.util.Map;

public class MonExtra {
    private String esito1;
    private String esito2;
    private String infoApp;
    private long threads;
    private Map<String, Long> tps = new HashMap();

    public MonExtra() {
    }

    public void setEsito1(String esito1) {
        this.esito1 = esito1;
    }

    public void setEsito2(String esito2) {
        this.esito2 = esito2;
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

    public String getEsito1() {
        return this.esito1;
    }

    public String getEsito2() {
        return this.esito2;
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

    public Map<String, Long> getTps() {
        return this.tps;
    }
}
