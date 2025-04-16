package it.ariaspa.sireal20.kogito;

import io.quarkus.logging.Log;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@ApplicationScoped
public class Service {
    private static final Logger Log = LoggerFactory.getLogger(Service.class);

    @Inject
    Mailer mailer;

    public Users contratti(Users user) {

        System.out.println("Servizio Contratti invocato");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return user;
    }


    public Users preparazione(Users utente) {

        System.out.println("Preparazione Contratti invocato");
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
        System.out.println("Invio info Postalizzazione Contratti");
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
        System.out.println("User service called");
        System.out.println("User: " + user.getNome() + " " + user.getCognome());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return user;
    }


    public void archiviazione() {

        System.out.println("Servizio archiviazione invocato");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void revoca() {

        System.out.println("Servizio archiviazione invocato");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
