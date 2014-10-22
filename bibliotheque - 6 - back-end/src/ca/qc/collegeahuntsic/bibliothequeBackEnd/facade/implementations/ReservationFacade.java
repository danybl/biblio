
package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.implementations;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IReservationFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IReservationService;
import org.hibernate.Session;

/**
 * Facade pour interagir avec le service de réservations.
 *
 */
public class ReservationFacade extends Facade implements IReservationFacade {
    private IReservationService reservationService;

    /**
     * Crée la façade de la table <code>reservation</code>.
     *
     * @param reservationService Le service de la table <code>reservation</code>
     * @throws InvalidServiceException Si le service de réservations est <code>null</code>
     */
    public ReservationFacade(IReservationService reservationService) throws InvalidServiceException {
        super();
        if(reservationService == null) {
            throw new InvalidServiceException("Le service de réservations ne peut être null");
        }
        setReservationService(reservationService);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.reservationService</code>.
     *
     * @return La variable d'instance <code>this.reservationService</code>
     */
    private IReservationService getReservationService() {
        return this.reservationService;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationService</code>.
     *
     * @param reservationService La valeur à utiliser pour la variable d'instance <code>this.reservationService</code>
     */
    private void setReservationService(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void placer(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        InvalidDTOClassException,
        FacadeException {
        try {
            getReservationService().placer(session,
                reservationDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void utiliser(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        ExistingReservationException,
        ExistingLoanException,
        InvalidLoanLimitException,
        InvalidDTOClassException,
        FacadeException {
        try {
            getReservationService().utiliser(session,
                reservationDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void annuler(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidDTOClassException,
        FacadeException {
        try {
            getReservationService().annuler(session,
                reservationDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }
}
