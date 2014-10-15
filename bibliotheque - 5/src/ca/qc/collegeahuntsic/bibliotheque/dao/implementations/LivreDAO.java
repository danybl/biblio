
package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import java.util.List;
import org.hibernate.Session;

/**
 * DAO pour effectuer des CRUDs avec la table <code>livre</code>.
 *
 */
public class LivreDAO extends DAO implements ILivreDAO {

    protected LivreDAO(Class<?> dtoClass) throws InvalidDTOClassException {
        super(dtoClass);
    }

    @SuppressWarnings("unchecked")
    /**
     * {@inheritDoc}
     */
    @Override
    public List<LivreDTO> findByTitre(Session session,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {

        return (List<LivreDTO>) super.find(session,
            LivreDTO.TITRE_COLUMN_NAME,
            titre,
            sortByPropertyName);
    }
}
