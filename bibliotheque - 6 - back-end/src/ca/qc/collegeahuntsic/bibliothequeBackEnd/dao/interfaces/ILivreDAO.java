
package ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import java.util.List;
import org.hibernate.Session;

/**
 * Interface DAO pour manipuler les livres dans la base de données.
 *
 */
public interface ILivreDAO extends IDAO {
    /**
     * Trouve les livres à partir d'un titre. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun livre
     * n'est trouvé, une {@link List} vide est retournée.
     *
     * @param session La session Hibernate à utiliser
     * @param titre Le titre à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des livres correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si la propriété à utiliser est <code>null</code>
     * @throws InvalidCriterionValueException Si le titre à prouver est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    List<LivreDTO> findByTitre(Session session,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException;
}