
package ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.implementations;

import java.util.List;
import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>membre</code>.
 */

public class MembreDAO extends DAO implements IMembreDAO {

    public MembreDAO(Class<MembreDTO> membreDTOClass) throws InvalidDTOClassException {
        super(membreDTOClass);
    }

    /**
     * {@inheritDoc}
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<MembreDTO> findByNom(Session session,
        String nom,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        InvalidCriterionValueException,
        DAOException {
        return (List<MembreDTO>) super.find(session,
            MembreDTO.NOM_COLUMN_NAME,
            nom,
            sortByPropertyName);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MembreDTO> findByTel(Session session,
        String numTel,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        InvalidCriterionValueException,
        DAOException {

        return (List<MembreDTO>) super.find(session,
            MembreDTO.TELEPHONE_COLUMN_NAME,
            numTel,
            sortByPropertyName);
    }
}