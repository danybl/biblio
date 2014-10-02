
package ca.qc.collegeahuntsic.bibliotheque.dao.interfaces;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;

public interface IPretDAO extends IDAO {
    /**
     * Trouve les prêts d'un membre. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt
     * n'est trouvé, une {@link List} vide est retournée.
     *
     * @param connexion La connexion à utiliser
     * @param idMembre L'ID du prêt à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondantes ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */

    List<PretDTO> findByMembre(Connexion connexion,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException;

    /**
     * Trouve les prêts d'un livre. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt
     * n'est trouvé, une {@link List} vide est retournée.
     *
     * @param connexion La connexion à utiliser
     * @param idLivre L'ID du prêt à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondant ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si l'ID du livre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */

    List<PretDTO> findByLivre(Connexion connexion,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException;
}
