// Fichier ILivreService.java

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces;

import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
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

public interface ILivreService extends IService {
    /**
     * Ajoute un nouveau livre dans la base de données.
     *
     * @param session
     *            La session à utiliser
     * @param livreDTO
     *            Le livre à ajouter
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le livre est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void addLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException;

    /**
     * Lit un livre à partir de la base de données.
     *
     * @param session
     *            La session à utiliser
     * @param idLivre
     *            L'ID du livre à lire
     * @return Le livre
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    LivreDTO getLivre(Session session,
        String idLivre) throws InvalidHibernateSessionException,
        ServiceException;

    /**
     * Met à jour un livre dans la base de données.
     *
     * @param session
     *            La session à utiliser
     * @param livreDTO
     *            Le livre à mettre à jour
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le livre est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void updateLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException;

    /**
     * Supprime un livre de la base de données.
     *
     * @param session
     *            La session à utiliser
     * @param livreDTO
     *            Le livre à supprimer
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le livre est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void deleteLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException;

    /**
     * Trouve tous les livres de la base de données. La liste est classée par
     * ordre croissant sur <code>sortByPropertyName</code>. Si aucun livre n'est
     * trouvé, une {@link List} vide est retournée.
     *
     * @param session
     *            La session à utiliser
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste de tous les livres ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    List<LivreDTO> getAllLivres(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les livres à partir d'un titre. La liste est classée par ordre
     * croissant sur <code>sortByPropertyName</code>. Si aucun livre n'est
     * trouvé, une {@link List} vide est retournée.
     *
     * @param session
     *            La session à utiliser
     * @param titre
     *            Le titre à trouver
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste des livres correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidCriterionException
     *             Si la propriété à utiliser est <code>null</code>
     * @throws InvalidCriterionValueException
     *             Si le titre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    List<LivreDTO> findByTitre(Session session,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Acquiert un livre.
     *
     * @param session
     *            La session à utiliser
     * @param livreDTO
     *            Le livre à acquérir
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le livre est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void acquerir(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException;

    /**
     * Vend un livre.
     *
     * @param session
     *            La session à utiliser
     * @param livreDTO
     *            Le livre à vendre
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidDTOException
     *             Si le livre est <code>null</code>
     * @throws InvalidCriterionException
     *             Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException
     *             S'il y a une erreur avec la base de données
     */
    void vendre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException;
}
