
package ca.qc.collegeahuntsic.bibliotheque.dto;

import java.sql.Timestamp;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Permet de représenter un tuple de la table membre.
 * Je check juste si ca marche
 */

public final class ReservationDTO extends DTO {
    private static final long serialVersionUID = 1L;

    private long idReservation;

    private MembreDTO membreDTO;

    private LivreDTO livreDTO;

    private Timestamp dateReservation;

    public static final String ID_RESERVATION_COLUMN_NAME = "idReservation";

    public static final String ID_MEMBRE_COLUMN_NAME = "idMembre";

    public static final String ID_LIVRE_COLUMN_NAME = "idLivre";

    public static final String DATE_RESERVATION_COLUMN_NAME = "dateReservation";

    public ReservationDTO() {
        super();
    }

    /**
     * Getter de la variable d'instance <code>this.idReservation</code>.
     *
     * @return La variable d'instance <code>this.idReservation</code>
     */
    public long getIdReservation() {
        return this.idReservation;
    }

    /**
     * Setter de la variable d'instance <code>this.idReservation</code>.
     *
     * @param idReservation La valeur à utiliser pour la variable d'instance <code>this.idReservation</code>
     */
    public void setIdReservation(long idReservation) {
        this.idReservation = idReservation;
    }

    /**
     * Getter de la variable d'instance <code>this.membreDTO</code>.
     *
     * @return La variable d'instance <code>this.membreDTO</code>
     */
    public MembreDTO getMembreDTO() {
        return this.membreDTO;
    }

    /**
     * Setter de la variable d'instance <code>this.membreDTO</code>.
     *
     * @param membreDTO La valeur à utiliser pour la variable d'instance <code>this.membreDTO</code>
     */
    public void setMembreDTO(MembreDTO membreDTO) {
        this.membreDTO = membreDTO;
    }

    /**
     * Getter de la variable d'instance <code>this.livreDTO</code>.
     *
     * @return La variable d'instance <code>this.livreDTO</code>
     */
    public LivreDTO getLivreDTO() {
        return this.livreDTO;
    }

    /**
     * Setter de la variable d'instance <code>this.livreDTO</code>.
     *
     * @param livreDTO La valeur à utiliser pour la variable d'instance <code>this.livreDTO</code>
     */
    public void setLivreDTO(LivreDTO livreDTO) {
        this.livreDTO = livreDTO;
    }

    /**
     * Getter de la variable d'instance <code>this.dateReservation</code>.
     *
     * @return La variable d'instance <code>this.dateReservation</code>
     */
    public Timestamp getDateReservation() {
        return this.dateReservation;
    }

    /**
     * Setter de la variable d'instance <code>this.dateReservation</code>.
     *
     * @param dateReservation La valeur à utiliser pour la variable d'instance <code>this.dateReservation</code>
     */
    public void setDateReservation(Timestamp dateReservation) {
        this.dateReservation = dateReservation;
    }

    //EndRegion

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        boolean equals = this == obj;
        if(!equals) {
            equals = obj != null
                && obj instanceof ReservationDTO;
            if(equals) {
                ReservationDTO reservationDTO = (ReservationDTO) obj;
                EqualsBuilder equalsBuilder = new EqualsBuilder();
                equalsBuilder.appendSuper(super.equals(reservationDTO));
                equalsBuilder.append(getIdReservation(),
                    reservationDTO.getIdReservation());
                equals = equalsBuilder.isEquals();
            }
        }
        return equals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(25,
            15);
        hashCodeBuilder.appendSuper(super.hashCode());
        hashCodeBuilder.append(getIdReservation());
        return hashCodeBuilder.toHashCode();
    }
}
