
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
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

    private static final String FIND_BY_ID_LIVRE = "SELECT idReservation, idLivre, idMembre, dateReservation "
        + "FROM reservation "
        + "WHERE idLivre = ? ";

    private static final String FIND_BY_ID_MEMBRE = "SELECT idReservation, idLivre, idMembre, dateReservation "
        + "FROM reservation "
        + "WHERE idMembre = ? ";

    private static final String FIND_BY_DATE_PRET = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret"
        + "WHERE datePret = ? ";

    private static final String FIND_BY_DATE_RETOUR = "SELECT idPret, idMembre, idLivre, datePret, dateRetour "
        + "FROM pret"
        + "WHERE dateRetour = ? ";

    private static final String CREATE_PRIMARY_KEY = "SELECT nom_sequence.NEXTVAL from DUAL";

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
                getPrimaryKey());
            addPreparedStatement.setInt(2,
                pretDTO.getLivreDTO().getIdLivre());
            addPreparedStatement.setInt(3,
                pretDTO.getMembreDTO().getIdMembre());
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
                    pretDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                    pretDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
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
            updatePreparedStatement.setInt(2,
                pretDTO.getLivreDTO().getIdLivre());
            updatePreparedStatement.setInt(3,
                pretDTO.getMembreDTO().getIdMembre());
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
                        pretDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                        pretDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
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

    public PretDTO findByLivre(LivreDTO livreDTO) throws DAOException {
        PretDTO pretDTO = null;
        try(
            PreparedStatement findByIDLivrePreparedStatement = getConnection().prepareStatement(PretDAO.FIND_BY_ID_LIVRE)) {
            findByIDLivrePreparedStatement.setInt(1,
                livreDTO.getIdLivre());
            try(
                ResultSet resultSet = findByIDLivrePreparedStatement.executeQuery()) {

                if(resultSet.next()) {

                    pretDTO = new PretDTO();
                    pretDTO.setIdPret(resultSet.getInt(1));
                    pretDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                    pretDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
                    pretDTO.setDatePret(resultSet.getTimestamp(4));
                    pretDTO.setDateRetour(resultSet.getTimestamp(5));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return pretDTO;
    }

    public List<PretDTO> findByMembre(MembreDTO membreDTO) throws DAOException {
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByIDMembrePreparedStatement = getConnection().prepareStatement(PretDAO.FIND_BY_ID_MEMBRE)) {
            findByIDMembrePreparedStatement.setInt(1,
                membreDTO.getIdMembre());
            try(
                ResultSet resultSet = findByIDMembrePreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getInt(1));
                        pretDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                        pretDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
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

    public List<PretDTO> findByDatePret(Timestamp datePret) throws DAOException {
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByIDMembrePreparedStatement = getConnection().prepareStatement(PretDAO.FIND_BY_DATE_PRET)) {
            findByIDMembrePreparedStatement.setTimestamp(1,
                datePret);
            try(
                ResultSet resultSet = findByIDMembrePreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getInt(1));
                        pretDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                        pretDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
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

    public List<PretDTO> findByDateRetour(Timestamp dateRetour) throws DAOException {
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByIDMembrePreparedStatement = getConnection().prepareStatement(PretDAO.FIND_BY_DATE_RETOUR)) {
            findByIDMembrePreparedStatement.setTimestamp(1,
                dateRetour);
            try(
                ResultSet resultSet = findByIDMembrePreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getInt(1));
                        pretDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                        pretDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
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

    public int getPrimaryKey() throws DAOException {
        Integer primaryKey = null;
        try(
            Statement createPrimaryKeyStatement = getConnection().createStatement();
            ResultSet resultSet = createPrimaryKeyStatement.executeQuery(PretDAO.CREATE_PRIMARY_KEY);) {
            if(resultSet.next()) {
                primaryKey = (Integer) resultSet.getObject(1);

            }
            return primaryKey.intValue();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }
}
