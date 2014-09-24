
package ca.qc.collegeahuntsic.bibliotheque.service;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;

//import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;

public class MembreService extends Service {

    private static final long serialVersionUID = 1L;

    private MembreDAO membreDAO;

    private LivreDAO livreDAO;

    private ReservationDAO reservationDAO;

    public MembreService() {

    }

    public MembreDAO getMembreDAO() {
        return this.membreDAO;
    }

    public void setMembreDAO(MembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    public LivreDAO getLivreDAO() {
        return this.livreDAO;
    }

    public void setLivreDAO(LivreDAO livreDAO) {
        this.livreDAO = livreDAO;
    }

    public ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    public void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    //ajout d'un membre
    public void ajouter(int idMembre,
        String nom,
        long telephone,
        int limitePret) throws ServiceException {
        try {
            getMembreDAO().ajouter(idMembre,
                nom,
                telephone,
                limitePret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    //lecture d'un membre
    public MembreDTO read(int idMembre) throws ServiceException {
        try {
            return getMembreDAO().read(idMembre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    //mis à jour d'un membre
    public void update(MembreDTO membreDTO) throws ServiceException {
        try {
            getMembreDAO().update(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    //supprimer un membre
    public void delete(MembreDTO membreDTO) throws ServiceException {
        try {
            getMembreDAO().delete(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    //Trouver tous les membres
    public List<MembreDTO> getAll() throws ServiceException {
        try {
            return getMembreDAO().getAll();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    //recherche par telephone
    public MembreDTO findByTel(int idMembre) throws ServiceException {
        try {
            return getMembreDAO().findByTel(idMembre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    //recherche par nom
    public List<MembreDTO> findByNom() throws ServiceException {
        try {
            return getMembreDAO().findByNom();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
