package it.ariaspa.sireal20.kogito;

import io.quarkus.logging.Log;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;

@ApplicationScoped
public class Service {

    @Inject
    Mailer mailer;

    public Users contratti(Users user) {

        Log.info("Servizio Contratti invocato");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return user;
    }


    public Users preparazione(Users utente) {

        Log.info("Preparazione Contratti invocato");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return utente;
    }

    public Users postalizzazione(Users user) {
        mailer.send(
                Mail.withText("gmirabile@gmail.com",
                        "Postalizzazione "+user.getNome()+" "+user.getCognome(),
                        "Ciao "+user.getNome()+" "+user.getCognome()+", ti invitiamo ad aggiornare i tuoi dati entro la fine dell'anno."
                )
        );
        Log.info("Invio info Postalizzazione Contratti");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return user;
    }



    public Users utente() {
        Users user = new Users();
        user.setEmails(new ArrayList<>());
        user.setNome("Mario");
        user.setCognome("Rossi");
        user.setCodiceFiscale("RSSMRA80A01H501Z");
        user.setTelefono("1234567890");
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia("Via Roma");
        indirizzo.setNumeroCivico("1");
        indirizzo.setCap("00100");
        indirizzo.setCitta("Roma");
        indirizzo.setProvincia("RM");
        indirizzo.setNazione("Italia");
        user.setIndirizzo(indirizzo);
        user.setFamigliari(new ArrayList<>());
        Log.info("User service called");
        Log.info("User: " + user.getNome() + " " + user.getCognome());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return user;
    }


    public void archiviazione() {

        Log.info("Servizio archiviazione invocato");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void revoca() {

        Log.info("Servizio archiviazione invocato");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
