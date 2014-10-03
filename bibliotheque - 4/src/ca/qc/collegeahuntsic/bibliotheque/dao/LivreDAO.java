
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>livre</code>.
 */

public class LivreDAO extends DAO {
    private static final long serialVersionUID = 1L;

    /**
     * Déclaration des final String
     *
     * Les String contiennent du code sql pour effectuer des insertions, des lectures, des mis à jour,
     * des suppressions et de la création de la clé primaire dans la table Livre
     * */

    private static final String ADD_REQUEST = "INSERT INTO livre (idLivre, titre, auteur, dateAcquisition) "
        + "VALUES (?, ?, ?, ?, NULL, NULL)";

    private static final String READ_REQUEST = "SELECT idLivre, titre, auteur, dateAcquisition "
        + "FROM livre "
        + "WHERE idLivre = ?";

    private static final String UPDATE_REQUEST = "UPDATE livre "
        + "SET titre = ?, auteur = ?, dateAcquisition = ? "
        + "WHERE idLivre = ?";

    private static final String DELETE_REQUEST = "DELETE FROM livre "
        + "WHERE idLivre = ?";

    private static final String GET_ALL_REQUEST = "SELECT idLivre, titre, auteur, dateAcquisition "
        + "FROM livre";

    private static final String FIND_BY_TITRE = "SELECT idLivre, titre, auteur, dateAcquisition "
        + "FROM livre "
        + "WHERE LOWER(titre) like %?%";

    private static final String FIND_BY_MEMBRE = "SELECT idLivre, titre, auteur, dateAcquisition "
        + "FROM livre "
        + "WHERE idMembre = ?";

    private static final String CREATE_PRIMARY_KEY = "SELECT SEQ_ID_LIVRE.NEXTVAL from DUAL";

    /**
     * Crée un DAO à partir d'une connexion à la base de données.
     *
     * @param connexion La connexion à utiliser
     */
    public LivreDAO(Connexion connexion) {
        super(connexion);
    }

    /**
     * Ajoute un nouveau livre.
     *
     * @param livreDTO Le livre à ajouter
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public void add(LivreDTO livreDTO) throws DAOException {
        try(
            PreparedStatement addPreparedStatement = getConnection().prepareStatement(LivreDAO.ADD_REQUEST)) {
            addPreparedStatement.setLong(1,
                getPrimaryKey());
            addPreparedStatement.setString(2,
                livreDTO.getTitre());
            addPreparedStatement.setString(3,
                livreDTO.getAuteur());
            addPreparedStatement.setTimestamp(4,
                livreDTO.getDateAcquisition());
            addPreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * Lit un livre.
     *
     * @param idLivre L'ID du livre à lire
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public LivreDTO read(long idLivre) throws DAOException {
        LivreDTO livreDTO = null;
        try(
            PreparedStatement readPreparedStatement = getConnection().prepareStatement(LivreDAO.READ_REQUEST)) {
            readPreparedStatement.setLong(1,
                idLivre);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    livreDTO = new LivreDTO();
                    livreDTO.setIdLivre(resultSet.getLong(1));
                    livreDTO.setTitre(resultSet.getString(2));
                    livreDTO.setAuteur(resultSet.getString(3));
                    livreDTO.setDateAcquisition(resultSet.getTimestamp(4));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return livreDTO;
    }

    /**
     * Met à jour un livre.
     *
     * @param livreDTO Le livre à mettre à jour
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public void update(LivreDTO livreDTO) throws DAOException {
        try(
            PreparedStatement updatePreparedStatement = getConnection().prepareStatement(LivreDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setString(1,
                livreDTO.getTitre());
            updatePreparedStatement.setString(2,
                livreDTO.getAuteur());
            updatePreparedStatement.setTimestamp(3,
                livreDTO.getDateAcquisition());
            updatePreparedStatement.setLong(6,
                livreDTO.getIdLivre());
            updatePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * Supprime un livre.
     *
     * @param livreDTO Le livre à supprimer
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public void delete(LivreDTO livreDTO) throws DAOException {
        try(
            PreparedStatement deletePreparedStatement = getConnection().prepareStatement(LivreDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setLong(1,
                livreDTO.getIdLivre());
            deletePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }

    /**
     * Trouve tous les livres.
     *
     * @return La liste des livres ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<LivreDTO> getAll() throws DAOException {
        List<LivreDTO> livres = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = getConnection().prepareStatement(LivreDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                LivreDTO livreDTO = null;
                if(resultSet.next()) {
                    livres = new ArrayList<>();
                    do {
                        livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getInt(1));
                        livreDTO.setTitre(resultSet.getString(2));
                        livreDTO.setAuteur(resultSet.getString(3));
                        livreDTO.setDateAcquisition(resultSet.getTimestamp(4));
                        livres.add(livreDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return livres;
    }

    /**
     * Trouve les livres à partir d'un titre.
     *
     * @param titre Le titre à utiliser
     * @return La liste des livres correspondants ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public List<LivreDTO> findByTitre(String titre) throws DAOException {
        List<LivreDTO> livres = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByTitrePreparedStatement = getConnection().prepareStatement(LivreDAO.FIND_BY_TITRE)) {
            findByTitrePreparedStatement.setString(1,
                titre);
            try(
                ResultSet resultSet = findByTitrePreparedStatement.executeQuery()) {
                LivreDTO livreDTO = null;
                if(resultSet.next()) {
                    livres = new ArrayList<>();
                    do {
                        livreDTO = new LivreDTO();
                        livreDTO.setIdLivre(resultSet.getInt(1));
                        livreDTO.setTitre(resultSet.getString(2));
                        livreDTO.setAuteur(resultSet.getString(3));
                        livreDTO.setDateAcquisition(resultSet.getTimestamp(4));
                        livres.add(livreDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return livres;
    }

    /**
     * Trouve les livres à partir d'un membre.
     *
     * @param membreDTO Le membre à utiliser
     * @return Le livre correspondant ; null sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    public LivreDTO findByMembre(MembreDTO membreDTO) throws DAOException {
        LivreDTO livreDTO = null;
        try(
            PreparedStatement findByMembrePreparedStatement = getConnection().prepareStatement(LivreDAO.FIND_BY_MEMBRE)) {
            findByMembrePreparedStatement.setLong(1,
                membreDTO.getIdMembre());
            try(
                ResultSet resultSet = findByMembrePreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    livreDTO = new LivreDTO();
                    livreDTO.setIdLivre(resultSet.getInt(1));
                    livreDTO.setTitre(resultSet.getString(2));
                    livreDTO.setAuteur(resultSet.getString(3));
                    livreDTO.setDateAcquisition(resultSet.getTimestamp(4));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return livreDTO;
    }

    /**
     * Génère la clé primaire
     *
     * @return La clé primaire généré
     * @throws DAOException S'il y a une erreur avec la base de données
     * */
    private long getPrimaryKey() throws DAOException {
        return getPrimaryKey(LivreDAO.CREATE_PRIMARY_KEY);
    }
}