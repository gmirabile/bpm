package it.ariaspa.sireal20.kogito;

import java.io.Serializable;
import java.util.List;

public class Users implements Serializable{

    private static final long serialVersionUID = 1L;

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String telefono;
    private Indirizzo indirizzo; 
    private Number reddito;

    private List<Famigliare> famigliari;

    List<String> emails;

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public List<Famigliare> getFamigliari() {
        return famigliari;
    }

    public void setFamigliari(List<Famigliare> famigliari) {
        this.famigliari = famigliari;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getFamily()
    {
        return famigliari!=null?famigliari.size():0;
    }

    public Number getReddito() {
        return reddito;
    }

    public void setReddito(Number reddito) {
        this.reddito = reddito;
    }

    @Override
    public String toString() {
        return "Users{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", codiceFiscale='" + codiceFiscale + '\'' +
                ", telefono='" + telefono + '\'' +
                ", indirizzo=" + indirizzo +
                ", famigliari=" + famigliari +
                ", emails=" + emails +
                '}';
    }
}
