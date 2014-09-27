
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
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

public class ReservationDAO extends DAO {
    private static final long serialVersionUID = 1L;

    // Region String SQL
    private static final String ADD_REQUEST = "INSERT INTO reservation (idReservation, idlivre, idMembre, dateReservation) "
        + "VALUES (?,?,?,TO_DATE(?,'YYYY-MM-DD'))";

    private static final String READ_REQUEST = "SELECT idReservation, idLivre, idMembre, dateReservation "
        + "FROM reservation "
        + "WHERE idReservation = ?";

    private static final String UPDATE_REQUEST = "UPDATE reservation "
        + "SET idLivre = ?, idMembre = ?, dateReservation = ?"
        + "WHERE idReservation = ?";

    private static final String DELETE_REQUEST = "DELETE FROM reservation "
        + "WHERE idReservation = ?";

    private static final String GET_ALL_REQUEST = "SELECT idReservation, idLivre, idMembre, dateReservation "
        + "FROM reservation";

    private static final String FIND_BY_ID_LIVRE = "SELECT idReservation, idLivre, idMembre, dateReservation "
        + "FROM reservation "
        + "WHERE idLivre = ? ";

    private static final String FIND_BY_ID_MEMBRE = "SELECT idReservation, idLivre, idMembre, dateReservation "
        + "FROM reservation "
        + "WHERE idMembre = ? ";

    private static final String FIND_BY_DATE_RESERVATION = "SELECT idReservation, idLivre, idMembre, dateReservation "
        + "FROM reservation "
        + "WHERE dateReservation = ? ";

    private static final String CREATE_PRIMARY_KEY = "SELECT nom_sequence.NEXTVAL from DUAL";

    // EndRegion
    public ReservationDAO(Connexion connexion) {
        super(connexion);
    }

    public void add(ReservationDTO reservationDTO) throws DAOException {
        try(
            PreparedStatement addPreparedStatement = getConnection().prepareStatement(ReservationDAO.ADD_REQUEST)) {
            addPreparedStatement.setInt(1,
                getPrimaryKey());
            addPreparedStatement.setInt(2,
                reservationDTO.getLivreDTO().getIdLivre());
            addPreparedStatement.setInt(3,
                reservationDTO.getMembreDTO().getIdMembre());
            addPreparedStatement.setTimestamp(4,
                reservationDTO.getDateReservation());
            addPreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }// FinAdd

    public ReservationDTO read(int idReservation) throws DAOException {
        ReservationDTO reservationDTO = null;
        try(
            PreparedStatement readPreparedStatement = getConnection().prepareStatement(ReservationDAO.READ_REQUEST)) {
            readPreparedStatement.setInt(1,
                idReservation);
            try(
                ResultSet resultSet = readPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    reservationDTO = new ReservationDTO();
                    reservationDTO.setIdReservation(resultSet.getInt(1));
                    reservationDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                    reservationDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
                    reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservationDTO;
    }// FinRead

    public void update(ReservationDTO reservationDTO) throws DAOException {
        try(
            PreparedStatement updatePreparedStatement = getConnection().prepareStatement(ReservationDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setInt(1,
                getPrimaryKey());
            updatePreparedStatement.setInt(2,
                reservationDTO.getLivreDTO().getIdLivre());
            updatePreparedStatement.setInt(3,
                reservationDTO.getMembreDTO().getIdMembre());
            updatePreparedStatement.setTimestamp(4,
                reservationDTO.getDateReservation());
            updatePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }// FinUpdate

    public void delete(ReservationDTO reservationDTO) throws DAOException {
        try(
            PreparedStatement deletePreparedStatement = getConnection().prepareStatement(ReservationDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setInt(1,
                reservationDTO.getIdReservation());
            deletePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }// FinDelete

    public List<ReservationDTO> getAll() throws DAOException {
        List<ReservationDTO> reservations = Collections.EMPTY_LIST;
        try(
            PreparedStatement getAllPreparedStatement = getConnection().prepareStatement(ReservationDAO.GET_ALL_REQUEST)) {
            try(
                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
                ReservationDTO reservationDTO = null;
                if(resultSet.next()) {
                    reservations = new ArrayList<>();
                    do {
                        reservationDTO = new ReservationDTO();
                        reservationDTO.setIdReservation(resultSet.getInt(1));
                        reservationDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                        reservationDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
                        reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                        reservations.add(reservationDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservations;
    }// FinGetAll

    public List<ReservationDTO> findByLivre(LivreDTO livreDTO) throws DAOException {
        List<ReservationDTO> reservations = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByLivrePreparedStatement = getConnection().prepareStatement(ReservationDAO.FIND_BY_ID_LIVRE)) {
            findByLivrePreparedStatement.setInt(1,
                livreDTO.getIdLivre());
            try(
                ResultSet resultSet = findByLivrePreparedStatement.executeQuery()) {
                ReservationDTO reservationDTO = null;
                if(resultSet.next()) {
                    reservations = new ArrayList<>();
                    do {
                        reservationDTO = new ReservationDTO();
                        reservationDTO.setIdReservation(resultSet.getInt(1));
                        reservationDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                        reservationDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
                        reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                        reservations.add(reservationDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservations;
    }// FinFindByIDLivre

    public List<ReservationDTO> findByMembre(MembreDTO membreDTO) throws DAOException {
        List<ReservationDTO> reservations = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByMembrePreparedStatement = getConnection().prepareStatement(ReservationDAO.FIND_BY_ID_MEMBRE)) {
            findByMembrePreparedStatement.setInt(1,
                membreDTO.getIdMembre());
            try(
                ResultSet resultSet = findByMembrePreparedStatement.executeQuery()) {
                ReservationDTO reservationDTO = null;
                if(resultSet.next()) {
                    reservations = new ArrayList<>();
                    do {
                        reservationDTO = new ReservationDTO();
                        reservationDTO.setIdReservation(resultSet.getInt(1));
                        reservationDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                        reservationDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
                        reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                        reservations.add(reservationDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservations;
    }// FinFindByIDMembre

    public List<ReservationDTO> findByDateReservation(Timestamp dateReservation) throws DAOException {
        List<ReservationDTO> reservations = Collections.EMPTY_LIST;
        try(
            PreparedStatement findByDateReservationPreparedStatement = getConnection().prepareStatement(ReservationDAO.FIND_BY_DATE_RESERVATION)) {
            findByDateReservationPreparedStatement.setTimestamp(1,
                dateReservation);
            try(
                ResultSet resultSet = findByDateReservationPreparedStatement.executeQuery()) {
                ReservationDTO reservationDTO = null;
                if(resultSet.next()) {
                    reservations = new ArrayList<>();
                    do {
                        reservationDTO = new ReservationDTO();
                        reservationDTO.setIdReservation(resultSet.getInt(1));
                        reservationDTO.getLivreDTO().setIdLivre(resultSet.getInt(2));
                        reservationDTO.getMembreDTO().setIdMembre(resultSet.getInt(3));
                        reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                        reservations.add(reservationDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservations;
    }

    public int getPrimaryKey() throws DAOException {
        Integer primaryKey = null;
        try(
            Statement createPrimaryKeyStatement = getConnection().createStatement();
            ResultSet resultSet = createPrimaryKeyStatement.executeQuery(ReservationDAO.CREATE_PRIMARY_KEY);) {
            if(resultSet.next()) {
                primaryKey = (Integer) resultSet.getObject(1);

            }
            return primaryKey.intValue();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }
}