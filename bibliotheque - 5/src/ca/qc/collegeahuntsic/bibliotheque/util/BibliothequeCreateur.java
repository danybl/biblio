
package ca.qc.collegeahuntsic.bibliotheque.util;

import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.PretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.implementations.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.exception.db.ConnexionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliotheque.facade.implementations.LivreFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.implementations.MembreFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.implementations.PretFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.implementations.ReservationFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.ILivreFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IMembreFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IPretFacade;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IReservationFacade;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.LivreService;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.MembreService;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.PretService;
import ca.qc.collegeahuntsic.bibliotheque.service.implementations.ReservationService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.ILivreService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IMembreService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IPretService;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IReservationService;

public class BibliothequeCreateur {
    private Connexion connexion;

    private ILivreService livreService;

    private IMembreService membreService;

    private IPretService pretService;

    private IReservationService reservationService;

    private ILivreFacade livreFacade;

    private IMembreFacade membreFacade;

    private IReservationFacade reservationFacade;

    private IPretFacade pretFacade;

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
    public BibliothequeCreateur(String typeServeur,
        String schema,
        String nomUtilisateur,
        String motPasse) throws BibliothequeException,
        InvalidDTOClassException,
        InvalidDAOException,
        InvalidServiceException {
        try {
            setConnexion(new Connexion(typeServeur,
                schema,
                nomUtilisateur,
                motPasse));
            ILivreDAO livreDAO = new LivreDAO(LivreDTO.class);
            IMembreDAO membreDAO = new MembreDAO(MembreDTO.class);
            IReservationDAO reservationDAO = new ReservationDAO(ReservationDTO.class);
            IPretDAO pretDAO = new PretDAO(PretDTO.class);
            setLivreService(new LivreService(livreDAO,
                membreDAO,
                pretDAO,
                reservationDAO));
            setMembreService(new MembreService(membreDAO,
                livreDAO,
                pretDAO,
                reservationDAO));
            setPretService(new PretService(pretDAO,
                membreDAO,
                livreDAO,
                reservationDAO));
            setReservationService(new ReservationService(reservationDAO,
                membreDAO,
                livreDAO,
                pretDAO));
            setLivreFacade(new LivreFacade(getLivreService()));
            setMembreFacade(new MembreFacade(getMembreService()));
            setReservationFacade(new ReservationFacade(getReservationService()));
            setPretFacade(new PretFacade(getPretService()));

        } catch(ConnexionException connexionException) {
            throw new BibliothequeException(connexionException);
        }
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.connexion</code>.
     *
     * @return La variable d'instance <code>this.connexion</code>
     */
    public Connexion getConnexion() {
        return this.connexion;
    }

    /**
     * Setter de la variable d'instance <code>this.connexion</code>.
     *
     * @param connexion La valeur à utiliser pour la variable d'instance <code>this.connexion</code>
     */
    private void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }

    /**
     * Getter de la variable d'instance <code>this.livreService</code>.
     *
     * @return La variable d'instance <code>this.livreService</code>
     */
    private ILivreService getLivreService() {
        return this.livreService;
    }

    /**
     * Setter de la variable d'instance <code>this.livreService</code>.
     *
     * @param livreService La valeur à utiliser pour la variable d'instance <code>this.livreService</code>
     */
    private void setLivreService(ILivreService livreService) {
        this.livreService = livreService;
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
     * @param membreService La valeur à utiliser pour la variable d'instance <code>this.membreService</code>
     */
    private void setMembreService(IMembreService membreService) {
        this.membreService = membreService;
    }

    /**
     * Getter de la variable d'instance <code>this.pretService</code>.
     *
     * @return La variable d'instance <code>this.pretService</code>
     */
    private IPretService getPretService() {
        return this.pretService;
    }

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

    /**
     * Effectue un commit sur la connexion.
     *
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */
    public void commit() throws BibliothequeException {
        try {
            getConnexion().commit();
        } catch(Exception exception) {
            throw new BibliothequeException(exception);
        }
    }

    /**
     * Effectue un rollback sur la connexion.
     *
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */
    public void rollback() throws BibliothequeException {
        try {
            getConnexion().rollback();
        } catch(Exception exception) {
            throw new BibliothequeException(exception);
        }
    }

    /**
     * Ferme la connexion.
     *
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */
    public void close() throws BibliothequeException {
        try {
            getConnexion().close();
        } catch(Exception exception) {
            throw new BibliothequeException(exception);
        }
    }
}