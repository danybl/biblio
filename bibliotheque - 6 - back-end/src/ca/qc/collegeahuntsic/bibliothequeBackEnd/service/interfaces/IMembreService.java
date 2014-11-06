
package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces;

import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import org.hibernate.Session;

/**
 * Interface de base pour les services.<br />
 * Toutes les interfaces de service devrait en hériter.
 *
 * @author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran
 *         Singh Dhadda & David Andrés Gallego Mesa
 */

public interface IMembreService extends IService {
    /**
     * Ajoute un nouveau membre dans la base de données.
     *
     * @param session
     *            La session à utiliser
     * @param membreDTO
     *            Le membre à ajouter
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le membre est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void addMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException;

    /**
     * Lit un membre à partir de la base de données.
     *
     * @param session
     *            La session à utiliser
     * @param idMembre
     *            L'ID du membre à lire
     * @return Le membre
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    MembreDTO getMembre(Session session,
        String idMembre) throws InvalidHibernateSessionException,
        ServiceException;

    /**
     * Met à jour un membre dans la base de données.
     *
     * @param session
     *            La session à utiliser
     * @param membreDTO
     *            Le membre à mettre à jour
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le membre est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void updateMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException;

    /**
     * Supprime un membre de la base de données.
     *
     * @param session
     *            La session à utiliser
     * @param membreDTO
     *            Le membre à supprimer
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le membre est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void deleteMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException;

    /**
     * Trouve tous les membres de la base de données. La liste est classée par
     * ordre croissant sur <code>sortByPropertyName</code>. Si aucun membre
     * n'est trouvée, une {@link List} vide est retournée.
     *
     * @param session
     *            La session à utiliser
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste de toutes les membres ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    List<MembreDTO> getAllMembre(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les membres à partir d'un numéro de téléphone. La liste est
     * classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun
     * membre n'est trouvée, une {@link List} vide est retournée.
     *
     * @param session
     *            La session à utiliser
     * @param numTel
     *            Le numéro de téléphone du membre à trouver
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste des membres correspondantes ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidCriterionException
     *             Si l'ID du membre est <code>null</code>
     * @throws InvalidCriterionValueException
     *             Si le titre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    List<MembreDTO> findByTel(Session session,
        String numTel,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les membres à partir d'un nom. La liste est classée par ordre
     * croissant sur <code>sortByPropertyName</code>. Si aucune réservation
     * n'est trouvée, une {@link List} vide est retournée.
     *
     * @param session
     *            La session à utiliser
     * @param nom
     *            Le nom du membre à trouver
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste des membres correspondantes ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidCriterionException
     *             Si l'ID du membre est <code>null</code>
     * @throws InvalidCriterionValueException
     *             Si le titre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
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
     * @param session
     *            La session à utiliser
     * @param membreDTO
     *            Le membre à inscrire
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le membre est <code>null</code>
     * @throws InvalidCriterionException
     *             Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void inscrireMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Désinscrit un membre.
     *
     * @param session
     *            La session à utiliser
     * @param membreDTO
     *            Le membre à désinscrire
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le membre est <code>null</code>
     * @throws InvalidCriterionException
     *             Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void desinscrireMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;
}
