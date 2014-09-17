
package ca.qc.collegeahuntsic.bibliotheque.dto;

import java.sql.Date;

/**
 * Permet de représenter un tuple de la table membre.
 * Je check juste si ca marche
 */

public class ReservationDTO {

    public int idReservation;

    public int idLivre;

    public int idMembre;

    public Date dateReservation;
}
