
package ca.qc.collegeahuntsic.bibliotheque.facade.implementations;

import ca.qc.collegeahuntsic.bibliotheque.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IPretFacade;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IPretService;

public class PretFacade extends Facade implements IPretFacade { 
    private IPretService pretService;

    public PretFacade(IPretService pretService) throws InvalidServiceException { // TODO: Change to package when switching to Spring
        super();
        if(pretService == null) {
            throw new InvalidServiceException("Le service de réservations ne peut être null");
        }
        setPretService(pretService);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.reservationService</code>.
     *
     * @return La variable d'instance <code>this.reservationService</code>
     */
    @SuppressWarnings("unused")
    private IPretService getPretService() {
        return this.pretService;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationService</code>.
     *
     * @param reservationService La valeur à utiliser pour la variable d'instance <code>this.reservationService</code>
     */
    private void setPretService(IPretService pretService) {
        this.pretService = pretService;
    }
    
    

}
