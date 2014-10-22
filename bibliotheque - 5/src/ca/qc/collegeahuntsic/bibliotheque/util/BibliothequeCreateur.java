
package ca.qc.collegeahuntsic.bibliotheque.util;

import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.ILivreFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IMembreFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IPretFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IReservationFacade;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.ILivreService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IMembreService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IPretService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IReservationService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BibliothequeCreateur {
    private static final String SPRING_CONFIGURATION_FILE_NAME = "applicationContext.xml";

    private static final String SESSION_FACTORY_NAME = "sessionFactory";

    private static final String LIVRE_DAO_NAME = "livreDAO";

    private static final String MEMBRE_DAO_NAME = "membreDAO";

    private static final String RESERVATION_DAO_NAME = "reservationDAO";

    private static final String PRET_DAO_NAME = "pretDAO";

    private static final String LIVRE_SERVICE_NAME = "livreService";

    private static final String MEMBRE_SERVICE_NAME = "membreService";

    private static final String PRET_SERVICE_NAME = "pretService";

    private static final String RESERVATION_SERVICE_NAME = "reservationService";

    private static final String MEMBRE_FACADE_NAME = "membreFacade";

    private static final String LIVRE_FACADE_NAME = "livreFacade";

    private static final String PRET_FACADE_NAME = "pretFacade";

    private static final String RESERVATION_FACADE_NAME = "reservationFacade";

    private static final ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext(BibliothequeCreateur.SPRING_CONFIGURATION_FILE_NAME);

    private SessionFactory sessionFactory;

    private ILivreFacade livreFacade;

    private IMembreFacade membreFacade;

    private IReservationFacade reservationFacade;

    private IPretFacade pretFacade;
    
    private ILivreService livreService;
    
    private IMembreService membreService;
    
    private IReservationService reservationService;
    
    private IPretService pretService;

    /**
     * Crée les services nécessaires à l'application bibliothèque.
     *
     * @param typeServeur Type de serveur SQL de la BD
     * @param schema Nom du schéma de la base de données
     * @param nomUtilisateur Nom d'utilisateur sur le serveur SQL
     * @param motPasse Mot de passe sur le serveur SQL
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     * @throws InvalidDTOClassException
     * @throws InvalidDAOException
     * @throws InvalidServiceException
     */
    public BibliothequeCreateur() throws BibliothequeException {
        super();
        try {
            setSessionFactory((SessionFactory) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.SESSION_FACTORY_NAME));

            //session.open
        }

        catch(BeansException beanException) {
            throw new BibliothequeException();
        }
        BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.LIVRE_DAO_NAME);
        BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.MEMBRE_DAO_NAME);
        BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.RESERVATION_DAO_NAME);
        BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.PRET_DAO_NAME);
        setLivreService((ILivreService) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.LIVRE_SERVICE_NAME));
        setMembreService((IMembreService) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.MEMBRE_SERVICE_NAME));
        setReservationService((IReservationService) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.RESERVATION_SERVICE_NAME));
        setPretService((IPretService) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.PRET_SERVICE_NAME));
        setLivreFacade((ILivreFacade) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.LIVRE_FACADE_NAME));
        setMembreFacade((IMembreFacade) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.MEMBRE_FACADE_NAME));
        setReservationFacade((IReservationFacade) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.RESERVATION_FACADE_NAME));
        setPretFacade((IPretFacade) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.PRET_FACADE_NAME));

        //        } catch(ConnexionException sessionException) {
        //            throw new BibliothequeException(sessionException);
        //        }
    }

    private SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.session</code>.
     *
     * @return La variable d'instance <code>this.session</code>
     */
    public Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    /**
     * Getter de la variable d'instance <code>this.livreService</code>.
     *
     * @return La variable d'instance <code>this.livreService</code>
     */
    //    private ILivreService getLivreService() {
    //        return this.livreService;
    //    }

    /**
     * Setter de la variable d'instance <code>this.livreService</code>.
     *
     * @param livreService La valeur à utiliser pour la variable d'instance <code>this.livreService</code>
     */
    private void setLivreService(ILivreService livreService) {
        this.livreService=livreService;
    }

    /**
     * Getter de la variable d'instance <code>this.membreService</code>.
     *
     * @return La variable d'instance <code>this.membreService</code>
     */
    //    private IMembreService getMembreService() {
    //        return this.membreService;
    //    }

    /**
     * Setter de la variable d'instance <code>this.membreService</code>.
     *
     * @param membreService La valeur à utiliser pour la variable d'instance <code>this.membreService</code>
     */
    private void setMembreService(IMembreService membreService) {
        this.membreService=membreService;
    }

    /**
     * Getter de la variable d'instance <code>this.pretService</code>.
     *
     * @return La variable d'instance <code>this.pretService</code>
     */
    //    private IPretService getPretService() {
    //        return this.pretService;
    //    }

    /**
     * Setter de la variable d'instance <code>this.pretService</code>.
     *
     * @param pretService La valeur à utiliser pour la variable d'instance <code>this.pretService</code>
     */
    private void setPretService(IPretService pretService) {
        this.pretService = pretService;
    }

    /**
     * Getter de la variable d'instance <code>this.reservationService</code>.
     *
     * @return La variable d'instance <code>this.reservationService</code>
     */
    //    private IReservationService getReservationService() {
    //        return this.reservationService;
    //    }

    /**
     * Setter de la variable d'instance <code>this.reservationService</code>.
     *
     * @param reservationService La valeur à utiliser pour la variable d'instance <code>this.reservationService</code>
     */
    private void setReservationService(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // EndRegion Getters and Setters

    public ILivreFacade getLivreFacade() {
        return this.livreFacade;
    }

    private void setLivreFacade(ILivreFacade livreFacade) {
        this.livreFacade = livreFacade;
    }

    public IMembreFacade getMembreFacade() {
        return this.membreFacade;
    }

    private void setMembreFacade(IMembreFacade membreFacade) {
        this.membreFacade = membreFacade;
    }

    public IReservationFacade getReservationFacade() {
        return this.reservationFacade;
    }

    private void setReservationFacade(IReservationFacade reservationFacade) {
        this.reservationFacade = reservationFacade;
    }

    public IPretFacade getPretFacade() {
        return this.pretFacade;
    }

    private void setPretFacade(IPretFacade pretFacade) {
        this.pretFacade = pretFacade;
    }

    //    private void setSession(Session session){
    //        this.sessionFactory.
    //    }
    //private void getSession

    //    public void beginTransaction() throws BibliothequeException
    //    {
    //        try{
    //            setSession()
    //        }
    //    }

    /**
     * Effectue un commit sur la session.
     *
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */
    public void commitTransaction() throws BibliothequeException {
        try {
            getSession().getTransaction().commit();
        } catch(Exception exception) {
            throw new BibliothequeException(exception);
        }
    }

    /**
     * Effectue un rollback sur la session.
     *
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */
    public void rollbackTransaction() throws BibliothequeException {
        try {
            getSession().getTransaction().rollback();
        } catch(Exception exception) {
            throw new BibliothequeException(exception);
        }
    }

    /**
     * Ferme la session.
     *
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */
    public void closeSession() throws BibliothequeException {
        try {
            getSession().close();
        } catch(Exception exception) {
            throw new BibliothequeException(exception);
        }
    }
}