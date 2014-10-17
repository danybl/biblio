// Fichier PretService.java

package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IPretService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;

/**
 * Service de la table <code>livre</code>.
 *
 */
public class PretService extends Service implements IPretService {
    private ILivreDAO livreDAO;

    private IMembreDAO membreDAO;

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
    public PretService(IPretDAO pretDAO, // TODO: Change to package when switching to
        // Spring
        IMembreDAO membreDAO,
        ILivreDAO livreDAO,
        IReservationDAO reservationDAO) throws InvalidDAOException {
        super();
        if(livreDAO == null) {
            throw new InvalidDAOException("Le DAO de livre ne peut être null");
        }
        if(membreDAO == null) {
            throw new InvalidDAOException("Le DAO de membre ne peut être null");
        }
        if(pretDAO == null) {
            throw new InvalidDAOException("Le DAO de prêt ne peut être null");
        }
        setLivreDAO(livreDAO);
        setMembreDAO(membreDAO);
        setPretDAO(pretDAO);
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

    /**
     * Getter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @return La variable d'instance <code>this.membreDAO</code>
     */
    private IMembreDAO getMembreDAO() {
        return this.membreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @param membreDAO
     *            La valeur à utiliser pour la variable d'instance
     *            <code>this.membreDAO</code>
     */
    private void setMembreDAO(IMembreDAO membreDAO) {
        this.membreDAO = membreDAO;
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
        try {
            MembreDTO unMembreDTO = (MembreDTO) getMembreDAO().get(session,
                pretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + pretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(session,
                pretDTO.getLivreDTO().getIdLivre());
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
            if(!prets.isEmpty()) {
                PretDTO unPretDTO = prets.get(0);
                if(!unMembreDTO.equals(unPretDTO.getMembreDTO())) {
                    MembreDTO booker = (MembreDTO) getMembreDAO().get(session,
                        unPretDTO.getMembreDTO().getIdMembre());
                    throw new ExistingLoanException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est prété pour "
                        + booker.getNom()
                        + ")");
                }
            }

            pretDTO.setMembreDTO(unMembreDTO);
            pretDTO.setLivreDTO(unLivreDTO);
            pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            addPret(session,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
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
        try {

            PretDTO unPretDTO = (PretDTO) getPretDAO().get(session,
                pretDTO.getIdPret());
            if(unPretDTO == null) {
                throw new ServiceException("Le prêt "
                    + pretDTO.getIdPret()
                    + " n'existe pas");
            }

            MembreDTO unMembreDTO = (MembreDTO) getMembreDAO().get(session,
                unPretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + unPretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }

            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(session,
                unPretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + pretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
            if(!reservations.isEmpty()) {
                ReservationDTO reservationDTO = reservations.get(0);
                MembreDTO unMembre = (MembreDTO) getMembreDAO().get(session,
                    reservationDTO.getMembreDTO().getIdMembre());
                throw new ExistingReservationException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a des réservations "
                    + "au membre "
                    + unMembre.getNom()
                    + ")");
            }
            Set<PretDTO> prets = unLivreDTO.getPrets();
            if(prets.isEmpty()) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prêté");
            }
            //            boolean aEteEmprunteParMembre = false;
            //            for(PretDTO unAutrePretDTO : prets) {
            //                aEteEmprunteParMembre = unMembreDTO.equals(unAutrePretDTO.getMembreDTO());
            //            }
            //            if(!aEteEmprunteParMembre) {
            //                throw new ServiceException("Le livre "
            //                    + unLivreDTO.getTitre()
            //                    + " (ID de livre : "
            //                    + unLivreDTO.getIdLivre()
            //                    + ") n'a pas été prêté à "
            //                    + unMembreDTO.getNom()
            //                    + " (ID de membre : "
            //                    + unMembreDTO.getIdMembre()
            //                    + ")");
            //            }
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

            unPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            updatePret(session,
                unPretDTO);

        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
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

        try {

            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(session,
                pretDTO.getLivreDTO().getIdLivre());
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
            //            deletePret(session,
            //                unPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}