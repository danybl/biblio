
package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces;

import java.util.List;
import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;

/**
 * Interface de service pour manipuler les prêts dans la base de données.
 *
 * @author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran
 *         Singh Dhadda & David Andrés Gallego Mesa
 */

public interface IPretService extends IService {
    /**
     * Ajoute un nouveau prêt dans la base de données.
     * 
     * @param pretDTO
     *            , Le prêt à ajouter
     * @throws InvalidHibernateSessionException
     *             , Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             , Si la réservation est <code>null</code>
     * @throws InvalidDTOClassException
     *             , Si la classe de la réservation n'est pas celle que prend en
     *             charge le DAO
     * @throws ServiceException
     *             , S'il y a une erreur avec la base de données
     */
    void addPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Lit un prêt à partir de la base de données.
     * 
     * @param session
     *            , La session à utiliser
     * @param idPret
     *            , L'ID du prêt à lire
     * @return Le prêt
     * @throws InvalidHibernateSessionException
     *             , Si la session est <code>null</code>
     *             Si la clef primaire du DTO est <code>null</code>
     * @throws ServiceException
     *             , S'il y a une erreur avec la base de données
     */
    PretDTO getPret(Session session,
        String idPret) throws InvalidHibernateSessionException,
        ServiceException;

    /**
     * Met à jour un prêt dans la base de données.
     * 
     * @param pretDTO
     *            , Le prêt à mettre à jour
     * @throws InvalidHibernateSessionException
     *             , Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             , Si la réservation est <code>null</code>
     * @throws InvalidDTOClassException
     *             , Si la classe de la réservation n'est pas celle que prend en
     *             charge le DAO
     * @throws ServiceException
     *             , S'il y a une erreur avec la base de données
     */
    void updatePret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Supprime un membre de la base de données.
     * 
     * @param pretDTO
     *            , Le prêt à supprimer
     * @throws InvalidHibernateSessionException
     *             , Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             , Si la réservation est <code>null</code>
     * @throws InvalidDTOClassException
     *             , Si la classe de la réservation n'est pas celle que prend en
     *             charge le DAO
     * @throws ServiceException
     *             , S'il y a une erreur avec la base de données
     */
    void deletePret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Trouve tous les prêts de la base de données. La liste est classée par
     * ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt n'est
     * trouvé, une {@link List} vide est retournée.
     * 
     * @param sortByPropertyName
     *            ,Le nom de la propriété à utiliser pour classer
     * @return La liste de toutes les réservations ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             , Si la session est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             , Si la propriété à utiliser pour classer est
     *             <code>null</code>
     * @throws ServiceException
     *             , S'il y a une erreur avec la base de données
     */
    List<PretDTO> getAll(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Emprunte un livre.
     * 
     * @param session
     *            , la session à utiliser
     * @param pretDTO
     *            , Le prêt à emprunter
     * @throws InvalidHibernateSessionException
     *             , Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             , Si le livre est <code>null</code>
     * @throws InvalidDTOClassException
     *             , Si la classe du livre n'est pas celle que prend en charge
     *             le DAO
     * @throws MissingDTOException
     *             , Si le livre n'existe pas
     * @throws InvalidCriterionException
     *             , Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             , Si la propriété à utiliser pour classer est
     *             <code>null</code>
     * @throws ExistingLoanException
     *             , Si le livre a été prêté
     * @throws ExistingReservationException
     *             , Si le livre a été réservé
     * @throws ServiceException
     *             , S'il y a une erreur avec la base de données
     */
    void emprunter(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        InvalidLoanLimitException,
        ServiceException;

    /**
     * Renouvele un prêt.
     * 
     * @param session
     *            ,La session à utiliser
     * @param pretDTO
     *            , Le prêt à renouveler
     * @throws InvalidHibernateSessionException
     *             , Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             , Si le livre est <code>null</code>
     * @throws InvalidDTOClassException
     *             , Si la classe du livre n'est pas celle que prend en charge
     *             le DAO
     * @throws MissingDTOException
     *             , Si le livre n'existe pas
     * @throws InvalidCriterionException
     *             , Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             , Si la propriété à utiliser pour classer est
     *             <code>null</code>
     * @throws ExistingLoanException
     *             , Si le livre a été prêté
     * @throws ExistingReservationException
     *             , Si le livre a été réservé
     * @throws ServiceException
     *             , S'il y a une erreur avec la base de données
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
        ServiceException;

    /**
     * Retourne un livre.
     * 
     * @param session
     *            , La session à utiliser
     * @param pretDTO
     *            , Le prêt du livre à retourner
     * @throws InvalidHibernateSessionException
     *             , Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             , Si le livre est <code>null</code>
     * @throws InvalidDTOClassException
     *             , Si la classe du livre n'est pas celle que prend en charge
     *             le DAO
     * @throws MissingDTOException
     *             , Si le livre n'existe pas
     * @throws InvalidCriterionException
     *             , Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             , Si la propriété à utiliser pour classer est
     *             <code>null</code>
     * @throws ExistingLoanException
     *             , Si le livre a été prêté
     * @throws ExistingReservationException
     *             , Si le livre a été réservé
     * @throws ServiceException
     *             , S'il y a une erreur avec la base de données
     */

    void retourner(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        MissingLoanException,
        ServiceException;
}
