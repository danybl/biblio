// Fichier ILivreFacade.java

package ca.qc.collegeahuntsic.bibliotheque.facade.interfaces;

import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import org.hibernate.Session;

/**
 * Interface de façade pour manipuler les livres dans la base de données.
 *
 */
public interface ILivreFacade extends IFacade {
    /**
     * Acquiert un livre.
     *
     * @param session La session à utiliser
     * @param livreDTO Le livre à acquérir
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws FacadeException S'il y a une erreur avec la base de données
     */

    void acquerir(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        FacadeException;

    /**
     * Vend un livre
     *
     * @param session La session à utiliser
     * @param livreDTO Le livre à vendre
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws MissingDTOException Si le livre n'existe pas
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidCriterionValueException Si la value est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingLoanException Si le livre a été prêté
     * @throws ExistingReservationException Si le livre a été réservé
     * @throws FacadeException S'il y a une erreur avec la base de données
     */

    void vendre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        FacadeException;
}
