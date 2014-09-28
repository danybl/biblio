
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>membre</code>.
 *
 *
 */

public class MembreDAO extends DAO {

    private static final long serialVersionUID = 1L;

    /*
     * Les chaines de caractères String pour effectuer des insertions, des lectures, des mis à jour,
     * des suppressions et de la création de la clé primaire dans la table membre*/
    //region

    private static final String ADD_REQUEST = "INSERT INTO membre (nom, telephone, limitePret, nbpret)"
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

    private static final String CREATE_PRIMARY_KEY = "SELECT nom_sequence.NEXTVAL from DUAL";

    //endregion

    /**
     * Crée un DAO à partir d'une connexion à la base de données.
     *
     * @param connexion La connexion à utiliser
     */
    public MembreDAO(Connexion connexion) {
        super(connexion);
    }

    /**
     * Ajoute un nouveau membre.
     *
     * @param membreDTO Le membre à ajouter
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    /*
     * Addition d'un membre dans la table Membre avec un PreparedStatement
     * qui recoit un String d'addition ADD_REQUEST en paramètre et
     * la gestion de toutes les exceptions possibles
     * */
    public void add(MembreDTO membreDTO) throws DAOException {

        try(
            PreparedStatement preparedAjout = getConnection().prepareStatement(ADD_REQUEST);) {

            preparedAjout.setString(1,
                membreDTO.getNom());
            preparedAjout.setLong(2,
                membreDTO.getTelephone());
            preparedAjout.setInt(3,
                membreDTO.getLimitePret());
            preparedAjout.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException();
        }
    }

    /**
     * Lit un membre.
     *
     * @param idMembre L'ID du membre à lire
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    /*
     * Lecture d'un membre à partir de la table Membre avec un PreparedStatement
     * qui recoit un String de lecture READ_REQUEST en paramètre
     * et la gestion de toutes les exceptions possibles
     * */
    public MembreDTO read(int id) throws DAOException {
        MembreDTO membreDTO = null;
        try(
            PreparedStatement readPreparedStatement = getConnection().prepareStatement(MembreDAO.READ_REQUEST)) {
            readPreparedStatement.setInt(1,
                id);
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

    /**
     * Met à jour un membre.
     *
     * @param membreDTO Le membre à mettre à jour
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    /*
     * Mis à jour d'un membre dans la table Membre avec un PreparedStatement
     * qui recoit un String de mis à jour UPDATE_REQUEST en paramètre
     * et la gestion de toutes les exceptions possibles
     * */
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

    /**
     * Supprime un membre.
     *
     * @param membreDTO Le membre à supprimer
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    /*
     * Suppression d'un membre dans la table Membre avec un PreparedStatement
     * qui recoit un String de suppression DELETE_REQUEST en paramètre et la
     * gestion de toutes les exceptions possibles
     * */
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

    /**
     * Trouve tous les membres.
     *
     * @return La liste des membres ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    /*
     * Recherche de tous les membres dans la table Membre avec un PreparedStatement
     * qui recoit un String de lecture générale GET_ALL_REQUEST en paramètre et
     * la gestion de toutes les exceptions possibles
     * */
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

    /**
     * Trouve les membres à partir d'un nom.
     *
     * @param nom Le nom à utiliser
     * @return La liste des noms correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    /*
     * Recherche d'un membre dans la table Membre par son nom avec un PreparedStatement
     * qui recoit un String de recherche FIND_BY_NOM en paramètre et la
     * gestion de toutes les exceptions possibles
     * */
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

    /**
     * Trouve les membres à partir d'un numero de telephone.
     *
     * @param numero de telephone Le numero de telephone à utiliser
     * @return La liste des numeros de telephone correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    /*
     * Recherche d'un numero de telephone dans la table Membre par son numero de telephone avec un PreparedStatement
     * qui recoit un String de recherche FIND_BY_TEL en paramètre et la
     * gestion de toutes les exceptions possibles
     * */
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

    /*
     * Méthode de génération de la clé primaire d'un livre donné à l'aide d'un Statement
     * qui recoit en paramètre un String de création de la clé primaire CREATE_PRIMARY_KEY*/
    public int getPrimaryKey() throws DAOException {
        Integer primaryKey = null;
        try(
            Statement createPrimaryKeyStatement = getConnection().createStatement();
            ResultSet resultSet = createPrimaryKeyStatement.executeQuery(MembreDAO.CREATE_PRIMARY_KEY);) {
            if(resultSet.next()) {
                primaryKey = (Integer) resultSet.getObject(1);

            }
            return primaryKey.intValue();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }
}
