
package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.implementations;

import java.util.ArrayList;
import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.ILivreService;
import org.hibernate.Session;

/**
 * Service de la table <code>livre</code>.
 *
 * @author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
 *
 */
public class LivreService extends Service implements ILivreService {
    private ILivreDAO livreDAO;

    /**
     * Crée le service de la table <code>livre</code>.
     *
     * @param livreDAO
     *            Le DAO de la table <code>livre</code>
     *            Le DAO de la table <code>membre</code>
     *            Le DAO de la table <code>pret</code>
     *            Le DAO de la table <code>reservation</code>
     * @throws InvalidDAOException
     *             Si le DAO de livre est <code>null</code>, si le DAO de membre
     *             est <code>null</code>, si le DAO de prêt est
     *             <code>null</code> ou si le DAO de réservation est
     *             <code>null</code>
     */
    LivreService(ILivreDAO livreDAO) throws InvalidDAOException {
        super();
        if(livreDAO == null) {
            throw new InvalidDAOException("Le DAO de livre ne peut être null");
        }
        setLivreDAO(livreDAO);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @return La variable d'instance <code>this.livreDAO</code>
     */
    private ILivreDAO getLivreDAO() {
        return this.livreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @param livreDAO
     *            La valeur à utiliser pour la variable d'instance
     *            <code>this.livreDAO</code>
     */
    private void setLivreDAO(ILivreDAO livreDAO) {
        this.livreDAO = livreDAO;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        try {
            getLivreDAO().add(session,
                livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LivreDTO getLivre(Session session,
        String idLivre) throws InvalidHibernateSessionException,
        ServiceException {
        try {
            return (LivreDTO) getLivreDAO().get(session,
                idLivre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        try {
            getLivreDAO().update(session,
                livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     * @throws InvalidDTOException
     */
    @Override
    public void deleteLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        try {
            getLivreDAO().delete(session,
                livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<LivreDTO> getAllLivres(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<LivreDTO>) getLivreDAO().getAll(session,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LivreDTO> findByTitre(Session session,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return getLivreDAO().findByTitre(session,
                titre,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acquerir(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
        }
        if(livreDTO == null) {
            throw new InvalidDTOException("Le livre ne peut être null");
        }
        addLivre(session,
            livreDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void vendre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException {

        if(session == null) {
            throw new InvalidHibernateSessionException("La Session ne peut être null");
        }

        if(livreDTO == null) {
            throw new InvalidDTOException("Le livre ne peut être null");
        }

        final List<PretDTO> prets = new ArrayList<>(livreDTO.getPrets());
        if(!prets.isEmpty()) {
            for(PretDTO pretDTO : prets) {
                if(pretDTO.getDateRetour() == null) {
                    final MembreDTO emprunteur = pretDTO.getMembreDTO();
                    throw new ExistingLoanException("Le livre "
                        + livreDTO.getTitre()
                        + " (ID de livre : "
                        + livreDTO.getIdLivre()
                        + ") a été prêté à "
                        + emprunteur.getNom()
                        + " (ID de membre : "
                        + emprunteur.getIdMembre()
                        + ")");
                }
            }
        }
        final List<ReservationDTO> reservations = new ArrayList<>(livreDTO.getReservations());
        if(!reservations.isEmpty()) {
            final ReservationDTO reservationDTO = reservations.get(0);
            final MembreDTO booker = reservationDTO.getMembreDTO();
            throw new ExistingReservationException("Le livre "
                + livreDTO.getTitre()
                + " (ID de livre : "
                + livreDTO.getIdLivre()
                + ") est réservé pour "
                + booker.getNom()
                + " (ID de membre : "
                + booker.getIdMembre()
                + ")");
        }
        deleteLivre(session,
            livreDTO);
    }
}
