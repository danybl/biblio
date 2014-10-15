
package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import org.hibernate.Session;

/**
 * DAO pour effectuer des CRUDs avec la table <code>membre</code>.
 */

public class MembreDAO extends DAO implements IMembreDAO {

    /**
     * {@inheritDoc}
     */

    @Override
    public List<MembreDTO> findByNom(Session session,
        String nom,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        return (List<MembreDTO>) super.find(session,
            MembreDTO.NOM_COLUMN_NAME,
            nom,
            sortByPropertyName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MembreDTO> findByTel(Session session,
        String numTel,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {

        return (List<MembreDTO>) super.find(session,
            MembreDTO.TELEPHONE_COLUMN_NAME,
            numTel,
            sortByPropertyName);
    }
}