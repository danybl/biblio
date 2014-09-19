
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

public class MembreDAO extends DAO {

    private static final long serialVersionUID = 1L;

    private static final String ADD_REQUEST = "INSERT INTO membre (idMembre, nom, telephone, limitePret, nbpret)";

    //        + "VALUES (?,?,?,?,?)";
    //
    //    private static final String UPDATE_INFO_REQUEST = "UPDATE membre"
    //        + "SET nom = ?, telephone = ?, limitePret = ?, "
    //        + "WHERE idMembre = ?";
    //
    //    private static final String DELETE_REQUEST = "DELETE FROM membre"
    //        + "WHERE idMembre = ?";
    //
    //    private static final String GET_ALL_REQUEST = "SELECT idMembre, nom, telephone, limitePret, nbpret"
    //        + " FROM membre";
    //
    //    //a completer
    //    private static final String FIND_BY_ID = "";
    //
    //    private static final String FIND_BY_NOM = "SELECT idMembre, nom, telephone, limitePret, nbpret"
    //        + "FROM membre"
    //        + "where nom like ?";
    //
    //    private static final String FIND_BY_TEL = "SELECT idMembre, nom, telephone, limitePret, nbpret"
    //        + "FROM membre"
    //        + "where telephone = ?";

    public MembreDAO() {
        // TODO Auto-generated constructor stub
    }

    //    public void delete(LivreDTO livreDTO) throws DAOException {
    //        //        try(PreparedStatement deletePrepStmt = getConnection().preparedStament()){
    //        //
    //        //        }
    //    }

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
}
