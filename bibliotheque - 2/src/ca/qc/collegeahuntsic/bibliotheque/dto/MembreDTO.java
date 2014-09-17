
package ca.qc.collegeahuntsic.bibliotheque.dto;

/**
 * Permet de représenter un tuple de la table membre.
 *
 */

public class MembreDTO extends Dto {

    private static final long serialVersionUID = 1L;

    public int getIdMembre() {
        return this.idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public long getTelephone() {
        return this.telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public int getLimitePret() {
        return this.limitePret;
    }

    public void setLimitePret(int limitePret) {
        this.limitePret = limitePret;
    }

    public int getNbPret() {
        return this.nbPret;
    }

    public void setNbPret(int nbPret) {
        this.nbPret = nbPret;
    }

    private int idMembre;

    private String nom;

    private long telephone;

    private int limitePret;

    private int nbPret;
}
