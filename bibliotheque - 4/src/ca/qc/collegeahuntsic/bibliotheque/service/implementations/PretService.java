// Fichier PretService.java

package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

import java.sql.Timestamp;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IPretService;

/**
 * Service de la table <code>livre</code>.
 *
 */
public class PretService extends Service implements IPretService {
    private ILivreDAO livreDAO;

    private IMembreDAO membreDAO;

    private IPretDAO pretDAO;

    private IReservationDAO reservationDAO;

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
        if(reservationDAO == null) {
            throw new InvalidDAOException("Le DAO de réservation ne peut être null");
        }
        setLivreDAO(livreDAO);
        setMembreDAO(membreDAO);
        setPretDAO(pretDAO);
        setReservationDAO(reservationDAO);
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

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        ServiceException {
        try {
            getPretDAO().add(connexion,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PretDTO get(Connexion connexion,
        String idPret) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException {
        try {
            return (PretDTO) getPretDAO().get(connexion,
                idPret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getPretDAO().update(connexion,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getPretDAO().delete(connexion,
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
    public List<PretDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<PretDTO>) getPretDAO().getAll(connexion,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByMembre(Connexion connexion,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return getPretDAO().findByMembre(connexion,
                idMembre,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<PretDTO> findByLivre(Connexion connexion,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return getPretDAO().findByLivre(connexion,
                idLivre,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void emprunter(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le pret ne peut être null");
        }
        try {
            MembreDTO unMembreDTO = (MembreDTO) getMembreDAO().get(connexion,
                pretDTO.getMembreDTO().getIdMembre());
            if(unMembreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + pretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(connexion,
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
            List<PretDTO> prets = findByLivre(connexion,
                unLivreDTO.getIdLivre(),
                PretDTO.DATE_PRET_COLUMN_NAME);
            if(prets != null) {
                PretDTO unPretDTO = prets.get(0);
                if(!unMembreDTO.equals(unPretDTO.getMembreDTO())) {
                    MembreDTO booker = (MembreDTO) getMembreDAO().get(connexion,
                        unPretDTO.getMembreDTO().getIdMembre());
                    throw new ExistingReservationException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est réservé pour "
                        + booker.getNom()
                        + ")");
                }
            }
            long deuxSemaines = 14
                * 24
                * 60
                * 60
                * 1000;
            java.sql.Timestamp datePret = new Timestamp(System.currentTimeMillis());
            java.sql.Timestamp dateRetour = new java.sql.Timestamp(datePret.getTime()
                + deuxSemaines);
            PretDTO nouveauPretDTO = new PretDTO();
            nouveauPretDTO.setMembreDTO(unMembreDTO);
            nouveauPretDTO.setLivreDTO(unLivreDTO);
            nouveauPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            nouveauPretDTO.setDateRetour(null);
            getPretDAO().add(connexion,
                nouveauPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void renouveler(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le pret ne peut être null");
        }
        try {
            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(connexion,
                pretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + pretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByLivre(connexion,
                unLivreDTO.getIdLivre(),
                PretDTO.DATE_PRET_COLUMN_NAME);
            if(prets != null) {
                PretDTO unPretDTO = prets.get(0);
                if(!unPretDTO.equals(unPretDTO.getMembreDTO())) {
                    MembreDTO booker = (MembreDTO) getMembreDAO().get(connexion,
                        unPretDTO.getMembreDTO().getIdMembre());
                    throw new ExistingReservationException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est réservé pour "
                        + booker.getNom()
                        + ")");
                }
                long deuxSemaines = 14
                    * 24
                    * 60
                    * 60
                    * 1000;
                java.sql.Timestamp datePret = new Timestamp(System.currentTimeMillis());
                java.sql.Timestamp dateRetour = new java.sql.Timestamp(datePret.getTime()
                    + deuxSemaines);

                PretDTO nouveauPretDTO = new PretDTO();
                nouveauPretDTO.setDatePret(datePret);
                nouveauPretDTO.setDateRetour(dateRetour);

                update(connexion,
                    nouveauPretDTO);
            }
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void retourner(Connexion connexion,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le pret ne peut être null");
        }
        try {
            LivreDTO unLivreDTO = (LivreDTO) getLivreDAO().get(connexion,
                pretDTO.getLivreDTO().getIdLivre());
            if(unLivreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + pretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            PretDTO nouveauPretDTO = new PretDTO();
            delete(connexion,
                nouveauPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}