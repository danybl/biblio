
package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IReservationService;
import org.hibernate.Session;

/**
 * Service de la table <code>reservation</code>.
 *
 * @author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
 *
 */
public class ReservationService extends Service implements IReservationService {
    private IReservationDAO reservationDAO;

    private IPretDAO pretDAO;

    /**
     * Crée le service de la table <code>reservation</code>.
     *
     * @param reservationDAO
     *            Le DAO de la table <code>reservation</code>
     * @param pretDAO
     *            Le DAO de la table <code>pret</code>
     * @throws InvalidDAOException
     *             Si le DAO de réservation est <code>null</code>, si le DAO de
     *             membre est <code>null</code>, si le DAO de livre est
     *             <code>null</code> ou si le DAO de prêt est <code>null</code>
     */
    ReservationService(IReservationDAO reservationDAO,
        IPretDAO pretDAO) throws InvalidDAOException {
        super();
        if(reservationDAO == null) {
            throw new InvalidDAOException("Le DAO de réservation ne peut être null");
        }
        if(pretDAO == null) {
            throw new InvalidDAOException("Le DAO de prêt ne peut être null");
        }
        setReservationDAO(reservationDAO);
        setPretDAO(pretDAO);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @return La variable d'instance <code>this.reservationDAO</code>
     */
    private IReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @param reservationDAO
     *            La valeur à utiliser pour la variable d'instance
     *            <code>this.reservationDAO</code>
     */
    private void setReservationDAO(IReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.pretDAO</code>.
     *
     * @return La variable d'instance <code>this.pretDAO</code>
     */
    private IPretDAO getPretDAO() {
        return this.pretDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.pretDAO</code>.
     *
     * @param pretDAO
     *            La valeur à utiliser pour la variable d'instance
     *            <code>this.pretDAO</code>
     */
    private void setPretDAO(IPretDAO pretDAO) {
        this.pretDAO = pretDAO;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void addReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        try {
            getReservationDAO().add(session,
                reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReservationDTO getReservation(Session session,
        String idReservation) throws InvalidHibernateSessionException,
        ServiceException {
        try {
            return (ReservationDTO) getReservationDAO().get(session,
                idReservation);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        try {
            getReservationDAO().update(session,
                reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        try {
            getReservationDAO().delete(session,
                reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<ReservationDTO> getAllReservation(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<ReservationDTO>) getReservationDAO().getAll(session,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placer(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(reservationDTO == null) {
            throw new InvalidDTOException("La réservation ne peut être null");
        }
        final MembreDTO unMembreDTO = reservationDTO.getMembreDTO();
        final LivreDTO unLivreDTO = reservationDTO.getLivreDTO();

        final List<PretDTO> prets = new ArrayList<>(unLivreDTO.getPrets());

        if(prets.isEmpty()) {
            throw new MissingLoanException("Le livre "
                + reservationDTO.getLivreDTO().getTitre()
                + " (ID de livre : "
                + reservationDTO.getLivreDTO().getIdLivre()
                + ") n'est pas encore prêté");
        }

        final List<PretDTO> membrePrets = new ArrayList<>(unMembreDTO.getPrets());

        for(PretDTO pretDTO : membrePrets) {
            if(pretDTO.getDateRetour() == null) {
                throw new ExistingLoanException("Le livre "
                    + pretDTO.getLivreDTO().getTitre()
                    + " (ID de livre : "
                    + pretDTO.getLivreDTO().getIdLivre()
                    + ") est présentemment prêté à "
                    + pretDTO.getMembreDTO().getNom()
                    + " (ID de membre : "
                    + pretDTO.getMembreDTO().getIdMembre()
                    + ")");
            }
        }

        final List<ReservationDTO> reservations = new ArrayList<>(reservationDTO.getMembreDTO().getReservations());
        for(ReservationDTO uneReservationDTO : reservations) {
            if(reservationDTO.getLivreDTO().equals(uneReservationDTO.getLivreDTO())) {
                throw new ExistingReservationException("Le livre "
                    + uneReservationDTO.getLivreDTO().getTitre()
                    + " (ID de livre : "
                    + uneReservationDTO.getLivreDTO().getIdLivre()
                    + ") est déjà réservé pour "
                    + uneReservationDTO.getMembreDTO().getNom()
                    + " (ID de membre : "
                    + uneReservationDTO.getMembreDTO().getIdMembre()
                    + ")");
            }
        }

        reservationDTO.setDateReservation(new Timestamp(System.currentTimeMillis()));
        addReservation(session,
            reservationDTO);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void utiliser(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidLoanLimitException,
        ExistingReservationException,
        ExistingLoanException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(reservationDTO == null) {
            throw new InvalidDTOException("La réservation ne peut être null");
        }

        try {
            final MembreDTO unMembreDTO = reservationDTO.getMembreDTO();
            final LivreDTO unLivreDTO = reservationDTO.getLivreDTO();

            final List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());

            if(!reservations.isEmpty()) {
                final ReservationDTO uneReservationDTO = reservations.get(0);
                final MembreDTO unMembre = uneReservationDTO.getMembreDTO();
                if(!reservationDTO.getMembreDTO().equals(unMembre)) {
                    throw new ExistingReservationException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") a des réservations "
                        + "au membre "
                        + unMembre.getNom()
                        + ")");
                }
            }

            final List<PretDTO> prets = new ArrayList<>(reservationDTO.getLivreDTO().getPrets());
            for(PretDTO unPretDTO : prets) {
                if(unPretDTO.getDateRetour() == null) {
                    final MembreDTO emprunteur = unPretDTO.getMembreDTO();
                    throw new ExistingLoanException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") a été prêté à "
                        + emprunteur.getNom()
                        + " (ID de membre : "
                        + emprunteur.getIdMembre()
                        + ")");
                }
            }

            final List<ReservationDTO> membreReservations = new ArrayList<>(unMembreDTO.getReservations());

            if(unMembreDTO.getLimitePret().equals(String.valueOf(membreReservations.size()))) {

                throw new InvalidLoanLimitException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a atteint sa limite de prêt ("
                    + unMembreDTO.getLimitePret()
                    + " emprunt(s) maximum)");
            }

            final PretDTO unPretDTO = new PretDTO();
            unPretDTO.setMembreDTO(unMembreDTO);
            unPretDTO.getMembreDTO().setNbPret(Integer.toString(Integer.parseInt(unPretDTO.getMembreDTO().getNbPret()) + 1));
            unPretDTO.setLivreDTO(unLivreDTO);
            unPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            unPretDTO.setDateRetour(null);
            getPretDAO().add(session,
                unPretDTO);
            reservationDTO.getLivreDTO().getReservations().remove(reservationDTO);
            annuler(session,
                reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void annuler(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(reservationDTO == null) {
            throw new InvalidDTOException("La réservation ne peut être null");
        }
        deleteReservation(session,
            reservationDTO);
    }
}
