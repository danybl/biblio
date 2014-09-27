
package ca.qc.collegeahuntsic.bibliotheque.service;

import java.sql.Timestamp;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.PretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;

/**
 * Service de la table <code>reservation</code>.
 */
public class ReservationService extends Service {

    private static final long serialVersionUID = 1L;

    private ReservationDAO reservationDAO;

    private LivreDAO livreDAO;

    private MembreDAO membreDAO;

    private PretDAO pretDAO;

    public ReservationService(ReservationDAO reservationDAO,
        LivreDAO livreDAO,
        MembreDAO membreDAO) {
        super();
        setReservationDAO(reservationDAO);
        setMembreDAO(membreDAO);
        setLivreDAO(livreDAO);

    }

    /**
     * Getter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @return La variable d'instance <code>this.reservationDAO</code>
     */
    public ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    public void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public LivreDAO getLivreDAO() {
        return this.livreDAO;
    }

    public void setLivreDAO(LivreDAO livreDAO) {
        this.livreDAO = livreDAO;
    }

    public MembreDAO getMembreDAO() {
        return this.membreDAO;
    }

    public void setMembreDAO(MembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    public PretDAO getPretDAO() {
        return this.pretDAO;
    }

    public void setPretDAO(PretDAO pretDAO) {
        this.pretDAO = pretDAO;
    }

    /**
     * Ajoute une nouvelle reservation.
     *
     * @param reservationDTO La reservation à ajouter
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void add(ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().add(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Lit la reservation.
     *
     * @param idReservation L'ID de la reservation
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public ReservationDTO read(int idReservation) throws ServiceException {
        try {
            return getReservationDAO().read(idReservation);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Met à jour une reservation.
     *
     * @param reservationDTO La reservation à mettre à jour
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void update(ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().update(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Supprime une reservation.
     *
     * @param reservationDTO La reservation à supprimer
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public void delete(ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().delete(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve tous les reservations.
     *
     * @return La liste des reservations ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    public List<ReservationDTO> getAll() throws ServiceException {
        try {
            return getReservationDAO().getAll();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    //findByDateReservation
    public List<ReservationDTO> findByDateReservation(java.sql.Timestamp dateReservation) throws ServiceException {
        try {
            return getReservationDAO().findByDateReservation(dateReservation);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    //findByMembre
    public List<ReservationDTO> findByMembre(MembreDTO membreDTO) throws ServiceException {
        try {
            return getReservationDAO().findByMembre(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public List<ReservationDTO> findByLivre(LivreDTO livreDTO) throws ServiceException {
        try {
            return getReservationDAO().findByLivre(livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Réserve un livre.
     *
     * @param reservationDTO La réservation à créer
     * @param membreDTO Le membre qui réserve
     * @param livreDTO Le livre à réserver
     * @throws ServiceException Si la réservation existe déjà, si le membre n'existe pas, si le livre n'existe pas, si le livre n'a pas encore
     *         été prêté, si le livre est déjà prêté au membre, si le membre a déjà réservé ce livre ou s'il y a une erreur avec la base de
     *         données
     */
    public void reserver(MembreDTO membreDTO,
        LivreDTO livreDTO,
        PretDTO pretDTO) throws ServiceException {
        try {
            //            ReservationDTO uneReservationDTO = read(reservationDTO.getIdReservation());
            //            if(uneReservationDTO != null) {
            //                throw new ServiceException("La réservation "
            //                    + reservationDTO.getIdReservation()
            //                    + " existe déjà");
            //            }
            MembreDTO unMembreDTO = getMembreDAO().read(membreDTO.getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = getLivreDAO().read(livreDTO.getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + livreDTO.getIdLivre()
                    + " n'existe pas");
            }
            MembreDTO emprunteur = getPretDAO().read(pretDTO.getIdPret()).getMembreDTO();
            if(emprunteur == null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prêté");
            }
            if(unMembreDTO.getIdMembre() == emprunteur.getIdMembre()) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") est déjà prêté à "
                    + emprunteur.getNom()
                    + " (ID de membre : "
                    + emprunteur.getIdMembre()
                    + ")");
            }

            // Cas éliminé en utilisant la date de réservation comme étant la date système de la base de données

            List<ReservationDTO> reservations = getReservationDAO().findByMembre(unMembreDTO);
            for(ReservationDTO uneAutreReservationDTO : reservations) {
                if(uneAutreReservationDTO.getLivreDTO().getIdLivre() == unLivreDTO.getIdLivre()) {
                    throw new ServiceException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est déjà réservé à "
                        + emprunteur.getNom()
                        + " (ID de membre : "
                        + emprunteur.getIdMembre()
                        + ")");
                }
            }
            java.sql.Timestamp dateReservation = new Timestamp(System.currentTimeMillis());

