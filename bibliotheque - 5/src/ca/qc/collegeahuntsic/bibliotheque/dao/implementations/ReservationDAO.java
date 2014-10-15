
package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
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
     * Crée une nouvelle clef primaire pour la table <code>reservation</code>.
     *
     * @param session La session à utiliser
     * @return La nouvelle clef primaire
     * @throws InvalidHibernateSessionException Si la session est <code>null</code>
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du livre est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données


    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReservationDTO> findByMembre(Session session,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {

        return null;//TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReservationDTO> findByLivre(Session session,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {

        return null;//TODO
    }
}
