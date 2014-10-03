
package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.DTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>pret</code>.
 *
 *
 */

public class PretDAO extends DAO implements IPretDAO {

    /**
     * Déclaration des final String
     *
     * Les String contiennent du code sql pour effectuer des insertions, des lectures, des mis à jour,
     * des suppressions et de la création de la clé primaire dans la table Pret
     * */
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

    private static final String CREATE_PRIMARY_KEY = "SELECT SEQ_ID_PRET.NEXTVAL from DUAL";

    public PretDAO(Class<PretDTO> pretDTOClass) throws InvalidDTOClassException {
        super(pretDTOClass);
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
            PretDAO.CREATE_PRIMARY_KEY);
    }

    /**
     * Ajoute un nouveau prêt.
     *
     * @param pretDTO Le pret à ajouter
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
        PretDTO pretDTO = (PretDTO) dto;
        try(
            PreparedStatement addPreparedStatement = connexion.getConnection().prepareStatement(PretDAO.ADD_REQUEST)) {
            pretDTO.setIdPret(PretDAO.getPrimaryKey(connexion));
            addPreparedStatement.setString(1,
                pretDTO.getIdPret());
            addPreparedStatement.setString(2,
                pretDTO.getLivreDTO().getIdLivre());
            addPreparedStatement.setString(3,
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
    @Override
    public PretDTO get(Connexion connexion,
        Serializable primaryKey) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(primaryKey == null) {
            throw new InvalidPrimaryKeyException("La clef primaire ne peut être null");
        }
        String idPret = (String) primaryKey;
        PretDTO pretDTO = null;
        try(
            PreparedStatement readPreparedStatement = connexion.getConnection().prepareStatement(PretDAO.READ_REQUEST)) {
            readPreparedStatement.setString(1,
                idPret);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    pretDTO = new PretDTO();
                    pretDTO.setIdPret(resultSet.getString(1));
                    pretDTO.getLivreDTO().setIdLivre(resultSet.getString(2));
                    pretDTO.getMembreDTO().setIdMembre(resultSet.getString(3));
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
        PretDTO pretDTO = (PretDTO) dto;
        try(
            PreparedStatement updatePreparedStatement = connexion.getConnection().prepareStatement(PretDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setString(2,
                pretDTO.getLivreDTO().getIdLivre());
            updatePreparedStatement.setString(3,
                pretDTO.getMembreDTO().getIdMembre());
            updatePreparedStatement.setTimestamp(3,
                pretDTO.getDatePret());
            updatePreparedStatement.setTimestamp(4,
                pretDTO.getDateRetour());
            updatePreparedStatement.setString(5,
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
        PretDTO pretDTO = (PretDTO) dto;

        try(
            PreparedStatement deletePreparedStatement = connexion.getConnection().prepareStatement(PretDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setString(1,
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
    @Override
    public List<PretDTO> getAll(Connexion connexion,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = connexion.getConnection().prepareStatement(PretDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getString(1));
                        pretDTO.getLivreDTO().setIdLivre(resultSet.getString(2));
                        pretDTO.getMembreDTO().setIdMembre(resultSet.getString(3));
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

    /**
     * Trouve les prêts à partir d'un livre.
     *
     * @param livreDTO Le livre à utiliser
     * @return Les prêts correspondant ; null sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    @Override
    public List<PretDTO> findByLivre(Connexion connexion,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(idLivre == null) {
            throw new InvalidCriterionException("Le idLivre ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByIDLivrePreparedStatement = connexion.getConnection().prepareStatement(PretDAO.FIND_BY_ID_LIVRE)) {
            findByIDLivrePreparedStatement.setString(1,
                idLivre);
            try(
                ResultSet resultSet = findByIDLivrePreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getString(1));
                        pretDTO.getLivreDTO().setIdLivre(resultSet.getString(2));
                        pretDTO.getMembreDTO().setIdMembre(resultSet.getString(3));
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

    /**
     * Trouve les prêts à partir d'un membre.
     *
     * @param membreDTO Le membre à utiliser
     * @return Les prêts correspondant ; null sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    @Override
    public List<PretDTO> findByMembre(Connexion connexion,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(idMembre == null) {
            throw new InvalidCriterionException("Le idMembre ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByIDMembrePreparedStatement = connexion.getConnection().prepareStatement(PretDAO.FIND_BY_ID_MEMBRE)) {
            findByIDMembrePreparedStatement.setString(1,
                idMembre);
            try(
                ResultSet resultSet = findByIDMembrePreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getString(1));
                        pretDTO.getLivreDTO().setIdLivre(resultSet.getString(2));
                        pretDTO.getMembreDTO().setIdMembre(resultSet.getString(3));
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

    /**
     * Trouve les prêts à partir de la date.
     *
     * @param datePret La date à utiliser
     * @return Les prêts correspondant ; null sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    @Override
    public List<PretDTO> findByDatePret(Connexion connexion,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(datePret == null) {
            throw new InvalidCriterionException("Le datePret ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByIDMembrePreparedStatement = connexion.getConnection().prepareStatement(PretDAO.FIND_BY_DATE_PRET)) {
            findByIDMembrePreparedStatement.setTimestamp(1,
                datePret);
            try(
                ResultSet resultSet = findByIDMembrePreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getString(1));
                        pretDTO.getLivreDTO().setIdLivre(resultSet.getString(2));
                        pretDTO.getMembreDTO().setIdMembre(resultSet.getString(3));
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

    /**
     * Trouve les prêts à partir de la date de retour.
     *
     * @param dateRetour La date à utiliser
     * @return Les prêts correspondant ; null sinon
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    @Override
    public List<PretDTO> findByDateRetour(Connexion connexion,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException {
        if(connexion == null) {
            throw new InvalidHibernateSessionException("La connexion ne peut être null");
        }
        if(dateRetour == null) {
            throw new InvalidCriterionException("Le datePret ne peut être null");
        }
        if(sortByPropertyName == null) {
            throw new InvalidSortByPropertyException("La propriété utilisée pour classer ne peut être null");
        }
        List<PretDTO> prets = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByIDMembrePreparedStatement = connexion.getConnection().prepareStatement(PretDAO.FIND_BY_DATE_RETOUR)) {
            findByIDMembrePreparedStatement.setTimestamp(1,
                dateRetour);
            try(
                ResultSet resultSet = findByIDMembrePreparedStatement.executeQuery()) {
                PretDTO pretDTO = null;
                if(resultSet.next()) {
                    prets = new ArrayList<>();
                    do {
                        pretDTO = new PretDTO();
                        pretDTO.setIdPret(resultSet.getString(1));
                        pretDTO.getLivreDTO().setIdLivre(resultSet.getString(2));
                        pretDTO.getMembreDTO().setIdMembre(resultSet.getString(3));
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