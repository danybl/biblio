
package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.DTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>membre</code>.
 */

public class MembreDAO extends DAO implements IMembreDAO {

    /**
     * Déclaration des final String
     *
     * Les String contiennent du code sql pour effectuer des insertions, des lectures, des mis à jour,
     * des suppressions et de la création de la clé primaire dans la table Membre
     * */

    private static final String ADD_REQUEST = "INSERT INTO membre (idMembre, nom, telephone, limitePret)"
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

    private static final String CREATE_PRIMARY_KEY = "SELECT SEQ_ID_MEMBRE.NEXTVAL from DUAL";

    public MembreDAO(Class<MembreDTO> memebreDTOClass) throws InvalidDTOClassException {
        super(memebreDTOClass);
        // TODO Auto-generated constructor stub
    }

    /**
     * Génère la clé primaire
     *
     * @return La clé primaire généré
     * @throws DAOException S'il y a une erreur avec la base de données
     * */
    private static String getPrimaryKey(Connexion connexion) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyRequestException,
        DAOException {
        return DAO.getPrimaryKey(connexion,
            MembreDAO.CREATE_PRIMARY_KEY);

    }

    /**
     * Ajoute un nouveau membre.
     *
     * @param membreDTO Le membre à ajouter
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    @Override
    public void add(Connexion connexion,
        DTO dto) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        InvalidPrimaryKeyRequestException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(dto == null) {
            throw new InvalidDTOException("Le DTO ne peut être null");
        }
        if(!dto.getClass().equals(getDtoClass())) {
            throw new InvalidDTOClassException("Le DTO doit être un "
                + getDtoClass().getName());
        }
        MembreDTO membreDTO = (MembreDTO) dto;

        try(
            PreparedStatement preparedAjout = connexion.getConnection().prepareStatement(ADD_REQUEST);) {
            membreDTO.setIdMembre(MembreDAO.getPrimaryKey(connexion));
            preparedAjout.setString(1,
                membreDTO.getIdMembre());
            preparedAjout.setString(2,
                membreDTO.getNom());
            preparedAjout.setString(3,
                membreDTO.getTelephone());
            preparedAjout.setString(4,
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
    @Override
    public MembreDTO get(Connexion connexion,
        Serializable primaryKey) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(primaryKey == null) {
            throw new InvalidPrimaryKeyException("La clef primaire ne peut être null");
        }
        String idMembre = (String) primaryKey;
        MembreDTO membreDTO = null;
        try(
            PreparedStatement readPreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.READ_REQUEST)) {
            readPreparedStatement.setString(1,
                idMembre);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    membreDTO = new MembreDTO();
                    membreDTO.setIdMembre(resultSet.getString(1));
                    membreDTO.setNom(resultSet.getString(2));
                    membreDTO.setTelephone(resultSet.getString(3));
                    membreDTO.setLimitePret(resultSet.getString(4));
                    membreDTO.setNbPret(resultSet.getString(5));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return membreDTO;
    }

    /**
     * Met à jour d'un membre.
     *
     * @param membreDTO Le membre à mettre à jour
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    @Override
    public void update(Connexion connexion,
        DTO dto) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(dto == null) {
            throw new InvalidDTOException("Le DTO ne peut être null");
        }
        if(!dto.getClass().equals(getDtoClass())) {
            throw new InvalidDTOClassException("Le DTO doit être un "
                + getDtoClass().getName());
        }
        MembreDTO membreDTO = (MembreDTO) dto;
        try(
            PreparedStatement updatePreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setString(1,
                membreDTO.getNom());
            updatePreparedStatement.setString(2,
                membreDTO.getTelephone());
            updatePreparedStatement.setString(3,
                membreDTO.getNbPret());
            updatePreparedStatement.setString(4,
                membreDTO.getIdMembre());
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
    @Override
    public void delete(Connexion connexion,
        DTO dto) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(dto == null) {
            throw new InvalidDTOException("Le DTO ne peut être null");
        }
        if(!dto.getClass().equals(getDtoClass())) {
            throw new InvalidDTOClassException("Le DTO doit être un "
                + getDtoClass().getName());
        }
        MembreDTO membreDTO = (MembreDTO) dto;
        try(
            PreparedStatement deletePreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setString(1,
                membreDTO.getIdMembre());
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
    @Override
    public List<MembreDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<MembreDTO> membres = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                MembreDTO membreDTO = null;
                if(resultSet.next()) {
                    membres = new ArrayList<>();
                    do {
                        membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getString(1));
                        membreDTO.setNom(resultSet.getString(2));
                        membreDTO.setTelephone(resultSet.getString(3));
                        membreDTO.setLimitePret(resultSet.getString(4));
                        membreDTO.setNbPret(resultSet.getString(6));
                        membres.add(membreDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return membres;
    }

    /**
     * Trouve les membres à partir d'un nom.
     *
     * @return La liste des membres ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    @Override
    public List<MembreDTO> findByNom(Connexion connexion,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(titre == null) {
            throw new InvalidCriterionException("Le titre ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<MembreDTO> membres = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByNomPreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.FIND_BY_NOM)) {
            findByNomPreparedStatement.setString(1,
                titre);
            try(
                ResultSet resultSet = findByNomPreparedStatement.executeQuery()) {
                MembreDTO membreDTO = null;
                if(resultSet.next()) {
                    membres = new ArrayList<>();
                    do {
                        membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getString(1));
                        membreDTO.setNom(resultSet.getString(2));
                        membreDTO.setTelephone(resultSet.getString(3));
                        membreDTO.setLimitePret(resultSet.getString(4));
                        membreDTO.setNbPret(resultSet.getString(5));
                        membres.add(membreDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return membres;
    }

    /**
     * Trouve les membres à partir d'un numero de telephone.
     *
     * @param numTelephone Le numéro à utiliser
     * @return La liste des membres ; une liste vide sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    @Override
    public List<MembreDTO> findByTel(Connexion connexion,
        String numTel,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(numTel != null
            && !numTel.equals("")) {
            throw new InvalidCriterionException("Le numero de telephone ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<MembreDTO> membres = Collections.EMPTY_LIST;
        try(
            PreparedStatement readPreparedStatement = connexion.getConnection().prepareStatement(MembreDAO.FIND_BY_TEL)) {
            readPreparedStatement.setString(1,
                numTel);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                MembreDTO membreDTO = null;
                if(resultSet.next()) {
                    membres = new ArrayList<>();
                    do {
                        membreDTO = new MembreDTO();
                        membreDTO.setIdMembre(resultSet.getString(1));
                        membreDTO.setNom(resultSet.getString(2));
                        membreDTO.setTelephone(resultSet.getString(3));
                        membreDTO.setLimitePret(resultSet.getString(4));
                        membreDTO.setNbPret(resultSet.getString(5));
                        membres.add(membreDTO);
                    } while(resultSet.next());
                }
            }
            return membres;
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }

    }
}