
package ca.qc.collegeahuntsic.bibliotheque.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Permet de représenter un tuple de la table membre.
 *
 */

public class MembreDTO extends DTO {

    private static final long serialVersionUID = 1L;

    private int idMembre;

    private String nom;

    private int telephone;

    private int limitePret;

    private int nbPret;

    MembreDTO(int idMembre,
        String nom,
        int telephone,
        int limitePret,
        int nbPret) {
        this.idMembre = idMembre;
        this.nom = nom;
        this.telephone = telephone;
        this.limitePret = limitePret;
        this.nbPret = nbPret;
    }

    //Region get/set
    /**
     * Getter de la variable d'instance <code>this.idMembre</code>.
     *
     * @return La variable d'instance <code>this.idMembre</code>
     */
    public int getIdMembre() {
        return this.idMembre;
    }

    /**
     * Setter de la variable d'instance <code>this.idMembre</code>.
     *
     * @param idMembre La valeur à utiliser pour la variable d'instance <code>this.idMembre</code>
     */
    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    /**
     * Getter de la variable d'instance <code>this.nom</code>.
     *
     * @return La variable d'instance <code>this.nom</code>
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Setter de la variable d'instance <code>this.nom</code>.
     *
     * @param nom La valeur à utiliser pour la variable d'instance <code>this.nom</code>
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter de la variable d'instance <code>this.telephone</code>.
     *
     * @return La variable d'instance <code>this.telephone</code>
     */
    public int getTelephone() {
        return this.telephone;
    }

    /**
     * Setter de la variable d'instance <code>this.telephone</code>.
     *
     * @param telephone La valeur à utiliser pour la variable d'instance <code>this.telephone</code>
     */
    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter de la variable d'instance <code>this.limitePret</code>.
     *
     * @return La variable d'instance <code>this.limitePret</code>
     */
    public int getLimitePret() {
        return this.limitePret;
    }

    /**
     * Setter de la variable d'instance <code>this.limitePret</code>.
     *
     * @param limitePret La valeur à utiliser pour la variable d'instance <code>this.limitePret</code>
     */
    public void setLimitePret(int limitePret) {
        this.limitePret = limitePret;
    }

    /**
     * Getter de la variable d'instance <code>this.nbPret</code>.
     *
     * @return La variable d'instance <code>this.nbPret</code>
     */
    public int getNbPret() {
        return this.nbPret;
    }

    /**
     * Setter de la variable d'instance <code>this.nbPret</code>.
     *
     * @param nbPret La valeur à utiliser pour la variable d'instance <code>this.nbPret</code>
     */
    public void setNbPret(int nbPret) {
        this.nbPret = nbPret;
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
                && obj instanceof MembreDTO;
            if(equals) {
                MembreDTO membreDTO = (MembreDTO) obj;
                EqualsBuilder equalsBuilder = new EqualsBuilder();
                equalsBuilder.appendSuper(super.equals(membreDTO));
                equalsBuilder.append(getIdMembre(),
                    membreDTO.getIdMembre());
                equals = equalsBuilder.isEquals();
            }
        }
        return equals;
    }
}