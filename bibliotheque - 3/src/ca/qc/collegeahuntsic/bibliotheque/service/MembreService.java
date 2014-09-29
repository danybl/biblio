
package ca.qc.collegeahuntsic.bibliotheque.service;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.PretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;

//import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;

public class MembreService extends Service {

    private static final long serialVersionUID = 1L;

    private MembreDAO membreDAO;

    private ReservationDAO reservationDAO;

    private PretDAO pretDAO;

    public MembreService(MembreDAO membreDAO,
        ReservationDAO reservationDAO,
        PretDAO pretDAO) {
        setMembreDAO(membreDAO);
        setReservationDAO(reservationDAO);
        setPretDAO(pretDAO);
    }

    public MembreDAO getMembreDAO() {
        return this.membreDAO;
    }

    public void setMembreDAO(MembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    private ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    private void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    private PretDAO getPretDAO() {
        return this.pretDAO;
    }

    private void setPretDAO(PretDAO pretDAO) {
        this.pretDAO = pretDAO;
    }

    //ajout d'un membre
    public void add(MembreDTO membreDTO) throws ServiceException {
        try {
            getMembreDAO().add(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    //lecture d'un membre
    public MembreDTO read(int id) throws ServiceException {
        try {
            return getMembreDAO().read(id);
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

    public void inscrire(MembreDTO membreDTO) throws ServiceException {
        add(membreDTO);
    }

    public void desincrire(MembreDTO membreDTO) throws ServiceException {
        try {
            MembreDTO unMembreDTO = read(membreDTO.getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }
            if(unMembreDTO.getNbPret() > 0) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a encore des prêts");
            }
            if(getReservationDAO().findByMembre(unMembreDTO) != null) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a des réservations");
            }
            if(getPretDAO().findByMembre(unMembreDTO) != null) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a des prêts");
            }
            delete(unMembreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
