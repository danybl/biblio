
package ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.implementations;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;

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
    public ReservationDAO(Class<ReservationDTO> reservationDTOClass) throws InvalidDTOClassException {
        super(reservationDTOClass);
    }
}
