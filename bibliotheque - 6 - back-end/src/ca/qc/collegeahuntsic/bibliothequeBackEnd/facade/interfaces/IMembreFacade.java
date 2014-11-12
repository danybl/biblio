// Fichier IMembreFacade.java
// Auteur : Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
// Date de création : 2014-11-04

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import org.hibernate.Session;

/**
 * Interface de façade pour manipuler les membres dans la base de données.
 *
 * @author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
 */

public interface IMembreFacade extends IFacade {
    /**
     * Inscrit un membre.
     *
     * @param session La session à utiliser
     * @param membreDTO Le membre à inscrire
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void inscrireMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        FacadeException;

    /**
     * Désinscrit un membre.
     *
     * @param session La session à utiliser
     * @param membreDTO Le membre à désinscrire
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws FacadeException S'il y a une erreur avec la base de données
     */
    void desinscrireMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        FacadeException;

    /**
     * Lit un membre.
     *
     * @param session La session à utiliser
     * @param idMembre L'ID du membre à lire
     * @return Le membre
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws FacadeException  S'il y a une erreur avec la base de données
     */
    MembreDTO getMembre(Session session,
        String idMembre) throws InvalidHibernateSessionException,
        FacadeException;
}