            ReservationDTO nouveauReservationDTO = new ReservationDTO();
            nouveauReservationDTO.setIdReservation(getReservationDAO().getPrimaryKey());
            nouveauReservationDTO.setMembreDTO(membreDTO);
            nouveauReservationDTO.setLivreDTO(livreDTO);
            nouveauReservationDTO.setDateReservation(dateReservation);

            add(nouveauReservationDTO);

        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void renouveler(ReservationDTO reservationDTO,
        MembreDTO membreDTO,
        LivreDTO livreDTO,
        PretDTO pretDTO) throws ServiceException {
        try {
            ReservationDTO uneReservationDTO = read(reservationDTO.getIdReservation());
            if(uneReservationDTO == null) {
                throw new ServiceException("La réservation "
                    + reservationDTO.getIdReservation()
                    + " n'existe pas");
            }

            MembreDTO unMembreDTO = getMembreDAO().read(membreDTO.getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }

            LivreDTO unLivreDTO = getLivreDAO().read(livreDTO.getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + livreDTO.getIdLivre()
                    + " n'existe pas");
            }

            List<ReservationDTO> reservations = findByLivre(unLivreDTO);
            if(!reservations.isEmpty()) {
                uneReservationDTO = reservations.get(0);
                if(uneReservationDTO.getMembreDTO().getIdMembre() != unMembreDTO.getIdMembre()) {
                    MembreDTO booker = getMembreDAO().read(uneReservationDTO.getMembreDTO().getIdMembre());
                    throw new ServiceException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est réservé pour "
                        + booker.getNom()
                        + " (ID de membre : "
                        + booker.getIdMembre()
                        + ")");
                }
            }
            MembreDTO emprunteur = getPretDAO().read(pretDTO.getIdPret()).getMembreDTO();
            if(emprunteur != null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a été prêté à "
                    + emprunteur.getNom()
                    + " (ID de membre : "
                    + emprunteur.getIdMembre()
                    + ")");
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

            java.sql.Timestamp dateReservation = new Timestamp(System.currentTimeMillis());

            ReservationDTO nouveauReservationDTO = new ReservationDTO();
            nouveauReservationDTO.setIdReservation(getReservationDAO().getPrimaryKey());
            nouveauReservationDTO.setMembreDTO(membreDTO);
            nouveauReservationDTO.setLivreDTO(livreDTO);
            nouveauReservationDTO.setDateReservation(dateReservation);

            update(nouveauReservationDTO);

        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Annule une réservation.
     *
     * @param reservationDTO Le reservation à annuler
     * @throws ServiceException Si la réservation n'existe pas ou s'il y a une erreur avec la base de données
     */
    public void annuler(ReservationDTO reservationDTO) throws ServiceException {

        ReservationDTO uneReservationDTO = read(reservationDTO.getIdReservation());
        if(uneReservationDTO == null) {
            throw new ServiceException("La réservation "
                + reservationDTO.getLivreDTO().getIdLivre()
                + " n'existe pas");
        }
        delete(uneReservationDTO);
    }
}
