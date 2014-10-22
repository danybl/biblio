
package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import org.hibernate.Session;

/**
 * Interface de façade pour manipuler les prêts dans la base de données.
 *
 */

public interface IPretFacade extends IFacade {

    /**
     * Emprunte un livre.
     *
     * @param session
     *            la session à utiliser
     * @param pretDTO
     *            Le prêt à emprunter
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le livre est <code>null</code>
     * @throws InvalidDTOClassException
     *             Si la classe du livre n'est pas celle que prend en charge le
     *             DAO
     * @throws MissingDTOException
     *             Si le livre n'existe pas
     * @throws InvalidCriterionException
     *             Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingLoanException
     *             Si le livre a été prêté
     * @throws ExistingReservationException
     *             Si le livre a été réservé
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */

    void emprunter(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        InvalidLoanLimitException,
        InvalidDTOClassException,
        FacadeException;

    /**
     * Retourne un livre.
     *
     * @param session
     *            la session à utiliser
     * @param pretDTO
     *            Le prêt du livre à retourner
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le livre est <code>null</code>
     * @throws InvalidDTOClassException
     *             Si la classe du livre n'est pas celle que prend en charge le
     *             DAO
     * @throws MissingDTOException
     *             Si le livre n'existe pas
     * @throws InvalidCriterionException
     *             Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingLoanException
     *             Si le livre a été prêté
     * @throws ExistingReservationException
     *             Si le livre a été réservé
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void retourner(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        InvalidLoanLimitException,
        InvalidDTOClassException,
        FacadeException;

    /**
     * Renouvele un prêt.
     *
     * @param session
     *            la session à utiliser
     * @param pretDTO
     *            Le prêt à renouveler
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le livre est <code>null</code>
     * @throws InvalidDTOClassException
     *             Si la classe du livre n'est pas celle que prend en charge le
     *             DAO
     * @throws MissingDTOException
     *             Si le livre n'existe pas
     * @throws InvalidCriterionException
     *             Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingLoanException
     *             Si le livre a été prêté
     * @throws ExistingReservationException
     *             Si le livre a été réservé
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */

    void renouveler(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        FacadeException;
}
