
package ca.qc.collegeahuntsic.bibliotheque.service.interfaces;

import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import java.util.List;
import org.hibernate.Session;

public interface IPretService extends IService {
    /**
     * Ajoute un nouveau prêt dans la base de données.
     *
     * @param pretDTO
     *            Le prêt à ajouter
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si la réservation est <code>null</code>
     * @throws InvalidDTOClassException
     *             Si la classe de la réservation n'est pas celle que prend en
     *             charge le DAO
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void addPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Lit un prêt à partir de la base de données.
     *
     * @param connexion
     *            La connexion à utiliser
     * @param idPret
     *            L'ID du prêt à lire
     * @return Le prêt
     * @throws InvalidHibernateSessionException
     *             Si la connexion est <code>null</code>
     * @throws InvalidPrimaryKeyException Si la clef primaire du DTO est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    PretDTO getPret(Session session,
        String idPret) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException;

    /**
     * Met à jour un prêt dans la base de données.
     *
     * @param pretDTO
     *            Le prêt à mettre à jour
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si la réservation est <code>null</code>
     * @throws InvalidDTOClassException
     *             Si la classe de la réservation n'est pas celle que prend en
     *             charge le DAO
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
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
     *            Le prêt à supprimer
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si la réservation est <code>null</code>
     * @throws InvalidDTOClassException
     *             Si la classe de la réservation n'est pas celle que prend en
     *             charge le DAO
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
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
     *            The nom de la propriété à utiliser pour classer
     * @return La liste de toutes les réservations ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    List<PretDTO> getAll(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les prêts à partir d'un meembre. La liste est classée par ordre
     * croissant sur <code>sortByPropertyName</code>. Si aucun prêt n'est
     * trouvé, une {@link List} vide est retournée.
     *
     * @param idMembre
     *            L,id du membre à trouver
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste des réservations correspondantes ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidCriterionException
     *             Si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByMembre(Session session,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les prêts à partir d'un livre. La liste est classée par ordre
     * croissant sur <code>sortByPropertyName</code>. Si aucun prêt n'est
     * trouvé, une {@link List} vide est retournée.
     *
     * @param idLivre
     *            L'id du livre à trouver
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste des réservations correspondantes ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidCriterionException
     *             Si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByLivre(Session session,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Emprunte un livre.
     *
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
     * @throws InvalidPrimaryKeyException Si la clef primaire du DTO est <code>null</code>
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
        InvalidDTOClassException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        InvalidPrimaryKeyException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException;

    /**
     * Renouvele un prêt.
     *
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
     * @throws InvalidPrimaryKeyException Si la clef primaire du DTO est <code>null</code>
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
        InvalidPrimaryKeyException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException;

    /**
     * Retourne un livre.
     *
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
     * @throws InvalidPrimaryKeyException Si la clef primaire du DTO est <code>null</code>
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
        InvalidDTOClassException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        InvalidPrimaryKeyException,
        ExistingLoanException,
        ExistingReservationException,
        MissingLoanException,
        ServiceException;
}
