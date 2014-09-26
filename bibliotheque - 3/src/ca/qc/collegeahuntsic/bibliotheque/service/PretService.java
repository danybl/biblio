
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
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;

public class PretService extends Service {

    private static final long serialVersionUID = 1L;

    private PretDAO pretDAO;

    private MembreDAO membreDAO;

    private ReservationDAO reservationDAO;

    private LivreDAO livreDAO;

    public PretService(PretDAO pretDAO,
        MembreDAO membreDAO,
        LivreDAO livreDAO,
        ReservationDAO reservationDAO) {
        super();
        setPretDAO(pretDAO);
        setMembreDAO(membreDAO);
        setLivreDAO(livreDAO);
        setReservationDAO(reservationDAO);
    }

    public PretDAO getPretDAO() {
        return this.pretDAO;
    }

    public void setPretDAO(PretDAO pretDAO) {
        this.pretDAO = pretDAO;
    }

    private MembreDAO getMembreDAO() {
        return this.membreDAO;
    }

    private void setMembreDAO(MembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    private ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    private void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    private LivreDAO getLivreDAO() {
        return this.livreDAO;
    }

    private void setLivreDAO(LivreDAO livreDAO) {
        this.livreDAO = livreDAO;
    }

    public void add(PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().add(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public PretDTO read(int idPret) throws ServiceException {
        try {
            return getPretDAO().read(idPret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void update(PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().update(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void delete(PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().delete(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public List<PretDTO> getAll() throws ServiceException {
        try {
            return getPretDAO().getAll();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void findByMembre(MembreDTO membreDTO) throws ServiceException {
        try {
            getPretDAO().findByMembre(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void findByLivre(LivreDTO livreDTO) throws ServiceException {
        try {
            getPretDAO().findByLivre(livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void findByDatePret(Timestamp datePret) throws ServiceException {
        try {
            getPretDAO().findByDatePret(datePret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void findByDateRetour(Timestamp dateRetour) throws ServiceException {
        try {
            getPretDAO().findByDateRetour(dateRetour);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void emprunter(MembreDTO membreDTO,
        LivreDTO livreDTO) throws ServiceException {
        try {
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
            if(unMembreDTO.getNbPret() == unMembreDTO.getLimitePret()) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a atteint sa limite de prêt ("
                    + unMembreDTO.getLimitePret()
                    + " emprunt(s) maximum)");
            }
            if(getReservationDAO().findByLivre(unLivreDTO) != null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a des réservations");
            }

            if(getPretDAO().findByLivre(unLivreDTO) != null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a des prets");
            }

            //            PretDTO nouveauPretDTO;
            //            nouveauPretDTO.setLivreDTO(livreDTO);
            //            nouveauPretDTO.setMembreDTO(membreDTO);
            //            nouveauPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            //            
            //            add(nouveauPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Renouvelle le prêt d'un livre.
     *
     * @param membreDTO Le membre qui renouvelle
     * @param livreDTO Le livre à renouveler
     * @throws ServiceException Si le membre n'existe pas, si le livre n'existe pas, si le livre n'a pas encore été prêté, si le livre a été
     *         prêté à quelqu'un d'autre, si le livre a été réservé ou s'il y a une erreur avec la base de données
     */
    public void renouveler(MembreDTO membreDTO,
        LivreDTO livreDTO,
        PretDTO pretDTO) throws ServiceException {
        try {
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

            if(unMembreDTO.getNbPret() == unMembreDTO.getLimitePret()) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a atteint sa limite de prêt ("
                    + unMembreDTO.getLimitePret()
                    + " emprunt(s) maximum)");
            }

            if(getReservationDAO().findByLivre(unLivreDTO) != null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a des réservations");
            }

            update(pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Retourne un livre.
     *
     * @param membreDTO Le membre qui retourne
     * @param livreDTO Le livre à retourner
     * @throws ServiceException Si le membre n'existe pas, si le livre n'existe pas, si le livre n'a pas encore été prêté, si le livre a été
     *         prêté à quelqu'un d'autre ou s'il y a une erreur avec la base de données
     */
    public void retourner(LivreDTO livreDTO,
        PretDTO pretDTO) throws ServiceException {
        try {

            LivreDTO unLivreDTO = getLivreDAO().read(livreDTO.getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + livreDTO.getIdLivre()
                    + " n'existe pas");
            }

            MembreDTO emprunteur = read(pretDTO.getIdPret()).getMembreDTO();
            if(emprunteur == null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prêté");
            }

            getPretDAO().delete(pretDTO);

        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

}
