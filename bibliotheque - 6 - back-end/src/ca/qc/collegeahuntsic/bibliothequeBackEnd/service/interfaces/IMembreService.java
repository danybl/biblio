
package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces;

import java.util.List;
import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;

/**
 * Interface de base pour les services.<br />
 * Toutes les interfaces de service devrait en hériter.
 *
 */

public interface IMembreService extends IService {
    /**
     * Ajoute un nouveau membre dans la base de données.
     *
     * @param session La session à utiliser
     * @param membreDTO Le membre à ajouter
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void addMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Lit un membre à partir de la base de données.
     *
     * @param session La session à utiliser
     * @param idMembre L'ID du membre à lire
     * @return Le membre
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    MembreDTO getMembre(Session session,
        String idMembre) throws InvalidHibernateSessionException,
        ServiceException;

    /**
     * Met à jour un membre dans la base de données.
     *
     * @param session La session à utiliser
     * @param membreDTO Le membre à mettre à jour
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void updateMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Supprime un membre de la base de données.
     *
     * @param session La session à utiliser
     * @param membreDTO Le membre à supprimer
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void deleteMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Trouve tous les membres de la base de données. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si
     * aucun membre n'est trouvée, une {@link List} vide est retournée.
     *
     * @param session La session à utiliser
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste de toutes les membres ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<MembreDTO> getAllMembre(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les membres à partir d'un numéro de téléphone. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun
     * membre n'est trouvée, une {@link List} vide est retournée.
     *
     * @param session La session à utiliser
     * @param numTel Le numéro de téléphone du membre à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des membres correspondantes ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<MembreDTO> findByTel(Session session,
        String numTel,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les membres à partir d'un nom. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucune
     * réservation n'est trouvée, une {@link List} vide est retournée.
     *
     * @param session La session à utiliser
     * @param nom Le nom du membre à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des membres correspondantes ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<MembreDTO> findByNom(Session session,
        String nom,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Inscrit un membre.
     *
     * @param session La session à utiliser
     * @param membreDTO Le membre à inscrire
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws MissingDTOException Si le membre n'existe pas
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO ou si la classe du  n'est pas
     *         celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void inscrireMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Désinscrit un membre.
     *
     * @param session La session à utiliser
     * @param membreDTO Le membre à désinscrire
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidDTOException Si le membre est <code>null</code>
     * @throws MissingDTOException Si le membre n'existe pas
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingReservationException Si la réservation n'est pas la première de la liste
     * @throws ExistingLoanException Si le livre est déjà prêté au membre
     * @throws InvalidLoanLimitException Si le membre a atteint sa limite de prêt
     * @throws InvalidDTOClassException Si la classe du membre n'est pas celle que prend en charge le DAO ou si la classe du  n'est pas
     *         celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void desinscrireMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingReservationException,
        ExistingLoanException,
        InvalidLoanLimitException,
        InvalidDTOClassException,
        ServiceException;
}
