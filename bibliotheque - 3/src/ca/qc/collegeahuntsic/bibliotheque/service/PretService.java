
package ca.qc.collegeahuntsic.bibliotheque.service;

import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;
import java.util.List;

public class PretService extends Service {

    private static final long serialVersionUID = 1L;

    private PretDAO pretDAO;

    public PretService(PretDAO pretDAO) {
        super();
        setPretDAO(pretDAO);
    }

    public PretDAO getPretDAO() {
        return this.pretDAO;
    }

    public void setPretDAO(PretDAO pretDAO) {
        this.pretDAO = pretDAO;
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

    public void getByIDPret(PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().getByIDPret(pretDTO.getIdPret());
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void getByIDMembre(MembreDTO membreDTO) throws ServiceException {
        try {
            getPretDAO().getByIDMembre(membreDTO.getIdMembre());
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    public void getByIDLivre(LivreDTO livreDTO) throws ServiceException {
        try {
            getPretDAO().getByIDLivre(livreDTO.getIdLivre());
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
