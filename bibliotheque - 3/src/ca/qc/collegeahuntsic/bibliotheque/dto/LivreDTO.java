
package ca.qc.collegeahuntsic.bibliotheque.dto;

import java.sql.Timestamp;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * DTO de la table <code>livre</code>.
 *
 */
public class LivreDTO extends DTO {
    private static final long serialVersionUID = 1L;

    private String idLivre;

    private String titre;

    private String auteur;

    private Timestamp dateAcquisition;

    /**
     * Getter de la variable d'instance <code>this.idLivre</code>.
     *
     * @return La variable d'instance <code>this.idLivre</code>
     */

    public LivreDTO() {
        super();
    }

    public String getIdLivre() {
        return this.idLivre;
    }

    /**
     * Setter de la variable d'instance <code>this.idLivre</code>.
     *
     * @param string La valeur à utiliser pour la variable d'instance <code>this.idLivre</code>
     */
    public void setIdLivre(String string) {
        this.idLivre = string;
        //return this.idLivre;
    }

    /**
     * Getter de la variable d'instance <code>this.titre</code>.
     *
     * @return La variable d'instance <code>this.titre</code>
     */
    public String getTitre() {
        return this.titre;
    }

    /**
     * Setter de la variable d'instance <code>this.titre</code>.
     *
     * @param titre La valeur à utiliser pour la variable d'instance <code>this.titre</code>
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Getter de la variable d'instance <code>this.auteur</code>.
     *
     * @return La variable d'instance <code>this.auteur</code>
     */
    public String getAuteur() {
        return this.auteur;
    }

    /**
     * Setter de la variable d'instance <code>this.auteur</code>.
     *
     * @param auteur La valeur à utiliser pour la variable d'instance <code>this.auteur</code>
     */
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    /**
     * Getter de la variable d'instance <code>this.dateAcquisition</code>.
     *
     * @return La variable d'instance <code>this.dateAcquisition</code>
     */
    public Timestamp getDateAcquisition() {
        return this.dateAcquisition;
    }

    /**
     * Setter de la variable d'instance <code>this.dateAcquisition</code>.
     *
     * @param dateAcquisition La valeur à utiliser pour la variable d'instance <code>this.dateAcquisition</code>
     */
    public void setDateAcquisition(Timestamp dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(459,
            449);
        hashCodeBuilder.appendSuper(super.hashCode());
        hashCodeBuilder.append(getIdLivre());
        return hashCodeBuilder.toHashCode();
    }
}