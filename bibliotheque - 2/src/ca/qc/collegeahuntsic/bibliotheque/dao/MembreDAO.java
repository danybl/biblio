
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

public class MembreDAO extends DAO {

    private static final long serialVersionUID = 1L;

    private static final String ADD_REQUEST = "INSERT INTO membre (idMembre, nom, telephone, limitePret, nbpret)"
        + "VALUES (?,?,?,?,?)";

    private static final String READ_REQUEST = "SELECT idMembre, nom, telephone, limitePret, nbpret FROM membre"
        + "WHERE idMembre = ?";

    private static final String UPDATE_REQUEST = "UPDATE membre"
        + "SET nom = ?, telephone = ?, limitePret = ? "
        + "WHERE idMembre = ?";

    private static final String DELETE_REQUEST = "DELETE FROM membre"
        + "WHERE idMembre = ?";

    private static final String GET_ALL_REQUEST = "SELECT idMembre, nom, telephone, limitePret, nbpret"
        + " FROM membre";

    private static final String FIND_BY_NOM = "SELECT idMembre, nom, telephone, limitePret, nbpret"
        + "FROM membre"
        + "where nom like ?";

    private static final String FIND_BY_TEL = "SELECT idMembre, nom, telephone, limitePret, nbpret"
        + "FROM membre"
        + "where telephone = ?";

    public MembreDAO(Connexion connexion) {
        super(connexion);
    }

    public void ajouter(int idMembre,
        String nom,
        long telephone,
        int limitePret) throws DAOException {
        /* Ajout du membre. */

        try(
            PreparedStatement preparedAjout = getConnection().prepareStatement(ADD_REQUEST);) {

            preparedAjout.setInt(1,
                idMembre);
            preparedAjout.setString(2,
                nom);
            preparedAjout.setLong(3,
                telephone);
            preparedAjout.setInt(4,
                limitePret);
            preparedAjout.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException();
        }
    }

    public MembreDTO read(int idMembre) throws DAOException {
        MembreDTO membreDTO = null;
        try(
            PreparedStatement readPreparedStatement = getConnection().prepareStatement(MembreDAO.READ_REQUEST)) {
            readPreparedStatement.setInt(1,
                idMembre);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    membreDTO = new MembreDTO();
                    membreDTO.setIdMembre(resultSet.getInt(1));
                    membreDTO.setNom(resultSet.getString(2));
                    membreDTO.setTelephone(resultSet.getInt(3));
                    membreDTO.setLimitePret(resultSet.getInt(4));
                    membreDTO.setNbPret(resultSet.getInt(6));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return membreDTO;
    }

    public void update(MembreDTO MembreDTO) throws DAOException {
        try(
            PreparedStatement updatePreparedStatement = getConnection().prepareStatement(MembreDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setString(1,
                MembreDTO.getNom());
            updatePreparedStatement.setInt(2,
                MembreDTO.getTelephone());
            updatePreparedStatement.setInt(3,
                MembreDTO.getNbPret());
            updatePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    public void delete(MembreDTO MembreDTO) throws DAOException {
        try(
            PreparedStatement deletePreparedStatement = getConnection().prepareStatement(MembreDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setInt(1,
                MembreDTO.getIdMembre());
            deletePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    public List<MembreDTO> getAll() throws DAOException {
        List<MembreDTO> membre = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = getConnection().prepareStatement(MembreDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                MembreDTO MembreDTO = null;
                if(resultSet.next()) {
                    membre = new ArrayList<>();
                    do {
                        MembreDTO = new MembreDTO();
                        MembreDTO.setIdMembre(resultSet.getInt(1));
                        MembreDTO.setNom(resultSet.getString(2));
                        MembreDTO.setTelephone(resultSet.getInt(3));
                        MembreDTO.setLimitePret(resultSet.getInt(4));
                        MembreDTO.setNbPret(resultSet.getInt(6));
                        membre.add(MembreDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return membre;
    }

    public List<MembreDTO> findByNom() throws DAOException {
        List<MembreDTO> membre = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = getConnection().prepareStatement(MembreDAO.FIND_BY_NOM)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                MembreDTO MembreDTO = null;
                if(resultSet.next()) {
                    membre = new ArrayList<>();
                    do {
                        MembreDTO = new MembreDTO();
                        MembreDTO.setIdMembre(resultSet.getInt(1));
                        MembreDTO.setNom(resultSet.getString(2));
                        MembreDTO.setTelephone(resultSet.getInt(3));
                        MembreDTO.setLimitePret(resultSet.getInt(4));
                        MembreDTO.setNbPret(resultSet.getInt(6));
                        membre.add(MembreDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return membre;
    }

    public MembreDTO findByTel(int idMembre) throws DAOException {
        MembreDTO membreDTO = null;
        try(
            PreparedStatement readPreparedStatement = getConnection().prepareStatement(MembreDAO.FIND_BY_TEL)) {
            readPreparedStatement.setInt(1,
                idMembre);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    membreDTO = new MembreDTO();
                    membreDTO.setIdMembre(resultSet.getInt(1));
                    membreDTO.setNom(resultSet.getString(2));
                    membreDTO.setTelephone(resultSet.getInt(3));
                    membreDTO.setLimitePret(resultSet.getInt(4));
                    membreDTO.setNbPret(resultSet.getInt(6));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return membreDTO;
    }
}
