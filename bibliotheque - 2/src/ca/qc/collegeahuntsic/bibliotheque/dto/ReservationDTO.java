
package ca.qc.collegeahuntsic.bibliotheque.dto;

import java.sql.Date;

/**
 * Permet de représenter un tuple de la table membre.
 * Je check juste si ca marche
 */

public class ReservationDTO extends DTO {

    private static final long serialVersionUID = 1L;

    private int idReservation;

    private int idLivre;

    private int idMembre;

    private Date dateReservation;

    public ReservationDTO(int idReservation,
        int idLivre,
        int idMembre,
        Date dateReservation) {
        this.idReservation = idReservation;
        this.idLivre = idLivre;
        this.idMembre = idMembre;
        this.dateReservation = dateReservation;
    }

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

    public Date getDateReservation() {
        return this.dateReservation;
    }
    //EndRegion

}
