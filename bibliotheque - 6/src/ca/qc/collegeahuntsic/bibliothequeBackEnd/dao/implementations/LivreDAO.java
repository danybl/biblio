
package ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.implementations;

import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import org.hibernate.Session;

/**
 * DAO pour effectuer des CRUDs avec la table <code>livre</code>.
 *
 */
public class LivreDAO extends DAO implements ILivreDAO {

    public LivreDAO(Class<LivreDTO> livreDTOClass) throws InvalidDTOClassException {
        super(livreDTOClass);
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
