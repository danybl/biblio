// Fichier IReservationService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliotheque.service.interfaces;

import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import java.util.List;
import org.hibernate.Session;

/**
 * Interface de service pour manipuler les réservations dans la base de données.
 *
 */
public interface IReservationService extends IService {
    /**
     * Ajoute une nouvelle réservation dans la base de données.
     *
     * @param session La session à utiliser
     * @param reservationDTO La réservation à ajouter
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws InvalidDTOClassException Si la classe de la réservation n'est pas celle que prend en charge le DAO
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire de la réservation est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void addReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Lit une réservation à partir de la base de données.
     *
     * @param session La session à utiliser
     * @param idReservation L'ID de la réservation à lire
     * @return La réservation
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire de la réservation est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    ReservationDTO getReservation(Session session,
        String idReservation) throws InvalidHibernateSessionException,
        ServiceException;

    /**
     * Met à jour une réservation dans la base de données.
     *
     * @param session La session à utiliser
     * @param reservationDTO La réservation à mettre à jour
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws InvalidDTOClassException Si la classe de la réservation n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void updateReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Supprime une réservation de la base de données.
     *
     * @param session La session à utiliser
     * @param reservationDTO La réservation à supprimer
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws InvalidDTOClassException Si la classe de la réservation n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void deleteReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Trouve toutes les réservations de la base de données. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si
     * aucune réservation n'est trouvée, une {@link List} vide est retournée.
     *
     * @param session La session à utiliser
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste de toutes les réservations ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<ReservationDTO> getAllReservation(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Place une réservation.
     *
     * @param session La session à utiliser
     * @param reservationDTO La réservation à placer
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws MissingDTOException Si le membre n'existe pas ou si le livre n'existe pas
     * @throws InvalidCriterionException Si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws MissingLoanException Si le livre n'a pas encore été prêté
     * @throws ExistingLoanException Si le livre est déjà prêté au membre
     * @throws ExistingReservationException Si le membre a déjà réservé ce livre
     * @throws InvalidDTOClassException Si la classe de la réservation n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void placer(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Utilise une réservation.
     *
     * @param session La session à utiliser
     * @param reservationDTO La réservation à utiliser
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws MissingDTOException Si la réservation n'existe pas, si le membre n'existe pas ou si le livre n'existe pas
     * @throws InvalidCriterionException Si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingReservationException Si la réservation n'est pas la première de la liste
     * @throws ExistingLoanException Si le livre est déjà prêté au membre
     * @throws InvalidLoanLimitException Si le membre a atteint sa limite de prêt
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO ou si la classe du  n'est pas
     *         celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void utiliser(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        ExistingReservationException,
        ExistingLoanException,
        InvalidLoanLimitException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Annule une réservation.
     *
     * @param session La session à utiliser
     * @param reservationDTO Le reservation à annuler
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si la réservation est <code>null</code>
     * @throws MissingDTOException Si la réservation n'existe pas, si le membre n'existe pas ou si le livre n'existe pas
     * @throws InvalidDTOClassException Si la classe de la réservation n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void annuler(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidDTOClassException,
        ServiceException;
}
