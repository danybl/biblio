
package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.implementations;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
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
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IMembreFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IMembreService;
import org.hibernate.Session;

/**
 * Facade pour interagir avec le service des membres.
 *
 */
public class MembreFacade extends Facade implements IMembreFacade {
    private IMembreService membreService;

    /**
     * Crée la façade de la table <code>membre</code>.
     *
     * @param membreService Le service de la table <code>membre</code>
     * @throws InvalidServiceException Si le service de membres est <code>null</code>
     */
    MembreFacade(IMembreService membreService) throws InvalidServiceException {
        super();
        if(membreService == null) {
            throw new InvalidServiceException("Le service de membres ne peut être null");
        }
        setMembreService(membreService);
    }

    /**
     * Getter de la variable d'instance <code>this.membreService</code>.
     *
     * @return La variable d'instance <code>this.membreService</code>
     */
    private IMembreService getMembreService() {
        return this.membreService;
    }

    /**
     * Setter de la variable d'instance <code>this.membreService</code>.
     *
     * @param livreService La valeur à utiliser pour la variable d'instance <code>this.membreService</code>
     */
    private void setMembreService(IMembreService membreService) {
        this.membreService = membreService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void desinscrireMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingReservationException,
        ExistingLoanException,
        InvalidLoanLimitException,
        InvalidDTOClassException,
        FacadeException {
        try {
            getMembreService().desinscrireMembre(session,
                membreDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inscrireMembre(Session session,
        MembreDTO membreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        InvalidDTOClassException,
        FacadeException,
        FacadeException {
        try {
            getMembreService().inscrireMembre(session,
                membreDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException();
        }
    }
}
