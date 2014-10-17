
package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Session;

/**
 * DAO pour effectuer des CRUDs avec la table <code>pret</code>.
 *
 */

public class PretDAO extends DAO implements IPretDAO {

    public PretDAO(Class<PretDTO> pretDTOClass) throws InvalidDTOClassException {
        super(pretDTOClass);
    }

    /**
     * {@inheritDoc}
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<PretDTO> findByDateRetour(Session session,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {

        return (List<PretDTO>) super.find(session,
            PretDTO.DATE_RETOUR_COLUMN_NAME,
            dateRetour,
            sortByPropertyName);
    }

    /**
     * {@inheritDoc}
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<PretDTO> findByDatePret(Session session,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {

        return (List<PretDTO>) super.find(session,
            PretDTO.DATE_PRET_COLUMN_NAME,
            datePret,
            sortByPropertyName);
    }
}