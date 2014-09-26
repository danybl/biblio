
package ca.qc.collegeahuntsic.bibliotheque.service;

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

    public void getByIdPret(PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().getByIdPret(pretDTO.getIdPret());
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void getByIdMembre(MembreDTO membreDTO) throws ServiceException {
        try {
            getPretDAO().getByIdMembre(membreDTO.getIdMembre());
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void getByIdLivre(LivreDTO livreDTO) throws ServiceException {
        try {
            getPretDAO().getByIDLivre(livreDTO.getIdLivre());
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
                    + ") a des réservations");
            }
            // Problème de la date de prêt
            // On voit également le manque de la table prêt simulée en ce moment par les deux tables
            // unLivreDTO.setIdMembre(unMembreDTO.getIdMembre());
            // getLivreDAO().emprunter(unLivreDTO);
            //getMembreDAO().emprunter(unMembreDTO);
            getPretDAO().emprunter(livreDTO,
                membreDTO);
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

            MembreDTO emprunteur = getLivreDAO().read(pretDTO.getIdMembre());
            if(emprunteur == null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prêté");
            }
            if(unMembreDTO.getIdMembre() != emprunteur.getIdMembre()) {
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
            if(getPretDAO().findByLivre(unLivreDTO) != null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a des pret");
            }

            if(getReservationDAO().findByLivre(unLivreDTO) != null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a des réservations");
            }

            if(getPretDAO().findByLivre(unLivreDTO) == null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'a pas de pret");
            }

            // Cas éliminé en utilisant la date de prêt comme étant la date système de la base de données

            /* Verifier si date renouvellement >= datePret */
            //          if(Date.valueOf(datePret).before(tupleLivre.getDatePret())) {
            //              throw new BibliothequeException("Date de renouvellement inférieure à la date de prêt");
            //          }

            getLivreDAO().emprunter(unLivreDTO);
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
    public void retourner(MembreDTO membreDTO,
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
            MembreDTO emprunteur = read(pretDTO.getIdMembre());
            if(emprunteur == null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prêté");
            }
            if(unMembreDTO.getIdMembre() != emprunteur.getIdMembre()) {
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

            // Cas éliminé en utilisant la date de prêt comme étant la date système de la base de données

            /* Verifier si date retour >= datePret */
            //          if(Date.valueOf(dateRetour).before(tupleLivre.getDatePret())) {
            //              throw new BibliothequeException("Date de retour inférieure à la date de prêt");
            //          }

            // On voit le manque de la table prêt simulée en ce moment par les deux tables
            getLivreDAO().retourner(unLivreDTO);
            getMembreDAO().retourner(unMembreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

}
