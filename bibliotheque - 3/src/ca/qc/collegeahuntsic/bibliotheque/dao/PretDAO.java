
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>pret</code>.
 *
 *
 */

public class PretDAO extends DAO {
    private static final long serialVersionUID = 1L;

    private static final String ADD_REQUEST = "INSERT INTO pret (idPret, idMembre, idLivre, datePret, dateRetour) "
        + "VALUES (?, ?, ?, NULL, NULL)";

    private static final String READ_REQUEST = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret "
        + "WHERE idPret = ?";

    private static final String UPDATE_REQUEST = "UPDATE pret "
        + "SET idMembre = ?, idLivre = ?, datePret = ?, dateRetour = ?"
        + "WHERE idPret = ?";

    private static final String DELETE_REQUEST = "DELETE FROM pret "
        + "WHERE idPret = ?";

    private static final String GET_ALL_REQUEST = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret";

    /**
                      * Crée un DAO à partir d'une connexion à la base de données.
                       *
                       * @param connexion La connexion à utiliser
                        */

    public PretDAO(Connexion connexion) {
        super(connexion);
    }

    /**
    * Ajoute un nouveau livre.
    *
    * @param Ajouter un Pret
    * @throws DAOException S'il y a une erreur avec la base de données
     */
    public void add(PretDTO pretDTO) throws DAOException {
        try(
            PreparedStatement addPreparedStatement = getConnection().prepareStatement(PretDAO.ADD_REQUEST)) {
            addPreparedStatement.setInt(1,
                pretDTO.getIdPret());
            addPreparedStatement.setTimestamp(4,
                pretDTO.getDatePret());
            addPreparedStatement.setTimestamp(5,
                pretDTO.getDateRetour());
            addPreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
    * Lit un pret.
    *
    * @param idPret L'ID du pret
    * @throws DAOException S'il y a une erreur avec la base de données
    */
    public PretDTO read(int idPret) throws DAOException {
        PretDTO pretDTO = null;
        try(
            PreparedStatement readPreparedStatement = getConnection().prepareStatement(PretDAO.READ_REQUEST)) {
            readPreparedStatement.setInt(1,
                idPret);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    pretDTO = new PretDTO();
                    pretDTO.setIdPret(resultSet.getInt(1));
                    pretDTO.setDatePret(resultSet.getTimestamp(4));
                    pretDTO.setDateRetour(resultSet.getTimestamp(5));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return pretDTO;
    }

    /**
    * Met à jour un pret.
    *
    * @param pretDTO Le pret à mettre à jour
    * @throws DAOException S'il y a une erreur avec la base de données
    */
    public void update(PretDTO pretDTO) throws DAOException {
        try(
            PreparedStatement updatePreparedStatement = getConnection().prepareStatement(PretDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setTimestamp(3,
                pretDTO.getDatePret());
            updatePreparedStatement.setTimestamp(4,
                pretDTO.getDateRetour());
            updatePreparedStatement.setInt(5,
                pretDTO.getIdPret());
            updatePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
    * Supprime un pret.
    *
    * @param pretDTO Le pret à supprimer
    * @throws DAOException S'il y a une erreur avec la base de données
    */
    public void delete(PretDTO pretDTO) throws DAOException {
        try(
            PreparedStatement deletePreparedStatement = getConnection().prepareStatement(PretDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setInt(1,
                pretDTO.getIdPret());
            deletePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
    * Trouve tous les prets.
    *
    * @return La liste des prets ; une liste vide sinon
    * @throws DAOException S'il y a une erreur avec la base de données
    */
    public List<PretDTO> getAll() throws DAOException {
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = getConnection().prepareStatement(PretDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getInt(1));
                        pretDTO.setDatePret(resultSet.getTimestamp(4));
                        pretDTO.setDateRetour(resultSet.getTimestamp(5));
                        prets.add(pretDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return prets;
    }
}
