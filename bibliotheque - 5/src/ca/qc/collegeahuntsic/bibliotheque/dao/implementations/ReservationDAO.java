
package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import org.hibernate.Session;

/**
 * DAO pour effectuer des CRUDs avec la table <code>reservation</code>.
 *
 */
public class ReservationDAO extends DAO implements IReservationDAO {

    /**
     * Crée le DAO de la table <code>reservation</code>.
     *
     * @param reservationDTOClass The classe de réservation DTO to use
     * @throws InvalidDTOClassException Si la classe de DTO est <code>null</code>
     */
    public ReservationDAO(Class<ReservationDTO> reservationDTOClass) throws InvalidDTOClassException { // TODO: Change to package when switching to Spring
        super(reservationDTOClass);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ReservationDTO> findByMembre(Session session,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {

        return (List<ReservationDTO>) super.find(session,
            ReservationDTO.ID_MEMBRE_COLUMN_NAME,
            idMembre,
            sortByPropertyName);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ReservationDTO> findByLivre(Session session,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {

        return (List<ReservationDTO>) super.find(session,
            ReservationDTO.ID_LIVRE_COLUMN_NAME,
            idLivre,
            sortByPropertyName);
    }
}
