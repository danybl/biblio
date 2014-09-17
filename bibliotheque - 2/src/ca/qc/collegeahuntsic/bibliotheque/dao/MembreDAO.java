
package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.Connection;

public class MembreDAO extends DAO {

    private static final long serialVersionUID = 1L;

    //    private static final String INSERT_REQUEST = "INSERT INTO membre (idMembre, nom, telephone, limitePret, nbpret)"
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

    public Connection getConnection() {
        return null;

    }
}
