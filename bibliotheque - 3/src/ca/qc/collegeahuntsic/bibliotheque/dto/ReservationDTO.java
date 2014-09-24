
package ca.qc.collegeahuntsic.bibliotheque.dto;

import java.sql.Timestamp;

/**
 * Permet de représenter un tuple de la table membre.
 * Je check juste si ca marche
 */

public class ReservationDTO extends DTO {

    private static final long serialVersionUID = 1L;

    private int idReservation;

    private int idLivre;

    private int idMembre;

    private Timestamp dateReservation;

    //Region get/set
    public int getIdReservation() {
        return this.idReservation;
    }

    public int getIdLivre() {
        return this.idLivre;
    }

    public int getIdMembre() {
        return this.idMembre;
    }

    public Timestamp getDateReservation() {
        return this.dateReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public void setDateReservation(Timestamp dateReservation) {
        this.dateReservation = dateReservation;
    }
    //EndRegion

}
