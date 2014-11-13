// Fichier PretService.java

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IPretDAO;
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
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IPretService;
import org.hibernate.Session;

/**
 * Service de la table <code>livre</code>.
 *
 * @author Cedric Soumpholphakdy, Dany Benoit-Lafond, Nkezimana Franz, Jaskaran Singh Dhadda & David Andrés Gallego Mesa
 *
 */
public class PretService extends Service implements IPretService {

    private IPretDAO pretDAO;

    /**
     * Crée le service de la table <code>livre</code>.
     *
     * @param pretDAO
     *            Le DAO de la table <code>pret</code>
     * @throws InvalidDAOException
     *             Si le DAO de livre est <code>null</code>, si le DAO de membre
     *             est <code>null</code>, si le DAO de prêt est
     *             <code>null</code> ou si le DAO de réservation est
     *             <code>null</code>
     */
    PretService(IPretDAO pretDAO) throws InvalidDAOException {
        super();
        if(pretDAO == null) {
            throw new InvalidDAOException("Le DAO de prêt ne peut être null");
        }
        setPretDAO(pretDAO);
    }

    // Region Getters and Setters
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
    public void addPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        try {
            getPretDAO().add(session,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PretDTO getPret(Session session,
        String idPret) throws InvalidHibernateSessionException,
        ServiceException {
        try {
            return (PretDTO) getPretDAO().get(session,
                idPret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        try {
            getPretDAO().update(session,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ServiceException {
        try {
            getPretDAO().delete(session,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public List<PretDTO> getAll(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<PretDTO>) getPretDAO().getAll(session,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void emprunter(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidLoanLimitException,
        ExistingReservationException,
        ExistingLoanException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }

        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }

        final MembreDTO membreDTO = pretDTO.getMembreDTO();
        final LivreDTO livreDTO = pretDTO.getLivreDTO();

        final List<PretDTO> membrePrets = new ArrayList<>(membreDTO.getPrets());

        if(membreDTO.getLimitePret().equals(String.valueOf(membrePrets.size()))) {
            throw new InvalidLoanLimitException("Le membre "
                + membreDTO.getNom()
                + " (ID de membre : "
                + membreDTO.getIdMembre()
                + ") a atteint sa limite de prêt ("
                + membreDTO.getLimitePret()
                + " emprunt(s) maximum)");
        }

        final List<PretDTO> livrePrets = new ArrayList<>(livreDTO.getPrets());
        for(PretDTO unPretDTO : livrePrets) {
            if(unPretDTO.getDateRetour() == null) {
                final MembreDTO emprunteur = unPretDTO.getMembreDTO();
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

            final List<ReservationDTO> reservations = new ArrayList<>(livreDTO.getReservations());
            if(!reservations.isEmpty()) {
                final ReservationDTO uneReservationDTO = reservations.get(0);
                throw new ExistingReservationException("Le livre "
                    + livreDTO.getTitre()
                    + " (ID de livre : "
                    + livreDTO.getIdLivre()
                    + ") est réservé pour "
                    + uneReservationDTO.getMembreDTO().getNom()
                    + " (ID de membre : "
                    + uneReservationDTO.getMembreDTO().getIdMembre()
                    + ")");
            }
        }

        pretDTO.getMembreDTO().setNbPret(Integer.toString(Integer.parseInt(membreDTO.getNbPret()) + 1));
        pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
        pretDTO.setDateRetour(null);
        addPret(session,
            pretDTO);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renouveler(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        ExistingReservationException,
        MissingLoanException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }

        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        if(pretDTO.getDateRetour() != null) {
            throw new MissingLoanException("Le livre "
                + pretDTO.getLivreDTO().getTitre()
                + " (ID de livre : "
                + pretDTO.getLivreDTO().getIdLivre()
                + ") a déjà été retourné");
        }

        final MembreDTO unMembreDTO = pretDTO.getMembreDTO();
        final LivreDTO unLivreDTO = pretDTO.getLivreDTO();

        final List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());

        if(!reservations.isEmpty()) {
            final ReservationDTO reservationDTO = reservations.get(0);
            final MembreDTO unMembre = reservationDTO.getMembreDTO();
            throw new ExistingReservationException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") a des réservations "
                + "au membre "
                + unMembre.getNom()
                + ")");
        }
        final List<PretDTO> prets = new ArrayList<>(unLivreDTO.getPrets());
        if(prets.isEmpty()) {
            throw new MissingLoanException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") n'est pas encore prêté");
        }
        if(unMembreDTO.getPrets().isEmpty()) {
            throw new MissingLoanException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") n'a pas été prêté à "
                + unMembreDTO.getNom()
                + " (ID de membre : "
                + unMembreDTO.getIdMembre()
                + ")");
        }

        pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
        pretDTO.setDateRetour(null);
        updatePret(session,
            pretDTO);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void retourner(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        MissingLoanException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }

        if(pretDTO.getDateRetour() != null) {
            throw new MissingLoanException("Le livre "
                + pretDTO.getLivreDTO().getTitre()
                + " (ID de livre : "
                + pretDTO.getLivreDTO().getIdLivre()
                + ") a déjà été retourné");
        }
        pretDTO.getMembreDTO().setNbPret(Integer.toString(Integer.parseInt(pretDTO.getMembreDTO().getNbPret()) - 1));
        pretDTO.setDateRetour(new Timestamp(System.currentTimeMillis()));
        updatePret(session,
            pretDTO);

    }
}
