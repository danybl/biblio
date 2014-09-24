
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
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

public class ReservationDAO extends DAO {
    private static final long serialVersionUID = 1L;

    //Region String SQL
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

    private static final String FIND_LIVRE_BY_ID = "SELECT idReservation, idLivre, idMembre, dateReservation "
        + "FROM reservation "
        + "WHERE idLivre = ? ";

    private static final String FIND_RESERVATION_BY_ID_MEBRE = "SELECT idReservation, idLivre, idMembre, dateReservation "
        + "FROM reservation "
        + "WHERE idMembre = ? ";

    private static final String FIND_BY_DATE_RESERVATION = "SELECT idReservation, idLivre, idMembre, dateReservation "
        + "FROM reservation "
        + "WHERE dateReservation = ? ";

    //EndRegion
    public ReservationDAO(Connexion connexion) {
        super(connexion);
    }

    public void add(ReservationDTO reservationDTO) throws DAOException {
        try(
            PreparedStatement addPreparedStatement = getConnection().prepareStatement(ReservationDAO.ADD_REQUEST)) {
            addPreparedStatement.setInt(1,
                reservationDTO.getIdReservation());
            addPreparedStatement.setInt(2,
                reservationDTO.getIdLivre());
            addPreparedStatement.setInt(3,
                reservationDTO.getIdMembre());
            addPreparedStatement.setTimestamp(4,
                reservationDTO.getDateReservation());
            addPreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }//FinAdd

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
                    reservationDTO.setIdLivre(resultSet.getInt(2));
                    reservationDTO.setIdMembre(resultSet.getInt(3));
                    reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservationDTO;
    }//FinRead

    public void update(ReservationDTO reservationDTO) throws DAOException {
        try(
            PreparedStatement updatePreparedStatement = getConnection().prepareStatement(ReservationDAO.UPDATE_REQUEST)) {
            updatePreparedStatement.setInt(1,
                reservationDTO.getIdReservation());
            updatePreparedStatement.setInt(2,
                reservationDTO.getIdLivre());
            updatePreparedStatement.setInt(3,
                reservationDTO.getIdMembre());
            updatePreparedStatement.setTimestamp(4,
                reservationDTO.getDateReservation());
            updatePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }//FinUpdate

    public void delete(ReservationDTO reservationDTO) throws DAOException {
        try(
            PreparedStatement deletePreparedStatement = getConnection().prepareStatement(ReservationDAO.DELETE_REQUEST)) {
            deletePreparedStatement.setInt(1,
                reservationDTO.getIdReservation());
            deletePreparedStatement.executeUpdate();
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
    }//FinDelete

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
                        reservationDTO.setIdLivre(resultSet.getInt(2));
                        reservationDTO.setIdMembre(resultSet.getInt(3));
                        reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                        reservations.add(reservationDTO);
                    } while(resultSet.next());
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservations;
    }//FinGetAll

    public ReservationDTO findByLivre(LivreDTO livreDTO) throws DAOException {
        ReservationDTO reservationDTO = null;
        try(
            PreparedStatement findByIDLivrePreparedStatement = getConnection().prepareStatement(ReservationDAO.FIND_LIVRE_BY_ID)) {
            findByIDLivrePreparedStatement.setInt(1,
                livreDTO.getIdLivre());
            try(
                ResultSet resultSet = findByIDLivrePreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    reservationDTO = new ReservationDTO();
                    reservationDTO.setIdReservation(resultSet.getInt(1));
                    reservationDTO.setIdLivre(resultSet.getInt(2));
                    reservationDTO.setIdMembre(resultSet.getInt(3));
                    reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservationDTO;
    }//FinFindByIDLivre

    public ReservationDTO findByMembre(MembreDTO membreDTO) throws DAOException {
        ReservationDTO reservationDTO = null;
        try(
            PreparedStatement findByIDMembrePreparedStatement = getConnection().prepareStatement(ReservationDAO.FIND_RESERVATION_BY_ID_MEBRE)) {
            findByIDMembrePreparedStatement.setInt(1,
                membreDTO.getIdMembre());
            try(
                ResultSet resultSet = findByIDMembrePreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    reservationDTO = new ReservationDTO();
                    reservationDTO.setIdReservation(resultSet.getInt(1));
                    reservationDTO.setIdLivre(resultSet.getInt(2));
                    reservationDTO.setIdMembre(resultSet.getInt(3));
                    reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservationDTO;
    }//FinFindByIDMembre

    public ReservationDTO findByDateReservation(LivreDTO livreDTO) throws DAOException {
        ReservationDTO reservationDTO = null;
        try(
            PreparedStatement findByDateReservationPreparedStatement = getConnection().prepareStatement(ReservationDAO.FIND_BY_DATE_RESERVATION)) {
            findByDateReservationPreparedStatement.setTimestamp(1,
                livreDTO.getDatePret());
            try(
                ResultSet resultSet = findByDateReservationPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    reservationDTO = new ReservationDTO();
                    reservationDTO.setIdReservation(resultSet.getInt(1));
                    reservationDTO.setIdLivre(resultSet.getInt(2));
                    reservationDTO.setIdMembre(resultSet.getInt(3));
                    reservationDTO.setDateReservation(resultSet.getTimestamp(4));
                }
            }
        } catch(SQLException sqlException) {
            throw new DAOException(sqlException);
        }
        return reservationDTO;
    }//FinFindByDateReservation
}
