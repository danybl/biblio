// Fichier PretService.java

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IPretService;
import org.hibernate.Session;

/**
 * Service de la table <code>livre</code>.
 *
 */
public class PretService extends Service implements IPretService {

    private IPretDAO pretDAO;

    /**
     * Crée le service de la table <code>livre</code>.
     *
     * @param livreDAO
     *            Le DAO de la table <code>livre</code>
     * @param membreDAO
     *            Le DAO de la table <code>membre</code>
     * @param pretDAO
     *            Le DAO de la table <code>pret</code>
     * @param reservationDAO
     *            Le DAO de la table <code>reservation</code>
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
        InvalidDTOClassException,
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
        InvalidDTOClassException,
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
        InvalidDTOClassException,
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
    @SuppressWarnings("unchecked")
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
        InvalidDTOClassException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le pret ne peut être null");
        }

        MembreDTO unMembreDTO = pretDTO.getMembreDTO();
        if(unMembreDTO == null) {
            throw new MissingDTOException("Le membre "
                + pretDTO.getMembreDTO().getIdMembre()
                + " n'existe pas");
        }
        LivreDTO unLivreDTO = pretDTO.getLivreDTO();
        if(unLivreDTO == null) {
            throw new MissingDTOException("Le livre "
                + pretDTO.getLivreDTO().getIdLivre()
                + " n'existe pas");
        }
        if(unMembreDTO.getNbPret() == unMembreDTO.getLimitePret()) {
            throw new ServiceException("Le membre "
                + unMembreDTO.getNom()
                + " (ID de membre : "
                + unMembreDTO.getIdMembre()
                + ") a atteint sa limite de prêt ("
                + unMembreDTO.getLimitePret()
                + " emprunt(s) maximum)");
        }
        Set<ReservationDTO> reservations = unLivreDTO.getReservations();

        if(!reservations.isEmpty()) {
            throw new ExistingReservationException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") a des réservations"
                + ")");
        }
        List<PretDTO> prets = new ArrayList<>(unLivreDTO.getPrets());
        for(PretDTO unPretDTO : prets) {
            if(unPretDTO.getDateRetour() == null) {
                MembreDTO emprunteur = unPretDTO.getMembreDTO();
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

        pretDTO.setMembreDTO(unMembreDTO);
        pretDTO.setLivreDTO(unLivreDTO);
        pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
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
        InvalidDTOClassException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le pret ne peut être null");
        }

        MembreDTO unMembreDTO = pretDTO.getMembreDTO();
        if(unMembreDTO == null) {
            throw new ServiceException("Le membre "
                + pretDTO.getMembreDTO().getIdMembre()
                + " n'existe pas");
        }

        LivreDTO unLivreDTO = pretDTO.getLivreDTO();
        if(unLivreDTO == null) {
            throw new MissingDTOException("Le livre "
                + pretDTO.getLivreDTO().getIdLivre()
                + " n'existe pas");
        }
        List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
        if(!reservations.isEmpty()) {
            ReservationDTO reservationDTO = reservations.get(0);
            MembreDTO unMembre = reservationDTO.getMembreDTO();
            throw new ExistingReservationException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") a des réservations "
                + "au membre "
                + unMembre.getNom()
                + ")");
        }
        List<PretDTO> prets = new ArrayList<>(unLivreDTO.getPrets());
        if(prets.isEmpty()) {
            throw new ServiceException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") n'est pas encore prêté");
        }
        if(unMembreDTO.getPrets().isEmpty()) {
            throw new ServiceException("Le livre "
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
        InvalidDTOClassException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        MissingLoanException,
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le pret ne peut être null");
        }

        LivreDTO unLivreDTO = pretDTO.getLivreDTO();
        if(unLivreDTO == null) {
            throw new MissingDTOException("Le livre "
                + pretDTO.getLivreDTO().getIdLivre()
                + " n'existe pas");
        }
        List<PretDTO> prets = new ArrayList<>(unLivreDTO.getPrets());
        PretDTO unPretDTO = new PretDTO();
        if(prets.isEmpty()) {
            throw new MissingLoanException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") n'a pas été prêté ");
        }
        unPretDTO = prets.get(0);
        unPretDTO.setDateRetour(new Timestamp(System.currentTimeMillis()));//TODO à revoir
        updatePret(session,
            unPretDTO);

    }
}